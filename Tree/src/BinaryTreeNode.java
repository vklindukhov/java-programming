import java.util.List;

/**
 * Abstract Data Type describing an (immutable) binary tree Node, 
 * with in-order traversal, as well as specialised methods for interacting
 * with left and right children
 * @author Max Fisher
 *
 * @param <E> The type to store in this Node
 */
public interface BinaryTreeNode<E> extends TreeNode<E> {
	/**
	 * Performs an in-order traversal of this Node and all its descendants,
	 * starting from this Node, and returns a list containing all traversed
	 * Nodes in the order in which they were traversed.
	 * @return A list containing this Node and all of its descendants, in the 
	 * order they are traversed in an in-order traversal
	 */
	public List<TreeNode<E>> inOrderTraversal();
	
	/**
	 * Checks whether this Node has a left child.
	 * This method is equivalent to {@code this.left() != null} 
	 * @return True if this Node has a left child, otherwise false.
	 */
	public boolean hasLeft();
	
	/**
	 * Checks whether this Node has a right child.
	 * This method is equivalent to {@code this.right() != null} 
	 * @return True if this Node has a right child, otherwise false.
	 */
	public boolean hasRight();
	
	/**
	 * Returns the left child of this Node, 
	 * or null if this Node has no left child.
	 * @return the left child of this Node, if it exists, otherwise null.
	 */
	public BinaryTreeNode<E> left();
	
	/**
	 * Returns the right child of this Node, 
	 * or null if this Node has no right child.
	 * @return the right child of this Node, if it exists, otherwise null.
	 */
	public BinaryTreeNode<E> right();
}
