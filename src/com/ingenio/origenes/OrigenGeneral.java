package com.ingenio.origenes;

import com.ingenio.modelo.Supervisor;
import com.ingenio.modelo.Usuario;
import com.ingenio.modelo.Zona;
import java.util.List;
import mx.com.ledi.database.Transaccion;
import mx.com.ledi.database.TransaccionSr;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.gui.Aplicacion;
import mx.com.ledi.interfaces.gui.AplicacionBean;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author José Bernardo Gómez-Andrade
 */
public class OrigenGeneral implements AplicacionBean {

    private static final Logger log = LoggerFactory.getLogger(OrigenGeneral.class);
    
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
    
    public List<Zona> listarZonasSinSupervisor() throws AppException {
        return (List<Zona>) new Transaccion(factory) {
            
            @Override
            public Object execInTransaction(Session s, Object... params) throws AppException {
                return s.createQuery("from Zona as z where z.id not in (select s.zona from Supervisor as s)").list();
            }
        }.exec();
    }
    
    public Supervisor getSupervisor(final Usuario usuario) throws AppException {
        return (Supervisor) new Transaccion(factory) {
            
            @Override
            public Object execInTransaction(Session s, Object... params) throws AppException {
                return s.createCriteria(Supervisor.class)
                        .add(Restrictions.eq("id", usuario.getId()))
                        .setFetchMode("zona", FetchMode.JOIN)
                        .uniqueResult();
            }
        }.exec();
    }
    
    public void asignar(final Usuario usuario, final Zona zona) throws AppException {
        new TransaccionSr(factory) {
            
            @Override
            public void execInTransaction(Session s, Object... params) throws AppException {
                Supervisor sup = new Supervisor();
                sup.setUsuario(usuario);
                sup.setZona(zona);
                s.save(sup);
                log.info("Supervisor guardado exitosamente");
            }
        }.exec();
    }
    
    public void removerAsignacion(final Supervisor supervisor) throws AppException {
        new TransaccionSr(factory) {
            
            @Override
            public void execInTransaction(Session s, Object... params) throws AppException {
                s.delete(supervisor);
            }
        }.exec();
    }
    
}
