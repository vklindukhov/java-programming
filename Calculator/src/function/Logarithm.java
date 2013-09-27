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


public class Logarithm extends Expression {

	public Logarithm(MathToken operand) throws IllegalOperatorException {
		super(Operator.LOG, operand);
	}

	@Override
	public BigDecimal compute() {

		double operandD = operand[0].getValue().doubleValue();
		return (BigDecimal.valueOf(Math.log10(operandD)));
	}
}
