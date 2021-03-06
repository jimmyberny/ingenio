package com.ingenio.datos;

import com.ingenio.modelo.Zafra;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.Editor;
import mx.com.ledi.interfaces.gui.Aplicacion;
import mx.com.ledi.interfaces.listeners.MonitorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class ZafraEditor extends Editor<Zafra> {

    private static final Logger log = LoggerFactory.getLogger(ZafraEditor.class);
    
    private Aplicacion app;
    private Zafra zafra;

    public ZafraEditor(Aplicacion app, MonitorListener monitor) {
        initComponents();

        this.app = app;
        monitor.listenTo(jtfNombre);
        monitor.listenTo(jtaDescripcion);
    }

    @Override
    public void initNoItem() {
        zafra = null;
        limpiar();
        setActivo(false);
    }

    @Override
    public void initItem() {
        zafra = new Zafra();
        limpiar();
        setActivo(true);
    }

    @Override
    public void mostrarItem(Zafra item) throws AppException {
        zafra = item;
        mostrar();
        setActivo(true);
    }

    @Override
    public void borrarItem(Zafra item) throws AppException {
        zafra = item;
        mostrar();
        setActivo(false);
    }

    public void mostrar() {
        jtfNombre.setText(zafra.getNombre());
        jtaDescripcion.setText(zafra.getDescripcion());
    }

    @Override
    public Zafra getItem() throws AppException {
        zafra.setNombre(jtfNombre.getText());
        zafra.setDescripcion(jtaDescripcion.getText());
        return zafra;
    }

    @Override
    public void setActivo(boolean activo) {
        jtfNombre.setEnabled(activo);
        jtaDescripcion.setEnabled(activo);
    }

    @Override
    public void actualizar() {
        // No hace nada
    }

    @Override
    public void limpiar() {
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(226, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jtaDescripcion;
    private javax.swing.JTextField jtfNombre;
    // End of variables declaration//GEN-END:variables

}
