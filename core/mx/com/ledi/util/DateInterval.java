package mx.com.ledi.util;

import java.util.Date;

/**
 *
 * @author Magneto
 */
public class DateInterval {

	private Date start;
	private Date end;

	public DateInterval() {
	}

	public static DateInterval getDay(Date date) {
		DateInterval ax = new DateInterval();
		ax.setDay(date);
		return ax;
	}

	public static DateInterval getMonth(Date date) {
		DateInterval ax = new DateInterval();
		ax.setMonth(date);
		return ax;
	}

	public void setDay(Date day) {
		if (day == null) {
			day = DateUtil.getNow();
		}
		start = DateUtil.getStartOfDay(day);
		end = DateUtil.getEndOfDay(day);
	}

	public void setToday() {
		setDay(DateUtil.getNow());
	}

	public void setMonth(Date day) {
		if (day == null) {
			day = DateUtil.getNow();
		}
		DateUtil.setMonth(day, this);
	}

	public void setCurrentMont() {
		setMonth(DateUtil.getNow());
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getStart() {
		return start;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getEnd() {
		return end;
	}

	public Object[] getParams() {
		Object[] pars = new Object[4];
		pars[0] = start == null ? Comparation.NULL : Comparation.GREATER_THAN;
		pars[1] = start;

		pars[2] = end == null ? Comparation.NULL : Comparation.LESS_EQUALS_THAN;
		pars[3] = end;
		return pars;
	}

	@Override
	public String toString() {
		return String.format("%s - %s",
				Format.DATETIME.format(start),
				Format.DATETIME.format(end));
	}
}
