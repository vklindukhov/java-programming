package part2;

import javax.swing.JOptionPane;

public class LoadingBar {
	public static void main(String[] args) {
		int width = 25;
		int repetitions = 150;
		for (int count = 0; count < repetitions; count++) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < width; i++) {
				if (i == count % width)
					sb.append(">");		
				else sb.append("_");
			}
			JOptionPane.showMessageDialog(null, sb.toString());
		}
		System.exit(0);
	}

}
