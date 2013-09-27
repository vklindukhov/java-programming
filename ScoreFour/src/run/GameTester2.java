package run;

import player.*;
import core.Player;
import core.ScoreFour;

public class GameTester2 {
	public static void main(String[] args) {
		Player p1 = new Seafood6();
		Player p2 = new Dummy();
		ScoreFour game = new ScoreFour(p1, p2);
		System.out.println("Winner: " + game.run(/*debug =*/true));

	}
}
