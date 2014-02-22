package com.siem.test;

import com.ingenio.modelo.Supervisor;
import java.util.List;
import junit.framework.Assert;
import org.hibernate.Query;
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
     * Un test de ejemplo. Ejecutan con Run file o Shift + F6
     */
    @Test
    public void test() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory(); // Cargar la configuracion de hibernate.cfg.xml
        Assert.assertNotNull(sf); // El factory de las sesiones si se creo

        Session cs = sf.getCurrentSession();
        Assert.assertNotNull(cs); // Que la session actual no es null

        cs.beginTransaction(); // Abrir una transaccion
        List list = cs.createQuery("from Zona as z where z.id not in (select s.zona from Supervisor as s)").list();
        for (Object obj : list) {
            log.info("{}", obj);
        }
        cs.getTransaction().commit();
    }
}
