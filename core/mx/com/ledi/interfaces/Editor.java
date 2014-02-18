package mx.com.ledi.interfaces;

import javax.swing.JComponent;
import javax.swing.JPanel;
import mx.com.ledi.error.AppException;

/**
 *
 * @author Enrique
 * @param <T>
 */
public abstract class Editor<T> extends JPanel {

    public abstract void initNoItem();

    public abstract void initItem();

    public abstract void mostrarItem(T item) throws AppException;

    public abstract void borrarItem(T item) throws AppException;

    public abstract T getItem() throws AppException;

    public abstract void setActivo(boolean activo);

    public abstract void actualizar();

    public abstract void limpiar();

    public JComponent getComponente() {
        return this;
    }
}
