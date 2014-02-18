package mx.com.ledi.app;

import java.util.HashMap;
import mx.com.ledi.interfaces.gui.AplicacionBean;

/**
 *
 * @author jimmy
 */
public class AppMapCache extends AppCache<String, AplicacionBean> {

	private final HashMap<String, AplicacionBean> cache;

	public AppMapCache() {
		cache = new HashMap<String, AplicacionBean>(10);
	}

	@Override
	public void put(String key, AplicacionBean item) {
		cache.put(key, item);
	}

	@Override
	public AplicacionBean get(String key) {
		return cache.get(key);
	}

	@Override
	public void clear() {
		cache.clear();
	}
}
