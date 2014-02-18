package mx.com.ledi.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tony Stark
 */
public abstract class ArchivoConfiguracion {

	public static final Logger log = LoggerFactory.getLogger(ArchivoConfiguracion.class);

	private File archivo;
	private Properties props;

	public ArchivoConfiguracion(String nombreArchivo) {
		archivo = new File(String.format("%s%s%s",
				System.getProperty("user.home"),
				System.getProperty("file.separator"),
				nombreArchivo));

		props = new Properties();
		initPorDefecto(props);
		try {
			props.load(new FileReader(archivo));
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		} finally {
			guardar();
		}
	}

	public abstract void initPorDefecto(Properties props);

	public void guardar() {
		try {
			props.store(new FileWriter(archivo), "Configuracion");
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	public Properties getProperties() {
		return props;
	}

	public String getProperty(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

	public String getProperty(String key) {
		return props.getProperty(key);
	}
}
