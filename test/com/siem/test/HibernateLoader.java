package com.siem.test;

import junit.framework.Assert;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author José Bernardo Goméz-Andrade
 */
public class HibernateLoader {

	public static final Logger log = LoggerFactory.getLogger(HibernateLoader.class);

	public void test() {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Assert.assertNotNull(sf);

		Session cs = sf.getCurrentSession();
		Assert.assertNotNull(cs);
		cs.beginTransaction();
		cs.getTransaction().commit();
	}
}
