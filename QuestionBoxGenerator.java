import java.util.ArrayList;

public class QuestionBoxGenerator {
    

    public QuestionBoxGenerator(){}

    public QuestionBox generatePopBox(int amount){
        ArrayList<Question> pop_questions = new ArrayList<>(amount);

        for(int i = 0; i < amount; ++i){
            Question q = new Question("Pop Question " + (i+1),"Answer to pop question " + (i + 1), 1);
            pop_questions.add(q);
        }

        return new QuestionBox(pop_questions);
    }

    
    public QuestionBox generateSciencePlace(int amount){
        ArrayList<Question> science_questions = new ArrayList<>(amount);

        for(int i = 0; i < amount; ++i){
            Question q = new Question("Science Question " + (i+1),"Answer to science question " + (i + 1), 1);
            science_questions.add(q);
        }

        return new QuestionBox(science_questions);
    }

    
    public QuestionBox generateSportsBox(int amount){
        ArrayList<Question> sports_questions = new ArrayList<>(amount);

        for(int i = 0; i < amount; ++i){
            Question q = new Question("Sports Question " + (i+1),"Answer to sports question " + (i + 1), 1);
            sports_questions.add(q);
        }

        return new QuestionBox(sports_questions);
    }

    
    public QuestionBox generateRockBox(int amount){
        ArrayList<Question> rock_questions = new ArrayList<>(amount);

        for(int i = 0; i < amount; ++i){
            Question q = new Question("Rock Question " + (i+1),"Answer to rock question " + (i + 1), 1);
            rock_questions.add(q);
        }

        return new QuestionBox(rock_questions);
    }
}
