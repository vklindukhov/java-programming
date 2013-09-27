package part1;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class JFrameDemo {
	public static final int WIDTH = 300, HEIGHT = 200;
	
	public static void main(String[] args) {
		JFrame myWindow = new JFrame();
		myWindow.setSize(WIDTH, HEIGHT);
		JLabel myLabel = new JLabel("Please don't click that button!");
		myWindow.getContentPane().add(myLabel);
		WindowDestroyer myListener = new WindowDestroyer();
		myWindow.addWindowListener(myListener);
		myWindow.setVisible(true);
	}
}
