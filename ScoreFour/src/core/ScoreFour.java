/**
 * @author Max Fisher (mfis1267)
 * @version 1.4
 */
package core;

import util.Coordinate;

/*
 * ScoreFour Class
 * 
 * This class handles execution of the game.  
 * The constructor accepts two player instances, which will make moves in the game.
 * The ScoreFour class will deal with those moves and determine whether a move is legal
 * (see isValidCoordinate, isValidPlacement and place methods), and after each move
 * whether the game is over (see isGameOver).
 * 
 * In the comments below, i is the row number, j is the column number, and k is the height.
 */
public class ScoreFour {
	private static final int boardSize = 4;
	private final Player p1, p2;
	protected final char[][][] board;
	private int moves;
	private final int[][] rowCoords; // coordinates of any four given pieces in a row, used to check for winning moves
	private int[][] winningCoords; //stores coordinates of pieces in the winning row
	
	/**
	 * #1
	 * 
	 * Initialize all instance variables (e.g. players, board ... )
	 * @param player1 - first player
	 * @param player2 - second player
	 */
	public ScoreFour(Player player1, Player player2) {
		p1 = player1;
		p2 = player2;
		board = new char[boardSize][boardSize][boardSize];
		moves = 0;
		rowCoords = new int[boardSize][3];
		winningCoords = null;
	}
	
	/**
	 * #2
	 * 
	 * Check if the coordinates are valid
	 * 
	 * Coordinates are valid if and only if they are:
	 * 		* greater than or equal to 0
	 * and	* less than boardSize
	 * 
	 * @param coords[0] - i coordinate
	 * @param coords[1] - j coordinate
	 * @param coords[2] - k coordinate
	 * @return true - if coordinates are valid
	 * @return false - if coordinates are invalid
	 */
	protected boolean isValidCoordinate(int ... coords) {
		boolean isValid = true;
		for (int i : coords) {
			if (!(i >= 0 && i < boardSize)) // if i is not within the bounds of the board
				isValid = false;
		}
		return isValid;
	}
	
	/**
	 * #3
	 * 
	 * Get the value of the board at (i, j, k).
	 * 
	 * The value of the board is a single char: use 'w' for WHITE, representing player 1, 
	 * and 'b' for BLACK, representing player 2.
	 * 
	 * return -1 if the coordinates are invalid
	 * return 0 (without quotes) if the board is empty at the given coordinates.
	 * 
	 * NOTE: All other methods require this method to be working for testing.
	 * 
	 * @param coords[0] - i coordinate
	 * @param coords[1] - j coordinate
	 * @param coords[2] - k coordinate
	 * @return value
	 */
	protected char getValue(int ... coords) {
		if (isValidCoordinate(coords))
			return board[coords[0]][coords[1]][coords[2]]; // will return (char) 0 by default if location is empty
		else return (char) -1;
	}
	
	/**
	 * #3
	 * 
	 * Set the value of the board at (i, j, k)
	 * 
	 * if the coordinates are invalid, do nothing
	 * 
	 * NOTE: All other methods require this method to be working for testing.
	 * 
	 * @param i - i coordinate
	 * @param j - j coordinate
	 * @param k - k coordinate
	 * @param value - the new value
	 */
	protected void setValue(int i, int j, int k, char value) {	
		if (isValidCoordinate(i, j, k))
			board[i][j][k] = value;
	}
	
	/**
	 * #4
	 * 
	 * Check if the placement is Valid
	 * 
	 * The placement is valid if and only if:
	 * 		* the coordinates are valid
	 * and	* the column is not full
	 * 
	 * @param placement - the position of the placement
	 * @return true - if the placement is valid
	 * @return false - if the placement is invalid
	 */
	protected boolean isValidPlacement(Coordinate placement) {
		if (placement == null)
			return false;
		int i = placement.getI();
		int j = placement.getJ();
		if ((isValidCoordinate(i, j, 0)) && board[i][j][boardSize - 1] == 0)
			// the placement must be valid if the top slot of the column is empty
			return true;
		else return false;
	}
	
	
	/**
	 * #5
	 * 
	 * Place a value at the coordinate
	 * 
	 * If the position is invalid, ignore it.
	 *  
	 * @param position - the position of the placement
	 * @param value - the value ('b'/'w')
	 */
	protected void place(Coordinate placement, char value) {
		if (isValidPlacement(placement) && (value == 'w' || value == 'b')) {
			int i = placement.getI();
			int j = placement.getJ();
			int k = findK(i, j);
			setValue(i, j, k, value);
		}
	}
	/**
	 * Same as above but also places the counter in the specified array (of same size as this board)
	 * @param position the position of the placement
	 * @param value the value ('b'/'w')
	 * @param boardCopy the secondary array to place the piece in. 
	 */
	protected void place(Coordinate placement, char value, char[][][] boardCopy) {
		if (isValidPlacement(placement) && (value == 'w' || value == 'b')) {
			int i = placement.getI();
			int j = placement.getJ();
			int k = findK(i, j);
			setValue(i, j, k, value);
			boardCopy[i][j][k] = value;
		}
	}
	
