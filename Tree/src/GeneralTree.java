import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GeneralTree<E> extends AbstractTree<E> {
	public static class GeneralTreeNode<E> extends AbstractNode<E> {
		private Node<E> parent;
		private Set<Node<E>> children;

		public GeneralTreeNode(E element) {
			super(element);
			this.parent = null;
			this.children = new HashSet<Node<E>>();
		}
		
		private Node<E> getParent() {
			return parent;
		}

		private void setParent(Node<E> parent) {
			this.parent = parent;

		}

		private Collection<Node<E>> getChildren() {
			return children;
		}

		private void addChild(Node<E> child) {
			children.add(child);
		}

		private void removeChild(Node<E> child) {
			children.remove(child);
		}
		
	}
	/**
	 * Constructor setting root to the specified node
	 * @param root the root Note of this Tree
	 */
	public GeneralTree(Node<E> root) {
		super(root);
	}
	/**
	 * Default constructor which does not set root Node.
	 */
	public GeneralTree() {
		this(null);
	}
	
	/**
	 * Adds the given Tree as a subtree to this Tree, attaching the root node
	 * of the new subtree to the specified parent node. An exception is thrown
	 * if the parent node is not contained within this tree.
	 * @param subtree the Tree to append to this Tree
	 * @param the parent Node to attach the subtree to
	 * @throws DuplicateNodeException if the subtree and this Tree contain a common node
	 * @throws NoSuchNodeException if the given parent Node 
	 * is not contained within this Tree
	 */
	public void add(Tree<E> subTree, Node<E> parent) throws DuplicateNodeException, NoSuchNodeException {
		if (subTree == null) 
			return;
		checkNode(parent);
		// compute intersection of sets
		Collection <Node<E>> commonNodes = this.nodes();
		commonNodes.retainAll(subTree.nodes());
		if (!commonNodes.isEmpty())
			throw new DuplicateNodeException("Cannot add the following duplicate nodes to Tree " + this + ": " + commonNodes);
		// link in 
		((GeneralTreeNode<E>) parent).addChild(subTree.getRoot());
		((GeneralTreeNode<E>) subTree.getRoot()).setParent(parent);
		
		nodes.addAll(subTree.nodes());
	}
	
	/**
	 * Adds the given Object as a subtree to this Tree, attaching it 
	 * as a child of the specified parent node. An exception is thrown
	 * if the parent node is not contained within this tree.
	 * Returns the node that is created to store the Object
	 * @param element the Object to append to this Tree
	 * @param the parent Node to attach the Tree to
	 * @return a reference to the new Node created, which stores the given element
	 * @throws NoSuchNodeException if the given parent Node 
	 * is not contained within this Tree
	 */
	public Node<E> add(E element, Node<E> parent) throws NoSuchNodeException {
		checkNode(parent);
		GeneralTreeNode<E> child = new GeneralTreeNode<>(element);
		//child.setContainingTree(this);
		((GeneralTreeNode<E>) parent).addChild(child);
		child.setParent(parent);
		nodes.add(child);
		return child;	
	}
	
	/**
	 * Adds the specified Node to this Tree, with the specified parent Node.
	 * If this Tree already contains the node to be added, an exception is thrown.
	 * The given node must be a compatible type with this Tree (i.e. GeneralTreeNode),
	 * otherwise an exception is thrown. An exception is also thrown if the new Node
	 * is already a part of another Tree.
	 * @param v the Node to add to this Tree
	 * @param parent the parent Node for the new Node
	 * @throws DuplicateNodeException if the new Node is already contained within this Tree.
	 * @throws IllegalNodeException if the new Node type is incompatible with this Tree
	 * @throws NoSuchNodeException if the parent node cannot be found in this Tree
	 */
	public void addNode(Node<E> v, Node<E> parent) 
			throws DuplicateNodeException, IllegalNodeException, NoSuchNodeException {
		checkNodeType(v);
		checkNode(parent);
		if (containsNode(v))
			throw new DuplicateNodeException("Node " + v + " is already contained in " + this);
		
		// link in new node
		((GeneralTreeNode<E>) parent).addChild(v);
		((GeneralTreeNode<E>) v).setParent(parent);
		nodes.add(v);
	}
	
	/** 
	 * Returns an iterable collection of the children of a given node.
	 * An exception is thrown if the node is not contained within this tree.
	 * @return an iterable collection of the children of a given node, 
	 * if that node is contained within this tree
	 * @throws NoSuchNodeException if the given node is not contained in this tree
	 */
	public Collection<Node<E>> getChildren(Node<E> v) throws NoSuchNodeException {
		checkNode(v); // also handles null argument
		return ((GeneralTreeNode<E>) v).getChildren();
	}
	
	/**
	 * Returns the parent of a given node. If the given node 
	 * is the root node of this tree, this method returns null. 
	 * An exception is thrown if the node is not contained within this tree.
	 * @param the node to find the parent of
	 * @return the parent of the given node, or null if it is the root of this tree.
	 * @throws NoSuchNodeException if the given node is not contained in this tree
	 */
	public Node<E> getParent(Node<E> v) throws NoSuchNodeException {
		checkNode(v); // also handles null argument
		return isRoot(v) ? null : ((GeneralTreeNode<E>) v).getParent();
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
	protected void checkNode(Node<E> v) throws NoSuchNodeException {
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
	protected void checkNodeType(Node<E> v) throws IllegalNodeException {
		if (!(v instanceof GeneralTreeNode))
			throw new IllegalNodeException("Node " + v + " not allowed in tree " + this + 
					" (must be subtype of GeneralTreeNode");
	}
	
//	/**
//	 * Creates a subtree from this Tree, using the given Node as the root node 
//	 * returns it as a new Tree object. The underlying Node objects are the same, 
//	 * so any changes to the Nodes of the returned subtree will affect this Tree as well.
//	 * An exception is thrown if the specified Node is not contained by this tree.
//	 * @param v the node at the root of the subtree to form from this tree.
//	 * @return A new Tree object, representing the subtree formed from this Tree
//	 * by treating the given node as the root.
//	 * @throws NoSuchNodeException if the given Node is not present in this Tree
//	 */
//	public Tree<E> subtree(Node<E> v) throws NoSuchNodeException {
//		checkNode(v);
//		return new GeneralTree<>(v);
//		//ensure nodes added to the returned tree are also added to this tree
//	}
	
	/**
	 * Removes the given Node and all its descendents from this tree, 
	 * and returns them as a new Tree object, if that Node is contained by this tree.
	 * @param v the node at the root of the subtree to remove from this tree.
	 * @return The subtree of this Tree with the given node as root
	 * @throws NoSuchNodeException if the given Node is not present in this Tree
	 */
	public Tree<E> removeSubtree(Node<E> v) throws NoSuchNodeException {
		checkNode(v);
		//make new tree with root
		GeneralTree<E> subtree = new GeneralTree<>(v);
		
		// unlink from current tree
		((GeneralTreeNode<E>) getParent(v)).removeChild(v);
		((GeneralTreeNode<E>) v).setParent(null);
		if (!nodes.remove(v)) //should be true always
			throw new NoSuchNodeException("Node " + v + "expected to be found in stored nodes of " + this 
					+ ", but was not found.");
		
		// remove all children of v from this Tree and add to other tree; 
		// don't need to change structure
		Collection<Node<E>> descendants = (getDescendants(v));
		nodes.removeAll(descendants);
		subtree.nodes.addAll(descendants);
		return subtree;

	}
	
	/**
	 * Removes a single Node object from this Tree,
	 * on the condition that it is an external node, 
	 * and returns the element stored by that Node.
	 * An exception is thrown if the given Node is not External, 
	 * or not contained by this Tree.
	 * @throws UnsupportedOperationException if the given node is not external 
	 * @throws NoSuchNodeException if the given node is not present in this tree
	 */
	public E removeNode(Node<E> v) throws UnsupportedOperationException, NoSuchNodeException {
		if (!isExternal(v)) // also handles NoSuchNodeException
			throw new UnsupportedOperationException("Node " + v + "is not external in Tree " + this);
		((GeneralTreeNode<E>) getParent(v)).removeChild(v);
		((GeneralTreeNode<E>) v).setParent(null);
		if (!nodes.remove(v)) //should be true always
			throw new NoSuchNodeException("Node " + v + "expected to be found in stored nodes of " + this 
					+ ", but was not found.");
		return v.getElement();
	}
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
	public Collection<Node<E>> getDescendants(Node<E> v) throws NoSuchNodeException {
		checkNode(v);
		Collection<Node<E>> descendants = new ArrayList<>(size()/4); // max of two resizes necessary
		descendants.add(v);
		for (Node<E> child : getChildren(v)) {
			descendants.addAll(getDescendants(child));
		}
		return null;
	}

}
