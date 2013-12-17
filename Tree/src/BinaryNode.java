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

 * 
 * @author Max Fisher
 * 
 * @param <E> the type to store in this Node
 */
public class BinaryNode<E> extends ContainingNode<E> implements
		BinaryTreeNode<E> {

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
	 * In this class, unlike in the GeneralNode class, adding a child Node
	 * only works with indices 0 and 1, and this method will throw an 
	 * IndexOutOfBoundsException if the index is neither of these.
	 * Furthermore, rather than adding the new child Node to the 
	 * current children, this class instead replaces the old child Node 
	 * at the given index, with the new child Node.
	 * @throws IndexOutOfBoundsException if the index neither 0 nor 1.
	 */
	@Override
	public void addChild(int index, TreeNode<E> child)
			throws DuplicateNodeException, IllegalNodeException,
			IndexOutOfBoundsException {
		if (index != 0 && index != 1)
			throw new IndexOutOfBoundsException("Index must be either 0 or 1");
		BinaryNode<E> newChild = checkNodeType(child);
		checkDuplicate(newChild);
		newChild.setAllContainingTree(this.getContainingTree());
		children[index] = newChild;
	}

	@Override
	protected BinaryNode<E> checkNodeType(TreeNode<E> v) {
		if (!(v instanceof BinaryNode))
			throw new IllegalNodeException(v);
		return (BinaryNode<E>)v;
	}

	@Override
	public List<TreeNode<E>> getChildren() {
		// return a copy of the list
		return new ArrayList<TreeNode<E>>(Arrays.asList(children));
	}

	@Override
	protected int getIndex(TreeNode<E> child) {
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
	 * Convenience method to set the left child.
	 * Equivalent to {@code this.addChild(0, child)}. 
	 */
	public void setLeft(TreeNode<E> child) throws DuplicateNodeException, IllegalNodeException{
		addChild(0, child);
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
	/**
	 * Convenience method to set the right child.
	 * Equivalent to {@code this.addChild(1, child)}. 
	 */
	public void setRight(TreeNode<E> child) throws DuplicateNodeException, IllegalNodeException{
		addChild(1, child);
	}
	
	@Override
	protected boolean removeChild(TreeNode<E> child) {
		int index;
		if ((index = getIndex(child)) == -1)
			return false;
		removeChild(index);
		return true;
	}

	@Override
	public BinaryTreeNode<E> right() {
		return children[1];
	}

}
