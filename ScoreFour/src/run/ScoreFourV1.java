/**
 * @author Max Fisher (mfis1267)
 */
package run;

import core.Player;
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
public class ScoreFourV1 {
	private static final int boardSize = 4;
	private final Player p1, p2;
	protected final char[][][] board;
	private int moves;
	private final char[] rowOfPieces; // any four given pieces in a row, used to check for winning moves
	private char winningPiece; // colour of the winning piece
	
	/**
	 * #1
	 * 
	 * Initialize all instance variables (e.g. players, board ... )
	 * @param player1 - first player
	 * @param player2 - second player
	 */
	public ScoreFourV1(Player player1, Player player2) {
		p1 = player1;
		p2 = player2;
		board = new char[boardSize][boardSize][boardSize];
		moves = 0;
		rowOfPieces = new char[boardSize];
		winningPiece = 0;
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
	 * @param i - i coordinate
	 * @param j - j coordinate
	 * @param k - k coordinate
	 * @return true - if coordinates are valid
	 * @return false - if coordinates are invalid
	 */
	protected boolean isValidCoordinate(int i, int j, int k) {
		if ((i >= 0 && i < boardSize) && (j >= 0 && j < boardSize) && (k >= 0 && k < boardSize))
			return true;
		else return false;
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
	 * @param i - i coordinate
	 * @param j - j coordinate
	 * @param k - k coordinate
	 * @return value
	 */
	protected char getValue(int i, int j, int k) {
		if (isValidCoordinate(i, j, k))
			return board[i][j][k]; // will return (char) 0 by default if array is empty
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
		int i = placement.getI();
		int j = placement.getJ();
		if ((isValidCoordinate(i, j, 0)) && board[i][j][boardSize - 1] == 0)
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
		int i, j, k;
		resetRow();
		// check i direction 
		for (k = 0; k < boardSize; k++) {
			for (j = 0; j < boardSize; j++) {
				resetRow();
				for (i = 0; i < boardSize; i++)
					rowOfPieces[i] = getValue(i, j, k);
				if (rowWins())
					return true;
			}
		}
		// check j direction 
		for (k = 0; k < boardSize; k++) {
			for (i = 0; i < boardSize; i++) {
				resetRow();
				for (j = 0; j < boardSize; j++)
					rowOfPieces[j] = getValue(i, j, k);
				if (rowWins())
					return true;
			}
		}
		// check k direction 
		for (j = 0; j < boardSize; j++) {
			for (i = 0; i < boardSize; i++) {
				resetRow();
				for (k = 0; k < boardSize; k++)
					rowOfPieces[k] = getValue(i, j, k);
				if (rowWins())
					return true;
			}
		}
		//check i-j diagonals
		for (k = 0; k < boardSize; k++) {
			resetRow();
			for (i = 0, j = 0; i < boardSize && j < boardSize; i++, j++)
				rowOfPieces[i] = getValue(i, j, k);
			if (rowWins())
				return true;
			resetRow();
			for (i = boardSize - 1, j = 0; i >= 0 && j < boardSize; i--, j++)
				rowOfPieces[i] = getValue(i, j, k);
			if (rowWins())
				return true;
		}
		//check i-k diagonals
		for (j = 0; j < boardSize; j++) {
			resetRow();
			for (i = 0, k = 0; i < boardSize && k < boardSize; i++, k++)
				rowOfPieces[i] = getValue(i, j, k);
			if (rowWins())
				return true;
			resetRow();
			for (i = boardSize - 1, k = 0; i >= 0 && k < boardSize; i--, k++)
				rowOfPieces[i] = getValue(i, j, k);
			if (rowWins())
				return true;
		}
		//check j-k diagonals
		for (i = 0; i < boardSize; i++) {
			resetRow();
			for (j = 0, k = 0; j < boardSize && k < boardSize; j++, k++)
				rowOfPieces[j] = getValue(i, j, k);
			if (rowWins())
				return true;
			resetRow();
			for (j = boardSize - 1, k = 0; j >= 0 && k < boardSize; j--, k++)
				rowOfPieces[j] = getValue(i, j, k);
			if (rowWins())
				return true;
		}
		//check major diagonals
		resetRow();
		for (i = 0, j = 0, k = 0; i < boardSize && j < boardSize && k < boardSize; i++, j++, k++)
			rowOfPieces[i] = getValue(i, j, k);
		if (rowWins())
			return true;
		resetRow();
		for (i = boardSize - 1, j = 0, k = 0; i >= 0 && j < boardSize && k < boardSize; i--, j++, k++)
			rowOfPieces[i] = getValue(i, j, k);
		if (rowWins())
			return true;
		resetRow();
		for (i = 0, j = boardSize - 1, k = 0; i < boardSize && j >= 0 && k < boardSize; i++, j--, k++)
			rowOfPieces[i] = getValue(i, j, k);
		if (rowWins())
			return true;
		resetRow();
		for (i = boardSize - 1, j = boardSize - 1, k = 0; i >= 0 && j >= 0 && k < boardSize; i--, j--, k++)
			rowOfPieces[i] = getValue(i, j, k);
		if (rowWins())
			return true;
		
		return false;
	}
	/**
	 * #6.5
	 * 
	 * Resets the rowOfPieces array to check a new row
	 */
	protected void resetRow() {
		for (int i = 0; i < boardSize; i++)
			rowOfPieces[i] = 0;
	}
	
	/**
	 * #6.75
	 * 
	 * Checks the rowOfPieces array for four white or four black pieces
	 */
	protected boolean rowWins() {
		boolean rowWins = true;
		char first = rowOfPieces[0];
		if (first == 'w' || first == 'b') {
			for (int i = 1; i < boardSize; i++) {
				// makes sure every other character is the same as the first
				if (rowOfPieces[i] != first) { 
					rowWins = false;
					break; // no need to check the other characters
				}
			}
			winningPiece = first; // if for-loop completes then the row must have won
		}
		else rowWins = false;
		return rowWins;
	}
	
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
		do {
			place(p1.getNextMove(board, 'w'), 'w');
			moves++;
			printBoard();
			if (isGameOver())
				break;
			place(p2.getNextMove(board, 'b'), 'b');
			moves++;
			printBoard();
		}
		while(!isGameOver());
		switch (winningPiece) {
		case 'w':
			p1.notifyOutcome("WIN");
			p2.notifyOutcome("LOSS");
			return p1;
		case 'b':
			p2.notifyOutcome("WIN");
			p1.notifyOutcome("LOSS");
			return p2;
		default:
			p1.notifyOutcome("DRAW");
			p2.notifyOutcome("DRAW");
			return null;
		}
	}
}
