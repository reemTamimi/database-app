public class submission 
{	    
	     protected String contestantWallet;
	     protected String contestWallet;
	 
	    //constructors
	    public submission() {
	    }
	 
	    public submission(String contestantWallet, String contestWallet) 
	    {
	    	this.contestantWallet = contestantWallet;
	    	this.contestWallet = contestWallet;
	    }
	    
	   //getter and setter methods
	    public String getContest() {
	        return contestWallet;
	    }
	    public void setContest(String contestWallet) {
	        this.contestWallet = contestWallet;
	    }
	    
	    public String getContestant() {
	        return contestantWallet;
	    }
	    public void setContestant(String contestantWallet) {
	        this.contestantWallet = contestantWallet;
	    }
	}