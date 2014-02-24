package mx.com.ledi.interfaces;

/**
 *
 * @author José Bernardo Gómez-Andrade
 */
public class EstadoDialogo {

	public static final int ACEPTAR = 1;
	public static final int CANCELAR = 2;

	private int estado;

	public EstadoDialogo() {
		estado = ACEPTAR;
	}

	public void aceptar() {
		estado = ACEPTAR;
	}

	public void cancelar() {
		estado = CANCELAR;
	}

	public boolean isAceptar() {
		return estado == ACEPTAR;
	}

	public boolean isCancelar() {
		return estado == CANCELAR;
	}

}
