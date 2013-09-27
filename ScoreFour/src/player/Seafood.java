/**
 * @author Max Fisher (mfis1267)
 * @version 1.1
 */
package player;

import util.Coordinate;
import core.Player;

public class Seafood implements Player {
	private static final int currentSize = 4;
	private String opponentName;
	private String outcome;
	private char[][][] currentBoard;
	private Coordinate bestCoord1;
	private Coordinate bestCoord2;
	private int[] bestStatus;
	private char currentColour;
	private char enemyColour;
	private boolean[][][] goodMove; // used to store coordinates of winning moves that aren't currently playable
	@SuppressWarnings("unused")
	private int goodMoveCount;
	private boolean[][][] badMove; //used to store coordinates of moves that would enable the opponent to win
	@SuppressWarnings("unused")
	private int badMoveCount;

	public Seafood() {
		opponentName = null;
		outcome = "Game in progress";
		currentBoard = null;
		bestCoord1 = null;
		bestCoord2 = null;
		bestStatus = new int[2];
		currentColour = 0;
		enemyColour = 0;
		goodMove = new boolean[currentSize][currentSize][currentSize];
		goodMoveCount = 0;
		badMove = new boolean[currentSize][currentSize][currentSize];
		badMoveCount = 0;
	}
	
	@Override
	public Coordinate getNextMove(char[][][] board, char colour) {
		bestCoord1 = null;
		bestCoord2 = null;
		currentBoard = board;
		currentColour = colour;
		if (currentColour == 'w')
			enemyColour = 'b';
		else enemyColour = 'w';
		//if board is empty, do some starting move
		// else find the 'optimal' move and do that
		// debug: play the first available move
		if (boardIsEmpty())
			return new Coordinate(1, 1);
		for (int j = 0; j < currentSize; j++) {
			for (int i = 0; i < currentSize; i++) {
				if (isValidPlacement(new Coordinate(i, j)))
					checkMove(i, j, findK(i, j));
			}
		}
		if (bestCoord1 != null && badMove[bestCoord1.getI()][bestCoord1.getJ()][findK(bestCoord1.getI(), bestCoord1.getJ())] == false)
			return bestCoord1;
		else if (bestCoord2 != null && badMove[bestCoord2.getI()][bestCoord2.getJ()][findK(bestCoord2.getI(), bestCoord2.getJ())] == false)
			return bestCoord2;
		else {// return a random coordinate
			do {
				int i = (int) Math.floor(Math.random()*currentSize);
				int j = (int) Math.floor(Math.random()*currentSize);
				int k = findK(i, j);
				if (k < currentSize && badMove[i][j][k] == false)
					return new Coordinate(i, j);
			}
			while (true);
		}
	}

	@Override
	public void notifyNewOpponent(String opponentName) {
		this.opponentName = opponentName;
	}

	@Override
	public void notifyOutcome(String outcome) {
		this.outcome = outcome;
		finishUp();

	}
	private void finishUp() {
		System.out.println(getStatus());
	}
	
	private String getStatus() {
		return "Opponent: " + opponentName + "\nOutcome: " + outcome;
		
	}
	
	public String toString() {
		return this.getClass().getCanonicalName();
	}
	
	//Decision logic
	
	private boolean boardIsEmpty() {
		boolean isEmpty = true;
		for (int i = 0; i < currentSize; i++) {
			for (int j = 0; j < currentSize; j++) {
				if (getValue(i, j, 0) != 0)
					isEmpty = false;
			}
		}
		return isEmpty;
	}

	private boolean isValidCoordinate(int i, int j, int k) {
		if ((i >= 0 && i < currentSize) && (j >= 0 && j < currentSize) && (k >= 0 && k < currentSize))
			return true;
		else return false;
	}

	private char getValue(int i, int j, int k) {
		if (isValidCoordinate(i, j, k))
			return currentBoard[i][j][k]; // will return (char) 0 by default if array is empty
		else return (char) -1;
	}


