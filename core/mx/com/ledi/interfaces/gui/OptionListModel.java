package mx.com.ledi.interfaces.gui;

import java.util.List;
import mx.com.ledi.interfaces.Identificable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jimmy
 * @param <T>
 * @param <U>
 */
public class OptionListModel<T extends Identificable<U>, U> extends OptionAbstractModel<T, U> {

    public static Logger log = LoggerFactory.getLogger(OptionListModel.class);
    //
    private List<T> items;
    private boolean nullable;

    public OptionListModel(List<T> items) {
        this(items, false);
    }

    public OptionListModel(List<T> items, boolean nullable) {
        this.nullable = nullable;
        setItems(items);
    }

    public void clear() {
        setSelectedItem(null);
        items.clear();
        fireContentsChanged(this, -1, -1);
    }

    public List<T> getItems() {
        return items;
    }

    public final void setItems(List<T> items) {
        this.items = items;
        if (nullable) {
            this.items.add(0, null);
        }
    }

    @Override
    public T getItemById(U id) {
        T res = null;
        for (T t : items) {
            if (t != null && t.getId().equals(id)) {
                res = t;
                break;
            }
        }
        return res;
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public T getElementAt(int index) {
        return items.get(index);
    }
}
