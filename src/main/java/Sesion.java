import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Sesion {
    private final String idSesion;
    private final int estudianteId;
    private final int tutorId;
    private String materia;
    // CAMBIO: Se reemplaza fechaHora por dos campos separados
    private String fecha;
    private String hora;
    private EstadoSesion estado;

    // Formato esperado para fechas: "HH:mm dd/MM/yy"
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");

    
    // CAMBIO: Constructor actualizado para aceptar fecha y hora
    public Sesion(String idSesion, int estudianteId, int tutorId, String materia, String fecha, String hora, EstadoSesion estado) {
        //  verificar nulls 
        if (idSesion == null || idSesion.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de sesión no puede ser nulo o vacío");
        }
        if (materia == null || materia.trim().isEmpty()) {
            throw new IllegalArgumentException("La materia no puede ser nula o vacía");
        }
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha no puede ser nula o vacía");
        }
        if (hora == null || hora.trim().isEmpty()) {
            throw new IllegalArgumentException("La hora no puede ser nula o vacía");
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

        this.idSesion = idSesion.trim();
        this.estudianteId = estudianteId;
        this.tutorId = tutorId;
        this.materia = materia.trim();
        this.fecha = fecha.trim();
        this.hora = hora.trim();
        this.estado = estado;
        
        if (this.estado != EstadoSesion.PENDIENTE) {
            // validar formato de fecha
            validarFormatoFecha(getFechaHoraCombinada());

            // la sesión no puede ser en el pasado (solo para nuevas sesiones)
            if (this.estado == EstadoSesion.PROGRAMADA || this.estado == EstadoSesion.AGENDADA) {
                validarFechaFutura(getFechaHoraCombinada());
            }
        }
    }

    //Valida que el formato de fecha sea correcto (HH:mm dd/MM/yy).
    private void validarFormatoFecha(String fechaHora) {
        try {
            LocalDateTime.parse(fechaHora, FORMATO_FECHA_HORA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "Formato de fecha inválido. Use: HH:mm dd/MM/yy (ejemplo: 14:30 23/10/25)"
            );
        }
    }
    
    
    //Valida que la fecha sea en el futuro.
    private void validarFechaFutura(String fechaHora) {
        LocalDateTime fechaSesion = LocalDateTime.parse(fechaHora, FORMATO_FECHA_HORA);
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
    public String getFecha() { 
        return fecha; 
    }
    public String getHora() { 
        return hora; 
    }
    
    //Devuelve la fecha y hora en el formato estándar de la aplicación
    public String getFechaHoraCombinada() {
        return this.hora + " " + this.fecha;
    }
    
    public EstadoSesion getEstado() { 
        return estado; 
    }
    // programacion defensiva en setters
    public void setEstado(EstadoSesion estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        this.estado = estado;
    }
    
    /*Logica de agendamiento: 
     * Verifica si esta sesion tiene conflicto de horario con otra sesion
     * Dos sesiones tienen conflicto si:
     * - Comparten mismo tutor o mismo estudiante
     * - Los horarios se traslapan
     */

    public boolean tieneConflicto(Sesion otra){
        // Verificar si comparten tutor o estudiante
        boolean compartenTutor = this.tutorId == otra.tutorId;
        boolean compartenEstudiante = this.estudianteId == otra.estudianteId;

        if (!compartenTutor && !compartenEstudiante){
            return false; // no hay conflicto si no comparten tutor ni estudiante
        }       
        // CAMBIO: Compara fecha y hora por separado
        return this.fecha.equals(otra.getFecha()) && this.hora.equals(otra.getHora());
    }

        /*
         * Logica de agendamiento:
         * verifica si la sesion esta disponible para agendar
         * esta disponible si:
         * - Esta en estado PROGRAMADA o AGENDADA
         * - La fecha y hora es en el futuro
         */
        
    public boolean estaDisponibleParaAgendar() {
        if (this.estado != EstadoSesion.PROGRAMADA && this.estado != EstadoSesion.AGENDADA) {
            return false;
        }
        
        try {
            LocalDateTime fechaSesion = LocalDateTime.parse(getFechaHoraCombinada(), FORMATO_FECHA_HORA);
            LocalDateTime ahora = LocalDateTime.now();
            return fechaSesion.isAfter(ahora);
        } catch (DateTimeParseException e) {
            return false; // Si la fecha es inválida, no está disponible
        }
    }
    
    /*
     * Logica de Agendamiento:
     * Calcula las horas que faltan para la sesion
     * Util para validaciones y recordatorios
     */

    public long horasHastaSesion() {
        try {
            LocalDateTime fechaSesion = LocalDateTime.parse(getFechaHoraCombinada(), FORMATO_FECHA_HORA);
            LocalDateTime ahora = LocalDateTime.now();
            
            if (fechaSesion.isBefore(ahora)) {
                return -1; // La sesión ya pasó
            }
            
            return java.time.Duration.between(ahora, fechaSesion).toHours();
        } catch (DateTimeParseException e) {
            return -1;
        }
    }

    /*
     * Logica de Agendamiento: 
     * Verifica si la sesion puede ser cancelada.
     * Se puede cancelar si:
     * - No esta completada o ya esta cancelada
     * - Faltan almenos 2 horas para que inicie
     */

    public boolean puedeSerCancelada() {
        if (this.estado == EstadoSesion.COMPLETADA || this.estado == EstadoSesion.CANCELADA) {
            return false;
        }
        
        long horasRestantes = horasHastaSesion();
        return horasRestantes >= 2; // Mínimo 2 horas de anticipación
    }
    
    @Override
    public String toString() {
        return "Sesion{" +
            "id=" + idSesion +
            ", materia='" + materia + '\'' +
            // CAMBIO: Se usa fecha y hora
            ", fecha=" + fecha +
            ", hora=" + hora +
            ", estado=" + estado +
            ", estudiante=" + estudianteId +
            ", tutor=" + tutorId +
            '}';
    }
}