package com.ingenio.app.vistas;

import com.ingenio.app.Agenda;
import com.ingenio.app.AgendaClaves;
import com.ingenio.app.AgendaConf;
import com.ingenio.modelo.Configuracion;
import com.ingenio.origenes.OrigenGeneral;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import mx.com.ledi.error.AppException;
import mx.com.ledi.gui.LookAndFeelInfo;
import mx.com.ledi.interfaces.gui.Aplicacion;
import mx.com.ledi.interfaces.gui.AplicacionView;
import mx.com.ledi.interfaces.gui.OptionListModel;
import mx.com.ledi.interfaces.listeners.MonitorListener;
import mx.com.ledi.msg.AppMensaje;
import mx.com.ledi.util.DialogoUtil;
import mx.com.ledi.util.Format;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;
import org.pushingpixels.substance.api.skin.SkinInfo;

/**
 *
 * @author None
 */
public class PreferenciasVista extends JPanel implements AplicacionView {

    private Aplicacion app;
    private AgendaConf configuracion;
    private OrigenGeneral oGeneral;
    //
    private MonitorListener monitor;
    private OptionListModel<LookAndFeelInfo, String> lafs;

    public PreferenciasVista() {
        initComponents();

        monitor = new MonitorListener();
    }

    @Override
    public void init(Aplicacion aplicacion) throws AppException {
        this.app = aplicacion;
        oGeneral = (OrigenGeneral) app.getBean(AgendaClaves.ORI_GENERAL);

        configuracion = ((Agenda) aplicacion).getConfiguracion();
        //
        List<LookAndFeelInfo> lafis = new ArrayList<>();
        for (UIManager.LookAndFeelInfo lafi : UIManager.getInstalledLookAndFeels()) {
            lafis.add(new LookAndFeelInfo(lafi.getClassName(), lafi.getName()));
        }
        Iterator<Map.Entry<String, SkinInfo>> entries
                = SubstanceLookAndFeel.getAllSkins().entrySet().iterator();
        for (; entries.hasNext();) {
            Map.Entry<String, SkinInfo> next = entries.next();
            lafis.add(new LookAndFeelInfo(next.getValue().getClassName(), next.getValue().getDisplayName()));
        }

        lafs = new OptionListModel<>(lafis);
        jcbLafs.setModel(lafs);
        lafs.addListDataListener(monitor);
        lafs.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                setLookAndFeel(lafs.getSelected());
            }
        });
    }

    private void setLookAndFeel(final LookAndFeelInfo lafInfo) {
        if (lafInfo != null
                && !lafInfo.getId().equals(UIManager.getLookAndFeel().getClass().getName())) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Object laf = Class.forName(lafInfo.getId()).newInstance();
                        if (laf instanceof LookAndFeel) {
                            UIManager.setLookAndFeel((LookAndFeel) laf);
                        } else if (laf instanceof SubstanceSkin) {
                            SubstanceLookAndFeel.setSkin((SubstanceSkin) laf);
                        }
                        SwingUtilities.updateComponentTreeUI(PreferenciasVista.this.getTopLevelAncestor());
                    } catch (ClassNotFoundException |
                            InstantiationException |
                            IllegalAccessException |
                            UnsupportedLookAndFeelException gex) {
                    }
                }
            });
        }
    }

    @Override
    public void showView() throws AppException {
        //
        String laf = configuracion.getProperty(AgendaClaves.CFG_KEY_LAF);
        lafs.setSelectedById(laf != null ? laf : UIManager.getSystemLookAndFeelClassName());

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
        jLabel1 = new javax.swing.JLabel();
        jcbLafs = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jbGuardarConfiguracion = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Tema");
        jLabel1.setPreferredSize(new java.awt.Dimension(140, 30));

        jcbLafs.setPreferredSize(new java.awt.Dimension(160, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbLafs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbLafs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jbGuardarConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarConfiguracionActionPerformed
        try {
            configuracion.getProperties().put(AgendaClaves.CFG_KEY_LAF, lafs.getSelected() != null ? lafs.getSelected().getId()
                    : UIManager.getSystemLookAndFeelClassName());
            configuracion.guardar();
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
    private javax.swing.JComboBox jcbLafs;
    // End of variables declaration//GEN-END:variables
}
