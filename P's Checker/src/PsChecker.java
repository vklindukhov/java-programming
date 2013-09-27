import javax.swing.JOptionPane;

public class PsChecker {
	private static final String[] goodName = new String[] {"Alex Yip", 
			"Samuel Baumgart", "Matthew Foster", "Max Fisher", 
			"Francis Potter", "Ian Matchett"};
	
	public static void main(String[] args) {
		String title = "P's Checker";
		while (true) {
			String name = JOptionPane.showInputDialog(null, 
					"Enter someone's full name to see if they have their P's:",
					title, JOptionPane.INFORMATION_MESSAGE);
			if (name == null) 
				break;
			if (!name.matches("[a-zA-z]+ ?[a-zA-z]*")) {
				JOptionPane.showMessageDialog(null, 
						"Well that's a silly name. Try again.", 
						title, JOptionPane.INFORMATION_MESSAGE);
				continue;
			}
			Object[] options = {"Check another", "Exit"};
			String response = nameMatches(name) ? 
					"Yes, they do have their P's!\nCheck another person?" 
					: "Maybe they do, maybe they don't, I don't know!\nCheck another person?";
			int selectedValue = JOptionPane.showOptionDialog(null, response, title, 
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if (selectedValue == 1)
			break;
		}
	}
	/**
	 * Checks the given String against a list of recognised names
	 * @param name the String to check
	 * @return true if the String matches one of the known names, otherwise false
	 * If only a first name is supplied, it will also match any given first names
	 */
	private static boolean nameMatches(String name) {
		boolean nameMatches = false;
		for (String good: goodName) {
			if (name.equalsIgnoreCase(good))
					nameMatches = true;
		}
		return nameMatches;
	}
}
