package com.siem.test;

import com.ingenio.modelo.Usuario;
import java.util.List;
import junit.framework.Assert;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author José Bernardo Gómez-Andrade
 */
public class BernardoTest {
	
	private static final Logger log = LoggerFactory.getLogger(BernardoTest.class);

	/**
	 * Un test de ejemplo.
	 * Ejecutan con Run file o Shift + F6
	 */
	@Test
	public void test() {
		SessionFactory sf = new Configuration().configure().buildSessionFactory(); // Cargar la configuracion de hibernate.cfg.xml
		Assert.assertNotNull(sf); // El factory de las sesiones si se creo

		Session cs = sf.getCurrentSession();
		Assert.assertNotNull(cs); // Que la session actual no es null

		cs.beginTransaction(); // Abrir una transaccion
		Criteria cr = cs.createCriteria(Usuario.class); // Hacer un criteria sobre los objetos de la clase usuario.
		List<Usuario> usuarios = cr.list(); // Hacer un select * from usuario, si lo quieren ver de ese modo
		Assert.assertNotNull(usuarios); // Probar si hay resultado, al menos una lista vacía.

		for (Usuario u : usuarios) { // Iterar sobre los resultados
			log.info("{} -> {}", u.getId(), u.getNombre());
		}
		cs.getTransaction().commit();
	}
}
