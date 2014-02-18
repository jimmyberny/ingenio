package mx.com.ledi.app;

/**
 *
 * @author jimmy
 * @param <S>
 * @param <T>
 */
public abstract class AppCache<S, T> {

	public abstract void put(S key, T item);

	public abstract T get(S key);
	
	public abstract void clear();
}
