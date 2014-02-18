package com.ingenio.app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import mx.com.ledi.util.DialogoUtil;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class AppCerrar extends WindowAdapter {

	@Override
	public void windowClosing(WindowEvent e) {
		if (DialogoUtil.yes(null, "¿Desea cerrar la aplicación?")) {
			System.exit(1);
		}
	}

}
