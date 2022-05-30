/**
 * Testing strategy:
 *
 * Correct/incorrect answering of questions:
 * answer = incorrect && inPenaltyBox = false
 * answer = incorrect && inPenaltyBox = true
 * answer = correct && inPenaltyBox = true && win = true
 * answer = correct && inPenaltyBox = false && win = true
 * answer = correct && inPenaltyBox = true && win = false
 * answer = correct && inPenaltyBox = false && win = false
 *
 * Changing of question categories and whether a question relating to the correct
 * category is asked
 * placeIndex = 0,4,8 && place = popPlace && questionCategory = pop
 * placeIndex = 1,5,9 && place = sciencePlace && questionCategory = science
 * placeIndex = 2,6,10 && place = sportsPlace && questionCategory = sports
 * placeIndex = 3,7,11 && place = rockPlace && questionCategory = rock
 *
 * The roll method in the game class
 * roll = 1,2,3,4,5,6 && placeIndex = placeIndex + roll
 * placeIndex of currentPlayer == 11 and roll == 1
 */


public class GameUnitTest {
    
    
    public static void main(String[] args){
        GameUnitTest test = new GameUnitTest();
        test.testRollNumber();
        test.testRollNumberWhenMax();

        
        test.testCorrectAndInBoxAndWin();
        test.testCorrectAndInBoxNoWin();
        test.testCorrectAndOutOfBoxAndWin();
        test.testCorrectAndOutOfBoxNoWin();
        test.testIncorrectAnsweringAndInBox();
        test.testIncorrectAnsweringAndOutOfBox();

        test.testPopPlaceAndQuestion();
        test.testSportsPlaceAndQuestion();
        test.testSciencePlaceAndQuestion();
        test.testRockPlaceAndQuestion();


    }

    
    Game2 game;

    public GameUnitTest(){
        resetGame();
    }

    public void resetGame()
    {
        game = new Game2();
        game.addPlayer("Chet");
        game.addPlayer("Pat");
    }

    //Tests if the player answered wrong and is not in the box
    public void testIncorrectAnsweringAndOutOfBox()
    {
        resetGame();
        int index = game.current_player_id;
        game.getPlayers().get(index).setInPenatly(false);
        game.current_player =  game.getPlayers().get(index);
        game.wrongAnswerAndContinue();
        
        assertCheck( game.current_player.inPenatlyBox(), "testIncorrectAnsweringAndOutOfBox");
    }

    //Tests if the player answered wrong and is in the box
    public void testIncorrectAnsweringAndInBox()
    {
        resetGame();
        int index = game.current_player_id;
        game.getPlayers().get(index).setInPenatly(true);
        game.current_player =  game.getPlayers().get(index);
        game.wrongAnswerAndContinue();
        assertCheck( game.current_player.inPenatlyBox(), "testIncorrectAnsweringAndInBox");
    }

    //Tests if the current player answered right, and they are in the box and if they won the game
    public void testCorrectAndInBoxAndWin()
    {
        resetGame();
        int index = game.current_player_id;
        game.getPlayers().get(index).setInPenatly(true);
        game.getPlayers().get(index).setIsgettingFromPenalty(true);
        game.getPlayers().get(index).addToPurse(5);
        game.current_player =  game.getPlayers().get(index);
        game.current_question = new Question("Test question", "No answer", 1);

        assertCheck( !game.correctAnswerAndContinue(), "testCorrectAndInBoxAndWin");
        assertCheck( !game.current_player.inPenatlyBox(), "testCorrectAndInBoxAndWin");
    }

    //Tests if the current player answered right, and they are in the penalty box, and they haven't won
    public void testCorrectAndInBoxNoWin()
    {
        resetGame();
        int index = game.current_player_id;
        game.getPlayers().get(index).setInPenatly(true);
        game.current_player =  game.getPlayers().get(index);
        game.current_question = new Question("Test question", "No answer", 1);

        assertCheck( game.correctAnswerAndContinue(), "testCorrectAndInBoxNoWin");
        assertCheck( game.current_player.inPenatlyBox(), "testCorrectAndInBoxNoWin");
    }

    //Tests if the current player answered right, and they are not in the penalty box, and they have won
    public void testCorrectAndOutOfBoxAndWin()
    {
        resetGame();
        int index = game.current_player_id;
        game.getPlayers().get(index).setInPenatly(false);
        game.current_player =  game.getPlayers().get(index);
        game.getPlayers().get(index).addToPurse(5);
        game.current_question = new Question("Test question", "No answer", 1);

        assertCheck( !game.correctAnswerAndContinue(), "testCorrectAndOutOfBoxAndWin");
        assertCheck( !game.current_player.inPenatlyBox(), "testCorrectAndOutOfBoxAndWin");
    }

