/**
 * Thrown when 
 * @author Max
 *
 */
public class NegativeDNAException extends RuntimeException {
	private static final long serialVersionUID = -6255016843773996044L;

	public NegativeDNAException() {
		super("Negative DNA!");
	}
	public static void main(String[] args) {
		System.out.println(4%8);
	}
}
