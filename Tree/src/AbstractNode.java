/**
 * Abstract Node class that defines general Node method implementations.
 * @author Max Fisher
 *
 * @param <E> the type of object to store in this Node
 */
public abstract class AbstractNode<E> implements Node<E> {
	private E element;
	//private Tree<E> containingTree;
	
	/**
	 * Constructor that initializes element
	 * @param element the Object to store in this Node
	 */
	public AbstractNode(E element) {
		setElement(element);
		//containingTree = null;
	}

	/**
	 * Default contstructor that does not set element.
	 */
	public AbstractNode() {
		this(null);
	}
	
	/**
	 * Returns the data stored by this Node
	 * @return the data stored by this Node
	 */
	@Override
	public E getElement() {
		return element;
	}
	
	/**
	 * Sets the data stored by this Node to the given element, 
	 * and returns the Object previously stored by this Node.
	 * @param element the new Object to store in this Node.
	 * @return the Object previously stored by this Node.
	 */
	@Override
	public E setElement(E element) {
		E oldElement = this.element;
		this.element = element;
		return oldElement;
	}
	
//	/**
//	 * Checks whether the specified tree contains this Node.
//	 * The value returned by this method is identical to 
//	 * {@code (t == null) ? false : t.equals(this.getContainingTree())}
//	 * @param t the tree to check for presence of this Node
//	 * @return True if the given tree contains this Node, otherwise false
//	 */
//	@Override
//	public boolean containedBy(Tree<E> t) {
//		return (t == null) ? false : t.equals(containingTree);
//	}
//	
//	/**
//	 * Sets the containing Tree of this node to the given tree, 
//	 * and returns the old containing Tree of this Node.
//	 * This method should only be called by the Tree instance to
//	 * which this Node is being added
//	 * @param t the new containing Tree for this Node
//	 * @return the old containing Tree for this Node
//	 */
//	protected Tree<E> setContainingTree(Tree<E> t) {
//		Tree<E> oldContainingTree = this.containingTree;
//		this.containingTree = t;
//		return oldContainingTree;
//	}
//	
//	/**
//	 * Returns the tree that is currently storing this node,
//	 * or null if this node is not stored by any tree
//	 * @return The Tree which stores this node, or null if this node
//	 * is not contained by any tree
//	 */
//	@Override
//	public Tree<E> getContainingTree() {
//		return containingTree;
//	}
	
	@Override
	public String toString() {
		return element.toString();
	}
}
