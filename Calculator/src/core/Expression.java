/**
 * @author Max Fisher
 * @version 1.0
 */
package core;

import java.math.BigDecimal;

import dict.Operator;

import exception.IllegalOperatorException;

import function.Absolute;
import function.Cosine;
import function.Divide;
import function.Exponent;
import function.Exponential;
import function.Logarithm;
import function.Minus;
import function.NaturalLogarithm;
import function.Negative;
import function.Plus;
import function.Positive;
import function.Power;
import function.Sine;
import function.SquareRoot;
import function.Tangent;
import function.Times;

public abstract class Expression {
	protected String stringVal;
	protected MathToken[] operand;
	private final Operator operator;
		
	/**
	 * @param operator - Operation of the expression. Used to check validity of construction
	 * @param operand - Expression array containing operands
	 * @throws IllegalOperatorException if the operands are incorrect or the number of operands is wrong
	 */
	public Expression(Operator operator, MathToken ... operand) 
			throws IllegalOperatorException {
		int numOperands = operand.length;
		this.operand = operand;
		this.operator = operator;
		if (numOperands == 1) {
			stringVal = operator.symbol + "(" + operand[0] + ")";
			if (operator.type != 1)
				throw new IllegalOperatorException(operator, "Unary");
		} else if (numOperands == 2) {
			stringVal = operand[0] + " " + operator.symbol + " " + operand[1];
			if (operator.type != 2)
				throw new IllegalOperatorException(operator, "Binary");
		} else if (numOperands == 0) {
			throw new IllegalOperatorException("No operands");
		} else {
			throw new IllegalOperatorException("Too many operators");
		}
		// else stringVal = o.symbol + "(" + operand[0] + ", " + ... + ", "
		// + operand[n] + ")"; - general case
	}

	/**
	 * Computes the operation and returns the result as a BigDecimal
	 * <p>
	 * Precondition: the operands all have values
	 * @return the result of the computation as a BigDecimal
	 * @ one or more operands do not have values
	 * @throws ArithmeticException Operation is mathematically undefined
	 */
	public abstract BigDecimal compute();
	
	public Operator getType() {
		return operator;
	}

	/**
	 * Instantiates an Expression subclass from a parsed TokenList
	 * @param operator The operand found by parsing the TokenList
	 * @param operand An array containing the operands of the new Expression
	 * @return an instance of the specific Operation subclass, determined by lastOperator
	 * @throws IllegalOperatorException if the operands are inappropriate for the Expression
	 * @see #Expression(Operator, MathToken...)
	 */
	protected static Expression instantiate(Operator operator, MathToken ... operand) throws IllegalOperatorException {
		Expression newExp = null;
		switch (operator) {
		case DIVIDE: newExp = new Divide(operand[0], operand[1]); break;
		case EXP: newExp = new Exponent(operand[0], operand[1]); break;
		case MINUS: newExp = new Minus(operand[0], operand[1]); break;
		case PLUS: 	newExp = new Plus(operand[0], operand[1]); break;
		case POWEROF: newExp = new Power(operand[0], operand[1]); break;
		case TIMES: newExp = new Times(operand[0], operand[1]); break;
		case ABSOLUTE: newExp = new Absolute(operand[0]); break;
		case COSINE: newExp = new Cosine(operand[0]); break;
		case EXPONENTIAL: newExp = new Exponential(operand[0]); break;
		case LOG: newExp = new Logarithm(operand[0]); break;
		case NATLOG: newExp = new NaturalLogarithm(operand[0]); break;
		case NEGATIVE: newExp = new Negative(operand[0]); break;
		case SINE: newExp = new Sine(operand[0]); break;
		case TANGENT: newExp = new Tangent(operand[0]); break;
		case SQROOT: newExp = new SquareRoot(operand[0]); break;
		case POSITIVE: newExp = new Positive(operand[0]); break;
		}
		return newExp;
	}
	@Override
	public String toString() {
		return stringVal;
	}
}
