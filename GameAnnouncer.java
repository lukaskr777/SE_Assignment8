public class GameAnnouncer
{
    public GameAnnouncer(){}

    private boolean silent = false;

    public void setSilent(boolean is_silent){
        silent = is_silent;
    }

    public void printPlayerAdded(String playerName)
    {   
        if(silent) return;
        System.out.println(playerName + " was added");
    }

    public void printPlayerNumber(int playerNumber)
    {
        if(silent) return;
        System.out.println("They are player number " + playerNumber);
    }

    public void printCurrentPlayer(String playerName)
    {
        if(silent) return;
        System.out.println(playerName + " is the current player");
    }

    public void printRoll(int roll)
    {
        if(silent) return;
        System.out.println("They have rolled a " + roll);
    }

    public void printNotOutOfPenaltyBox(String playerName)
    {
        if(silent) return;
        System.out.println(playerName + " is not getting out of the penalty box");
    }

    public void printOutOfPenaltyBox(String playerName)
    {
        if(silent) return;
        System.out.println(playerName + " is getting out of the penalty box");
    }

    public void printNewLocation(String playerName, int playerPosition)
    {
        if(silent) return;
        System.out.println(playerName + "'s new location is " + playerPosition);
    }

    public void printCategory(String category)
    {
        if(silent) return;
        System.out.println("The category is " + category);
    }

    public void printQuestion(String question)
    {
        if(silent) return;
        System.out.println(question);
    }

    public void printCorrect()
    {
        if(silent) return;
        System.out.println("Answer was correct!!!!");
    }

    public void printIncorrect()
    {
        if(silent) return;
        System.out.println("Question was incorrectly answered");
    }

    public void printCoins(String playerName, int coins)
    {
        if(silent) return;
        System.out.println(playerName + " now has " + coins + " Gold Coins.");
    }

    public void printSentToPenaltyBox(String playerName)
    {
        if(silent) return;
        System.out.println(playerName + " was sent to the penalty box");
    }
}
