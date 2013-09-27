package part2;

import javax.swing.JOptionPane;

public class OptionPain {
	public static void main(String[] args) {
		int apples = getNumber("number of apples");
		int oranges = getNumber("number of oranges");

		int fruitsOfOurLabour = apples + oranges;
		
		JOptionPane.showMessageDialog(null, "You have " + fruitsOfOurLabour + " fruits!");
		
		System.exit(0);
	}
	/**
	 * Asks the user to enter a given quantity as an integer.
	 * @param query describes what the user has to enter.
	 * @return the user's numerical answer to the query.
	 */
	private static int getNumber(String query) {
		String message = "Enter " + query + ":";
		int quantity = 0;
		boolean goodInput = true;
		do {
				String userInput = JOptionPane.showInputDialog(message);
			try {
				if (userInput.equals(""))
					quantity = 0;
				else
					quantity = Integer.parseInt(userInput);
				if (quantity >= 0)
					goodInput = true;
				else {
					JOptionPane.showMessageDialog(null, query + " can't be negative! Try again.");
					message = "Enter " + query + ":";
					goodInput = false;
				}
			} catch (NumberFormatException e) {
				message = "Invalid input.\nEnter " + query + " in digits:";
				goodInput = false;
			}
		}
		while (goodInput == false);
		return quantity;
	}
}
