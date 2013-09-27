import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileEditor {
	public static void main(String[] args) {
		List<Integer> primes = new ArrayList<Integer>(10000);
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(new File("Primes.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		while (fileReader.hasNext()) {
			primes.add(fileReader.nextInt());
		}
		PrintWriter writer = null;
		try{
			writer = new PrintWriter("newPrimes.txt");
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		for (Integer prime: primes) {
			writer.print(prime);
			writer.print(" ");
		}
		writer.close();
		System.out.println("Done!");
	}
}
