import java.io.PrintWriter;
import java.util.List;

/**
 * An interface for the Tree ADT, or a Directed Acyclic Graph.
 * where nodes can have an arbitrary number of children.
 * Null nodes are not permitted.
 */
public interface Tree<E> {
	
	/**
	 * Returns whether this tree stores the given element
	 * @param the Object to check for
	 * @return True if this tree contains the specified object stored in a Node, otherwise false
	 */
	public boolean contains(Object o);
	
	/**
	 * Returns whether this tree contains the given Node in its structure
	 * @param v the Node to search for
	 * @return True if this tree contains the specified Node, otherwise false
	 */
	public boolean containsNode(TreeNode<E> v);
	
	/**
	 * Returns the depth of the specified node. The depth of a node 
	 * is defined as the number of ancestors that node has, 
	 * excluding the node itself.
	 * Node that the sum of the depth and the height of any node in a tree
	 * is always equal to that tree's height.
	 * @param v the node to compute the depth of 
	 * @return the depth of the specified node, in this tree
	 * @throws NoSuchNodeException if the specified node is not in this tree
	 */
	public int depth(TreeNode<E> v) throws NoSuchNodeException;
	
	/** 
	 * Returns the root node of this tree, or null if this tree has no root.
	 * @return the root node of this tree, or null if this tree has no root.
	 */
	public TreeNode<E> getRoot();

	/**
	 * Returns the height of this Tree.
	 * The height of a tree is defined as the height of its root node, 
	 * where the height of a node is the number of nodes in the longest 
	 * path from that node to an external node.
	 * Node that the sum of the depth and the height of any node in a tree
	 * is always equal to that tree's height.
	 * @return The height of this Tree, or equivalently, 
	 * the height of this tree's root node
	 */
	public int height();

	/**
	 * Returns the height of the specified node. 
	 * The height of a node is the number of nodes in the longest 
	 * path from that node to an external node.
	 * Height can also be defined recursively as follows:
	 * If v is an external node, the the height of v is 0.
	 * Otherwise, the height of v is one plus the maximum height of a child of v.
	 * Node that the sum of the depth and the height of any node in a tree
	 * is always equal to that tree's height.
	 * @param v the node to compute the height of 
	 * @return the height of the specified node, in this tree
	 * @throws NoSuchNodeException if the specified node is not in this tree
	 */
	public int height(TreeNode<E> v) throws NoSuchNodeException;

	/** 
	 * Returns true if this tree is empty, otherwise false.
	 * @return true if this tree is empty, otherwise false.
	 */
	public boolean isEmpty();

	/**
	 * Returns true if the given node is the root of this tree, otherwise false.
	 * More precisely, returns the value of 
	 * {@code (v == null) ? this.getRoot() == null : this.getRoot().equals(v)}
	 * The result of {@code this.isRoot(null)} is the same as {@code this.isEmpty()}.
	 */
	public boolean isRoot(TreeNode<E> v);
	
	/**
	 * Returns a list of Nodes in this Tree in order of pre-order traversal.
	 * The order of traversal of children, unless specified in implementations, 
	 * defaults to the order in which the children were added to the parent Node.
	 * @return a list of Nodes in this Tree in order of pre-order traversal.
	 */
	public List<TreeNode<E>> preOrderTraversal();
	
	/**
	 * Returns a list of Nodes in this Tree in order of post order traversal.
	 * The order of traversal of children, unless specified in implementations, 
	 * defaults to the order in which the children were added to the parent Node.
	 * @return a list of Nodes in this Tree in order of post order traversal.
	 */
	public List<TreeNode<E>> postOrderTraversal();
	
	/** 
	 * Sets the root of this tree to the given node, and returns the previous root node.
	 * Only the root node is allowed to be set to null.
	 * @return The previous root node of this tree, or null if there was no previous root node.
	 */
	public TreeNode<E> setRoot(TreeNode<E> root);
	
	/**
	 * Prints a nicely formatted version of this Tree 
	 * to the specified PrintWriter
	 * @param PrintWriter the PrintWriter to print to 
	 */
	public void print(PrintWriter pw);
	
	/**
	 * Prints a nicely formatted version of this Tree 
	 * to the standard output
	 */
	public void print();
	
//	/**
//	 * Constructs a new Tree identical to this Tree,
//	 * but using different nodes. The new nodes contain the 
//	 * same elements as the old nodes, however.
//	 * @return a Tree identical to this one,
//	 * but constructed using different nodes.
//	 */
//	public Tree<E> clone();
	
	/** 
	 * Returns the number of nodes in this tree.
	 * @return the number of nodes in this tree.
	 */
	public int size();
}
