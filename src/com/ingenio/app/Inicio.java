package com.ingenio.app;

import javax.swing.SwingUtilities;
import mx.com.ledi.config.HibernateConfigUtil;
import org.hibernate.SessionFactory;

/**
 *
 * @author César
 */
public class Inicio {

    public Inicio() {

    }

    public static void main(String[] args) {
        AgendaConf conf = new AgendaConf("agenda.cfg");
        HibernateConfigUtil adapter = new HibernateConfigUtil(conf);
        adapter.addPassword("app.agenda.contra");
        adapter.addURL("app.agenda.url");
        adapter.addUsuario("app.agenda.usuario");

        // Hibernate 
        final SessionFactory sf = adapter.getSessionFactory();

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Agenda frame = new Agenda(sf);
                frame.setVisible(true);
            }
        });
    }
}
