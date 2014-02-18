package mx.com.ledi.gui.menu;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jimmy
 */
public class TreeMenuModel extends DefaultTreeModel {

	public static Logger log = LoggerFactory.getLogger(TreeMenuModel.class);

	public TreeMenuModel(TreeNode root) {
		super(root);
	}
}
