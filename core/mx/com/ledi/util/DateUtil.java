package mx.com.ledi.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Wolverime
 */
public class DateUtil {

	private DateUtil() {
	}

	public static Date getNow() {
		// return Calendar.getInstance().getTime();
		return new Date();
	}

	public static void setDay(Date date, DateInterval interval) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		interval.setStart(c.getTime());

		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 9999);
		interval.setEnd(c.getTime());
	}

	public static void setMonth(Date date, DateInterval interval) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// Start
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		interval.setStart(c.getTime());

		// End
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		interval.setEnd(c.getTime());
	}

	public static Date getStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 9999);
		return calendar.getTime();
	}

	public static Date getPrevMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		int m = c.get(Calendar.MONTH);
		if (m == 0) {
			c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
			c.set(Calendar.MONTH, 11);
		} else {
			c.set(Calendar.MONTH, m - 1);
		}
		return c.getTime();
	}

	public static Date getStartOfMonth(int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.MONTH, month);
		return c.getTime();
	}
}
