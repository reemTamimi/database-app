public class contest 
{
	    
	     protected String title;
	     protected String walletAddress;
		 protected String startDate;
		 protected String endDate; 
		 protected String contestStatus; 
		 protected double sponsorFee; 
		 protected String requirements; 
	 
	    //constructors
	    public contest() {
	    }
	 
//	    public contest(String walletAddress) 
//	    {
//	    	this.walletAddress = walletAddress;
//	    }
	    
	    
	    public contest(String walletAddress, String title) 
	    {
	    	this.title = title;
	    	this.walletAddress = walletAddress;
	    }
	    
	   //getter and setter methods
	    public String getWallet() {
	        return walletAddress;
	    }
	    public void setWallet(String walletAddress) {
	        this.walletAddress = walletAddress;
	    }
	    
	    public String getTitle() {
	        return title;
	    }
	    public void setTitle(String title) {
	        this.title = title;
	    }
	    
	    public String getStartDate() {
	        return startDate;
	    }
	    public void setStartDate(String startDate) {
	        this.startDate = startDate;
	    }
	    
	    public String getEndDate() {
	        return endDate;
	    }
	    public void setEndDate(String endDate) {
	        this.endDate = endDate;
	    }
	    
	    public String getStatus() {
	        return contestStatus;
	    }
	    public void setStatus(String contestStatus) {
	        this.contestStatus = contestStatus;
	    }
	    
	    public double getFee() {
	        return sponsorFee;
	    }
	    public void setFee(double sponsorFee) {
	        this.sponsorFee = sponsorFee;
	    }
	    
	    public String getRequirements() {
	        return requirements;
	    }
	    public void setRequirements(String requirements) {
	        this.requirements = requirements;
	    }
	}
