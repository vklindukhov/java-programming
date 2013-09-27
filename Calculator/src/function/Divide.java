/**
 * @author Max Fisher
 * @version 1.0
 */
package function;

import java.math.BigDecimal;
import java.math.MathContext;

import core.MathToken;
import core.Expression;
import dict.Operator;
import exception.IllegalOperatorException;


public class Divide extends Expression {

	public Divide(MathToken operand, MathToken operand2) throws IllegalOperatorException {
		super(Operator.DIVIDE, operand, operand2);
	}

	@Override
	public BigDecimal compute() throws ArithmeticException {
		return (operand[0].getValue().divide(operand[1].getValue(),
				MathContext.DECIMAL64));
	}
}
