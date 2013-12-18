
/**
 * Abstract class containing more implementation-specific methods
 * and code, including checking for duplicate and null nodes.
 * This class makes use of a containing Tree reference, in order to 
 * simplify interactions between Nodes and Trees.
 * That is, each Node instance can only be associated with a single Tree instance.
 * Duplicate node objects are not allowed either as children of the same parent, 
 * or both belonging to the same tree.
 * @author Max Fisher
 *
 * @param <E> the type to store in this Node
 */

public abstract class ContainingNode<E> extends AbstractNode<E> {
	/**
	 * The unique parent Node of this Node.
	 * A parent of null means that this Node has no parent
	 * (and hence should either be the root of a tree,
	 * or not belong to any tree)
	 */
	private ContainingNode<E> parent;

	/**
	 * The unique Tree that contains this Node
	 */
	private Tree<E> containingTree;
	
	public ContainingNode() {
		this(null);
	}
	
	public ContainingNode(E element) {
		super(element);
		// use for consistent ordering 
		this.parent = null;
		this.containingTree = null;
	}
	
	/**
	 * Ensures that a node attempting to be added as a child of this Node
	 * is not already a child of this Node, or contained elsewhere in the same tree.
	 * If a duplicate is found, an exception is thrown.
	 * @param v the new child Node to check. Assumed not to be null.
	 * @throws DuplicateNodeException if the given Node is a duplicate of 
	 * an existing child of this Node, or is contained elsewhere in the same tree. 
	 */
	protected void checkDuplicate(ContainingNode<E> v) throws DuplicateNodeException {
		if (this.equals(v.getParent())) {
			throw new DuplicateNodeException("Node " + v + "already a child of node " + this);
		}
		Tree<E> containingThis = this.getContainingTree();
		if (containingThis != null && containingThis.equals(v.getContainingTree()))
			throw new DuplicateNodeException(containingThis, v);
	}
	
	/**
	 * Ensures that the given Node is compatible with this Node
	 * an Exception is thrown if this condition is not met.
	 * Otherwise, a reference to the given Node,
	 * cast as the appropriate type of Node, is returned.
	 * Subclasses should override this method so that the required type
	 * of Node matches the subclass.
	 * @param v the node to check.
	 * @return the given Node cast to the appropriate type of Node, if possible
	 * @throws IllegalNodeException if the type of the given node is inappropriate for this Tree.
	 */
	protected ContainingNode<E> checkNodeType(TreeNode<E> v) {
		if (!(v instanceof ContainingNode))
			throw new IllegalNodeException(v);
		return (ContainingNode<E>)v;
	}
	
	/**
	 * Checks whether the specified tree contains this Node.
	 * A null containing Tree signifies no containing Tree
	 * The value returned by this method is identical to 
	 * {@code (t == null) ? containingTree == null : t.equals(this.getContainingTree())}
	 * @param t the tree to check for presence of this Node
	 * @return True if the given tree contains this Node, otherwise false
	 */
	public boolean containedBy(Tree<E> t) {
		return (t == null) ? containingTree == null : t.equals(containingTree);
	}

	/**
	 * Returns the tree that is currently storing this node,
	 * or null if this node is not stored by any tree
	 * @return the Tree which stores this node, 
	 * or null if this node is not contained by any tree
	 */
	public Tree<E> getContainingTree() {
		return containingTree;
	}
	
	public TreeNode<E> getParent() {
		return parent;
	}
		
	/**
	 * Unlinks this Node, from its parent, if it has one.
	 * If detached nodes are removed from the Tree they were originally
	 * contained in, then this method should also detach 
	 * all the descendant nodes of this node from that Tree.
	 */
	protected void removeFromParent() {
		if (parent != null) {
			parent.removeChild(this);
			setParent(null);
			setAllContainingTree(null);
		}
	}
	
	/**
	 * Removes the given node from this Node's list of children,
	 * if it was a child of this Node, and sets the containingTree
	 * of the child Node and all of its descendants to null.
	 * @param child the child Node to separate from this Node.
	 * @return True if the specified Node was found and removed, 
	 * otherwise false.
	 */
	protected abstract boolean removeChild(TreeNode<E> child);
	
	/**
	 * Sets the containing Tree of this node, 
	 * and all of its descendants, to the given tree.
	 * @param t the new containing Tree for this Node
	 * and all its children
	 */
	protected void setAllContainingTree(Tree<E> t) {
		if (!this.containedBy(t)) {
			setContainingTree(t);
			for (TreeNode<E> child : getChildren())
				((ContainingNode<E>) child).setChildrenContainingTree(t);
		}
	}
	
	/**
	 * Sets the containing Tree of all of this Node's child nodes
	 * to be the specified tree
	 * A value of null indicates that this node is not associated with any tree.
	 * @param t the new containingTree for all children of this Node
	 */
	private void setChildrenContainingTree(Tree<E> t) {
		for (TreeNode<E> child : getChildren())
			((ContainingNode<E>) child).setContainingTree(t);
	}
	
	/**
	 * Sets the containing Tree of this node to the given tree.
	 * A value of null indicates that this node is not associated with any tree.
	 * @param t the new containing Tree for this Node
	 */
	private void setContainingTree(Tree<E> t) {
		// update tree sizes
		if (containingTree instanceof GeneralTree)
			((GeneralTree<E>)containingTree).size--;
		if (t instanceof GeneralTree)
			((GeneralTree<E>)t).size++;
		containingTree = t;
	}

	/**
	 * Does nothing but set this Node's parent to the given Node - 
	 * no checking of types is done. Should remain a protected method.
	 * @param parent the new parent Node for this Node.
	 */
	@Override
	protected void setParent(TreeNode<E> parent) {
		this.parent = (ContainingNode<E>) parent;
	}
	
}
