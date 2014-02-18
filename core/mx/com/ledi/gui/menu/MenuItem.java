package mx.com.ledi.gui.menu;

/**
 *
 * @author jimmy
 */
public class MenuItem {

	private String name;
	private String clazz;

	public MenuItem(String name, String clazz) {
		this.name = name;
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public String getClazz() {
		return clazz;
	}
}
