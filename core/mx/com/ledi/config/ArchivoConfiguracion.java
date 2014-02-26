package mx.com.ledi.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import mx.com.ledi.error.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tony Stark
 */
public abstract class ArchivoConfiguracion {

    public static final Logger log = LoggerFactory.getLogger(ArchivoConfiguracion.class);

    private final File archivo;
    private final Properties props;

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
            try {
                guardar();
            } catch (AppException ex) {
                // Aqui se ignora esta waring
                log.error(ex.getMessage(), ex);
            }
        }
    }

    public abstract void initPorDefecto(Properties props);

    public void guardar() throws AppException {
        try {
            props.store(new FileWriter(archivo), "Configuracion");
        } catch (IOException ex) {
            throw new AppException("Ha ocurrido un error al guardar la configuración.", ex);
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
