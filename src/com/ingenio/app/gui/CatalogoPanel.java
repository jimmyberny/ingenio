package com.ingenio.app.gui;

import mx.com.ledi.interfaces.listeners.MonitorListener;
import mx.com.ledi.interfaces.listeners.PosicionListener;
import javax.swing.JComponent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mx.com.ledi.database.ModeloItems;
import mx.com.ledi.database.NavegadorDatos;
import mx.com.ledi.database.Saver;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.DataProvider;
import mx.com.ledi.interfaces.Editor;
import mx.com.ledi.interfaces.gui.Aplicacion;
import mx.com.ledi.interfaces.gui.AplicacionView;
import mx.com.ledi.msg.AppMensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @param <T>
 * @author Zulma
 */
public abstract class CatalogoPanel<T> extends javax.swing.JPanel implements AplicacionView, PosicionListener {

    private static final Logger log = LoggerFactory.getLogger(CatalogoPanel.class);

    protected Aplicacion app;
    protected MonitorListener monitor;
    protected NavegadorDatos<T> nav;

    public CatalogoPanel() {
        initComponents();
    }

    @Override
    public void init(Aplicacion app) throws AppException {
        this.app = app;
        this.monitor = new MonitorListener();

        inicializar();
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    private void initNav() {
        Editor<T> editor = getEditor();
        jspEditor.setViewportView(editor.getComponente());
        jspEditor.getVerticalScrollBar().setUnitIncrement(50);

        ModeloItems<T> modelo = new ModeloItems(getProvider(), getSaver());
        nav = new NavegadorDatos<T>(modelo, editor, monitor);

        jlItems.setModel(modelo);
        jlItems.addListSelectionListener(new ItemSelectionListener(this));
        nav.addPosicionListener(this);

        jpControles.add(new JLabelMonitor(monitor));
        jpControles.add(new JLabelEstado(nav));
        jpControles.add(new JLabelPosicion(nav));
        jpControles.add(new JPanelCtrlModelo(nav));
    }

    @Override
    public void showView() throws AppException {
        if (nav == null) {
            initNav();
        }
        nav.iniciar();
    }

    @Override
    public boolean hideView() {
        try {
            return nav.puedeCerrar();
        } catch (AppException iex) {
            new AppMensaje(iex).mostrar(this);
            return false;
        }
    }

    @Override
    public void actualizarPosicion(int pos, int total) {
        log.info("Posicionando en {} de {}", pos, total);
        if (pos >= 0 && pos < total) {
            log.info("Seleccionando {}", pos);
            // jlItems.getSelectionModel().setSelectionInterval(pos, pos);
            jlItems.setSelectedIndex(pos);
            // jlItems.ensureIndexIsVisible(pos);
        } else {
            log.info("Limpiando selección");
            jlItems.getSelectionModel().clearSelection();
        }
    }

    protected abstract void inicializar() throws AppException;

    protected abstract Editor<T> getEditor();

    protected abstract Saver<T> getSaver();

    protected abstract DataProvider<T> getProvider();

    private class ItemSelectionListener implements ListSelectionListener {

        private JComponent padre;

        public ItemSelectionListener(JComponent padre) {
            this.padre = padre;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int i = jlItems.getSelectedIndex();
                if (i >= 0) {
                    if (!nav.isAjustando()) {
                        try {
                            if (nav.puedeMover()) {
                                nav.moverA(i);
                            } else {
                                nav.regresar();
                            }
                        } catch (AppException iex) {
                            nav.regresar();
                            new AppMensaje(iex).mostrar(padre);
                        }
                    }
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jspItems = new javax.swing.JScrollPane();
        jlItems = new javax.swing.JList();
        jpContenido = new javax.swing.JPanel();
        jpControles = new javax.swing.JPanel();
        jspEditor = new javax.swing.JScrollPane();

        setLayout(new java.awt.BorderLayout());

        jspItems.setPreferredSize(new java.awt.Dimension(220, 131));

        jlItems.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jspItems.setViewportView(jlItems);

        add(jspItems, java.awt.BorderLayout.LINE_START);

        jpContenido.setLayout(new java.awt.BorderLayout());

        jpControles.setPreferredSize(new java.awt.Dimension(612, 40));
        jpControles.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 2, 0));
        jpContenido.add(jpControles, java.awt.BorderLayout.PAGE_START);
        jpContenido.add(jspEditor, java.awt.BorderLayout.CENTER);

        add(jpContenido, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList jlItems;
    private javax.swing.JPanel jpContenido;
    private javax.swing.JPanel jpControles;
    private javax.swing.JScrollPane jspEditor;
    private javax.swing.JScrollPane jspItems;
    // End of variables declaration//GEN-END:variables

}
