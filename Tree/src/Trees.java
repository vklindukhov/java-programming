/**
 * Collection of static methods implementing general tree algorithms
 * @author Max Fisher
 *
 */
public class Trees {
	// Can't instantiate
	private Trees() {
	}
	
	/**
	 * Calculates the path length of the given tree.
	 * The path length is defined as the sum of the depths
	 * of all nodes in the tree.
	 * @param t the Tree to measure the path length of
	 * @return the path length of the Tree, or the sum of the 
	 * depths of all its nodes.
	 */
	public static <E> int pathLength(Tree<E> t) {
		return localPathLength(t.getRoot(), 0);
	}
	
	/**
	 * Helper method to use in calculating path length recursively
	 * Calculates the path length of the subtree of the original tree
	 * passed to {@code pathLength()} formed by removing all nodes that
	 * are neither descendants or ancestors of the given node.
	 * Example: 
	 * If the original tree passed to {@code pathLength()} was the following
	 *      R
	 *     / \
	 *    A   B
	 *   / \ / \
	 *  C  D E  F
	 * 
	 * Then calling localPathLength on B, or more precisely, 
	 * {@code localPathLength(B, 1)} would calculate the path length
	 * of the following tree: 
	 * 	    R
	 *       \
	 *        B
	 *       / \
	 *       E  F
	 * @param v the Node to use in forming the subtree of the Tree
	 * passed to {@code pathlength()}.
	 * @param depth the depth of v (can be calculated independently 
	 * but this saves time)
	 * @return the path length of the tree formed by all descendents
	 * of the specified node, and all the parents of that node.
	 */
	private static <E> int localPathLength(Node<E> v, int depth) {
		int pathLength = depth; // account for path length of v itself
		for (Node<E> child : v.getChildren()) {
			pathLength += localPathLength(child, depth+1); // depth is calculated anyway
		}
		return pathLength;
	}
	private static void pathLengthTest() {
		GeneralTree<Character> t = new GeneralTree<>();
		t.setRoot(new GeneralNode<>('R'));
		Node<Character> A = t.add('A', t.getRoot());
		Node<Character> B = t.add('B', t.getRoot());
		Node<Character> C = t.add('C', t.getRoot());
		Node<Character> D = t.add('D', A);
		Node<Character> E = t.add('E', B);
		Node<Character> F = t.add('F', B);
		Node<Character> G = t.add('G', C);
		Node<Character> H = t.add('H', C);
		Node<Character> I = t.add('I', C);
		Node<Character> J = t.add('J', F);
		Node<Character> K = t.add('K', F);
		Node<Character> L = t.add('L', F);
		Node<Character> M = t.add('M', G);
		Node<Character> N = t.add('N', K);
		Node<Character> O = t.add('O', L);
		Node<Character> P = t.add('P', O);
		Node<Character> Q = t.add('Q', I);
		t.add((GeneralTree<Character>)null, I);
		
		System.out.println("Path length should be 43");
		System.out.println("Path length calculated: " + pathLength(t));
		t.print();
	}
	
	public static void main(String[] args) {
		pathLengthTest();
	}
}
