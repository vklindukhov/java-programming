package part1;

import javax.swing.JApplet;
import java.awt.Graphics;

public class Olympic extends JApplet {
	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;
	
	private static final short radius = 50;
	private static final short xSpacing = radius/5, ySpacing = -radius/2, xOffset = (radius+ xSpacing)/2;
	private static final short ringsEven = 3, ringsOdd = 2, numRows = 4;
	
	public void paint(Graphics canvas) {
		for (short i = 0; i < numRows; i++) {
			if (i % 2 == 0) {
				for (short j = 0; j < ringsEven; j++)
					canvas.drawOval(j*(radius + xSpacing), i*(radius + ySpacing), radius, radius);
			}
			else {
				for (short j = 0; j < ringsOdd; j++)
					canvas.drawOval(j*(radius + xSpacing) + xOffset, i*(radius + ySpacing), radius, radius);
			}
		}
	}
}
