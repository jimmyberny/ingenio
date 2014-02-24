package com.ingenio.modelo.auxiliar;

import com.ingenio.modelo.ActividadCiclo;
import com.ingenio.modelo.ReporteSemanal;

/**
 *
 * @author None
 */
public class ReporteSemanalExt {

    private ActividadCiclo actividad;
    private ReporteSemanal reporte;

    public ReporteSemanalExt(ActividadCiclo actividad) {
        this.actividad = actividad;
        reporte = new ReporteSemanal();
        reporte.setActividad(actividad);
    }

    public ActividadCiclo getActividad() {
        return actividad;
    }

    public void setActividad(ActividadCiclo actividad) {
        this.actividad = actividad;
    }

    public ReporteSemanal getReporte() {
        return reporte;
    }

    public void setReporte(ReporteSemanal reporte) {
        this.reporte = reporte;
    }

    @Override
    public String toString() {
        // En teoría nunca debe ser null
        return actividad.getActividad().getNombre();
    }

}
