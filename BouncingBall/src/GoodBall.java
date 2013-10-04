import java.awt.*;
import javax.swing.*;

/** Bouncing Ball (Animation) via custom thread */
public class GoodBall extends JFrame {
	private static final long serialVersionUID = -2317915431127681538L;
	// Define named-constants
	private static final int CANVAS_WIDTH = 1500;
	private static final int CANVAS_HEIGHT = 1100;
	private static final int UPDATE_INTERVAL = 5; // milliseconds

	private DrawCanvas canvas; // the drawing canvas (extends JPanel)

	// Attributes of moving object
	private int x = 100; // top-left (x, y)
	private int y = 100;
	private int size = 90; // width and height
	private int xSpeed = 1; // moving speed in x and y directions
	private int ySpeed = 2; // displacement per step in x and y

	/** Constructor to setup the GUI components */
	public GoodBall() {
		canvas = new DrawCanvas(true);
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		this.setContentPane(canvas);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setTitle("Bouncing Ball");
		this.setVisible(true);

		// Create a new thread to run update at regular interval
		Thread updateThread = new Thread() {
			@Override
			public void run() {
				while (true) {
					update(); // update the (x, y) position
					repaint(); // Refresh the JFrame. Called back
								// paintComponent()
					try {
						// Delay and give other thread a chance to run
						Thread.sleep(UPDATE_INTERVAL); // milliseconds
					} catch (InterruptedException ignore) {
					}
				}
			}
		};
		updateThread.start(); // called back run()
	}

	/** Update the (x, y) position of the moving object */
	public void update() {
		x += xSpeed;
		y += ySpeed;
		if (x > CANVAS_WIDTH - size || x < 0) {
			xSpeed = -xSpeed;
		}
		if (y > CANVAS_HEIGHT - size || y < 0) {
			ySpeed = -ySpeed;
		}
	}

	/** DrawCanvas (inner class) is a JPanel used for custom drawing */
	class DrawCanvas extends JPanel {
		private static final long serialVersionUID = 5307036153137781017L;
		
		DrawCanvas(boolean isDoubleBuffered) {
			super(isDoubleBuffered);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g); // paint parent's background
			setBackground(Color.BLACK);
			g.setColor(Color.BLUE);
			g.fillOval(x, y, size, size); // draw a circle
		}
	}

	/** The entry main method */
	public static void main(String[] args) {
		// Run GUI codes in Event-Dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GoodBall(); // Let the constructor do the job
			}
		});
	}
}