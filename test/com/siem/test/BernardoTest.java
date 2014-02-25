package com.siem.test;

import com.ingenio.modelo.ActividadSemana;
import java.util.List;
import junit.framework.Assert;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author None
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
        List<ActividadSemana> ls = cs.createCriteria(ActividadSemana.class).list();
        for (ActividadSemana as : ls) {
            log.info("{} {} {} {}", as.getNombreActividad(), as.getNombreZafra(), as.getNombreCiclo(), as.getNombreZona());
        }
        cs.getTransaction().commit();
    }
}
