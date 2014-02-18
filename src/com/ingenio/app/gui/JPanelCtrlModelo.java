package com.ingenio.app.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mx.com.ledi.database.NavegadorDatos;
import mx.com.ledi.error.AppException;
import mx.com.ledi.msg.AppMensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class JPanelCtrlModelo extends javax.swing.JPanel implements ActionListener {

	private final static Logger log = LoggerFactory.getLogger(JPanelCtrlModelo.class);
	private final NavegadorDatos nav;

	public JPanelCtrlModelo(NavegadorDatos nav) {
		initComponents();
		this.nav = nav;

		init(); // Rid off that stupid notification
	}

	private void init() {
		jbNuevo.addActionListener(this);
		jbBorrar.addActionListener(this);
		jbGuardar.addActionListener(this);
		jbDescartar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource().equals(jbNuevo)) {
				nav.registrar();
			} else if (e.getSource().equals(jbBorrar)) {
				nav.borrar();
			} else if (e.getSource().equals(jbGuardar)) {
				nav.guardarCambios();
			} else if (e.getSource().equals(jbDescartar)) {
				nav.descartar();
			}
		} catch (AppException ex) {
			new AppMensaje("Error del modelo", ex).mostrar(this);
		}
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbNuevo = new javax.swing.JButton();
        jbBorrar = new javax.swing.JButton();
        jbGuardar = new javax.swing.JButton();
        jbDescartar = new javax.swing.JButton();

        jbNuevo.setIcon(mx.com.ledi.gui.IconFactory.SET.getSaveIcon());
        jbNuevo.setToolTipText("Nuevo registro");
        jbNuevo.setPreferredSize(new java.awt.Dimension(40, 40));

        jbBorrar.setIcon(mx.com.ledi.gui.IconFactory.SET.getDeleteIcon());
        jbBorrar.setToolTipText("Eliminar registro");
        jbBorrar.setPreferredSize(new java.awt.Dimension(40, 40));

        jbGuardar.setIcon(mx.com.ledi.gui.IconFactory.SET.getSaveIcon());
        jbGuardar.setToolTipText("Guardar cambios");
        jbGuardar.setPreferredSize(new java.awt.Dimension(40, 40));

        jbDescartar.setIcon(mx.com.ledi.gui.IconFactory.SET.getResetIcon());
        jbDescartar.setToolTipText("Descartar los cambios");
        jbDescartar.setPreferredSize(new java.awt.Dimension(40, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jbDescartar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbDescartar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbBorrar;
    private javax.swing.JButton jbDescartar;
    private javax.swing.JButton jbGuardar;
    private javax.swing.JButton jbNuevo;
    // End of variables declaration//GEN-END:variables

}
