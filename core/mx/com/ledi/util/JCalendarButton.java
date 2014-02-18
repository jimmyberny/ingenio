package mx.com.ledi.util;

import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

/**
 *
 * @author Green lantern
 */
public class JCalendarButton extends JDateChooser {

	private Format<Date> format;
	private JTextField editor;

	public JCalendarButton() {
		((JFormattedTextField) getDateEditor()).setVisible(false);
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		getCalendarButton().setPreferredSize(preferredSize);
	}

	@Override
	public void setEnabled(boolean bln) {
		super.setEnabled(bln);
		if (editor != null) {
			editor.setEnabled(bln);
		}
	}

	public void setEditor(JTextField editor) {
		setEditor(editor, Format.DATETIME);
	}

	public void setEditor(JTextField jtf, Format<Date> ft) {
		this.editor = jtf;
		this.format = ft;

		this.editor.setEditable(false);
		addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("date".equals(evt.getPropertyName())) {
					editor.setText(format.format((Date) evt.getNewValue()));
				}
			}
		});
	}
}
