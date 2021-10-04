/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

 import java.util.Random;

/**
 * This class includes the methods to support the search of a solution.
 */
public class Search
{
    public static final int horizontalGridSize = 5;
    public static final int verticalGridSize = 6;
    
    public static final char[] input = { 'W', 'Y', 'I', 'T', 'Z', 'L'};
    
    //Static UI class to display the board
    public static UI ui = new UI(horizontalGridSize, verticalGridSize, 50);

	/**
	 * Helper function which starts a basic search algorithm
	 */
    public static void search()
    {
        // Initialize an empty board
        int[][] field = new int[horizontalGridSize][verticalGridSize];

        for(int i = 0; i < field.length; i++)
        {
            for(int j = 0; j < field[i].length; j++)
            {
                // -1 in the state matrix corresponds to empty square
                // Any positive number identifies the ID of the pentomino
            	field[i][j] = -1;
            }
        }
        //Start the basic search
        basicSearch(field);
    }
	
	/**
	 * Get as input the character representation of a pentomino and translate it into its corresponding numerical value (ID)
	 * @param character a character representating a pentomino
	 * @return	the corresponding ID (numerical value)
	 */
    private static int characterToID(char character) {
    	int pentID = -1; 
    	if (character == 'X') {
    		pentID = 0;
    	} else if (character == 'I') {
    		pentID = 1;
    	} else if (character == 'Z') {
    		pentID = 2;
    	} else if (character == 'T') {
    		pentID = 3;
    	} else if (character == 'U') {
    		pentID = 4;
     	} else if (character == 'V') {
     		pentID = 5;
     	} else if (character == 'W') {
     		pentID = 6;
     	} else if (character == 'Y') {
     		pentID = 7;
    	} else if (character == 'L') {
    		pentID = 8;
    	} else if (character == 'P') {
    		pentID = 9;
    	} else if (character == 'N') {
    		pentID = 10;
    	} else if (character == 'F') {
    		pentID = 11;
    	} 
    	return pentID;
    }

    private static boolean recursionSearch(int[][] field,int i,int mutation,int x,int y){
        //!!WARNING!!
        //This method is INCORRECT, just me "thinking out loud",
        //but perhaps this will help someone in their though process
        //when coming up with a solution.
        int pentID = characterToID(input[i]);
        int[][] piece = PentominoDatabase.data[pentID][mutation];
    	if(isFull(field)) {
            return true;
        }
    	//checks whether there even are 5 tiles left, and if not, the solution does not work and the field is reset
    	else if(x*y > horizontalGridSize * verticalGridSize - 5 && x*y < horizontalGridSize * verticalGridSize){
            //resets field after recursive call is done
            for(int j = 0; j < verticalGridSize; j++){
                for(int k = 0; k < horizontalGridSize; k++){
                    field[i][j] = 0;
                }
            }
    	    return false;
        }
    	else{
			//when adding a piece, check if squares are empty for the specific
            addPiece(field,piece,PentominoDatabase.data[pentID].length,x,y);
            //all mutations must be tried before changing the pentID,
            //which is determined by the integer i
            if(mutation < 7) {
                return recursionSearch(field,i,mutation + 1,x,y);
            }
            //after trying mutations of first pentomino in first space, tries all mutations of second pentomino in tile x+1
            else if(mutation == 7 && x < horizontalGridSize){
                mutation = 0;
                if(field[x+1][y] < 1) {
                    return recursionSearch(field, i + 1, mutation, x + 1, y);
                }
                else{
                    x++;
                }
            }
            //tries mutations of another pentomino in tile x+1
            else if(mutation == 7 && y < verticalGridSize){
                mutation = 0;
                if(field[x][y+1] < 1) {
                    return recursionSearch(field,i + 1,mutation,x,y + 1);
                }
                else{
                    y++;
                }
            }
		}
        return recursionSearch(field,i + 1,mutation,x,y);
	}
	
	/**
	 * Basic implementation of a search algorithm. It is not a brute force algorithm (it does not check all the posssible combinations)
	 * but randomly takes possible combinations and positions to find a possible solution.
	 * The solution is not necessarily the most efficient one
	 * This algorithm can be very time-consuming
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 */
    private static void basicSearch(int[][] field){
    	Random random = new Random();
    	boolean solutionFound = false;
    	
    	while (!solutionFound) {
    		solutionFound = true;
    		
    		//Empty board again to find a solution
			for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[i].length; j++) {
					field[i][j] = -1;
				}
			}
    		
    		//Put all pentominoes with random rotation/flipping on a random position on the board
    		for (int i = 0; i < input.length; i++) {
    			
    			//Choose a pentomino and randomly rotate/flip it
    			int pentID = characterToID(input[i]);
    			int mutation = random.nextInt(PentominoDatabase.data[pentID].length);
    			int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];
    		
    			//Randomly generate a position to put the pentomino on the board
    			int x;
    			int y;
    			if (horizontalGridSize < pieceToPlace.length) {
    				//this particular rotation of the piece is too long for the field
    				x=-1;
    			} else if (horizontalGridSize == pieceToPlace.length) {
    				//this particular rotation of the piece fits perfectly into the width of the field
    				x = 0;
    			} else {
    				//there are multiple possibilities where to place the piece without leaving the field
    				x = random.nextInt(horizontalGridSize-pieceToPlace.length+1);
    			}

    			if (verticalGridSize < pieceToPlace[0].length) {
    				//this particular rotation of the piece is too high for the field
    				y=-1;
    			} else if (verticalGridSize == pieceToPlace[0].length) {
    				//this particular rotation of the piece fits perfectly into the height of the field
    				y = 0;
    			} else {
    				//there are multiple possibilities where to place the piece without leaving the field
    				y = random.nextInt(verticalGridSize-pieceToPlace[0].length+1);
    			}
    		
    			//If there is a possibility to place the piece on the field, do it
    			if (x >= 0 && y >= 0) {
	    			addPiece(field, pieceToPlace, pentID, x, y);
	    		}
    		}
    		//Check whether complete field is filled
    		//
    		//
    		// TODO: To be implemented
    		//
    		//
    		if(!isFull(field)){
    			solutionFound = false;
			}

    		
    		if (solutionFound) {
    			//display the field
    			ui.setState(field); 
    			System.out.println("Solution found");
    			break;
    		}
    	}
    }
    private static boolean isFull(int[][] field){
		for(int i = 0; i < field.length; i++){
			for(int j = 0; j < field[i].length; j++){
				if(field[i][j] == -1){
					return false;
				}
			}
		}
		return true;
	}

    
	/**
	 * Adds a pentomino to the position on the field (overriding current board at that position)
	 * @param field a matrix representing the board to be filled with pentominoes
	 * @param piece a matrix representing the pentomino to be placed in the board
	 * @param pieceID ID of the relevant pentomino
	 * @param x x position of the pentomino
	 * @param y y position of the pentomino
	 */
    public static void addPiece(int[][] field, int[][] piece, int pieceID, int x, int y)
    {
        for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1)
                {
                    // Add the ID of the pentomino to the board if the pentomino occupies this square
                    field[x + i][y + j] = pieceID;
                }
            }
        }
    }

	/**
	 * Main function. Needs to be executed to start the basic search algorithm
	 */
    public static void main(String[] args)
    {
        search();
    }
}