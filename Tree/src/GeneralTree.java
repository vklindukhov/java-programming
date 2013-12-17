/**
 * General Tree implementation, making use of the concept of 
 * a single containing Tree for each Node to simplify interactions 
 * between Tree and Node objects, and to efficiently implement 
 * a size method. Duplicate and null nodes are not allowed, 
 * with exceptions being throws if these conditions are about 
 * to be violated. In this tree class, Node objects must be of type
 * GeneralNode (or a subtype).
 * @author Max Fisher
 *
 */
public class GeneralTree<E> extends AbstractTree<E> {
	/**
	 * Number of nodes stored in this Tree.
	 */
	protected int size = 0;

	public GeneralTree() {
		this(null);
	}
	
	public GeneralTree(TreeNode<E> root) {
		super(root);
	}
	
	@Override
	public boolean containsNode(TreeNode<E> v) {
		return v instanceof GeneralNode && this.equals(((GeneralNode<E>)v).getContainingTree());
	}
	
	@Override
	public TreeNode<E> setRoot(TreeNode<E> root) {
		if (root != null) {
		GeneralNode<E> newRoot = new GeneralNode<E>().checkNodeType(root);
		newRoot.setAllContainingTree(this);
		}
		TreeNode<E> oldRoot = super.setRoot(root);
		if (oldRoot != null) { // then it's gotta be a GeneralNode
			((GeneralNode<E>)oldRoot).setAllContainingTree(null);
		}
		return oldRoot;
	}
	
	/**
	 * Returns the number of nodes in this tree.
	 * @return the number of nodes in this tree.
	 */
	@Override
	public int size() {
		return size;
	}

	@Override
	public String toString() {
		return "[" + String.valueOf(getRoot()) + " (" + size + ")]";
	}
}
