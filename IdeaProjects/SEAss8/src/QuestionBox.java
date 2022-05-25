import java.util.ArrayList;

public class QuestionBox {

    protected ArrayList<Question> questions;
    protected int question_index;


    public QuestionBox(ArrayList<Question> questions){
        this.questions = questions;
        this.question_index = 0;
    }

    public Question getQuestion(){
        Question q = questions.get(question_index);
        question_index = (question_index + 1)%questions.size();
        return q;
    }


    
}
