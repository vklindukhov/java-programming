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


public class Power extends Expression {

	public Power(MathToken operand, MathToken operand2)
			throws IllegalOperatorException {
		super(Operator.POWEROF, operand, operand2);
	}

	@Override
	public BigDecimal compute() {

		double operand0D = operand[0].getValue().doubleValue();
		double operand1D = operand[1].getValue().doubleValue();
		return (BigDecimal.valueOf(Math.pow(operand0D, operand1D)));
	}
}
