package com.ingenio.datos;

import com.ingenio.app.gui.CatalogoPanel;
import com.ingenio.modelo.Supervisor;
import mx.com.ledi.database.ListaProvider;
import mx.com.ledi.database.Saver;
import mx.com.ledi.interfaces.DataProvider;
import mx.com.ledi.interfaces.Editor;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class SupervisoresVista extends CatalogoPanel<Supervisor> {

    @Override
    protected void inicializar() {
        // Nada por el momento
    }

    @Override
    protected Editor<Supervisor> getEditor() {
        return new SupervisorEditor(app, monitor);
    }

    @Override
    protected Saver<Supervisor> getSaver() {
        return Saver.NO_SAVER;
    }

    @Override
    protected DataProvider<Supervisor> getProvider() {
        return new ListaProvider<Supervisor>(app) {

            @Override
            protected void decorate(Criteria cr) {
                cr.setFetchMode("usuario", FetchMode.JOIN).
                        setFetchMode("zona", FetchMode.JOIN);
            }

            @Override
            public Class getClase() {
                return Supervisor.class;
            }
        };
    }

    @Override
    public String getTitle() {
        return "Supervisores";
    }

}
