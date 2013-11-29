import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;

import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;

/**
 * Demonstrates how to use the {@link TreeLayout} to render a tree in a Swing
 * application.
 * <p>
 * Intentionally the sample code is kept simple. I.e. it does not include stuff
 * like anti-aliasing and other stuff one would add to make the output look
 * nice.
 * <p>
 * Screenshot:
 * <p>
 * <img src="doc-files/swingdemo.png">
 *
 * @author Udo Borkowski (ub@abego.org)
 */
public class SwingDemo {

        private static void showInDialog(JComponent panel) {
                JDialog dialog = new JDialog();
                Container contentPane = dialog.getContentPane();
                ((JComponent) contentPane).setBorder(BorderFactory.createEmptyBorder(
                                10, 10, 10, 10));
                contentPane.add(panel);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // exit on close
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
        }


        /**
         * Shows a dialog with a tree in a layout created by {@link TreeLayout},
         * using the Swing component {@link TextInBoxTreePane}.
         */
        public static void main(String[] args) {
                // get the sample tree
        		Node<String> root = new GeneralNode<String>("Hello");
        		GeneralTreeWrapper tree = new GeneralTreeWrapper(new GeneralTree<String>(root));
        		Node<String> initial = tree.add("Zero", root);
        		@SuppressWarnings("unchecked")
				Node<String>[] nodes = (Node<String>[]) new Node[12];
        		nodes[0] = initial;
        		for (int i = 1; i < 12; i++) {
        			nodes[i] = tree.add(String.valueOf("A"), nodes[i-1]);
        			tree.add(String.valueOf("B"), nodes[i/2]);
        			tree.add(String.valueOf("C"), nodes[i/3]);
        		}
        		tree = new GeneralTreeWrapper((GeneralTree<String>) tree.removeSubtree(initial));
        		
        		tree.print();
                // setup the tree layout configuration
                double gapBetweenLevels = 50;
                double gapBetweenNodes = 10;
                DefaultConfiguration<Node<String>> configuration = new DefaultConfiguration<Node<String>>(
                                gapBetweenLevels, gapBetweenNodes);

                // create the NodeExtentProvider for TextInBox nodes
                TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();

                // create the layout
                TreeLayout<Node<String>> treeLayout = new TreeLayout<Node<String>>(tree,
                                nodeExtentProvider, configuration);

                // Create a panel that draws the nodes and edges and show the panel
                TextInBoxTreePane panel = new TextInBoxTreePane(treeLayout);
                showInDialog(panel);
        }
}

