package run;

import java.util.Scanner;

import player.Seafood6;
import player.Seafood7;
import core.Player;
import core.ScoreFour;

public class GameStats {
	public static void main(String[] args) {
		int games = 0;
		Player p1 = new Seafood7();
		Player p2 = new Seafood6();
		System.out.println("Enter number of games to play");
		Scanner numberInput = new Scanner(System.in);
		games = numberInput.nextInt();
		numberInput.close();
		
		int p1Wins = 0;
		int p2Wins = 0;
		for (int i = 0; i < games; i++) {
			ScoreFour game = new ScoreFour(p1, p2);
			Player winner = game.run();
			if (winner == p1)
				p1Wins++;
			else if (winner == p2)
				p2Wins++;
		}
		System.out.println("Games played: " + games);
		System.out.println("Player 1 won " + p1Wins + " times");
		System.out.println("Player 2 won " + p2Wins + " times");
	}
}
