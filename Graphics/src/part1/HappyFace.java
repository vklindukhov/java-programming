package part1;

import javax.swing.JApplet;
import java.awt.Graphics;

public class HappyFace extends JApplet {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;
	
	public void paint(Graphics canvas) {
		canvas.drawOval(100, 50, 200, 200);
		canvas.fillOval(150, 100, 20, 20);
		canvas.fillOval(225, 100, 20, 20);
		canvas.fillArc(150, 160, 100, 50, 180, 180);
	}
}
