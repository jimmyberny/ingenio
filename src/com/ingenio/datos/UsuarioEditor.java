package com.ingenio.datos;

import com.ingenio.app.AgendaClaves;
import com.ingenio.modelo.Supervisor;
import com.ingenio.modelo.Usuario;
import com.ingenio.modelo.Zona;
import com.ingenio.modelo.auxiliar.ListaSimple;
import com.ingenio.origenes.OrigenGeneral;
import java.util.logging.Level;
import javax.swing.DefaultListModel;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.Editor;
import mx.com.ledi.interfaces.gui.Aplicacion;
import mx.com.ledi.interfaces.listeners.MonitorListener;
import mx.com.ledi.msg.AppMensaje;
import mx.com.ledi.util.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class UsuarioEditor extends Editor<Usuario> {

    private static final Logger log = LoggerFactory.getLogger(UsuarioEditor.class);

    private Aplicacion app;
    private Usuario usuario;
    private OrigenGeneral oGeneral;
    //
    private ListaSimple<Zona> lZonas;
    private Supervisor supervisor;

    public UsuarioEditor(Aplicacion app, MonitorListener monitor) {
        initComponents();

        this.app = app;
        oGeneral = (OrigenGeneral) app.getBean(AgendaClaves.ORI_GENERAL);

        lZonas = new ListaSimple<Zona>();
        jlZonas.setModel(lZonas);

        monitor.listenTo(jtfNombre);
        monitor.listenTo(jtfPaterno);
        monitor.listenTo(jtfMaterno);
        monitor.listenTo(jtfUsuario);
        // Sobre la contraseña
        monitor.listenTo(jpfContra);
        monitor.listenTo(jpfRepetir);
        monitor.listenTo(jpfAnterior);
    }

    @Override
    public void initNoItem() {
        usuario = null;
        supervisor = null;

        limpiar();
        setActivo(false);
    }

    @Override
    public void initItem() {
        usuario = new Usuario();
        supervisor = null;

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
        jtfNombre.setText(usuario.getNombre());
        jtfPaterno.setText(usuario.getPaterno());
        jtfMaterno.setText(usuario.getMaterno());
        jtfUsuario.setText(usuario.getUsuario());
        jpfContra.setText(usuario.getClave());

        try {
                // Stuff about idnk
            // Listar las zonas sin supervisor
            lZonas.setList(oGeneral.listarZonasSinSupervisor());
            supervisor = oGeneral.getSupervisor(usuario);
            Zona zona = supervisor != null ? supervisor.getZona() : null;
            jtfZonaAsignada.setText(Format.OBJECT.format(zona));
        } catch (AppException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public Usuario getItem() throws AppException {
        usuario.setNombre(jtfNombre.getText());
        usuario.setPaterno(jtfPaterno.getText());
        usuario.setMaterno(jtfMaterno.getText());
        usuario.setUsuario(jtfUsuario.getText());
        String c1 = new String(jpfContra.getPassword());
        String c2 = new String(jpfRepetir.getPassword());
        if (!c1.equals(c2)) {
            throw new AppException("La nueva contraseña no coincide");
        }
        if (usuario.getId() == null) {
            // nuevo usuario
            usuario.setClave(c1);
        } else {
            String op = new String(jpfAnterior.getPassword());
            if (op.equals(usuario.getClave())) {
                usuario.setClave(c1);
            } else {
                throw new AppException("Se requiere la contraseña anterior para cambiar la actual");
            }
        }
        return usuario;
    }

    @Override
    public void setActivo(boolean activo) {
        jtfNombre.setEnabled(activo);
        jtfPaterno.setEnabled(activo);
        jtfMaterno.setEnabled(activo);
        jtfUsuario.setEnabled(activo);

        jpfContra.setEnabled(activo);
        jpfRepetir.setEnabled(activo);
        jpfAnterior.setEnabled(activo);
    }

    @Override
    public void actualizar() {
        // No hace nada
    }

    @Override
    public void limpiar() {
        jtfNombre.setText(null);
        jtfPaterno.setText(null);
        jtfMaterno.setText(null);
        jtfUsuario.setText(null);

        jpfContra.setText(null);
        jpfRepetir.setText(null);
        jpfAnterior.setText(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtPanels = new javax.swing.JTabbedPane();
        jpUsuarios = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jpfAnterior = new javax.swing.JPasswordField();
        jtfPaterno = new javax.swing.JTextField();
        jtfUsuario = new javax.swing.JTextField();
        jtfMaterno = new javax.swing.JTextField();
        jpfRepetir = new javax.swing.JPasswordField();
        jpfContra = new javax.swing.JPasswordField();
        jpSupervisor = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlZonas = new javax.swing.JList();
        jLabel8 = new javax.swing.JLabel();
        jtfZonaAsignada = new javax.swing.JTextField();
        jbDejar = new javax.swing.JButton();
        jbAsignar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Repetir contraseña");
        jLabel6.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfNombre.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Antigüa contraseña");
        jLabel7.setPreferredSize(new java.awt.Dimension(140, 30));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Nombre");
        jLabel1.setPreferredSize(new java.awt.Dimension(140, 30));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Usuario");
        jLabel2.setPreferredSize(new java.awt.Dimension(140, 30));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Apellido materno");
        jLabel5.setPreferredSize(new java.awt.Dimension(140, 30));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Apellido paterno");
        jLabel4.setPreferredSize(new java.awt.Dimension(140, 30));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Contraseña");
        jLabel3.setPreferredSize(new java.awt.Dimension(140, 30));

        jpfAnterior.setPreferredSize(new java.awt.Dimension(200, 30));

        jtfPaterno.setPreferredSize(new java.awt.Dimension(200, 30));

        jtfUsuario.setPreferredSize(new java.awt.Dimension(200, 30));

        jtfMaterno.setPreferredSize(new java.awt.Dimension(200, 30));

        jpfRepetir.setPreferredSize(new java.awt.Dimension(200, 30));

        jpfContra.setPreferredSize(new java.awt.Dimension(200, 30));

        javax.swing.GroupLayout jpUsuariosLayout = new javax.swing.GroupLayout(jpUsuarios);
        jpUsuarios.setLayout(jpUsuariosLayout);
        jpUsuariosLayout.setHorizontalGroup(
            jpUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpUsuariosLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpUsuariosLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpUsuariosLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpUsuariosLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpfContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpUsuariosLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpfRepetir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpUsuariosLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpfAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpUsuariosLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(376, 376, 376))
        );
        jpUsuariosLayout.setVerticalGroup(
            jpUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpfContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpfRepetir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpfAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jtPanels.addTab("Usuario", jpUsuarios);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Zonas sin supervisor"));

        jlZonas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jlZonas);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Zona asignada");
        jLabel8.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfZonaAsignada.setEditable(false);
        jtfZonaAsignada.setPreferredSize(new java.awt.Dimension(200, 30));

        jbDejar.setText("Dejar asignación");
        jbDejar.setPreferredSize(new java.awt.Dimension(160, 28));
        jbDejar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDejarActionPerformed(evt);
            }
        });

        jbAsignar.setText("Asignar");
        jbAsignar.setPreferredSize(new java.awt.Dimension(160, 28));
        jbAsignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAsignarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpSupervisorLayout = new javax.swing.GroupLayout(jpSupervisor);
        jpSupervisor.setLayout(jpSupervisorLayout);
        jpSupervisorLayout.setHorizontalGroup(
            jpSupervisorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpSupervisorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpSupervisorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfZonaAsignada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbDejar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        jpSupervisorLayout.setVerticalGroup(
            jpSupervisorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSupervisorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpSupervisorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpSupervisorLayout.createSequentialGroup()
                        .addGroup(jpSupervisorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfZonaAsignada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbDejar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(123, Short.MAX_VALUE))
        );

        jtPanels.addTab("Supervisor", jpSupervisor);

        add(jtPanels, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jbDejarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDejarActionPerformed
        //
        if (supervisor != null) {
            try {
                oGeneral.removerAsignacion(supervisor);
                mostrar(); // actualizar la informacion
                new AppMensaje("Asignación removida.").mostrar(this);
            } catch (AppException ex) {
                new AppMensaje("No se pudo remover la asignación.", ex).mostrar(this);
            }
        } else {
            new AppMensaje("No existe una asignación.").mostrar(this);
        }
    }//GEN-LAST:event_jbDejarActionPerformed

    private void jbAsignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAsignarActionPerformed
        if (supervisor == null) {
            if (jlZonas.getSelectedIndex() != -1) {
                try {
                    oGeneral.asignar(usuario, lZonas.get(jlZonas.getSelectedIndex()));
                    mostrar(); // actualizar la informacion
                    new AppMensaje("Asignación guardada.").mostrar(this);
                } catch (AppException ex) {
                    new AppMensaje("No se ha podido guardar la asignacion.", ex).mostrar(this);
                }
            }
        } else {
            new AppMensaje("Ya existe una asignación.").mostrar(this);
        }
    }//GEN-LAST:event_jbAsignarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAsignar;
    private javax.swing.JButton jbDejar;
    private javax.swing.JList jlZonas;
    private javax.swing.JPanel jpSupervisor;
    private javax.swing.JPanel jpUsuarios;
    private javax.swing.JPasswordField jpfAnterior;
    private javax.swing.JPasswordField jpfContra;
    private javax.swing.JPasswordField jpfRepetir;
    private javax.swing.JTabbedPane jtPanels;
    private javax.swing.JTextField jtfMaterno;
    private javax.swing.JTextField jtfNombre;
    private javax.swing.JTextField jtfPaterno;
    private javax.swing.JTextField jtfUsuario;
    private javax.swing.JTextField jtfZonaAsignada;
    // End of variables declaration//GEN-END:variables

}
