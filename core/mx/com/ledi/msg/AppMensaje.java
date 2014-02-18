package mx.com.ledi.msg;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author Enrique, el de plaza Sesámo.
 */
public class AppMensaje {

    private String mensaje;
    private Throwable causa;
    private int tipo;

    public AppMensaje(String mensaje, Throwable causa, int tipo) {
        this.mensaje = mensaje;
        this.causa = causa;
        this.tipo = tipo;
    }

    public AppMensaje(String mensaje) {
        this(mensaje, null, JOptionPane.INFORMATION_MESSAGE);
    }

    public AppMensaje(String mensaje, Throwable causa) {
        this(mensaje, causa, JOptionPane.ERROR_MESSAGE);
    }

    public AppMensaje(Throwable causa) {
        this(causa.getMessage(), causa, JOptionPane.ERROR_MESSAGE);
    }

    public int getTipo() {
        return tipo;
    }

    public Throwable getCausa() {
        return causa;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void mostrar(Component padre) {
        AppDialog.mostrar(padre, this);
    }
}
