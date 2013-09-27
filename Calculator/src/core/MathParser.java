package core;

import dict.Operator;
import dict.Token;
import exception.IllegalExpressionException;
import exception.IllegalOperatorException;

public class MathParser {
	/**
	 * Parses an input of mathematical tokens, 
	 * where there is only one operator per sublist
	 * @param input the expression as formatted by {@link Tokeniser#format(String)}
	 * @return a MathToken of type either {@link Token#OPERATION} or {@link Token#NUMBER},
	 * the former of which can be evaluated to give a result, and the latter of which the 
	 * result can be read directly
	 */
	
	public static MathToken parse(TokenList input) throws IllegalExpressionException, IllegalOperatorException {
		Operator operator = null;
		MathToken operand1 = null;
		MathToken operand2 = null;
		MathToken output = null;
		
		for (MathToken tok : input) {
			switch (tok.getType()) {
			case SUBLIST:
				MathToken sub = parse((TokenList) tok.getData());
				if (operand1 == null)
					operand1 = sub;
				else 
					operand2 = sub;
				break;
			case NUMBER:
			case CONSTANT:
				if (operand1 == null)
					operand1 = tok;
				else 
					operand2 = tok;
				break;
			case UN_OPERATOR:
			case BIN_OPERATOR:
				operator = (Operator) tok.getData();
				break;
			default:
				throw new IllegalExpressionException("Inappropriate token "
						+ "found while parsing: " + tok.toString());
			}
		}
		if (operand1 == null) {
			throw new IllegalExpressionException("No operand.");
		} else if (operator == null) {
			// nothing to calculate - just a number/constant
			if (operand2 == null) {
				output = new MathToken(Token.NUMBER, operand1.getValue());
			} else if (operand2 != null) {
				throw new IllegalExpressionException("Two operands and no operator!");
			}
		} else {
			Expression operation = null;
			if (operand2 == null) { // unary expression
				if (operator == Operator.MINUS) {
					operator = Operator.NEGATIVE;
				}
				operation = Expression.instantiate(operator, operand1);
			} else {
				operation = Expression.instantiate(operator, operand1, operand2);
			}
			output = new MathToken(Token.OPERATION, operation);
		}
		return output;
	}
}
