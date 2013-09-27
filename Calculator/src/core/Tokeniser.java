/**
 * Takes a mathematical expression and makes it into a format which can be 
 * parsed by a calculator parser more easily.
 * 
 * @author Max Fisher
 * @version 2.0
 */
package core;

import dict.Constant;
import dict.Operator;
import dict.Token;
import exception.IllegalExpressionException;

public class Tokeniser {
	/**
	 * Runs the Tokeniser on an input String
	 */
	public static TokenList format(String input) throws IllegalExpressionException {
		return formBracketTree(tokenise(input));
	}
	
	/**
	 * Takes a string representation of a mathematical expression and creates 
	 * a List of objects representing the different parts of the expression.
	 * 
	 * @param input the mathematical expression to turn into tokens
	 * @return a list containing the same expression encoded in tokens
	 * @throws IllegalExpressionException if the input expression is incorrect in some way
	 */
	
	protected static TokenList tokenise(String input) throws IllegalExpressionException {
		TokenList tokens = new TokenList();
		if (input == null || input.isEmpty())
			return tokens;
		int lBrackets = 0;
		int rBrackets = 0;
		for (int index = 0; index < input.length(); index++) {
			boolean symbolFound = false; //double check that character has been recognised
			char current = input.charAt(index);
			// check for brackets or whitespace
			if (Character.isWhitespace(current)) {
				symbolFound = true;
			} else if (current == '(') {
				tokens.add(new MathToken(Token.L_BRACKET));
				lBrackets++;
				symbolFound = true;
			} else if (current == ')') {
				tokens.add(new MathToken(Token.R_BRACKET));
				rBrackets++;
				if (rBrackets > lBrackets) // make sure bracketing up to this point is valid
					throw new IllegalExpressionException("invalid bracketing");
				symbolFound = true;
			} else if (current == ',') {
				tokens.add(new MathToken(Token.COMMA));
				symbolFound = true;
			} else if (Character.isDigit(current)) {
				//looks ahead for more digits to capture the entire number
				int lookahead;
				boolean decimalPointFound = false;
				for (lookahead = 1; index + lookahead < input.length(); lookahead++) {
					char numericalChar = input.charAt(index + lookahead);
					if (Character.isDigit(numericalChar)) {//character is neither a digit
						continue;
					} else if (numericalChar == '.' && decimalPointFound == true) {
						throw new IllegalExpressionException("invalid decimal point");
						//too many decimal points!
					} else if (numericalChar == '.' && index + lookahead < input.length() 
							&& Character.isDigit(input.charAt(index + lookahead + 1))
							&& decimalPointFound == false) {
						//character is a decimal point followed by a digit (first line ensures no OutOfBounds)
						//and there has not been a decimal point found previously
						decimalPointFound = true;
						continue;
					}	
					else 
						break;
				}
				// number is given by input.substring(index, index + lookahead), as 
				//input.CharAt(index + lookahead) is the first character to fail the test
				tokens.add(new MathToken(Token.NUMBER, input.substring(index, index + lookahead)));
				symbolFound = true;
				index = index + lookahead - 1; // so that after the update we will check the character that just failed
			} else if (!Character.isLetterOrDigit(current)) {
				for (Operator op : Operator.values()) {
					if (String.valueOf(current).equals(op.symbol)) {
						if (op.type == 2) {
							tokens.add(new MathToken(Token.BIN_OPERATOR, op));
						} else if (op.type == 1) {
							tokens.add(new MathToken(Token.UN_OPERATOR, op));
						}
					symbolFound = true;
					break;
					}
				}
			} else if (Character.isLetter(current)) {
				// looks ahead for more letters to capture the entire symbol
				int lookahead;
				for (lookahead = 1; index + lookahead < input.length(); lookahead++) {
					if (!(Character.isLetter(input.charAt(index + lookahead))))
						break;
				}
				String symbolicChars = input.substring(index, index + lookahead);
				index = index + lookahead - 1;

				// check if it's a constant
				for (Constant cst : Constant.values()) {
					if (symbolicChars.equalsIgnoreCase(cst.symbol)) {
						tokens.add(new MathToken(Token.CONSTANT, cst));
						symbolFound = true;
						break;
					}
				}
				// check if it's an operator
				if (!symbolFound) {
					for (Operator op : Operator.values()) {
						if (symbolicChars.equals(op.symbol)) {
							if (op.type == 2) {
								tokens.add(new MathToken(Token.BIN_OPERATOR, op));
							} else if (op.type == 1) {
								tokens.add(new MathToken(Token.UN_OPERATOR, op));
							}
							symbolFound = true;
							break;
						}
					}
				}
				// what is it???
				if (!symbolFound)
					throw new IllegalExpressionException("illegal characters include: " + symbolicChars); 
			} 
			if (!symbolFound) // character is unrecognised
				throw new IllegalExpressionException("unrecognised character: '" + String.valueOf(current) + "'");
		}
		// make sure total number of brackets is valid
				if (lBrackets != rBrackets)
					throw new IllegalExpressionException("invalid bracketing");
		tokens.trimToSize();
		return tokens;
	}
	
	/**
	 * Replaces the bracketed tokens in the MathToken list with SUBEXPRESSION MathTokens, 
	 * whose data is another MathToken list with those replaced MathTokens. 
	 * (Brackets are removed as well) This method is run recursively to create a 
	 * tree of MathTokens which can then be parsed
	 * @throws IllegalExpressionException if bracketing is mathematically invalid
	 */
	protected static TokenList formBracketTree(TokenList root) throws IllegalExpressionException {
		TokenList newList = new TokenList();
		for (int index = 0; index < root.size(); index++) {
			if (root.get(index).getType().equals(Token.L_BRACKET)) {
				int lBrackets = 1, rBrackets = 0;
				TokenList tempList = new TokenList();
				for (int index2 = index + 1; index2 < root.size(); index2++) { 
					// starts eating tokens from after the opening bracket
					if (root.get(index2).getType().equals(Token.L_BRACKET)) {
						lBrackets++;
						MathToken ex = root.get(index2);
						tempList.add(ex);
					} else if (root.get(index2).getType().equals(Token.R_BRACKET)) {
						rBrackets++;
						if (lBrackets == rBrackets) { // index and index2 refer to outermost left and right brackets
							tempList.trimToSize();
							// recursive step
							newList.add(new MathToken(Token.SUBLIST, formBracketTree(tempList)));
							index = index2;
							break;
						} else
							tempList.add(root.get(index2));
					} else
						tempList.add(root.get(index2));
				}
				//second bracket has not been found
				if (lBrackets != rBrackets)
					throw new IllegalExpressionException("matching right bracket not found");
			} 
			// these should always be picked up by the matching left bracket
			else if (root.get(index).getType().equals(Token.R_BRACKET))
				throw new IllegalExpressionException("invalid bracketing");
			else
				newList.add(root.get(index));
		}
		newList.trimToSize();
		return newList;
	}
}
