package com.ingenio.app.vistas;

import com.ingenio.app.AgendaClaves;
import com.ingenio.datos.dialogos.ZafraBuscador;
import com.ingenio.modelo.Configuracion;
import com.ingenio.modelo.Zafra;
import com.ingenio.origenes.OrigenGeneral;
import javax.swing.JComponent;
import javax.swing.JPanel;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.gui.Aplicacion;
import mx.com.ledi.interfaces.gui.AplicacionView;
import mx.com.ledi.interfaces.listeners.MonitorListener;
import mx.com.ledi.msg.AppMensaje;
import mx.com.ledi.util.DialogoUtil;
import mx.com.ledi.util.Format;

/**
 *
 * @author None
 */
public class ConfiguracionVista extends JPanel implements AplicacionView {

    private Aplicacion app;
    private OrigenGeneral oGeneral;
    //
    private MonitorListener monitor;
    private Configuracion configuracion;

    public ConfiguracionVista() {
        initComponents();

        monitor = new MonitorListener();
    }

    @Override
    public void init(Aplicacion aplicacion) throws AppException {
        this.app = aplicacion;
        oGeneral = (OrigenGeneral) app.getBean(AgendaClaves.ORI_GENERAL);

        monitor.listenTo(jtfZafraActual);
    }

    @Override
    public void showView() throws AppException {
        configuracion = oGeneral.getConfiguracion();
        if (configuracion == null) {
            // Nueva configuración
            configuracion = new Configuracion();
        }
        jtfZafraActual.setText(Format.OBJECT.format(configuracion.getZafra()));
        
        monitor.setDirty(false);
    }

    @Override
    public boolean hideView() {
        if (monitor.isDirty()) {
            monitor.setDirty(DialogoUtil.yes(this, "¿Desea descartar los cambios y continuar?"));
        }
        return !monitor.isDirty();
    }

    @Override
    public String getTitle() {
        return "Configuración";
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jbSetZafra = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jtfZafraActual = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jbGuardarConfiguracion = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jbSetZafra.setIcon(mx.com.ledi.gui.IconFactory.SET.getFindIcon());
        jbSetZafra.setPreferredSize(new java.awt.Dimension(40, 30));
        jbSetZafra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSetZafraActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Zafra actual");
        jLabel1.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfZafraActual.setPreferredSize(new java.awt.Dimension(160, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfZafraActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbSetZafra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfZafraActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbSetZafra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(436, 34));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 2));

        jbGuardarConfiguracion.setIcon(mx.com.ledi.gui.IconFactory.SET.getSaveIcon());
        jbGuardarConfiguracion.setText("Guardar");
        jbGuardarConfiguracion.setPreferredSize(new java.awt.Dimension(120, 30));
        jbGuardarConfiguracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarConfiguracionActionPerformed(evt);
            }
        });
        jPanel2.add(jbGuardarConfiguracion);

        add(jPanel2, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jbSetZafraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSetZafraActionPerformed
        ZafraBuscador zb = ZafraBuscador.mostrar(this, app);
        if (zb.isAceptar()) {
            Zafra zafra = zb.getItem();
            
            configuracion.setZafra(zafra);
            jtfZafraActual.setText(Format.OBJECT.format(zafra));
        }
    }//GEN-LAST:event_jbSetZafraActionPerformed

    private void jbGuardarConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarConfiguracionActionPerformed
        try {
            oGeneral.guardarConfiguracion(configuracion);
            new AppMensaje("La configuración ha sido guardada exitosamente.").mostrar(this);
        } catch (AppException ex) {
            new AppMensaje("No se pudo guardar la configuración.", ex).mostrar(this);
        }
    }//GEN-LAST:event_jbGuardarConfiguracionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jbGuardarConfiguracion;
    private javax.swing.JButton jbSetZafra;
    private javax.swing.JTextField jtfZafraActual;
    // End of variables declaration//GEN-END:variables
}
