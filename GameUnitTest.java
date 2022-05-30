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
    
    Game2 game;

    public GameUnitTest(){
        this.game = new Game2();
        game.addPlayer("Chet");
        game.addPlayer("Pat");
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
        game.current_player.setInPenatly(false);
        game.wrongAnswer();
        assert game.players.get(0).inPenatlyBox();
    }

    //Tests if the player answered wrong and is in the box
    public void testIncorrectAnsweringAndInBox()
    {
        resetGame();
        game.current_player.setInPenatly(true);
        game.wrongAnswer();
        assert game.players.get(0).inPenatlyBox();
    }

    //Tests if the current player answered right, and they are in the box and if they won the game
    public void testCorrectAndInBoxAndWin()
    {
        resetGame();
        game.current_player.setInPenatly(true);
        game.current_player.addToPurse(6);
        assert !game.correctAnswer();
        assert !game.players.get(0).inPenatlyBox();
    }

    //Tests if the current player answered right, and they are in the penalty box, and they haven't won
    public void testCorrectAndInBoxNoWin()
    {
        resetGame();
        game.current_player.setInPenatly(true);
        assert game.correctAnswer();
        assert game.players.get(0).inPenatlyBox();
    }

    //Tests if the current player answered right, and they are not in the penalty box, and they have won
    public void testCorrectAndOutOfBoxAndWin()
    {
        resetGame();
        game.current_player.setInPenatly(false);
        assert !game.correctAnswer();
        assert !game.players.get(0).inPenatlyBox();
    }

    //Tests if the current player answered right, and they are not in the penalty box, and they have not won
    public void testCorrectAndOutOfBoxNoWin()
    {
        resetGame();
        game.current_player.setInPenatly(false);
        assert game.correctAnswer();
        assert !game.players.get(0).inPenatlyBox();
    }

    //Tests the places 0,4,8 and the place is a pop place, and if the question is a pop question
    public void testPopPlaceAndQuestion()
    {
        resetGame();
        assert game.board[0].getCategory().equals("Pop");
        assert game.board[0].getQuestion().getQuestion().equals("Pop Question 1");
        assert game.board[4].getCategory().equals("Pop");
        assert game.board[4].getQuestion().getQuestion().equals("Pop Question 2");
        assert game.board[8].getCategory().equals("Pop");
        assert game.board[8].getQuestion().getQuestion().equals("Pop Question 3");
    }

    //Tests the places 1,5,9 and the place is a science place, and if the question is a science question
    public void testSciencePlaceAndQuestion()
    {
        resetGame();
        assert game.board[1].getCategory().equals("Science");
        assert game.board[1].getQuestion().getQuestion().equals("Science Question 1");
        assert game.board[5].getCategory().equals("Science");
        assert game.board[5].getQuestion().getQuestion().equals("Science Question 2");
        assert game.board[9].getCategory().equals("Science");
        assert game.board[9].getQuestion().getQuestion().equals("Science Question 3");
    }

    //Tests the places 2,6,10 and the place is a sports place, and if the question is a sports question
    public void testSportsPlaceAndQuestion()
    {
        resetGame();
        assert game.board[2].getCategory().equals("Sports");
        assert game.board[2].getQuestion().getQuestion().equals("Sports Question 1");
        assert game.board[6].getCategory().equals("Sports");
        assert game.board[6].getQuestion().getQuestion().equals("Sports Question 2");
        assert game.board[10].getCategory().equals("Sports");
        assert game.board[10].getQuestion().getQuestion().equals("Sports Question 3");
    }

    //Tests the places 3,7,11 and the place is a rock place, and if the question is a rock question
    public void testRockPlaceAndQuestion()
    {
        resetGame();
        assert game.board[3].getCategory().equals("Rock");
        assert game.board[3].getQuestion().getQuestion().equals("Rock Question 1");
        assert game.board[7].getCategory().equals("Rock");
        assert game.board[7].getQuestion().getQuestion().equals("Rock Question 2");
        assert game.board[11].getCategory().equals("Rock");
        assert game.board[11].getQuestion().getQuestion().equals("Rock Question 3");
    }

    //Tests whether rolling a number between 1 and 6 increments the current position by that number
    public void testRollNumber()
    {
        resetGame();
        game.roll(3);
        assert game.current_player.getPosition() == 3;
    }

    //Tests whether rolling when the current position is at the max position
    public void testRollNumberWhenMax()
    {
        resetGame();
        game.current_player.moveToPosition(game.board_size - 1);
        game.roll(1);
        assert game.current_player.getPosition() == 0;
    }
}
