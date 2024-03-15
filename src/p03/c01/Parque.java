package Parque;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Clase parque que controla el flujo de entradas y salidas de las personas,
 * además del aforo del mismo.
 * 
 * @author Juan Pedro Alarcón Gómez, Estíbalitz Díez Rioja.
 * @since 1.0
 * @version 1
 */
public class Parque implements IParque {

	/**
	 * Contador de personas totales en el parque.
	 */
	private int contadorPersonasTotales;
	/**
	 * Tiempo medio.
	 */
	private double tmedio;
	/**
	 * Contador de personas por puerta.
	 */
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	/**
	 * Tiempo inicial.
	 */
	private long tinicial;
	/**
	 * Aforo máximo de personas en el parque.
	 */
	private static final int MAX_PERSONAS = 50;
	/**
	 * Contador de entrada de personas por puerta.
	 */
	private int contadorEntradasTotales;
	/**
	 * Contador de salida de personas por puerta.
	 */
	private int contadorSalidasTotales;

	/**
	 * Constructor de la clase Parque.
	 */
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
		tinicial = System.currentTimeMillis();
		tmedio = 0;
		contadorEntradasTotales = 0;
		contadorSalidasTotales = 0;
	}

	/**
	 * Salida de personas del parque.
	 * 
	 * @param puerta Por la que sale del parque.
	 */
	public synchronized void salirDelParque(String puerta) {
		comprobarAntesDeSalir(); // Verificar condiciones antes de salir
		if (contadoresPersonasPuerta.get(puerta) == null) {
			contadoresPersonasPuerta.put(puerta, 0);
		}

		Integer contPu = contadoresPersonasPuerta.get(puerta);

		contPu--;
		contadorPersonasTotales--;
		contadorSalidasTotales++;

		contadoresPersonasPuerta.put(puerta, contPu);

		long tActual = System.currentTimeMillis();

		tmedio = (tmedio + (tActual - tinicial)) / 2.0;

		imprimirInfo(puerta, "Salida");

		checkInvariante();

		notifyAll();
	}

	/**
	 * Entrada de personas al parque por la puerta.
	 * 
	 * @param puerta Por la que accede al parque.
	 */
	public synchronized void entrarAlParque(String puerta) {
		comprobarAntesDeEntrar(); // Verificar condiciones antes de entrar
		if (contadoresPersonasPuerta.get(puerta) == null) {
			contadoresPersonasPuerta.put(puerta, 0);
		}

		contadorPersonasTotales++;
		contadorEntradasTotales++;
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) + 1);

		imprimirInfo(puerta, "Entrada");

		checkInvariante();

		notifyAll();

	}

	/**
	 * Mostramos la entrada y salidas del parque.
	 * 
	 * @param puerta     Por la que se realiza la acción.
	 * @param movimiento Salida o entrada del parque.
	 */
	private void imprimirInfo(String puerta, String movimiento) {
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println(
				"--> Personas en el parque " + contadorPersonasTotales + " tiempo medio de estancia: " + tmedio);
		System.out.println("--> Entradas totales acumuladas " + contadorEntradasTotales);
		System.out.println("--> Salidas totales acumuladas " + contadorSalidasTotales);
		// Iteramos por todas las puertas e imprimimos sus entradas
		for (String p : contadoresPersonasPuerta.keySet()) {
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}

	/**
	 * Gestiona las invariantes.
	 */
	private void checkInvariante() {

		assert sumarContadoresPuerta() == contadorPersonasTotales
				: "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		assert sumarContadoresPuerta() <= MAX_PERSONAS : "Se ha llegado al maximo de personas";
		assert sumarContadoresPuerta() >= 0 : "No quedan personas en el parque";
	}

	/**
	 * Contador de personas por cada puerta.
	 * 
	 * @return sumaContadoresPuerta Devuelve el número de personas por puerta.
	 */
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
		Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
		while (iterPuertas.hasMoreElements()) {
			sumaContadoresPuerta += iterPuertas.nextElement();
		}
		return sumaContadoresPuerta;
	}

	/**
	 * Comprobación de acceso al parque con el aforo máximo.
	 */
	protected void comprobarAntesDeEntrar() {
		while (contadorPersonasTotales >= MAX_PERSONAS) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.print(e.toString());
			}
		}
	}

	/**
	 * Comprobación de salida del parque.
	 */
	protected void comprobarAntesDeSalir() {
		while (contadorPersonasTotales == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.print(e.toString());
			}
		}
	}
}
