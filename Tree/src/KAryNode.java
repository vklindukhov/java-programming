/**
 * Abstract Node class outlining Node implementations for classes in which
 * the maximum number of children is fixed and known. This means that each 
 * child can be identified by an index.
 * @author max
 *
 * @param <E>
 */
public abstract class KAryNode<E> extends ContainingNode<E> {

	public KAryNode() {
		super();
	}

	public KAryNode(E element) {
		super(element);
	}

	/**
	 * Removes and returns the child of this Node at the given index,
	 * if it exists. This sets the containingTree of the child Node 
	 * and all of its descendants to null. 
	 * If there is no child at the given index, this
	 * method returns null.
	 * @param index the index at which to remove the child node
	 * @return the removed Node, or null if there was no Node 
	 * at the specified index.
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	protected abstract TreeNode<E> removeChild(int index)
			throws IndexOutOfBoundsException;

	/**
	 * Sets the specified node as a child node of this Node with the 
	 * specified index.
	 * The child node must not be null or otherwise any type not allowed by 
	 * {@code checkNodeType()}. It must also not be an existing child 
	 * of this Node, or have the same containing Tree at the time it is added. 
	 * The index of the new child node must be between zero and the total 
	 * number of permitted children of this Node.
	 * @param index the index to associate with the new child Node.
	 * @param child the new child Node of this Node
	 * @throws DuplicateNodeException if the specified node is an existing child of this Node, 
	 * or has the same containing Tree as this one at the time when it is added to this Node
	 * @throws IllegalNodeException if the specified node is of an illegal type
	 * (for example null). Subclasses may be more restrictive and disallow other types.
	 * @throws IndexOutOfBoundsException if the index supplied is negative 
	 * or greater than the number of children this Node has at the time 
	 * this method executes.
	 */
	protected abstract void setChild(int index, TreeNode<E> child)
			throws DuplicateNodeException, IllegalNodeException,
			IndexOutOfBoundsException;

	/**
	 * Unless otherwise specified in subclasses, this method throws an
	 * UnsupportedOperationException by default.
	 */
	@Override
	protected void addChild(TreeNode<E> child) {
		throw new UnsupportedOperationException(
				"Adding child Nodes with no index "
				+ "is an undefined operation for this class.");
	}
	
	/**
	 * Finds the index of a given child node in this Node's
	 * list of children. If the given node is not a child of this Node,
	 * this method returns -1. Since this Node implementation does not allow 
	 * duplicates, the index found by this method is the unique index of that 
	 * child in this Node's list of children.
	 * @param child the node to find the index of.
	 * @return the index of the specified Node in this Node's list 
	 * of children, or -1 if the node is not a child of this Node.
	 */
	protected abstract int getIndex(TreeNode<E> child);

}