/**
 * General Node interface to use with data structures which use nodes to store Objects. 
 * This interface provides methods solely to interact with the Object stored by this Node.
 * 
 * @author Max Fisher
 *
 * @param <E> The Type to store in this Node
 */
public interface Node<E> {
	/**
	 * Returns the element stored by this Node.
	 * @return the element stored by this Node.
	 */
	public E getElement();
	
	/**
	 * Sets the element stored by this Node to the given Object, 
	 * and returns the element previously stored by this node,
	 * or null if there was no previous element.
	 * @return the element previously stored by this Node, 
	 * or null if there was no previous element.
	 */
	public E setElement(E element);
}
