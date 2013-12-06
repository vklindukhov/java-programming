import java.util.List;
import java.util.ArrayList;
/**
 * Class to test assignment of pointer values
 * @author Max Fisher
 *
 */
public class NullTest {
	public static void main(String[] args) {
		List<TreeNode<String>> nodes = new ArrayList<TreeNode<String>>();
		for (int i = 0; i < 10; i++) {
			nodes.add(new GeneralNode<String>(String.valueOf(i)));
		}
		System.out.println(nodes);
		TreeNode<String> a = nodes.get(1);
		TreeNode<String> b = nodes.get(6);
		a = b;
		b = a;
		a = null;
		System.out.println(nodes);
	}
}
