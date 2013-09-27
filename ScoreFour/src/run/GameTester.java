package run;

import mfis1267.LobsterMornay;
import player.*;
import core.Player;
import core.ScoreFour;

public class GameTester {
	public static void main(String[] args) {
		Player p1 = new Seafood7();
		Player p2 = new Seafood3();
		ScoreFour game = new ScoreFour(p1, p2);
		System.out.println("Winner: " + game.run(/*debug =*/false));

	}
}
