/**
 * @author Max Fisher (mfis1267)
 * @version 1.1
 */
package player;

import util.Coordinate;
import core.Player;

public class Seafood2 implements Player {
	private static final int currentSize = 4;
	private String opponentName;
	private String outcome;
	private char[][][] currentBoard;
	private Coordinate bestCoord1;
	private Coordinate bestCoord2;
	private MoveStatus bestStatus;
	private char currentColour;
	private char enemyColour;
	private boolean[][][] goodMove; // used to store coordinates of winning moves that aren't currently playable
	private boolean[][][] badMove; //used to store coordinates of moves that would enable the opponent to win

	public Seafood2() {
		opponentName = null;
		outcome = "Game in progress";
		currentBoard = null;
		bestCoord1 = null;
		bestCoord2 = null;
		bestStatus = new MoveStatus();
		currentColour = 0;
		enemyColour = 0;
		goodMove = new boolean[currentSize][currentSize][currentSize];
		badMove = new boolean[currentSize][currentSize][currentSize];
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
	 * Analyzes the current row with a given prospective move and returns a
	 * @return a new Status object describing the move's effectiveness
	 * @return null if the position is already taken
	 */
	private MoveStatus checkPlay(char[] row, int coord) {
		MoveStatus status = new MoveStatus();
		if (row[coord] != 0)
			return null; // position is already taken
		else {
			// counts opponent's tokens
			for (int i = 0; i < currentSize; i++) {
				if (row[i] == enemyColour) {
					status.theirs++;
					status.off = -1; // row is blocked
				}
				else if (row[i] == currentColour)
					status.def = -1;
			}
			if (status.theirs >= currentSize / 2 && status.def >= 0) {
				status.def = 1; // they probably should be blocked
				if (status.theirs == currentSize - 1)
					status.def = 2; // need to be blocked
			}
			//counts player's tokens
			row[coord] = currentColour; // plays a hypothetical move
			for (int i = 0; i < currentSize; i++) {
				if (row[i] == currentColour)
					status.mine++;
			}
			if (status.mine > 2 && status.off >= 0) {
				status.off = 1; // decent move
				if (status.mine == currentSize)
					status.off = 2; //potential winning move!
			}
		}
		return status;
	}
	/**
	 * checks the status of the current move
	 * @param status - the information about the current move as provided by the method checkRow()
	 * @return 
	 */
	private void recordStatus(MoveStatus status, int i, int j, int k) {
		if (status == null)
			return;
		int kValue = findK(i, j);
		if (status.off == 2 || status.def == 2) {
			goodMove[i][j][k] = true;
			if (kValue == k)
				bestCoord1 = new Coordinate(i, j);
			else if (status.def == 2 && kValue < k ) {
				badMove[i][j][k-1] = true; // tells AI to not play there
			}
		}
		if ((status.off > bestStatus.off && status.def > bestStatus.def) || // status is absolutely greater
				(status.off + status.def > bestStatus.off + bestStatus.def) || //sum of statuses is greater
				(status.off + status.def == bestStatus.off + bestStatus.def && Math.random() > 0.5)) { //variety is the spice of life
			bestStatus = status;
			if (kValue == k)
				bestCoord2 = new Coordinate(i, j);
		}
	}
	/**
	 * Class to provide information about a given ScoreFour move.
	 */
	private class MoveStatus {
		/**
		 * Counts player's tokens in row
		 */
		private int mine;
		/** 
		 * Counts opponent's tokens in row
		 */
		private int theirs;
		/**
		 * Rates the offensive quality of move: <p>
		 * -1: the move is pointless as the row is blocked from winning (but if
		 * the move is good to make four in another row then it will be picked
		 * up by another search) <p>
		 * 0: normal move; neither particularly good nor bad <p>
		 * 1: good move: increases number of consecutive tokens in that row <p>
		 * 2: winning move: this move would win the game! <p>
		 */
		private int off;
		/**
		 * Rates defensive quality of move: <p>
		 * -1: unnecessary: the row already blocks the opponent from winning <p>
		 * 0: normal: the opponent has few tokens in this row (less than half of row) <p>
		 * 1: reasonable defensive move: the opponent has more than half the row filled <p>
		 * 2: the opponent should be blocked if possible as they have all but one slot filled
		 */
		private int def;

		private MoveStatus() {
			mine = 0;
			theirs = 0;
			off = 0;
			def = 0;
		
		}
	}
}
