package com.ingenio.origenes;

import com.ingenio.modelo.Actividad;
import com.ingenio.modelo.ActividadCiclo;
import com.ingenio.modelo.ActividadSemana;
import com.ingenio.modelo.Ciclo;
import com.ingenio.modelo.Configuracion;
import com.ingenio.modelo.Contrato;
import com.ingenio.modelo.Organizacion;
import com.ingenio.modelo.ReporteSemanal;
import com.ingenio.modelo.Sector;
import com.ingenio.modelo.Supervisor;
import com.ingenio.modelo.Usuario;
import com.ingenio.modelo.Zafra;
import com.ingenio.modelo.Zona;
import com.ingenio.modelo.auxiliar.ActividadCicloExt;
import com.ingenio.modelo.auxiliar.ActividadesPorCiclo;
import com.ingenio.modelo.auxiliar.ReporteSemanalExt;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import mx.com.ledi.database.Transaccion;
import mx.com.ledi.database.TransaccionSr;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.gui.Aplicacion;
import mx.com.ledi.interfaces.gui.AplicacionBean;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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

    public Usuario getUsuario(final String usuario, final String clave) throws AppException {
        return (Usuario) new Transaccion(factory) {

            @Override
            public Object execInTransaction(Session s, Object... params) throws AppException {
                return s.createCriteria(Usuario.class)
                        .add(Restrictions.eq("usuario", usuario))
                        .add(Restrictions.eq("clave", clave))
                        .uniqueResult();
            }
        }.exec();
    }

    private <T> List<T> listar(final Class clazz, final String field, final String value) throws AppException {
        return (List<T>) new Transaccion(factory) {

            @Override
            public Object execInTransaction(Session s, Object... params) throws AppException {
                Criteria cr = s.createCriteria(clazz);
                if (value != null) {
                    cr.add(Restrictions.like(field, value, MatchMode.ANYWHERE));
                }
                return cr.list();
            }
        }.exec();
    }

    public List<Zona> listarZonas(final String nombre) throws AppException {
        return listar(Zona.class, "nombre", nombre);
    }

    public List<Organizacion> listarOrganizaciones(final String nombre) throws AppException {
        return listar(Organizacion.class, "nombre", nombre);
    }

    public List<Zafra> listarZafras(final String nombre) throws AppException {
        return listar(Zafra.class, "nombre", nombre);
    }

    public List<Contrato> listarContratos(final String nombre) throws AppException {
        return listar(Contrato.class, "nombre", nombre);
    }

    public List<Sector> listarSectores(final String clave) throws AppException {
        return listar(Sector.class, "clave", clave);
    }

    public List<Actividad> listarActividades(final String nombre) throws AppException {
        return listar(Actividad.class, "nombre", nombre);
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

    public Configuracion getConfiguracion() throws AppException {
        return (Configuracion) new Transaccion(factory) {

            @Override
            public Object execInTransaction(Session s, Object... params) throws AppException {
                return s.createCriteria(Configuracion.class).setMaxResults(1).uniqueResult();
            }
        }.exec();
    }

    public void guardarConfiguracion(final Configuracion configuracion) throws AppException {
        new TransaccionSr(factory) {

            @Override
            public void execInTransaction(Session s, Object... params) throws AppException {
                s.saveOrUpdate(configuracion);
                // xD
            }
        }.exec();
    }

    public List<ActividadCicloExt> getActividades(final Zafra zafra, final Ciclo ciclo) throws AppException {
        return (List<ActividadCicloExt>) new Transaccion(factory) {

            @Override
            public Object execInTransaction(Session s, Object... params) throws AppException {
                List<ActividadCiclo> acts = s.createCriteria(ActividadCiclo.class)
                        .add(Restrictions.eq("ciclo", ciclo))
                        .createCriteria("ciclo").add(Restrictions.eq("zafra", zafra)).list();

                List<ActividadCicloExt> res = new ArrayList<>(acts.size());
                for (ActividadCiclo ac : acts) {
                    res.add(new ActividadCicloExt(ac));
                }
                return res;
            }
        }.exec();
    }

    public List<ActividadesPorCiclo> getCiclosByZafra(final Zafra zafra) throws AppException {
        return (List<ActividadesPorCiclo>) new Transaccion(factory) {

            @Override
            public Object execInTransaction(Session s, Object... params) throws AppException {
                List<Ciclo> ciclos = s.createCriteria(Ciclo.class)
                        .add(Restrictions.eq("zafra", zafra))
                        .list();

                List<ActividadCiclo> grupos = s.createCriteria(ActividadCiclo.class)
                        .setFetchMode("actividad", FetchMode.JOIN)
                        .add(Restrictions.in("ciclo", ciclos))
                        .list();

                HashMap<Ciclo, List<ActividadCicloExt>> map = new HashMap<>(grupos.size());
                for (ActividadCiclo ac : grupos) {
                    Ciclo ciclo = ac.getCiclo();
                    if (!map.containsKey(ciclo)) {
                        map.put(ciclo, new ArrayList<ActividadCicloExt>(5));
                    }
                    map.get(ciclo).add(new ActividadCicloExt(ac));
                }

                List<ActividadesPorCiclo> res = new ArrayList<>(map.size());
                for (Ciclo ciclo : ciclos) {
                    ActividadesPorCiclo tmp = new ActividadesPorCiclo();
                    tmp.setCiclo(ciclo);
                    tmp.setActividades(map.get(ciclo));

                    res.add(tmp);
                }
                return res;
            }
        }.exec();
    }

    public Transaccion getTGuardarActividades() {
        return new Transaccion(factory) {

            @Override
            public Object execInTransaction(Session s, Object... params) throws AppException {
                ActividadesPorCiclo actividades = (ActividadesPorCiclo) params[0];
                List<ActividadCicloExt> acs = actividades.getActividades();
                for (ActividadCicloExt ace : acs) {
                    if (ace.getEstado().isNuevo()) {
                        s.save(ace.getActividad());
                    } else if (ace.getEstado().isRemovido()) {
                        s.delete(ace.getActividad());
                    }
                }
                return true; // Todo salio bien
            }
        };
    }

    public List<ReporteSemanal> listarReportes(final Supervisor supervisor,
            final Ciclo ciclo,
            final Date inicio,
            final Date fin) throws AppException {
        return (List<ReporteSemanal>) new Transaccion(factory) {

            @Override
            public Object execInTransaction(Session s, Object... params) throws AppException {
                return s.createCriteria(ReporteSemanal.class)
                        // .add(Restrictions.eq("supervisor", supervisor))
                        .setFetchMode("supervisor", FetchMode.JOIN)
                        .setFetchMode("actividad", FetchMode.JOIN)
                        .add(Restrictions.eq("zona", supervisor.getZona()))
                        .add(Restrictions.le("fecha", inicio))
                        .add(Restrictions.lt("fecha", fin)) // No inclusivo
                        .createCriteria("actividad").add(Restrictions.eq("ciclo", ciclo))
                        .list();
            }
        }.exec();
    }

    public void eliminarReporte(final ReporteSemanalExt reporte) throws AppException {
        new TransaccionSr(factory) {

            @Override
            public void execInTransaction(Session s, Object... params) throws AppException {
                s.delete(reporte.getReporte());
            }
        }.exec();
    }

    public void guardarReporte(final ReporteSemanalExt reporte) throws AppException {
        new TransaccionSr(factory) {

            @Override
            public void execInTransaction(Session s, Object... params) throws AppException {
                s.saveOrUpdate(reporte.getReporte());
            }
        }.exec();
    }

    public List<ActividadSemana> listarActividades(final Date inicio, final Date fin) throws AppException {
        return (List<ActividadSemana>) new Transaccion(factory) {

            @Override
            public Object execInTransaction(Session s, Object... params) throws AppException {
                List<Object[]> list = s.createCriteria(ActividadSemana.class)
                        .setProjection(Projections.projectionList()
                                .add(Projections.property("mes"), "mes")
                                .add(Projections.property("anio"), "anio")
                                .add(Projections.sum("programa"))
                                .add(Projections.sum("avance"))
                                .add(Projections.groupProperty("zafra"), "zafra")
                                .add(Projections.groupProperty("zona"), "zona")
                                .add(Projections.groupProperty("ciclo"), "ciclo")
                                .add(Projections.groupProperty("actividad"), "actividad"))
                        .addOrder(Order.asc("zafra"))
                        .addOrder(Order.asc("zona"))
                        .addOrder(Order.asc("ciclo"))
                        .addOrder(Order.asc("actividad"))
                        .addOrder(Order.asc("anio"))
                        .addOrder(Order.asc("mes")).list();
                
                List<ActividadSemana> actividades = new ArrayList<>(list.size());
                for (Object[] arr : list) {
                    ActividadSemana am = new ActividadSemana();
                    am.setMes((Integer) arr[0]);
                    am.setAnio((Integer) arr[1]);
                    am.setPrograma((Double) arr[2]);
                    am.setAvance((Double) arr[3]);
                    am.setZafra((Zafra) arr[4]);
                    am.setZona((Zona) arr[5]);
                    am.setCiclo((Ciclo) arr[6]);
                    am.setActividad((Actividad) arr[7]);
                    actividades.add(am);
                }
                return actividades;
            }
        }.exec();
    }

}
