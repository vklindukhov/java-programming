/**
 * Lists mathematical constants
 * 
 * @author Max Fisher
 * @version 2.0
 */
package dict;

import java.math.BigDecimal;

public enum Constant {
	PI("pi", Math.PI), E("e", Math.E);
	
	public final String symbol;
	public final BigDecimal value;
	
	private Constant(String symbol, Number value) {
		this.symbol = symbol;
		this.value = BigDecimal.valueOf(value.doubleValue());
	}
	public String toString() {
		return symbol;
	}
	public BigDecimal getValue() {
		return value;
	}
}
