public class GameUnitTest {
    
    Game game;

    public GameUnitTest(){
        this.game = new Game();
        game.add("Chet");
        game.add("Pat");
        game.add("Sue");
    }


    public void testCorrectAnswering(){
        
        game.roll(1);
        assert game.wasCorrectlyAnswered(); //player 0 did not win the game
        assert game.currentPlayer == 1; // next player is player 1
        assert game.purses[0] == 1; // player 0 has one point from the correctly answered question
    }

    public void testIncorrectAnswering(){
        
        game.roll(3);
        assert game.wrongAnswer(); // player 0 did not win the game
        assert game.currentPlayer == 1; // next player is player 1
        assert game.purses[0] == 0; // player 0 has zero point from the incorrectly answered question
    }

    public void testCategoryChange(){ // is this even possible to test?
        game.roll(5);

        assert game.places[0] == 5; // on place 5 is Science
    }

    public void testCategoryPick(){
        game.roll(5);

        assert game.scienceQuestions.size() == 49; // after removing one question out of 50, there are 49 questions
    }
}
