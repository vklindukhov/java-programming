/**
 * @author Max Fisher
 * @version 1.0
 */

package run;

import java.math.BigDecimal;
import java.util.Scanner;

import core.MathParser;
import core.MathToken;
import core.TokenList;
import core.Tokeniser;
import exception.IllegalExpressionException;
import exception.IllegalOperatorException;


public class Calculator {
	
	public static void main(String[] args) {
		/*
		 * Get the input args, checking for errors
		 * set it as a static variable
		 * parse the expression
		 * evaluate it and print out result
		 */
		BigDecimal result = null;
		while (true) {
			try {
				String inputString = getInput(args);
				result = evaluate(inputString);
				break;
			}
			catch (IllegalExpressionException e) {
				System.out.println(e.getMessage());
				args = null;
				continue;
			} catch (IllegalOperatorException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Result: " + result.toString());
		//if (e.getClass().operand1)

	}
	/**
	 * Runs the calculator with the given input string
	 * @param input the expression to calculate
	 * @return the result of the calculation
	 * @throws IllegalExpressionException 
	 * @throws IllegalOperatorException 
	 */
	protected static BigDecimal evaluate(String input) throws IllegalExpressionException, IllegalOperatorException {
		TokenList inputTokens = Tokeniser.format(input);
		MathToken resultToken = MathParser.parse(inputTokens);
		return resultToken.getValue();
	}

	public static String getInput(String[] inputArray) {
		String input;
		if (inputArray == null || inputArray.length == 0) {
			System.out.println("Please enter your expression");
			Scanner scan = new Scanner(System.in);
			input = scan.nextLine();
		}
		else if (inputArray.length > 1) { // combines spaced out expressions into a single string
			StringBuilder stringRep = new StringBuilder();
			for (String s : inputArray)
			    stringRep.append(s + " ");
			input = stringRep.toString();
		}
		else
			input = inputArray[0];
		return input;
	}
}
