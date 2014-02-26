package mx.com.ledi.interfaces.gui;

import javax.swing.AbstractListModel;
import mx.com.ledi.interfaces.Identificable;

/**
 *
 * @author None
 */
public abstract class OptionAbstractModel<T extends Identificable<U>, U> extends AbstractListModel<T> implements javax.swing.ComboBoxModel<T> {

    protected T selected;

    @Override
    public void setSelectedItem(Object anItem) {
        if ((selected != null && !selected.equals(anItem))
                || (selected == null && anItem != null)) {
            selected = (T) anItem;
            fireContentsChanged(this, -1, -1);
        }
    }

    @Override
    public Object getSelectedItem() {
        return selected;
    }

    public T getSelected() {
        return selected;
    }

    public U getSelectedId() {
        return selected != null ? selected.getId() : null;
    }

    public void setSelected(T value) {
        if (value != null) {
            setSelectedById(value.getId());
        } else {
            setSelectedItem(null);
        }
    }

    public abstract T getItemById(U id);

    public void setSelectedById(U id) {
        T sel = null;
        if (id != null) {
            sel = getItemById(id);
        }
        setSelectedItem(sel);
    }
}