    //Tests if the current player answered right, and they are not in the penalty box, and they have not won
    public void testCorrectAndOutOfBoxNoWin()
    {
        resetGame();
        int index = game.current_player_id;
        game.getPlayers().get(index).setInPenatly(false);
        game.current_player =  game.getPlayers().get(index);
        game.current_question = new Question("Test question", "No answer", 1);
        
        assertCheck( game.correctAnswerAndContinue(), "testCorrectAndOutOfBoxNoWin");
        assertCheck( !game.current_player.inPenatlyBox(), "testCorrectAndOutOfBoxNoWin");
    }

    //Tests the places 0,4,8 and the place is a pop place, and if the question is a pop question
    public void testPopPlaceAndQuestion()
    {
        resetGame();
        assertCheck( game.board[0].getCategory().equals("Pop"), "pop place error");
        assertCheck( game.board[0].getQuestion().getQuestion().equals("Pop Question 1"), "pop place error");
        assertCheck( game.board[4].getCategory().equals("Pop"), "pop place error");
        assertCheck( game.board[4].getQuestion().getQuestion().equals("Pop Question 2"), "pop place error");
        assertCheck( game.board[8].getCategory().equals("Pop"), "pop place error");
        assertCheck( game.board[8].getQuestion().getQuestion().equals("Pop Question 3"), "pop place error");
    }

    //Tests the places 1,5,9 and the place is a science place, and if the question is a science question
    public void testSciencePlaceAndQuestion()
    {
        resetGame();
        assertCheck( game.board[1].getCategory().equals("Science"), "science place error");
        assertCheck( game.board[1].getQuestion().getQuestion().equals("Science Question 1"), "science place error");
        assertCheck( game.board[5].getCategory().equals("Science"), "science place error");
        assertCheck( game.board[5].getQuestion().getQuestion().equals("Science Question 2"), "science place error");
        assertCheck( game.board[9].getCategory().equals("Science"), "science place error");
        assertCheck( game.board[9].getQuestion().getQuestion().equals("Science Question 3"), "science place error");
    }

    //Tests the places 2,6,10 and the place is a sports place, and if the question is a sports question
    public void testSportsPlaceAndQuestion()
    {
        resetGame();
        assertCheck( game.board[2].getCategory().equals("Sports"), "sports place error");
        assertCheck( game.board[2].getQuestion().getQuestion().equals("Sports Question 1"), "sports place error");
        assertCheck( game.board[6].getCategory().equals("Sports"), "sports place error");
        assertCheck( game.board[6].getQuestion().getQuestion().equals("Sports Question 2"), "sports place error");
        assertCheck( game.board[10].getCategory().equals("Sports"), "sports place error");
        assertCheck( game.board[10].getQuestion().getQuestion().equals("Sports Question 3"), "sports place error");
    }

    //Tests the places 3,7,11 and the place is a rock place, and if the question is a rock question
    public void testRockPlaceAndQuestion()
    {
        resetGame();
        assertCheck( game.board[3].getCategory().equals("Rock"),"rock place error");
        assertCheck( game.board[3].getQuestion().getQuestion().equals("Rock Question 1"), "rock place error");
        assertCheck( game.board[7].getCategory().equals("Rock"), "rock place error");
        assertCheck( game.board[7].getQuestion().getQuestion().equals("Rock Question 2"), "rock place error");
        assertCheck( game.board[11].getCategory().equals("Rock"), "rock place error");
        assertCheck( game.board[11].getQuestion().getQuestion().equals("Rock Question 3"), "rock place error");
    }

    //Tests whether rolling a number between 1 and 6 increments the current position by that number
    public void testRollNumber()
    {
        resetGame();
        game.roll(3);
        assertCheck(game.current_player.getPosition() == 3, "Wrong position");
    }

    //Tests whether rolling when the current position is at the max position
    public void testRollNumberWhenMax()
    {
        resetGame();
        game.getPlayers().get(0).moveToPosition(game.board_size - 1);
        game.roll(2);

        assertCheck((game.current_player.getPosition() == 1), "Bad position after boeard cycle");
    }

    public void assertCheck(boolean b, String message){
        if(!b) System.out.println("[ERROR]\t" + message);;
    }
}
