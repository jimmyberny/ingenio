package com.ingenio.datos;

import com.ingenio.app.AgendaClaves;
import com.ingenio.app.gui.CatalogoPanel;
import com.ingenio.modelo.Configuracion;
import com.ingenio.modelo.Supervisor;
import com.ingenio.modelo.Zafra;
import com.ingenio.modelo.auxiliar.ActividadesPorCiclo;
import com.ingenio.origenes.OrigenGeneral;
import java.util.List;
import mx.com.ledi.database.Saver;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.DataProvider;
import mx.com.ledi.interfaces.Editor;
import mx.com.ledi.msg.AppMensaje;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class ReporteSemanalVista extends CatalogoPanel<ActividadesPorCiclo> {

    private OrigenGeneral oGeneral;
    private Zafra zafraActual;
    private Supervisor supervisor;

    @Override
    protected void inicializar() throws AppException {
        oGeneral = (OrigenGeneral) app.getBean(AgendaClaves.ORI_GENERAL);

        Configuracion conf = oGeneral.getConfiguracion(); // La configuracion actual
        zafraActual = conf.getZafra();
        supervisor = oGeneral.getSupervisor(app.getUsuario());
        if (supervisor == null) {
            throw new AppException("No puede realizar acciones porque no esta asignado como supervisor de una zona.");
        }
        // things ???
    }

    @Override
    protected Editor<ActividadesPorCiclo> getEditor() {
        return new ReporteSemanalEditor(app, monitor, supervisor);
    }

    @Override
    protected Saver<ActividadesPorCiclo> getSaver() {
        return new Saver<>(null, oGeneral.getTGuardarActividades(), null);
    }

    @Override
    protected DataProvider<ActividadesPorCiclo> getProvider() {
        return new DataProvider<ActividadesPorCiclo>() {

            @Override
            public List<ActividadesPorCiclo> cargar() throws AppException {
                return oGeneral.getCiclosByZafra(zafraActual);
            }
        };

    }

    @Override
    public String getTitle() {
        return "Reporte semanal";
    }

}
