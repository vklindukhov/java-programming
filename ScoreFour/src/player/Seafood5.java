/**
 * @author Max Fisher (mfis1267)
 * @version 1.5
 */
package player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Coordinate;
import core.Player;

public class Seafood5 implements Player {
	public static final int boardSize = 4;
	public static final int maxRows = 3*boardSize*boardSize + 6*boardSize + 4;

	private String opponentName;
	private String outcome;
	private int skips; // counts number of skipped moves
	private int badMoveCount; // counts number of badMoves recorded
	private char[][][] board;
	private char colour;
	private char enemyColour;
	
	// decision logic variables
	List<MoveStatus> rankedMoves; // stores MoveStatuses in a set, sorted by moveRank
	Map<RowInfo, int[]> rowMap; // maps rows to coordinates so they can be used for every position
	
	public Seafood5() {
		outcome = "Game in progress";
		badMoveCount = 0;
		skips = 0;
		rowMap = null;
		rankedMoves = null;
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
		rowMap = new HashMap<RowInfo, int[]>(maxRows/2);
		rankedMoves = new ArrayList<MoveStatus>();
		//fill the rowMap
		for (int j = 0; j < boardSize; j++) {
			for (int i = 0; i < boardSize; i++) {
				int k = findK(i, j);
				mapRows(i, j, k);
				if (tokenCount > 2*boardSize - 1) //don't need to watch out for dangerous rows for a little while
					mapRows(i, j, k + 1);
			}
		}
		// allocate rows to MoveStatuses
		for (int j = 0; j < boardSize; j++) {
			for (int i = 0; i < boardSize; i++) {
				int k = findK(i, j);
				MoveStatus statusHere = new MoveStatus(i, j);
				MoveStatus statusAbove = new MoveStatus(i, j);
				/*
				 * for (RowInfo r: horiz1Set) {
				 * 	if (r.refJ == j && r.refK == k)
				 * 		statusHere.setDirection(r)
				 */
				for (RowInfo r : rowMap.keySet()) {
					if (rowMap.get(r)[0] == i && rowMap.get(r)[1] == j && rowMap.get(r)[2] == k) { // row includes the current position
						statusHere.setDirection(r);
					}
					else if (rowMap.get(r)[0] == i && rowMap.get(r)[1] == j && rowMap.get(r)[2] == k + 1) {// row includes the position above current position
						statusAbove.setDirection(r);
					}
				}
				statusHere.analyseMove();
				statusAbove.analyseMove();
				
				if (statusAbove.enemyRowsOf[boardSize - 1] > 0) {
						statusHere.badMove = true; // playing here would let opponent win on next turn
						badMoveCount++;
				}
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
				if (m.rank == 1)  {// win!
					return new Coordinate(m.iPos, m.jPos);
				}
				
				if (m.badMove) {
					rankedMoves.remove(m); // removes bad moves that let the opponent win next round
					break;
				}			
				if (m.rank < 6) {
					return new Coordinate(m.iPos, m.jPos);
				}
			
				else if (m.rank > MoveStatus.defaultRank) // try not to play bad moves
					continue;
				else if (Math.random() > (double)2*(8 - m.rank)/m.rank) { // if moves is low enough ranked, might not play it... 
					return new Coordinate(m.iPos, m.jPos);
				}
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
		System.out.println(getStatus() + "\n");
	}
	
	private String getStatus() {
		return "Player: " + this.getClass().getCanonicalName() + "\nOpponent: " + opponentName 
				+ "\nOutcome: " + outcome + "\nSkips: "+ skips + "\nBad move count: " + badMoveCount;
		
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

	private boolean isValidCoordinate(int i, int j, int k) {
		if (i >= 0 && i < boardSize && j >= 0 && j < boardSize && k >= 0 && k < boardSize) // if i, j, k, are not within the bounds of the board
			return true;
		else return false;
	}

	private char getValue(int i, int j, int k) {
		if (isValidCoordinate(i, j, k))
			return board[i][j][k]; // will return (char) 0 by default if location is empty
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
	 * For each position, checks orthogonal directions, then the diagonal or diagonals that the position lies on
	 * @param currI the i coordinate of the position to check
	 * @param currJ the j coordinate of the position to check
	 * @param currK the k coordinate of the position to check
	 * Can modify the passed value of currK to enable checking of rows above or below the current available slot in the column
	 */
	private void mapRows(int currI, int currJ, int currK) {
		if (!isValidCoordinate(currI, currJ, currK))
			return;
		
		int[] currCoord = new int[] { currI, currJ, currK };

		RowInfo row = null;

		// check i direction
		row = new RowInfo(RowType.HORIZ1, 0, currJ, currK);
		if (!rowMap.containsKey(row)) {
			for (int i = 0; i < boardSize; i++)
				row.addToken(getValue(i, currJ, currK));
			rowMap.put(row, currCoord);
		}

		// check j direction
		row = new RowInfo(RowType.HORIZ2, currI, 0, currK);
		if (!rowMap.containsKey(row)) {
			for (int j = 0; j < boardSize; j++)
				row.addToken(getValue(currI, j, currK));
			rowMap.put(row, currCoord);
		}
		// check k direction
		row = new RowInfo(RowType.VERT, currI, currJ, 0);
		if (!rowMap.containsKey(row)) {
			for (int k = 0; k < boardSize; k++)
				row.addToken(getValue(currI, currJ, k));
			rowMap.put(row, currCoord);
		}
		// checking diagonals: only valid for coordinates that lie on the
		// diagonals of the cube

		// check i-j diagonals
		if (currI == currJ) {
			row = new RowInfo(RowType.IJDIAG1, 0, 0, currK);
			if (!rowMap.containsKey(row)) {
				for (int i = 0, j = 0; i < boardSize && j < boardSize; i++, j++)
					row.addToken(getValue(i, j, currK));
				rowMap.put(row, currCoord);
			}
		}

		else if ((currI + currJ) == (boardSize - 1)) {
			row = new RowInfo(RowType.IJDIAG2, 0, boardSize - 1, currK);
			if (!rowMap.containsKey(row)) {
				for (int i = 0, j = boardSize - 1; i < boardSize && j >= 0; i++, j--)
					row.addToken(getValue(i, j, currK));
				rowMap.put(row, currCoord);
			}
		}

		// check i-k diagonals
		if (currI == currK) {
			row = new RowInfo(RowType.IKDIAG1, 0, currJ, 0);
			if (!rowMap.containsKey(row)) {
				for (int i = 0, k = 0; i < boardSize && k < boardSize; i++, k++)
					row.addToken(getValue(i, currJ, k));
				rowMap.put(row, currCoord);
			}
		}

		else if ((currI + currK) == (boardSize - 1)) {
			row = new RowInfo(RowType.IKDIAG2, 0, currJ, boardSize - 1);
			if (!rowMap.containsKey(row)) {
				for (int i = 0, k = boardSize - 1; i < boardSize && k >= 0; i++, k--)
					row.addToken(getValue(i, currJ, k));
				rowMap.put(row, currCoord);
			}
		}

		// check j-k diagonals
		if (currJ == currK) {
			row = new RowInfo(RowType.JKDIAG1, currI, 0, 0);
			if (!rowMap.containsKey(row)) {
				for (int j = 0, k = 0; j < boardSize && k < boardSize; j++, k++)
					row.addToken(getValue(currI, j, k));
				rowMap.put(row, currCoord);
			}
		}

		else if ((currI + currK) == (boardSize - 1)) {
			row = new RowInfo(RowType.JKDIAG2, currI, 0, boardSize - 1);
			if (!rowMap.containsKey(row)) {
				for (int j = 0, k = boardSize - 1; j < boardSize && k >= 0; j++, k--)
					row.addToken(getValue(currI, j, k));
				rowMap.put(row, currCoord);
			}
		}

		// check major diagonals
		if (currI == currJ && currI == currK) {
			row = new RowInfo(RowType.MAJDIAG1, 0, 0, 0);
			if (!rowMap.containsKey(row)) {
				for (int i = 0, j = 0, k = 0; i < boardSize && j < boardSize
						&& k < boardSize; i++, j++, k++)
					row.addToken(getValue(i, j, k));
				rowMap.put(row, currCoord);
			}
		}

		else if (currI == currK && currI + currJ == boardSize - 1) {
			row = new RowInfo(RowType.MAJDIAG2, 0, boardSize - 1, 0);
			if (!rowMap.containsKey(row)) {
				for (int i = 0, j = boardSize - 1, k = 0; i < boardSize
						&& j >= 0 && k < boardSize; i++, j--, k++)
					row.addToken(getValue(i, j, k));
				rowMap.put(row, currCoord);
			}
		}

		else if (currI == currJ && currI + currK == boardSize - 1) {
			row = new RowInfo(RowType.MAJDIAG3, 0, 0, boardSize - 1);
			if (!rowMap.containsKey(row)) {
				for (int i = 0, j = 0, k = boardSize - 1; i < boardSize
						&& j < boardSize && k >= 0; i++, j++, k--)
					row.addToken(getValue(i, j, k));
				rowMap.put(row, currCoord);
			}
		} else if (currI + currJ == boardSize - 1 && currI + currK == boardSize - 1) {
			row = new RowInfo(RowType.MAJDIAG4, 0, boardSize - 1, boardSize - 1);
			if (!rowMap.containsKey(row)) {
				for (int i = 0, j = boardSize - 1, k = boardSize - 1; i < boardSize
						&& j >= 0 && k >= 0; i++, j--, k--)
					row.addToken(getValue(i, j, k));
				rowMap.put(row, currCoord);
			}
		}
	}

	/**
	 * Class to provide information about a given ScoreFour move.
	 */
	private class MoveStatus implements Comparable<MoveStatus> {
		public static final int defaultRank = 7;
		/**
		 * Records the coordinates of the position in the board
		 */
		private final int iPos, jPos;
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
		
		private MoveStatus(int iPos, int jPos) {
			this.iPos = iPos;
			this.jPos = jPos;
			direction = new RowInfo[7];
			playerRowsOf = new int[boardSize + 1];
			playerGoodRowsOf = new int[boardSize + 1];
			enemyRowsOf = new int[boardSize];
			enemyGoodRowsOf = new int[boardSize + 1];
			badMove = false;
			rank = defaultRank;
			randFactor = Math.random();
		}
		private void setDirection(RowInfo row) {
			switch (row.type) {
			case HORIZ1:
				direction[0] = row;
				break;
			case HORIZ2:
				direction[1] = row;
				break;
			case VERT:
				direction[2] = row;
				break;
			case IJDIAG1:
				direction[3] = row;
				break;
			case IJDIAG2:
				direction[3] = row;
				break;
			case IKDIAG1:
				direction[4] = row;
				break;
			case IKDIAG2:
				direction[4] = row;
				break;
			case JKDIAG1:
				direction[5] = row;
				break;
			case JKDIAG2:
				direction[5] = row;
				break;
			case MAJDIAG1:
				direction[6] = row;
				break;
			case MAJDIAG2:
				direction[6] = row;
				break;
			case MAJDIAG3:
				direction[6] = row;
				break;
			case MAJDIAG4:
				direction[6] = row;
				break;
			default:
				break;
			
			}
		}
		
		private void analyseMove() {
			for (RowInfo r : direction) {
				if (r != null) {
					r.count();
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
		private final RowType type;
		private int refI, refJ, refK;
		private final char[] token;
		private int tokenCount;
		private int usCount, themCount;
		/**
		 * Keeps track of whether the row is unable to be blocked completely.
		 * This would occur in horizontal or diagonal rows where there is at least 
		 * one space below one or more of the coordinates in the row
		 */
		private boolean unBlockable;
		private boolean playerIsBlocked, enemyIsBlocked = true;
		
		private RowInfo(RowType type, int refI, int refJ, int refK) {
			this.type = type;
			this.refI = refI;
			this.refJ = refJ;
			this.refK = refK;
			token = new char[boardSize];
			tokenCount = 0;
			usCount = themCount = 0;
			unBlockable = playerIsBlocked = enemyIsBlocked = false;
		}
		/**
		 * needed so that only one copy of each identical row is added to the rowMap
		 * @param otherRow other RowInfo to compare
		 * @return true if the rows are of the same type and have the same reference coords
		 * (Therefore the major diagonals should only be mapped once per turn)
		 */
		@Override
		public boolean equals(Object otherRow) {
			if (otherRow.getClass() != RowInfo.class)
				return false;
			else if (this.type == ((RowInfo)otherRow).type && this.refI == ((RowInfo)otherRow).refI 
					&& this.refJ == ((RowInfo)otherRow).refJ && this.refK == ((RowInfo)otherRow).refK)
				return true;
			else return false;
		}

		public void addToken(char tokenColour) {
				token[tokenCount] = tokenColour;
				tokenCount++;
		}
		/**
		 * finds the value of the board, any number of spaces below or above a specific token in the row
		 * @param index the index of the token in this object's token array
		 * @param verticalOffset the number of spaces above the token to get the value from (make negative for spaces below)
		 * @return the character at the specified spot in the grid
		 */
		private char findCharOffset(int index, int verticalOffset) {
			/* values of the row's identifying coordinate
			 * which can be transformed into the coordinate below the index character */ 
			int i = refI;
			int j = refJ;
			int k = refK;
			switch (type) {
			case HORIZ1:
				i += index;
				break;
			case HORIZ2:
				j += index;
				break;
			case VERT:
				k += index;
				break;
			case IJDIAG1:
				i += index;
				j += index;
				break;
			case IJDIAG2:
				i += index;
				j -= index;
				break;
			case IKDIAG1:
				i += index;
				k += index;
				break;
			case IKDIAG2:
				i += index;
				k -= index;
				break;
			case JKDIAG1:
				j += index;
				k += index;
				break;
			case JKDIAG2:
				j += index;
				k -= index;
				break;
			case MAJDIAG1:
				i += index;
				j += index;
				k += index;
				break;
			case MAJDIAG2:
				i += index;
				j -= index;
				k += index;
				break;
			case MAJDIAG3:
				i += index;
				j += index;
				k -= index;
				break;
			case MAJDIAG4:
				i += index;
				j -= index;
				k -= index;
				break;
			default:
				break;
			}
			k += verticalOffset;
			return getValue(i, j, k);
		}

		private void count() {
			// counts opponent's tokens
			for (int charCount = 0; charCount < token.length; charCount++) {
				if (token[charCount] == colour) {
					usCount++; // counts player's tokens
					enemyIsBlocked = true;
				}
				else if (token[charCount] == enemyColour) {
					themCount++; // counts opponent's tokens
					playerIsBlocked = true;
				}
				else if (token[charCount] == 0) { // coordinate is empty, and...
					if (type != RowType.VERT && findCharOffset(charCount, -1) == 0)
					// place below this coordinate is also empty, meaning the row cannot be blocked yet
					unBlockable = true;
				}
			}
			usCount++; //accounts for potential move played
		}
	}
	private enum RowType {
		HORIZ1, HORIZ2, VERT, IJDIAG1, IJDIAG2, IKDIAG1, IKDIAG2, 
		JKDIAG1, JKDIAG2, MAJDIAG1, MAJDIAG2, MAJDIAG3, MAJDIAG4;
	}
}
