/**
 * @author Max Fisher
 * @version 2.0
 */
package exception;

public class IllegalExpressionException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public IllegalExpressionException() {
		super("Error - Illegal expression");
	}
	public IllegalExpressionException(String message) {
		super("Error - Illegal expression (" + message + ")");
	}
}
