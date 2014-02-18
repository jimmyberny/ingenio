package mx.com.ledi.database;

import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;
import mx.com.ledi.error.AppException;
import mx.com.ledi.interfaces.Editor;
import mx.com.ledi.interfaces.listeners.EstadoListener;
import mx.com.ledi.interfaces.listeners.MonitorListener;
import mx.com.ledi.interfaces.listeners.PosicionListener;
import mx.com.ledi.util.DialogoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @param <T>
 */
public class NavegadorDatos<T> {

	private static final Logger log = LoggerFactory.getLogger(NavegadorDatos.class);

	public static final int NULL = 0;
	public static final int INSERTAR = 1;
	public static final int ACTUALIZAR = 2;
	public static final int ELIMINAR = 3;

	private static final int SIN_POSICION = -1;

	private final Editor<T> editor;
	private final MonitorListener monitor;
	private final ModeloItems<T> modelo;
	private int indice;
	private int estado;
	private boolean ajustando;

	private final EventListenerList listeners = new EventListenerList();

	public NavegadorDatos(ModeloItems<T> modelo, Editor<T> editor, MonitorListener monitor) {
		this.modelo = modelo;
		this.editor = editor;
		this.monitor = monitor;

		ajustando = false;
		estado = NULL;
		indice = SIN_POSICION;

		editor.initNoItem();
		monitor.setDirty(false);

	}

	public boolean isAjustando() {
		return ajustando;
	}

	public T getRegistroActual() {
		return indice >= 0 && indice < modelo.getSize() ? modelo.getElementAt(indice) : null;
	}

	private void indiceA(int nuevoIndice) {
		if (nuevoIndice >= 0 && nuevoIndice < modelo.getSize()) {
			indice = nuevoIndice;
		} else {
			indice = SIN_POSICION;
		}

		navegar();
	}

	private void navegar() {
		ajustando = true;
		T actual = getRegistroActual();
		if (actual == null) {
			estado = NULL;
			editor.initNoItem();
		} else {
			estado = ACTUALIZAR;
			try {
				editor.mostrarItem(actual);
			} catch (AppException iex) {
				// mostrar error
			}
		}
		monitor.setDirty(false);

		actualizarEstado();
		actualizarPosicion(indice, modelo.getSize());

		ajustando = false;
	}

	public void guardarCambios() throws AppException {
		if (monitor.isDirty()) {
			T item = editor.getItem();
			switch (estado) {
				case ACTUALIZAR: {
					int i = modelo.modificar(indice, item);
					editor.actualizar();
					indiceA(i);
					break;
				}
				case INSERTAR: {
					int i = modelo.registrar(item);
					editor.actualizar();
					indiceA(i);
					break;
				}
				case ELIMINAR: {
					int i = modelo.borrar(indice);
					editor.actualizar();
					indiceA(i);
					break;
				}
			}
		}
	}

	public boolean puedeMover() throws AppException {
		if (monitor.isDirty()) {
			int res = DialogoUtil.confirmar(null, "Existen datos sin guardar, ¿Desea descartar los cambios y continuar?", JOptionPane.YES_NO_CANCEL_OPTION);
			if (res == JOptionPane.YES_OPTION) {
				monitor.setDirty(false);
				return true;
			} else if (res == JOptionPane.NO_OPTION) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	public boolean puedeCerrar() throws AppException {
		if (monitor.isDirty()) {
			int res = DialogoUtil.confirmar(null, "Existen datos sin guardar", JOptionPane.YES_NO_CANCEL_OPTION);
			if (res == JOptionPane.YES_OPTION) {
				guardarCambios();
				return true;
			} else if (res == JOptionPane.NO_OPTION) {
				indiceA(indice);
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	public void cargar() throws AppException {
		editor.actualizar();
		modelo.cargar();
		indiceA(0);
	}

	public void iniciar() throws AppException {
		cargar();
		if (modelo.getSize() == 0) {
			registrar();
		}
	}

	public void moverA(int nuevoIndice) throws AppException {
		guardarCambios();
		if (indice != nuevoIndice) {
			indiceA(nuevoIndice);
		}
	}

	public void regresar() {
		ajustando = true;
		actualizarPosicion(indice, modelo.getSize());
		ajustando = false;
	}

	public void registrar() throws AppException {
		if (modelo.puedeRegistrar() && puedeMover()) {
			guardarCambios();
			estado = INSERTAR;
			editor.initItem();
			monitor.setDirty(false);

			actualizarEstado();
		}
	}

	public void borrar() throws AppException {
		if (modelo.puedeBorrar() && puedeMover()) {
			T item = getRegistroActual();
			if (item != null) {
				estado = ELIMINAR;
				editor.borrarItem(item);
				monitor.setDirty(true);

				actualizarEstado();
			}
		}
	}

	public void descartar() {
		if (!monitor.isDirty()
				|| DialogoUtil.confirmar(null, "Hay cambio, ¿Desea descartarlos para continuar?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			indiceA(indice);
		}
	}

	public void addEstadoListener(EstadoListener el) {
		listeners.add(EstadoListener.class, el);
	}

	public void addPosicionListener(PosicionListener pl) {
		listeners.add(PosicionListener.class, pl);
	}

	public void actualizarEstado() {
		for (EstadoListener el : listeners.getListeners(EstadoListener.class)) {
			el.actualizarEstado(estado);
		}
	}

	public void actualizarPosicion(int pos, int total) {
		for (PosicionListener pl : listeners.getListeners(PosicionListener.class)) {
			pl.actualizarPosicion(pos, total);
		}
	}

}