	/**
	 * #5.5
	 * 
	 * Find the correct height to place the value at
	 * 
	 * If the position is invalid, ignore it
	 * Assumes coordinate is valid, as it was checked already by place()
	 * 
	 * @param position - the position of the placement
	 */
	protected int findK(int i, int j) {
		int k = 0;
		while (getValue(i, j, k) == 'w' || getValue(i, j, k) == 'b')
			k++;
		return k;
	}
	
	/**
	 * #6
	 * 
	 * Check if the game is over.
	 * 
	 * The game is over if and only if:
	 * 		* 64 moves have been attempted (valid or invalid) and neither player has 
	 * 		won;
	 * or		* a player has won.
|	 * 
	 * A player has won if and only if:
	 * 		* they have boardSize pieces in a row (horizontal, vertical or diagonal)
	 * 	
	 * @return true if the game is over, otherwise false
	 */
	protected boolean isGameOver() {
		if (moves >= boardSize*boardSize *boardSize || gameWon())
			return true;
		else return false;
	}
	
	/**
	 * #6.25
	 * 
	 * Check the board for winning moves.
	 * 
	 * First checks straight lines in each dimension, then both diagonals in each dimension, then the four major diagonals
	 */
	protected boolean gameWon() {
		resetRow();
		// check i direction 
		for (int k = 0; k < boardSize; k++) {
			for (int j = 0; j < boardSize; j++) {
				resetRow();
				for (int i = 0; i < boardSize; i++) {
					rowCoords[i][0] = i;
					rowCoords[i][1] = j;
					rowCoords[i][2] = k;
				}				
				if (rowWins(rowCoords))
					return true;
			}
		}
		// check j direction 
		for (int k = 0; k < boardSize; k++) {
			for (int i = 0; i < boardSize; i++) {
				resetRow();
				for (int j = 0; j < boardSize; j++) {
					rowCoords[j][0] = i;
					rowCoords[j][1] = j;
					rowCoords[j][2] = k;
				}
				if (rowWins(rowCoords))
					return true;
			}
		}
		// check k direction 
		for (int j = 0; j < boardSize; j++) {
			for (int i = 0; i < boardSize; i++) {
				resetRow();
				for (int k = 0; k < boardSize; k++) {
					rowCoords[k][0] = i;
					rowCoords[k][1] = j;
					rowCoords[k][2] = k;
				}
				if (rowWins(rowCoords))
					return true;
			}
		}
		//check i-j diagonals
		for (int k = 0; k < boardSize; k++) {
			resetRow();
			for (int i = 0, j = 0; i < boardSize && j < boardSize; i++, j++) {
				rowCoords[i][0] = i;
				rowCoords[i][1] = j;
				rowCoords[i][2] = k;
			}
			if (rowWins(rowCoords))
				return true;
			
			resetRow();
			for (int i = boardSize - 1, j = 0; i >= 0 && j < boardSize; i--, j++) {
				rowCoords[i][0] = i;
				rowCoords[i][1] = j;
				rowCoords[i][2] = k;
			}
			if (rowWins(rowCoords))
				return true;
		}
		//check i-k diagonals
		for (int j = 0; j < boardSize; j++) {
			resetRow();
			for (int i = 0, k = 0; i < boardSize && k < boardSize; i++, k++) {
				rowCoords[i][0] = i;
				rowCoords[i][1] = j;
				rowCoords[i][2] = k;
			}
			if (rowWins(rowCoords))
				return true;
			
			resetRow();
			for (int i = boardSize - 1, k = 0; i >= 0 && k < boardSize; i--, k++) {
				rowCoords[i][0] = i;
				rowCoords[i][1] = j;
				rowCoords[i][2] = k;
			}
			if (rowWins(rowCoords))
				return true;
		}
		//check j-k diagonals
		for (int i = 0; i < boardSize; i++) {
			resetRow();
			for (int j = 0, k = 0; j < boardSize && k < boardSize; j++, k++) {
				rowCoords[j][0] = i;
				rowCoords[j][1] = j;
				rowCoords[j][2] = k;
			}
			if (rowWins(rowCoords))
				return true;
			
			resetRow();
			for (int j = boardSize - 1, k = 0; j >= 0 && k < boardSize; j--, k++) {
				rowCoords[j][0] = i;
				rowCoords[j][1] = j;
				rowCoords[j][2] = k;
			}
			if (rowWins(rowCoords))
				return true;
		}
		//check major diagonals
		resetRow();
		for (int i = 0, j = 0, k = 0; i < boardSize && j < boardSize && k < boardSize; i++, j++, k++) {
			rowCoords[i][0] = i;
			rowCoords[i][1] = j;
			rowCoords[i][2] = k;
		}
		if (rowWins(rowCoords))
			return true;
		
		resetRow();
		for (int i = boardSize - 1, j = 0, k = 0; i >= 0 && j < boardSize && k < boardSize; i--, j++, k++) {
			rowCoords[i][0] = i;
			rowCoords[i][1] = j;
			rowCoords[i][2] = k;
		}
		if (rowWins(rowCoords))
			return true;
		
		resetRow();
		for (int i = 0, j = boardSize - 1, k = 0; i < boardSize && j >= 0 && k < boardSize; i++, j--, k++) {
			rowCoords[i][0] = i;
			rowCoords[i][1] = j;
			rowCoords[i][2] = k;
		}
		if (rowWins(rowCoords))
			return true;
		
		resetRow();
		for (int i = boardSize - 1, j = boardSize - 1, k = 0; i >= 0 && j >= 0 && k < boardSize; i--, j--, k++) {
			rowCoords[i][0] = i;
			rowCoords[i][1] = j;
			rowCoords[i][2] = k;
		}
		if (rowWins(rowCoords))
			return true;
		
		else return false;
	}
	/**
	 * #6.5
	 * 
	 * Resets the rowCoords array to accept a new row
	 */
	protected void resetRow() {
		for (int i = 0; i < boardSize; i++)
			rowCoords[i] = new int[3];
	}
	
