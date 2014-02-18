package com.ingenio.datos;

import com.ingenio.modelo.Usuario;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.Editor;
import mx.com.ledi.interfaces.listeners.MonitorListener;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class UsuarioEditor extends Editor<Usuario> {

    private Usuario usuario;

    public UsuarioEditor(MonitorListener monitor) {
        initComponents();

        monitor.listenTo(jtfNombre);
        monitor.listenTo(jtfUsuario);
        monitor.listenTo(jpfContra);
    }

    @Override
    public void initNoItem() {
        usuario = null;
        limpiar();
        setActivo(false);
    }

    @Override
    public void initItem() {
        usuario = new Usuario();
        limpiar();
        setActivo(true);
    }

    @Override
    public void mostrarItem(Usuario item) throws AppException {
        usuario = item;
        mostrar();
        setActivo(true);
    }

    @Override
    public void borrarItem(Usuario item) throws AppException {
        usuario = item;
        mostrar();
        setActivo(false);
    }

    public void mostrar() {
        jtfUsuario.setText(usuario.getUsuario());
        jtfNombre.setText(usuario.getNombre());
        jpfContra.setText(usuario.getContrasena());
    }

    @Override
    public Usuario getItem() throws AppException {
        usuario.setNombre(jtfNombre.getText());
        usuario.setUsuario(jtfUsuario.getText());
        usuario.setContrasena(new String(jpfContra.getPassword()));
        return usuario;
    }

    @Override
    public void setActivo(boolean activo) {
        jtfNombre.setEnabled(activo);
        jtfUsuario.setEnabled(activo);
        jpfContra.setEnabled(activo);
    }

    @Override
    public void actualizar() {
        // No hace nada
    }

    @Override
    public void limpiar() {
        jtfNombre.setText(null);
        jtfUsuario.setText(null);
        jpfContra.setText(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtfUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jpfContra = new javax.swing.JPasswordField();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Nombre");
        jLabel1.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfNombre.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Usuario");
        jLabel2.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfUsuario.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Contraseña");
        jLabel3.setPreferredSize(new java.awt.Dimension(140, 30));

        jpfContra.setPreferredSize(new java.awt.Dimension(200, 30));

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
                        .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpfContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpfContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(183, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField jpfContra;
    private javax.swing.JTextField jtfNombre;
    private javax.swing.JTextField jtfUsuario;
    // End of variables declaration//GEN-END:variables

}
