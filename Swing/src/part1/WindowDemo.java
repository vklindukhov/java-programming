package part1;

import java.awt.Color;

public class WindowDemo {
	public static void main(String[] args) {
		FirstWindow window1 = new FirstWindow();
		SecondWindow window2 = new SecondWindow(Color.BLUE);
		SecondWindow window3 = new SecondWindow();
		SecondWindow window4 = new SecondWindow(Color.GREEN);
		window1.setVisible(true);
		window2.setVisible(true);
		window3.setVisible(true);
		window4.setVisible(true);
	}
}
