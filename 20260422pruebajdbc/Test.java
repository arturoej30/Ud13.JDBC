package pruebajdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class Test {
	public static void main(String[] args) {
		Connection connection=null;
		/*try {
			/*
			 * Class.forName("com.mysql.cj.jdbc.Driver"); connection =
			 * DriverManager.getConnection("jdbc:mysql://localhost/biblioteca","usuario",
			 * "Usr2425!"); Statement st = connection.createStatement(); ResultSet rs =
			 * st.executeQuery("SELECT * FROM usuarios"); HashSet<Usuario> conjunto = new
			 * HashSet<>(); while (rs.next()) { int id = rs.getInt("idusuarios"); String
			 * nombre = rs.getString("nombre"); String clave = rs.getString("clave");
			 * Usuario usuario = new Usuario(id,nombre,clave); conjunto.add(usuario); }
			 */
			UsuarioDAO usrDAO = new UsuarioDAO();
			int resultado = usrDAO.insertarUsuario(new Usuario(0,"Pepito Grillo","123456"));
			HashSet<Usuario> conjunto = usrDAO.cargarUsuarios();
			for (Usuario usuario: conjunto) {
				System.out.println(usuario);
			}
		
	}

}
