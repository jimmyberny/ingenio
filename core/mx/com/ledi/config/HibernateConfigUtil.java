package mx.com.ledi.config;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class HibernateConfigUtil {

	private ArchivoConfiguracion base;
	private Properties keys;

	private byte urlComp = 0;

	public HibernateConfigUtil(ArchivoConfiguracion base) {
		this.base = base;
		keys = new Properties();
	}

	public void add(String hbKey, String cfKey) {
		keys.put(hbKey, cfKey);
	}

	public void addUsuario(String usrKey) {
		add("hibernate.connection.username", usrKey);
	}

	public void addPassword(String passKey) {
		add("hibernate.connection.password", passKey);
	}

	public void addURL(String urlKey) {
		add("hibernate.connection.url", urlKey);
	}

	public SessionFactory getSessionFactory() {
		// Configuracion por defecto de hibernate
		Configuration cfg = new Configuration().configure();

		// Recorrer las claves que tienen un equivalente
		Iterator<Map.Entry<Object, Object>> it = keys.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Object, Object> next = it.next();
			String hbk = next.getKey().toString(),
					cck = next.getValue().toString();
			if (base.getProperties().contains(cck)) {
				// Si la clave tiene valor en el archivo de configuracion, sobrecargarla en la cfg de hibernate
				cfg.setProperty(hbk, base.getProperty(cck));
			}
		}
		cfg.buildSettings();
		return cfg.buildSessionFactory();
	}

}
