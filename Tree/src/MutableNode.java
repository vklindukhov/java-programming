/**
 * Defines methods for nodes which allow addition and removal of child 
 * nodes, for use in tree data structures
 * @author Max Fisher
 *
 * @param <E> the type to store in this Node
 */
public interface MutableNode<E> extends TreeNode<E> {
	
	/**
	 * Adds the specified node as a child node of this Node with the 
	 * specified index. The child node must not be null or otherwise any type
	 * not allowed by {@code checkNodeType()}, must not be an
	 * existing child of this Node, or have the same containing Tree 
	 * at the time when it is added to this Node. 
	 * The index of the new child node must be between zero and 
	 * the current number of children of this Node.
	 * @param index the index at which to add the child to this Node's list of children
	 * @param child the new child node of this Node
	 * @throws DuplicateNodeException if the specified node is an existing child of this Node, 
	 * or has the same containing Tree as this one at the time when it is added to this Node
	 * @throws IllegalNodeException if the specified node is of an illegal type
	 * (for example null). Subclasses may be more restrictive and disallow other types.
	 * @throws IndexOutOfBoundsException if the index supplied is negative 
	 * or greater than the number of children this Node has at the time 
	 * this method executes.
	 */
	public void addChild(int index, TreeNode<E> child) 
			throws DuplicateNodeException, IllegalNodeException, IndexOutOfBoundsException;
	
	/**
	 * Adds the specified node as a child node of this Node, with index equal
	 * to the number of children this node currently has. (Optional operation)
	 * @param child the new child node of this Node
	 * @throws DuplicateNodeException if the specified node is an existing child of this Node, 
	 * or has the same containing Tree as this one at the time when it is added to this Node
	 * @throws IllegalNodeException if the specified node is of an illegal type
	 * (for example null). Subclasses may be more restrictive and disallow other types.
	 */
	public void addChild(TreeNode<E> child);
	
	/**
	 * Unlinks this Node, from its parent, if it has one.
	 * If detached nodes are removed from the Tree they were originally
	 * contained in, then this method should also detach 
	 * all the descendant nodes of this node from that Tree.
	 */
	public void removeFromParent();
}
