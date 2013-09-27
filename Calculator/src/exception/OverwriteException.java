/**
 * @author Max Fisher
 * @version 1.0
 */
package exception;

import core.MathToken;

public class OverwriteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public OverwriteException() {
		super ("Error - Overwrite denied");
	}
	public OverwriteException(String pString, String fieldName) {
		super ("Error - Overwriting " + fieldName + " of Expression " + pString + " denied");
	}
	public OverwriteException(MathToken x, String fieldName) {
		super ("Error - Overwriting " + fieldName + " of MathToken " + x.toString() + " denied");
	}
}
