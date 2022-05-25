
public class SportsPlace extends Place{


    public SportsPlace(QuestionBox sports_questions){
        super(sports_questions);
    }


    public Question getQuestion(){
        return question_box.getQuestion();
    }

    public String getCategory(){
        return "Sports";
    }
    
}
