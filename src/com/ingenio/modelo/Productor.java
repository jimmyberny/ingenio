package com.ingenio.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author None
 */
@Entity
@Table(name = "productor")
public class Productor implements Serializable {

    private static final long serialVersionUID = 443979752L;

    @Id
    private String clave;
    @Column
    private String nombre;
    @Column
    private String paterno;
    @Column
    private String materno;
    @ManyToOne
    @JoinColumn(name = "id_contrato")
    private Contrato contrato;
    @ManyToOne
    @JoinColumn(name = "id_organizacion")
    private Organizacion organizacion;
    @ManyToOne
    @JoinColumn(name = "id_zona")
    private Zona zona;
    @ManyToOne
    @JoinColumn(name = "id_sector")
    private Sector sector;

    public Productor() {
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

}
