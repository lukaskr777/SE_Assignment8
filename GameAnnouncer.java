public class GameAnnouncer
{
    public GameAnnouncer(){}

    public void printPlayerAdded(String playerName)
    {
        System.out.println(playerName + " was added");
    }

    public void printPlayerNumber(int playerNumber)
    {
        System.out.println("They are player number " + playerNumber);
    }

    public void printCurrentPlayer(String playerName)
    {
        System.out.println(playerName + " is the current player");
    }

    public void printRoll(int roll)
    {
        System.out.println("They have rolled a " + roll);
    }

    public void printNotOutOfPenaltyBox(String playerName)
    {
        System.out.println(playerName + " is not getting out of the penalty box");
    }

    public void printOutOfPenaltyBox(String playerName)
    {
        System.out.println(playerName + " is getting out of the penalty box");
    }

    public void printNewLocation(String playerName, int playerPosition)
    {
        System.out.println(playerName + "'s new location is " + playerPosition);
    }

    public void printCategory(String category)
    {
        System.out.println("The category is " + category);
    }

    public void printQuestion(String question)
    {
        System.out.println(question);
    }

    public void printCorrect()
    {
        System.out.println("Answer was correct!!!!");
    }

    public void printIncorrect()
    {
        System.out.println("Question was incorrectly answered");
    }

    public void printCoins(String playerName, int coins)
    {
        System.out.println(playerName + " now has " + coins + " Gold Coins.");
    }

    public void printSentToPenaltyBox(String playerName)
    {
        System.out.println(playerName + " was sent to the penalty box");
    }
}
