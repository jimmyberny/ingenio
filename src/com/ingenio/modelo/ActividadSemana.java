package com.ingenio.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Field,Type,Null,Key,Default,Extra id,varchar(40),NO,,NULL,
 * mes,int(2),YES,,NULL, year,int(4),YES,,NULL,
 * id_actividad_ciclo,varchar(40),NO,,NULL, id_zona,varchar(40),NO,,NULL,
 * nombre_zona,varchar(20),NO,,NULL, avance,double,NO,,NULL,
 * programa,double,NO,,NULL, id_zafra,varchar(40),NO,,NULL,
 * nombre_zafra,varchar(100),NO,,NULL, id_ciclo,varchar(40),NO,,NULL,
 * nombre_ciclo,varchar(120),NO,,NULL, id_actividad,varchar(40),NO,,NULL,
 * nombre_actividad,varchar(100),NO,,NULL,
 *
 * @author None
 */
@Entity
@Table(name = "reporte_anual")
public class ActividadSemana implements Serializable {

    private static final long serialVersionUID = 48562L;
    @Id
    private String id;
    @Column
    private Integer mes;
    @Column
    private Integer anio;
    @ManyToOne
    @JoinColumn(name = "id_actividad_ciclo")
    private ActividadCiclo actividadCiclo;
    @ManyToOne
    @JoinColumn(name = "id_zona")
    private Zona zona;
    @Column(name = "nombre_zona")
    private String nombreZona;
    @Column
    private Double avance;
    @Column
    private Double programa;
    @ManyToOne
    @JoinColumn(name = "id_zafra")
    private Zafra zafra;
    @Column(name = "nombre_zafra")
    private String nombreZafra;
    @ManyToOne
    @JoinColumn(name = "id_ciclo")
    private Ciclo ciclo;
    @Column(name = "nombre_ciclo")
    private String nombreCiclo;
    @ManyToOne
    @JoinColumn(name = "id_actividad")
    private Actividad actividad;
    @Column(name = "nombre_actividad")
    private String nombreActividad;

    public ActividadSemana() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public ActividadCiclo getActividadCiclo() {
        return actividadCiclo;
    }

    public void setActividadCiclo(ActividadCiclo actividadCiclo) {
        this.actividadCiclo = actividadCiclo;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public Double getAvance() {
        return avance;
    }

    public void setAvance(Double avance) {
        this.avance = avance;
    }

    public Double getPrograma() {
        return programa;
    }

    public void setPrograma(Double programa) {
        this.programa = programa;
    }

    public Zafra getZafra() {
        return zafra;
    }

    public void setZafra(Zafra zafra) {
        this.zafra = zafra;
    }

    public String getNombreZafra() {
        return nombreZafra;
    }

    public void setNombreZafra(String nombreZafra) {
        this.nombreZafra = nombreZafra;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    public String getNombreCiclo() {
        return nombreCiclo;
    }

    public void setNombreCiclo(String nombreCiclo) {
        this.nombreCiclo = nombreCiclo;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }
}
