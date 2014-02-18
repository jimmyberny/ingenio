package com.ingenio.app.gui;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import mx.com.ledi.database.NavegadorDatos;
import mx.com.ledi.interfaces.listeners.EstadoListener;

/**
 *
 */
public class JLabelEstado extends JLabel implements EstadoListener {

    public JLabelEstado(NavegadorDatos nav) {
        super();
        setFont(getFont().deriveFont(Font.BOLD));
        setPreferredSize(new Dimension(160, 40));
        // setBorder(BorderFactory.createLineBorder(Color.red));
        nav.addEstadoListener(this);
    }

    @Override
    public void actualizarEstado(int state) {
        String str = "Sin definir";
        switch (state) {
            case NavegadorDatos.INSERTAR:
                str = "Nuevo registro";
                break;
            case NavegadorDatos.ACTUALIZAR:
                str = "Modificar registro";
                break;
            case NavegadorDatos.ELIMINAR:
                str = "Borrar registro";
                break;
        }
        setText(str);
    }
}
