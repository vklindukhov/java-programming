import java.util.Collection;
import java.util.Iterator;

/**
 * An interface for the Tree ADT, or a Directed Acyclic Graph.
 * where nodes can have an arbitrary number of children.
 * Null nodes are not permitted.
 */
public interface Tree<E> extends Iterable<E> {
	/** 
	 * Returns whether the two nodes satisfy an ancestor-descendent 
	 * relationship in this tree. If u and v refer to the same Node,
	 * then this method returns true. An exception is thrown 
	 * if either node is not contained in this tree.
	 * @param u the node to check as an ancestor node of v
	 * @param v the node to check as a descendent node of u
	 * @return True if u is an ancestor of v, otherwise false.
	 * @throws NoSuchNodeException if either node is not contained by this tree
	 */
	public boolean ancestorOf(Node<E> u, Node<E> v) throws NoSuchNodeException;
	
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
	public boolean containsNode(Node<E> v);
	
	/**
	 * Returns the depth of the specified node. The depth of a node 
	 * is defined as the number ancestors that node has, 
	 * excluding the node itself.
	 * Node that the sum of the depth and the height of any node in a tree
	 * is always equal to that tree's height.
	 * @param v the node to compute the depth of 
	 * @return the depth of the specified node, in this tree
	 * @throws NoSuchNodeException if the specified node is not in this tree
	 */
	public int depth(Node<E> v) throws NoSuchNodeException;
	
	/** 
	 * Returns an iterable collection of the children of a given node.
	 * An exception is thrown if the node is not contained within this tree.
	 * @return an iterable collection of the children of a given node, 
	 * if that node is contained within this tree
	 * @throws NoSuchNodeException if the given node is not contained in this tree
	 */
	public Collection<Node<E>> getChildren(Node<E> v) throws NoSuchNodeException;

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
	public abstract Collection<Node<E>> getDescendants(Node<E> v) throws NoSuchNodeException;
	
	/**
	 * Returns the parent of a given node. 
	 * An exception is thrown if the node is not contained within this tree.
	 * @param the node to find the parent of
	 * @return the parent of the given node
	 * @throws NoSuchNodeException if the given node is not contained in this tree
	 */
	public Node<E> getParent(Node<E> v) throws NoSuchNodeException;

	/** 
	 * Returns the root node of this tree, or null if this tree has no root.
	 * @return the root node of this tree, or null if this tree has no root.
	 */
	public Node<E> getRoot();

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
	public int height(Node<E> v) throws NoSuchNodeException;

	/** 
	 * Returns true if this tree is empty, otherwise false.
	 * @return true if this tree is empty, otherwise false.
	 */
	public boolean isEmpty();

	/** 
	 * Returns true if the given node is external, otherwise false. More precisely, 
	 * returns true if the given node has no children, or equivalently, 
	 * if {@code this.getChildren(v)} returns an empty list.
	 * Note that {@code this.isInternal(v)} and {@code this.isExternal(v)} 
	 * always return opposite values.
	 * An exception is thrown if the given node is not contained within this tree.
	 * @return true if the given node is external, otherwise false.
	 * @throws NoSuchNodeException if the given node is not contained in this tree
	 */
	public boolean isExternal(Node<E> v) throws NoSuchNodeException;
	
	/** 
	 * Returns true if the given node is internal, otherwise false. More precisely, 
	 * returns true if the given node has at least one child in this tree, or equivalently, 
	 * if {@code this.getChildren(v)} returns a non-empty list.
	 * Note that {@code this.isInternal(v)} and {@code this.isExternal(v)} 
	 * always return opposite values.
	 * An exception is thrown if the given node is not contained within this tree.
	 * @return true if the given node is internal, otherwise false.
	 * @throws NoSuchNodeException if the given node is not contained in this tree
	 */
	
	public boolean isInternal(Node<E> v) throws NoSuchNodeException;
	
	/** 
	 * Returns true if the given node is the root of this tree, otherwise false.
	 * @return true if the given node is the root of this tree, otherwise false.
	 */
	public boolean isRoot(Node<E> v);
	
	/** 
	 * Returns an iterator over the elements stored in this Tree.
	 * @return an iterator over the elements stored in this Tree.
	 */
	public Iterator<E> iterator();
	
	/** 
	 * Returns an iterable collection over the nodes of this Tree.
	 * @return an iterable collection over the nodes of this Tree.
	 */
	public Collection<Node<E>> nodes();
	
	/** 
	 * Sets the root of this tree to the given node, and returns the previous root node
	 * @return The previous root node of this tree
	 */
	public Node<E> setRoot(Node<E> root);
	
	/** 
	 * Returns the number of nodes in this tree.
	 * @return the number of nodes in this tree.
	 */
	public int size();
}
