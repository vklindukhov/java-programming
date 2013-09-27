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


public class Plus extends Expression {

	public Plus(MathToken operand, MathToken operand2)
			throws IllegalOperatorException {
		super(Operator.PLUS, operand, operand2);
	}

	@Override
	public BigDecimal compute() {

		return (operand[0].getValue().add(operand[1].getValue()));
	}
}
