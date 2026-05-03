
public class Partido {
	private Jugador jugador1;
	private Jugador jugador2;
	private Jugador ganador;
	
	public Partido(Jugador jugador1, Jugador jugador2) {
		super();
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.ganador = null;
	}

	public Jugador getJugador1() {
		return jugador1;
	}

	public void setJugador1(Jugador jugador1) {
		this.jugador1 = jugador1;
	}

	public Jugador getJugador2() {
		return jugador2;
	}

	public void setJugador2(Jugador jugador2) {
		this.jugador2 = jugador2;
	}

	public Jugador getGanador() {
		return ganador;
	}

	public void setGanador(Jugador ganador) {
		this.ganador = ganador;
	}

	@Override
	public String toString() {
		String resultado;
		if (ganador==null)
			resultado = jugador1.getNombre()+" vs. "+jugador2.getNombre();
		else if (jugador1.equals(ganador))
			resultado = jugador1.getNombre().toUpperCase()+" vs. "+jugador2.getNombre();
		else
			resultado = jugador1.getNombre()+" vs. "+jugador2.getNombre().toUpperCase();
		return resultado;
	}
	
	

}
