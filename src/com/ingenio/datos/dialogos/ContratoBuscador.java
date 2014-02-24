package com.ingenio.datos.dialogos;

import com.ingenio.app.AgendaClaves;
import com.ingenio.modelo.Contrato;
import com.ingenio.modelo.auxiliar.ListaSimple;
import com.ingenio.origenes.OrigenGeneral;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.SwingUtilities;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.Buscador;
import mx.com.ledi.interfaces.gui.Aplicacion;
import mx.com.ledi.msg.AppMensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class ContratoBuscador extends Buscador<Contrato> {

    private static final Logger log = LoggerFactory.getLogger(ContratoBuscador.class);

    private OrigenGeneral oGeneral;

    public ContratoBuscador(Frame parent, boolean modal) {
        super(parent, modal);
    }

    public ContratoBuscador(Dialog parent, boolean modal) {
        super(parent, modal);
    }

    public static ContratoBuscador mostrar(Component padre, Aplicacion app) {
        ContratoBuscador ab;
        Window mw = SwingUtilities.getWindowAncestor(padre);
        if (mw instanceof Frame) {
            ab = new ContratoBuscador((Frame) mw, true);
        } else {
            ab = new ContratoBuscador((Dialog) mw, true);
        }
        ab.initComponents();
        ab.init(app);
        ab.setLocationRelativeTo(mw);
        ab.setVisible(true);
        return ab;
    }

    private void init(Aplicacion app) {
        oGeneral = (OrigenGeneral) app.getBean(AgendaClaves.ORI_GENERAL);

        // Settear
        modelo = new ListaSimple<>();
        jlContratos.setModel(modelo);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jbBuscar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jbCancelar = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();
        jspItems = new javax.swing.JScrollPane();
        jlContratos = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Contratos");
        setPreferredSize(new java.awt.Dimension(480, 320));

        jPanel3.setPreferredSize(new java.awt.Dimension(554, 34));

        jbBuscar.setIcon(mx.com.ledi.gui.IconFactory.SET.getExecuteIcon());
        jbBuscar.setText("Buscar");
        jbBuscar.setPreferredSize(new java.awt.Dimension(120, 30));
        jbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Nombre");
        jLabel1.setPreferredSize(new java.awt.Dimension(120, 26));

        jtfNombre.setPreferredSize(new java.awt.Dimension(160, 30));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel2.setPreferredSize(new java.awt.Dimension(542, 34));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 2));

        jbCancelar.setIcon(mx.com.ledi.gui.IconFactory.SET.getCancelIcon());
        jbCancelar.setText("Cancelar");
        jbCancelar.setPreferredSize(new java.awt.Dimension(120, 30));
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(jbCancelar);

        jbAceptar.setIcon(mx.com.ledi.gui.IconFactory.SET.getOkIcon()
        );
        jbAceptar.setText("Aceptar");
        jbAceptar.setPreferredSize(new java.awt.Dimension(120, 30));
        jbAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAceptarActionPerformed(evt);
            }
        });
        jPanel2.add(jbAceptar);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jlContratos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jspItems.setViewportView(jlContratos);

        getContentPane().add(jspItems, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed
        try {
            modelo.setList(oGeneral.listarContratos(jtfNombre.getText()));
        } catch (AppException ex) {
            new AppMensaje("No se pudo consultar los contratos", ex).mostrar(this);
        }
    }//GEN-LAST:event_jbBuscarActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        cancel();
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jbAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAceptarActionPerformed
        try {
            aceptar(jlContratos.getSelectedIndex());
        } catch (AppException ex) {
            new AppMensaje(ex).mostrar(this);
        }
    }//GEN-LAST:event_jbAceptarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JList jlContratos;
    private javax.swing.JScrollPane jspItems;
    private javax.swing.JTextField jtfNombre;
    // End of variables declaration//GEN-END:variables

}
