package mx.com.ledi.gui.menu;

/**
 *
 * @author jimmy
 */
public class MenuAction extends MenuItem {

	private String method;

	public MenuAction(String name, String clazz, String method) {
		super(name, clazz);
		this.method = method;
	}

	public String getMethod() {
		return method;
	}
}
