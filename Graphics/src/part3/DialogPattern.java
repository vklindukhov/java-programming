package part3;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DialogPattern extends JFrame {
	private static final long serialVersionUID = 1L;
	// all units are pixels
	private static int innerDiam; //diameter of inner circles
	private static int outerDiam; // diameter of outer circles
	private static int boxSize; //total size of image
	private static int spotDiam; // diameter of spot
	private static int lineSize; // thickness of lines
	private static Color outerColor = Color.BLACK,
			circleColor = Color.BLACK, spotColor = Color.BLACK; // colour choices
	
	private static final int INNER_DIAM_DEFAULT = 96;
	private static final int SPOT_DIAM_DEFAULT = 32;
	private static final int LINE_SIZE_DEFAULT = 1;
		
	public DialogPattern() {
		innerDiam = INNER_DIAM_DEFAULT;
		spotDiam = SPOT_DIAM_DEFAULT;
		lineSize = LINE_SIZE_DEFAULT;

		
		int spotChoice = JOptionPane.showConfirmDialog(null, "Make centre spot red?", 
				"Choice 1/4", JOptionPane.YES_NO_OPTION);
		if (spotChoice == JOptionPane.YES_OPTION)
			spotColor = Color.RED;
		else 
			spotColor = Color.BLACK;
		int circleChoice = JOptionPane.showConfirmDialog(null, "Make centre circle blue?", 
				"Choice 2/4", JOptionPane.YES_NO_OPTION);
		if (circleChoice == JOptionPane.YES_OPTION)
			circleColor = Color.BLUE;
		else
			circleColor = Color.BLACK;
		int outerChoice = JOptionPane.showConfirmDialog(null, "Make outer circles green?", 
				"Choice 3/4", JOptionPane.YES_NO_OPTION);
		if (outerChoice == JOptionPane.YES_OPTION)
			outerColor = Color.GREEN;
		else
			outerColor = Color.BLACK;
		int valuesChoice = JOptionPane.showConfirmDialog(null, "Enter dimensions?", 
				"Choice 4/4", JOptionPane.YES_NO_OPTION);
		if (valuesChoice == JOptionPane.YES_OPTION) {
			innerDiam = getNumber("inner circle diameter", INNER_DIAM_DEFAULT);
			spotDiam = getNumber("centre spot diameter", SPOT_DIAM_DEFAULT);
			lineSize = getNumber("line thickness", LINE_SIZE_DEFAULT);
		}
		outerDiam = 2*innerDiam;
		boxSize = innerDiam + outerDiam;
		
		setSize(boxSize, boxSize);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		DialogPattern guiWindow = new DialogPattern();
		guiWindow.setVisible(true);
	}
	public void paint(Graphics canvas) {
		super.paint(canvas);
		// Draw enclosing square
		canvas.setColor(Color.WHITE);
		canvas.fillRect(0, 0, boxSize, boxSize);
		// Draw outer circles
		canvas.setColor(outerColor);
		canvas.fillArc(innerDiam/2, -outerDiam/2, outerDiam, outerDiam, 180, 180);
		canvas.fillArc(innerDiam/2, innerDiam + outerDiam/2, outerDiam, outerDiam, 0, 180);
		canvas.fillArc(-outerDiam/2, innerDiam/2, outerDiam, outerDiam, 270, 180);
		canvas.fillArc(innerDiam + outerDiam/2, innerDiam/2, outerDiam, outerDiam, 90, 180);
		
		canvas.setColor(Color.WHITE);
		canvas.fillArc(innerDiam/2 + lineSize, -outerDiam/2 + lineSize, 
				outerDiam - 2*lineSize, outerDiam -2*+ lineSize, 180, 180);
		canvas.fillArc(innerDiam/2 + lineSize, innerDiam + outerDiam/2 + lineSize,
				outerDiam - 2*lineSize, outerDiam - 2*lineSize, 0, 180);
		canvas.fillArc(-outerDiam/2 + lineSize, innerDiam/2 + lineSize, outerDiam - 2*lineSize,
				outerDiam - 2*lineSize, 270, 180);
		canvas.fillArc(innerDiam + outerDiam/2 + lineSize, innerDiam/2 + lineSize,
				outerDiam - 2*lineSize, outerDiam - 2*lineSize, 90, 180);
		// Draw inner circle
		canvas.setColor(circleColor);
		canvas.fillOval(outerDiam/2, outerDiam/2, innerDiam, innerDiam);
		
		canvas.setColor(Color.WHITE);
		canvas.fillOval(outerDiam/2 + lineSize, outerDiam/2 + lineSize, 
				innerDiam - 2*lineSize, innerDiam - 2*lineSize);
		// Draw spot in middle
		canvas.setColor(spotColor);
		canvas.fillOval((boxSize - spotDiam)/2, (boxSize - spotDiam)/2, spotDiam, spotDiam);
	}
	/**
	 * Asks the user to enter a given quantity as an integer.
	 * @param query describes what the user has to enter.
	 * @param defaultValue the value assigned when the user enters no input
	 * @return the user's numerical answer to the query.
	 */
	private static int getNumber(String query, int defaultValue) {
		String message = "Enter " + query + " in pixels,\nor press enter for default value:";
		int quantity = 0;
		boolean goodInput = true;
		do {
				String userInput = JOptionPane.showInputDialog(message);
			try {
				if (userInput.equals(""))
					quantity = defaultValue;
				else
					quantity = Integer.parseInt(userInput);
				if (quantity >= 0)
					goodInput = true;
				else {
					JOptionPane.showMessageDialog(null, query + " can't be negative! Try again.");
					message = "Enter " + query + " in pixels:";
					goodInput = false;
				}
			} catch (NumberFormatException e) {
				message = "Invalid input.\nEnter " + query + " in pixels:";
				goodInput = false;
			}
		}
		while (goodInput == false);
		return quantity;
	}
}
