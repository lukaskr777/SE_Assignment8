
public class PopPlace extends Place{


    public PopPlace(QuestionBox pop_questions){
        super(pop_questions);
    }


    public Question getQuestion(){
        return question_box.getQuestion();
    }
    
    public String getCategory(){
        return "Pop";
    }
}
