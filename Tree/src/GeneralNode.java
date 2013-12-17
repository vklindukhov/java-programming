import java.util.ArrayList;
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

public class GeneralNode<E> extends ContainingNode<E> implements MutableNode<E> {
	/**
	 * Iterable collection of the children of this Node
	 */
	private List<GeneralNode<E>> children;
	
	public GeneralNode() {
		this(null);
	}
	
	public GeneralNode(E element) {
		super(element);
		this.children = new ArrayList<GeneralNode<E>>();
	}

	@Override
	public void addChild(int index, TreeNode<E> v)  
		throws DuplicateNodeException, IllegalNodeException, IndexOutOfBoundsException {
		GeneralNode<E> child = checkNodeType(v);
		checkDuplicate(child);
		// actually add
		child.setParent(this);
		child.setAllContainingTree(this.getContainingTree());
		children.add(index, child);
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
		// actually add
		child.setParent(this);
		child.setAllContainingTree(this.getContainingTree());
		children.add(child);
	}
		
	/**
	 * Convenience method to add multiple children at once,
	 * using {@code addChild(AbstractGeneralNode<E> v)}.
	 * @param nodes
	 */
	@SafeVarargs
	public final void addChildren(GeneralNode<E> ... nodes) {
		for (GeneralNode<E> v : nodes)
			addChild(v);
	}
	
	/**
	 * Convenience method to add multiple children at once,
	 * using {@code addChild(TreeNode<E> v)}.
	 * @param nodes
	 */
	@SafeVarargs
	public final void addChildren(TreeNode<E> ... nodes) {
		for (TreeNode<E> v : nodes)
			addChild(v);
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
	@Override
	protected GeneralNode<E> checkNodeType(TreeNode<E> v) {
		if (!(v instanceof GeneralNode))
			throw new IllegalNodeException(v);
		return (GeneralNode<E>)v;
	}

	@Override
	public List<TreeNode<E>> getChildren() {
		return new ArrayList<TreeNode<E>>(children);
	}

	@Override
	protected int getIndex(TreeNode<E> child) {
		return children.indexOf(child);
	}

	@Override
	protected TreeNode<E> removeChild(int index) throws IndexOutOfBoundsException {
		return children.remove(index);
	}
	
	@Override
	protected boolean removeChild(TreeNode<E> child) {
		return children.remove(child);
	}

	@Override
	public void removeFromParent() {
		super.removeFromParent();
	}

}