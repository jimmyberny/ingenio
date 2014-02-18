package mx.com.ledi.gui.menu;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import mx.com.ledi.interfaces.gui.Aplicacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jimmy
 */
public class TreeMenu extends javax.swing.JPanel {

	public static Logger log = LoggerFactory.getLogger(TreeMenu.class);
	//
	private final TreeMenuModel model;
	private Aplicacion app;
	private MenuUtil defMenu;
	//
	private boolean adjust;

	public TreeMenu() {
		initComponents();

		model = new TreeMenuModel(null);
		jtMenu.setModel(model);
		jtMenu.addTreeSelectionListener(new ActionMenuItem());
		jtMenu.setRowHeight(28);
		jtMenu.setFont(jtMenu.getFont().deriveFont(jtMenu.getFont().getSize() * 2));
	}

	public void init(Aplicacion app) {
		this.app = app;
		defMenu = app.getMenu();
	}

	public void updateMenu() {
		adjust = true;
		log.info("Ajustando menu");
		model.setRoot(defMenu.getRoot());
		model.nodeStructureChanged((MenuNode) model.getRoot());
		log.info("Terminando de ajustar el menu");
		adjust = false;
	}

	private class ActionMenuItem implements TreeSelectionListener {

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			MenuNode mn = (MenuNode) e.getPath().getLastPathComponent();
			if (mn.isLeaf()) {
				if (mn.isAction()) {
					try {
						Method m = Aplicacion.class.getMethod(mn.getClassname());
						if (m != null) {
							m.invoke(app);
						}
					} catch (NoSuchMethodException nsmex) {
					} catch (IllegalAccessException iaex) {
					} catch (IllegalArgumentException iarex) {
					} catch (InvocationTargetException itex) {
					} catch (SecurityException sex) {
					}
				} else if (mn.getClassname() != null) {
					log.info("Mostrando: {}", mn.getClassname());
					app.mostrarTarea(mn.getClassname());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jspMenu = new javax.swing.JScrollPane();
        jtMenu = new javax.swing.JTree();

        setLayout(new java.awt.BorderLayout());

        jtMenu.setRootVisible(false);
        jtMenu.setShowsRootHandles(true);
        jspMenu.setViewportView(jtMenu);

        add(jspMenu, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jspMenu;
    private javax.swing.JTree jtMenu;
    // End of variables declaration//GEN-END:variables
}
