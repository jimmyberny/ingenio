package mx.com.ledi.interfaces.gui;

import mx.com.ledi.gui.menu.MenuUtil;
import org.hibernate.SessionFactory;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public interface Aplicacion {

	public SessionFactory getFactory();
	
	public MenuUtil getMenu();

	public void mostrarTarea(String tarea);

	public AplicacionBean getBean(Class clazz);

	public AplicacionBean getBean(String className);
}
