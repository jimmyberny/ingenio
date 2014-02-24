package com.ingenio.datos;

import com.ingenio.modelo.Supervisor;
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
public class SupervisorEditor extends Editor<Supervisor> {

    private static final Logger log = LoggerFactory.getLogger(SupervisorEditor.class);

    private Aplicacion app;
    private Supervisor supervisor;

    public SupervisorEditor(Aplicacion app, MonitorListener monitor) {
        initComponents();

        this.app = app;
    }

    @Override
    public void initNoItem() {
        supervisor = null;
        limpiar();
        setActivo(false);
    }

    @Override
    public void initItem() {
        supervisor = new Supervisor();
        limpiar();
        setActivo(true);
    }

    @Override
    public void mostrarItem(Supervisor item) throws AppException {
        supervisor = item;
        mostrar();
        setActivo(true);
    }

    @Override
    public void borrarItem(Supervisor item) throws AppException {
        supervisor = item;
        mostrar();
        setActivo(false);
    }

    public void mostrar() {
        jtfSupervisor.setText(Format.OBJECT.format(supervisor.getUsuario()));
        jtfZona.setText(Format.OBJECT.format(supervisor.getZona())); // We hope never null
    }

    @Override
    public Supervisor getItem() throws AppException {
        // Nada que cambiar
        return supervisor;
    }

    @Override
    public void setActivo(boolean activo) {
        jtfSupervisor.setEnabled(activo);
        jtfZona.setEnabled(activo);
    }

    @Override
    public void actualizar() {
        // No hace nada
    }

    @Override
    public void limpiar() {
        jtfSupervisor.setText(null);
        jtfZona.setText(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfSupervisor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtfZona = new javax.swing.JTextField();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Supervisor");
        jLabel1.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfSupervisor.setEditable(false);
        jtfSupervisor.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Zona");
        jLabel3.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfZona.setEditable(false);
        jtfZona.setPreferredSize(new java.awt.Dimension(200, 30));

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
                        .addComponent(jtfSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(376, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(273, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jtfSupervisor;
    private javax.swing.JTextField jtfZona;
    // End of variables declaration//GEN-END:variables

}
