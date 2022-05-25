import java.util.ArrayList;


// add observers ???

public class Game2 {
    
    int board_size;
    int winning_purse;
    Place[] board;
	ArrayList<Player> players;
    int current_player_id;
    Player current_player;
    Question current_question;

	
	public Game2() {
		this.board_size = 12;
        this.winning_purse = 6;
		this.current_player_id = 0;
        this.players = new ArrayList<>();
		initializePlayBoard();
	}

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


		System.out.println(playerName + " was added");
		System.out.println("They are player number " + players.size());
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
		
		System.out.println(current_player.getName() + " is the current player");
		System.out.println("They have rolled a " + roll);


		if(current_player.inPenatlyBox() && !luckRoll(roll)){
			
			
			System.out.println(current_player.getName() + " is not getting out of the penalty box");
			current_player.setIsgettingFromPenalty(false);
		}
		else{
			if(current_player.inPenatlyBox()){
				current_player.setIsgettingFromPenalty(true);

				System.out.println(current_player.getName() + " is getting out of the penalty box");
				
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


        System.out.println(player.getName()
						+ "'s new location is "
						+ player.getPosition());
		System.out.println("The category is " + place.getCategory());
		System.out.println(current_question.getQuestion());		

    }



	public boolean correctAnswer() {

        boolean continue_game = true;
		if (current_player.inPenatlyBox() && !current_player.isGettinFromPentalyBox()) {
				continue_game = !didPlayerWin();
		} 	
		else{
				
				System.out.println("Answer was correct!!!!");
				
				current_player.addToPurse(current_question.getReward());
	
				System.out.println(current_player.getName()
						+ " now has "
						+ current_player.getPurse()
						+ " Gold Coins.");
	
				 continue_game = !didPlayerWin();
		}
		
    	current_player = nextPlayer();
    	return continue_game;
	}

	public boolean wrongAnswer() {
		System.out.println("Question was incorrectly answered");
		System.out.println(current_player.getName() + " was sent to the penalty box");
		current_player.setInPenatly(true);
		current_player = nextPlayer();
        
		return true;
	}

	// misleading, returns true when player did not win but the name of the methods indicated it should return false
	private boolean didPlayerWin() {
		return current_player.getPurse() >= winning_purse;

	}


}
