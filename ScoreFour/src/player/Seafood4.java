/**
 * @author Max Fisher (mfis1267)
 * @version 1.4
 */
package player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.Coordinate;
import core.Player;

public class Seafood4 implements Player {
	private static final int boardSize = 4;

	private String opponentName;
	private String outcome;
	private int skips; // counts number of skipped moves
	private int badMoveCount; // counts number of badMoves recorded
	private char[][][] board;
	private char colour;
	private char enemyColour;
	
	public Seafood4() {
		outcome = "Game in progress";
		badMoveCount = 0;
		skips = 0;
	}
	
	@Override
	public Coordinate getNextMove(char[][][] board, char colour) {
		//get current game information
		this.board = board;
		
		int tokenCount = countTokens();
		// starting move
		if (tokenCount < 2)
			// returns some coordinate in the middle four squares on the first move
			return new Coordinate(1 + (int)Math.round(Math.random()), 1 + (int)Math.round(Math.random()));
		
		//get rest of game information
		this.colour = colour;
		if (colour == 'w')
			enemyColour = 'b';
		else enemyColour = 'w';

		List<MoveStatus> rankedMoves = new ArrayList<MoveStatus>();
		// stores MoveStatuses in a set, sorted by moveRank
		for (int j = 0; j < boardSize; j++) {
			for (int i = 0; i < boardSize; i++) {
				Coordinate coord = new Coordinate(i, j);
				MoveStatus statusHere = getMove(coord);
				if (statusHere == null)
					continue;
				
				if (tokenCount > 2* boardSize) { //don't need to watch out for dangerous rows for a little while
					MoveStatus statusAbove = getMove(coord, 1);
					if (statusAbove != null && statusAbove.enemyRowsOf[boardSize - 1] > 0) {
						statusHere.setBadMove(); // playing here would let opponent win on next turn
						badMoveCount++;
					}
				}
				
				statusHere.analyseMove();
				rankedMoves.add(statusHere);
				if (statusHere.rank == 1)
					break;
			}
		}
		Collections.sort(rankedMoves);

		while (true) {
			if (rankedMoves.isEmpty()) {  // run out of moves in the set
				skips++;
				return null; // no moves possible that don't let the opponent win next round... so do nothing!
			}
			else for (MoveStatus m : rankedMoves) {
				if (m.rank == 1) // win!
					return new Coordinate(m.position[0], m.position[1]);
			if (m.isBadMove()) {
				rankedMoves.remove(m); // removes bad moves that let the opponent win next round
				break;
			}			
			if (m.rank < 6)
				return new Coordinate(m.position[0], m.position[1]);
			
			else if (m.rank > MoveStatus.defaultRank) // try not to play bad moves
				continue;
			else if (Math.random() > (double)2*(8 - m.rank)/m.rank) // if moves is low enough ranked, might not play it... 
				return new Coordinate(m.position[0], m.position[1]);
			}
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
	
	private int countTokens() {
		int tokenCount = 0;
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (getValue(i, j, 0) != 0)
					tokenCount++;
			}
		}
		return tokenCount;
	}

