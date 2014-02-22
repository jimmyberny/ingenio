package com.ingenio.datos;

import com.ingenio.app.gui.CatalogoPanel;
import com.ingenio.modelo.Zafra;
import mx.com.ledi.database.ListaProvider;
import mx.com.ledi.database.Saver;
import mx.com.ledi.interfaces.DataProvider;
import mx.com.ledi.interfaces.Editor;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class ZafraVista extends CatalogoPanel<Zafra> {

    @Override
    protected void inicializar() {
        // Nada por el momento
    }

    @Override
    protected Editor<Zafra> getEditor() {
        return new ZafraEditor(app, monitor);
    }

    @Override
    protected Saver<Zafra> getSaver() {
        return new Saver<Zafra>(app);
    }

    @Override
    protected DataProvider<Zafra> getProvider() {
        return new ListaProvider<Zafra>(app) {

            @Override
            public Class getClase() {
                return Zafra.class;
            }
        };
    }

    @Override
    public String getTitle() {
        return "Zafras";
    }

}
