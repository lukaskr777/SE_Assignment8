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
    public static int horizontalGridSize = 5;
    public static int verticalGridSize = 6;
    //public static int counter = 0; - this is for counting mistakes to prevent duplicate pentominos
    
    public static char[] input = {'W','X','N','L'};
    
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
	private static boolean greedyRecursion(int[][] field, int i, int mutation, int x, int y)
	{
		//need to check for pieces not yet placed
		int pentID = characterToID(input[i]);
		int[][] piece = PentominoDatabase.data[pentID][mutation];
		if(isFull(field))
		{
			ui.setState(field);
			System.out.println("Solution found");
			return true;
		}
		else
		{
			if(addPiece(field,piece,pentID,x,y)){
				addPiece(field,piece,pentID,x,y);
				if(y < horizontalGridSize - 1)
				{
					return greedyRecursion(field,i + 1,mutation,x,y + 1);
				}
				else
				{
					y = 0;
					return greedyRecursion(field,i + 1,mutation,x + 1,y);
				}
			}
			else
			{
				if(mutation < PentominoDatabase.data[pentID].length)
				{
					return greedyRecursion(field,i,mutation + 1,x,y);
				}
				else if(mutation == PentominoDatabase.data[pentID].length - 1)
				{
					mutation = 0;
					return greedyRecursion(field,i + 1,mutation,x,y);
				}
				else
				{
					deletePiece(field,pentID);
					return greedyRecursion(field,i + 1,mutation,x,y);
				}
			}
		}
	}
    private static boolean recursionSearch(int[][] field, int i, int mutation, int x, int y)
			//can there be more of the same pentomino but mutated differently?
	{
		if(i >= input.length)
		{
			return false;
		}
        int pentID = characterToID(input[i]);
		if(mutation >= PentominoDatabase.data[pentID].length)
		{
			return false;
		}
        int[][] piece = PentominoDatabase.data[pentID][mutation];
		if(isFull(field))
		{
			return true;
		}
		else
		{
			//tries to add the piece to the field
			if(addPiece(field,piece,pentID,x,y))
			{
				//if it is possible, adds the piece
				addPiece(field,piece,pentID,x,y);
				//runs the function again for the next column value
				if(y < horizontalGridSize - 1)
				{
					if(!recursionSearch(field,i + 1,mutation,x,y + 1))
					{
						return recursionSearch(field,i + 2,mutation,x,y + 1);
					}
					else
					{
						return recursionSearch(field,i + 1,mutation,x,y + 1);
					}
				}
				//if columns are exceeded, set columns to 0 and increment x by 1
				else
				{
					y = 0;
					//if you can't place the next pentomino, try the one after that
					if(!recursionSearch(field,i + 1,mutation,x + 1,y))
					{
						return recursionSearch(field,i+2,mutation,x + 1,y);
					}
					else
					{
						return recursionSearch(field,i + 1,mutation,x + 1,y);
					}
				}
				//this way, the whole array will be traversed until it is no longer possible to add pentominoes
			}
			//if it's not possible, try a different mutation of the piece
			else
			{
				//tries all of the mutations
				if(mutation < 7)
				{
					if(!recursionSearch(field,i,mutation + 1,x,y))
					{
						return recursionSearch(field,i + 1,mutation,x,y);
					}
					else
					{
						return recursionSearch(field,i,mutation + 1,x,y);
					}
				}
				//if none fit, tries a different pentomino
				else if(mutation == 7)
				{
					mutation = 0;
					if(!recursionSearch(field,i + 1,mutation,x,y))
					{
						return recursionSearch(field,i+2,mutation,x,y);
					}
					else
					{
						return recursionSearch(field,i + 1,mutation,x,y);
					}
				}
				//if no pentomino and no mutation of pentominoes fit, return false
				else
				{
					return false;
                }
			}
		}
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
    public static boolean addPiece(int[][] field, int[][] piece, int pieceID, int x, int y)
    {
        for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1)
                {
                	if(field[x + i][y + j] < 0)
                	{
						// Add the ID of the pentomino to the board if the pentomino occupies this square
						field[x + i][y + j] = pieceID;
					}
                	else
					{
						return false;
					}
                }
            }
        }
        return true;
    }
    public static void deletePiece(int[][] field, int pieceID)
	{
		for(int i = 0; i < field.length; i++)
		{
			for(int j = 0; j < field[i].length; j++)
			{
				if(field[i][j] == pieceID)
				{
					field[i][j] = -1;
				}
			}
		}
	}

	/**
	 * Main function. Needs to be executed to start the basic search algorithm
	 */
    public static void main(String[] args)
    {
    	/*for(int i = 0; i < args[0].length(); i++){//input commands in the format "WXFN"
			input[i] = args[0].charAt(i);
		}
    	if(args[1] != null && args[2] != null) {
			horizontalGridSize = Integer.parseInt(args[1]);
			verticalGridSize = Integer.parseInt(args[2]);
		}
    	else{
    		horizontalGridSize = 5;
    		verticalGridSize = 12;
		}*/
		int[][] field = new int[horizontalGridSize][verticalGridSize];
		System.out.println(recursionSearch(field,0,0,0,0));
    }
}