	/**
	 * #6.75
	 * 
	 * Checks each coordinate in the rowCoords array for the same piece
	 */
	protected boolean rowWins(int[][] rowCoords) {
		char first = getValue(rowCoords[0]);
		if (first == 'w' || first == 'b') {
			for (int[] coord : rowCoords) {
				if (getValue(coord) != first) // makes sure every other character is the same as the first
					return false; // no need to check the other characters
			}
			// if code reaches this point then the row must have won
			winningCoords = rowCoords; //winning coords are recorded
			return true;
		}
		else return false;
	}
	
	private boolean boardEquals(char[][][] otherBoard) {
		boolean isEqual = true;
		for (int k = 0; k < boardSize; k++) {
			for (int j = 0; j < boardSize; j++) {
				for (int i = 0; i < boardSize; i++) {
					if (board[i][j][k] != otherBoard[i][j][k])
						isEqual = false;
				}
			}
		}
		return isEqual;
	}
	private char[][][] boardCopy() {
		char[][][] copy = new char[boardSize][boardSize][boardSize];
		for (int k = 0; k < boardSize; k++) {
			for (int j = 0; j < boardSize; j++) {
				for (int i = 0; i < boardSize; i++)
					copy[i][j][k] = board[i][j][k];
			}
		}
		return copy;
	}
	
