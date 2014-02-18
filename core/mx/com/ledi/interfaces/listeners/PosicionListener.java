package mx.com.ledi.interfaces.listeners;

import java.util.EventListener;

/**
 *
 * @author Enrique
 */
public interface PosicionListener extends EventListener{

    public void actualizarPosicion(int pos, int total);
}
