package mx.com.ledi.util;

import java.awt.CardLayout;
import java.awt.Container;
import java.beans.PropertyChangeSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jun 27, 2013 - 1:52:31 PM
 *
 * @author jimmy
 */
public class CardNavigator {

	public static Logger log = LoggerFactory.getLogger(CardNavigator.class);
	//
	private Container container;
	private CardLayout cardLayout;
	//
	private String current;
	private String last;

	public CardNavigator(Container container) {
		this.container = container;
		cardLayout = (CardLayout) container.getLayout();
	}

	public CardNavigator(Container container, String start) {
		this(container);
		this.current = start;
	}

	public void moveTo(String card) {
		last = current;
		current = card;
		cardLayout.show(container, current);
	}

	public void goBack() {
		if (last != null) {
			moveTo(last);
		}
	}

	public boolean isOn(String card) {
		return current != null && current.equals(card);
	}
}
