/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

 import java.util.Random;
 import java.util.Scanner;

/**
 * This class includes the methods to support the search of a solution.
 */
public class Search
{
	public static int getHorizontal()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the preferred horizontal grid size: ");
		return scanner.nextInt();
	}

	// get the vertical grid size from user
	public static int getVertical()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the preferred vertical grid size: ");
		return scanner.nextInt();
	}

	// get the characters that should be used from user
	public static char[] getCharacters(){
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the characters that you want to use: ");
		String character = scanner.nextLine();
		char[] inputChar = new char[character.length()];

		for(int i=0; i<character.length(); i++)
		{
			inputChar[i] = character.charAt(i);
		}
		return inputChar;
	}
	public static int counter;
	public static int solutionCount = 0;
    public static int horizontalGridSize = getHorizontal();
    public static int verticalGridSize = getVertical();

    public static char[] input = getCharacters();
    
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
    public static void search() throws InterruptedException {
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
        //Start the search
		//basicSearch(field);
        pieceByPiece(field,input);
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
	 * Checks if the pentomino to be placed does not overlap with any other pentomino.
	 * @param field is the field where the pentomino will be placed
	 * @param x is the x coordinate of the tile examined
	 * @param y is the y coordinate of the tile examined
	 * @param pieceToAdd is the piece to add
	 * @return true if the piece is able to be placed, and false if vice versa
	 */
	private static boolean checkIfValid(int[][] field,int x,int y,int[][] pieceToAdd)
	{
		for(int i = 0; i < pieceToAdd.length; i++)
		{
			for(int j = 0; j < pieceToAdd[i].length; j++){
				if(pieceToAdd[i][j] == 1)
				{
					if(field[x + i][y + j] != -1)
					{
						return false;
					}
				}
			}
		}
		return true;
	}

    /**
     * The recursive search with a basic pruning algorithm implemented, explained in method
     * @param field is a 2-dimensional array to be filled with tiles
     * @param inputField is a 1-dimensional array used for tracking what tiles can still be placed on the board
     */
	private static void pieceByPiece(int[][] field,char[] inputField) throws InterruptedException {
		//if the field is full, print the board state (assuming no overlap this is the correct solution)
		if(isFull(field)){
			solutionCount++;
			ui.setState(field);
			return;
		}
		//goes through all indexes in the list of inputs
		for(int i = 0; i < inputField.length; i++)
		{
			if(i == 0 && characterToID(inputField[i]) == 'X')//prunes the branching with X at the beginning
			{
				i++;
			}
			int pentID = characterToID(inputField[i]);//uses the index to set the pentomino
			//goes through all of the permutations for each pentomino, accounting for less mutation possibilities
			for(int j = 0; j < PentominoDatabase.data[pentID].length; j++)
			{
				//creates the piece based on the mutations and pentomino ID's dicatated by the previous loop
				int[][] piece = PentominoDatabase.data[pentID][j];
				//goes through all x values and tries to place a pentomino there except when the height would exceed
				for(int k = 0; k < field.length - PentominoDatabase.data[pentID][j].length + 1; k++)// the number of rows
				{
					//same as above, but for y values
					for(int l = 0; l < field[k].length - PentominoDatabase.data[pentID][j][0].length + 1; l++)
					{
                        if(checkIfValid(field,k,l,piece))
                        {
                            //actually adds the piece
							//ui.setState(field);
                            addPiece(field,piece,pentID,k,l);
                            //Thread.sleep(1000);
                            //before starting recursion it needs to be checked whether the solution is even feasible (pruning)
							//if(smallTilesValid(field))
							//{
								//System.out.println("Small tiles not present");
								//runs the whole function again and removing the pentomino added at the same time
								inputField = removeInputChar(inputField, idToCharacter(pentID));
								pieceByPiece(field, inputField);
								//Thread.sleep(1000);
								//if the branch is complete and a solution has not been found, try again with the pentomino added
								//back to the list, where the function runs again but using a different set of pentominos
								//not yet placed.  This method also deletes all pentID tiles by forming a square
								//starting at the x and y coordinates of the places tile, ALLOWING FOR DUPLICATES,
								//since it just deletes the most recent pentomino.
								deletePiece(field, pentID, k, l);
								//ui.setState(field);
								inputField = addInputChar(inputField, idToCharacter(pentID));
							//}
                        }
                    }
				}
			}
		}
	}

	/**
	 * Simply prints the number of solutions :)
	 * @param count is the solution count
	 */
	private static void solutionCheck(int count)
	{
		if(count == 0)
		{
			System.out.println("No solutions.");
		}
		else
		{
			System.out.println("Number of solutions is: " + count);
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
    	boolean[][] floodArray = new boolean[field.length][field[0].length];
    	for(int i = 0; i < field.length; i++)
    	{
    		for(int j = 0; j < field[i].length; j++)
    		{
				floodArray[i][j] = false;
			}
		}
        for(int i = 0; i < field.length; i++)
        {
            for(int j = 0; j < field[i].length; j++)
            {
            	if(field[i][j] == -1)
            	{
					counter = 0;
					floodFill(field, i, j, floodArray);
					System.out.println(counter);
					if (counter % 5 != 0)
					{
						return false;
					}
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
     * @return the number of squares next to each other for the given x and y
     */
    public static void floodFill(int[][] field,int x,int y,boolean[][] visited)
    {

        //checks tile to the right of the specified tile
        if(y < field.length - 1 && field[x][y + 1] < 0 && !visited[x][y + 1])
		{
            counter = counter + 1;
            visited[x][y + 1] = true;
            floodFill(field,x,y + 1,visited);
        }
        //checks tile above the specified tile
        if(x < field.length - 1 && field[x + 1][y] < 0 && !visited[x + 1][y])
        {
			counter = counter + 1;
			visited[x + 1][y] = true;
            floodFill(field,x + 1,y,visited);
        }
        //checks tile to the left of the specified tile
        if(y > 0 && field[x][y - 1] < 0 && !visited[x][y - 1])
        {
			counter = counter + 1;
			visited[x][y - 1] = true;
            floodFill(field,x,y - 1,visited);
        }
        //checks tile below the specified tile
        if(x > 0 && field[x - 1][y] < 0 && !visited[x - 1][y])
        {
			counter = counter + 1;
			visited[x - 1][y] = true;
            floodFill(field,x - 1,y,visited);
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
                	if(field[x + i][y + j] == -1)
                	{
						// Add the ID of the pentomino to the board if the pentomino occupies this square
						field[x + i][y + j] = pieceID;
					}
                }
            }
        }
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
    public static void main(String[] args) throws InterruptedException {
		search();
		solutionCheck(solutionCount);
    }
}