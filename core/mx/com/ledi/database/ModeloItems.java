package mx.com.ledi.database;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.DataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @param <T>
 */
public class ModeloItems<T> extends AbstractListModel {

    private static final Logger log = LoggerFactory.getLogger(ModeloItems.class);

    private List<T> lista;
    private Saver<T> saver;
    private DataProvider<T> provider;

    private boolean ajustando;

    public ModeloItems(DataProvider<T> provider, Saver<T> saver) {
        this.provider = provider;
        this.saver = saver;
        lista = new ArrayList<T>(0);

        ajustando = false;
    }

    @Override
    public int getSize() {
        return lista.size();
    }

    @Override
    public T getElementAt(int index) {
        return lista.get(index);
    }

    public void cargar() throws AppException {
        lista = provider != null ? provider.cargar() : new ArrayList<T>(0);
    }
    
    public int registrar(T item) throws AppException{
        if (saver.puedeRegistrar() && saver.registrar(item)) {
            int idx = lista.size();
            lista.add(idx, item);
            fireIntervalAdded(this, idx, idx);
            return idx;
        } else {
            throw new AppException("No se pudo guardar el registro");
        }
    }
    
    public int modificar(int pos, T item) throws AppException {
        if (saver.puedeModificar() && pos >= 0 && pos < lista.size()) {
            if (saver.modificar(item)) {
                lista.set(pos, item);
                fireContentsChanged(this, pos, pos);
                return pos;
            } else {
                throw new AppException("No se pudo modificar el registro");
            }
        } else {
            throw new AppException("No se puede modificar el registro indicado");
        }
    }
    
    public int borrar(int pos) throws AppException {
        if (saver.puedeBorrar() && pos >= 0 && pos < lista.size()) {
            if (saver.borrar(lista.get(pos))) {
                lista.remove(pos);
                fireIntervalRemoved(this, pos, pos);
                return pos < lista.size() ? pos : lista.size() - 1;
            } else {
                throw new AppException("No se pudo borrar el registro");
            }
        } else {
            throw new AppException("No se puede borrar el registro indicado");
        }
    }
    
    public boolean puedeRegistrar() {
        return saver.puedeRegistrar();
    }

    public boolean puedeModificar() {
        return saver.puedeModificar();
    }

    public boolean puedeBorrar() {
        return saver.puedeBorrar();
    }
}
