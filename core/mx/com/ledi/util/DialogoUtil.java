package mx.com.ledi.util;

import java.awt.Window;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author Enrique
 */
public class DialogoUtil {

	public static boolean yes(JComponent parent, String mensaje) {
		return JOptionPane.YES_OPTION == confirmar(parent, mensaje, JOptionPane.YES_NO_OPTION);
	}
	
	public static int confirmar(JComponent parent, String mensaje, int tipo) {
		return JOptionPane.showConfirmDialog(GuiUtil.getWindow(parent), mensaje,
				"Mensaje de la aplicación", tipo);
	}
	
	public static void mensaje(JComponent parent, String mensaje) {
		JOptionPane.showMessageDialog(GuiUtil.getWindow(parent), mensaje, 
				"Mensaje de la aplicación", JOptionPane.INFORMATION_MESSAGE);
	}
}
