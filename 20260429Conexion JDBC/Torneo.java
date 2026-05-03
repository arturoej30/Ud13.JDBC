import java.awt.EventQueue;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class Torneo {

	private JFrame frame;
	private TreeSet<Jugador> jugadores;
	private JTextArea textAreaJugadores;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Torneo window = new Torneo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Torneo() {
		initialize();
		cargarJugadores();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(530,450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JButton btnCargar = new JButton("Cargar jugadores");
		panel.add(btnCargar);
		
		JButton btnGenerar = new JButton("Generar ronda");
		panel.add(btnGenerar);
		
		JButton btnJugar = new JButton("Jugar ronda");
		panel.add(btnJugar);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		
		textAreaJugadores = new JTextArea();
		textAreaJugadores.setColumns(20);
		textAreaJugadores.setRows(20);
		panel_1.add(textAreaJugadores);
		
		JTextArea textAreaPartidos = new JTextArea();
		textAreaPartidos.setColumns(20);
		textAreaPartidos.setRows(20);
		panel_1.add(textAreaPartidos);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		
		JButton btnNuevoTorneo = new JButton("Nuevo torneo");
		panel_2.add(btnNuevoTorneo);
		
		JButton btnNuevoATP = new JButton("Nuevo torneo ATP");
		panel_2.add(btnNuevoATP);

		frame.setVisible(true);
	}

	private void cargarJugadores() {
		JugadorDAO usrDAO = new JugadorDAO();
		jugadores = usrDAO.cargarJugadores();
		for(Jugador j: jugadores) {
			textAreaJugadores.append(j.toString()+"\n");
		}
	}
}
