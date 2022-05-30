import java.util.ArrayList;


// add observers ???

public class Game2 {
    
    protected int board_size;
    protected int winning_purse;
    protected Place[] board;
	protected ArrayList<Player> players;
    protected int current_player_id;
    protected Player current_player;
    protected Question current_question;
    protected GameAnnouncer announcer;
	
	
	public Game2() {
		this.board_size = 12;
        this.winning_purse = 6;
		this.current_player_id = 0;
        this.players = new ArrayList<>();
        this.announcer = new GameAnnouncer();
		this.announcer.setSilent(true);
		initializePlayBoard();
	}

	public ArrayList<Player> getPlayers(){ return this.players; }
	
    private void initializePlayBoard(){
		int question_amount = 50;
        QuestionBoxGenerator generator = new QuestionBoxGenerator();
		
        QuestionBox pop = generator.generatePopBox(question_amount);
        QuestionBox science = generator.generateSciencePlace(question_amount);
        QuestionBox sports = generator.generateSportsBox(question_amount);
        QuestionBox rock = generator.generateRockBox(question_amount);
		
        board = new Place[board_size];
		
        board[0] = new PopPlace(pop);
        board[1] = new SciencePlace(science);
        board[2] = new SportsPlace(sports);
        board[3] = new RockPlace(rock);
        board[4] = new PopPlace(pop);
        board[5] = new SciencePlace(science);
        board[6] = new SportsPlace(sports);
        board[7] = new RockPlace(rock);
        board[8] = new PopPlace(pop);
        board[9] = new SciencePlace(science);
        board[10] = new SportsPlace(sports);
        board[11] = new RockPlace(rock);
		
		
    }

    private Player nextPlayer(){
        Player next_player = players.get(current_player_id);
        current_player_id = (current_player_id + 1)% players.size();
        return next_player;
    }
	
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}
	
	public boolean addPlayer(String playerName) {
		
		players.add(new Player(playerName, 0,0,false));
		
		
		announcer.printPlayerAdded(playerName);
		announcer.printPlayerNumber(players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}
	
    public boolean luckRoll(int roll){
		return roll % 2 != 0;
    }
	
	
	public void roll(int roll) {
		this.current_player = nextPlayer();
		
		announcer.printCurrentPlayer(current_player.getName());
		announcer.printRoll(roll);
		
		if(current_player.inPenatlyBox() && !luckRoll(roll)){
			
			
			announcer.printNotOutOfPenaltyBox(current_player.getName());
			current_player.setIsgettingFromPenalty(false);
		}
		else{
			if(current_player.inPenatlyBox()){
				current_player.setIsgettingFromPenalty(true);
				
				announcer.printOutOfPenaltyBox(current_player.getName());
				
			}
			movePlayer(current_player, roll);
			askQuestionToPlayer(current_player);
			
		}
	}

	private void movePlayer(Player player, int roll){
		int next_position = (player.getPosition() + roll) % board_size;
		player.moveToPosition(next_position);
	}

    private void askQuestionToPlayer(Player player){
        
        Place place = board[player.getPosition()];
        current_question = place.getQuestion();


        announcer.printNewLocation(player.getName(),player.getPosition());
        announcer.printCategory(place.getCategory());
        announcer.printQuestion(current_question.getQuestion());
    }

	public boolean correctAnswerAndContinue() {

        boolean continue_game = true;
		if (current_player.inPenatlyBox() && !current_player.isGettinFromPentalyBox()) {
				continue_game = !didPlayerWin();
		} 	
		else{
				current_player.setInPenatly(false);
				
				announcer.printCorrect();
				
				current_player.addToPurse(current_question.getReward());

                announcer.printCoins(current_player.getName(), current_player.getPurse());
	
				continue_game = !didPlayerWin();
		}		

    	return continue_game;
	}

	public boolean wrongAnswerAndContinue() {
		announcer.printIncorrect();
		announcer.printSentToPenaltyBox(current_player.getName());
		current_player.setInPenatly(true);
        
		return true;
	}

	// misleading, returns true when player did not win but the name of the methods indicated it should return false
	private boolean didPlayerWin() {
		return current_player.getPurse() >= winning_purse;

	}


}
