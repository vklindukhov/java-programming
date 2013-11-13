import java.util.List;

/**
 * General Node ADT interface for use with Trees, declaring accessor methods for data, parents and children.
 * Mutator methods for parent and child nodes are not defined publicly.
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
	/**
	 * Returns the parent Node of this Node.
	 * @return the parent Node of this Node.
	 */
	public Node<E> getParent();

	//void setParent(Node<E> parent);

	/**
	 * Returns a list of this Node's children in the order in which they were
	 * added. The collection is guaranteed to be random-access.
	 * 
	 * @returna list of this Node's children in the order in which they were
	 *          added.
	 */
	public List<Node<E>> getChildren();

	/**
	 * Adds the given node as a child node of this Node An exception is thrown
	 * is this Node already contains the node that is being added.
	 * 
	 * @param child
	 *            the new child node
	 * @throws DuplicateNodeException
	 *             if this Node is already a parent node of the given Node.
	 */
	//void addChild(Node<E> child) throws DuplicateNodeException;

	//void removeChild(Node<E> child);
	
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
