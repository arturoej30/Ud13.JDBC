package modelo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Tarea{
    private String titulo;
    private String descripcion;
    private LocalDate fechaVencimiento;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Tarea(String titulo, String descripcion, LocalDate fechaVencimiento) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    // Devuelve la fecha formateada como String
    public String getFechaFormateada() {
        return fechaVencimiento.format(FORMATTER);
    }

    @Override
    public String toString() {
        return "Título: " + titulo + " | Descripción: " + descripcion + " | Vence: " + getFechaFormateada();
    }

    // Para guardar en CSV (opcional)
    public String toCSV() {
        return titulo + ";" + descripcion + ";" + getFechaFormateada();
    }

    // Para crear desde CSV (opcional)
    public static Tarea fromCSV(String linea) {
        String[] partes = linea.split(";");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Formato CSV inválido");
        }
        LocalDate fecha = LocalDate.parse(partes[2], FORMATTER);
        return new Tarea(partes[0], partes[1], fecha);
    }

}