	private boolean isValidCoordinate(int ... coords) {
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
	private MoveStatus getMove(Coordinate coord, int offset) {
		int currentI = coord.getI();
		int currentJ = coord.getJ();
		int currentK = findK(currentI, currentJ) + offset;

		if (!isValidCoordinate(currentI, currentJ, currentK) || getValue(currentI, currentJ, currentK) != 0)
			return null;

		MoveStatus status = new MoveStatus(currentI, currentJ, currentK);
		RowInfo row = null;
		
		// check i direction
		row = new RowInfo(RowType.HORIZONTAL);
		for (int i = 0; i < boardSize; i++)
			row.setCoords(i, i, currentJ, currentK);
		status.setRowInfo(row, 0);

		// check j direction
		row = new RowInfo(RowType.HORIZONTAL);
		for (int j = 0; j < boardSize; j++)
			row.setCoords(j, currentI, j, currentK);
		status.setRowInfo(row, 1);

		// check k direction
		row = new RowInfo(RowType.VERTICAL);
		for (int k = 0; k < boardSize; k++)
			row.setCoords(k, currentI, currentJ, k);
		status.setRowInfo(row, 2);

		// checking diagonals: only valid for coordinates that lie on the
		// diagonals of the cube

		// check i-j diagonals
		if (currentI == currentJ) {
			row = new RowInfo(RowType.DIAGONAL);
			for (int i = 0, j = 0; i < boardSize && j < boardSize; i++, j++)
				row.setCoords(i, i, j, currentK);
			status.setRowInfo(row, 3);
		}

		else if ((currentI + currentJ) == (boardSize - 1)) {
			row = new RowInfo(RowType.DIAGONAL);
			for (int i = 0, j = boardSize - 1; i < boardSize && j >= 0; i++, j--)
				row.setCoords(i, i, j, currentK);
			status.setRowInfo(row, 3);
			}

		// check i-k diagonals
		if (currentI == currentK) {
			row = new RowInfo(RowType.DIAGONAL);
			for (int i = 0, k = 0; i < boardSize && k < boardSize; i++, k++)
				row.setCoords(i, i, currentJ, k);
			status.setRowInfo(row, 4);
		}

		else if ((currentI + currentK) == (boardSize - 1)) {
			row = new RowInfo(RowType.DIAGONAL);
			for (int i = 0, k = boardSize - 1; i < boardSize && k >= 0; i++, k--)
				row.setCoords(i, i, currentJ, k);
			status.setRowInfo(row, 4);
		}

		// check j-k diagonals
		if (currentJ == currentK) {
			row = new RowInfo(RowType.DIAGONAL);
			for (int j = 0, k = 0; j < boardSize && k < boardSize; j++, k++)
				row.setCoords(j, currentI, j, k);
			status.setRowInfo(row, 5);
		}

		else if ((currentI + currentK) == (boardSize - 1)) {
			row = new RowInfo(RowType.DIAGONAL);
			for (int j = 0, k = boardSize - 1; j < boardSize && k >= 0; j++, k--)
				row.setCoords(j, currentI, j, k);
			status.setRowInfo(row, 5);
		}

		// check major diagonals
		if (currentI == currentJ && currentI == currentK) {
			row = new RowInfo(RowType.DIAGONAL);
			for (int i = 0, j = 0, k = 0; i < boardSize && j < boardSize
					&& k < boardSize; i++, j++, k++)
				row.setCoords(i, i, j, k);
			status.setRowInfo(row, 6);
		}

		else if (currentI + currentJ == boardSize - 1
				&& currentI + currentK == boardSize - 1) {
			row = new RowInfo(RowType.DIAGONAL);
			for (int i = boardSize - 1, j = 0, k = 0; i >= 0 && j < boardSize
					&& k < boardSize; i--, j++, k++)
				row.setCoords(i, i, j, k);
			status.setRowInfo(row, 6);
		}

		else if (currentI == currentK && currentI + currentJ == boardSize - 1) {
			row = new RowInfo(RowType.DIAGONAL);
			for (int i = 0, j = boardSize - 1, k = 0; i < boardSize && j >= 0
					&& k < boardSize; i++, j--, k++)
				row.setCoords(i, i, j, k);
			status.setRowInfo(row, 6);
		}

		else if (currentI == currentJ && currentI + currentK == boardSize - 1) {
			row = new RowInfo(RowType.DIAGONAL);
			for (int i = 0, j = 0, k = boardSize - 1; i < boardSize
					&& j < boardSize && k >= 0; i++, j++, k--)
				row.setCoords(i, i, j, k);
			status.setRowInfo(row, 6);
		}
		return status;
	}
	
	private MoveStatus getMove(Coordinate coord) {
		return getMove(coord, 0);
	}

	/**
	 * Class to provide information about a given ScoreFour move.
	 */
	private class MoveStatus implements Comparable<MoveStatus> {
		public static final int defaultRank = 7;
		/**
		 * Records the coordinates of the position in the board
		 */
		private final int[] position;
		/**
		 * RowInfo objects keeping track of each direction that a move can be counted in.
		 * Directions are, in order, iDir, jDir, kDir, ijDiag, ikDiag, jkDiag, majDiag;
		 * Diagonals (specifically the 2D diagonals) may not all be instantiated depending on position
		 */
		private final RowInfo[] direction;
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
		private final int[] playerRowsOf;
		
		/**
		 * Counts number of unblockable rows of each length in the array above
		 * For a move to be unblockable, none of the other positions in that row must be accessible on the next turn
		 */
		private final int[] playerGoodRowsOf;

		/**
		 * Equivalent variables counting opponent's tokens
		 */
		private final int[] enemyRowsOf;
		private final int[] enemyGoodRowsOf;
		
		/**
		 * Records whether this move, when played, would enable an opponent to win on the next turn by playing a move on top of this piece
		 */
		boolean badMove;
		
		/**
		 * Gives a numerical indication of the move's effectiveness. The lower the number the better.
		 */
		private int rank;
		/**
		 * random number used to randomise the order of moves within the same rank
		 */
		private final double randFactor; 
		
		private MoveStatus(int ... coords) {
			this.position = coords.clone();
			direction = new RowInfo[7];
			playerRowsOf = new int[boardSize + 1];
			playerGoodRowsOf = new int[boardSize + 1];
			enemyRowsOf = new int[boardSize];
			enemyGoodRowsOf = new int[boardSize + 1];
			badMove = false;
			rank = defaultRank;
			randFactor = Math.random();
		}
		
		/** 
		 * Stores a RowInfo object as one of this move's directions 
		 * @param info the RowInfo object to store
		 * @param index specifies direction array index to store result in
		 * Directions are, in order: iDir, jDir, kDir, ijDiag, ikDiag, jkDiag, majDiag;
		 */
		private void setRowInfo(RowInfo info, int index) {
			if (index < 0 || index > 7 || direction[index] != null)
			return;
			else direction[index] = info;
		}
		
		/**
		 * Performs the checkRow() method on each RowInfo object array stored in this direction array
		 */
		private void analyseMove() {
			for (RowInfo r : direction) {
				if (r != null) {
					r.checkRow();
					if (!r.playerIsBlocked && r.usCount > 1) { // not interested in empty rows
						playerRowsOf[r.usCount]++;
						if (r.unBlockable)
							playerGoodRowsOf[r.usCount]++;
					}
					if (!r.enemyIsBlocked && r.themCount > 1) {
						enemyRowsOf[r.themCount]++;
						if (r.unBlockable)
							enemyGoodRowsOf[r.usCount]++;
					}
				}
			}
			setRank();
		}
		private void setRank() {
			if (playerRowsOf[boardSize] > 0)
				rank = 1; // winning row
			else if (enemyRowsOf[boardSize - 1] > 0) // opponent should be blocked in this row
				rank = 2;
			else if (playerRowsOf[boardSize - 1] - playerGoodRowsOf[boardSize - 1] > 1) 
				// 'trapping move' - makes 2 rows of 3 at once
				rank = 3;
			else if (enemyRowsOf[(boardSize + 1) / 2] > 1)
				rank = 4; // block two or more rows of two opponent tokens
			else if (playerGoodRowsOf[boardSize - 1] > 0)
				rank = 4; // play any unblockable rows of 3
			else if (playerRowsOf[(boardSize + 1) / 2] > 1)
				rank = 5; // two or more rows of two player tokens
			else if (playerRowsOf[(boardSize + 1) / 2] == 1)
				rank = 6; // row of two
			else if (enemyRowsOf[(boardSize + 1) / 2] == 1)
				rank = 6; // block a row of two
			else if (playerRowsOf[boardSize - 1] == 1 && playerGoodRowsOf[boardSize - 1] == 0)
				rank = 8; // just one, blockable, row of three
		}
		public boolean isBadMove() {
			return badMove;
		}
		
		private void setBadMove() {
			badMove = true;
		}

		@Override
		/**
		 * compares two statuses by rank and by randFactor
		 */
		public int compareTo(MoveStatus m) {
			if (this.rank > m.rank)
				return 1;
			else if (this.rank < m.rank)
				return -1;
			else if (this.randFactor > m.randFactor)
				return 1;
			else if (this.randFactor < m.randFactor)
				return -1;
			else return 0;
		}
	}
	private class RowInfo {
		/**
		 * Records row type
		 */
		private final RowType type;
		/**
		 * Stores coordinates of row
		 */
		private final int[][] coords;
		/**
		 * Counts player's and opponent's tokens respectively
		 */
		private int usCount, themCount;
		/**
		 * Keeps track of whether the row is unable to be blocked completely.
		 * This would occur in horizontal or diagonal rows where there is at least 
		 * one space below one or more of the coordinates in the row
		 */
		private boolean unBlockable;
		/**
		 * records whether the row is already blocked for either player
		 */
		private boolean playerIsBlocked, enemyIsBlocked = true;
		
		private RowInfo(RowType type) {
			this.type = type;
			coords = new int[boardSize][];
			usCount = themCount = 0;
			unBlockable = playerIsBlocked = enemyIsBlocked = false;
		}

		public void setCoords(int index, int ... coords) {
			if (this.coords[index] == null)
				this.coords[index] = coords;
		}
		/**
		 * Analyzes the current row with a given prospective move
		 * @param coords array describing the coordinates of the current row
		 * @param movePosition the location in the row of the move to be made
		 */
		private void checkRow() {
			// counts opponent's tokens
			for (int[] current : coords) {
				if (getValue(current) == colour) {
					usCount++; // counts player's tokens
					enemyIsBlocked = true;
				}
				else if (getValue(current) == enemyColour) {
					themCount++; // counts opponent's tokens
					playerIsBlocked = true;
				}
				else if (getValue(current) == 0) { // coordinate is empty, and...
					if (type != RowType.VERTICAL && getValue(current[0], current[1], current[2] - 1) == 0)
					// place below this coordinate is also empty, meaning the row cannot be blocked yet
					unBlockable = true;
				}
				else throw new RuntimeException("Invalid grid coordinate at " 
					+ current[0] + ", " + current[1] + ", " + current[2] + ".");
			}
			usCount++; //accounts for potential move played
		}
	}
	private enum RowType {
		HORIZONTAL, VERTICAL, DIAGONAL;
	}
}
