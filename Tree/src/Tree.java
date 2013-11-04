import java.util.Collection;
import java.util.Iterator;

/**
 * An interface for the Tree ADT, or a Directed Acyclic Graph.
 * where nodes can have an arbitrary number of children.
 */
public interface Tree<E> extends Iterable<E> {
	/** 
	 * Returns an iterable collection of the children of a given node.
	 * An exception is thrown if the node is not contained within this tree.
	 * @return an iterable collection of the children of a given node, 
	 * if that node is contained within this tree
	 * @throws NoSuchNodeException if the given node is not contained in this tree
	 */
	public Collection<Node<E>> getChildren(Node<E> v) throws NoSuchNodeException;
	
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
	 * An exception is thrown if the given node is not contained within this tree.
	 * @return true if the given node is the root of this tree, otherwise false.
	 * @throws NoSuchNodeException if the given node is not contained in this tree.
	 */
	public boolean isRoot(Node<E> v) throws NoSuchNodeException;

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
	 * Returns the number of nodes in this tree.
	 * @return the number of nodes in this tree.
	 */
	public int size();
}
