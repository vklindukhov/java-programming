
public class Hanoi {
	private int discs; // the game starts with this number of discs
	private char[] peg1;
	private char[] peg2;
	private char[] peg3;
	// arrays representing the pegs of the game, with 
	// enough virtual 'spaces' to accommodate all the discs at once
	// numbering starts at the bottom. That is, peg1[0] is the lowest disc on peg 1
	
	private Hanoi(int numDiscs) {
		this.discs = numDiscs;
		peg1 = new char[numDiscs];
		peg2 = new char[numDiscs];
		peg3 = new char[numDiscs];
		for (int i = discs; i >= 1; i--) { // places the discs on the first peg
			peg1[i - discs] = (char)(i + 48); // converts integers into their character representation (only works for i < 10)
		}	
	}
	public static void main(String[] args) {
		Hanoi game = new Hanoi(Integer.parseInt(args[0]));
		game.solve();
		
	}
	public void solve() {
		
	}
	public void recursiveStep(int num, char[] first, char[] last, char[] temp) {
		if (last[num - 1] == '1') //smallest disc on last peg ==> all discs on last peg
			return;
		
	}
	private int findEmptySpot(char[] peg) {
		int index = discs - 1; //starts at the top
		while (peg[index] == 0 && index > 0)
			index--;
		return index;
	}
	public void printState() {
		for (int i = discs - 1; i >= 0; i--) {
			if (peg1[i] != 0)
				System.out.print(peg1[i]);
			else System.out.print(".");
			System.out.print("   ");
			if (peg2[i] != 0)
				System.out.print(peg2[i]);
			else System.out.print(".");
			System.out.print("   ");
			if (peg3[i] != 0)
				System.out.print(peg3[i]);
			else System.out.print(".");
			System.out.println("");
		}
		System.out.println("************\n");
	}
}