	private boolean isValidPlacement(Coordinate placement) {
		int i = placement.getI();
		int j = placement.getJ();
		if ((isValidCoordinate(i, j, 0)) && currentBoard[i][j][currentSize - 1] == 0)
			return true;
		else return false;
	}

	private int findK(int i, int j) {
		int k = 0;
		while (getValue(i, j, k) == 'w' || getValue(i, j, k) == 'b')
			k++;
		return k;
	}
	/**
	 * #6.25
	 * 
	 * Check the board for winning moves.
	 * 
	 * First checks straight lines in each dimension, then both diagonals in each dimension, then the four major diagonals
	 */
	private void checkMove(int iC, int jC, int kC) {
		int i, j, k;
		// check i direction
		char[] rowOfPieces = new char[currentSize];
		for (i = 0; i < currentSize; i++)
			rowOfPieces[i] = getValue(i, jC, kC);
		recordStatus(checkPlay(rowOfPieces, iC), iC, jC, kC);
		
		// check j direction
		rowOfPieces = new char[currentSize];
		for (j = 0; j < currentSize; j++)
			rowOfPieces[j] = getValue(iC, j, kC);
		recordStatus(checkPlay(rowOfPieces, jC), iC, jC, kC);
		
		// check k direction
		rowOfPieces = new char[currentSize];
		for (k = 0; k < currentSize; k++)
			rowOfPieces[k] = getValue(iC, jC, k);
		recordStatus(checkPlay(rowOfPieces, kC), iC, jC, kC);
		
		//check i-j diagonals
		rowOfPieces = new char[currentSize];
		for (i = 0, j = 0; i < currentSize && j < currentSize; i++, j++)
			rowOfPieces[i] = getValue(i, j, kC);
		recordStatus(checkPlay(rowOfPieces, iC), iC, jC, kC);
		
		rowOfPieces = new char[currentSize];
		for (i = currentSize - 1, j = 0; i >= 0 && j < currentSize; i--, j++)
			rowOfPieces[i] = getValue(i, j, kC);
		recordStatus(checkPlay(rowOfPieces, iC), iC, jC, kC);
		
		//check i-k diagonals
		rowOfPieces = new char[currentSize];
		for (i = 0, k = 0; i < currentSize && k < currentSize; i++, k++)
			rowOfPieces[i] = getValue(i, jC, k);
		recordStatus(checkPlay(rowOfPieces, iC), iC, jC, kC);
		
		rowOfPieces = new char[currentSize];
		for (i = currentSize - 1, k = 0; i >= 0 && k < currentSize; i--, k++)
			rowOfPieces[i] = getValue(i, jC, k);
		recordStatus(checkPlay(rowOfPieces, iC), iC, jC, kC);
		
		//check j-k diagonals
		rowOfPieces = new char[currentSize];
		for (j = 0, k = 0; j < currentSize && k < currentSize; j++, k++)
			rowOfPieces[j] = getValue(iC, j, k);
		recordStatus(checkPlay(rowOfPieces, jC), iC, jC, kC);
		
		rowOfPieces = new char[currentSize];
		for (j = currentSize - 1, k = 0; j >= 0 && k < currentSize; j--, k++)
			rowOfPieces[j] = getValue(iC, j, k);
		recordStatus(checkPlay(rowOfPieces, jC), iC, jC, kC);
		
		//check major diagonals
		rowOfPieces = new char[currentSize];
		for (i = 0, j = 0, k = 0; i < currentSize && j < currentSize && k < currentSize; i++, j++, k++)
			rowOfPieces[i] = getValue(i, j, k);
		recordStatus(checkPlay(rowOfPieces, iC), iC, jC, kC);
		
		rowOfPieces = new char[currentSize];
		for (i = currentSize - 1, j = 0, k = 0; i >= 0 && j < currentSize && k < currentSize; i--, j++, k++)
			rowOfPieces[i] = getValue(i, j, k);
		recordStatus(checkPlay(rowOfPieces, iC), iC, jC, kC);
		
		rowOfPieces = new char[currentSize];
		for (i = 0, j = currentSize - 1, k = 0; i < currentSize && j >= 0 && k < currentSize; i++, j--, k++)
			rowOfPieces[i] = getValue(i, j, k);
		recordStatus(checkPlay(rowOfPieces, iC), iC, jC, kC);
		
		rowOfPieces = new char[currentSize];
		for (i = currentSize - 1, j = currentSize - 1, k = 0; i >= 0 && j >= 0 && k < currentSize; i--, j--, k++)
			rowOfPieces[i] = getValue(i, j, k);
		recordStatus(checkPlay(rowOfPieces, iC), iC, jC, kC);
		}
	
