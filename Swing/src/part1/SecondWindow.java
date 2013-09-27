package part1;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class SecondWindow extends JFrame {
	private static final long serialVersionUID = 8462592207747811509L;
	public static final int WIDTH = 300, HEIGHT = 200;
	
	public SecondWindow() {
		super();
		setSize(WIDTH, HEIGHT);
		JLabel myLabel = new JLabel("Now available in colour!");
		getContentPane().add(myLabel);
		setTitle("Second Window");
		addWindowListener(new WindowDestroyer());
	}
	
	public SecondWindow(Color customColor) {
		this();
		getContentPane().setBackground(customColor);
	}
}