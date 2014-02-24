package com.ingenio.app.vistas;

import com.ingenio.app.Agenda;
import com.ingenio.app.AgendaClaves;
import com.ingenio.modelo.Usuario;
import com.ingenio.origenes.OrigenGeneral;
import javax.swing.JComponent;
import javax.swing.JPanel;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.gui.Aplicacion;
import mx.com.ledi.interfaces.gui.AplicacionView;
import mx.com.ledi.msg.AppMensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author None
 */
public class LoginVista extends JPanel implements AplicacionView {

    private static final Logger log = LoggerFactory.getLogger(LoginVista.class);

    private Aplicacion app;
    private OrigenGeneral oGeneral;

    public LoginVista() {
        initComponents();

    }

    @Override
    public void init(Aplicacion aplicacion) throws AppException {
        this.app = aplicacion;
        oGeneral = (OrigenGeneral) app.getBean(AgendaClaves.ORI_GENERAL);

    }

    @Override
    public void showView() throws AppException {
    }

    @Override
    public boolean hideView() {
        return app.getUsuario() != null;
    }

    @Override
    public String getTitle() {
        return "Bienvenido a Ingenio Central Progreso";
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jpfClave = new javax.swing.JPasswordField();
        jbIngresar = new javax.swing.JButton();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Usuario");
        jLabel1.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfUsuario.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Contraseña");
        jLabel2.setPreferredSize(new java.awt.Dimension(140, 30));

        jpfClave.setPreferredSize(new java.awt.Dimension(200, 30));

        jbIngresar.setIcon(mx.com.ledi.gui.IconFactory.SET.getExecuteIcon());
        jbIngresar.setText("Ingresar");
        jbIngresar.setPreferredSize(new java.awt.Dimension(120, 30));
        jbIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbIngresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jtfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jpfClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpfClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(184, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbIngresarActionPerformed
        String u = jtfUsuario.getText();
        String c = new String(jpfClave.getPassword());
        if (!u.trim().isEmpty() && !c.trim().isEmpty()) {
            try {
                Usuario usuario = oGeneral.getUsuario(u, c);
                if (usuario == null) {
                    new AppMensaje("Usuario no encontrado.").mostrar(this);
                } else {
                    ((Agenda) app).ingresar(usuario);
                }
            } catch (AppException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }//GEN-LAST:event_jbIngresarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton jbIngresar;
    private javax.swing.JPasswordField jpfClave;
    private javax.swing.JTextField jtfUsuario;
    // End of variables declaration//GEN-END:variables
}
