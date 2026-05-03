
public class Jugador implements Comparable<Jugador>{
	private String nombre;
	private int puntuacion;
	
	public Jugador(String nombre, int puntuacion) {
		super();
		this.nombre = nombre;
		this.puntuacion = puntuacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	@Override
	public String toString() {
		return nombre + " - " + puntuacion;
	}

	@Override
	public int compareTo(Jugador o) {
		Integer puntuacionThis = this.getPuntuacion();
		Integer puntuacionOther = o.getPuntuacion();
		return -puntuacionThis.compareTo(puntuacionOther);
	}

	@Override
	public boolean equals(Object obj) {
		boolean resultado=false;
		if (obj instanceof Jugador) {
			Jugador otro = (Jugador) obj;
			resultado  = this.getNombre().equalsIgnoreCase(otro.getNombre()) &&
							this.getPuntuacion()==otro.getPuntuacion();
		}
		return resultado;
	}
	
	
	
}
