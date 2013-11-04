import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class BouncingBallStable extends JFrame {
	private static final long serialVersionUID = 3211937133712601506L;
	// ball params
	private static final Color BALL_COLOR = Color.GREEN;
	private static final int BALL_SIZE = 80; // all length units in pixels
	private static final int DEFAULT_X_POSITION = 100; // starting horizontal position
	private static final int DEFAULT_Y_POSITION = 500; // starting height
	private static final int DEFAULT_X_VELOCITY = 300; // in pixels per second
	private static final int DEFAULT_Y_VELOCITY = 200;
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
	private static final double ROLLING_FRICTION = -10; // coefficient of rolling friction
	private static final double ROLLING_TOLERANCE = 5; // vertical speed below which to decide that ball is rolling
	private static final int TIME_DELAY_MILLIS = 0; //milliseconds
	private static final int TIME_DELAY_NANOS = 5000; //nanoseconds
	
	private long currentTime; // the last recorded time
	private boolean isStarted = false; // becomes true when mouse is clicked
	private double ballXPos = DEFAULT_X_POSITION; // displacement from left of ball to left boundary point; positive to the right
	private double ballYPos = DEFAULT_Y_POSITION; // displacement from bottom of ball to ground point; positive if ball is above ground
	private double ballXVel = DEFAULT_X_VELOCITY; // displacement from left of ball to left boundary point; positive to the right
	private double ballYVel = DEFAULT_Y_VELOCITY; // in pixels per second; positive is upwards
	private boolean ballIsRolling = false;
	
	public BouncingBallStable() {
		DrawCanvas canvas = new DrawCanvas(true);
		canvas.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setContentPane(canvas);
		pack();
		setTitle("Bouncing Ball");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

//		addMouseListener(new MouseAdapter() {
//			// starts the animation on mouse click
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				if (!isStarted) {
//					SwingUtilities.invokeLater(new GameCommands());
//					isStarted = true;
//					removeMouseListener(this);
//				}
//			}
//		});
//		addMouseListener(new MouseAdapter() {
//			//Pauses the game when the mouse is clicked
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				if (isStarted) {
//					try {
//						Thread.currentThread().wait();
//					} catch (InterruptedException e1) {}
//					isStarted = false;
//				}
//			}
//		});
//		addMouseListener(new MouseAdapter() {
//			// Resumes paused game on another mouse click
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				if (!isStarted) {
//					Thread.currentThread().notify();
//					isStarted = true;
//				}
//		}
//		});
	}
	/**
	 * Starts the game, invoking the thread using SwingUtilities
	 */
	public void startGame() {
		new Thread(new GameCommands()).start();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BouncingBallStable animation = new BouncingBallStable();
				animation.setLocationRelativeTo(null); // center on screen
				animation.setVisible(true);
				animation.startGame();
			}
		}
		);
	}
	public void updatePosition() {
		updatePosition(false);
	}

	/**
	 * get the current time in seconds, and find the time interval since the last time was recorded
	 * calculate change in ball velocity by multiplying acceleration due to gravity by time change
	 * change change in ball position by averaging new and old speeds and multiplying by time change
	 * if new ball position would be below ground point (i.e ballHeight < 0) then make signs of new position 
	 * and velocity positive, and multiply velocity by coefficient of restitution
	 * update current time, speed and position
	 *
	 * @param debug if true, the program outputs time interval data to a text file.
	 */
	public synchronized void updatePosition(boolean debug) {
		long timeInterval = System.nanoTime() - currentTime;
		currentTime += timeInterval; // update time quickly
		int windowWidth = getWidth(); // get current window width
		
		if (debug) { // print out timing info to text
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
		}
		
		double deltaT = timeInterval / 1000000000.0; // time change in seconds
		double deltaVy = deltaT * GRAVITY;
		double deltaVx = 0.0; // no change unless rolling
		double deltaY = deltaT * (ballYVel + deltaVy / 2); // include previous speed as well
		double deltaX = deltaT * ballXVel;
		ballXPos += deltaX;
		
		if (ballIsRolling) {
			deltaY = 0.0;
			deltaVy = 0.0;
			deltaVx = deltaT * ROLLING_FRICTION;
		}
		
		if (ballXVel < 0) {
			ballXVel -= deltaVx;
		} else {
		ballXVel += deltaVx;
		}
		ballYVel += deltaVy;
		ballYPos += deltaY;
		ballXPos += deltaX;

		if (Double.compare(ballYPos, 0) <= 0 && !ballIsRolling) {
			double groundSpeedSquared = ballYVel*ballYVel - 2* GRAVITY*ballYPos;
			// speed that the ball would have been travelling when it passed through the ground; from v^2 = u^2 + 2ar
			// accounts for delay in game updates by computer
			if (Double.compare(groundSpeedSquared - ROLLING_TOLERANCE, 0) < 0) {
				// if moving almost parallel to ground (i.e low vertical velocity and close to ground)
				// then treat as rolling
				ballIsRolling = true;
				ballYVel = 0.0;
				ballYPos = 0.0;
			}
			else {
			double correctedSpeed = -Math.sqrt(groundSpeedSquared);
			ballYPos = 0;// *= -1 * COEFF_REST;
			ballYVel = correctedSpeed * -1 * COEFF_REST;
			}
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
	/**
	 * Game code
	 */
	private class GameCommands implements Runnable {
		@Override
		public void run() {
			currentTime = System.nanoTime();
			while (true) {
				updatePosition(); // update the (x, y) position
				repaint(); // Refresh the JFrame. Calls paintComponent()
				try { // Delay and give other thread a chance to run
					Thread.sleep(TIME_DELAY_MILLIS, TIME_DELAY_NANOS); 
				} catch (InterruptedException ignore) {
				}
			}
		}
	}
}
