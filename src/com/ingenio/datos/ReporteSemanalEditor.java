package com.ingenio.datos;

import com.ingenio.app.AgendaClaves;
import com.ingenio.modelo.ActividadSemana;
import com.ingenio.modelo.ReporteSemanal;
import com.ingenio.modelo.Supervisor;
import com.ingenio.modelo.auxiliar.ActividadCicloExt;
import com.ingenio.modelo.auxiliar.ActividadesPorCiclo;
import com.ingenio.modelo.auxiliar.ReporteSemanalExt;
import com.ingenio.origenes.OrigenGeneral;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.Editor;
import mx.com.ledi.interfaces.gui.Aplicacion;
import mx.com.ledi.interfaces.listeners.MonitorListener;
import mx.com.ledi.msg.AppMensaje;
import mx.com.ledi.util.DateUtil;
import mx.com.ledi.util.Format;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class ReporteSemanalEditor extends Editor<ActividadesPorCiclo> {

    private static final Logger log = LoggerFactory.getLogger(CicloEditor.class);

    private Aplicacion app;
    private OrigenGeneral oGeneral;
    private ActividadesPorCiclo ciclo;
    private Supervisor supervisor;
    //
    private ReporteModel modelo;
    private ReporteSemanalExt actividad;
    private Date fecha;
    private Date inicio;
    private Date fin;
    private int semana;

    //
    private JasperReport jr;

    public ReporteSemanalEditor(Aplicacion app, MonitorListener monitor, Supervisor supervisor) {
        initComponents();

        this.app = app;
        this.supervisor = supervisor;
        oGeneral = (OrigenGeneral) app.getBean(AgendaClaves.ORI_GENERAL);

        modelo = new ReporteModel(null);
        jtReporte.setModel(modelo);
        jtReporte.getSelectionModel().addListSelectionListener(new ActividadSelectionListener());

        jbFecha.setEditor(jtfFecha, Format.DATE);

        jbFecha.addPropertyChangeListener("date", new FechaListener());
        CumplimientoListener cl = new CumplimientoListener();
        jtfPrograma.getDocument().addDocumentListener(cl);
        jtfAvance.getDocument().addDocumentListener(cl);

        cargarReporte(); // 
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
        jtfZona.setText(Format.OBJECT.format(supervisor.getZona()));
        jbFecha.setDate(new Date()); // Hoy mismo, eso se supone dispara todo el proceso subsiguiente
    }

    @Override
    public ActividadesPorCiclo getItem() throws AppException {
        return ciclo;
    }

    @Override
    public void setActivo(boolean activo) {
        jtfNombre.setEnabled(activo);
    }

    @Override
    public void actualizar() {
    }

    @Override
    public void limpiar() {
        jtfNombre.setText(null);
    }

    private void mostrarReportes() {
        try {
            List<ReporteSemanal> reportes = oGeneral.listarReportes(supervisor, ciclo.getCiclo(), inicio, fin);
            Map<String, ReporteSemanal> map = new HashMap<>(reportes.size());
            // Mappear
            for (ReporteSemanal rs : reportes) {
                // reporte -> relacion CA -> Actividad -> id
                map.put(rs.getActividad().getActividad().getId(), rs);
            }
            List<ActividadCicloExt> acts = ciclo.getActividades();
            List<ReporteSemanalExt> res = new ArrayList<>(acts.size());
            for (ActividadCicloExt ace : acts) {
                ReporteSemanalExt rse = new ReporteSemanalExt(ace.getActividad());
                rse.setReporte(map.get(ace.getIdActividad()));

                res.add(rse);
            }
            fecha = inicio; // Sera la fecha que se registre en base de datos
            Calendar cWeek = Calendar.getInstance();
            cWeek.setTime(fecha);
            semana = cWeek.get(Calendar.WEEK_OF_YEAR);
            modelo.setItems(res); // Se supone que
        } catch (AppException ex) {
            new AppMensaje("Ha ocurrido un error al consultar los reportes para el intervalo de fecha dado.", ex).mostrar(this);
        }
    }

    /**
     * Inicializa el reporte de una actividad a la información básica.
     * <p>
     * Puede ser empleado para dos cosas:
     * <ul>
     * <li> Cuando no existe un reporte aún.
     * <li> Cuando se ha eliminado el reporte que estaba asociado a la
     * actividad.
     */
    private void initReporte() {
        ReporteSemanal rs = new ReporteSemanal();
        rs.setActividad(actividad.getActividad());
        rs.setFecha(fecha);
        rs.setSupervisor(supervisor);
        rs.setZona(supervisor.getZona());
        rs.setSemana(semana);
        this.actividad.setReporte(rs);
    }

    private void mostrarActividad() {
        if (jtReporte.getSelectionModel().isSelectionEmpty()) {
            return; // No hay nada seleccionado, salir del método
        }
        this.actividad = modelo.getRow(jtReporte.getSelectedRow());
        if (actividad.getReporte() == null) {
            initReporte();
        }
        ReporteSemanal rs = actividad.getReporte();

        jtfPrograma.setText(Format.DECIMAL.format(rs.getPrograma()));
        jtfAvance.setText(Format.DECIMAL.format(rs.getAvance()));
        jtfCumplimiento.setText(Format.PERCENT.format(rs.getCumplimiento()));

        // Datos comunes
        jtfActividad.setText(actividad.toString()); // Never null
        jtfSemana.setText(Format.INTEGER.format(rs.getSemana()));
        jtfSupervisor.setText(Format.OBJECT.format(rs.getSupervisor()));
    }

    private void calcularCumplimiento() {
        try {
            Double programa = Format.DECIMAL.parse(jtfPrograma.getText());
            if (programa != null && programa.doubleValue() != 0) {
                Double avance = Format.DECIMAL.parse(jtfAvance.getText(), 0d);
                jtfCumplimiento.setText(Format.PERCENT.format(avance / programa));
            } else {
                jtfCumplimiento.setText(Format.PERCENT.format(0d));
            }
        } catch (AppException ex) {
            // Nothing
        }
    }

    private void cargarReporte() {
        InputStream is = getClass().getResourceAsStream("/com/ingenio/app/reporte/mensual.ser");
        try {
            if (is == null) {
                JasperDesign jd = JRXmlLoader.load(
                        getClass().getResourceAsStream("/com/ingenio/app/reporte/mensual.jrxml"));
                jr = JasperCompileManager.compileReport(jd);

                OutputStream os = new FileOutputStream("anual.ser");
                OutputStream bos = new BufferedOutputStream(os, 32);
                try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                    oos.writeObject(jr);
                }
            } else {
                try (ObjectInputStream ois = new ObjectInputStream(is)) {
                    jr = (JasperReport) ois.readObject();
                }
            }
        } catch (ClassNotFoundException | IOException | JRException cnfex) {
            log.error(cnfex.getMessage(), cnfex);
        }
    }

    private class CumplimientoListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            calcularCumplimiento();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            calcularCumplimiento();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            calcularCumplimiento();
        }

    }

    private class FechaListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Calendar cal = Calendar.getInstance();
            cal.setTime((Date) evt.getNewValue());

            inicio = DateUtil.getStartOfDay(cal.getTime());
            cal.setTime(inicio);
            cal.add(Calendar.DAY_OF_YEAR, Calendar.SUNDAY - cal.get(Calendar.DAY_OF_WEEK));
            inicio = cal.getTime();
            jtfInicio.setText(Format.DATETIME.format(inicio));

            cal.add(Calendar.DAY_OF_YEAR, Calendar.SATURDAY - cal.get(Calendar.DAY_OF_WEEK));
            fin = DateUtil.getEndOfDay(cal.getTime());
            jtfFin.setText(Format.DATETIME.format(fin));

            log.info("From {} to {}", inicio, fin);
            mostrarReportes();
        }

    }

    private class ReporteModel extends AbstractTableModel {

        private List<ReporteSemanalExt> items;

        public ReporteModel(List<ReporteSemanalExt> items) {
            if (items == null) {
                items = new ArrayList<>(0);
            }
            this.items = items;
        }

        public void setItems(List<ReporteSemanalExt> items) {
            this.items = items;
            fireTableDataChanged();
        }

        public ReporteSemanalExt getRow(int idx) {
            return items.get(idx);
        }

        public void update(int idx) {
            fireTableRowsUpdated(idx, idx);
        }

        public void remove(int pos) {
            fireTableRowsDeleted(pos, pos);
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Actividad";
                case 1:
                    return "Programa";
                case 2:
                    return "Avance";
                case 3:
                    return "Cumplimiento";
                default:
                    return "Desconocido";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public int getRowCount() {
            return items.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ReporteSemanalExt rep = items.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return rep.toString();
                case 1:
                    return rep.getReporte() != null
                            ? Format.DECIMAL.format(rep.getReporte().getPrograma()) : "";
                case 2:
                    return rep.getReporte() != null
                            ? Format.DECIMAL.format(rep.getReporte().getAvance()) : "";
                case 3:
                    return rep.getReporte() != null
                            ? Format.PERCENT.format(rep.getReporte().getCumplimiento()) : "";
                default:
                    return "";
            }
        }

    }

    private class ActividadSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            log.info("Something happens");
            mostrarActividad();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jtfInicio = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jtfActividad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtfPrograma = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtfAvance = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtfCumplimiento = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jtfSemana = new javax.swing.JTextField();
        jtfSupervisor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jbGuardar = new javax.swing.JButton();
        jbEliminar = new javax.swing.JButton();
        jspReporte = new javax.swing.JScrollPane();
        jtReporte = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jtfFin = new javax.swing.JTextField();
        jbFecha = new mx.com.ledi.util.JCalendarButton();
        jLabel12 = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtfZona = new javax.swing.JTextField();
        jtfFecha = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jbConsultar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jmcInicio = new com.toedter.calendar.JMonthChooser();
        jmcFin = new com.toedter.calendar.JMonthChooser();
        jycIni = new com.toedter.calendar.JYearChooser();
        jycFin = new com.toedter.calendar.JYearChooser();
        jrvReporte = new mx.com.ledi.gui.JRViewerExt();

        setLayout(new java.awt.BorderLayout());

        jtfInicio.setEditable(false);
        jtfInicio.setPreferredSize(new java.awt.Dimension(200, 30));

        jtfActividad.setEditable(false);
        jtfActividad.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Actividad");
        jLabel5.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfPrograma.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Programada");
        jLabel6.setPreferredSize(new java.awt.Dimension(140, 30));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Avance");
        jLabel7.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfAvance.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Cumplimiento");
        jLabel8.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfCumplimiento.setEditable(false);
        jtfCumplimiento.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Semana");
        jLabel9.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfSemana.setEditable(false);
        jtfSemana.setPreferredSize(new java.awt.Dimension(200, 30));

        jtfSupervisor.setEditable(false);
        jtfSupervisor.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Supervisor");
        jLabel10.setPreferredSize(new java.awt.Dimension(140, 30));

        jbGuardar.setIcon(mx.com.ledi.gui.IconFactory.SET.getSaveIcon());
        jbGuardar.setText("Guardar");
        jbGuardar.setPreferredSize(new java.awt.Dimension(120, 30));
        jbGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarActionPerformed(evt);
            }
        });

        jbEliminar.setIcon(mx.com.ledi.gui.IconFactory.SET.getDeleteIcon());
        jbEliminar.setText("Eliminar");
        jbEliminar.setPreferredSize(new java.awt.Dimension(120, 30));
        jbEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfActividad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfSemana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfPrograma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfAvance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfCumplimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfActividad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfSemana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfPrograma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfAvance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfCumplimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jspReporte.setPreferredSize(new java.awt.Dimension(453, 200));

        jtReporte.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jspReporte.setViewportView(jtReporte);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Fin de semana");
        jLabel4.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfFin.setEditable(false);
        jtfFin.setPreferredSize(new java.awt.Dimension(200, 30));

        jbFecha.setPreferredSize(new java.awt.Dimension(40, 30));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Zona");
        jLabel12.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfNombre.setEditable(false);
        jtfNombre.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Fecha");
        jLabel2.setPreferredSize(new java.awt.Dimension(140, 30));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Inicio de semana");
        jLabel3.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfZona.setEditable(false);
        jtfZona.setPreferredSize(new java.awt.Dimension(200, 30));

        jtfFecha.setEditable(false);
        jtfFecha.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Ciclo");
        jLabel1.setPreferredSize(new java.awt.Dimension(140, 30));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspReporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtfFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtfZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jbFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtfZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtfFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtfInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jspReporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Reporte semanal", jPanel3);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jbConsultar.setIcon(mx.com.ledi.gui.IconFactory.SET.getExecuteIcon());
        jbConsultar.setText("Consultar");
        jbConsultar.setPreferredSize(new java.awt.Dimension(120, 30));
        jbConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbConsultarActionPerformed(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Mes inicio");
        jLabel11.setPreferredSize(new java.awt.Dimension(140, 30));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Mes fin");
        jLabel13.setPreferredSize(new java.awt.Dimension(140, 30));

        jmcInicio.setPreferredSize(new java.awt.Dimension(120, 30));

        jmcFin.setPreferredSize(new java.awt.Dimension(120, 30));

        jycIni.setPreferredSize(new java.awt.Dimension(60, 30));

        jycFin.setPreferredSize(new java.awt.Dimension(60, 30));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jmcInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jycIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jmcFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jycFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jmcInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jmcFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jycIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jycFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_START);
        jPanel2.add(jrvReporte, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Reporte anual", jPanel2);

        add(jTabbedPane1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarActionPerformed
        if (actividad != null && actividad.getReporte().getId() != null) {
            try {
                // do delete
                oGeneral.eliminarReporte(actividad);
                initReporte(); // Resetear la información del reporte
                modelo.update(jtReporte.getSelectedRow());
                new AppMensaje("El reporte ha sido eliminado exitosamente.").mostrar(this);
            } catch (AppException ex) {
                new AppMensaje("Ha sucedido un error en el intento de eliminar el reporte.", ex).mostrar(this);
            }
        }
    }//GEN-LAST:event_jbEliminarActionPerformed

    private void jbGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarActionPerformed
        if (actividad != null) {
            try {
                ReporteSemanal reporte = actividad.getReporte();
                reporte.setFechaReporte(new Date());
                reporte.setUsuario(app.getUsuario());

                reporte.setAvance(Format.DECIMAL.parse(jtfAvance.getText()));
                reporte.setPrograma(Format.DECIMAL.parse(jtfPrograma.getText()));

                oGeneral.guardarReporte(actividad);
                modelo.update(jtReporte.getSelectedRow());
                new AppMensaje("El reporte ha sido guardado exitosamente.").mostrar(this); // Todo bien
            } catch (AppException ex) {
                actividad.getReporte().setId(null); // Creo que puedo hacer esto
                new AppMensaje("Ha sucedido un error en el intento de guardar el reporte.", ex).mostrar(this);
            }
        }
    }//GEN-LAST:event_jbGuardarActionPerformed

    private void jbConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbConsultarActionPerformed
        if (jr != null) {
            try {
                Date mi = DateUtil.getStart(jmcInicio.getMonth(), jycIni.getYear());
                Date mf = DateUtil.getEnd(jmcFin.getMonth(), jycFin.getYear());
                /// Hacer la consuta
                List<ActividadSemana> actividades = oGeneral.listarActividades(mi, mf);
                Map<String, Object> params = new HashMap<>(4);
                ResourceBundle bundle = ResourceBundle.getBundle("com/ingenio/app/reporte/mensual", Format.getLocale());
                // 
                params.put("STR_FECHA", Format.DATETIME.format(new Date()));
                params.put("STR_INICIO", Format.DATETIME.format(mi));
                params.put("STR_FIN", Format.DATETIME.format(mf));
                params.put("REPORT_RESOURCE_BUNDLE", bundle);

                // 
                JasperPrint jp = JasperFillManager.fillReport(jr, params, new JRBeanCollectionDataSource(actividades));
                jrvReporte.update(jp);
            } catch (JRException | MissingResourceException | AppException ex) {
                new AppMensaje("Ha sucedido un error al inicializar el reporte", ex).mostrar(this);
            }
        } else {
            new AppMensaje("La plantilla para el reporte no existe").mostrar(this);
        }

    }//GEN-LAST:event_jbConsultarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbConsultar;
    private javax.swing.JButton jbEliminar;
    private mx.com.ledi.util.JCalendarButton jbFecha;
    private javax.swing.JButton jbGuardar;
    private com.toedter.calendar.JMonthChooser jmcFin;
    private com.toedter.calendar.JMonthChooser jmcInicio;
    private mx.com.ledi.gui.JRViewerExt jrvReporte;
    private javax.swing.JScrollPane jspReporte;
    private javax.swing.JTable jtReporte;
    private javax.swing.JTextField jtfActividad;
    private javax.swing.JTextField jtfAvance;
    private javax.swing.JTextField jtfCumplimiento;
    private javax.swing.JTextField jtfFecha;
    private javax.swing.JTextField jtfFin;
    private javax.swing.JTextField jtfInicio;
    private javax.swing.JTextField jtfNombre;
    private javax.swing.JTextField jtfPrograma;
    private javax.swing.JTextField jtfSemana;
    private javax.swing.JTextField jtfSupervisor;
    private javax.swing.JTextField jtfZona;
    private com.toedter.calendar.JYearChooser jycFin;
    private com.toedter.calendar.JYearChooser jycIni;
    // End of variables declaration//GEN-END:variables

}