	/**
	 * Run the game and return the winning player
	 * 		1. Notify players of new opponent (use the opponent's class' canonical name)
	 * 		2. Run the game until the game is over
	 * 		3. Notify players of outcome (possible outcomes are WIN, DRAW, LOSS
	 * 		4. Return winning player (return null for a draw)
	 * 
	 * 
	 * @return winning player
	 * @return null - if there's a draw
	 */
	public Player run() {
		p1.notifyNewOpponent(p2.getClass().getCanonicalName());
		p2.notifyNewOpponent(p1.getClass().getCanonicalName());
		char[][][] boardClone;
		char winningPiece = 0;
		do {
			boardClone = boardCopy();
			place(p1.getNextMove(boardClone, 'w'), 'w', boardClone);
			moves++;
			if (!boardEquals(boardClone)) {// then the player must have cheated
				System.out.println("Player 1 cheated!");
				winningPiece = 'b';
				break;
			}
			if (isGameOver())
				break; // check if the game is won after each move
			boardClone = boardCopy();
			place(p2.getNextMove(boardClone, 'b'), 'b', boardClone);
			moves++;
			if (!boardEquals(boardClone)) {
				System.out.println("Player 2 cheated!");
				winningPiece = 'w';
				break;
			}
		}
		while(!isGameOver());
		if (winningCoords != null) {
			winningPiece = getValue(winningCoords[0]); // gets the winning colour
		}
		switch (winningPiece) {
		case 'w':
			p1.notifyOutcome("WIN");
			p2.notifyOutcome("LOSS");
			return p1;
		case 'b':
			p1.notifyOutcome("LOSS");
			p2.notifyOutcome("WIN");
			return p2;
		default:
			p1.notifyOutcome("DRAW");
			p2.notifyOutcome("DRAW");
			return null;
		}
	}
	/**
	 * same as run() but prints board out after each turn and highlights winning row
	 * @param debug whether to run the debug mode
	 * @return same as run()
	 * @see #run()
	 */
	public Player run(boolean debug) {
		if (!debug)
			return run();
		p1.notifyNewOpponent(p2.getClass().getCanonicalName());
		p2.notifyNewOpponent(p1.getClass().getCanonicalName());
		char[][][] boardClone;
		char winningPiece = 0;
		do {
			boardClone = boardCopy();
			long timeBefore = System.nanoTime();
			Coordinate p1Turn = p1.getNextMove(boardClone, 'w');
			long timeTaken = (System.nanoTime() - timeBefore)/1000;
			place(p1Turn, 'w', boardClone);
			moves++;
			printBoard(p1Turn);
			System.out.println("Move took " + timeTaken + " us\n");
			if (timeTaken > 20000) {
			System.out.println("Too Long - player 1");
			}
			if (!boardEquals(boardClone)) {// then the player must have cheated
				System.out.println("Player 1 cheated!");
				winningPiece = 'b';
				break;
			}
			if (isGameOver())
				break; // check if the game is won after each move
			boardClone = boardCopy();
			timeBefore = System.nanoTime();
			Coordinate p2Turn = p2.getNextMove(boardClone, 'b');
			timeTaken = (System.nanoTime() - timeBefore)/1000;
			place(p2Turn, 'b', boardClone);
			moves++;
			printBoard(p2Turn);
			System.out.println("Move took " + timeTaken + " us\n");
			if (timeTaken > 20000) {
				System.out.println("Too Long - player 2");
			}
			if (!boardEquals(boardClone)) {
				System.out.println("Player 2 cheated!");
				winningPiece = 'w';
				break;
			}
		}
		while(!isGameOver());
		if (winningCoords != null) {
			winningPiece = getValue(winningCoords[0]); // gets the winning colour
			boardWinner(winningPiece, rowCoords);
			printBoard();
		}
		switch (winningPiece) {
		case 'w':
			p1.notifyOutcome("WIN");
			p2.notifyOutcome("LOSS");
			return p1;
		case 'b':
			p1.notifyOutcome("LOSS");
			p2.notifyOutcome("WIN");
			return p2;
		default:
			p1.notifyOutcome("DRAW");
			p2.notifyOutcome("DRAW");
			return null;
		}
	}
	/**
	 * prints out the board's current state
	 */
	private void printBoard() {
		for (int k = boardSize - 1; k >= 0; k--) {
			for (int j = boardSize - 1; j >= 0; j--) {
				for (int offset = j; offset < boardSize - 1; offset++)
					System.out.print(" ");
				for (int i = 0; i < boardSize; i++) {
					if (getValue(i, j, k) == 0)
						System.out.print("-");
					else System.out.print(getValue(i, j, k));
					for (int offset = 0; offset < boardSize - 1; offset++)
						System.out.print(" ");
				}
				System.out.println();
			}
			System.out.println("\n");
		}
		System.out.println("*****************\n");
	}
	/**
	 * prints out board's current state, highlighting the last move
	 */
	private void printBoard(Coordinate lastMove) {
		if (lastMove == null) {
			printBoard();
			return;
		}
		for (int k = boardSize - 1; k >= 0; k--) {
			for (int j = boardSize - 1; j >= 0; j--) {
				for (int offset = j; offset < boardSize - 1; offset++)
					System.out.print(" ");
				for (int i = 0; i < boardSize; i++) {
					char c = getValue(i, j, k);
					if (c == 0)
						System.out.print("-");
					
					else {
						int lastI = lastMove.getI();
						int lastJ = lastMove.getJ();
						int lastK = findK(lastI, lastJ) - 1;
						if (i == lastI && j == lastJ && k == lastK) {
							if (c == 'w')
								System.out.print('W');
							else System.out.print('B');
						}
						else System.out.print(c);
					}
					for (int offset = 0; offset < boardSize - 1; offset++)
						System.out.print(" ");
				}
				System.out.println();
			}
			System.out.println("\n");
		}
		System.out.println("*****************\n");
	}
	/**
	 * makes characters in the winning row capital so they can be identified during printing
	 */
	private void boardWinner(char winningPiece, int[][] rowCoords) {
		if (winningPiece == 'w' || winningPiece == 'b') {
			for (int[] coords : winningCoords)
				setValue(coords[0], coords[1], coords[2], (char) (winningPiece - 32)); // changes to capital
		}
	}
}
