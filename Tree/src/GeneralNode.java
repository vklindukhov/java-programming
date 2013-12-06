import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * General implementation of a tree node, making use of the concept of a
 * 'containing Tree' to simplify interactions between nodes and trees.
 * That is, each Node instance can only be associated with a single Tree instance.
 * Duplicate node objects are not allowed either as children of the same parent, 
 * or both belonging to the same tree.
 * @author Max Fisher
 * @param <E> the type to store in this Node
 */

public class GeneralNode<E> extends AbstractNode<E> {
	/**
	 * The unique parent Node of this Node.
	 * A parent of null means that this Node has no parent
	 * (and hence should either be the root of a tree,
	 * or not belong to any tree)
	 */
	private GeneralNode<E> parent;
	/**
	 * Iterable collection of the children of this Node
	 */
	private Collection<GeneralNode<E>> children;
	
	/**
	 * The unique Tree that contains this Node
	 */
	private Tree<E> containingTree;
	
	public GeneralNode() {
		this(null);
	}
	
	public GeneralNode(E element) {
		super(element);
		this.parent = null;
		// use for consistent ordering 
		this.children = new LinkedHashSet<GeneralNode<E>>();
		this.containingTree = null;
	}
	
	/**
	 * Creates a new Node object containing the given object,
	 * and adds it as a child Node of this node. A reference 
	 * to the newly created Node is returned.
	 * @param element the object to be contained by the new Node.
	 * @return a reference to the newly created child Node.
	 */
	public GeneralNode<E> addChild(E element) {
		GeneralNode<E> newChild = new GeneralNode<E>(element);
		addChildNoChecks(newChild);
		return newChild;
	}
	/**
	 * Adds the given node as a child node of this Node
	 * An exception is thrown is this Node already contains 
	 * the node that is being added.
	 * @param child the new child node
	 * @throws DuplicateNodeException if this Node is already
	 * a parent node of the given Node.
	 */
	public void addChild(TreeNode<E> v) throws DuplicateNodeException {
		GeneralNode<E> child = checkNodeType(v);
		checkDuplicate(child);
		addChildNoChecks(child);

	}
	
	/**
	 * Does the actual adding of the child Node,
	 * assuming that all checks have been made
	 * @param child the Node to add as a child Node of this Node
	 */
	private void addChildNoChecks(GeneralNode<E> child) {
		child.setParent(this);
		child.setAllContainingTree(this.getContainingTree());
		children.add(child);
	}
	
	/**
	 * Convenience method to add multiple children at once,
	 * using {@code addChild(TreeNode<E> v)}.
	 * @param nodes
	 */
	@SafeVarargs
	public final void addChildren(TreeNode<E> ... nodes) {
		for (TreeNode<E> v : nodes) {
			addChild(v);
		}
	}
	
	/**
	 * Ensures that a node attempting to be added as a child of this Node
	 * is not already a child of this Node, or contained elsewhere in the same tree.
	 * If a duplicate is found, an exception is thrown.
	 * @param v the new child Node to check. Assumed not to be null.
	 * @throws DuplicateNodeException if the given Node is a duplicate of 
	 * an existing child of this Node, or is contained elsewhere in the same tree. 
	 */
	protected void checkDuplicate(GeneralNode<E> v) throws DuplicateNodeException {
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
	 * @return 
	 * @throws IllegalNodeException if the type of the given node is inappropriate for this Tree.
	 */
	protected static <E> GeneralNode<E> checkNodeType(TreeNode<E> v) {
		if (!(v instanceof GeneralNode))
			throw new IllegalNodeException(v);
		return (GeneralNode<E>)v;
	}
	
	/**
	 * Checks whether the specified tree contains this Node.
	 * A null containing Tree signifies no containing Tree
	 * The value returned by this method is identical to 
	 * {@code (t == null) ? containingTree == null : t.equals(this.getContainingTree())}
	 * @param t the tree to check for presence of this Node
	 * @return True if the given tree contains this Node, otherwise false
	 */
	@Override
	public boolean containedBy(Tree<E> t) {
		return (t == null) ? containingTree == null : t.equals(containingTree);
	}

	/**
	 * Returns a list of this Node's children in the order in which they were added.
	 * The collection is guaranteed to be random-access.
	 * @return list of this Node's children in the order in which they were added.
	 */
	@Override
	public List<TreeNode<E>> getChildren() {
		return new ArrayList<TreeNode<E>>(children);
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
	
	public GeneralNode<E> getParent() {
		return parent;
	}
	
	private void removeChild(GeneralNode<E> child) {
		children.remove(child);
	}

	
	void removeChild(TreeNode<E> child) {
		children.remove(child);
	}
	
	/**
	 * Unlinks this Node from its parent, if it has one.
	 */
	public void removeFromParent() {
		if (getParent() != null) {
			getParent().removeChild(this);
			setParent(null);
			setAllContainingTree(null);
		}
	}

	/**
	 * Sets the containing Tree of this node, 
	 * and all of its descendants, to the given tree.
	 * @param t the new containing Tree for this Node
	 * and all its children
	 */
	protected void setAllContainingTree(Tree<E> t) {
		if (!this.containedBy(t)) {
			setContainingTree(t);
			for (GeneralNode<E> child : children)
				child.setChildrenContainingTree(t);
		}
	}
	
	/**
	 * Sets the containing Tree of all of this Node's child nodes
	 * to be the specified tree 
	 * @param t the new containingTree for all children of this Node
	 */
	private void setChildrenContainingTree(Tree<E> t) {
		for (GeneralNode<E> child : children)
			child.setContainingTree(t);
	}
	
	/**
	 * Sets the containing Tree of this node to the given tree.
	 * A value of null indicates that this node is not associated with any tree.
	 * @param t the new containing Tree for this Node
	 */
	protected void setContainingTree(Tree<E> t) {
		// update tree sizes
		if (containingTree instanceof GeneralTree)
			((GeneralTree<E>)containingTree).size--;
		if (t instanceof AbstractTree)
			((GeneralTree<E>)t).size++;
		containingTree = t;
	}

	void setParent(GeneralNode<E> parent) {
		this.parent = parent;
	}
}