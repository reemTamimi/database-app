import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/userDAO")
public class userDAO 
{
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public userDAO(){}
	
	/** 
	 * @see HttpServlet#HttpServlet()
     */
    protected void connect_func() throws SQLException {
    	//uses default connection to the database
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/projdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=pass1234");
            System.out.println(connect);
        }
    }
    
    public boolean database_login(String walletAddress, String pass) throws SQLException{
    	try {
    		connect_func("root","pass1234");
    		String sql = "select * from users where walletAddress = ?";
    		preparedStatement = connect.prepareStatement(sql);
    		preparedStatement.setString(1, walletAddress);
    		ResultSet rs = preparedStatement.executeQuery();
    		return rs.next();
    	}
    	catch(SQLException e) {
    		System.out.println("failed login");
    		return false;
    	}
    }
	//connect to the database 
    public void connect_func(String username, String password) throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/projdb?"
  			          + "useSSL=false&user=" + username + "&password=" + password);
            System.out.println(connect);
        }
    }
    
    public List<user> listAllUsers() throws SQLException {
        List<user> listUser = new ArrayList<user>();        
        String sql = "SELECT * FROM users";      
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String walletAddress = resultSet.getString("walletAddress");
            String pass = resultSet.getString("pass");
            String userRole = resultSet.getString("userRole");
            
            user users = new user(walletAddress,pass,userRole);
            listUser.add(users);
        }        
        resultSet.close();
        disconnect();        
        return listUser;
    }
    
    public List<user> bigSponsors() throws SQLException {
        List<user> bigSponsors = new ArrayList<user>();        
        String sql = "select a.sponsorWallet from "
        		+ "(select sponsorWallet,count(contestWallet) as countContest from contestSponsor group by sponsorWallet order by countContest) as a "
        		+ "inner join "
        		+ "(select distinct sponsorWallet,count(contestWallet) as countContest from contestSponsor group by sponsorWallet order by countContest desc limit 1) as b "
        		+ "on a.countContest = b.countContest;";      
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String sponsorWallet = resultSet.getString("sponsorWallet");
            
            user users = new user(sponsorWallet);
            bigSponsors.add(users);
        }        
        resultSet.close();
        disconnect();        
        return bigSponsors;
    }
    
    public List<user> topJudges() throws SQLException {
        List<user> topJudges = new ArrayList<user>();        
        String sql = "select a.judgeWallet from "
        		+ "(select judgeWallet,avg(score) as ascore from review group by judgeWallet order by ascore) as a  "
        		+ "inner join "
        		+ "(select distinct judgeWallet, avg(score) as ascore from review group by judgeWallet order by ascore desc limit 1) as b  "
        		+ "on a.ascore = b.ascore;";    
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String judgeWallet = resultSet.getString("judgeWallet");
            
            user users = new user(judgeWallet);
            topJudges.add(users);
        }        
        resultSet.close();
        disconnect();        
        return topJudges;
    }
    
    public ArrayList<String> commonContests(String c1, String c2) throws SQLException {
        ArrayList<String> common = new ArrayList<String>();        
        String sql = "select contestWallet from submission\n"
        		+ "where contestantWallet in ('" + c1 + "','" + c2 + "')\n"
        		+ "group by contestWallet having count(contestantWallet)=2;";    
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String res = resultSet.getString("contestWallet");
            
            common.add(res);
        }        
        resultSet.close();
        disconnect();        
        return common;
    }
    
    public List<user> bestContestants() throws SQLException {
        List<user> bestContestants = new ArrayList<user>();        
        String sql = "select walletAddress from contestant a "
        		+ "join (select distinct rewardBalance from contestant order by rewardBalance desc limit 1) b "
        		+ "on a.rewardBalance = b.rewardBalance;";    
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String walletAddress = resultSet.getString("walletAddress");
            
            user users = new user(walletAddress);
            bestContestants.add(users);
        }        
        resultSet.close();
        disconnect();        
        return bestContestants;
    }
    
    public List<user> sleepyContestants() throws SQLException {
        List<user> sleepyContestants = new ArrayList<user>();        
        String sql = "select u.walletAddress from users u where userRole = 'contestant' and u.walletAddress not in"
        		+ "(select s.contestantWallet from submission s);";    
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String walletAddress = resultSet.getString("walletAddress");
            
            user users = new user(walletAddress);
            sleepyContestants.add(users);
        }        
        resultSet.close();
        disconnect();        
        return sleepyContestants;
    }
    
    public List<user> busyJudges() throws SQLException {
        List<user> busyJudges = new ArrayList<user>();        
        String sql1 = "create or replace view pastcontests as "
        		+ "select * from contestJudge where contestWallet in "
        		+ "(select walletAddress from contest where contestStatus = 'past');"; 
        String sql2 = "select judgeWallet from "
        		+ "(select judgeWallet, count(contestWallet) as cnt from pastcontests group by judgeWallet) t1, "
        		+ "(select count(distinct(contestWallet)) as cnt from pastcontests) t2 "
        		+ "where t1.cnt = t2.cnt;"; 

        connect_func();      
        statement = (Statement) connect.createStatement();
        statement.executeUpdate(sql1);
        ResultSet resultSet = statement.executeQuery(sql2);

        while (resultSet.next()) {
            String walletAddress = resultSet.getString("judgeWallet");

            user users = new user(walletAddress);
            busyJudges.add(users);
        }        
        resultSet.close();
        disconnect();        
        return busyJudges;
    }
    
    public List<user> toughContests() throws SQLException {
        List<user> toughContests = new ArrayList<user>();        
        String sql = "select contestWallet from \n"
        		+ "(\n"
        		+ "	select * from submission where contestWallet in\n"
        		+ "	(select walletAddress from contest where contestStatus = 'past')\n"
        		+ ") as t1 group by contestWallet having count(contestantWallet)<10\n"
        		+ "union\n"
        		+ "(\n"
        		+ "	select walletAddress from contest\n"
        		+ "    where contestStatus = 'past'\n"
        		+ "    and contestStatus not in\n"
        		+ "		(select contestWallet from submission)\n"
        		+ ");";    
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String walletAddress = resultSet.getString("contestWallet");
            
            user users = new user(walletAddress);
            toughContests.add(users);
        }        
        resultSet.close();
        disconnect();        
        return toughContests;
    }
    
    public List<user> copyCats(String contestantWallet) throws SQLException {
    	List<user> copyCats = new ArrayList<user>();   
        String sql1 = "create or replace view sublist as\n"
        		+ "select contestantWallet from submission where contestWallet in\n"
        		+ "(select contestWallet from submission\n"
        		+ "where contestantWallet = '" + contestantWallet + "');";
        		
        String sql2 = "create or replace view subcounts as\n"
        		+ "select contestantWallet, count(contestWallet) as cnt from submission\n"
        		+ "where contestantWallet in \n"
        		+ "(select contestantWallet from sublist)\n"
        		+ "group by contestantWallet;";
        		
        String sql3 = "select t1.contestantWallet from\n"
        		+ "(select * from subcounts) t1, \n"
        		+ "(select * from subcounts where contestantWallet = '" + contestantWallet + "') t2\n"
        		+ "where t1.cnt = t2.cnt;";    
        
        connect_func();      
        statement = (Statement) connect.createStatement();
        statement.executeUpdate(sql1);
        statement.executeUpdate(sql2);
        ResultSet resultSet = statement.executeQuery(sql3);
         
        while (resultSet.next()) {
            String walletAddress = resultSet.getString("contestantWallet");
            
            user users = new user(walletAddress);
            copyCats.add(users);
        }        
        resultSet.close();
        disconnect();     
    	
    	return copyCats;
    }
    
    public double getUserReward(String wallet, String userType) throws SQLException {
    	  
    	String sql = "select rewardBalance from " + userType + " where walletAddress = '" + wallet + "';";

        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        
        double balance = 0;
        while (resultSet.next()) {
        	balance = resultSet.getDouble("rewardBalance");
        	break;
        }
        
        resultSet.close();
        disconnect();
        return(balance);
    }
    
    public List<user> listActiveContestants() throws SQLException {
        List<user> listContestants = new ArrayList<user>();        
        String sql = "SELECT distinct contestantWallet FROM submission";      
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String walletAddress = resultSet.getString("contestantWallet");
            
            user contestant = new user(walletAddress);
            listContestants.add(contestant);
        }        
        resultSet.close();
        disconnect();        
        return listContestants;
    }
    
    public List<judge> listAllJudges() throws SQLException {
        List<judge> listJudge = new ArrayList<judge>();        
        String sql = "SELECT * FROM judge";      
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String walletAddress = resultSet.getString("walletAddress");
            double balance = resultSet.getDouble("rewardBalance");
            
            judge judges = new judge(walletAddress,balance);
            listJudge.add(judges);
        }        
        resultSet.close();
        disconnect();        
        return listJudge;
    }
    
    public List<submission> listSubmissions(String activeJudge) throws SQLException {
        List<submission> listSubmission = new ArrayList<submission>();        
        //String sql = "SELECT * FROM submission WHERE contestWallet IN (SELECT contestWallet FROM contestJudge where judgeWallet like '" + activeJudge + "')";
        String sql = "SELECT s.contestantWallet, s.contestWallet, s.submissionFile, c.title, c.requirements FROM submission s INNER JOIN contest c ON s.contestWallet =  c.walletAddress WHERE contestWallet IN (SELECT contestWallet FROM contestJudge where judgeWallet like '" + activeJudge + "')";
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String contestantWallet = resultSet.getString("contestantWallet");
            String contestWallet = resultSet.getString("contestWallet");
            String submissionFile = resultSet.getString("submissionFile");
            
            String title = resultSet.getString("title");
            String requirements = resultSet.getString("requirements");
            
            submission submissions = new submission(contestantWallet,contestWallet,submissionFile,title,requirements);
            listSubmission.add(submissions);
        }        
        resultSet.close();
        disconnect();        
        return listSubmission;
    }
    
    public List<contest> listContests(String pattern) throws SQLException {
        List<contest> listContest = new ArrayList<contest>();        
        String sql = "SELECT * FROM contest WHERE contestStatus LIKE 'opened'";    
        if (pattern != "") {
        	sql = sql + " AND title LIKE '%" + pattern + "%'";
        }
        connect_func();
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String walletAddress = resultSet.getString("walletAddress");
            String title = resultSet.getString("title");
   		 	String startDate = resultSet.getString("startDate");
   		 	String endDate = resultSet.getString("endDate");
   		 	String contestStatus = resultSet.getString("contestStatus");
   		 	double sponsorFee = resultSet.getDouble("sponsorFee");
   		 	String requirements = resultSet.getString("requirements");
            
            contest contests = new contest(walletAddress,title);
            contests.setStartDate(startDate);
            contests.setEndDate(endDate);
            contests.setStatus(contestStatus);
            contests.setFee(sponsorFee);
            contests.setRequirements(requirements);
            
            listContest.add(contests);
        }        
        resultSet.close();
        disconnect();        
        return listContest;
    }
    
    public void distributeFunds(List<contest> closedContests) throws SQLException {
    	
    	connect_func();
        statement = (Statement) connect.createStatement();
    	
    	for (int c=0; c<closedContests.size(); c++) {
	    	String sql1 = "create or replace view thiscontest as\n"
	    			+ "	select walletAddress, sponsorFee from contest\n"
	    			+ "	where walletAddress = '" + closedContests.get(c).getWallet() + "';";
	    	String sql2 = "create or replace view thesejudges as\n"
	    			+ "	select judgeWallet from contestJudge\n"
	    			+ "    where contestWallet in (select walletAddress from thiscontest);";
	    	String sql3 = "update judge, thiscontest, thesejudges\n"
	    			+ "set rewardBalance = rewardBalance + ((thiscontest.sponsorFee * 0.2) / (select count(*) from thesejudges))\n"
	    			+ "where judge.walletAddress in (select judgeWallet from thesejudges);";
	    	String sql4 = "create or replace view thesecontestants as\n"
	    			+ "	select contestantWallet, grade from submissionGrade\n"
	    			+ "    where contestWallet in (select walletAddress from thiscontest);";
	    	String sql5 = "update contestant, thiscontest, thesecontestants\n"
	    			+ "set rewardBalance = rewardBalance +\n"
	    			+ "	((thiscontest.sponsorFee * 0.8 * (select grade from thesecontestants where contestantWallet = contestant.walletAddress)) / \n"
	    			+ "    (select count(*) from thesecontestants))\n"
	    			+ "where contestant.walletAddress in (select contestantWallet from thesecontestants);";
	    	String sql6 = "update contest\n"
	    			+ "set contestStatus = 'past'\n"
	    			+ "where walletAddress = '" + closedContests.get(c).getWallet() + "';";
	    	
	    	statement.executeUpdate(sql1);
	    	statement.executeUpdate(sql2);
	    	statement.executeUpdate(sql3);
	    	statement.executeUpdate(sql4);
	    	statement.executeUpdate(sql5);
	    	statement.executeUpdate(sql6);
    	}

        disconnect();
    }
    
    public List<contest> listClosedContests(String activeSponsor) throws SQLException {
        List<contest> listClosedContest = new ArrayList<contest>();        
        String sql = "SELECT * FROM contest WHERE walletAddress IN (SELECT contestWallet FROM contestSponsor where sponsorWallet like '" + activeSponsor + "') AND contestStatus LIKE 'closed'";
        connect_func();
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String walletAddress = resultSet.getString("walletAddress");
            String title = resultSet.getString("title");
   		 	String startDate = resultSet.getString("startDate");
   		 	String endDate = resultSet.getString("endDate");
   		 	String contestStatus = resultSet.getString("contestStatus");
   		 	double sponsorFee = resultSet.getDouble("sponsorFee");
   		 	String requirements = resultSet.getString("requirements");
            
            contest contests = new contest(walletAddress,title);
            contests.setStartDate(startDate);
            contests.setEndDate(endDate);
            contests.setStatus(contestStatus);
            contests.setFee(sponsorFee);
            contests.setRequirements(requirements);
            
            listClosedContest.add(contests);
        }        
        resultSet.close();
        disconnect();        
        return listClosedContest;
    }
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
    public void insert(user users) throws SQLException {
    	connect_func("root","pass1234");         
		String sql = "insert into users(walletAddress,pass,userRole) values (?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, users.getWallet());
			preparedStatement.setString(2, users.getPass());
			preparedStatement.setString(3, users.getRole());	

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public void insertContest(contest newContest, String sponsorWallet, String[] judgeList) throws SQLException {
    	
    	// insert contest
    	connect_func(); 
		String sql = "insert into contest(walletAddress,title,startDate,endDate,contestStatus," + 
				"sponsorFee,requirements) values (?, ?, ?, ?, ?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, newContest.getWallet());
			preparedStatement.setString(2, newContest.getTitle());
			preparedStatement.setString(3, newContest.getStartDate());	
			preparedStatement.setString(4, newContest.getEndDate());	
			preparedStatement.setString(5, newContest.getStatus());	
			preparedStatement.setDouble(6, newContest.getFee());
			preparedStatement.setString(7, newContest.getRequirements());	

		preparedStatement.executeUpdate();
        preparedStatement.close();
        
        //insert contest sponsor
        connect_func(); 
		String sql2 = "insert into contestSponsor(contestWallet,sponsorWallet) values (?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql2);
			preparedStatement.setString(1, newContest.getWallet());
			preparedStatement.setString(2, sponsorWallet);	

		preparedStatement.executeUpdate();
        preparedStatement.close();
        
        // insert contest judge
        connect_func(); 
		String sql3 = "insert into contestJudge(contestWallet,judgeWallet) values ";
		for (int j = 0; j<judgeList.length; j++) {
			sql3 = sql3.format("%s ('%s','%s')", sql3, newContest.getWallet(), judgeList[j]);
			if (j < judgeList.length - 1) {
				sql3 = sql3 + ", ";
			}
		}
		
		System.out.println(sql3);
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql3);

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
      public void insertSubmission(String contestantWallet, String contestWallet, String submission) throws SQLException {
    	connect_func(); 
		String sql = "insert into submission(contestantWallet,contestWallet,submissionFile) values (?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, contestantWallet);
		preparedStatement.setString(2, contestWallet);
		preparedStatement.setString(3, submission);

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public void insertSubmissionGrade(grade newSubmissionGrade) throws SQLException {
    	connect_func(); 
		String sql = "insert into submissionGrade(contestantWallet,contestWallet,judgeWallet,grade) values (?, ?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, newSubmissionGrade.getContestant());
			preparedStatement.setString(2, newSubmissionGrade.getContest());
			preparedStatement.setString(3, newSubmissionGrade.getJudge());
			preparedStatement.setString(4, newSubmissionGrade.getGrade());

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public boolean delete(String walletAddress) throws SQLException {
        String sql = "DELETE FROM users WHERE walletAddress = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, walletAddress);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowDeleted;     
    }
     
    public boolean update(user users) throws SQLException {
        String sql = "update users set walletAddress=?, pass=?, userRole=?";
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, users.getWallet());
		preparedStatement.setString(2, users.getPass());
		preparedStatement.setString(3, users.getRole());
         
        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowUpdated;     
    }
    
    public user getUser(String walletAddress) throws SQLException {
    	
    	user user = null;
        String sql = "SELECT * FROM users WHERE walletAddress = ?";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, walletAddress);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
            // String walletAddress = resultSet.getString("walletAddress");
            String pass = resultSet.getString("pass");
            String userRole = resultSet.getString("userRole");
            user = new user(walletAddress, pass, userRole);
        }

         
        resultSet.close();
        preparedStatement.close();
         
        return user;
    }
    
    public boolean checkWallet(String walletAddress) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM users WHERE walletAddress = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, walletAddress);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = true;
        }
        
        System.out.println(checks);
    	return checks;
    }
    
    public boolean checkPassword(String pass) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM users WHERE pass = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, pass);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = true;
        }
        
        System.out.println(checks);
       	return checks;
    }
    
    
    
    public boolean isValid(String walletAddress, String pass) throws SQLException
    {
    	String sql = "SELECT * FROM users";
    	connect_func();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	
    	resultSet.last();
    	
    	int setSize = resultSet.getRow();
    	resultSet.beforeFirst();
    	
    	for(int i = 0; i < setSize; i++)
    	{
    		resultSet.next();
    		if(resultSet.getString("walletAddress").equals(walletAddress) && resultSet.getString("pass").equals(pass)) {
    			return true;
    		}		
    	}
    	return false;
    }
    
    
    public void init() throws SQLException, FileNotFoundException, IOException{
    	connect_func();
        statement =  (Statement) connect.createStatement();
        
        //############
        //# ENTITIES #
        //############
        String[] INITDB = {"drop database if exists projdb;",
        					"create database projdb;",
        					"use projdb;"
        					};

        String[] CONTESTANT = {"drop table if exists contestant;",
        						("create table contestant (" +
        							"walletAddress varchar(42) not null," +
        							"rewardBalance double," +
        							"check (regexp_like (walletAddress, '^(0x[A-F[:digit:]]{40}|root)$'))," +
        							"check (rewardBalance >= 0)," +
        							"unique(walletAddress)," +
        							"primary key (walletAddress));")
        						};

        String[] CONTEST = {"drop table if exists contest;",
        					("create table contest (" +
        						"walletAddress varchar(42) not null," +
        						"title varchar(100) not null," +
        						"startDate date not null," +
        						"endDate date not null," +
        						"contestStatus varchar(20) not null," +
        						"sponsorFee double not null," +
        						"requirements varchar(100) not null," +
        						"check (regexp_like (walletAddress, '^(0x[A-F[:digit:]]{40}|root)$'))," +
        						"check (contestStatus in ('created','opened','closed','past'))," +
        						"check (sponsorFee >= 0)," +
        						"check (datediff(endDate,startDate) >= 1)," +
        						"unique (walletAddress)," +
        						"primary key (walletAddress));")
        					};

        String[] SPONSOR = {"drop table if exists sponsor;",
        					("create table sponsor (" +
        						"walletAddress varchar(42) not null," +
        						"address varchar(200) not null," +
        						"companyName varchar(100) not null," +
        						"email varchar(100) not null," +
        						"check (regexp_like (walletAddress, '^(0x[A-F[:digit:]]{40}|root)$'))," +
        						"check (regexp_like (email, '.+@.+'))," +
        						"primary key (walletAddress));")
        					};

        String[] JUDGE = {"drop table if exists judge;",
        					("create table judge (" +
        					"walletAddress varchar(42) not null," +
        					"rewardBalance double," +
        					"check (regexp_like (walletAddress, '^(0x[A-F[:digit:]]{40}|root)$'))," +
        					"check (rewardBalance >= 0)," +
        					"primary key (walletAddress));")
        					};

        String[] SUBMISSION = {"drop table if exists submission;",
        						("create table submission (" +
        							"contestantWallet varchar(42) not null," +
        							"contestWallet varchar(42) not null," +
        							"submissionFile varchar(100) not null," + 
        							"primary key (contestantWallet, contestWallet)," +
        							"foreign key (contestantWallet) references contestant (walletAddress)," +
        							"foreign key (contestWallet) references contest (walletAddress));")
        						};
        
        String[] USERS = {"drop table if exists users;",
        					("create table users (" +
			        		"walletAddress varchar(42) not null," +
			        		"pass varchar(20) not null," +
			        		"userRole varchar(20) not null," +
			        		"check (userRole in ('root','sponsor','judge','contestant'))," +
			        		"primary key (walletAddress));")
        				};


        //#################
        //# RELATIONSHIPS #
        //#################
        String[] REVIEW = {"drop table if exists review;",
        					("create table review (" +
        						"judgeWallet varchar(42) not null," +
        						"sponsorWallet varchar(42) not null," +
        						"score int not null," +
        						"reviewComment varchar(1000)," +
        						"check (score >= 0 and score <= 10)," +
        						"unique (sponsorWallet)," +
        						"foreign key (judgeWallet) references judge (walletAddress)," +
        						"foreign key (sponsorWallet) references sponsor (walletAddress)," +
        						"primary key (sponsorWallet));")
        					};

        String[] SUBMISSIONGRADE = {"drop table if exists submissionGrade;",
        							("create table submissionGrade (" +
        								"contestantWallet varchar(42) not null," +
        								"contestWallet varchar(42) not null," +
        								"judgeWallet varchar(42) not null," +
        								"grade int not null," +
        								"check (grade >= 0 and grade <= 100)," +
        								"primary key (contestantWallet, contestWallet, judgeWallet)," +
        								"foreign key (contestantWallet) references contestant (walletAddress)," +
        								"foreign key (contestWallet) references contest (walletAddress)," +
        								"foreign key (judgeWallet) references judge (walletAddress));")
        							};

        String[] CONTESTJUDGE = {"drop table if exists contestJudge;",
        						("create table contestJudge (" +
        							"contestWallet varchar(42) not null," +
        							"judgeWallet varchar(42) not null," +
        							"foreign key (contestWallet) references contest (walletAddress)," +
        							"foreign key (judgeWallet) references judge (walletAddress)," +
        							"unique (contestWallet, judgeWallet)," +
        							"primary key (contestWallet, judgeWallet));")
        						};

        String[] CONTESTSPONSOR = {"drop table if exists contestSponsor;",
        							("create table contestSponsor (" +
        								"contestWallet varchar(42) not null," +
        								"sponsorWallet varchar(42) not null," +
        								"unique (contestWallet)," +
        								"foreign key (contestWallet) references contest (walletAddress)," +
        								"foreign key (sponsorWallet) references sponsor (walletAddress)," +
        								"primary key (contestWallet));")
        							};

        //#######################
        //# TRIGGER CONSTRAINTS #
        //#######################
        String[] NUMJUDGES = {"drop trigger if exists numJudges;",
        						("delimiter$$" +
        						"create trigger numJudges before insert on contestJudge" +
        						"for each row" +
        						"begin" +
        						"if (select count(judgeWallet) from contestJudge where contestWallet = new.contestWallet) >= 10" +
        						"then set new.contestWallet = null;" +
        						"end if;" +
        						"end$$" +
        						"delimiter;")
        					};
        		
        //##############
        //# FILL TABLE #
        //##############
        String[] IN_CONTESTANT = {
        		("insert into contestant(walletAddress, rewardBalance)" +
        			"values ('0x000000000000000000000000000000000000001A',0)," +
        			"('0x000000000000000000000000000000000000002A',0)," +
        			"('0x000000000000000000000000000000000000003A',0)," +
        			"('0x000000000000000000000000000000000000004A',0)," +
        			"('0x000000000000000000000000000000000000005A',0)," +
        			"('0x000000000000000000000000000000000000006A',0)," +
        			"('0x000000000000000000000000000000000000007A',0)," +
        			"('0x000000000000000000000000000000000000008A',0)," +
        			"('0x000000000000000000000000000000000000009A',0)," +
        			"('0x00000000000000000000000000000000000000AA',0);")
        		};
        			
        	String[] IN_CONTEST = {("insert into contest(walletAddress, title, startDate, endDate, contestStatus, sponsorFee, requirements)" +
        		"values ('0x000000000000000000000000000000000000001B', 'Contest1', '2023-03-19', '2023-04-28', 'created', 1000000, 'Req1')," +
        			"('0x000000000000000000000000000000000000002B', 'Contest2', '2023-02-19', '2023-03-28', 'opened', 1000000, 'Req2')," +
        			"('0x000000000000000000000000000000000000003B', 'Contest3', '2023-02-19', '2023-03-28', 'opened', 1000000, 'Req3')," +
        			"('0x000000000000000000000000000000000000004B', 'Contest4', '2023-01-19', '2023-02-19', 'closed', 1000000, 'Req4')," +
        			"('0x000000000000000000000000000000000000005B', 'Contest5', '2023-01-19', '2023-02-19', 'closed', 1000000, 'Req5')," +
        			"('0x000000000000000000000000000000000000006B', 'Contest6', '2023-01-19', '2023-02-19', 'closed', 1000000, 'Req6')," +
        			"('0x000000000000000000000000000000000000007B', 'Contest7', '2023-01-19', '2023-02-19', 'closed', 1000000, 'Req7')," +
        			"('0x000000000000000000000000000000000000008B', 'Contest8', '2022-02-19', '2022-03-28', 'past', 0, 'Req8')," +
        			"('0x000000000000000000000000000000000000009B', 'Contest9', '2022-02-19', '2022-03-28', 'past', 0, 'Req9')," +
        			"('0x00000000000000000000000000000000000000AB', 'Contest10', '2022-02-19', '2022-03-28', 'past', 0, 'Req10');")
        		};
        			
        	String[] IN_SPONSOR = {("insert into sponsor(walletAddress, address, companyName, email)" +
        		"values ('0x000000000000000000000000000000000000001C', '1 Main St.', 'Main St. Co.', 'mainco@mainco.com')," +
        			"('0x000000000000000000000000000000000000002C', '1 Dog St.', 'Dog St. Co.', 'dogco@dogco.com')," +
        			"('0x000000000000000000000000000000000000003C', '1 Cat St.', 'Cat St. Co.', 'catco@catco.com')," +
        			"('0x000000000000000000000000000000000000004C', '1 Rat St.', 'Rat St. Co.', 'ratco@ratco.com')," +
        			"('0x000000000000000000000000000000000000005C', '1 Bird St.', 'Bird St. Co.', 'birdco@birdco.com')," +
        			"('0x000000000000000000000000000000000000006C', '1 Fish St.', 'Fish St. Co.', 'fishco@fishco.com')," +
        			"('0x000000000000000000000000000000000000007C', '1 Bug St.', 'Bug St. Co.', 'bugco@bugco.com')," +
        			"('0x000000000000000000000000000000000000008C', '1 Dolphin St.', 'Dolphin St. Co.', 'dolphinco@dolphinco.com')," +
        			"('0x000000000000000000000000000000000000009C', '1 Whale St.', 'Whale St. Co.', 'whaleco@whaleco.com')," +
        			"('0x00000000000000000000000000000000000000AC', '1 Gemsbok St.', 'Gemsbok St. Co.', 'gemsbokco@gemsbokco.com');")
        		};
        			
        	String[] IN_JUDGE = {("insert into judge(walletAddress, rewardBalance)" +
        		"values ('0x000000000000000000000000000000000000001D', 0)," +
        			"('0x000000000000000000000000000000000000002D', 0)," +
        			"('0x000000000000000000000000000000000000003D', 0)," +
        			"('0x000000000000000000000000000000000000004D', 0)," +
        			"('0x000000000000000000000000000000000000005D', 0)," +
        			"('0x000000000000000000000000000000000000006D', 0)," +
        			"('0x000000000000000000000000000000000000007D', 0)," +
        			"('0x000000000000000000000000000000000000008D', 0)," +
        			"('0x000000000000000000000000000000000000009D', 0)," +
        			"('0x00000000000000000000000000000000000000AD', 0);")
        		};

        	String[] IN_SUBMISSION = {("insert into submission(contestantWallet, contestWallet, submissionFile)" +
        		"values ('0x000000000000000000000000000000000000001A','0x000000000000000000000000000000000000002B','tst.txt')," +
        			"('0x000000000000000000000000000000000000002A','0x000000000000000000000000000000000000002B','tst.txt')," +
        			"('0x000000000000000000000000000000000000003A','0x000000000000000000000000000000000000002B','tst.txt')," +
        			"('0x000000000000000000000000000000000000004A','0x000000000000000000000000000000000000002B','tst.txt')," +
        			"('0x000000000000000000000000000000000000005A','0x000000000000000000000000000000000000002B','tst.txt')," +
        			"('0x000000000000000000000000000000000000006A','0x000000000000000000000000000000000000003B','tst.txt')," +
        			"('0x000000000000000000000000000000000000007A','0x000000000000000000000000000000000000003B','tst.txt')," +
        			"('0x000000000000000000000000000000000000008A','0x000000000000000000000000000000000000003B','tst.txt')," +
        			"('0x000000000000000000000000000000000000009A','0x000000000000000000000000000000000000003B','tst.txt')," +
        			"('0x00000000000000000000000000000000000000AA','0x000000000000000000000000000000000000003B','tst.txt')," +
        			"('0x000000000000000000000000000000000000001A','0x000000000000000000000000000000000000003B','tst.txt');")
        		};

        	String[] IN_USERS = {("insert into users (walletAddress,pass,userRole)" +
        		"values ('root','pass1234','root')," +
        			"('0x000000000000000000000000000000000000001A','password','contestant')," +
        			"('0x000000000000000000000000000000000000002A','password','contestant')," +
        			"('0x000000000000000000000000000000000000003A','password','contestant')," +
        			"('0x000000000000000000000000000000000000004A','password','contestant')," +
        			"('0x000000000000000000000000000000000000005A','password','contestant')," +
        			"('0x000000000000000000000000000000000000006A','password','contestant')," +
        			"('0x000000000000000000000000000000000000007A','password','contestant')," +
        			"('0x000000000000000000000000000000000000008A','password','contestant')," +
        			"('0x000000000000000000000000000000000000009A','password','contestant')," +
        			"('0x00000000000000000000000000000000000000AA','password','contestant')," +
        			"('0x000000000000000000000000000000000000001D','password','judge')," +
        			"('0x000000000000000000000000000000000000002D','password','judge')," +
        			"('0x000000000000000000000000000000000000003D','password','judge')," +
        			"('0x000000000000000000000000000000000000004D','password','judge')," +
        			"('0x000000000000000000000000000000000000005D','password','judge')," +
        			"('0x000000000000000000000000000000000000006D','password','judge')," +
        			"('0x000000000000000000000000000000000000007D','password','judge')," +
        			"('0x000000000000000000000000000000000000008D','password','judge')," +
        			"('0x000000000000000000000000000000000000009D','password','judge')," +
        			"('0x00000000000000000000000000000000000000AD','password','judge')," +
        			"('0x000000000000000000000000000000000000001C','password','sponsor')," +
        			"('0x000000000000000000000000000000000000002C','password','sponsor');")
        		};

        	String[] IN_REVIEW = {("insert into review(judgeWallet, sponsorWallet, score, reviewComment)" +
        		"values ('0x000000000000000000000000000000000000001D','0x000000000000000000000000000000000000001C', 7, 'Comment1')," +
        			"('0x000000000000000000000000000000000000001D','0x000000000000000000000000000000000000002C', 6, 'Comment2')," +
        			"('0x000000000000000000000000000000000000001D','0x000000000000000000000000000000000000003C', 8, 'Comment3')," +
        			"('0x000000000000000000000000000000000000002D','0x000000000000000000000000000000000000004C', 6, 'Comment4')," +
        			"('0x000000000000000000000000000000000000003D','0x000000000000000000000000000000000000005C', 6, 'Comment5')," +
        			"('0x000000000000000000000000000000000000004D','0x000000000000000000000000000000000000006C', 4, 'Comment6')," +
        			"('0x000000000000000000000000000000000000005D','0x000000000000000000000000000000000000007C', 4, 'Comment7')," +
        			"('0x000000000000000000000000000000000000006D','0x000000000000000000000000000000000000008C', 10, 'Comment8')," +
        			"('0x000000000000000000000000000000000000007D','0x000000000000000000000000000000000000009C', 9, 'Comment9')," +
        			"('0x000000000000000000000000000000000000008D','0x00000000000000000000000000000000000000AC', 9, 'Comment10');")
        		};
        			
        	String[] IN_SUBMISSIONGRADE = {("insert into submissionGrade(contestantWallet, contestWallet, judgeWallet, grade)" +
        		"values ('0x000000000000000000000000000000000000001A','0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000001D', 100)," +
        			"('0x000000000000000000000000000000000000001A','0x000000000000000000000000000000000000003B','0x000000000000000000000000000000000000006D', 100)," +
        			"('0x000000000000000000000000000000000000002A','0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000002D', 70)," +
        			"('0x000000000000000000000000000000000000003A','0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000003D', 100)," +
        			"('0x000000000000000000000000000000000000004A','0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000004D', 95)," +
        			"('0x000000000000000000000000000000000000005A','0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000005D', 100)," +
        			"('0x000000000000000000000000000000000000006A','0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000001D', 100)," +
        			"('0x000000000000000000000000000000000000007A','0x000000000000000000000000000000000000003B','0x000000000000000000000000000000000000007D', 97)," +
        			"('0x000000000000000000000000000000000000008A','0x000000000000000000000000000000000000003B','0x000000000000000000000000000000000000008D', 100)," +
        			"('0x000000000000000000000000000000000000009A','0x000000000000000000000000000000000000003B','0x000000000000000000000000000000000000009D', 87);")
        		};
        			
        	String[] IN_CONTESTJUDGE = {("insert into contestJudge(contestWallet, judgeWallet)" +
        		"values ('0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000001D')," +
        			"('0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000002D')," +
        			"('0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000003D')," +
        			"('0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000004D')," +
        			"('0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000005D')," +
        			"('0x000000000000000000000000000000000000003B','0x000000000000000000000000000000000000006D')," +
        			"('0x000000000000000000000000000000000000003B','0x000000000000000000000000000000000000007D')," +
        			"('0x000000000000000000000000000000000000003B','0x000000000000000000000000000000000000008D')," +
        			"('0x000000000000000000000000000000000000003B','0x000000000000000000000000000000000000009D')," +
        			"('0x000000000000000000000000000000000000003B','0x00000000000000000000000000000000000000AD');")
        		};
        			
        	String[] IN_CONTESTSPONSOR = {("insert into contestSponsor(contestWallet, sponsorWallet)" +
        		"values ('0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000001C')," +
        			"('0x000000000000000000000000000000000000003B','0x000000000000000000000000000000000000002C')," +
        			"('0x000000000000000000000000000000000000001B','0x000000000000000000000000000000000000003C')," +
        			"('0x000000000000000000000000000000000000004B','0x000000000000000000000000000000000000004C')," +
        			"('0x000000000000000000000000000000000000005B','0x000000000000000000000000000000000000005C')," +
        			"('0x000000000000000000000000000000000000006B','0x000000000000000000000000000000000000006C')," +
        			"('0x000000000000000000000000000000000000007B','0x000000000000000000000000000000000000007C')," +
        			"('0x000000000000000000000000000000000000008B','0x000000000000000000000000000000000000008C')," +
        			"('0x000000000000000000000000000000000000009B','0x000000000000000000000000000000000000009C')," +
        			"('0x00000000000000000000000000000000000000AB','0x00000000000000000000000000000000000000AC');")
        		};

        
        //for loop to put these in database
        String[][] INITIAL = {
			INITDB, CONTESTANT, CONTEST, SPONSOR,
			JUDGE, SUBMISSION, USERS, REVIEW, SUBMISSIONGRADE,
			CONTESTJUDGE, CONTESTSPONSOR
        };
        
        String[][] FILL = {
    			IN_CONTESTANT, IN_CONTEST, IN_SPONSOR,
    			IN_JUDGE, IN_SUBMISSION, IN_USERS, IN_REVIEW, IN_SUBMISSIONGRADE,
    			IN_CONTESTJUDGE, IN_CONTESTSPONSOR
        };
        
        for (int i = 0; i < INITIAL.length; i++)
        	for (int j = 0; j < INITIAL[i].length; j++)
        		statement.execute(INITIAL[i][j]);
        for (int i = 0; i < FILL.length; i++)
        	for (int j = 0; j < FILL[i].length; j++)
        		statement.execute(FILL[i][j]);
        disconnect();
    }
}
