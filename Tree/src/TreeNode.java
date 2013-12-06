import java.util.List;

/**
 * General Node ADT interface for use with Trees, declaring accessor methods for parent and child Nodes.
 * Mutator methods for parent and child nodes are not defined publicly.
 * @author Max Fisher
 *
 */
public interface TreeNode<E> extends Node<E> {
	
	/** 
	 * Returns whether this node is an ancestor of the given node.
	 * That is, tracing back through the given node's parents will 
	 * eventually lead to this node.
	 * This method also returns true if this node and the given node
	 * refer to the same object.
	 * @param v the node to check as a descendent node of this node
	 * @return True if this node is an ancestor of the given node, otherwise false.
	 */
	public boolean isAncestorOf(TreeNode<E> v);
	
	/**
	 * Returns the parent Node of this Node.
	 * @return the parent Node of this Node.
	 */
	public TreeNode<E> getParent();

	/**
	 * Returns a list of this Node's children in the order in which they were
	 * added. The collection is guaranteed to be random-access.
	 * 
	 * @returns list of this Node's children in the order in which they were
	 *          added.
	 */
	public List<TreeNode<E>> getChildren();

	/**
	 * Adds the given node as a child node of this Node An exception is thrown
	 * is this Node already contains the node that is being added.
	 * 
	 * @param child
	 *            the new child node
	 * @throws DuplicateNodeException
	 *             if this Node is already a parent node of the given Node.
	 */

	/**
	 * Returns an iterable collection of all descendants of a given node.
	 * The collection need not be in any particular order.
	 * That is, this method returns a collection of all nodes for which 
	 * {@code ancestorOf(v, node)} is true.
	 * An exception is thrown if the node is not contained within this tree.
	 * 
	 * @return an iterable collection of all descendents of a given node
	 */
	public List<TreeNode<E>> getDescendants();
	
	/**
	 * Checks whether the specified tree contains this Node.
	 * @param t the tree to check for presence of this Node
	 * @return True if the given tree contains this Node, otherwise false
	 */
	public boolean containedBy(Tree<E> t);
	
	/** 
	 * Returns true if the given node is external, otherwise false. More precisely, 
	 * returns true if the given node has no children, or equivalently, 
	 * if {@code this.getChildren()} returns an empty list.
	 * Note that {@code this.isInternal()} and {@code this.isExternal()} 
	 * always return opposite values.
	 * @return true if the given node is external, otherwise false.
	 */
	public boolean isExternal();
	
	/** 
	 * Returns true if the given node is internal, otherwise false. More precisely, 
	 * returns true if the given node has at least one child in this tree, or equivalently, 
	 * if {@code this.getChildren()} returns a non-empty list.
	 * Note that {@code this.isInternal()} and {@code this.isExternal()} 
	 * always return opposite values.
	 * @return true if the given node is internal, otherwise false.
	 */
	public boolean isInternal();
	
	/**
	 * Performs a pre-order traversal of this node and its descendants.
	 * @return a List containing this node and its descendants,
	 * in pre-order traversal order.
	 */
	public List<TreeNode<E>> preOrderTraversal();
	
	/**
	 * Performs a post-order traversal of this node and its descendants.
	 * @return a List containing this node and its descendants,
	 * in post-order traversal order.
	 */
	public List<TreeNode<E>> postOrderTraversal();
	
}
