package com.ingenio.datos;

import com.ingenio.app.gui.CatalogoPanel;
import com.ingenio.modelo.Sector;
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
public class SectorVista extends CatalogoPanel<Sector> {

    @Override
    protected void inicializar() {
        // Nada por el momento
    }

    @Override
    protected Editor<Sector> getEditor() {
        return new SectorEditor(app, monitor);
    }

    @Override
    protected Saver<Sector> getSaver() {
        return new Saver<>(app);
    }

    @Override
    protected DataProvider<Sector> getProvider() {
        return new ListaProvider<Sector>(app) {

            @Override
            protected void decorate(Criteria cr) {
                cr.setFetchMode("zona", FetchMode.JOIN);
            }

            @Override
            public Class getClase() {
                return Sector.class;
            }
        };
    }

    @Override
    public String getTitle() {
        return "Sectores";
    }

}
