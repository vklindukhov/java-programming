import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Abstract implementation of general Tree ADT, where nodes are visible.
 * Duplicate or null nodes are not allowed.
 * Adding and removing nodes is left to subclasses.
 * @author Max Fisher
 *
 * @param <E> the Object type to store in this Tree.
 */
public abstract class AbstractTree<E> implements Tree<E> {
	/**
	 * Number of nodes stored in this Tree.
	 */
	private int size;
	/**
	 * The root node of this Tree
	 */
	private Node<E> root;

	/**
	 * Set of Nodes stored in this tree (duplicates not permitted)
	 */
	protected Set<Node<E>> nodes;

	/**
	 * Constructor setting root to the specified node
	 * @param root the root Note of this Tree
	 */
	public AbstractTree(Node<E> root) {
		this.nodes = new HashSet<Node<E>>();
		setRoot(root);
	}
	/**
	 * Default constructor which does not set root Node.
	 */
	public AbstractTree() {
		this(null);
	}

	@Override
	public boolean ancestorOf(Node<E> u, Node<E> v) throws NoSuchNodeException {
		checkNode(u);
		Node<E> ancestor = v;
		while (ancestor != null) {
			if (ancestor.equals(u)) // ancestor found
				return true;
			else 
				ancestor = getParent(ancestor);
		}
		// no more ancestors to check
			return false;	
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
	protected void checkNode(Node<E> v) {
		if (!containsNode(v))
			throw new NoSuchNodeException("Node " + v + " not present in tree " + this);
	}
	/**
	 * Ensures that the given Node is the correct type for this Tree
	 * an Exception is thrown if this condition is not met.
	 * Subclasses should override this method so that the required type
	 * of Node matches the subclass.
	 * @param v the node to check.
	 * @throws IllegalNodeException if the type of the given node is inappropriate for this Tree.
	 */
	protected void checkNodeType(Node<E> v) {
		if (!(v instanceof AbstractNode))
			throw new IllegalNodeException("Node " + v + " not allowed in tree " + this + 
					" (must be subtype of AbstractNode");
	}
	
	@Override
	public boolean contains(Object o) {
		if (o == null) { // special case for null objects
			for (Node<E> v: nodes()) {
				if (v.getElement() == null)
					return true;
			}
			return false;
		}
		for (Node<E> v: nodes()) {
			if (o.equals(v.getElement()))
				return true;
		}
		return false;
	}

	@Override
	public boolean containsNode(Node<E> v) {
		return nodes.contains(v);
	}

	@Override
	public int depth(Node<E> v) throws NoSuchNodeException {
		if (isRoot(v)) // special case; depth of root is always 0.
			return 0;
		return 1 + depth(getParent(v)); // handles invalid & null arguments
	}

	/**
	 * Returns an iterable collection of the children of a given node. An
	 * exception is thrown if the node is not contained within this tree.
	 * 
	 * @return an iterable collection of the children of a given node, if that
	 *         node is contained within this tree
	 * @throws NoSuchNodeException if the given node is not contained in this tree
	 */
	@Override
	public abstract Collection<Node<E>> getChildren(Node<E> v) throws NoSuchNodeException;
	
	/**
	 * Returns an iterable collection of all descendants of a given node. 
	 * That is, this method returns a collection of all nodes for which 
	 * {@code ancestorOf(v, node)} is true.
	 * An exception is thrown if the node is not contained within this tree.
	 * 
	 * @return an iterable collection of all descendents of a given node, if that
	 *         node is contained within this tree
	 * @throws NoSuchNodeException if the given node is not contained in this tree
	 */
	@Override
	public abstract Collection<Node<E>> getDescendants(Node<E> v) throws NoSuchNodeException;

	/**
	 * Returns the parent of a given node. An exception is thrown if the node is
	 * not contained within this tree.
	 * 
	 * @param the node to find the parent of
	 * @return the parent of the given node
	 * @throws NoSuchNodeException if the given node is not contained in this tree
	 */
	public abstract Node<E> getParent(Node<E> v) throws NoSuchNodeException;

	/**
	 * Returns the root node of this tree, or null if this tree has no root.
	 * 
	 * @return the root node of this tree, or null if this tree has no root.
	 */
	@Override
	public Node<E> getRoot() {
		return root;
	}

	@Override
	public int height() {
		return height(root);
	}

	@Override
	public int height(Node<E> v) throws NoSuchNodeException {
		// isExternal() finds null and invalid arguments as well
		if (isExternal(v)) // special case; height of an external node is always 0
			return 0;
		int height = 0;
		for (Node<E> child : getChildren(v))
			height = Math.max(height(child), height);
		return 1 + height;
	}

	/**
	 * Returns true if this tree is empty, otherwise false.
	 * This normally should never be false, as the root cannot be null
	 * @return true if this tree is empty, otherwise false.
	 */
	@Override
	public boolean isEmpty() {
		return nodes.isEmpty();
	}

	/**
	 * Returns true if the given node is external, otherwise false. More
	 * precisely, returns true if the given node has no children, or
	 * equivalently, if {@code this.getChildren(v)} returns an empty list.
	 * An exception is thrown if the given node is not
	 * contained within this tree.
	 * 
	 * @return true if the given node is external, otherwise false.
	 * @throws NoSuchNodeException
	 *             if the given node is not contained in this tree
	 */
	public boolean isExternal(Node<E> v) throws NoSuchNodeException {
		checkNode(v);
		return getChildren(v).isEmpty();
	}
	
	/**
	 * Returns true if the given node is internal, otherwise false. More
	 * precisely, returns true if the given node has at least one child in this
	 * tree, or equivalently, if {@code this.getChildren(v)} returns a non-empty
	 * list. Note that {@code this.isInternal(v)} and {@code this.isExternal(v)}
	 * always return opposite values. An exception is thrown if the given node
	 * is not contained within this tree.
	 * 
	 * @return true if the given node is internal, otherwise false.
	 * @throws NoSuchNodeException
	 *             if the given node is not contained in this tree
	 */

	public boolean isInternal(Node<E> v) throws NoSuchNodeException {
		return !getChildren(v).isEmpty();
	}
	
	/**
	 * Returns true if the given node is the root of this tree, otherwise false.
	 * More precisely, returns the value of 
	 * {@code (v == null) ? this.getRoot() == null : this.getRoot().equals(v)}
	 */
	@Override
	public boolean isRoot(Node<E> v) throws NoSuchNodeException {
		return (v == null) ? this.getRoot() == null : this.getRoot().equals(v);
	}

	/**
	 * Returns an iterator over the elements stored in this Tree.
	 * The elements are returned in random order.
	 * @return an iterator over the elements stored in this Tree.
	 */
	@Override
	public Iterator<E> iterator() {
		ArrayList<E> elements = new ArrayList<>();
		for (Node<E> node : nodes)
			elements.add(node.getElement());
		return elements.iterator();
	}
	
//	/**
//	 * Adds the given node to this Tree's collection of Nodes.
//	 * Duplicates and null nodes are not permitted, 
//	 * but this method assumed that nodes have already been checked.
//	 */
//	protected void storeNode(Node<E> v) {
//		nodes.add(v);
//	}
//	
//	/**
//	 * Removes the given node to from this Tree's collection of Nodes.
//	 * @return True if the given node was found and removed from this Tree's
//	 * collection of Nodes, otherwise false.
//	 */
//	protected boolean removeStoredNode(Node<E> v) {
//		return nodes.remove(v);
//	}
	
	/**
	 * Returns an iterable collection over the nodes of this Tree.
	 * The collection is randomly ordered.
	 * @return an iterable collection over the nodes of this Tree.
	 */
	@Override
	public Collection<Node<E>> nodes() {
		// reference actual nodes but not the instance variable nodes
		return new HashSet<>(nodes);
	}
	
	@Override
	public Node<E> setRoot(Node<E> root) {
		if (root == null) {
			throw new IllegalNodeException("Root node must not be null");
		}
		Node<E> oldRoot = this.root; // save old root
		this.root = root;
		nodes.add(root);
		nodes.remove(oldRoot);
		return oldRoot;
	}

	/**
	 * Returns the number of nodes in this tree.
	 * 
	 * @return the number of nodes in this tree.
	 */
	@Override
	public int size() {
		return nodes.size();
	}

	@Override
	public String toString() {
		return "[" + root.toString() + " (" + size + ") ]";
	}
}
