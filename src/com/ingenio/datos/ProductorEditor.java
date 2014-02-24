package com.ingenio.datos;

import com.ingenio.datos.dialogos.ContratoBuscador;
import com.ingenio.datos.dialogos.OrganizacionBuscador;
import com.ingenio.datos.dialogos.SectorBuscador;
import com.ingenio.modelo.Actividad;
import com.ingenio.modelo.Contrato;
import com.ingenio.modelo.Organizacion;
import com.ingenio.modelo.Productor;
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
public class ProductorEditor extends Editor<Productor> {

    private static final Logger log = LoggerFactory.getLogger(ProductorEditor.class);
    
    private Aplicacion app;
    private Productor productor;
    //
    private Contrato contrato;
    private Organizacion organizacion;
    private Zona zona;
    private Sector sector;

    public ProductorEditor(Aplicacion app, MonitorListener monitor) {
        initComponents();

        this.app = app;
        monitor.listenTo(jtfClave);
        monitor.listenTo(jtfNombre);
        monitor.listenTo(jtfPaterno);
        monitor.listenTo(jtfMaterno);
        monitor.listenTo(jtfNombre);
        monitor.listenTo(jtfOrganizacion);
        monitor.listenTo(jtfContrato);
        monitor.listenTo(jtfZona);
        monitor.listenTo(jtfSector);
    }

    @Override
    public void initNoItem() {
        productor = null;
        limpiar();
        setActivo(false);
    }

    @Override
    public void initItem() {
        productor = new Productor();
        limpiar();
        setActivo(true);
    }

    @Override
    public void mostrarItem(Productor item) throws AppException {
        productor = item;
        mostrar();
        setActivo(true);
    }

    @Override
    public void borrarItem(Productor item) throws AppException {
        productor = item;
        mostrar();
        setActivo(false);
    }

    public void mostrar() {
        jtfClave.setText(productor.getClave());
        jtfNombre.setText(productor.getNombre());
        jtfPaterno.setText(productor.getPaterno());
        jtfMaterno.setText(productor.getMaterno());
        
        contrato = productor.getContrato();
        jtfContrato.setText(Format.OBJECT.format(contrato));
        organizacion = productor.getOrganizacion();
        jtfOrganizacion.setText(Format.OBJECT.format(organizacion));
        zona = productor.getZona();
        jtfZona.setText(Format.OBJECT.format(zona));
        sector = productor.getSector();
        jtfSector.setText(Format.OBJECT.format(sector));
    }

    @Override
    public Productor getItem() throws AppException {
        productor.setClave(jtfClave.getText());
        productor.setNombre(jtfNombre.getText());
        productor.setPaterno(jtfPaterno.getText());
        productor.setMaterno(jtfMaterno.getText());
        
        productor.setOrganizacion(organizacion);
        productor.setContrato(contrato);
        productor.setZona(zona);
        productor.setSector(sector);
        return productor;
    }

    @Override
    public void setActivo(boolean activo) {
        jtfClave.setEnabled(activo);
        jtfNombre.setEnabled(activo);
        jtfPaterno.setEnabled(activo);
        jtfMaterno.setEnabled(activo);
        // jtfOrganizacion.setEnabled(activo);
        // jtfContrato.setEnabled(activo);
        // jtfZona.setEnabled(activo);
        // jtfSector.setEnabled(activo);
        
        jbSetContrato.setEnabled(activo);
        jbSetOrganizacion.setEnabled(activo);
        jbSetSector.setEnabled(activo);
    }

    @Override
    public void actualizar() {
        // No hace nada
    }

