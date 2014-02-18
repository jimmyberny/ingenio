package mx.com.ledi.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import mx.com.ledi.error.AppException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Enrique, el de plaza Sésamo.
 * @param <T>
 */
public abstract class Format<T> {

	public static Logger log = LoggerFactory.getLogger(Format.class);
	//
	private static final Locale locale = Locale.getDefault();
	//
	private static NumberFormat NF_INTEGER = NumberFormat.getIntegerInstance(locale);
	private static NumberFormat NF_DECIMAL = NumberFormat.getNumberInstance(locale);
	private static NumberFormat NF_CURRENCY = NumberFormat.getCurrencyInstance(locale);
	private static NumberFormat NF_PERCENT = NumberFormat.getPercentInstance(locale);
	private static DateFormat DF_TIME = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
	private static DateFormat DF_DATE = DateFormat.getDateInstance(DateFormat.SHORT, locale);
	private static DateFormat DF_DATETIME = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
	private static DateFormat DF_CUSTOM_TS = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
	//
	public static final Format<Integer> INTEGER = new IntegerFormatImp();
	public static final Format<Double> DECIMAL = new DecimalFormatImp();
	public static final Format<Double> CURRENCY = new CurrencyFormatImp();
	public static final Format<Double> PERCENT = new PercentFormatImp();
	public static final Format<Date> TIME = new TimeFormatImp();
	public static final Format<Date> DATE = new DateFormatImp();
	public static final Format<Date> DATETIME = new DateTimeFormatImp();
	public static final Format<Date> TIMESTAMP = new TimeStampFormatImp();
	public static final Format<Object> OBJECT = new ObjectFormatImp();
	//
	protected String pattern;

	protected abstract String getFormated(T value);

	protected abstract T getParsed(String str) throws AppException;

	public String format(T value, String... pattern) {
		if (pattern != null) {
			this.pattern = pattern[0];
		}
		return format(value);
	}

	public String format(T value) {
		if (value != null) {
			return getFormated(value);
		}
		return "";
	}

	public T parse(String str) throws AppException {
		if (str == null || str.isEmpty()) {
			return null;
		} else {
			return getParsed(str);
		}
	}

	public T parse(String str, T backup) {
		T res = null;
		try {
			res = parse(str);
		} catch (AppException eex) {
		}
		return res == null ? backup : res;
	}

	public static void setIntegerFormat(String pattern) {
		if (pattern != null && !pattern.isEmpty()) {
			NF_INTEGER = new DecimalFormat(pattern);
		} else {
			NF_INTEGER = NumberFormat.getIntegerInstance(locale);
		}
	}

	public static void setDecimalFormat(String pattern) {
		if (pattern != null && !pattern.isEmpty()) {
			NF_DECIMAL = new DecimalFormat(pattern);
		} else {
			NF_DECIMAL = NumberFormat.getNumberInstance(locale);
		}
	}

	public static void setCurrencyFormat(String pattern) {
		if (pattern != null && !pattern.isEmpty()) {
			NF_CURRENCY = new DecimalFormat(pattern);
		} else {
			NF_CURRENCY = NumberFormat.getCurrencyInstance(locale);
		}
	}

	public static void setPercentFormat(String pattern) {
		if (pattern != null && !pattern.isEmpty()) {
			NF_PERCENT = new DecimalFormat(pattern);
		} else {
			NF_PERCENT = NumberFormat.getPercentInstance(locale);
		}
	}

	public static void setTimeFormat(String pattern) {
		if (pattern != null && !pattern.isEmpty()) {
			DF_TIME = new SimpleDateFormat(pattern);
		} else {
			DF_TIME = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
		}
	}

	public static void setDateFormat(String pattern) {
		if (pattern != null && !pattern.isEmpty()) {
			DF_DATE = new SimpleDateFormat(pattern);
		} else {
			DF_DATE = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		}
	}

	public static void setDateTimeFormat(String pattern) {
		if (pattern != null && !pattern.isEmpty()) {
			DF_DATETIME = new SimpleDateFormat(pattern);
		} else {
			DF_DATETIME = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
		}
	}

