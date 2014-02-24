package com.ingenio.datos;

import com.ingenio.app.gui.CatalogoPanel;
import com.ingenio.modelo.Productor;
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
public class ProductorVista extends CatalogoPanel<Productor> {

    @Override
    protected void inicializar() {
        // Nada por el momento
    }

    @Override
    protected Editor<Productor> getEditor() {
        return new ProductorEditor(app, monitor);
    }

    @Override
    protected Saver<Productor> getSaver() {
        return new Saver<>(app);
    }

    @Override
    protected DataProvider<Productor> getProvider() {
        return new ListaProvider<Productor>(app) {

            @Override
            protected void decorate(Criteria cr) {
                cr.setFetchMode("sector", FetchMode.JOIN)
                        .setFetchMode("zona", FetchMode.JOIN)
                        .setFetchMode("contrato", FetchMode.JOIN)
                        .setFetchMode("organizacion", FetchMode.JOIN);
            }

            @Override
            public Class getClase() {
                return Productor.class;
            }
        };
    }

    @Override
    public String getTitle() {
        return "Productores";
    }

}
