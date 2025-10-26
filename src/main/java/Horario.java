import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Horario {
    
    // ATRIBUTOS
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
    
    // CONSTRUCTOR
    public Horario(String diaSemana, LocalTime horaInicio, LocalTime horaFin) {
        // PROGRAMACIÓN DEFENSIVA
        if (diaSemana == null || diaSemana.trim().isEmpty()) {
            throw new IllegalArgumentException("El día de la semana no puede estar vacío");
        }
        if (horaInicio == null || horaFin == null) {
            throw new IllegalArgumentException("Las horas de inicio y fin no pueden ser nulas.");
        }
        if (horaFin.isBefore(horaInicio)) {
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio.");
        }
        
        this.diaSemana = diaSemana.trim();
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
    
    // GETTERS
    public String getDiaSemana() { return diaSemana; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }

    // METODO: Verifica si un horario se choca con otro.
    public boolean seChocaCon(Horario otro) {
        if (otro == null || !this.diaSemana.equalsIgnoreCase(otro.diaSemana)) {
            return false;
        }
        return this.horaInicio.isBefore(otro.horaFin) && this.horaFin.isAfter(otro.horaInicio);
    }

    // METODO DE PERSISTENCIA
    public String toCSV() {
        return String.format("%s;%s;%s", diaSemana, horaInicio.format(FORMATO_HORA), horaFin.format(FORMATO_HORA));
        // cada %s es un marcador de posición que es reemplazado por los Strings de abajo
    }
    
    // METODO DE PERSISTENCIA
    public static Horario fromCSV(String csv) {
        try {
            String[] partes = csv.split(";"); // corta la cadena CSV de entrada en cada ";", que devuelve un array de Strings
            if (partes.length != 3) {
                throw new IllegalArgumentException("Formato CSV de horario inválido.");
            }
            return new Horario(partes[0], LocalTime.parse(partes[1], FORMATO_HORA), LocalTime.parse(partes[2], FORMATO_HORA));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al parsear horario desde CSV: " + csv, e);
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s de %s a %s", diaSemana, horaInicio.format(FORMATO_HORA), horaFin.format(FORMATO_HORA));
    }
    
    // Al método predeterminado "equals" que dice que dos objetos son iguales solo si son exactamente el mismo objeto en memoria.
    // se le hace OVERRIDE para agregar funcionalidades
    @Override
    public boolean equals(Object obj) {
        // Comprobar si es el mismo objeto en memoria.
        if (this == obj) return true;
        // Comprobar si el otro objeto es nulo o de una clase diferente.
        if (obj == null || getClass() != obj.getClass()) return false;

        // Convertir el objeto y comparar el atributo clave.
        Horario otro = (Horario) obj;
        return diaSemana.equalsIgnoreCase(otro.diaSemana) && horaInicio.equals(otro.horaInicio) && horaFin.equals(otro.horaFin);
    }
}