package com.ingenio.datos;

import com.ingenio.app.gui.CatalogoPanel;
import com.ingenio.modelo.Zona;
import mx.com.ledi.database.ListaProvider;
import mx.com.ledi.database.Saver;
import mx.com.ledi.interfaces.DataProvider;
import mx.com.ledi.interfaces.Editor;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class ZonaVista extends CatalogoPanel<Zona> {

    @Override
    protected void inicializar() {
        // Nada por el momento
    }

    @Override
    protected Editor<Zona> getEditor() {
        return new ZonaEditor(app, monitor);
    }

    @Override
    protected Saver<Zona> getSaver() {
        return new Saver<Zona>(app);
    }

    @Override
    protected DataProvider<Zona> getProvider() {
        return new ListaProvider<Zona>(app) {

            @Override
            public Class getClase() {
                return Zona.class;
            }
        };
    }

    @Override
    public String getTitle() {
        return "Zonas";
    }

}
