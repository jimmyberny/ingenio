package com.ingenio.modelo.auxiliar;

import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author None
 */
public class ListaSimple<T> extends AbstractListModel {

    private List<T> datos;
    
    public ListaSimple() {
    }

    public ListaSimple(List<T> datos) {
        this.datos = datos;
    }

    public void setList(List<T> datos) {
        int last = this.datos != null ? this.datos.size() : 0;
        this.datos = datos;
        
        fireContentsChanged(datos, 0, last);
    }

    @Override
    public int getSize() {
        return datos != null ? datos.size() : 0;
    }

    @Override
    public Object getElementAt(int index) {
        return datos != null ? datos.get(index) : null;
    }

}