	/**
	 * Counts the number of consecutive tokens of the player's colour in the given row tokens,
	 * given a prospective move to make in that row. Similarly counts number of opposing player's tokens.
	 * @param coord - the position of the prospective token relative to the row being counted
	 * @return -2 the position is already taken
	 * @return -1 the play is for some reason a bad play
	 * @return 0 the play makes a row with less tokens than the best row count
	 * @return 1 the play makes a row with more or possibly the same number of tokens as the best row count
	 * @return 2 the play blocks an opponent from winning
	 * @return array containing information about the move: <p>
	 * status[0] rates the offensive quality of move <p>
	 * -1: the move is pointless as the row is blocked from winning
	 * 	(but if the move is good to make four in another row then it will be picked up by another search) <p>
	 *  0: normal move; neither particularly good nor bad <p>
	 *  1: good move: increases number of consecutive tokens in that row
	 *  2: winning move: this move would win the game! 
	 * status[1] rates defensive quality of move <p>
	 * -1: unnecessary: the row already blocks the opponent from winning <p>
	 *  0: normal: the opponent has few tokens in this row (less than half of row) <p>
	 *  1: reasonable defensive move: the opponent has more than half the row filled <p>
	 *  2: the opponent must be blocked as they have all but one slot filled
	 *  **this needs to check whether the slot is accessible on the next move** <p>
	 * @return null the position is already taken
	 */
	private int[] checkPlay(char[] row, int coord) {
		int[] status = new int[] {0, 0};
		if (row[coord] != 0)
			return null; // position is already taken
		else {
			// counts opponent's tokens
			int themCount = 0;
			for (int i = 0; i < currentSize; i++) {
				if (row[i] == enemyColour) {
					themCount++;
					status[0] = -1; // row is blocked
				}
				else if (row[i] == currentColour)
					status[1] = -1;
			}
			if (themCount >= currentSize / 2 && status[1] >= 0) {
				status[1] = 1; // they probably should be blocked
				if (themCount == currentSize - 1)
					status[1] = 2; // need to be blocked
			}
			//counts player's tokens
			int usCount = 0;
			row[coord] = currentColour; // plays a hypothetical move
			for (int i = 0; i < currentSize; i++) {
				if (row[i] == currentColour)
					usCount++;
			}
			if (usCount > 2 && status[0] >= 0) {
				status[0] = 1; // decent move
				if (usCount == currentSize)
					status[0] = 2; //potential winning move!
			}
		}
		return status;
	}
	/**
	 * checks the status of the current move
	 * @param status - the information about the current move as provided by the method checkRow()
	 * @return 
	 */
	private void recordStatus(int[] status, int i, int j, int k) {
		if (status == null)
			return;
		int kValue = findK(i, j);
		if (status[0] == 2 || status[1] == 2) {
			goodMove[i][j][k] = true;
			goodMoveCount++;
			if (kValue == k)
				bestCoord1 = new Coordinate(i, j);
			else if (status[1] == 2 && kValue < k ) {
				badMove[i][j][k-1] = true; // tells AI to not play there
				badMoveCount++;
			}
		}
		if ((status[0] > bestStatus[0] && status[1] > bestStatus[1]) || // status is absolutely greater
				(status[0] + status [1] > bestStatus[0] + bestStatus[1]) || //sum of statuses is greater
				(status[0] + status [1] == bestStatus[0] + bestStatus[1] && Math.random() > 0.5)) { //variety is the spice of life
			bestStatus = status;
			if (kValue == k)
				bestCoord2 = new Coordinate(i, j);
		}
	}
}