    @Override
    public void limpiar() {
        jtfClave.setText(null);
        jtfNombre.setText(null);
        jtfPaterno.setText(null);
        jtfMaterno.setText(null);
        
        jtfOrganizacion.setText(null);
        jtfContrato.setText(null);
        jtfZona.setText(null);
        jtfSector.setText(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtfClave = new javax.swing.JTextField();
        jtfPaterno = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtfMaterno = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtfContrato = new javax.swing.JTextField();
        jtfOrganizacion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtfZona = new javax.swing.JTextField();
        jtfSector = new javax.swing.JTextField();
        jbSetContrato = new javax.swing.JButton();
        jbSetOrganizacion = new javax.swing.JButton();
        jbSetSector = new javax.swing.JButton();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Nombre");
        jLabel1.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfNombre.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Clave");
        jLabel2.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfClave.setPreferredSize(new java.awt.Dimension(200, 30));

        jtfPaterno.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Apellido paterno");
        jLabel3.setPreferredSize(new java.awt.Dimension(140, 30));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Apellido materno");
        jLabel4.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfMaterno.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Contrato");
        jLabel5.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfContrato.setPreferredSize(new java.awt.Dimension(200, 30));

        jtfOrganizacion.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Organización");
        jLabel6.setPreferredSize(new java.awt.Dimension(140, 30));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Zona y sector");
        jLabel7.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfZona.setPreferredSize(new java.awt.Dimension(200, 30));

        jtfSector.setPreferredSize(new java.awt.Dimension(200, 30));

        jbSetContrato.setIcon(mx.com.ledi.gui.IconFactory.SET.getFindIcon());
        jbSetContrato.setPreferredSize(new java.awt.Dimension(40, 30));
        jbSetContrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSetContratoActionPerformed(evt);
            }
        });

        jbSetOrganizacion.setIcon(mx.com.ledi.gui.IconFactory.SET.getFindIcon());
        jbSetOrganizacion.setPreferredSize(new java.awt.Dimension(40, 30));
        jbSetOrganizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSetOrganizacionActionPerformed(evt);
            }
        });

        jbSetSector.setIcon(mx.com.ledi.gui.IconFactory.SET.getFindIcon());
        jbSetSector.setPreferredSize(new java.awt.Dimension(40, 30));
        jbSetSector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSetSectorActionPerformed(evt);
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
                        .addComponent(jtfClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfContrato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSetContrato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfOrganizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSetOrganizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(jtfSector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSetSector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(330, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtfContrato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbSetContrato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtfOrganizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbSetOrganizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfSector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbSetSector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbSetContratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSetContratoActionPerformed
        ContratoBuscador cb = ContratoBuscador.mostrar(this, app);
        if (cb.isAceptar()) {
            contrato = cb.getItem();
            jtfContrato.setText(Format.OBJECT.format(contrato));
        }
    }//GEN-LAST:event_jbSetContratoActionPerformed

    private void jbSetOrganizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSetOrganizacionActionPerformed
        OrganizacionBuscador ob = OrganizacionBuscador.mostrar(this, app);
        if (ob.isAceptar()) {
            organizacion = ob.getItem();
            jtfOrganizacion.setText(Format.OBJECT.format(organizacion));
        }
    }//GEN-LAST:event_jbSetOrganizacionActionPerformed

    private void jbSetSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSetSectorActionPerformed
        SectorBuscador sb = SectorBuscador.mostrar(this, app);
        if (sb.isAceptar()) {
            sector = sb.getItem();
            zona = sector.getZona();
            jtfZona.setText(Format.OBJECT.format(zona));
            jtfSector.setText(Format.OBJECT.format(sector));
        }
    }//GEN-LAST:event_jbSetSectorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JButton jbSetContrato;
    private javax.swing.JButton jbSetOrganizacion;
    private javax.swing.JButton jbSetSector;
    private javax.swing.JTextField jtfClave;
    private javax.swing.JTextField jtfContrato;
    private javax.swing.JTextField jtfMaterno;
    private javax.swing.JTextField jtfNombre;
    private javax.swing.JTextField jtfOrganizacion;
    private javax.swing.JTextField jtfPaterno;
    private javax.swing.JTextField jtfSector;
    private javax.swing.JTextField jtfZona;
    // End of variables declaration//GEN-END:variables

}
