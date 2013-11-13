import java.util.Collection;

/**
 * Thrown to indicate that the program is attempting to add a Node
 * to a Tree which already contains that Node.
 * Implementation makes use of java.lang.IllegalArgumentException
 * @author Max Fisher
 *
 */
public class DuplicateNodeException extends IllegalArgumentException {
	private static final long serialVersionUID = 7389638155968369076L;

	/**
	 * Creates a standard error message indicating that the 
	 * specified node is already contained by the specified tree.
	 */
	public <E> DuplicateNodeException(Tree<E> t, Node<E> v) {
		// what would be nice: node v is already a child of node (parent) in tree t.
		this("Node " + v + " is already contained in tree " + t + ".");
	}
	
	/**
	 * Creates a standard error message indicating that the 
	 * specified nodes are already contained by the specified tree.
	 */
	public <E> DuplicateNodeException(Tree<E> t, Collection<Node<E>> c) {
		this("Cannot add the following duplicate nodes to tree " + t + ": " + c);
	}
	
	
	/**
     * Constructs an <code>DuplicateNodeException</code> with no
     * detail message.
     */
    public DuplicateNodeException() {
        super();
    }

    /**
     * Constructs an <code>DuplicateNodeException</code> with the
     * specified detail message.
     *
     * @param   s   the detail message.
     */
    public DuplicateNodeException(String s) {
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
    public DuplicateNodeException(String message, Throwable cause) {
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
    public DuplicateNodeException(Throwable cause) {
        super(cause);
    }
}
