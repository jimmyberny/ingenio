package com.ingenio.origenes;

import com.ingenio.modelo.Zona;
import java.util.List;
import mx.com.ledi.database.Transaccion;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.gui.Aplicacion;
import mx.com.ledi.interfaces.gui.AplicacionBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author José Bernardo Gómez-Andrade
 */
public class OrigenGeneral implements AplicacionBean {

    private SessionFactory factory;

    @Override
    public void init(Aplicacion aplicacion) throws AppException {
        factory = aplicacion.getFactory();
    }

    public List<Zona> listarZonas() throws AppException {
        return (List<Zona>) new Transaccion(factory) {
            
            @Override
            public Object execInTransaction(Session s, Object... params) throws AppException {
                return s.createCriteria(Zona.class).list();
            }
        }.exec();
    }
    
}
