package part4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JApplet;

public class BouncingBall extends JApplet {

	private static final long serialVersionUID = 3211937133712601506L;
	// ball params
	private static final Color BALL_COLOR = Color.RED;
	private static final int BALL_SIZE = 50; //in pixels
	private static final int DEFAULT_X_POSITION = 100; // starting horizontal position in pixels
	private static final int DEFAULT_Y_POSITION = 50; // starting height in pixels
	private static final int DEFAULT_X_VELOCITY = 600; // in pixels per second
	private static final int DEFAULT_Y_VELOCITY = -1200; // in pixels per second;
	// wall params
	private static final int GROUND_POINT = 800; // where to draw the ground point
	private static final int RIGHT_BOUNDARY = 1500; // right hand wall to bounce off
	private static final Color GROUND_COLOR = Color.BLUE;
	// physics params
	private static final double GRAVITY = -1960; // pixels per second squared; positive is upwards acceleration
	private static final double COEFF_REST = 0.95; // coefficient of restitution
	private static final int TIME_DELAY = 10; //milliseconds
	
	private Dimension size;
	private long currentTime; // the last recorded time
	private double ballXPos; // displacement from left of ball to left boundary point; positive to the right
	private double ballYPos; // displacement from bottom of ball to ground point; positive if ball is above ground
	private double ballXVel; // displacement from left of ball to left boundary point; positive to the right
	private double ballYVel; // in pixels per second; positive is upwards
	
	@Override
	public void init() {
        setSize(1500, 1000);
        size = getSize();
		ballXPos = DEFAULT_X_POSITION;
		ballXVel = DEFAULT_X_VELOCITY;
		ballYPos = DEFAULT_Y_POSITION;
		ballYVel = DEFAULT_Y_VELOCITY;
		currentTime = System.nanoTime();
	}
	@Override
	public void start() {
		while(true) {
			repaint();
			try {
				Thread.sleep(TIME_DELAY);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void paint(Graphics canvas) {
		super.paint(canvas);
		canvas.setColor(GROUND_COLOR);
		canvas.drawRect(0, GROUND_POINT, RIGHT_BOUNDARY, 1);
		canvas.drawRect(RIGHT_BOUNDARY, 0, 1, GROUND_POINT);
		canvas.setColor(BALL_COLOR);
		canvas.fillOval((int)ballXPos, (int) (GROUND_POINT - BALL_SIZE - ballYPos), BALL_SIZE, BALL_SIZE);
		canvas.setColor(Color.BLACK);
		canvas.drawOval((int)ballXPos, (int) (GROUND_POINT - BALL_SIZE - ballYPos), BALL_SIZE, BALL_SIZE);
	}
	
	@Override
	public void update(Graphics canvas) {
        Dimension newSize = getSize();
        if (size.equals(newSize)) {
            // Erase old ball
            canvas.setColor(getBackground());
            canvas.fillOval((int)ballXPos, (int) (GROUND_POINT - BALL_SIZE - ballYPos), BALL_SIZE, BALL_SIZE);
        } else {
            size = newSize;
            canvas.clearRect(0, 0, size.width, size.height);
        }
        updatePosition();
		paint(canvas);
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
		 double deltaT = timeInterval / 1000000000.0; // time change in seconds
		 double deltaV = deltaT * GRAVITY;
		 double deltaX = deltaT * ballXVel;
		 double deltaY = deltaT * (ballYVel + deltaV / 2);
		 ballXPos += deltaX;
		 ballYPos += deltaY;
		 ballYVel += deltaV;
		 if (Double.compare(ballYPos, 0.0) <= 0) {
		 	ballYPos *= -1 * COEFF_REST;
		 	ballYVel *= -1 * COEFF_REST;
		 }
		 if (Double.compare(ballXPos, 0) <= 0) {
			 ballXVel *= -1 * COEFF_REST;
			 ballXPos *= -1 * COEFF_REST;
		 }
		 else if (Double.compare(ballXPos + BALL_SIZE, RIGHT_BOUNDARY) >= 0) {
			 ballXVel *= -1 * COEFF_REST;
			 ballXPos = RIGHT_BOUNDARY - BALL_SIZE - (ballXPos + BALL_SIZE - RIGHT_BOUNDARY) * COEFF_REST;
		 }
		 currentTime += timeInterval;

	}
}
