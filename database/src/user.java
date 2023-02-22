public class user 
{
		/*protected String password;
	 	protected String email;
	    protected String firstName;
	    protected String lastName;
	    protected String adress_street_num;
	    protected String adress_street;
	    protected String adress_city;
	    protected String adress_state;
	    protected String adress_zip_code;
	    protected String birthday;
	    protected int cash_bal;
	    protected int PPS_bal;*/
	    
	     protected String walletAddress;
	     protected String pass;
	     protected String userRole;
	 
	    //constructors
	    public user() {
	    }
	 
	    public user(String walletAddress) 
	    {
	    	this.walletAddress = walletAddress;
	        //this.email = email;
	    }
	    
	    
	    public user(String walletAddress,String pass, String userRole) 
	    {
	    	//this(firstName,lastName,password,birthday, adress_street_num,  adress_street,  adress_city,  adress_state,  adress_zip_code,cash_bal,PPS_bal);
	    	//this.email = email;
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