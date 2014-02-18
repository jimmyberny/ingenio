package mx.com.ledi.interfaces.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import mx.com.ledi.util.JCalendarButton;

/**
 *
 * @author Enrique
 */
public class MonitorListener implements DocumentListener, ListDataListener,
		ChangeListener, ItemListener, PropertyChangeListener,
		TableModelListener {

	private boolean dirty;
	private List<MonitorViewInterface> views;

	public MonitorListener() {
		dirty = false;
		views = new ArrayList<MonitorViewInterface>(1);
	}

	public void addView(MonitorViewInterface view) {
		views.add(view);
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		if (this.dirty != dirty) {
			this.dirty = dirty;
			updateView();
		}
	}

	private void updateView() {
		if (!views.isEmpty()) {
			for (MonitorViewInterface mvi : views) {
				mvi.changeState(dirty);
			}
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		setDirty(true);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		setDirty(true);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		setDirty(true);
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		setDirty(true);
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		setDirty(true);
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		setDirty(true);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		setDirty(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		setDirty(true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setDirty(true);
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		if (TableModelEvent.UPDATE == e.getType()) {
			setDirty(true);
		}
	}

	public void listenTo(JTextField field) {
		field.getDocument().addDocumentListener(this);
	}

	public void listenTo(JTextArea field) {
		field.getDocument().addDocumentListener(this);
	}

	public void listenTo(JCalendarButton button) {
		button.addPropertyChangeListener("date", this);
	}
	
	public void listenTo(JComboBox field) {
		field.addItemListener(this);
	}
}
