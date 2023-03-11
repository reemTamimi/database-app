public class submission 
{	    
	     protected String contestantWallet;
	     protected String contestWallet;
	     protected String submissionFile;
	     
	     protected String title;
	     protected String requirements;
	 
	    //constructors
	    public submission() {
	    }
	 
	    public submission(String contestantWallet, String contestWallet, String submissionFile, String title, String requirements) 
	    {
	    	this.contestantWallet = contestantWallet;
	    	this.contestWallet = contestWallet;
	    	this.submissionFile = submissionFile;
	    	this.title = title;
	    	this.requirements = requirements;
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
	    public String getTitle() {
	        return title;
	    }
	    public void setTitle(String title) {
	        this.title = title;
	    }
	    public String getRequirements() {
	        return requirements;
	    }
	    public void setRequirements(String requirements) {
	        this.requirements = requirements;
	    }
	}