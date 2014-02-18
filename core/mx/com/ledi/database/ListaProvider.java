package mx.com.ledi.database;

import java.util.List;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.DataProvider;
import mx.com.ledi.interfaces.gui.Aplicacion;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 */
public abstract class ListaProvider<T> implements DataProvider<T> {

	private final Transaccion listar;

	public ListaProvider(Aplicacion app) {
		listar = new Transaccion(app.getFactory()) {

			@Override
			public Object execInTransaction(Session s, Object... params) throws AppException {
				Criteria cr = s.createCriteria(getClase());
				decorate(cr);
				return cr.list();
			}
		};
	}

	protected void decorate(Criteria cr) {
		// 
	}

	@Override
	public List<T> cargar() throws AppException {
		return (List<T>) listar.exec();
	}

	public abstract Class getClase();

}
