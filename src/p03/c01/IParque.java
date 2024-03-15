package Parque;

/**
 * Interfaz Parque.
 * 
 * @author Juan Pedro Alarcón Gómez, Estíbalitz Díez Rioja.
 * @since 1.0
 * @version 1
 */
public interface IParque {
	/**
	 * Entrada al parque.
	 * 
	 * @param puerta Por la que va a entrar.
	 */
	public abstract void entrarAlParque(String puerta);

	/**
	 * Salida del parque.
	 * 
	 * @param idPuerta Por la que va a salir.
	 */
	public abstract void salirDelParque(String idPuerta);
}
