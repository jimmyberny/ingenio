package com.ingenio.datos;

import com.ingenio.app.gui.CatalogoPanel;
import com.ingenio.modelo.Cana;
import mx.com.ledi.database.ListaProvider;
import mx.com.ledi.database.Saver;
import mx.com.ledi.interfaces.DataProvider;
import mx.com.ledi.interfaces.Editor;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class CanaVista extends CatalogoPanel<Cana> {

    @Override
    protected void inicializar() {
        // Nada por el momento
    }

    @Override
    protected Editor<Cana> getEditor() {
        return new CanaEditor(app, monitor);
    }

    @Override
    protected Saver<Cana> getSaver() {
        return new Saver<Cana>(app);
    }

    @Override
    protected DataProvider<Cana> getProvider() {
        return new ListaProvider<Cana>(app) {

            @Override
            public Class getClase() {
                return Cana.class;
            }
        };
    }

    @Override
    public String getTitle() {
        return "Canas";
    }

}
