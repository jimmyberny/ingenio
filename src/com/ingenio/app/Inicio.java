package com.ingenio.app;

import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import mx.com.ledi.config.HibernateConfigUtil;
import mx.com.ledi.msg.AppMensaje;
import org.hibernate.SessionFactory;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;

/**
 *
 * @author César
 */
public class Inicio {

    public Inicio() {

    }

    public static void main(String[] args) {
        final AgendaConf conf = new AgendaConf("agenda.cfg");
        HibernateConfigUtil adapter = new HibernateConfigUtil(conf);

        adapter.addPassword("app.agenda.contra");
        adapter.addURL("app.agenda.url");
        adapter.addUsuario("app.agenda.usuario");

        // Hibernate 
        final SessionFactory sf = adapter.getSessionFactory();

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    String claf = conf.getProperty(AgendaClaves.CFG_KEY_LAF,
                            UIManager.getSystemLookAndFeelClassName());

                    // Setting look and feel
                    Object laf = Class.forName(claf).newInstance();
                    if (laf instanceof LookAndFeel) {
                        UIManager.setLookAndFeel((LookAndFeel) laf);
                    } else if (laf instanceof SubstanceSkin) {
                        SubstanceLookAndFeel.setSkin((SubstanceSkin) laf);
                    }

                    Agenda frame = new Agenda(conf, sf);
                    frame.setVisible(true);
                } catch (InstantiationException |
                        IllegalAccessException |
                        UnsupportedLookAndFeelException |
                        ClassNotFoundException ex) {
                    new AppMensaje("No se pudo iniciar la aplicación.", ex).mostrar(null);
                }
            }
        });
    }
}
