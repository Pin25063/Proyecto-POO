import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Horario {
    private DayOfWeek dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    // constructor principal

    public Horario (DayOfWeek dia, LocalTime horaInicio, LocalTime horaFin){
        if (horaInicio.isAfter(horaFin)){
            throw new IllegalArgumentException("La hora de inicio no puede ser posterior a la hora de fin.");
        }
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // constructor con strings

    public Horario(String dia, String horaInicio, String horaFin){
        this.dia = DayOfWeek.valueOf(dia.toUpperCase());
        this.horaInicio = LocalTime.parse(horaInicio, TIME_FORMAT);
        this.horaFin = LocalTime.parse(horaFin, TIME_FORMAT);


        if(this.horaInicio.isAfter(this.horaFin)){
            throw new IllegalArgumentException("La hora de inicio no puede ser posterior a la hora de fin.");    
        }
    }

    // Constructor con índice de día (1=LUNES, 2=MARTES, etc.)
    public Horario(int indiceDia, String horaInicio, String horaFin) {
        if (indiceDia < 1 || indiceDia > 7) {
            throw new IllegalArgumentException("Índice de día debe estar entre 1 y 7");
        }
        this.dia = DayOfWeek.of(indiceDia);
        this.horaInicio = LocalTime.parse(horaInicio, TIME_FORMAT);
        this.horaFin = LocalTime.parse(horaFin, TIME_FORMAT);
        
        if (this.horaInicio.isAfter(this.horaFin)) {
            throw new IllegalArgumentException("La hora de inicio no puede ser posterior a la hora de fin");
        }
    }
}
