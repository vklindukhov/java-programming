/**
 * Defines methods for nodes which allow addition and removal of child 
 * nodes, for use in tree data structures
 * @author Max Fisher
 *
 * @param <E> the type to store in this Node
 */
public interface MutableNode<E> extends TreeNode<E> {
	
	/**
	 * Adds the specified node as a child node of this Node. Implementations
	 * by default should do this in such a way that {@code this.getChildren()}
	 * returns a list of the children in the order in which they were added.
	 * @param child the new child node of this Node
	 * @throws DuplicateNodeException if the specified node is an existing child of this Node, 
	 * or has the same containing Tree as this one at the time when it is added to this Node
	 * @throws IllegalNodeException if the specified node is of an illegal type
	 * (for example null). Subclasses may be more restrictive and disallow other types.
	 */
	public void addChild(TreeNode<E> child) throws DuplicateNodeException, IllegalNodeException;
	
	/**
	 * Unlinks this Node, from its parent, if it has one.
	 * If detached nodes are removed from the Tree they were originally
	 * contained in, then this method should also detach 
	 * all the descendant nodes of this node from that Tree.
	 */
	public void removeFromParent();
}
