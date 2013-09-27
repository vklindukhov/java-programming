/**
 * @author Max Fisher (mfis1267)
 * @version 1.3
 */
package mfis1267;

import java.util.TreeMap;

import util.Coordinate;
import core.Player;

public class LobsterMornay implements Player {
	private static final int boardSize = 4;

	private String opponentName;
	private String outcome;
	private int skips; // counts number of skipped moves
	private int badMoveCount; // counts number of badMoves recorded
	private char[][][] board;
	private char colour;
	private char enemyColour;
	
	public LobsterMornay() {
		outcome = "Game in progress";
		badMoveCount = 0;
		skips = 0;
	}
	
	@Override
	public Coordinate getNextMove(char[][][] board, char colour) {
		//get current game information
		this.board = board;
		
		// starting move
		if (boardIsEmpty())
			// returns some coordinate in the middle four squares
			return new Coordinate(1 + (int)Math.round(Math.random()), 1 + (int)Math.round(Math.random()));
		
		//get rest of game information
		this.colour = colour;
		if (colour == 'w')
			enemyColour = 'b';
		else enemyColour = 'w';

		TreeMap<MoveStatus, Coordinate> rankMap = new TreeMap<MoveStatus, Coordinate>();
		// maps MoveStatuses to coordinates, and can be sorted in terms of moveRanks

		for (int j = 0; j < boardSize; j++) {
			for (int i = 0; i < boardSize; i++) {
				Coordinate coord = new Coordinate(i, j);
				MoveStatus statusHere = checkMove(coord);
				MoveStatus statusAbove = checkMove(coord, 1);
				if (statusHere == null)
					continue;
				if (statusAbove != null && statusAbove.enemyRowsOf[boardSize - 1] > 0)
					statusHere.badMove = true; // playing here would let opponent win on next term
			
				statusHere.moveRank = 6; // default move
				if (statusHere.friendlyRowsOf[boardSize] > 0)
					statusHere.moveRank = 1; // winning row
				else if (statusHere.enemyRowsOf[boardSize - 1] > 0) // opponent should be blocked in this row
					statusHere.moveRank = 2;
				else if (statusHere.enemyRowsOf[(boardSize + 1) / 2] > 1)
					statusHere.moveRank = 3; // two or more rows of two opponent tokens
				else if (statusHere.friendlyRowsOf[boardSize - 1] == 1)
					statusHere.moveRank = 4; // two rows of three
				else if (statusHere.friendlyRowsOf[(boardSize + 1) / 2] > 1)
					statusHere.moveRank = 5; // two or more rows of two player tokens
				else if (statusHere.friendlyRowsOf[boardSize - 1] == 1)
					statusHere.moveRank = 7; // one row of three
				rankMap.put(statusHere, coord);
			}
		}
		MoveStatus next = rankMap.firstKey();
		while (true) {
			if (next == null) {
				skips++;
				return null;
			}
			if (next.moveRank == 1 || (next.moveRank > 1 && next.badMove == false))
				return rankMap.get(next);
			else next = rankMap.higherKey(next);
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
		return "Opponent: " + opponentName + "\nOutcome: " + outcome
				+ "\nSkips: "+ skips + "\nBad move count: " + badMoveCount;
		
	}
	
	public String toString() {
		return this.getClass().getCanonicalName();
	}
	
	//Decision logic
	
	private boolean boardIsEmpty() {
		boolean isEmpty = true;
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (getValue(i, j, 0) != 0)
					isEmpty = false;
			}
		}
		return isEmpty;
	}

	protected boolean isValidCoordinate(int ... coords) {
		boolean isValid = true;
		for (int i : coords) {
			if (!(i >= 0 && i < boardSize)) // if i is not within the bounds of the board
				isValid = false;
		}
		return isValid;
	}

	private char getValue(int ... coords) {
		if (isValidCoordinate(coords))
			return board[coords[0]][coords[1]][coords[2]]; // will return (char) 0 by default if location is empty
		else return (char) 1;
	}

	private int findK(int i, int j) {
		int k = 0;
		while (getValue(i, j, k) > 1)
			k++;
		return k;
	}
	/**
	 * #6.25
	 * 
	 * Check the board for winning moves.
	 * 
	 * For each position, checks orthoganal directions, then the diagonal or diagonals that the position lies on
	 * @param coord the coordinate of the move to check
	 * @param offset selects how far above the current legal move in that row the method should check.
	 * For example, if the offset is 0, then the method will check the move obtained by placing one counter in the column.
	 * If the offset is 1, then the method would effectively levitate the counter one row above the next available space in the column
	 */
	private MoveStatus checkMove(Coordinate coord, int offset) {
		int currentI = coord.getI();
		int currentJ = coord.getJ();
		int currentK = findK(currentI, currentJ) + offset;

		if (!isValidCoordinate(currentI, currentJ, currentK) || getValue(currentI, currentJ, currentK) != 0)
			return null;

		MoveStatus status = new MoveStatus();

		// check i direction
		int[][] rowCoords = new int[boardSize][3];
		for (int i = 0; i < boardSize; i++) {
			rowCoords[i][0] = i;
			rowCoords[i][1] = currentJ;
			rowCoords[i][2] = currentK;
		}
		checkRow(rowCoords, currentI, status);

		// check j direction
		rowCoords = new int[boardSize][3];
		for (int j = 0; j < boardSize; j++) {
			rowCoords[j][0] = currentI;
			rowCoords[j][1] = j;
			rowCoords[j][2] = currentK;
		}
		checkRow(rowCoords, currentJ, status);

		// check k direction
		rowCoords = new int[boardSize][3];
		for (int k = 0; k < boardSize; k++) {
			rowCoords[k][0] = currentI;
			rowCoords[k][1] = currentJ;
			rowCoords[k][2] = k;
		}
		checkRow(rowCoords, currentK, status);

		// checking diagonals: only valid for coordinates that lie on the
		// diagonals of the cube

		// check i-j diagonals
		if (currentI == currentJ) {
			rowCoords = new int[boardSize][3];
			for (int i = 0, j = 0; i < boardSize && j < boardSize; i++, j++) {
				rowCoords[i][0] = i;
				rowCoords[i][1] = j;
				rowCoords[i][2] = currentK;
			}
			checkRow(rowCoords, currentI, status);
		}

		else if ((currentI + currentJ) == (boardSize - 1)) {
			rowCoords = new int[boardSize][3];
			for (int i = 0, j = boardSize - 1; i < boardSize && j >= 0; i++, j--) {
				rowCoords[i][0] = i;
				rowCoords[i][1] = j;
				rowCoords[i][2] = currentK;
			}
			checkRow(rowCoords, currentI, status);
		}

		// check i-k diagonals
		if (currentI == currentK) {
			rowCoords = new int[boardSize][3];
			for (int i = 0, k = 0; i < boardSize && k < boardSize; i++, k++) {
				rowCoords[i][0] = i;
				rowCoords[i][1] = currentJ;
				rowCoords[i][2] = k;
			}
			checkRow(rowCoords, currentI, status);
		}

		else if ((currentI + currentK) == (boardSize - 1)) {
			rowCoords = new int[boardSize][3];
			for (int i = 0, k = boardSize - 1; i < boardSize && k >= 0; i++, k--) {
				rowCoords[i][0] = i;
				rowCoords[i][1] = currentJ;
				rowCoords[i][2] = k;
			}
			checkRow(rowCoords, currentI, status);
		}

		// check j-k diagonals
		if (currentJ == currentK) {
			rowCoords = new int[boardSize][3];
			for (int j = 0, k = 0; j < boardSize && k < boardSize; j++, k++) {
				rowCoords[j][0] = currentI;
				rowCoords[j][1] = j;
				rowCoords[j][2] = k;
			}
			checkRow(rowCoords, currentJ, status);
		}

		else if ((currentI + currentK) == (boardSize - 1)) {
			rowCoords = new int[boardSize][3];
			for (int j = 0, k = boardSize - 1; j < boardSize && k >= 0; j++, k--) {
				rowCoords[j][0] = currentI;
				rowCoords[j][1] = j;
				rowCoords[j][2] = k;
			}
			checkRow(rowCoords, currentJ, status);
		}

		// check major diagonals
		if (currentI == currentJ && currentI == currentK) {
			rowCoords = new int[boardSize][3];
			for (int i = 0, j = 0, k = 0; i < boardSize && j < boardSize
					&& k < boardSize; i++, j++, k++) {
				rowCoords[i][0] = i;
				rowCoords[i][1] = j;
				rowCoords[i][2] = k;
			}
			checkRow(rowCoords, currentI, status);
		}

		else if (currentI + currentJ == boardSize - 1
				&& currentI + currentK == boardSize - 1) {
			rowCoords = new int[boardSize][3];
			for (int i = boardSize - 1, j = 0, k = 0; i >= 0 && j < boardSize
					&& k < boardSize; i--, j++, k++) {
				rowCoords[i][0] = i;
				rowCoords[i][1] = j;
				rowCoords[i][2] = k;
			}
			checkRow(rowCoords, currentI, status);
		}

		else if (currentI == currentK && currentI + currentJ == boardSize - 1) {
			rowCoords = new int[boardSize][3];
			for (int i = 0, j = boardSize - 1, k = 0; i < boardSize && j >= 0
					&& k < boardSize; i++, j--, k++) {
				rowCoords[i][0] = i;
				rowCoords[i][1] = j;
				rowCoords[i][2] = k;
			}
			checkRow(rowCoords, currentI, status);
		}

		else if (currentI == currentJ && currentI + currentK == boardSize - 1) {
			rowCoords = new int[boardSize][3];
			for (int i = 0, j = 0, k = boardSize - 1; i < boardSize
					&& j < boardSize && k >= 0; i++, j++, k--) {
				rowCoords[i][0] = i;
				rowCoords[i][1] = j;
				rowCoords[i][2] = k;
			}
			checkRow(rowCoords, currentI, status);
		}
		return status;
	}
	
	private MoveStatus checkMove(Coordinate coord) {
		return checkMove(coord, 0);
	}
	
	/**
	 * Analyzes the current row with a given prospective move
	 * @param rowCoords array describing the coordinates of the current row
	 * @param movePosition the location in the row of the move to be made
	 * @param status the status in which to record the result
	 */
	private void checkRow(int[][] rowCoords, int movePosition, MoveStatus status) {
		// counts opponent's tokens
		int usCount = 0, themCount = 0;
		boolean usRowBlocked = false, themRowBlocked = false;
		for (int[] coord : rowCoords) {
			if (getValue(coord) == colour) {
				usCount++; // counts player's tokens
				themRowBlocked = true;
			}
			else if (getValue(coord) == enemyColour) {
				themCount++; // counts opponent's tokens
				usRowBlocked = true;
			}
		
		}
		usCount++; // accounts for current move
		if (!usRowBlocked) // doesn't count player's tokens when row is blocked by opponent
		status.friendlyRowsOf[usCount]++;
		if (!themRowBlocked)// doesn't count opponent's tokens when row is already blocked by player
			status.enemyRowsOf[themCount]++;
	}
	/**
	 * Class to provide information about a given ScoreFour move.
	 */
	private class MoveStatus implements Comparable<MoveStatus> {
		/**
		 * Counts the number of unblocked rows of each length of player's tokens that the move would make. <p>
		 * 
		 * For example, if the move makes a row of 3 vertically, a row of 2 horizontally, 
		 * and a row of 3 diagonally, then numRowsOf[3] = 2, and numRowsOf[2] = 1. <p>
		 * However, if a row contains an opponent's token, then it is discounted.
		 * 
		 * Note this variable must be changed as each move is checked in different directions.
		 * Note also that a move can never make a row of 0, as the counter itself is counted in this count.
		 */
		private int[] friendlyRowsOf;

		/**
		 * Equivalent variable counting opponent's tokens
		 */
		private int[] enemyRowsOf;
		
		/**
		 * Records whether this move, when played, would enable an opponent to win on the next turn by playing a move on top of this piece
		 */
		boolean badMove;
		
		/**
		 * Gives a numerical indication of the move's effectiveness. The lower the number the better.
		 */
		private int moveRank;
				
		private MoveStatus() {
			friendlyRowsOf = new int[boardSize + 1];
			enemyRowsOf = new int[boardSize];
			badMove = false;
		}

		@Override
		public int compareTo(MoveStatus ms) {
			if (this.moveRank > ms.moveRank)
				return 1;
			else if (this.moveRank < ms.moveRank)
				return -1;
			else return 0;
		}
	}
}
