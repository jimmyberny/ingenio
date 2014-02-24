package com.ingenio.modelo.auxiliar;

import com.ingenio.modelo.Ciclo;
import java.util.List;

/**
 *
 * @author José Bernardo Gómez-Andrade
 */
public class ActividadesPorCiclo {

    private Ciclo ciclo;
    private List<ActividadCicloExt> actividades;

    public ActividadesPorCiclo() {
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    public List<ActividadCicloExt> getActividades() {
        return actividades;
    }

    public void setActividades(List<ActividadCicloExt> actividades) {
        this.actividades = actividades;
    }

    @Override
    public String toString() {
        return ciclo.toString();
    }

}
