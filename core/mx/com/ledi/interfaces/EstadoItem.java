package mx.com.ledi.interfaces;

import java.awt.Color;

/**
 *
 * @author None
 */
public class EstadoItem {

    public static final int SIN_CAMBIOS = -1;
    public static final int NUEVO = 0;
    public static final int MODIFICADO = 1;
    public static final int REMOVIDO = 2;

    private int estado = SIN_CAMBIOS;

    public EstadoItem() {
    }

    public boolean isNuevo() {
        return NUEVO == estado;
    }

    public boolean isRemovido() {
        return REMOVIDO == estado;
    }

    public boolean isModificado() {
        return MODIFICADO == estado;
    }

    public boolean isSinCambios() {
        return SIN_CAMBIOS == estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Color getColor() {
        switch (estado) {
            case NUEVO:
                return Color.GREEN;
            case MODIFICADO:
                return Color.BLUE;
            case REMOVIDO:
                return Color.RED;
            default:
                return Color.BLACK;
        }

    }
}
