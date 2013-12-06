import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import org.abego.treelayout.TreeForTreeLayout;

public class GeneralTreeWrapper implements Tree<String>, TreeForTreeLayout<TreeNode<String>> {
	private GeneralTree<String> lolly;

	public GeneralTreeWrapper(GeneralTree<String> lolly) {
		if (lolly == null) 
			throw new IllegalArgumentException("Lolly must not be null");
		this.lolly = lolly;
	}
	
	public boolean containsNode(TreeNode<String> v) {
		return lolly.containsNode(v);
	}

	public TreeNode<String> setRoot(TreeNode<String> root) {
		return lolly.setRoot(root);
	}

	public boolean contains(Object o) {
		return lolly.contains(o);
	}

	public int size() {
		return lolly.size();
	}

	public String toString() {
		return lolly.toString();
	}

	public int depth(TreeNode<String> v) throws NoSuchNodeException {
		return lolly.depth(v);
	}

	public TreeNode<String> getRoot() {
		return lolly.getRoot();
	}

	public int height() {
		return lolly.height();
	}

	public int height(TreeNode<String> v) throws NoSuchNodeException {
		return lolly.height(v);
	}

	public boolean isEmpty() {
		return lolly.isEmpty();
	}

	public boolean isRoot(TreeNode<String> v) {
		return lolly.isRoot(v);
	}

	public int hashCode() {
		return lolly.hashCode();
	}

	public List<TreeNode<String>> preOrderTraversal() {
		return lolly.preOrderTraversal();
	}

	public List<TreeNode<String>> postOrderTraversal() {
		return lolly.postOrderTraversal();
	}

	public void print(PrintWriter pw) {
		lolly.print(pw);
	}

	public void print() {
		lolly.print();
	}

	public boolean equals(Object obj) {
		return lolly.equals(obj);
	}

	@Override
	public Iterable<TreeNode<String>> getChildren(TreeNode<String> arg0) {
		return arg0 == null ? null : arg0.getChildren();
	}

	@Override
	public Iterable<TreeNode<String>> getChildrenReverse(TreeNode<String> arg0) {
		List<TreeNode<String>> reversekids = arg0 == null ? null : arg0.getChildren();
		Collections.reverse(reversekids);
		return reversekids;
		
	}

	@Override
	public TreeNode<String> getFirstChild(TreeNode<String> arg0) {
		List<TreeNode<String>> children = arg0 == null ? null : arg0.getChildren();
		return children.isEmpty() ? null : children.get(0);
	}

	@Override
	public TreeNode<String> getLastChild(TreeNode<String> arg0) {
		List<TreeNode<String>> children = arg0 == null ? null : arg0.getChildren();
		return children.isEmpty() ? null : children.get(children.size() - 1);
	}

	@Override
	public boolean isChildOfParent(TreeNode<String> arg0, TreeNode<String> arg1) {
		return arg1 == null ? false : arg1.isAncestorOf(arg0);
	}

	@Override
	public boolean isLeaf(TreeNode<String> arg0) {
		return arg0 == null ? false : arg0.isExternal();
	}
	
	
}
