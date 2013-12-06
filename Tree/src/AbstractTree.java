import java.io.PrintWriter;
import java.util.List;

/**
 * Abstract implementation of Tree ADT, where nodes are visible.
 * Provides fundamental methods for trees and tree derivatives, leaving
 * the implementation of adding and removing nodes to node classes.
 * @author Max Fisher
 *
 * @param <E> the Object type to store in this Tree.
 */
public abstract class AbstractTree<E> implements Tree<E> {
	/**
	 * The root node of this Tree
	 */
	private TreeNode<E> root = null;

	public AbstractTree() {
		this(null);
	}
	
	public AbstractTree(TreeNode<E> root) {
		setRoot(root);
	}
	
	/**
	 * Ensures the given node is contained within this tree.
	 * If this condition is not satisfied, an exception is thrown.
	 * This method also catches null nodes, assuming that null nodes
	 * are not allowed to be added to this tree.
	 * @param v the node to check.
	 * @throws NoSuchNodeException if this tree does not contain
	 * the specified node.
	 */
	private void checkNode(TreeNode<E> v) throws NoSuchNodeException {
		if (!containsNode(v))
			throw new NoSuchNodeException(this, v);
	}
	
	@Override
	public boolean contains(Object o) {
		if (o == null) { // special case for null objects
			for (TreeNode<E> v: preOrderTraversal()) {
				if (v.getElement() == null)
					return true;
			}
			return false;
		}
		for (TreeNode<E> v: preOrderTraversal()) {
			if (o.equals(v.getElement()))
				return true;
		}
		return false;
	}
	
	@Override
	public abstract boolean containsNode(TreeNode<E> v);

	@Override
	public int depth(TreeNode<E> v) throws NoSuchNodeException {
		checkNode(v);
		if (isRoot(v)) // special case; depth of root is always 0.
			return 0;
		return 1 + depth(v.getParent()); // handles invalid & null arguments
	}

	/**
	 * Returns the root node of this tree, or null if this tree has no root.
	 * @return the root node of this tree, or null if this tree has no root.
	 */
	@Override
	public TreeNode<E> getRoot() {
		return root;
	}

	@Override
	public int height() {
		return height(root);
	}

	@Override 
	public int height(TreeNode<E> v) throws NoSuchNodeException {
		checkNode(v);
		if (v.isExternal()) // special case; height of an external node is always 0
			return 0;
		int height = 0;
		for (TreeNode<E> child : v.getChildren())
			height = Math.max(height(child), height);
		return 1 + height;
	}

	/**
	 * Returns true if this tree is empty, otherwise false.
	 * @return true if this tree is empty, otherwise false.
	 */
	@Override
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Returns true if the given node is the root of this tree, otherwise false.
	 * More precisely, returns the value of 
	 * {@code (v == null) ? this.getRoot() == null : this.getRoot().equals(v)}
	 * The result of {@code this.isRoot(null)} is the same as {@code this.isEmpty()}.
	 */
	@Override
	public boolean isRoot(TreeNode<E> v) {
		return (v == null) ? this.root == null : this.root.equals(v);
	}

	/**
	 * Returns a list of Nodes in this Tree in order of pre-order traversal.
	 * The order of traversal of children, unless specified in implementations, 
	 * defaults to the order in which the children were added to the parent Node.
	 * @return a list of Nodes in this Tree in order of pre-order traversal.
	 */
	@Override
	public List<TreeNode<E>> preOrderTraversal() {
		return root.preOrderTraversal();
	}
	
	/**
	 * Returns a list of Nodes in this Tree in order of post order traversal.
	 * The order of traversal of children, unless specified in implementations, 
	 * defaults to the order in which the children were added to the parent Node.
	 * @return a list of Nodes in this Tree in order of post order traversal.
	 */
	@Override
	public List<TreeNode<E>> postOrderTraversal() {
		return root.postOrderTraversal();
	}
	
	@Override
	public TreeNode<E> setRoot(TreeNode<E> root) {
		TreeNode<E> oldRoot = this.root; // save old root
		this.root = root;
		return oldRoot;
	}
	
	/**
	 * Prints a nicely formatted version of this Tree 
	 * to the specified PrintWriter
	 * @param PrintWriter the PrintWriter to print to 
	 */
	public void print(PrintWriter pw) {
		for (TreeNode<E> v : this.preOrderTraversal()) {
			int depth = depth(v);
			for (int i = 0; i < depth; i++) {
				pw.print("    |"); // print indent
			}
			pw.print("--> ");
			pw.println(v);
		}
	}
	
	/**
	 * Prints a nicely formatted version of this Tree 
	 * to the standard output
	 */
	public void print() {
		PrintWriter sysout = new PrintWriter(System.out);
		print(sysout);
		sysout.flush();	
	}
	
	/**
	 * Returns the number of nodes in this tree.
	 * @return the number of nodes in this tree.
	 */
	@Override
	public abstract int size();

	@Override
	public String toString() {
		return "[" + String.valueOf(root) + "]";
	}
}
