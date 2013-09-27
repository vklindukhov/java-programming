package part4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class BouncingBallFrame extends JFrame implements MouseListener {
	private static final long serialVersionUID = 3211937133712601506L;
	// ball params
	private static final Color BALL_COLOR = Color.RED;
	private static final int BALL_SIZE = 80; // all length units in pixels
	private static final int DEFAULT_X_POSITION = 100; // starting horizontal position
	private static final int DEFAULT_Y_POSITION = 500; // starting height
	private static final int DEFAULT_X_VELOCITY = 800; // in pixels per second
	private static final int DEFAULT_Y_VELOCITY = 800;
	private static final int MAX_X_VELOCITY = 100000;
	// boundary params
	private static final int DEFAULT_WIDTH = 1000; // right hand wall to bounce off
	private static final int DEFAULT_HEIGHT = 800; // where to draw the ground point
	private static final Color BACKGROUND_COLOR = Color.BLACK;
	
	/* physics params
	 *
	 * pixel density of my monitor is approx. 3.89 px/mm, so -9.80m/s^2 ~= -38100px/s^2
	 */
	private static final double GRAVITY = -8100; // pixels per second squared; positive is upwards acceleration
	private static final double COEFF_REST = 0.94; // coefficient of restitution
	private static final int TIME_DELAY = 10000; //nanoseconds
	
	private long currentTime; // the last recorded time
	private boolean isStarted = false; // becomes true when mouse is clicked
	private double ballXPos = DEFAULT_X_POSITION; // displacement from left of ball to left boundary point; positive to the right
	private double ballYPos = DEFAULT_Y_POSITION; // displacement from bottom of ball to ground point; positive if ball is above ground
	private double ballXVel = DEFAULT_X_VELOCITY; // displacement from left of ball to left boundary point; positive to the right
	private double ballYVel = DEFAULT_Y_VELOCITY; // in pixels per second; positive is upwards
	
	public BouncingBallFrame() {
		DrawCanvas canvas = new DrawCanvas(true);
		canvas.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setContentPane(canvas);
		pack();
		setTitle("Bouncing Ball");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!isStarted) {
			SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Thread() {
				@Override
				public void run() {
					currentTime = System.nanoTime();
					while (true) {
						updatePosition(); // update the (x, y) position
						repaint(); // Refresh the JFrame. Calls paintComponent()
						try { // Delay and give other thread a chance to run
							Thread.sleep(0, TIME_DELAY); // nanoseconds
						} catch (InterruptedException ignore) {
						}
					}
				}
				}.start();
				}
			});
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!isStarted) {
			SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Thread() {
				@Override
				public void run() {
					currentTime = System.nanoTime();
					while (true) {
						updatePosition(); // update the (x, y) position
						repaint(); // Refresh the JFrame. Calls paintComponent()
						try { // Delay and give other thread a chance to run
							Thread.sleep(0, TIME_DELAY); // nanoseconds
						} catch (InterruptedException ignore) {
						}
					}
				}
				}.start();
				}
			});
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!isStarted) {
			SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Thread() {
				@Override
				public void run() {
					currentTime = System.nanoTime();
					while (true) {
						updatePosition(); // update the (x, y) position
						repaint(); // Refresh the JFrame. Calls paintComponent()
						try { // Delay and give other thread a chance to run
							Thread.sleep(0, TIME_DELAY); // nanoseconds
						} catch (InterruptedException ignore) {
						}
					}
				}
				}.start();
				}
			});
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	public static void main(String[] args) {
		final BouncingBallFrame animation = new BouncingBallFrame();
		animation.setVisible(true);
	}

	public void updatePosition() {
		/* get the current time in seconds, and find the time interval since the last time was recorded
		 * calculate change in ball velocity by multiplying acceleration due to gravity by time change
		 * change change in ball position by averaging new and old speeds and multiplying by time change
		 * if new ball position would be below ground point (i.e ballHeight < 0) then make signs of new position 
		 * and velocity positive, and multiply velocity by coefficient of restitution
		 * update current time, speed and position
		 */
		long timeInterval = System.nanoTime() - currentTime;
		currentTime += timeInterval; // update time quickly
		FileOutputStream debugOut = null;
		PrintWriter debugWriter = null;
		try {
			debugOut = new FileOutputStream("debug.txt", true);
			debugWriter = new PrintWriter(debugOut);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		debugWriter.println(Long.toString(timeInterval));
		debugWriter.close();
//		if (timeInterval > previousTimeInterval*100)
//			break;
		double deltaT = timeInterval / 1000000000.0; // time change in seconds
		double deltaV = deltaT * GRAVITY;
		double deltaX = deltaT * ballXVel;
		double deltaY = deltaT * (ballYVel + deltaV / 2);
		ballXPos += deltaX;
		ballYPos += deltaY;
		ballYVel += deltaV;
		int windowWidth = getWidth();
		// if moving almost parallel to ground (i.e low vertical velocity and close to ground)
		// then treat as rolling
		if (Double.compare(ballYPos, 0.0) <= 0) {
			ballYPos = 0;// *= -1 * COEFF_REST;
			ballYVel *= -1 * COEFF_REST;
		}
		if (Double.compare(ballXPos, 0) <= 0) {
			ballXPos = 0;// *= -1 * COEFF_REST;
			ballXVel *= -1 * COEFF_REST;
		}
		else if (Double.compare(ballXPos + BALL_SIZE, windowWidth) >= 0) {
			ballXPos = windowWidth - BALL_SIZE;// - (ballXPos + BALL_SIZE -
												// RIGHT_BOUNDARY) * COEFF_REST;
			ballXVel *= -1 * COEFF_REST;
			if (Math.abs(ballXVel) > MAX_X_VELOCITY)
				ballXVel = (ballXVel >= 0) ? MAX_X_VELOCITY : -MAX_X_VELOCITY;
		}

	}

	/**
	 * Panel for custom painting (animation)
	 * @author Max Fisher
	 */
	private class DrawCanvas extends JPanel {
		private static final long serialVersionUID = 7445399201681102083L;

		private DrawCanvas() {
			this(false);
		}
		private DrawCanvas(boolean isDoubleBuffered) {
			super(isDoubleBuffered);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(BACKGROUND_COLOR);
			g.setColor(BALL_COLOR);
			int windowHeight = getHeight();
			g.fillOval((int)ballXPos, (int) (windowHeight - BALL_SIZE - ballYPos), BALL_SIZE, BALL_SIZE);
			g.setColor(Color.WHITE);
			g.drawOval((int)ballXPos, (int) (windowHeight - BALL_SIZE - ballYPos), BALL_SIZE, BALL_SIZE);
		}
	}
}
