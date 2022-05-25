public class Question {


    private String question, answer;
    private int reward;

    public Question(String question, String answer, int reward){
        this.question = question;
        this.answer = answer;
        this.reward = reward;
    }

    public String getQuestion(){ return this.question;}
    public String getAnswer(){ return this.answer; }
    public int getReward(){ return this.reward; }
    
}
