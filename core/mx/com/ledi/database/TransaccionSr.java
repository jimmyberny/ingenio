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
 * @author Superman
 */
public abstract class TransaccionSr {

	private static final Logger log = LoggerFactory.getLogger(TransaccionSr.class);

	private SessionFactory f;

	public TransaccionSr(SessionFactory factory) {
		this.f = factory;
	}

	public abstract void execInTransaction(Session s, Object... params) throws AppException;

	public void exec(Object... params) throws AppException {
		Transaction tx = null;
		Exception err = null;
		try {
			Session s = f.getCurrentSession();
			if (s.getTransaction() != null && s.getTransaction().isActive()) {
				s.getTransaction().rollback();
			}
			tx = s.beginTransaction();
			execInTransaction(s, params);
			tx.commit();
		} catch (AppException aex) {
			err = aex;
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
				throw new AppException("No se pudo completar la transaccion", err);
			}
		}
	}
}
