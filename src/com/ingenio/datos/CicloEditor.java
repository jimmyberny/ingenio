package com.ingenio.datos;

import com.ingenio.datos.dialogos.ZafraBuscador;
import com.ingenio.modelo.Ciclo;
import com.ingenio.modelo.Zafra;
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
public class CicloEditor extends Editor<Ciclo> {

    private static final Logger log = LoggerFactory.getLogger(CicloEditor.class);

    private Aplicacion app;
    private Ciclo ciclo;
    private Zafra zafra;

    public CicloEditor(Aplicacion app, MonitorListener monitor) {
        initComponents();

        this.app = app;
        monitor.listenTo(jtfZafra);
        monitor.listenTo(jtfNombre);
        monitor.listenTo(jtaDescripcion);
    }

    @Override
    public void initNoItem() {
        ciclo = null;
        limpiar();
        setActivo(false);
    }

    @Override
    public void initItem() {
        ciclo = new Ciclo();
        limpiar();
        setActivo(true);
    }

    @Override
    public void mostrarItem(Ciclo item) throws AppException {
        ciclo = item;
        mostrar();
        setActivo(true);
    }

    @Override
    public void borrarItem(Ciclo item) throws AppException {
        ciclo = item;
        mostrar();
        setActivo(false);
    }

    public void mostrar() {
        zafra = ciclo.getZafra();
        jtfZafra.setText(Format.OBJECT.format(zafra));

        jtfNombre.setText(ciclo.getNombre());
        jtaDescripcion.setText(ciclo.getDescripcion());
    }

    @Override
    public Ciclo getItem() throws AppException {
        ciclo.setZafra(zafra);
        ciclo.setNombre(jtfNombre.getText());
        ciclo.setDescripcion(jtaDescripcion.getText());
        return ciclo;
    }

    @Override
    public void setActivo(boolean activo) {
        jbSetZafra.setEnabled(activo);
        jtfNombre.setEnabled(activo);
        jtaDescripcion.setEnabled(activo);
    }

    @Override
    public void actualizar() {
        // No hace nada
    }

    @Override
    public void limpiar() {
        zafra = null;
        jtfZafra.setText(null);
        jtfNombre.setText(null);
        jtaDescripcion.setText(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaDescripcion = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jtfZafra = new javax.swing.JTextField();
        jbSetZafra = new javax.swing.JButton();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Nombre");
        jLabel1.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfNombre.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Descripción");
        jLabel9.setPreferredSize(new java.awt.Dimension(140, 30));

        jtaDescripcion.setColumns(20);
        jtaDescripcion.setRows(5);
        jScrollPane2.setViewportView(jtaDescripcion);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Zafra");
        jLabel2.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfZafra.setEditable(false);
        jtfZafra.setPreferredSize(new java.awt.Dimension(200, 30));

        jbSetZafra.setIcon(mx.com.ledi.gui.IconFactory.SET.getFindIcon());
        jbSetZafra.setPreferredSize(new java.awt.Dimension(40, 30));
        jbSetZafra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSetZafraActionPerformed(evt);
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
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfZafra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbSetZafra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtfZafra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbSetZafra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(189, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbSetZafraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSetZafraActionPerformed
        ZafraBuscador zb = ZafraBuscador.mostrar(this, app);
        if (zb.isAceptar()) {
            zafra = zb.getItem();
            jtfZafra.setText(Format.OBJECT.format(zafra));
        }
    }//GEN-LAST:event_jbSetZafraActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbSetZafra;
    private javax.swing.JTextArea jtaDescripcion;
    private javax.swing.JTextField jtfNombre;
    private javax.swing.JTextField jtfZafra;
    // End of variables declaration//GEN-END:variables

}
