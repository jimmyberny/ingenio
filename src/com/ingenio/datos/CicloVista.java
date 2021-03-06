package com.ingenio.datos;

import com.ingenio.app.gui.CatalogoPanel;
import com.ingenio.modelo.Ciclo;
import mx.com.ledi.database.ListaProvider;
import mx.com.ledi.database.Saver;
import mx.com.ledi.interfaces.DataProvider;
import mx.com.ledi.interfaces.Editor;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class CicloVista extends CatalogoPanel<Ciclo> {

    @Override
    protected void inicializar() {
        // Nada por el momento
    }

    @Override
    protected Editor<Ciclo> getEditor() {
        return new CicloEditor(app, monitor);
    }

    @Override
    protected Saver<Ciclo> getSaver() {
        return new Saver<>(app);
    }

    @Override
    protected DataProvider<Ciclo> getProvider() {
        return new ListaProvider<Ciclo>(app) {

            @Override
            protected void decorate(Criteria cr) {
                cr.setFetchMode("zafra", FetchMode.JOIN)
                        .addOrder(Order.asc("nombre"))
                        .createCriteria("zafra").addOrder(Order.asc("nombre"));

            }

            @Override
            public Class getClase() {
                return Ciclo.class;
            }
        };
    }

    @Override
    public String getTitle() {
        return "Ciclos";
    }

}
