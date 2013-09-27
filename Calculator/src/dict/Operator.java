/**
 * Lists mathematical operators with type and order of operations
 * 
 * @author Max Fisher
 * @version 2.0
 */
package dict;

public enum Operator {
	//binary operators
	PLUS("+", 2, 4), MINUS("-", 2, 4), TIMES("*", 2, 3), 
	DIVIDE("/", 2, 3), POWEROF("^", 2, 2), EXP("E", 2, 2),
	//unary operators
	NEGATIVE("--", 1, 1), LOG("log", 1, 1), NATLOG("ln", 1, 1),
	SINE("sin", 1, 1), COSINE("cos", 1, 1), TANGENT("tan", 1, 1),
	ABSOLUTE("abs", 1, 1), SQROOT("sqrt", 1, 1), EXPONENTIAL("exp", 1, 1),
	//default operator
	POSITIVE("++", 0, 0);
	
	public final String symbol;
	public final int type; //0 for default, 1 for unary, 2 for binary
	public final int precedence; // identifies order of operations
	
	private Operator(String symbol, int type, int precedence) {
		this.symbol = symbol;
		this.type = type;
		this.precedence = precedence;
	}
	public String toString() {
		return symbol;
	}
}
