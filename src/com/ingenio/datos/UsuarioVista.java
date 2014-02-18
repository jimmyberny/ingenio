package com.ingenio.datos;

import com.ingenio.app.gui.CatalogoPanel;
import com.ingenio.modelo.Usuario;
import mx.com.ledi.database.ListaProvider;
import mx.com.ledi.database.Saver;
import mx.com.ledi.interfaces.DataProvider;
import mx.com.ledi.interfaces.Editor;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class UsuarioVista extends CatalogoPanel<Usuario> {

    @Override
    protected void inicializar() {
        // Nada por el momento
    }

    @Override
    protected Editor<Usuario> getEditor() {
        return new UsuarioEditor(monitor);
    }

    @Override
    protected Saver<Usuario> getSaver() {
        return new Saver<Usuario>(app);
    }

    @Override
    protected DataProvider<Usuario> getProvider() {
        return new ListaProvider<Usuario>(app) {

            @Override
            public Class getClase() {
                return Usuario.class;
            }
        };
    }

    @Override
    public String getTitle() {
        return "Usuarios";
    }

}
