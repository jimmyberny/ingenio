package mx.com.ledi.gui.menu;

import mx.com.ledi.lang.AppInt;


/**
 *
 * @author jimmy
 */
public class MenuUtil {

	private MenuNode menu;
	private MenuNode last;

	public MenuUtil() {
		menu = new MenuNode("label.root");
		last = menu;
	}

	public MenuNode getRoot() {
		return menu;
	}

	public void menu(String name) {
		MenuNode parent = last;
		last = new MenuNode(AppInt.str(name));
		parent.add(last);
	}

	public void closeSub() {
		last = (MenuNode) last.getParent();
	}

	public void item(String rbKey, Class clazz) {
		addItem(rbKey, clazz.getCanonicalName());
	}

	public void addAction(String rbKey, String klass, String actionMethod) {
		if (last == null) {
			throw new IllegalStateException("There is no parent to add item");
		}
		last.add(new MenuNode(rbKey, klass, actionMethod));
	}

	public void addItem(String rbKey, String clazz) {
		if (last == null) {
			throw new IllegalStateException("There is no parent to add item");
		}
		last.add(new MenuNode(AppInt.str(rbKey), clazz));
	}
}
