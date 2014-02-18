package com.ingenio.app;

import javax.swing.JFrame;
import mx.com.ledi.interfaces.gui.Aplicacion;
import mx.com.ledi.interfaces.gui.AplicacionBean;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public abstract class AplicacionFrame extends JFrame implements Aplicacion {

	@Override
	public AplicacionBean getBean(Class clazz) {
		return getBean(clazz.getCanonicalName());
	}
}
