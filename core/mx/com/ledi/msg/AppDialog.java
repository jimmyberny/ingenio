package mx.com.ledi.msg;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import mx.com.ledi.interfaces.gui.Cancelable;
import mx.com.ledi.util.DialogKeyEventDistpatcher;

/**
 * Este diálogo mostrará los errores de la aplicación.
 * <p>
 * Ok.
 *
 * @author Batman
 */
public class AppDialog extends javax.swing.JDialog implements Cancelable {

	public AppDialog(Frame parent, boolean modal) {
		super(parent, modal);
	}

	public AppDialog(Dialog parent, boolean modal) {
		super(parent, modal);
	}

	public static void mostrar(Component padre, AppMensaje mensaje) {
		AppDialog de;
		Window mw = SwingUtilities.getWindowAncestor(padre);
		// Window mw = GuiUtil.getWindow(padre);
		if (mw instanceof Frame) {
			de = new AppDialog((Frame) mw, true);
		} else {
			de = new AppDialog((Dialog) mw, true);
		}
		DialogKeyEventDistpatcher.dispatch(de);
		de.initComponents();
		de.setLocationRelativeTo(mw);
		de.setMensaje(mensaje);
		de.setVisible(true);
	}

	@Override
	public void cancel() {
		this.dispose();
	}

	private void setMensaje(AppMensaje mensaje) {
		jspDetalles.setVisible(false);
		jbDetalles.setVisible(false);

		jtpMensaje.setText(mensaje.getMensaje());
		jtpMensaje.setCaretPosition(0);

		switch (mensaje.getTipo()) {
			case JOptionPane.ERROR_MESSAGE:
				jlIcono.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
				break;
			case JOptionPane.INFORMATION_MESSAGE:
				jlIcono.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
				break;
			case JOptionPane.WARNING_MESSAGE:
				jlIcono.setIcon(UIManager.getIcon("OptionPane.warningIcon"));
				break;
		}

		if (mensaje.getCausa() != null) {
			// Hacer el boton de detalles visible
			jbDetalles.setVisible(true);

			StringBuilder sb = new StringBuilder(255);
			errorStack(sb, mensaje.getCausa());
			jtpDetalles.setText(sb.toString());

			jtpDetalles.setCaretPosition(0);
		}
		jbCerrar.requestFocus();
	}

	private void errorStack(StringBuilder sb, Object obj) {
		if (obj instanceof Throwable) {
			Throwable t = (Throwable) obj;
			sb.append(t.getClass().getCanonicalName()).append("\n");
			sb.append(t.getMessage()).append("\n\n");
			if (t.getCause() != null) {
				errorStack(sb, t.getCause());
			}
			sb.append("\n");
		} else if (obj instanceof String) {
			sb.append("\n").append(obj);
		}
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jspDetalles = new javax.swing.JScrollPane();
        jtpDetalles = new javax.swing.JTextPane();
        jlIcono = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtpMensaje = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        jbDetalles = new javax.swing.JButton();
        jbCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mensaje de la aplicación");

        jspDetalles.setViewportView(jtpDetalles);

        jlIcono.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jlIcono.setPreferredSize(new java.awt.Dimension(50, 50));

        jScrollPane2.setViewportView(jtpMensaje);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspDetalles)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlIcono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlIcono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspDetalles, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(542, 40));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 2));

        jbDetalles.setText("Ver detalles");
        jbDetalles.setPreferredSize(new java.awt.Dimension(120, 36));
        jbDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDetallesActionPerformed(evt);
            }
        });
        jPanel2.add(jbDetalles);

        jbCerrar.setText("Cerrar");
        jbCerrar.setPreferredSize(new java.awt.Dimension(120, 36));
        jbCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCerrarActionPerformed(evt);
            }
        });
        jPanel2.add(jbCerrar);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDetallesActionPerformed
		jspDetalles.setVisible(!jspDetalles.isVisible());
		this.setSize(getSize().width, getSize().height + (jspDetalles.isVisible() ? 200 : -200));
		this.validate(); // Para que se actualize la vista.
    }//GEN-LAST:event_jbDetallesActionPerformed

    private void jbCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCerrarActionPerformed
		cancel(); // Jeje
    }//GEN-LAST:event_jbCerrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbCerrar;
    private javax.swing.JButton jbDetalles;
    private javax.swing.JLabel jlIcono;
    private javax.swing.JScrollPane jspDetalles;
    private javax.swing.JTextPane jtpDetalles;
    private javax.swing.JTextPane jtpMensaje;
    // End of variables declaration//GEN-END:variables

}
