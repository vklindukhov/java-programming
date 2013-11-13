/**
 * Thrown to indicate that the given node 
 * is not suitable for addition to within the relevant tree.
 * Implementation makes use of java.lang.IllegalArgumentException
 * @author Max Fisher
 *
 */
public class IllegalNodeException extends IllegalArgumentException {
	private static final long serialVersionUID = -2108960235681036527L;

	/**
	 * Creates exception with standard error message
	 */
	public <E> IllegalNodeException(Tree<E> t, Node<E> v) {
		this("Node " + v + " not allowed in tree " + t + " (incorrect type).");
	}
	
	/**
     * Constructs an <code>IllegalNodeException</code> with no
     * detail message.
     */
    public IllegalNodeException() {
        super();
    }

    /**
     * Constructs an <code>IllegalNodeException</code> with the
     * specified detail message.
     *
     * @param   s   the detail message.
     */
    public IllegalNodeException(String s) {
        super(s);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * <p>Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link Throwable#getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value
     *         is permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since 1.5
     */
    public IllegalNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.5
     */
    public IllegalNodeException(Throwable cause) {
        super(cause);
    }
}
