package mx.com.ledi.util;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.JFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Captain America
 */
public class GuiUtil {

	public static Logger log = LoggerFactory.getLogger(GuiUtil.class);
	//

	public static Window getWindow(Component comp) {
		if (comp == null) {
			return new JFrame();
		} else {
			if (comp instanceof Dialog || comp instanceof Frame) {
				return (Window) comp;
			} else {
				return getWindow(comp.getParent());
			}
		}
	}

	public static Class getWindowClass(Component comp) {
		// log.info("Class to eval {}", comp.getClass().getCanonicalName());
		return getWindowType(comp.getClass());
	}

	private static Class getWindowType(Class clazz) {
		// log.info("arrive with {}, parent {} is {}", clazz,
		// 		clazz.getSuperclass() != null ? clazz.getSuperclass().getCanonicalName() : null);
		if (clazz == Frame.class || clazz == Dialog.class) {
			return clazz;
		} else if (clazz.getSuperclass() != null) {
			return getWindowType(clazz.getSuperclass());
		} else {
			return Frame.class;
		}
	}
}
