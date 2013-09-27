/**
 * @author Max Fisher (mfis1267)
 * @version 1.1
 */
package player;

import util.Coordinate;
import core.Player;

public class Dummy implements Player {
	private String opponentName;
	private String outcome;
	private double lastRand;

	public Dummy() {
		opponentName = null;
		outcome = "Game in progress";
		lastRand = Math.abs(Math.sin(Math.E));
			}
	
	@Override
	public Coordinate getNextMove(char[][][] board, char colour) {
		int i = (int) Math.floor(nextRand()*4);
		int j = (int) Math.floor(nextRand()*4);
		Coordinate move = new Coordinate(i, j);
			return move;

	}
	private double nextRand() {
		lastRand = Math.abs(Math.sin(lastRand));
		return lastRand;
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


}
