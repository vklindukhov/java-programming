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


public class Times extends Expression {

	public Times(MathToken operand, MathToken operand2)
			throws IllegalOperatorException {
		super(Operator.TIMES, operand, operand2);
	}

	@Override
	public BigDecimal compute() {

		return (operand[0].getValue().multiply(operand[1].getValue()));
	}
}
