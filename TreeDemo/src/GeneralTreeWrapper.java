import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.abego.treelayout.TreeForTreeLayout;

public class GeneralTreeWrapper implements Tree<String>, TreeForTreeLayout<Node<String>> {
	private GeneralTree<String> lolly;
	
	public GeneralTreeWrapper(GeneralTree<String> lolly) {
		this.lolly = lolly;
	}

	public boolean ancestorOf(Node<String> u, Node<String> v)
			throws NoSuchNodeException {
		return lolly.ancestorOf(u, v);
	}

	public void add(GeneralTree<String> subTree, Node<String> parent)
			throws DuplicateNodeException, NoSuchNodeException {
		lolly.add(subTree, parent);
	}

	public Set<Node<String>> getNodes() {
		return lolly.getNodes();
	}

	public List<Node<String>> preOrderTraversal() {
		return lolly.preOrderTraversal();
	}

	public List<Node<String>> postOrderTraversal() {
		return lolly.postOrderTraversal();
	}

	public int hashCode() {
		return lolly.hashCode();
	}

	public Node<String> add(String element, Node<String> parent)
			throws NoSuchNodeException {
		return lolly.add(element, parent);
	}

	public boolean contains(Object o) {
		return lolly.contains(o);
	}

	public boolean containsNode(Node<String> v) {
		return lolly.containsNode(v);
	}

	public int depth(Node<String> v) throws NoSuchNodeException {
		return lolly.depth(v);
	}

	public void addNode(Node<String> v, Node<String> parent)
			throws DuplicateNodeException, IllegalNodeException,
			NoSuchNodeException {
		lolly.add(v, parent);
	}

	public boolean equals(Object obj) {
		return lolly.equals(obj);
	}

	public List<Node<String>> getChildren(Node<String> v)
			throws NoSuchNodeException {
		return lolly.getChildren(v);
	}

	public Node<String> getRoot() {
		return lolly.getRoot();
	}

	public Node<String> getParent(Node<String> v) throws NoSuchNodeException {
		return lolly.getParent(v);
	}

	public int height() {
		return lolly.height();
	}

	public int height(Node<String> v) throws NoSuchNodeException {
		return lolly.height(v);
	}

	public boolean isEmpty() {
		return lolly.isEmpty();
	}

	public boolean isExternal(Node<String> v) throws NoSuchNodeException {
		return lolly.isExternal(v);
	}

	public boolean isInternal(Node<String> v) throws NoSuchNodeException {
		return lolly.isInternal(v);
	}

	public Tree<String> removeSubtree(Node<String> v)
			throws NoSuchNodeException {
		return lolly.removeSubtree(v);
	}

	public boolean isRoot(Node<String> v) throws NoSuchNodeException {
		return lolly.isRoot(v);
	}

	public Iterator<String> iterator() {
		return lolly.iterator();
	}

	public Node<String> setRoot(Node<String> root) {
		return lolly.setRoot(root);
	}

	public String removeNode(Node<String> v)
			throws UnsupportedOperationException, NoSuchNodeException {
		return lolly.removeNode(v);
	}

	public int size() {
		return lolly.size();
	}

	public String toString() {
		return lolly.toString();
	}

	public Collection<Node<String>> getDescendants(Node<String> v)
			throws NoSuchNodeException {
		return lolly.getDescendants(v);
	}

	@Override
	public Iterable<Node<String>> getChildrenReverse(Node<String> arg0) {
		List<Node<String>> children = lolly.getChildren(arg0);
		Collections.reverse(children);
		return children;
	}

	@Override
	public Node<String> getFirstChild(Node<String> arg0) {
		return (Node<String>) lolly.getChildren(arg0).get(0);
	}

	@Override
	public Node<String> getLastChild(Node<String> arg0) {
		return (Node<String>) lolly.getChildren(arg0).get(lolly.getChildren(arg0).size()-1);
	}

	@Override
	public boolean isChildOfParent(Node<String> arg0, Node<String> arg1) {
		return lolly.ancestorOf(arg1, arg0);
	}

	@Override
	public boolean isLeaf(Node<String> arg0) {
		return lolly.isExternal(arg0);
	}

	@Override
	public void print(PrintWriter pw) {
		lolly.print(pw);		
	}
	@Override
	public void print() {
		lolly.print();		
	}
	
	
}
