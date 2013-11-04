/**
 * General Node ADT interface, which only permits access of the data stored within it.
 * @author Max Fisher
 *
 */
public interface Node<E> {
	/**
	 * Returns the data stored by this Node
	 * @return the data stored by this Node
	 */
	public E getElement();
	
	/**
	 * Sets the data stored by this Node to the given element, 
	 * and returns the Object previously stored by this Node.
	 * @return the Object previously stored by this Node.
	 */
	public E setElement(E element);
}
