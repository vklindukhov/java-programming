/**
 * Thrown to indicate that the given node 
 * is not contained within the relevant tree.
 * Implementation makes use of java.lang.IllegalArgumentException
 * @author Max Fisher
 *
 */
public class NoSuchNodeException extends IllegalArgumentException {
	private static final long serialVersionUID = -2726076659570969276L;
	
	/**
	 * Creates exception with standard error message
	 */
	public <E> NoSuchNodeException(Tree<E> t, Node<E> v) {
		this("Node " + v + " not present in tree " + t + ".");
	}
	
	/**
	 * Creates exception with standard error message, when the node was expected to 
	 * be found but was not.
	 */
	public <E> NoSuchNodeException(Tree<E> t, Node<E> v, boolean critical) {
		this("Node " + v + "expected to be found in " + t + ", but was not found.");
	}
	
	/**
     * Constructs an <code>NoSuchNodeException</code> with no
     * detail message.
     */
    public NoSuchNodeException() {
        super();
    }

    /**
     * Constructs an <code>NoSuchNodeException</code> with the
     * specified detail message.
     *
     * @param   s   the detail message.
     */
    public NoSuchNodeException(String s) {
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
    public NoSuchNodeException(String message, Throwable cause) {
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
    public NoSuchNodeException(Throwable cause) {
        super(cause);
    }
}
