
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
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=pass1234");
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
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/userdb?"
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
        statement.close();
         
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

        String[] CONTESTANT = {"droptable if exists contestant;",
        						("create table contestant (" +
        							"walletAddress varchar(42) not null," +
        							"rewardBalance double," +
        							"check (regexp_like (walletAddress, '^(0x[A-F[:digit:]]{40}|root)$'))," +
        							"check (rewardBalance >= 0)," +
        							"unique(walletAddress)," +
        							"primary key (walletAddress));")
        						};

        String[] CONTEST = {"droptable if exists contest;",
        					("create table contest (" +
        						"walletAddress varchar(42) not null," +
        						"title varchar(100) not null," +
        						"startDate date not null," +
        						"endDate date not null," +
        						"contestStatus varchar(20) not null," +
        						"sponsorFee double not null," +
        						"requirements varchar(1000) not null," +
        						"check (regexp_like (walletAddress, '^(0x[A-F[:digit:]]{40}|root)$'))," +
        						"check (contestStatus in ('created','opened','closed','past'))," +
        						"check (sponsorFee >= 0)," +
        						"check (datediff(endDate,startDate) >= 1)," +
        						"unique (walletAddress)," +
        						"primary key (walletAddress));")
        					};

        String[] SPONSOR = {"droptable if exists sponsor;",
        					("create table sponsor (" +
        						"walletAddress varchar(42) not null," +
        						"address varchar(200) not null," +
        						"companyName varchar(100) not null," +
        						"email varchar(100) not null," +
        						"check (regexp_like (walletAddress, '^(0x[A-F[:digit:]]{40}|root)$'))," +
        						"check (regexp_like (email, '.+@.+'))," +
        						"primary key (walletAddress));")
        					};

        String[] JUDGE = {"droptable if exists judge;",
        					("create table judge (" +
        					"walletAddress varchar(42) not null," +
        					"rewardBalance double," +
        					"check (regexp_like (walletAddress, '^(0x[A-F[:digit:]]{40}|root)$'))," +
        					"check (rewardBalance >= 0)," +
        					"primary key (walletAddress));")
        					};

        String[] SUBMISSION = {"droptable if exists submission;",
        						("create table submission (" +
        							"contestantWallet varchar(42) not null," +
        							"contestWallet varchar(42) not null," +
        							"primary key (contestantWallet, contestWallet)," +
        							"foreign key (contestantWallet) references contestant (walletAddress)," +
        							"foreign key (contestWallet) references contest (walletAddress));")
        						};
        
        String[] USERS = {"droptable if exists users;",
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
        String[] REVIEW = {"droptable if exists review;",
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

        String[] SUBMISSIONGRADE = {"droptable if exists submissionGrade;",
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

        String[] CONTESTJUDGE = {"droptable if exists contestJudge;",
        						("create table contestJudge (" +
        							"contestWallet varchar(42) not null," +
        							"judgeWallet varchar(42) not null," +
        							"foreign key (contestWallet) references contest (walletAddress)," +
        							"foreign key (judgeWallet) references judge (walletAddress)," +
        							"unique (contestWallet, judgeWallet)," +
        							"primary key (contestWallet, judgeWallet));")
        						};

        String[] CONTESTSPONSOR = {"droptable if exists contestSponsor;",
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
        String[] NUMJUDGES = {"droptrigger if exists numJudges;",
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
        String[] IN_CONTESTANT = {"use projdb;",
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
        		"values ('0x000000000000000000000000000000000000001B', 'Contest1', '03-19-2023', '04-28-2023', 'created', 10000, 'Req1')," +
        			"('0x000000000000000000000000000000000000002B', 'Contest2', '02-19-2023', '03-28-2023', 'opened', 10000, 'Req2')," +
        			"('0x000000000000000000000000000000000000003B', 'Contest3', '02-19-2023', '03-28-2023', 'opened', 10000, 'Req3')," +
        			"('0x000000000000000000000000000000000000004B', 'Contest4', '01-19-2023', '02-19-2023', 'closed', 10000, 'Req4')," +
        			"('0x000000000000000000000000000000000000005B', 'Contest5', '01-19-2023', '02-19-2023', 'closed', 10000, 'Req5')," +
        			"('0x000000000000000000000000000000000000006B', 'Contest6', '01-19-2023', '02-19-2023', 'closed', 10000, 'Req6')," +
        			"('0x000000000000000000000000000000000000007B', 'Contest7', '01-19-2023', '02-19-2023', 'closed', 10000, 'Req7')," +
        			"('0x000000000000000000000000000000000000008B', 'Contest8', '02-19-2022', '03-28-2022', 'past', 10000, 'Req8')," +
        			"('0x000000000000000000000000000000000000009B', 'Contest9', '02-19-2022', '03-28-2022', 'past', 10000, 'Req9')," +
        			"('0x00000000000000000000000000000000000000AB', 'Contest10', '02-19-2022', '03-28-2022', 'past', 10000, 'Req10');")
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

        	String[] IN_SUBMISSION = {("insert into submission(contestantWallet, contestWallet)" +
        		"values ('0x000000000000000000000000000000000000001A','0x000000000000000000000000000000000000002B')," +
        			"('0x000000000000000000000000000000000000002A','0x000000000000000000000000000000000000002B')," +
        			"('0x000000000000000000000000000000000000003A','0x000000000000000000000000000000000000002B')," +
        			"('0x000000000000000000000000000000000000004A','0x000000000000000000000000000000000000002B')," +
        			"('0x000000000000000000000000000000000000005A','0x000000000000000000000000000000000000002B')," +
        			"('0x000000000000000000000000000000000000006A','0x000000000000000000000000000000000000003B')," +
        			"('0x000000000000000000000000000000000000007A','0x000000000000000000000000000000000000003B')," +
        			"('0x000000000000000000000000000000000000008A','0x000000000000000000000000000000000000003B')," +
        			"('0x000000000000000000000000000000000000009A','0x000000000000000000000000000000000000003B')," +
        			"('0x00000000000000000000000000000000000000AA','0x000000000000000000000000000000000000003B')," +
        			"('0x000000000000000000000000000000000000001A','0x000000000000000000000000000000000000003B');")
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
        			"('0x000000000000000000000000000000000000002A','0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000002D', 100)," +
        			"('0x000000000000000000000000000000000000003A','0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000003D', 100)," +
        			"('0x000000000000000000000000000000000000004A','0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000004D', 100)," +
        			"('0x000000000000000000000000000000000000005A','0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000005D', 100)," +
        			"('0x000000000000000000000000000000000000006A','0x000000000000000000000000000000000000002B','0x000000000000000000000000000000000000001D', 100)," +
        			"('0x000000000000000000000000000000000000007A','0x000000000000000000000000000000000000003B','0x000000000000000000000000000000000000007D', 100)," +
        			"('0x000000000000000000000000000000000000008A','0x000000000000000000000000000000000000003B','0x000000000000000000000000000000000000008D', 100)," +
        			"('0x000000000000000000000000000000000000009A','0x000000000000000000000000000000000000003B','0x000000000000000000000000000000000000009D', 100);")
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
			JUDGE, SUBMISSION, REVIEW, SUBMISSIONGRADE,
			CONTESTJUDGE, CONTESTSPONSOR
        };
        
        String[][] FILL = {
    			IN_CONTESTANT, IN_CONTEST, IN_SPONSOR,
    			IN_JUDGE, IN_SUBMISSION, IN_REVIEW, IN_SUBMISSIONGRADE,
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
