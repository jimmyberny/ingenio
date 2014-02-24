package com.ingenio.modelo.auxiliar;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author None
 */
public class ListaSimple<T> extends AbstractListModel {

    private List<T> datos;
    private boolean enlazado = true;

    public ListaSimple() {
    }

    public ListaSimple(List<T> datos) {
        this.datos = datos;
    }

    public void setList(List<T> datos) {
        if (datos == null) {
            datos = new ArrayList<>(5);
            enlazado = false;
        }
        this.datos = datos;
        fireContentsChanged(datos, 0, datos.size());
    }

    public void add(T item) {
        int s = datos.size();
        datos.add(item);
        fireIntervalAdded(this, s, s);
    }

    public void remove(int idx) {
        datos.remove(idx);
        fireIntervalRemoved(this, idx, idx);
    }

    public void update(int idx) {
        fireContentsChanged(this, idx, idx);
    }
    
    public void update(T item) {
        int pos = datos.indexOf(item);
        update(pos);
    }

    @Override
    public int getSize() {
        return datos != null ? datos.size() : 0;
    }

    public T get(int index) {
        return (T) getElementAt(index);
    }

    @Override
    public Object getElementAt(int index) {
        return datos != null ? datos.get(index) : null;
    }

    public boolean isEnlazado() {
        return enlazado;
    }

    public boolean contains(T o) {
        return datos.contains(o);
    }

    public List<T> getDatos() {
        return datos;
    }
}
