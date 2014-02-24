package com.ingenio.datos;

import com.ingenio.app.AgendaClaves;
import com.ingenio.datos.dialogos.ActividadBuscador;
import com.ingenio.modelo.Actividad;
import com.ingenio.modelo.ActividadCiclo;
import com.ingenio.modelo.Zafra;
import com.ingenio.modelo.auxiliar.ActividadCicloExt;
import com.ingenio.modelo.auxiliar.ActividadesPorCiclo;
import com.ingenio.modelo.auxiliar.ListaSimple;
import com.ingenio.origenes.OrigenGeneral;
import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.Editor;
import mx.com.ledi.interfaces.EstadoItem;
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
public class CicloActividadesEditor extends Editor<ActividadesPorCiclo> {

    private static final Logger log = LoggerFactory.getLogger(CicloEditor.class);

    private Aplicacion app;
    private OrigenGeneral oGeneral;
    private Zafra zafraActual;
    private ActividadesPorCiclo ciclo;
    //
    private ListaSimple<ActividadCicloExt> actividades;
    private Map<String, ActividadCicloExt> map; // Id actividad

    public CicloActividadesEditor(Aplicacion app, MonitorListener monitor, Zafra zafra) {
        initComponents();

        this.app = app;
        this.zafraActual = zafra;
        oGeneral = (OrigenGeneral) app.getBean(AgendaClaves.ORI_GENERAL);
        actividades = new ListaSimple<>();

        monitor.listenTo(jtfNombre);
        actividades.addListDataListener(monitor);
        jlActividades.setModel(actividades);
        jlActividades.setCellRenderer(new ActividadesCellRenderer());

        map = new HashMap<>(0);
    }

    @Override
    public void initNoItem() {
        ciclo = null;
        limpiar();
        setActivo(false);
    }

    @Override
    public void initItem() {
        ciclo = new ActividadesPorCiclo();
        limpiar();
        setActivo(true);
    }

    @Override
    public void mostrarItem(ActividadesPorCiclo item) throws AppException {
        ciclo = item;
        mostrar();
        setActivo(true);
    }

    @Override
    public void borrarItem(ActividadesPorCiclo item) throws AppException {
        ciclo = item;
        mostrar();
        setActivo(false);
    }

    public void mostrar() {
        jtfNombre.setText(Format.OBJECT.format(ciclo.getCiclo()));
        map.clear(); //
        List<ActividadCicloExt> src = ciclo.getActividades();

        actividades.setList(src); // Same list
        if (src != null) {
            for (ActividadCicloExt ace : src) {
                ace.setEstado(EstadoItem.SIN_CAMBIOS); // quitar los colores
                map.put(ace.getIdActividad(), ace);
            }
        }
    }

    @Override
    public ActividadesPorCiclo getItem() throws AppException {
        if (!actividades.isEnlazado()) {
            ciclo.setActividades(actividades.getDatos());
        }
        return ciclo;
    }

    @Override
    public void setActivo(boolean activo) {
        jtfNombre.setEnabled(activo);
    }

    @Override
    public void actualizar() {
        // El hecho de que datos NUNCA sea NULL
        // depende del hecho de que se ha inicializado una lista vacía cuando
        // la capa de datos ha devuelto null
        List<ActividadCicloExt> datos = actividades.getDatos();
        if (datos != null) {
            Iterator<ActividadCicloExt> it = datos.iterator();
            while (it.hasNext()) {
                ActividadCicloExt next = it.next();
                if (next.getEstado().isRemovido()) {
                    it.remove();
                } else {
                    next.setEstado(EstadoItem.SIN_CAMBIOS);
                }
            }
        }
    }

    @Override
    public void limpiar() {
        jtfNombre.setText(null);
    }

    private class ActividadesCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            ActividadCicloExt ace = (ActividadCicloExt) value;
            setForeground(ace.getEstado().getColor());

            return this;
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        jspActividades = new javax.swing.JScrollPane();
        jlActividades = new javax.swing.JList();
        jbEliminar = new javax.swing.JButton();
        jbAgregarAct = new javax.swing.JButton();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Nombre");
        jLabel1.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfNombre.setEditable(false);
        jtfNombre.setPreferredSize(new java.awt.Dimension(200, 30));

        jspActividades.setBorder(javax.swing.BorderFactory.createTitledBorder("Actividades del ciclo"));
        jspActividades.setPreferredSize(new java.awt.Dimension(260, 131));

        jlActividades.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jspActividades.setViewportView(jlActividades);

        jbEliminar.setIcon(mx.com.ledi.gui.IconFactory.SET.getDeleteIcon());
        jbEliminar.setPreferredSize(new java.awt.Dimension(40, 30));
        jbEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEliminarActionPerformed(evt);
            }
        });

        jbAgregarAct.setIcon(mx.com.ledi.gui.IconFactory.SET.getAddIcon());
        jbAgregarAct.setPreferredSize(new java.awt.Dimension(40, 30));
        jbAgregarAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAgregarActActionPerformed(evt);
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
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jspActividades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbAgregarAct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(368, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jspActividades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbAgregarAct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbAgregarActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAgregarActActionPerformed
        ActividadBuscador ab = ActividadBuscador.mostrar(this, app);
        if (ab.isAceptar()) {
            Actividad item = ab.getItem();
            if (!map.containsKey(item.getId())) {
                ActividadCiclo ac = new ActividadCiclo();
                ac.setActividad(item);
                ac.setCiclo(ciclo.getCiclo());

                ActividadCicloExt wrap = new ActividadCicloExt(ac);
                wrap.setEstado(EstadoItem.NUEVO);

                actividades.add(wrap);
                map.put(item.getId(), wrap);
            } else {
                ActividadCicloExt wrap = map.get(item.getId());
                if (wrap != null && wrap.getEstado().isRemovido()) {
                    wrap.setEstado(EstadoItem.SIN_CAMBIOS);
                    actividades.update(wrap);
                } else {
                    new AppMensaje("Esta actividad ya se encuentra en la lista.").mostrar(this);
                }
            }
        }
    }//GEN-LAST:event_jbAgregarActActionPerformed

    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarActionPerformed
        int idx = jlActividades.getSelectedIndex();
        if (idx != -1) {
            ActividadCicloExt wrap = actividades.get(idx);
            wrap.setEstado(EstadoItem.REMOVIDO);
            actividades.update(idx);
        }
    }//GEN-LAST:event_jbEliminarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jbAgregarAct;
    private javax.swing.JButton jbEliminar;
    private javax.swing.JList jlActividades;
    private javax.swing.JScrollPane jspActividades;
    private javax.swing.JTextField jtfNombre;
    // End of variables declaration//GEN-END:variables

}
