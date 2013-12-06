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
	private static <E> int localPathLength(TreeNode<E> v, int depth) {
		int pathLength = depth; // account for path length of v itself
		for (TreeNode<E> child : v.getChildren()) {
			pathLength += localPathLength(child, depth+1); // depth is calculated anyway
		}
		return pathLength;
	}
	private static void pathLengthTest() {
		AbstractTree<Character> t = new GeneralTree<>();
		GeneralNode<Character> A = new GeneralNode<>('A');
		GeneralNode<Character> B = new GeneralNode<>('B');
		GeneralNode<Character> C = new GeneralNode<>('C');
		GeneralNode<Character> D = new GeneralNode<>('D');
		GeneralNode<Character> E = new GeneralNode<>('E');
		GeneralNode<Character> F = new GeneralNode<>('F');
		GeneralNode<Character> G = new GeneralNode<>('G');
		GeneralNode<Character> H = new GeneralNode<>('H');
		GeneralNode<Character> I = new GeneralNode<>('I');
		GeneralNode<Character> J = new GeneralNode<>('J');
		GeneralNode<Character> K = new GeneralNode<>('K');
		GeneralNode<Character> L = new GeneralNode<>('L');
		GeneralNode<Character> M = new GeneralNode<>('M');
		GeneralNode<Character> N = new GeneralNode<>('N');
		GeneralNode<Character> O = new GeneralNode<>('O');
		GeneralNode<Character> P = new GeneralNode<>('P');
		GeneralNode<Character> Q = new GeneralNode<>('Q');
		GeneralNode<Character> R = new GeneralNode<>('R');
		
		t.setRoot(R);
		R.addChildren(A, B, C);
		A.addChild(D);
		B.addChildren(E, F);
		C.addChildren(G, H, I);
		F.addChildren(J, K, L);
		G.addChild(M);
		K.addChild(N);
		L.addChild(O);
		O.addChild(P);
		I.addChild(Q);

		System.out.println("Path length should be 43");
		System.out.println("Path length calculated: " + pathLength(t));
		t.print();
	}
	
	public static void main(String[] args) {
		pathLengthTest();
	}
}