	public static Locale getLocale() {
		return locale;
	}

	private static abstract class NumberFormatImp<T> extends Format<T> {
		
		@Override
		protected String getFormated(T value) {
			return getFormat().format(value);
		}

		protected abstract T getValue(Number number);

		protected abstract NumberFormat getFormat();

		@Override
		protected T getParsed(String str) throws AppException {
			ParsePosition pp = new ParsePosition(0);
			Number res = getFormat().parse(str, pp);
			if (str.length() != pp.getIndex()) {
				throw new AppException("No se puede convertir a número.");
			}
			return getValue(res);
		}
	}

	private static final class IntegerFormatImp extends NumberFormatImp<Integer> {

		@Override
		protected NumberFormat getFormat() {
			return NF_INTEGER;
		}

		@Override
		protected Integer getValue(Number number) {
			return number.intValue();
		}
	}

	private static final class DecimalFormatImp extends NumberFormatImp<Double> {

		@Override
		protected NumberFormat getFormat() {
			return NF_DECIMAL;
		}

		@Override
		protected Double getValue(Number number) {
			return number.doubleValue();
		}
	}

	private static final class CurrencyFormatImp extends NumberFormatImp<Double> {

		@Override
		protected NumberFormat getFormat() {
			return NF_CURRENCY;
		}

		@Override
		protected Double getValue(Number number) {
			return number.doubleValue();
		}

		@Override
		protected Double getParsed(String str) throws AppException {
			try {
				return NF_CURRENCY.parse(str).doubleValue();
			} catch (ParseException ex) {
				return DECIMAL.parse(str);
			}
		}
	}

	private static final class PercentFormatImp extends Format<Double> {

		@Override
		protected String getFormated(Double value) {
			return NF_PERCENT.format(value);
		}

		@Override
		protected Double getParsed(String str) throws AppException {
			try {
				return NF_PERCENT.parse(str).doubleValue();
			} catch (ParseException ex) {
				try {
					return DECIMAL.parse(str) / 100;
				} catch (AppException eex) {
					throw new AppException("No se puede convertir a porcentaje.", eex);
				}
			}
		}
	}

	private static final class DateFormatImp extends Format<Date> {

		@Override
		protected String getFormated(Date value) {
			return DF_DATE.format(value);
		}

		@Override
		protected Date getParsed(String str) throws AppException {
			try {
				return DF_DATE.parse(str);
			} catch (ParseException pex) {
				throw new AppException("No se puede convertir a fecha.");
			}
		}
	}

	private static final class TimeFormatImp extends Format<Date> {

		@Override
		protected String getFormated(Date value) {
			return DF_TIME.format(value);
		}

		@Override
		protected Date getParsed(String str) throws AppException {
			try {
				return DF_TIME.parse(str);
			} catch (ParseException pex) {
				throw new AppException("No se puede convertir a hora.");
			}
		}
	}

	private static final class DateTimeFormatImp extends Format<Date> {

		@Override
		protected String getFormated(Date value) {
			return DF_DATETIME.format(value);
		}

		@Override
		protected Date getParsed(String str) throws AppException {
			try {
				return DF_TIME.parse(str);
			} catch (ParseException pex) {
				throw new AppException("No se puede convertir a fecha y hora.");

			}
		}
	}

	private static final class ObjectFormatImp extends Format<Object> {

		@Override
		protected String getFormated(Object value) {
			return value != null ? value.toString() : "";
		}

		@Override
		protected Object getParsed(String str) throws AppException {
			return str;
		}
	}
	
	private static final class TimeStampFormatImp extends Format<Date> {

		@Override
		protected String getFormated(Date value) {
			((SimpleDateFormat) DF_CUSTOM_TS).applyPattern(pattern);
			return StringUtils.capitalize(DF_CUSTOM_TS.format(value));
		}

		@Override
		protected Date getParsed(String str) throws AppException {
			try {
				((SimpleDateFormat) DF_CUSTOM_TS).applyPattern(pattern);
				return DF_CUSTOM_TS.parse(str);
			} catch (ParseException pex) {
				throw new AppException("No se puede convertir a fecha.");
			}
		}
	}
}
