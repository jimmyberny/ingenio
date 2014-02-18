package mx.com.ledi.util;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import mx.com.ledi.interfaces.gui.Cancelable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sirve para hacer que el dialogo se cierre cuando pulse la tecla Enter o
 * Escape.
 * <p>
 * Esta exclusivamente diseñada para el dialogo de errores.
 *
 * @author Darth Vader
 */
public class DialogKeyEventDistpatcher implements KeyEventDispatcher {

    public static Logger log = LoggerFactory.getLogger(DialogKeyEventDistpatcher.class);
    private static DialogKeyEventDistpatcher single;
    //
    private Cancelable dialog;

    public DialogKeyEventDistpatcher() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
    }

    public static void dispatch(Cancelable dialog) {
        if (single == null) {
            single = new DialogKeyEventDistpatcher();
        }
        single.dialog = dialog;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED
                && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            log.info("Dispatching {}", e.getKeyChar());
            if (dialog != null) {
                dialog.cancel();
            }
        }
        return false;
    }
}
