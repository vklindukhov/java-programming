/**
 * @author Max Fisher
 * @version 1.0
 */
package function;

import java.math.BigDecimal;

import core.MathToken;
import core.Expression;
import dict.Operator;
import exception.IllegalOperatorException;

public class Exponent extends Expression {

	public Exponent(MathToken operand, MathToken operand2)
			throws IllegalOperatorException {
		super(Operator.EXP, operand, operand2);
	}

	@Override
	public BigDecimal compute() throws ArithmeticException {
		int exponent = 0;
		exponent = operand[1].getValue().intValueExact();
		return (operand[0].getValue().scaleByPowerOfTen(exponent));

	}
}
