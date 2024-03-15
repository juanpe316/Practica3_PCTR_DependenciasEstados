package Parque;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entradas por la puerta.
 * 
 * @author Juan Pedro Alarcón Gómez, Estíbalitz Díez Rioja.
 * @since 1.0
 * @version 1
 */
public class ActividadEntradaPuerta implements Runnable {

	/**
	 * Número de entradas por la puerta.
	 */
	private static final int NUMENTRADAS = 20;

	/**
	 * Puerta por la que accede al parque.
	 */
	private String puerta;

	/**
	 * Objeto interfaz Parque.
	 */
	private IParque parque;

	/**
	 * Constructor de la clase.
	 * 
	 * @param puerta Recibido como parámetro.
	 * @param parque Recibido como parámetro.
	 */
	public ActividadEntradaPuerta(String puerta, IParque parque) {
		this.puerta = puerta;
		this.parque = parque;
	}

	/**
	 * Acceso al parque por la puerta.
	 */
	@Override
	public void run() {
		for (int i = 0; i < NUMENTRADAS; i++) {
			try {
				parque.entrarAlParque(puerta);
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5) * 1000);
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Entrada interrumpida");
				Logger.getGlobal().log(Level.INFO, e.toString());
				return;
			}
		}
	}
}
