package mx.com.ledi.gui.menu;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author jimmy
 */
public class MenuNode extends DefaultMutableTreeNode {

	public MenuNode(MenuItem item) {
		super(item);
	}

	public MenuNode(String name) {
		this(new MenuItem(name, null));
	}

	public MenuNode(String name, String clazz) {
		this(new MenuItem(name, clazz));
	}
	
	public MenuNode(String name, String action, String method){
		this(new MenuAction(name, action, method));
	}

	public String getName() {
		return ((MenuItem) getUserObject()).getName();
	}

	public String getClassname() {
		return ((MenuItem) getUserObject()).getClazz();
	}

	public boolean isAction() {
		return getUserObject() instanceof MenuAction;
	}

	@Override
	public String toString() {
		return getName();
	}
}
