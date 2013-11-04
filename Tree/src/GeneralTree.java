import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GeneralTree<E> implements Tree<E> {
	static class TreeNode<E> implements Node<E> {
		private E element;
		private Node<E> parent;
		private Set<Node<E>> children;

		private TreeNode(E element) {
			this.element = element;
			this.parent = null;
			this.children = new HashSet<Node<E>>();
		}
		
		@Override
		public E getElement() {
			return element;
		}
		
		@Override
		public E setElement(E element) {
			E oldElement = this.element;
			this.element = element;
			return oldElement;
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
		
		@Override
		public String toString() {
			return "{" + element.toString() + "}";
		}
	}
	/**
	 * Number of nodes stored in this Tree. 
	 */
	private int size;
	/**
	 * The root node of this Tree
	 */
	private Node<E> root;
	
	/**
	 * Set of Nodes stored in this tree.
	 */
	private Set<Node<E>> nodes;
	
	public GeneralTree(Node<E> root) {
		if (root == null) {
			throw new IllegalArgumentException("root must not be null");
		}
		this.root = root;
		this.nodes = new HashSet<Node<E>>();
	}
	
	/**
	 * Adds the given Tree as a subtree to this Tree, attaching the root node
	 * of the new subtree to the specified parent node. An exception is thrown
	 * if the parent node is not contained within this tree.
	 * @param subtree the Tree to append to this Tree
	 * @param the parent Node to attach the subtree to
	 * @throws NoSuchNodeException if the given parent Node 
	 * is not contained within this Tree
	 */
	public void add(Tree<E> subTree, TreeNode<E> parent) {
		if (!nodes.contains(parent))
			throw new NoSuchNodeException();
		parent.addChild(subTree.getRoot());
		subTree.getRoot()
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
	public void add(E element, TreeNode<E> parent) {
		if (!nodes.contains(parent))
			throw new NoSuchNodeException("Node not present in tree");
		parent.
			
	}
	
	
	/** 
	 * Returns an iterable collection of the children of a given node.
	 * An exception is thrown if the node is not contained within this tree.
	 * @return an iterable collection of the children of a given node, 
	 * if that node is contained within this tree
	 * @throws NoSuchNodeException if the given node is not contained in this tree
	 */
	public Collection<Node<E>> getChildren(TreeNode<E> v) throws NoSuchNodeException {
		return null;
	}
	
	/**
	 * Returns the parent of a given node. 
	 * An exception is thrown if the node is not contained within this tree.
	 * @param the node to find the parent of
	 * @return the parent of the given node
	 * @throws NoSuchNodeException if the given node is not contained in this tree
	 */
	public TreeNode<E> getParent(TreeNode<E> v) throws NoSuchNodeException {
		return null;
	}
	
	/** 
	 * Returns the root node of this tree, or null if this tree has no root.
	 * @return the root node of this tree, or null if this tree has no root.
	 */
	public TreeNode<E> getRoot() {
		return root;
	}

	/** 
	 * Returns true if this tree is empty, otherwise false.
	 * @return true if this tree is empty, otherwise false.
	 */
	public boolean isEmpty() {
		return nodes.isEmpty();
	}

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
	public boolean isExternal(Node<E> v) throws NoSuchNodeException {
		if (!nodes.contains(v))
			throw new NoSuchNodeException("Node not present in tree");
		return getChildren(v).isEmpty();
	}

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
	
	public boolean isInternal(Node<E> v) throws NoSuchNodeException {
		if (!nodes.contains(v))
			throw new NoSuchNodeException("Node not present in tree");
		return !getChildren(v).isEmpty();
	}

	/** 
	 * Returns true if the given node is the root of this tree, otherwise false.
	 */
	public boolean isRoot(Node<E> v) throws NoSuchNodeException {
		return root == v;
	}

	/** 
	 * Returns an iterator over the elements stored in this Tree.
	 * @return an iterator over the elements stored in this Tree.
	 */
	public Iterator<E> iterator() {
		ArrayList<E> elements = new ArrayList<>();
		for (Node<E> node : nodes)
			elements.add(node.getElement());
		return elements.iterator();
	}

	/** 
	 * Returns an iterable collection over the nodes of this Tree.
	 * @return an iterable collection over the nodes of this Tree.
	 */
	public Collection<Node<E>> nodes() {
		// reference actual nodes but not the instance variable nodes
		return new HashSet<>(nodes);
	}

	/**
	 * Removes the subtree formed by the given node, from this Tree,
	 * if that Node is contained by this tree.
	 * Returns true if the subtree was removed from this Tree,
	 * otherwise false.
	 * @param v the node at the root of the subtree to remove from this tree.
	 * @return True if the given subtree was found and removed, otherwise false.
	 */
	public boolean prune(Node<E> v) {
		return false;
	}

	/** 
	 * Returns the number of nodes in this tree.
	 * @return the number of nodes in this tree.
	 */
	public int size() {
		return nodes.size();
	}
	@Override
	public String toString() {
		return "[" + root.toString() + " (" + size + ") ]";
	}
}
