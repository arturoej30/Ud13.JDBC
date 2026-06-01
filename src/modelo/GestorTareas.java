package modelo;

import java.util.ArrayList;

public class GestorTareas {
	private ArrayList<Tarea> tareas;
	
	public GestorTareas() {
		tareas = new ArrayList<>();
	}
	
	public void agregarTarea(Tarea tarea) {
		if (tarea!=null)
			tareas.add(tarea);
	}
	
	public boolean eliminarTarea(String titulo) {
		boolean eliminada=false;
		int indice=0;
		while (!eliminada) {
			if (tareas.get(indice).getTitulo().equalsIgnoreCase(titulo)) {
				tareas.remove(indice);
				eliminada=true;
			} else
				indice++;
		}
		return eliminada;
	}
	
	public boolean modificarTarea(int indice, Tarea tarea) {
		boolean modificada=false;
		if (indice<tareas.size() && indice>=0) {
			tareas.set(indice, tarea);
			modificada=true;
		}
		return modificada;
	}
	
	public ArrayList<Tarea> obtenerTodasTareas(){
		return tareas;
	}
	
	public int getNumeroTareas() {
		return tareas.size();
	}
	
}
