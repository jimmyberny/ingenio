package mx.com.ledi.database;

import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.gui.Aplicacion;
import org.hibernate.Session;

/**
 *
 * @author Enrique
 * @param <T>
 */
public class Saver<T> {

	private Transaccion registrar;
	private Transaccion modificar;
	private Transaccion borrar;

	public Saver(Aplicacion app) {
		registrar = new Transaccion(app.getFactory()) {

			@Override
			public Object execInTransaction(Session s, Object... params) throws AppException {
				s.save(params[0]);
				return true;
			}
		};

		modificar = new Transaccion(app.getFactory()) {

			@Override
			public Object execInTransaction(Session s, Object... params) throws AppException {
				s.update(params[0]);
				return true;
			}
		};

		borrar = new Transaccion(app.getFactory()) {

			@Override
			public Object execInTransaction(Session s, Object... params) throws AppException {
				s.delete(params[0]);
				return true;
			}
		};
	}

	public boolean registrar(T item) throws AppException {
		return (Boolean) registrar.exec(item);
	}

	public boolean modificar(T item) throws AppException {
		return (Boolean) modificar.exec(item);
	}

	public boolean borrar(T item) throws AppException {
		return (Boolean) borrar.exec(item);
	}

	public boolean puedeRegistrar() {
		return registrar != null;
	}

	public boolean puedeModificar() {
		return modificar != null;
	}

	public boolean puedeBorrar() {
		return borrar != null;
	}
}
