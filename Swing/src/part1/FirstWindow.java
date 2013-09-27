package part1;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class FirstWindow extends JFrame {
	private static final long serialVersionUID = 8359732846710095768L;
	public static final int WIDTH = 300, HEIGHT = 200;
	
	public FirstWindow() {
		super();
		setSize(WIDTH, HEIGHT);
		JLabel myLabel = new JLabel("Don't close me!");
		getContentPane().add(myLabel);
		WindowDestroyer listener = new WindowDestroyer();
		addWindowListener(listener);
	}
}
