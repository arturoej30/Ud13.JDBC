package pruebajdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class UsuarioDAO {
	
	public HashSet<Usuario> cargarUsuarios(){
		HashSet<Usuario> conjunto=null;
		Connection connection=null;
		try {
			connection=ConexionBD.conectarBD();
			ResultSet rs = ConexionBD.ejecutarConsulta("SELECT * FROM usuarios");
			conjunto = new HashSet<>();
			while (rs.next()) {
				int id = rs.getInt("idusuarios");
				String nombre = rs.getString("nombre");
				String clave = rs.getString("clave");
				Usuario usuario = new Usuario(id,nombre,clave);
				conjunto.add(usuario);
			}
		}catch(SQLException e) {
			System.out.println("Error al conectar con la base de datos");
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No puedo acceder a la base de datos");
		}finally {
			try {
			if (connection!=null)
				ConexionBD.desconectarBD();
			}catch (SQLException e) {
				System.out.println("Error al cerrar la conexión");
			}
		}
		return conjunto;
	}
	
	public int insertarUsuario(Usuario usuario) {
		int resultado=-1;
		Connection connection=null;
		try {
			connection=ConexionBD.conectarBD();
			String sql = "INSERT INTO usuarios (nombre,clave) VALUES ('"+usuario.getNombre()+"','"+usuario.getClave()+"')";
			resultado = ConexionBD.ejecutarSentencia(sql);
		}catch (SQLException e) {
			System.out.println("Error al insertar el usuario");
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
			if (connection!=null)
				ConexionBD.desconectarBD();
			}catch (SQLException e) {
				System.out.println("Error al cerrar la conexión");
			}
		}
		return resultado;
	}
	

}
