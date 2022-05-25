
public abstract class Place {


    protected QuestionBox question_box;


    public Place(QuestionBox question_box){
        this.question_box=  question_box;
    }


    public abstract Question getQuestion();

    public abstract String getCategory();
    
}
