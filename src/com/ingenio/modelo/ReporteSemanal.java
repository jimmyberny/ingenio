package com.ingenio.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author None
 */
@Entity
@Table(name = "reporte_semanal")
public class ReporteSemanal implements Serializable {

    private static final long serialVersionUID = 44378421982L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @ManyToOne
    @JoinColumn(name = "id_actividad_ciclo")
    private ActividadCiclo actividad;
    @ManyToOne
    @JoinColumn(name = "id_zona")
    private Zona zona;
    @ManyToOne
    @JoinColumn(name = "id_supervisor")
    private Supervisor supervisor;
    @Column(name = "fecha_reporte")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReporte;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @Column
    private Double programa;
    @Column
    private Double avance;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column
    private Integer semana;

    public ReporteSemanal() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ActividadCiclo getActividad() {
        return actividad;
    }

    public void setActividad(ActividadCiclo actividad) {
        this.actividad = actividad;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Date getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(Date fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getPrograma() {
        return programa;
    }

    public void setPrograma(Double programa) {
        this.programa = programa;
    }

    public Double getAvance() {
        return avance;
    }

    public void setAvance(Double avance) {
        this.avance = avance;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
    }

    public Double getCumplimiento() {
        if (avance != null && programa != null && programa.doubleValue() != 0) {
            return avance / programa;
        }
        return null;
    }
}
