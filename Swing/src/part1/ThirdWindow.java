package part1;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ThirdWindow extends JFrame {
	private static final long serialVersionUID = -1401944676545253273L;
	public static final int WIDTH = 300, HEIGHT = 200;
	
	public ThirdWindow() {
		super();
		setSize(WIDTH, HEIGHT);
		setTitle("Labelled window");
		addWindowListener(new WindowDestroyer());
		Container content = getContentPane();
		content.setLayout(new FlowLayout());
		content.add(new JLabel("First label here"));
		content.add(new JLabel("Second label there"));
		content.add(new JLabel("Third label anywhere"));
	}
	
	public static void main(String[] args) {
		ThirdWindow gui = new ThirdWindow();
		gui.setVisible(true);
	}
}
