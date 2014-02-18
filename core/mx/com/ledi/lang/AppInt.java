package mx.com.ledi.lang;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Obi Wan Kenobi
 */
public class AppInt {

	public static Logger log = LoggerFactory.getLogger(AppInt.class);
	//
	private static ResourceBundle res = ResourceBundle.getBundle("lang", Locale.getDefault());
	private static MessageFormat msg = new MessageFormat("", Locale.getDefault());
	private static final String fmtErr = "** %s **";

	public static String str(String label, Object... params) {
		String i18n = String.format(fmtErr, label);
		if (res.containsKey(label)) {
			i18n = res.getString(label);
			if (params != null && params.length != 0) {
				msg.applyPattern(i18n);
				i18n = msg.format(params);
			}
		} else {
			log.error("Key not found {}", label);
		}
		return i18n;
	}
}
