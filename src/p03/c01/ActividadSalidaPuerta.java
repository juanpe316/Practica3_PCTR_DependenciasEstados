package Parque;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Salidas por la puerta.
 * 
 * @author Juan Pedro Alarcón Gómez, Estíbalitz Díez Rioja.
 * @since 1.0
 * @version 1
 */
public class ActividadSalidaPuerta implements Runnable {

	/**
	 * Número de salidas por la puerta.
	 */
	private static final int NUMSALIDAS = 20;
	/**
	 * Puerta por la que sale del parque.
	 */
	private String puerta;
	/**
	 * Objeto interfaz parque.
	 */
	private IParque parque;

	/**
	 * Constructor de la clase.
	 * 
	 * @param puerta Recibida como parámetro.
	 * @param parque Recibida como parámetro.
	 */
	public ActividadSalidaPuerta(String puerta, IParque parque) {
		this.puerta = puerta;
		this.parque = parque;
	}

	/**
	 * Salida por la puerta.
	 */
	@Override
	public void run() {
		for (int iteracion = 0; iteracion < NUMSALIDAS; iteracion++) {
			try {
				parque.salirDelParque(puerta);
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5) * 1000);
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Salida interrumpida");
				Logger.getGlobal().log(Level.INFO, e.toString());
				return;
			}
		}

	}

}
