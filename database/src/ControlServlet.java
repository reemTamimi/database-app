import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class ControlServlet extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	    private userDAO userDAO = new userDAO();
	    private String currentUser;
	    private HttpSession session=null;
	    
	    public ControlServlet()
	    {
	    	
	    }
	    
	    public void init()
	    {
	    	userDAO = new userDAO();
	    	currentUser= "";
	    }
	    
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String action = request.getServletPath();
	        System.out.println(action);
	    
	    try {
        	switch(action) {  
        	case "/login":
        		login(request,response);
        		break;
        	case "/register":
        		register(request, response);
        		break;
        	case "/initialize":
        		userDAO.init();
        		System.out.println("Database successfully initialized!");
        		rootPage(request,response,"","","");
        		break;
        	case "/find_copycats":
        		rootPage(request,response, request.getParameter("contestantWallet"),"","");
        		break;
        	case "/find_common":
        		rootPage(request,response,"",request.getParameter("contestant1"),request.getParameter("contestant2"));
        		break;
        	case "/root":
        		rootPage(request,response,"","","");
        		break;
        	case "/contest_search":
        		contestantPage_search(request,response, request.getParameter("pattern"));
        		break;
        	case "/contestant_submission":
        		contestantPage_submit(request,response);
        		break;
        	case "/judge_submission":
         		judgePage(request,response);
         		break;
//        	case "/sponsor":
//        		sponsorPage(request,response);
//        		break;
        	case "/sponsor_create":
        		sponsorCreate(request,response);
        		break;
        	case "/sponsor_distribute":
        		sponsorDistribute(request,response);
        		break;
        	case "/sponsor_do_distribute":
        		sponsorDoDistribute(request,response);
        		break;
        	case "/logout":
        		logout(request,response);
        		break;
        	 case "/list": 
                 System.out.println("The action is: list");
                 listUser(request, response);           	
                 break;
	    	}
	    }
	    catch(Exception ex) {
        	System.out.println(ex.getMessage());
	    	}
	    }
        	
	    private void listUser(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        System.out.println("listUser started: 00000000000000000000000000000000000");

	     
	        List<user> listUser = userDAO.listAllUsers();
	        request.setAttribute("listUser", listUser);       
	        RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");       
	        dispatcher.forward(request, response);
	     
	        System.out.println("listPeople finished: 111111111111111111111111111111111111");
	    }
	    	        
	    private void rootPage(HttpServletRequest request, HttpServletResponse response, String copiedcat, String c1, String c2) throws ServletException, IOException, SQLException{
	    	System.out.println("root view");
			request.setAttribute("listUser", userDAO.listAllUsers());
			request.setAttribute("bigSponsors", userDAO.bigSponsors());
			request.setAttribute("commonContests", userDAO.commonContests(c1,c2));
			request.setAttribute("topJudges", userDAO.topJudges());
			request.setAttribute("bestContestants", userDAO.bestContestants());
			request.setAttribute("sleepyContestants", userDAO.sleepyContestants());
			request.setAttribute("busyJudges", userDAO.busyJudges());
			request.setAttribute("toughContests", userDAO.toughContests());
			request.setAttribute("contestants", userDAO.listActiveContestants());
			request.setAttribute("copyCats", userDAO.copyCats(copiedcat));
			request.setAttribute("statistics", userDAO.statistics());
			request.setAttribute("copiedcat", copiedcat);
			request.setAttribute("c1", c1);
			request.setAttribute("c2", c2);

	    	request.getRequestDispatcher("rootView.jsp").forward(request, response);
	    }
	    
	    private void contestantPage_search(HttpServletRequest request, HttpServletResponse response, String pattern) throws ServletException, IOException, SQLException{
	    	System.out.println("contestant view");
	    	
			request.setAttribute("listContest", userDAO.listContests(pattern));
			request.setAttribute("reward", userDAO.getUserReward(currentUser, "contestant"));
	    	request.getRequestDispatcher("contestantView.jsp").forward(request, response);
	    }
	    
	    private void contestantPage_submit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	System.out.println("contestant view");
	    	
	    	String contestWallet = request.getParameter("contestWallet");
	    	String submission = request.getParameter("submission");
	    	String contestantWallet = currentUser;
	    	
	    	Boolean b = contestWallet != null &
	    		contestantWallet != null &
	    		submission != null;
	    	
	    	if (b) {
	    		System.out.println(contestWallet);
		    	userDAO.insertSubmission(contestantWallet, contestWallet, submission);
	    	}
	    	
			request.setAttribute("listContest", userDAO.listContests(""));
			request.setAttribute("reward", userDAO.getUserReward(currentUser, "contestant"));
	    	request.getRequestDispatcher("contestantView.jsp").forward(request, response);
	    }
	    
//	    private void sponsorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
//	    	System.out.println("sponsor view");
//			request.setAttribute("listJudge", userDAO.listAllJudges());
//	    	request.getRequestDispatcher("sponsorCreateView.jsp").forward(request, response);
//	    }
	    
//	    private void sponsorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
//	    	System.out.println("sponsor view");
//			//request.setAttribute("listClosedContest", userDAO.listClosedContests(currentUser));
//	    	request.getRequestDispatcher("sponsorView.jsp").forward(request, response);
//	    }
	    
	    private void sponsorCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	System.out.println("sponsor create");
	    	
	    	String contestWallet = request.getParameter("walletAddress");
	    	String contestTitle = request.getParameter("title");
	    	String contestStart = request.getParameter("startDate");
	    	String contestEnd = request.getParameter("endDate");
	    	String sponsorFee = request.getParameter("sponsorFee");
	    	String contestReq = request.getParameter("requirements");
	    	String[] judgeList = request.getParameterValues("judges");
	    	
