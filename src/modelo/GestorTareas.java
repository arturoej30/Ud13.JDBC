package modelo;

import java.util.ArrayList;

public class GestorTareas {                      // Clase que gestiona todas las tareas.

	private ArrayList<Tarea> tareas;            // Lista donde se almacenan las tareas.

	public GestorTareas() {                     // Constructor de la clase.
		tareas = new ArrayList<>();             // Crea una lista vacía de tareas.
	}

	public void agregarTarea(Tarea tarea) {     // Método para añadir una tarea.
		if (tarea != null)                      // Comprueba que la tarea existe.
			tareas.add(tarea);                  // Añade la tarea a la lista.
	}

	public boolean eliminarTarea(String titulo) { // Método para eliminar una tarea por título.
		boolean eliminada = false;               // Indica si se ha eliminado una tarea.
		int indice = 0;                          // Posición actual de búsqueda.

		while (!eliminada) {                    // Repite hasta encontrar la tarea.
			if (tareas.get(indice).getTitulo().equalsIgnoreCase(titulo)) {
                                                 // Comprueba si el título coincide.
				tareas.remove(indice);          // Elimina la tarea encontrada.
				eliminada = true;              // Marca que ya fue eliminada.
			} else
				indice++;                      // Pasa a la siguiente tarea.
		}

		return eliminada;                       // Devuelve true si se eliminó.
	}

	public boolean modificarTarea(int indice, Tarea tarea) { // Modifica una tarea.
		boolean modificada = false;            // Indica si se modificó correctamente.

		if (indice < tareas.size() && indice >= 0) {
                                                 // Comprueba que el índice es válido.
			tareas.set(indice, tarea);         // Sustituye la tarea antigua.
			modificada = true;                // Marca la modificación como correcta.
		}

		return modificada;                     // Devuelve true si se modificó.
	}

	public ArrayList<Tarea> obtenerTodasTareas() { // Devuelve todas las tareas.
		return tareas;                         // Retorna la lista completa.
	}

	public int getNumeroTareas() {            // Obtiene el número de tareas.
		return tareas.size();                 // Devuelve la cantidad de tareas guardadas.
	}

}
