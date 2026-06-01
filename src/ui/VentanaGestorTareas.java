package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;

import modelo.GestorTareas;
import modelo.Tarea;
import modelo.ValidacionTareaException;

import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;

public class VentanaGestorTareas {

	private JFrame frame;
	private JTable tablaTareas;
	private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnSalir;
    private JTextField tfTitulo;
    private JTextField tfDescripcion;
    private JTextField tfFecha;
    private JButton btnGuardar;
    private JButton btnCargar;
    

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private GestorTareas gestor;

    private DefaultTableModel modeloTabla;
    private int filaSeleccionada = -1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaGestorTareas window = new VentanaGestorTareas();
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
	public VentanaGestorTareas() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		gestor = new GestorTareas();
		frame = new JFrame();
		frame.setSize(550,580);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Gestión de tareas");
		
		JPanel panelNorte = new JPanel();
		frame.getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Título:");
		panelNorte.add(lblNewLabel);
		
		tfTitulo = new JTextField();
		panelNorte.add(tfTitulo);
		tfTitulo.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Descripción:");
		panelNorte.add(lblNewLabel_1);
		
		tfDescripcion = new JTextField();
		panelNorte.add(tfDescripcion);
		tfDescripcion.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Fecha (dd/MM/yyyy):");
		panelNorte.add(lblNewLabel_2);
		
		tfFecha = new JTextField();
		panelNorte.add(tfFecha);
		tfFecha.setColumns(10);
		
		JPanel panelCentro = new JPanel();
		frame.getContentPane().add(panelCentro, BorderLayout.CENTER);
		