//	    	System.out.println("sponsor params passed...");
//	    	System.out.println(judgeList);
	    	
	    	Boolean b = contestWallet != null &
	    		contestTitle != null &
	    		contestStart != null &
	    		contestEnd != null &
	    		sponsorFee != null &
	    		contestReq != null &
	    		judgeList != null;
	    	
	    	if (b) {
		    	contest newContest = new contest(contestWallet,contestTitle);
		    	newContest.setStartDate(contestStart);
		    	newContest.setEndDate(contestEnd);
		    	newContest.setFee(Double.valueOf(sponsorFee.replace("$","")));
		    	newContest.setStatus("opened");
		    	newContest.setRequirements(contestReq);
		    	
		    	userDAO.insertContest(newContest, currentUser, judgeList);
	    	}
	    	
			request.setAttribute("listJudge", userDAO.listAllJudges());
	    	request.getRequestDispatcher("sponsorCreateView.jsp").forward(request, response);
	    }
	    
	    private void sponsorDistribute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	System.out.println("sponsor distribute");
	    	
			request.setAttribute("listClosedContest", userDAO.listClosedContests(currentUser));
	    	request.getRequestDispatcher("sponsorDistributeView.jsp").forward(request, response);
	    }
	    
	    private void sponsorDoDistribute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	System.out.println("sponsor do distribute");
	    	
	    	userDAO.distributeFunds(userDAO.listClosedContests(currentUser));
	    	
			request.setAttribute("listClosedContest", userDAO.listClosedContests(currentUser));
	    	request.getRequestDispatcher("sponsorDistributeView.jsp").forward(request, response);
	    }
	    
//	    private void sponsorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
//	    	//System.out.println("sponsor view");
//			//request.setAttribute("listClosedContest", userDAO.listClosedContests(currentUser));
//	    	System.out.println("sponsor view");
//			request.setAttribute("listJudge", userDAO.listAllJudges());
//	    	request.getRequestDispatcher("sponsorView1.jsp").forward(request, response);
//	    }
	    
	    private void judgePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	System.out.println("judge view");
	    	String contestantWallet = request.getParameter("contestantWallet");
 	    	String contestWallet = request.getParameter("contestWallet");
 	    	String judgeWallet = currentUser;
 	    	String grade = request.getParameter("grade");

 	    	Boolean b = contestWallet != null &
 		    		contestantWallet != null &
 		    		judgeWallet != null & 
 		    		grade != null;

 		    	if (b) {
 		    		System.out.println(judgeWallet);
 			    	userDAO.insertSubmissionGrade(contestantWallet, contestWallet, judgeWallet,grade);
 		    	}
			request.setAttribute("listSubmission", userDAO.listSubmissions(currentUser));
			request.setAttribute("reward", userDAO.getUserReward(currentUser, "judge"));
	    	request.getRequestDispatcher("judgeView.jsp").forward(request, response);
	    }
	    
	    
	    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
   	 		
	    	 String walletAddress = request.getParameter("walletAddress");
	    	 String pass = request.getParameter("pass");
	    	 String targPage = "";
	    	
	    	 
	    	 if (userDAO.isValid(walletAddress, pass)) {
		 		currentUser = walletAddress;
		 		System.out.println("Login Successful! Redirecting");
				session = request.getSession();
				session.setAttribute("username", walletAddress);
				
				String userRole = userDAO.getUser(walletAddress).getRole();
				switch(userRole) {
					case "root":
						rootPage(request, response,"","","");
						break;
					case "sponsor":
						sponsorCreate(request, response);
						break;
					case "contestant":
						contestantPage_search(request, response, "");
						break;
					case "judge":
						judgePage(request, response);
						break;
					default:
						System.out.println("Login unsuccessful! Role is invalid...");
				} 
			} else {
   	    		request.setAttribute("loginStr","Login Failed: Please check your credentials.");
	    		request.getRequestDispatcher("login.jsp").forward(request, response);
			}
	    }
	           
	    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String walletAddress = request.getParameter("walletAddress");
	    	String pass = request.getParameter("password");
	    	String userRole = request.getParameter("userRole");
	    	String confirm = request.getParameter("confirmation");
	   	 	
	   	 	if (pass.equals(confirm)) {
	   	 		if (!userDAO.checkWallet(walletAddress)) {
		   	 		System.out.println("Registration Successful! Added to database");
		   	 		user users = new user(walletAddress,pass,userRole);
		   	 		userDAO.insert(users);
		   	 		response.sendRedirect("login.jsp");
	   	 		}
		   	 	else {
		   	 		System.out.println("Username taken, please enter new username");
		    		 request.setAttribute("errorOne","Registration failed: Username taken, please enter a new username.");
		    		 request.getRequestDispatcher("register.jsp").forward(request, response);
		   	 	}
	   	 	}
	   	 	else {
	   	 		System.out.println("Password and Password Confirmation do not match");
	   		 request.setAttribute("errorTwo","Registration failed: Password and Password Confirmation do not match.");
	   		 request.getRequestDispatcher("register.jsp").forward(request, response);
	   	 	}
	    }    
	    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	currentUser = "";
        		response.sendRedirect("login.jsp");
        	}
	
	    

	     
        
	    
	    
	    
	    
	    
}
	        
	        
	    
	        
	        
	        
	    


