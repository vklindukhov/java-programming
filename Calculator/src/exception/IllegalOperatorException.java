/**
 * @author Max Fisher
 * @version 1.0
 */
package exception;

import dict.Operator;

public class IllegalOperatorException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public IllegalOperatorException() {
		super ("Illegal or undefined operator");
	}
	public IllegalOperatorException(String message) {
		super(message);
	}
	public IllegalOperatorException(Operator o) {
		super ("Illegal operator: " + o);
	}
	public IllegalOperatorException(Operator o, String expectedType) {
		super ("Wrong operator type: " + o.type + "\nExpected type: " + expectedType);
	}

}
