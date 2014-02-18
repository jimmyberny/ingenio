package com.ingenio.app.gui;

import java.awt.Dimension;
import javax.swing.JLabel;
import mx.com.ledi.interfaces.listeners.MonitorListener;
import mx.com.ledi.interfaces.listeners.MonitorViewInterface;

/**
 *
 */
public class JLabelMonitor extends JLabel implements MonitorViewInterface {

	public JLabelMonitor() {
		super();
	}
	
    public JLabelMonitor(MonitorListener monitor) {
        super();
        // setBorder(BorderFactory.createLineBorder(Color.blue));
        setPreferredSize(new Dimension(40, 40));
        monitor.addView(this);
    }

    @Override
    public void changeState(boolean state) {
        setText(state ? "*" : "");
    }
}
