package com.ingenio.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author None
 */
@Entity
@Table(name = "configuracion")
public class Configuracion implements Serializable {

    private static final long serialVersionUID = 4096538439L;
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @ManyToOne
    @JoinColumn(name = "id_zafra_actual")
    private Zafra zafra;

    public Configuracion() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Zafra getZafra() {
        return zafra;
    }

    public void setZafra(Zafra zafra) {
        this.zafra = zafra;
    }

}
