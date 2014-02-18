package com.ingenio.app.gui;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import mx.com.ledi.database.NavegadorDatos;
import mx.com.ledi.interfaces.listeners.PosicionListener;

/**
 *
 */
public class JLabelPosicion extends JLabel implements PosicionListener {

    public JLabelPosicion(NavegadorDatos nav) {
        super();
        setHorizontalAlignment(SwingConstants.CENTER);
        // setBorder(BorderFactory.createLineBorder(Color.green));
        setPreferredSize(new Dimension(80, 40));
        nav.addPosicionListener(this);
    }

    @Override
    public void actualizarPosicion(int pos, int total) {
        setText(String.format("<html><b>%s</b> de <b>%s</b></html>", pos + 1, total));
    }

}
