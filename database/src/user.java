public class user 
{	    
	     protected String walletAddress;
	     protected String pass;
	     protected String userRole;
	 
	    //constructors
	    public user() {
	    }
	 
	    public user(String walletAddress) 
	    {
	    	this.walletAddress = walletAddress;
	    }
	    
	    
	    public user(String walletAddress,String pass, String userRole) 
	    {
	    	this(pass,userRole);
	    	this.walletAddress = walletAddress;
	    }
	 
	
	    public user(String pass, String userRole) 
	    {
	    	this.pass = pass;
	    	this.userRole = userRole;
	    }
	    
	   //getter and setter methods
	    public String getWallet() {
	        return walletAddress;
	    }
	    public void setWallet(String walletAddress) {
	        this.walletAddress = walletAddress;
	    }
	    
	    public String getPass() {
	        return pass;
	    }
	    public void setPass(String pass) {
	        this.pass = pass;
	    }
	    
	    public String getRole() {
	        return userRole;
	    }
	    public void setRole(String userRole) {
	        this.userRole = userRole;
	    }
	}
