package com.ingenio.datos;

import com.ingenio.app.gui.CatalogoPanel;
import com.ingenio.modelo.Organizacion;
import mx.com.ledi.database.ListaProvider;
import mx.com.ledi.database.Saver;
import mx.com.ledi.interfaces.DataProvider;
import mx.com.ledi.interfaces.Editor;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class OrganizacionVista extends CatalogoPanel<Organizacion> {

    @Override
    protected void inicializar() {
        // Nada por el momento
    }

    @Override
    protected Editor<Organizacion> getEditor() {
        return new OrganizacionEditor(app, monitor);
    }

    @Override
    protected Saver<Organizacion> getSaver() {
        return new Saver<Organizacion>(app);
    }

    @Override
    protected DataProvider<Organizacion> getProvider() {
        return new ListaProvider<Organizacion>(app) {

            @Override
            public Class getClase() {
                return Organizacion.class;
            }
        };
    }

    @Override
    public String getTitle() {
        return "Organizaciones";
    }

}
