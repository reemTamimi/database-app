public class judge 
{	    
	     protected String walletAddress;
	     protected double balance;
	 
	    //constructors
	    public judge() {
	    }
	 
	    public judge(String walletAddress) 
	    {
	    	this.walletAddress = walletAddress;
	    }
	    
	    
	    public judge(String walletAddress, double balance) 
	    {
	    	this.balance = balance;
	    	this.walletAddress = walletAddress;
	    }
	    
	   //getter and setter methods
	    public String getWallet() {
	        return walletAddress;
	    }
	    public void setWallet(String walletAddress) {
	        this.walletAddress = walletAddress;
	    }
	    
	    public double getBalance() {
	        return balance;
	    }
	    public void setBalance(double balance) {
	        this.balance = balance;
	    }
	}