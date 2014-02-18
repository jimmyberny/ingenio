package com.ingenio.app;

import mx.com.ledi.app.AppMapCache;
import mx.com.ledi.error.AppException;
import mx.com.ledi.gui.menu.MenuUtil;
import mx.com.ledi.interfaces.gui.AplicacionBean;
import mx.com.ledi.interfaces.gui.AplicacionView;
import mx.com.ledi.msg.AppMensaje;
import mx.com.ledi.util.CardNavigator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class Agenda extends AplicacionFrame {

	private static final Logger log = LoggerFactory.getLogger(Agenda.class);
	private SessionFactory factory;
	private MenuUtil menu;
	private CardNavigator nav;
	private AppMapCache cache;
	private AplicacionView vistaActual;

	public Agenda(SessionFactory factory) {
		initComponents();

		this.factory = factory;
		nav = new CardNavigator(jpContenido);

		cache = new AppMapCache();
		init();
	}

	private void init() {
		// No permitir cerrar la venta sin confirmación
		this.addWindowListener(new AppCerrar());

		// Intentar conectar, si no salir!
		try {
			Session s = factory.getCurrentSession();
			s.beginTransaction(); // Iniciar la transaccion
			s.getTransaction().commit(); // Confirmar la transaccion
		} catch (HibernateException hex) {
			log.error(hex.getMessage(), hex);
			new AppMensaje("La configuración no es correcta.", hex).mostrar(this);
			System.exit(1); // Salir!
		}

		// Continua con el cargado de la aplicacion
		log.info("La base de datos es correcto.");

		menu = new MenuUtil();
		menu.menu("Datos");
		menu.addItem("title.usuarios", AgendaClaves.USUARIOS);
		menu.closeSub();
		
		menu.menu("Acerca de");
		menu.closeSub();

		tmMenu.init(this);
		tmMenu.updateMenu();

	}

	@Override
	public MenuUtil getMenu() {
		return menu;
	}

	@Override
	public void mostrarTarea(String tarea) {
		if (vistaActual != null && !vistaActual.hideView()) {
			return;
		}

		AplicacionView vista = (AplicacionView) cache.get(tarea);
		if (vistaActual != null && vistaActual == vista) {
			return; // No hacer nada si la vista es la actual
		}
		if (vista == null) {
			try {
				vista = (AplicacionView) Class.forName(tarea).newInstance();
				vista.init(this); // Pass data
				cache.put(tarea, vista); // Cacching data
				jpContenido.add(vista.getComponent(), tarea);
			} catch (AppException ex) {
				log.error(ex.getMessage(), ex);
			} catch (ClassNotFoundException ex) {
				log.error(ex.getMessage(), ex);
			} catch (InstantiationException ex) {
				log.error(ex.getMessage(), ex);
			} catch (IllegalAccessException ex) {
				log.error(ex.getMessage(), ex);
			}
		}

		if (vista != null) {
			try {
				vista.showView();
				// El titlo
				nav.moveTo(tarea);
			} catch (AppException aex) {
				new AppMensaje("message.viewerror", aex).mostrar(this);
			}
		}
	}

	@Override
	public AplicacionBean getBean(String className) {
		AplicacionBean bean = cache.get(className);
		if (bean == null) {
			try {
				bean = (AplicacionBean) Class.forName(className).newInstance();
				bean.init(this);
				cache.put(className, bean);
			} catch (AppException ex) {
				log.error(ex.getMessage(), ex);
			} catch (ClassNotFoundException ex) {
				log.error(ex.getMessage(), ex);
			} catch (InstantiationException ex) {
				log.error(ex.getMessage(), ex);
			} catch (IllegalAccessException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return bean;
	}

	@Override
	public SessionFactory getFactory() {
		return factory;
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tmMenu = new mx.com.ledi.gui.menu.TreeMenu();
        jpContenido = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Agenda SIEM");

        tmMenu.setPreferredSize(new java.awt.Dimension(220, 563));
        getContentPane().add(tmMenu, java.awt.BorderLayout.LINE_START);

        jpContenido.setLayout(new java.awt.CardLayout());
        getContentPane().add(jpContenido, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jpContenido;
    private mx.com.ledi.gui.menu.TreeMenu tmMenu;
    // End of variables declaration//GEN-END:variables
}
