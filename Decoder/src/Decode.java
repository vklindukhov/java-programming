public class Decode {
	public static final String code = "AWVLI QIQVT QOSQO ELGCV IIQWD LCUQE EOENN WWOAO "
			+ "LTDNU QTGAW TSMDO QTLAO QSDCH PQQIQ DQQTQ OOTUD "
			+ "BNIQH BHHTD UTEET FDUEA UMORE SQEQE MLTME TIREC "
			+ "LICAI QATUN QRALT ENEIN RKG";
	public static final Character[] alphabetCharsLower = 
		{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	public static final Character[] alphabetCharsUpper = 
		{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
		'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	public static void main(String[] args) {

		String tempCode = code.replaceAll(" ", "");
		tempCode = tempCode.replace("Q", " ");
		System.out.println(tempCode);

		char[] arrayCode = tempCode.toCharArray();
		GridWork.gridPrint(arrayCode, 11, 13, System.out);
		
		char[] sideways = GridWork.transpose(arrayCode, 11, 13);
		
		String rightway = new String(sideways);
		System.out.println(rightway);
		int[] letterCount2 = count(tempCode);
		printCount(letterCount2);

	}

	/**
	 * Rotates all characters of a String by a number of letters, ignoring case.
	 * Can also subtract the character index from the given number,
	 * for example to invert the letters Z -> A, Y -> B etc (use rot = 25)
	 * Any non letter character will be ignored.
	 * @param input the input string to rotate.
	 * @param rot the number of characters to rotate by.
	 * @return the rotated string, in upper case. Returns an null if the input was null.
	 */
	public static String rotate(String input, int rot, boolean subtract) {
		if (input == null)
			return null;
		char[] newChars = new char[input.length()];
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if ((int) c >= 65 && (int) c <= 90) { // upper case letter
				int baseCode = (int) input.charAt(i) - (int) 'A';
				if (subtract)
					baseCode = -1 * baseCode + 26; // adds 26 so mod doesn't
													// return a negative number
				int rotated = (baseCode + rot) % 26 + (int) 'A';
				newChars[i] = (char) rotated;
			} else if ((int) c >= 97 && (int) c <= 122) { // lower case letter
				int baseCode = (int) input.charAt(i) - (int) 'a';
				if (subtract)
					baseCode = -1 * baseCode + 26; // adds 26 so mod doesn't
													// return a negative number
				int rotated = (baseCode + rot) % 26 + (int) 'a';
				newChars[i] = (char) rotated;
			} else
				newChars[i] = c;
		}
		return new String(newChars);
	}
	/**
	 * Counts the number of each letter appearing in a String
	 * Ignores any character which is not a letter
	 * @param input the input string to count letters of
	 * @return an int array of length 26, whose value at each index corresponds
	 * to the number of that character (from A to Z) found in the String.
	 * If the input string is null, an empty array is returned.
	 */
	private static int[] count(String input) {
		int[] numChars = new int[26];
		if (input == null); // do nothing
		else {
			for (int i = 0; i < input.length(); i++) {
				char c = input.charAt(i);
				if ((int) c >= 65 && (int) c <= 90) { // upper case letter
					int baseCode = (int) input.charAt(i) - (int) 'A';
					numChars[baseCode]++;
				} 	else if ((int) c >= 97 && (int) c <= 122) { // lower case letter
					int baseCode = (int) input.charAt(i) - (int) 'a';
					numChars[baseCode]++;
				} // otherwise do nothing.
			}
		}
		return numChars;
	}
	/**
	 * Prints out the count() function nicely
	 */
	private static void printCount(int[] count) {
		if (count.length != 26) {
			return;
		}
		System.out.println("Count of: ");
		for (int i = 0; i < 26; i++) {
			System.out.print((char)((int)'A' + i) + "   ");
		}
		System.out.println("\n");
		for (int i = 0; i < 26; i++) {
			System.out.printf("%-4d", count[i]);
		}
		System.out.println("\n");
	}
	public static String removeDoubleChars(String input) {
		if (input == null)
			return null;
		StringBuilder noDoubles = new StringBuilder(input);
		for (int i = 1; i < noDoubles.length(); i++) {
			if (noDoubles.charAt(i) == noDoubles.charAt(i-1))
				noDoubles.deleteCharAt(i);
		}
		return noDoubles.toString();
	}
	/**
	 * Takes an input string, and for every instance of the character c, 
	 * removes that character and the one after it.
	 * @param input the input string to modify
	 * @param c the character to detect
	 * @return the string with the specified characters removed, 
	 * or null if the input string was null.
	 */
	public static String removeCharPair(String input, char c) {
		if (input == null)
			return null;
		StringBuilder noDoubles = new StringBuilder(input);
		for (int i = 0; i < noDoubles.length() - 1; i++) {
			if (noDoubles.charAt(i) == 'Q') {
				noDoubles.deleteCharAt(i+1);
				noDoubles.deleteCharAt(i);
				i--;
			}
		}
		return noDoubles.toString();
	}
	
}