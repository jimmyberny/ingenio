package mx.com.ledi.interfaces.gui;

import javax.swing.JComponent;
import mx.com.ledi.error.AppException;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public interface AplicacionView extends AplicacionBean {

	public void showView() throws AppException;

	public boolean hideView();

	public String getTitle();

	public JComponent getComponent();
}
