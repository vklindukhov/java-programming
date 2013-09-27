package part1;

import javax.swing.JFrame;
import java.awt.Graphics;

public class Pattern extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static final int INNER_DIAM = 120; //diameter of inner circles
	private static final int OUTER_DIAM = 200;// diameter of outer circles
	private static final int WINDOW_SIZE = INNER_DIAM + 2*OUTER_DIAM;
	private static final int BOX_SIZE = INNER_DIAM + OUTER_DIAM;
	private static final int SPOT_RATIO = 5; // ratio of sizes of central circle to spot in middle
	private static final int SPOT_DIAM = (INNER_DIAM/SPOT_RATIO); // diameter of spot
	

	
	public Pattern() {
		setSize(WINDOW_SIZE, WINDOW_SIZE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		Pattern guiWindow = new Pattern();
		guiWindow.setVisible(true);
	}
	public void paint(Graphics canvas) {
		// Draw enclosing square
		canvas.drawRect(OUTER_DIAM/2, OUTER_DIAM/2, BOX_SIZE, BOX_SIZE);
		// Draw outer circles
		canvas.drawArc(BOX_SIZE/2, 0, OUTER_DIAM, OUTER_DIAM, 180, 180);
		canvas.drawArc(0, BOX_SIZE/2, OUTER_DIAM, OUTER_DIAM, 270, 180);
		canvas.drawArc(BOX_SIZE/2, BOX_SIZE, OUTER_DIAM, OUTER_DIAM, 0, 180);
		canvas.drawArc(BOX_SIZE, BOX_SIZE/2, OUTER_DIAM, OUTER_DIAM, 90, 180);
		// Draw inner circle
		canvas.drawOval(OUTER_DIAM, OUTER_DIAM, INNER_DIAM, INNER_DIAM);
		// Draw spot in middle
		canvas.fillOval((WINDOW_SIZE - SPOT_DIAM)/2, (WINDOW_SIZE - SPOT_DIAM)/2, SPOT_DIAM, SPOT_DIAM);
	}
}
