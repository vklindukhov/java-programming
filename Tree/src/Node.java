/**
 * General Node ADT interface, which only permits access of the data stored within it, 
 * and access to the Tree object which contains it.
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
	 * @param element the new Object to store in this Node.
	 * @return the Object previously stored by this Node.
	 */
	public E setElement(E element);
	
//	/**
//	 * Checks whether the specified tree contains this Node.
//	 * The value returned by this method is identical to 
//	 * {@code this.getContainingTree == t)}
//	 * @param t the tree to check for presence of this Node
//	 * @return True if the given tree contains this Node, otherwise false
//	 */
//	public boolean containedBy(Tree<E> t);
//	
//	/**
//	 * Returns the tree that is currently storing this node,
//	 * or null if this node is not stored by any tree
//	 * @return The Tree which stores this node, or null if this node
//	 * is not contained by any tree
//	 */
//	public Tree<E> getContainingTree();
}
