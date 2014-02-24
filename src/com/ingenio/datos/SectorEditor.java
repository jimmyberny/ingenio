package com.ingenio.datos;

import com.ingenio.datos.dialogos.ZonaBuscador;
import com.ingenio.modelo.Sector;
import com.ingenio.modelo.Zona;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.Editor;
import mx.com.ledi.interfaces.gui.Aplicacion;
import mx.com.ledi.interfaces.listeners.MonitorListener;
import mx.com.ledi.util.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class SectorEditor extends Editor<Sector> {

    private static final Logger log = LoggerFactory.getLogger(SectorEditor.class);

    private Aplicacion app;
    private Sector sector;
    //
    private Zona zona;

    public SectorEditor(Aplicacion app, MonitorListener monitor) {
        initComponents();

        this.app = app;
        monitor.listenTo(jtfZona);
        monitor.listenTo(jtfClave);
    }

    @Override
    public void initNoItem() {
        sector = null;
        limpiar();
        setActivo(false);
    }

    @Override
    public void initItem() {
        sector = new Sector();
        limpiar();
        setActivo(true);
    }

    @Override
    public void mostrarItem(Sector item) throws AppException {
        sector = item;
        mostrar();
        setActivo(true);
    }

    @Override
    public void borrarItem(Sector item) throws AppException {
        sector = item;
        mostrar();
        setActivo(false);
    }

    public void mostrar() {
        jtfClave.setText(sector.getClave());
        zona = sector.getZona();
        jtfZona.setText(Format.OBJECT.format(zona));
    }

    @Override
    public Sector getItem() throws AppException {
        sector.setClave(jtfClave.getText());
        sector.setZona(zona); // replace zona
        return sector;
    }

    @Override
    public void setActivo(boolean activo) {
        jtfClave.setEnabled(activo);
        jbSetZona.setEnabled(activo);
    }

    @Override
    public void actualizar() {
        // No hace nada
    }

    @Override
    public void limpiar() {
        jtfClave.setText(null);
        jtfZona.setText(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfClave = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtfZona = new javax.swing.JTextField();
        jbSetZona = new javax.swing.JButton();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Clave");
        jLabel1.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfClave.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Zona");
        jLabel2.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfZona.setEditable(false);
        jtfZona.setPreferredSize(new java.awt.Dimension(200, 30));

        jbSetZona.setIcon(mx.com.ledi.gui.IconFactory.SET.getFindIcon());
        jbSetZona.setPreferredSize(new java.awt.Dimension(40, 30));
        jbSetZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSetZonaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSetZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(330, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbSetZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(291, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbSetZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSetZonaActionPerformed
        ZonaBuscador zb = ZonaBuscador.mostrar(this, app);
        if (zb.isAceptar()) {
            zona = zb.getItem();
            jtfZona.setText(Format.OBJECT.format(zona));
        }
    }//GEN-LAST:event_jbSetZonaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton jbSetZona;
    private javax.swing.JTextField jtfClave;
    private javax.swing.JTextField jtfZona;
    // End of variables declaration//GEN-END:variables

}
