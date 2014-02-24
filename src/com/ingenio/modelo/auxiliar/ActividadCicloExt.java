package com.ingenio.modelo.auxiliar;

import com.ingenio.modelo.ActividadCiclo;
import mx.com.ledi.interfaces.EstadoItem;

/**
 *
 * @author José Bernardo Gómez-Andrade
 */
public class ActividadCicloExt {

    private ActividadCiclo actividad;
    private EstadoItem estado = new EstadoItem();

    public ActividadCicloExt(ActividadCiclo actividad) {
        this.actividad = actividad;
    }

    public ActividadCiclo getActividad() {
        return actividad;
    }

    public void setActividad(ActividadCiclo actividad) {
        this.actividad = actividad;
    }

    /**
     * Identificador de la actividad.
     *
     * @return El identificador de la actividad.
     */
    public String getIdActividad() {
        return actividad.getActividad().getId();
    }

    public EstadoItem getEstado() {
        return estado;
    }

    /**
     * Método de facilidad.
     *
     * @param estado El estado.
     */
    public void setEstado(int estado) {
        this.estado.setEstado(estado);
    }

    @Override
    public String toString() {
        return actividad.getActividad().toString();
    }

}
