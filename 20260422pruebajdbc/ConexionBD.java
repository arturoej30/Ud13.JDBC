package pruebajdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
	private static Connection connection;
	
	public static Connection conectarBD() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca","usuario","Usr2425!");
		return connection;
	}

	public static void desconectarBD() throws SQLException {
		if (connection!=null)
			connection.close();
	}
	
	public static ResultSet ejecutarConsulta(String sql) throws SQLException {
		Statement st = connection.createStatement();
		return st.executeQuery(sql);
	}
	
	public static int ejecutarSentencia(String sql) throws SQLException {
		Statement st = connection.createStatement();
		return st.executeUpdate(sql);
	}
}
