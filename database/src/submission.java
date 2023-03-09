public class submission 
{	    
	     protected String contestantWallet;
	     protected String contestWallet;
	     protected String submissionFile;
	 
	    //constructors
	    public submission() {
	    }
	 
	    public submission(String contestantWallet, String contestWallet, String submissionFile) 
	    {
	    	this.contestantWallet = contestantWallet;
	    	this.contestWallet = contestWallet;
	    	this.submissionFile = submissionFile;
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
	    public String getSubmission() {
	        return submissionFile;
	    }
	    public void setSubmission(String submissionFile) {
	        this.submissionFile = submissionFile;
	    }
	}