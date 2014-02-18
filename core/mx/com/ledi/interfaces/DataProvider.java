package mx.com.ledi.interfaces;

import java.util.List;
import mx.com.ledi.error.AppException;

/**
 *
 * @param <T>
 */
public interface DataProvider<T> {

    public List<T> cargar() throws AppException;
}
