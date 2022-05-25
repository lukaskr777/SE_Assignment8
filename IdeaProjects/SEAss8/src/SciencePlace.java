
public class SciencePlace extends Place{


    public SciencePlace(QuestionBox science_questions){
        super(science_questions);
    }


    public Question getQuestion(){
        return question_box.getQuestion();
    }

    public String getCategory(){
        return "Science";
    }
    
}
