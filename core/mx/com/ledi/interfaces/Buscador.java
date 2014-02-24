package mx.com.ledi.interfaces;

import com.ingenio.modelo.auxiliar.ListaSimple;
import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.JDialog;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.gui.Cancelable;
import mx.com.ledi.util.DialogKeyEventDistpatcher;

/**
 *
 * @author None
 * @param <T>
 */
public abstract class Buscador<T> extends JDialog implements Cancelable {

    protected T item;
    protected ListaSimple<T> modelo;
    protected EstadoDialogo estado;

    public Buscador(Frame parent, boolean modal) {
        super(parent, modal);
        init();
    }

    public Buscador(Dialog parent, boolean modal) {
        super(parent, modal);
        init();
    }

    private void init() {
        estado = new EstadoDialogo();
        DialogKeyEventDistpatcher.dispatch(this);
    }

    @Override
    public void cancel() {
        estado.cancelar();
        this.dispose();
    }

    protected void aceptar(int index) throws AppException {
        if (index >= 0) {
            item = modelo.get(index);
            estado.aceptar();
            this.dispose();
        } else {
            throw new AppException("Seleccion no válida");
        }
    }

    public T getItem() {
        return item;
    }

    public boolean isAceptar() {
        return estado.isAceptar();
    }

    public boolean isCancelar() {
        return estado.isCancelar();
    }

}
