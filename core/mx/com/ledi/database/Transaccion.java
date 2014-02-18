package mx.com.ledi.database;

import mx.com.ledi.error.AppException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Pikachu
 */
public abstract class Transaccion {

	private static final Logger log = LoggerFactory.getLogger(Transaccion.class);
	//
	private SessionFactory f;

	public Transaccion(SessionFactory factory) {
		this.f = factory;
	}

	public abstract Object execInTransaction(Session s, Object... params) throws AppException;

	public Object exec(Object... params) throws AppException {
		Transaction tx = null;
		Exception err = null;
		Object res = null;
		try {
			Session s = f.getCurrentSession();
			if (s.getTransaction() != null && s.getTransaction().isActive()) {
				s.getTransaction().rollback();
			}
			tx = s.beginTransaction();
			res = execInTransaction(s, params);
			tx.commit();
		} catch (HibernateException ex) {
			err = ex;
		} catch (Exception ex) {
			err = ex;
		} finally {
			if (err != null) {
				log.error(err.getMessage(), err);
				if (tx != null) {
					tx.rollback();
				}
				throw new AppException("No se pudo ejecutar la instruccion", err);
			}
		}
		return res;
	}
}
