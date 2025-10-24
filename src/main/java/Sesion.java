import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Sesion {
    private final String idSesion;
    private final int estudianteId;
    private final int tutorId;
    private String materia;
    private String fechaHora;
    private EstadoSesion estado;

    // Formato esperado para fechas: "yyyy-MM-dd HH:mm"
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
      Constructor con programacion defensiva.
     * @throws IllegalArgumentException 
     * si algún parámetro es inválido
     */
    public Sesion(String idSesion, int estudianteId, int tutorId, String materia, String fechaHora, EstadoSesion estado) {
        //  verificar nulls 
        if (idSesion == null || idSesion.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de sesión no puede ser nulo o vacío");
        }
        if (materia == null || materia.trim().isEmpty()) {
            throw new IllegalArgumentException("La materia no puede ser nula o vacía");
        }
        if (fechaHora == null || fechaHora.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha y hora no pueden ser nulas o vacías");
        }
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        
        // Validar IDs (deben ser positivos)
        if (estudianteId <= 0) {
            throw new IllegalArgumentException("El ID del estudiante debe ser positivo");
        }
        if (tutorId <= 0) {
            throw new IllegalArgumentException("El ID del tutor debe ser positivo");
        }
        
        // validar que estudiante y tutor no son  la misma persona
        if (estudianteId == tutorId) {
            throw new IllegalArgumentException("El estudiante y el tutor no pueden ser la misma persona");
        }
        
        // validar formato de fecha
        validarFormatoFecha(fechaHora);
        
        // la sesión no puede ser en el pasado (solo para nuevas sesiones)
        if (estado == EstadoSesion.PROGRAMADA || estado == EstadoSesion.AGENDADA) {
            validarFechaFutura(fechaHora);
        }
        this.idSesion = idSesion.trim();
        this.estudianteId = estudianteId;
        this.tutorId = tutorId;
        this.materia = materia.trim();
        this.fechaHora = fechaHora.trim();
        this.estado = estado;
    }

     //Valida que el formato de fecha sea correcto (yyyy-MM-dd HH:mm).
     
    private void validarFormatoFecha(String fecha) {
        try {
            LocalDateTime.parse(fecha, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "Formato de fecha inválido. Use: yyyy-MM-dd HH:mm (ejemplo: 2025-10-23 14:30)"
            );
        }
    }
    
    
    //Valida que la fecha sea en el futuro.

    private void validarFechaFutura(String fecha) {
        LocalDateTime fechaSesion = LocalDateTime.parse(fecha, FORMATO_FECHA);
        LocalDateTime ahora = LocalDateTime.now();
        
        if (fechaSesion.isBefore(ahora)) {
            throw new IllegalArgumentException("No se puede agendar una sesión en el pasado");
        }
    }
    
    // getters
    public String getIdSesion() { 
        return idSesion; 
    }
    public int getEstudianteId() { 
        return estudianteId; 
    }
    public int getTutorId() { 
        return tutorId; 
    }
    public String getMateria() { 
        return materia; 
    }
    public String getFechaHora() { 
        return fechaHora; 
    }
    public EstadoSesion getEstado() { 
        return estado; 
    }
    public void setEstado(EstadoSesion estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return "Sesion{ " + idSesion + ", " + materia + ", " + estado + "}";
    }
}