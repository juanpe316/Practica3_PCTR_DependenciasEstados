package Parque;

/**
 * Clase principal, la cual arranca la aplicación.
 * 
 * @author Juan Pedro Alarcón Gómez, Estíbalitz Díez Rioja.
 * @since 1.0
 * @version 1
 */
public class SistemaLanzador {

	/**
	 * Método main.
	 * 
	 * @param args Recibe argumentos del programa.
	 */
	public static void main(String[] args) {

		IParque parque = new Parque();
		char letra_puerta = 'A';

		System.out.println("¡Parque abierto!");

		for (int i = 0; i < Integer.parseInt(args[0]); i++) {

			String puerta = "" + ((char) (letra_puerta++));

			// Creación de hilos de entrada
			ActividadEntradaPuerta entradas = new ActividadEntradaPuerta(puerta, parque);
			new Thread(entradas).start();

			// Creación de hilos de salida
			ActividadSalidaPuerta salidas = new ActividadSalidaPuerta(puerta, parque);
			new Thread(salidas).start();

		}

	}
}