		JButton btnAgregarTarea = new JButton("Agregar tarea");
		btnAgregarTarea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarTarea();
			}
		});
		panelCentro.add(btnAgregarTarea);
		
		JButton btnEliminarTarea = new JButton("Eliminar tarea");
		btnEliminarTarea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarTareaSeleccionada();
			}
		});
		panelCentro.add(btnEliminarTarea);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarCSV();
			}
		});
		panelCentro.add(btnGuardar);
		
		JButton btnCargar = new JButton("Cargar");
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarCSV();
			}
		});
		panelCentro.add(btnCargar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmacion = JOptionPane.showConfirmDialog(frame,
		                "¿Está seguro de que desea salir?",
		                "Confirmar salida",
		                JOptionPane.YES_NO_OPTION,
		                JOptionPane.QUESTION_MESSAGE);

		        if (confirmacion == JOptionPane.YES_OPTION) {
		            System.exit(0);
		        }
			}
		});
		panelCentro.add(btnSalir);
		
		JPanel panelSur = new JPanel();
		panelSur.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.getContentPane().add(panelSur, BorderLayout.SOUTH);
		panelSur.setLayout(new GridLayout(0, 1, 1, 1));
		
		JScrollPane scrollPane = new JScrollPane();
		panelSur.add(scrollPane);
		
		tablaTareas = new JTable();
		tablaTareas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPane.setViewportView(tablaTareas);
		inicializarTabla();
		
	}

	private void inicializarTabla() {
		modeloTabla = new DefaultTableModel(
                new String[]{"Título", "Descripción", "Fecha vencimiento"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // mejor editar desde los JTextField
            }
        };
        

        tablaTareas.setModel(modeloTabla);
        tablaTareas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaTareas.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                cargarTareaSeleccionadaEnFormulario();
            }
        });
	}
	
    private void cargarTareaSeleccionadaEnFormulario() {
        int filaVista = tablaTareas.getSelectedRow();

        if (filaVista != -1) {
            filaSeleccionada = filaVista;
            tfTitulo.setText(modeloTabla.getValueAt(filaVista, 0).toString());
            tfDescripcion.setText(modeloTabla.getValueAt(filaVista, 1).toString());
            tfFecha.setText(modeloTabla.getValueAt(filaVista, 2).toString());
        }
    }

	private void cargarCSV() {
		JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Cargar tareas desde CSV");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv"));
        chooser.setCurrentDirectory(new File("."));

        int resultado = chooser.showOpenDialog(frame);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();

            try (BufferedReader br = Files.newBufferedReader(archivo.toPath())) {
                gestor = new GestorTareas(); // reinicia la lista actual
                String linea;
                boolean primeraLinea = true;

                while ((linea = br.readLine()) != null) {
                    if (primeraLinea) {
                        primeraLinea = false;
                        continue; // saltar cabecera
                    }

                    if (linea.trim().isEmpty()) {
                        continue;
                    }

                    String[] campos = parsearLineaCSV(linea);

                    if (campos.length >= 3) {
                        String titulo = campos[0];
                        String descripcion = campos[1];
                        LocalDate fecha = LocalDate.parse(campos[2], FORMATTER);

                        gestor.agregarTarea(new Tarea(titulo, descripcion, fecha));
                    }
                }

                actualizarListaTareas();
                limpiarFormulario();
                
                JOptionPane.showMessageDialog(chooser,
                        "Datos cargados correctamente desde:\n" + archivo.getAbsolutePath(),
                        "Cargar CSV",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(chooser,
                        "Error al leer el archivo: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(chooser,
                        "Hay fechas con formato inválido en el CSV. Deben estar en formato dd/MM/yyyy.",
                        "Error de formato",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
	}
	
    private void limpiarFormulario() {
        tfTitulo.setText("");
        tfDescripcion.setText("");
        tfFecha.setText("");
        tablaTareas.clearSelection();
        filaSeleccionada = -1;
        tfTitulo.requestFocus();
    }
    
    private void actualizarListaTareas() {
        modeloTabla.setRowCount(0);
        ArrayList<Tarea> tareas = gestor.obtenerTodasTareas();

        for (Tarea t : tareas) {
            modeloTabla.addRow(new Object[]{
                    t.getTitulo(),
                    t.getDescripcion(),
                    t.getFechaFormateada()
            });
        }
    }
    
    private String[] parsearLineaCSV(String linea) {
        String[] campos;
    	campos = linea.split(",");
        return campos;
    }
    
	private void agregarTarea() {
		try {
            String titulo = tfTitulo.getText().trim();
            if (titulo.isEmpty()) {
                throw new ValidacionTareaException("El título es obligatorio.");
            }
            if (titulo.length() < 3) {
                throw new ValidacionTareaException("El título debe tener al menos 3 caracteres.");
            }

            String descripcion = tfDescripcion.getText().trim();
            if (descripcion.isEmpty()) {
                throw new ValidacionTareaException("La descripción es obligatoria.");
            }

            String fechaStr = tfFecha.getText().trim();
            if (fechaStr.isEmpty()) {
                throw new ValidacionTareaException("La fecha de vencimiento es obligatoria.");
            }

            LocalDate fechaVencimiento;
            try {
                fechaVencimiento = LocalDate.parse(fechaStr, FORMATTER);
            } catch (DateTimeParseException e) {
                throw new ValidacionTareaException("Formato de fecha inválido. Use dd/MM/yyyy.");
            }

            if (fechaVencimiento.isBefore(LocalDate.now())) {
                throw new ValidacionTareaException("La fecha de vencimiento no puede ser anterior.");
            }

            Tarea tarea = new Tarea(titulo, descripcion, fechaVencimiento);

            if (filaSeleccionada == -1) {
                gestor.agregarTarea(tarea);
                JOptionPane.showMessageDialog(frame,
                        "Tarea agregada con éxito.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (gestor.modificarTarea(filaSeleccionada, tarea)) {
                    JOptionPane.showMessageDialog(frame,
                            "Tarea modificada con éxito.",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "No se ha podido modificar la tarea.",
                            "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            limpiarFormulario();
            actualizarListaTareas();
        } catch (ValidacionTareaException ex) {
            JOptionPane.showMessageDialog(frame,
                    ex.getMessage(),
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
        }
		
	}
	
    private void guardarCSV() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar tareas en CSV");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv"));
        chooser.setCurrentDirectory(new File("."));

        int resultado = chooser.showSaveDialog(frame);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();

            if (!archivo.getName().toLowerCase().endsWith(".csv")) {
                archivo = new File(archivo.getAbsolutePath() + ".csv");
            }

            try (BufferedWriter bw = Files.newBufferedWriter(archivo.toPath())) {
                bw.write("titulo,descripcion,fecha");
                bw.newLine();

                for (Tarea t : gestor.obtenerTodasTareas()) {
                    bw.write(t.getTitulo() + "," +
                            t.getDescripcion() + "," +
                            t.getFechaFormateada());
                    bw.newLine();
                }

                JOptionPane.showMessageDialog(frame,
                        "Datos guardados correctamente en:\n" + archivo.getAbsolutePath(),
                        "Guardar CSV",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame,
                        "Error al guardar el archivo: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarTareaSeleccionada() {
        int fila = tablaTareas.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(frame,
                    "Seleccione una tarea de la tabla.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        } else {
        	int confirmacion = JOptionPane.showConfirmDialog(frame,
	                "¿Desea eliminar esta tarea?",
	                "Confirmar borrado",
	                JOptionPane.YES_NO_OPTION,
	                JOptionPane.QUESTION_MESSAGE);

	        if (confirmacion == JOptionPane.YES_OPTION) {
	            String titulo = modeloTabla.getValueAt(fila, 0).toString();
	            boolean eliminado = gestor.eliminarTarea(titulo);

	            if (eliminado) {
	                actualizarListaTareas();
	                limpiarFormulario();
	                JOptionPane.showMessageDialog(frame,
	                        "Tarea eliminada correctamente.",
	                        "Éxito",
	                        JOptionPane.INFORMATION_MESSAGE);
	            }
	        }
        }

    }

}
