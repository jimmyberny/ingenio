package com.ingenio.datos;

import com.ingenio.app.gui.CatalogoPanel;
import com.ingenio.modelo.Actividad;
import com.ingenio.modelo.Zona;
import mx.com.ledi.database.ListaProvider;
import mx.com.ledi.database.Saver;
import mx.com.ledi.interfaces.DataProvider;
import mx.com.ledi.interfaces.Editor;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class ActividadVista extends CatalogoPanel<Actividad> {

    @Override
    protected void inicializar() {
        // Nada por el momento
    }

    @Override
    protected Editor<Actividad> getEditor() {
        return new ActividadEditor(app, monitor);
    }

    @Override
    protected Saver<Actividad> getSaver() {
        return new Saver<Actividad>(app);
    }

    @Override
    protected DataProvider<Actividad> getProvider() {
        return new ListaProvider<Actividad>(app) {

            @Override
            public Class getClase() {
                return Actividad.class;
            }
        };
    }

    @Override
    public String getTitle() {
        return "Actividades";
    }

}
