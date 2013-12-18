/**
 * Interface to be implemented by mutable Node classes with a fixed number
 * of children where children can be addressed by an index. (e.g. Binary Node)
 * @author Max Fisher
 *
 * @param <E> The type of element to store in this Node
 */
public interface IndexMutableNode<E> extends MutableNode<E> {

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
	public void setChild(int index, TreeNode<E> child)
			throws DuplicateNodeException, IllegalNodeException,
			IndexOutOfBoundsException;

}