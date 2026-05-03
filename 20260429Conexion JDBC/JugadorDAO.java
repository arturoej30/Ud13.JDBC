import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;

public class JugadorDAO {
	
	public TreeSet<Jugador> cargarJugadores(){
		TreeSet<Jugador> jugadores = new TreeSet<>();
		try {
			ConexionBD.conectarBD();
			String sql = "SELECT * from jugadores";
			ResultSet rs = ConexionBD.ejecutarConsulta(sql);
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				int puntuacion = rs.getInt("puntuacion");
				Jugador jugador = new Jugador(nombre,puntuacion);
				jugadores.add(jugador);
			}
		}catch (ClassNotFoundException e) {
			
		}catch (SQLException e) {
			
		}finally {
			try {
				ConexionBD.desconectarBD();
			}catch (SQLException e) {
				
			}
		}
		
		return jugadores;
	}

}
