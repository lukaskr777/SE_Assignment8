
public class RockPlace extends Place{


    public RockPlace(QuestionBox rock_questions){
        super(rock_questions);
    }


    public Question getQuestion(){
        return this.question_box.getQuestion();
    }

    public String getCategory(){
        return "Rock";
    }
    
}
