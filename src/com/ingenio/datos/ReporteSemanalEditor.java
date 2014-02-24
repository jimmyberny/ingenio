package com.ingenio.datos;

import com.ingenio.app.AgendaClaves;
import com.ingenio.modelo.ReporteSemanal;
import com.ingenio.modelo.Supervisor;
import com.ingenio.modelo.auxiliar.ActividadCicloExt;
import com.ingenio.modelo.auxiliar.ActividadesPorCiclo;
import com.ingenio.modelo.auxiliar.ReporteSemanalExt;
import com.ingenio.origenes.OrigenGeneral;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.Editor;
import mx.com.ledi.interfaces.gui.Aplicacion;
import mx.com.ledi.interfaces.listeners.MonitorListener;
import mx.com.ledi.msg.AppMensaje;
import mx.com.ledi.util.DateUtil;
import mx.com.ledi.util.Format;
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

    public ReporteSemanalEditor(Aplicacion app, MonitorListener monitor) {
        initComponents();

        this.app = app;
        oGeneral = (OrigenGeneral) app.getBean(AgendaClaves.ORI_GENERAL);

        modelo = new ReporteModel(null);
        jtReporte.setModel(modelo);
        jbFecha.setEditor(jtfFecha, Format.DATE);

        jbFecha.addPropertyChangeListener("date", new FechaListener());
        try {
            supervisor = oGeneral.getSupervisor(app.getUsuario());
            // things ???
        } catch (AppException ex) {
            // Que no creeemos que pase alguna vez, pero bueno, por si las dudas
            new AppMensaje("No se pudo cargar el supervisor asociado a el usuario.", ex).mostrar(this);
        }
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

    private void mostrarReportes(Date inicio, Date fin) {
        try {
            List<ReporteSemanal> reportes = oGeneral.listarReportes(supervisor, inicio, fin);
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
            modelo.setItems(res); // Se supone que
        } catch (AppException ex) {
            new AppMensaje("Ha ocurrido un error al consultar los reportes para el intervalo de fecha dado.", ex).mostrar(this);
        }
    }

    private class FechaListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Calendar cal = Calendar.getInstance();
            cal.setTime((Date) evt.getNewValue());

            cal.add(Calendar.DAY_OF_YEAR, Calendar.SUNDAY - cal.get(Calendar.DAY_OF_WEEK));
            Date inicio = DateUtil.getStartOfDay(cal.getTime());
            jtfInicio.setText(Format.DATE.format(inicio));

            cal.add(Calendar.DAY_OF_YEAR, Calendar.SATURDAY);
            Date fin = DateUtil.getEndOfDay(cal.getTime());
            jtfFin.setText(Format.DATE.format(fin));
            log.info("From {} to {}", inicio, fin);
            
            mostrarReportes(inicio, fin);
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
                            ? Format.DECIMAL.format(rep.getReporte().getCumplimiento()) : "";
                default:
                    return "";
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        jspReporte = new javax.swing.JScrollPane();
        jtReporte = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtfFecha = new javax.swing.JTextField();
        jbFecha = new mx.com.ledi.util.JCalendarButton();
        jLabel3 = new javax.swing.JLabel();
        jtfInicio = new javax.swing.JTextField();
        jtfFin = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Ciclo");
        jLabel1.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfNombre.setEditable(false);
        jtfNombre.setPreferredSize(new java.awt.Dimension(200, 30));

        jtReporte.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jspReporte.setViewportView(jtReporte);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Fecha");
        jLabel2.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfFecha.setEditable(false);
        jtfFecha.setPreferredSize(new java.awt.Dimension(200, 30));

        jbFecha.setPreferredSize(new java.awt.Dimension(40, 30));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Inicio de semana");
        jLabel3.setPreferredSize(new java.awt.Dimension(140, 30));

        jtfInicio.setEditable(false);
        jtfInicio.setPreferredSize(new java.awt.Dimension(200, 30));

        jtfFin.setEditable(false);
        jtfFin.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Fin de semana");
        jLabel4.setPreferredSize(new java.awt.Dimension(140, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspReporte)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtfFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jspReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private mx.com.ledi.util.JCalendarButton jbFecha;
    private javax.swing.JScrollPane jspReporte;
    private javax.swing.JTable jtReporte;
    private javax.swing.JTextField jtfFecha;
    private javax.swing.JTextField jtfFin;
    private javax.swing.JTextField jtfInicio;
    private javax.swing.JTextField jtfNombre;
    // End of variables declaration//GEN-END:variables

}
