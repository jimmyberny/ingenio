package mx.com.ledi.util;

import mx.com.ledi.interfaces.gui.Identifiable;
import mx.com.ledi.lang.AppInt;

/**
 *
 * @author Mystic
 */
public enum Comparation implements Identifiable<Integer> {

	NULL(0, ""),
	EQUALS(1, "label.equals"),
	CONTAINS(2, "label.contains"),
	STARTS(3, "label.startswith"),
	ENDS(4, "label.endswith"),
	GREATER_THAN(5, "label.greaterthan"),
	GREATER_EQUALS_THAN(6, "label.greaterequalthan"),
	LESS_THAN(7, "label.lessthan"),
	LESS_EQUALS_THAN(8, "label.lessequalthan"),
	IDENTITY(9, "label.equals");
	//
	private int id;
	private String label;

	private Comparation(int id, String label) {
		this.id = id;
		this.label = AppInt.str(label);
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return label;
	}
}
