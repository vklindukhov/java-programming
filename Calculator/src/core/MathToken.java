/**
 * Represents different parts of a mathematical expression using immutable objects to aid in parsing
 * @author Max Fisher
 * @version 2.0
 */
package core;

import java.math.BigDecimal;

import dict.Constant;
import dict.Operator;
import dict.Token;
import exception.OverwriteException;

public class MathToken {
	private Token type;
	private Object data;
	BigDecimal value; // for Operations
	
	/** 
	 * Creates a new token, checking that the data is appropriate to the token's type.
	 * For example, the object supplied when creating a 'Number' token must 
	 * be a BigDecimal. An exception is thrown if the object fails this check.
	 * @param type the type of token to create
	 * @param data information associated with the token
	 * @throws IllegalArgumentException
	 */
	public MathToken(Token type, Object data) throws IllegalArgumentException {
		this.type = type;
		this.value = null;
		try {
			switch (type) {
			case BIN_OPERATOR:
				this.data = (Operator) data;
				break;
			case COMMA:
				this.data = ",";
				break;
			case CONSTANT:
				this.data = (Constant) data;
				this.value = ((Constant) data).getValue();
				break;
			case L_BRACKET:
				this.data = "(";
				break;
			case NUMBER:
				this.data = data; // stores string used to parse for number, if string is passed
				if (data instanceof String) {
					value = new BigDecimal((String)data);
				} else {
					value = (BigDecimal) data;
				}
				break;
			case OPERATION:
				this.data = (Expression) data;
				break;
			case SUBLIST:
				this.data = (TokenList) data;
				break;
			case R_BRACKET:
				this.data = ")";
				break;
			case UN_OPERATOR:
				this.data = (Operator) data;
				break;
			default:
				throw new IllegalArgumentException();
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException();
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}

	}
	/**
	 * Constructor for token types which require no input data such as brackets.
	 * An exception is thrown if this constructor is used with another token type.
	 * @param type
	 * @throws IllegalArgumentException
	 */
	public MathToken(Token type) throws IllegalArgumentException {
		this.type = type;
		switch (type) {
		case COMMA:
			this.data = ",";
			break;
		case L_BRACKET:
			data = "(";
			break;
		case R_BRACKET:
			data = ")";
			break;
		default:
			throw new IllegalArgumentException();
		}
	}
	@Override
	public String toString() {
		if (type == Token.SUBLIST)
			return "E";
		else 
			return data.toString();
	}
	public Token getType() {
		return type;
	}
	
	public Object getData() {
		switch (type) {
		case OPERATION:
			return (Expression) data;
		case SUBLIST:
			return ((TokenList) data);
		case NUMBER:
			return value;
		default: 
			return data;
		}
	}
	protected void setValue(BigDecimal value) {
		if (this.value == null) {
			this.value = value;
		} else {
			throw new OverwriteException("value", this.toString());
		}
	}
	
	public BigDecimal getValue() {
		if (type == Token.OPERATION && value == null) {
			value = ((Expression) data).compute();
		}
		return value;
	}
	
	@Override
	public boolean equals(Object other) {
		boolean areEqual = false;
		if (other == null || !(other instanceof MathToken))
			areEqual = false;
		else if (type == ((MathToken) other).type && (data.equals(((MathToken) other).data)))
			areEqual = true;
		return areEqual;
	}
	@Override
	public MathToken clone() {
		return new MathToken(type, getData()); 
	}
}
