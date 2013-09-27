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


public class Positive extends Expression {

	public Positive(MathToken operand) throws IllegalOperatorException {
		super(Operator.POSITIVE, operand);
	}

	@Override
	public BigDecimal compute() {

		return (operand[0].getValue());
	}
}
