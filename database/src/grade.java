
public class grade {
	
    protected String contestantWallet;
    protected String contestWallet;
    protected String judgeWallet;
    protected String grade;
    

   //constructors
   public grade() {
   }

   public grade(String contestantWallet, String contestWallet, String judgeWallet, String grade) 
   {
   	this.contestantWallet = contestantWallet;
   	this.contestWallet = contestWallet;
   	this.judgeWallet = judgeWallet;
   	this.grade = grade;
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
   public String getJudge() {
       return judgeWallet;
   }
   public void setJudge(String judgeWallet) {
       this.judgeWallet = judgeWallet;
   }
   public String getGrade() {
       return grade;
   }
   public void setGrade(String grade) {
       this.grade = grade;
   }

}
