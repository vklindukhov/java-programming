import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;


public class TreeNode<E> extends AbstractNode<E> {
	private Node<E> parent;
	private Collection<Node<E>> children;

	public TreeNode(E element) {
		super(element);
		this.parent = null;
		// use for consistent ordering 
		this.children = new LinkedHashSet<Node<E>>();
	}
	
	Node<E> getParent() {
		return parent;
	}

	void setParent(Node<E> parent) {
		this.parent = parent;

	}

	/**
	 * Returns a list of this Node's children in the order in which they were added.
	 * The collection is guaranteed to be random-access.
	 * @returna list of this Node's children in the order in which they were added.
	 */
	List<Node<E>> getChildren() {
		return new ArrayList<Node<E>>(children);
	}

	/**
	 * Adds the given node as a child node of this Node
	 * An exception is thrown is this Node already contains 
	 * the node that is being added.
	 * @param child the new child node
	 * @throws DuplicateNodeException if this Node is already
	 * a parent node of the given Node.
	 */
	void addChild(Node<E> child) throws DuplicateNodeException {
		checkDuplicate(child);
		children.add(child);
	}

	void removeChild(Node<E> child) {
		children.remove(child);
	}
	/**
	 * Ensures that a node attempting to be added 
	 * as a child Node of this Node
	 * is not already a child of this Node
	 * If a duplicate is found, an exception is thrown.
	 * @throws DuplicateNodeException if the given Node 
	 * is a duplicate of an existing child of this Node. 
	 */
	private void checkDuplicate(Node<E> v) throws DuplicateNodeException {
		if (children.contains(v)) {
			throw new DuplicateNodeException("Node " + v + "already a child of node " + this);
		}
	}
}