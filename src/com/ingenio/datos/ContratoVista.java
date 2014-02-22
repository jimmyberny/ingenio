package com.ingenio.datos;

import com.ingenio.app.gui.CatalogoPanel;
import com.ingenio.modelo.Contrato;
import mx.com.ledi.database.ListaProvider;
import mx.com.ledi.database.Saver;
import mx.com.ledi.interfaces.DataProvider;
import mx.com.ledi.interfaces.Editor;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class ContratoVista extends CatalogoPanel<Contrato> {

    @Override
    protected void inicializar() {
        // Nada por el momento
    }

    @Override
    protected Editor<Contrato> getEditor() {
        return new ContratoEditor(app, monitor);
    }

    @Override
    protected Saver<Contrato> getSaver() {
        return new Saver<Contrato>(app);
    }

    @Override
    protected DataProvider<Contrato> getProvider() {
        return new ListaProvider<Contrato>(app) {

            @Override
            public Class getClase() {
                return Contrato.class;
            }
        };
    }

    @Override
    public String getTitle() {
        return "Contratos";
    }

}
