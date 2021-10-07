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

    public static char[] input = {'W','X','N','L','Y','P'};
    
    //Static UI class to display the board
    public static UI ui = new UI(horizontalGridSize, verticalGridSize, 50);

	/**
	 * "removes" a certain value from the tempInput array, which is at the beginning equal to the input array
	 * @param input is the input array given
	 * @param value is the value to be removed from the array
	 */
    public static char[] removeInputChar(char[] input, char value)
	{
		char[] tempInput = new char[input.length - 1];
		for(int i = 0, j = 0; i < input.length; i++)
		{
			if(input[i] != value)
			{
				tempInput[j++] = input[i];
			}
		}
		return tempInput;
	}

    /**
     * "adds" a certain value to the input array
     * @param input is the array to which the value will be added
     * @param value is the value to be added
     * @return an array which includes the desired value along with the values in the input array
     */
	public static char[] addInputChar(char[] input, char value)
	{
		char[] tempInput = new char[input.length + 1];
		for(int i = 0; i < input.length; i++)
		{
			tempInput[i+1] = input[i];
		}
		tempInput[0] = value;
		return tempInput;
	}

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
        recursiveSearch(field,input);
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

	/**
	 * Gets as input the pentID and returns a character, useful for removing values from the input array
	 * @param pentID is the pentomino ID that is being used
	 * @return the character corresponding to the pentomino ID
	 */
    private static char idToCharacter(int pentID)
	{
		char character = ' ';
		if (pentID == 0) {
			character = 'X';
		} else if (pentID == 1) {
			character = 'I';
		} else if (pentID == 2) {
			character = 'Z';
		} else if (pentID == 3) {
			character = 'T';
		} else if (pentID == 4) {
			character = 'U';
		} else if (pentID == 5) {
			character = 'V';
		} else if (pentID == 6) {
			character = 'W';
		} else if (pentID == 7) {
			character = 'Y';
		} else if (pentID == 8) {
			character = 'L';
		} else if (pentID == 9) {
			character = 'P';
		} else if (pentID == 10) {
			character = 'N';
		} else if (pentID == 11) {
			character = 'F';
		}
		return character;
	}

    /**
     * The recursive search with a basic pruning algorithm implemented, explained in method
     * @param field is a 2-dimensional array to be filled with tiles
     * @param inputField is a 1-dimensional array used for tracking what tiles can still be placed on the board
     */
	private static void recursiveSearch(int[][] field,char[] inputField)
	{
		//if the field is full, print the board state (assuming no overlap this is the correct solution)
		if(isFull(field)){
			ui.setState(field);
			System.out.println("Solution found");
			return;
		}
		//goes through all indexes in the list of inputs
		for(int i = 0; i < inputField.length; i++)
		{
			int pentID = characterToID(inputField[i]);//uses the index to set the pentomino
			//goes through all of the permutations for each pentomino, accounting for less mutation possibilities
			for(int j = 0; j < PentominoDatabase.data[pentID].length; j++)
			{
				//creates the piece based on the mutations and pentomino ID's dicatated by the previous loop
				int[][] piece = PentominoDatabase.data[pentID][j];
				//goes through all x values and tries to place a pentomino there except when the height would exceed
				for(int k = 0; k < field.length - PentominoDatabase.data[pentID][j].length; k++)// the number of rows
				{
					//same as above, but for y values
					for(int l = 0; l < field[k].length - PentominoDatabase.data[pentID][j][0].length; l++)
					{
                        if (addPiece(field,piece,pentID,k,l))
                        {
                            //actually adds the piece
                            addPiece(field,piece,pentID,k,l);

                            //before starting recursion it needs to be checked whether the solution is even feasible (pruning)
                            if(smallTilesValid(field))
                            {
                                //runs the whole function again and removing the pentomino added at the same time
                                recursiveSearch(field, removeInputChar(inputField, idToCharacter(pentID)));

                                //if the branch is complete and a solution has not been found, try again with the pentomino added
                                //back to the list, where the function runs again but using a different set of pentominos
                                //not yet placed.  This method also deletes all pentID tiles by forming a square
								//starting at the x and y coordinates of the places tile, ALLOWING FOR DUPLICATES,
								//since it just deletes the most recent pentomino.
                                deletePiece(field, pentID,k,l);
                            }
                        }
                    }
				}
			}
		}
	}

    /**
     * Method checks entire field array, and for each tile checks how many consecutive empty spaces there are
     * This method is used in conjunction with the recursion search to prune solutions with emtpy tiles next to each
     * other that can not lead to any solutions
     * @param field is the field to be checked, in this case the field used for the recursion search
     * @return false if such a piece exists, true if not
     */
	public static boolean smallTilesValid(int[][] field)
    {
        for(int i = 0; i < field.length; i++)
        {
            for(int j = 0; j < field[i].length; j++)
            {
                if(floodFill(field,i,j,0,false) % 5 != 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method checks all tiles near one to see if they are all empty for pruning purposes
     * @param field is the field used in the recursion search
     * @param x is the x value of the tile being checked
     * @param y is the y value of the tile being checked
     * @param counter is the counter to track the number of empty spaces next to each other
     * @return the number of squares next to each other for the given x and y
     */
    public static int floodFill(int[][] field,int x,int y,int counter,boolean visited)
    {
        //checks tile to the right of the specified tile
        if(y < field.length && field[x][y + 1] < 0 && !visited)
        {
            counter++;
            floodFill(field,x,y + 1,counter,true);
        }
        //checks tile above the specified tile
        if(x < field.length && field[x + 1][y] < 0 && !visited)
        {
            counter++;
            floodFill(field,x + 1,y,counter,true);
        }
        //checks tile to the left of the specified tile
        if(y > 0 && field[x][y - 1] < 0 && !visited)
        {
            counter++;
            floodFill(field,x,y - 1,counter,true);
        }
        //checks tile below the specified tile
        if(x > 0 && field[x - 1][y] < 0 && !visited)
        {
            counter++;
            floodFill(field,x - 1,y,counter,true);
        }
        return counter;
    }

    /**
     * Failed recursion method #2.  Did not account for all solutions, as an array of which pieces are left to be placed
     * was not used, and as such did not include the option to backtrack.  An unsuccessful attempt at the backtracking
     * used the deletePiece method and recursion, however it did not add the piece it deleted back to the list of pieces
     * to be added.
     * In addition to this, the method would try to place a pentomino on top of another at the very beginning, and never
     * iterate the y or x values if it was not possible, it would simply try either rotating the pentomino or changing it,
     * and thus the method was abandoned after changing a few things past that point.
     * @param field is the input field on which the recursion is to be performed
     * @param i is the number that defines which pentomino to use
     * @param mutation is the mutation of the pentomino
     * @param x is the x value where to place the pentomino
     * @param y is the y value of the pentomino
     */
	private static void greedyRecursion(int[][] field, int i, int mutation, int x, int y)
	{
		int pentID = characterToID(input[i]);
		int[][] piece = PentominoDatabase.data[pentID][mutation];
		if(isFull(field))
		{
			ui.setState(field);
			System.out.println("Solution found");
		}
		else
		{
			if(addPiece(field,piece,pentID,x,y)){
				addPiece(field,piece,pentID,x,y);
				removeInputChar(input,idToCharacter(pentID));
			}
			else
			{
				if(y < horizontalGridSize - 1)
				{
					greedyRecursion(field,i + 1,mutation,x,y + 1);
				}
				else if(y == horizontalGridSize - 1)
				{
					y = 0;
					greedyRecursion(field,i + 1,mutation,x + 1,y);
				}
				else if(mutation < PentominoDatabase.data[pentID].length - 1)
				{
					greedyRecursion(field,i,mutation + 1,x,y);
				}
				else if(mutation == PentominoDatabase.data[pentID].length - 1)
				{
					mutation = 0;
					greedyRecursion(field,i + 1,mutation,x,y);
				}
				else
				{
					deletePiece(field,pentID,x,y);
					greedyRecursion(field,i + 1,mutation,x,y);
				}
			}
		}
	}

    /**
     * Failed recursion attempt #1.  Works somewhat, but extremely inefficient and attempts to work with the fact that
     * duplicate pentominoes are allowed.  Method was quickly abandoned but took very long to produce, which caused many
     * problems and delays in the planned schedule of completing tasks.
     * @param field is the field where the recursion was to be performed
     * @param i dictates which pentomino to use
     * @param mutation is the mutation of the pentomino
     * @param x is the x value where to place the pentomino
     * @param y is the y value where to place the pentomino
     * @return a boolean if the solution was possible
     */
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
    public static void deletePiece(int[][] field, int pieceID,int x,int y)
	{
		for(int i = x; i < field.length; i++)
		{
			for(int j = y; j < field[i].length; j++)
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
		search();
    }
}