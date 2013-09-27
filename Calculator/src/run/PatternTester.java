/**
 * @author Max Fisher
 * @version 1.0
 */
package run;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PatternTester {
//	private static final Pattern oldNumber = Pattern.compile("-??\\d+?\\.?\\d*[eE]?-?\\d*");
//	private static final Pattern oldNumber2 = Pattern.compile("-??\\d+?(\\.\\d+)?([eE]{1}-?\\d+)?");
//	private static final String decimalNumberRegex = "(?<![\\.\\d])-??\\d+?(\\.\\d+)?(?![\\.\\d])";
	private static final String exponentNumberRegex = "(?<![\\.\\dE-])-??\\d+?(\\.\\d+)?(E{1}-?\\d+)?(?![\\.\\dE])";
	private static final String testString = "-1200E4";
	private static BigDecimal number;

	public static void main(String[] args) {
		Matcher n = Pattern.compile(exponentNumberRegex).matcher(testString.toString());
		if (n.find()) {
			System.out.println(testString + " contains a number");
			System.out.println("The number is " + n.group());
			number = new BigDecimal(n.group());
			System.out.println("Value = " + number.toString());
		}
		else
			System.out.println(testString + " does not contain a number");
	}
}
