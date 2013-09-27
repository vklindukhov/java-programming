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


public class Sine extends Expression {

	public Sine(MathToken operand) throws IllegalOperatorException {
		super(Operator.SINE, operand);
	}

	@Override
	public BigDecimal compute() {

		double operandD = operand[0].getValue().doubleValue();
		return (BigDecimal.valueOf(Math.sin(operandD)));
	}
}
