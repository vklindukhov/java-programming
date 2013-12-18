import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class implementing a node to be used in a binary tree. This node allows
 * direct access to and modification of left and right child nodes. As with the
 * GeneralNode class, duplicate and null nodes are not allowed, and this
 * implementation throws exceptions if these conditions are violated.
 * 
 * Note that this class's add and remove methods for children work
 * differently from that of the GeneralNode class; see the individual 
 * methods for details.
 * 
 * @author Max Fisher
 * 
 * @param <E> the element type to store in this Node
 */
public class BinaryNode<E> extends KAryNode<E> implements
		BinaryTreeNode<E>, IndexMutableNode<E> {

	private BinaryNode<E>[] children; // always of length 2
	
	public BinaryNode() {
		this(null);
	}
	
	@SuppressWarnings("unchecked")
	public BinaryNode(E element) {
		super(element);
		this.children = (BinaryNode<E>[]) new BinaryNode[2];
	}
	
	/**
	 * This class does not support adding nodes with no index
	 * as the semantics are undefined.
	 */
	@Override
	public void addChild(TreeNode<E> child) {
		super.addChild(child);
	}

	/**
	 * Creates a new Node object containing the given object,
	 * and sets it as a left child Node of this node. A reference 
	 * to the newly created Node is returned.
	 * @param element the object to be contained by the new Node.
	 * @return a reference to the newly created child Node.
	 */
	public BinaryNode<E> addLeft(E element) {
		BinaryNode<E> newChild = new BinaryNode<E>(element);
		setLeft(newChild);
		return newChild;
	}

	/**
	 * Creates a new Node object containing the given object,
	 * and sets it as a right child Node of this node. A reference 
	 * to the newly created Node is returned.
	 * @param element the object to be contained by the new Node.
	 * @return a reference to the newly created child Node.
	 */
	public BinaryNode<E> addRight(E element) {
		BinaryNode<E> newChild = new BinaryNode<E>(element);
		setLeft(newChild);
		return newChild;
	}

	@Override
	public List<TreeNode<E>> getChildren() {
		// return a copy of the list
		return new ArrayList<TreeNode<E>>(Arrays.asList(children));
	}

	@Override
	public int getIndex(TreeNode<E> child) {
		if (child == null)
			return -1;
		else if (child.equals(children[0]))
			return 0;
		else if (child.equals(children[1]))
			return 1;
		else 
			return -1;
	}
	
	@Override
	public boolean hasLeft() {
		return left() != null;
	}

	@Override
	public boolean hasRight() {
		return right() != null;
	}

	@Override
	public List<TreeNode<E>> inOrderTraversal() {
		List<TreeNode<E>> inOrder = new ArrayList<>();
		if (hasLeft())
			inOrder.addAll(left().inOrderTraversal());
		inOrder.add(this);
		if (hasRight())
			inOrder.addAll(left().inOrderTraversal());
		return inOrder;
	}
	@Override
	public BinaryTreeNode<E> left() {
		return children[0];
	}
	
	/**
	 * In this class, unlike in the GeneralNode class, removing a child Node
	 * only works with indices 0 and 1, and this method will throw an 
	 * IndexOutOfBoundsException if the index is neither of these.
	 * Furthermore, removal of the child at index 0 (the left child)
	 * does not cause the right child to subsequently become the left child;
	 * this removal does not affect the indices of the other children.
	 * @throws IndexOutOfBoundsException if the index neither 0 nor 1.
	 */
	@Override
	public TreeNode<E> removeChild(int index) {
		if (index != 0 && index != 1)
			throw new IndexOutOfBoundsException("Index must be either 0 or 1");
		BinaryNode<E> removed = children[index];
		removed.setAllContainingTree(null);
		children[index] = null;
		return removed;
	}
	@Override
	public void removeFromParent() {
		super.removeFromParent();
	}
	/**
	 * Convenience method to remove the left child.
	 * Equivalent to {@code this.removeChild(0)}
	 * @return the left child of this Node, or null 
	 * if there was no left child.
	 */
	public TreeNode<E> removeLeft() {
		return removeChild(0);
	}
	
	/**
	 * Convenience method to remove the right child.
	 * Equivalent to {@code this.removeChild(1)}
	 * @return the right child of this Node, or null 
	 * if there was no right child.
	 */
	public TreeNode<E> removeRight() {
		return removeChild(1);
	}
	@Override
	public BinaryTreeNode<E> right() {
		return children[1];
	}
	
	/**
	 * Sets the specified node as a child node of this Node with the 
	 * specified index.
	 * The child node must not be null or otherwise any type not allowed by 
	 * {@code checkNodeType()}. It must also not be an existing child 
	 * of this Node, or have the same containing Tree at the time it is added. 
	 * The index of the new child node must be between zero and the total 
	 * number of permitted children of this Node.
	 * @param index the index to associate with the new child Node.
	 * @param child the new child Node of this Node
	 * @throws DuplicateNodeException if the specified node is an existing child of this Node, 
	 * or has the same containing Tree as this one at the time when it is added to this Node
	 * @throws IllegalNodeException if the specified node is of an illegal type
	 * (for example null). Subclasses may be more restrictive and disallow other types.
	 * @throws IndexOutOfBoundsException if the index supplied is negative 
	 * or greater than the number of children this Node has at the time 
	 * this method executes.
	 */
	@Override
	public void setChild(int index, TreeNode<E> child)
			throws DuplicateNodeException, IllegalNodeException,
			IndexOutOfBoundsException {
		if (index != 0 && index != 1)
			throw new IndexOutOfBoundsException("Index must be either 0 or 1");
		BinaryNode<E> newChild = checkNodeType(child);
		checkDuplicate(newChild);
		newChild.setAllContainingTree(this.getContainingTree());
		children[index] = newChild;
	}

	/**
	 * Convenience method to set the left child.
	 * Equivalent to {@code this.addChild(0, child)}. 
	 */
	public void setLeft(TreeNode<E> child) throws DuplicateNodeException, IllegalNodeException{
		setChild(0, child);
	}
	/**
	 * Convenience method to set the right child.
	 * Equivalent to {@code this.addChild(1, child)}. 
	 */
	public void setRight(TreeNode<E> child) throws DuplicateNodeException, IllegalNodeException{
		setChild(1, child);
	}
	
	@Override
	protected BinaryNode<E> checkNodeType(TreeNode<E> v) {
		if (!(v instanceof BinaryNode))
			throw new IllegalNodeException(v);
		return (BinaryNode<E>)v;
	}

	@Override
	protected boolean removeChild(TreeNode<E> child) {
		int index;
		if ((index = getIndex(child)) == -1)
			return false;
		removeChild(index);
		return true;
	}

}
