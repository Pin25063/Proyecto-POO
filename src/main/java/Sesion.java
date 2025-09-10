public class Sesion {
    private final String idSesion;
    private final int estudianteId;
    private final int tutorId;
    private String materia;
    private String fechaHora;
    private EstadoSesion estado;
    //Constructor 
    public Sesion(String idSesion, int estudianteId, int  tutorId, String materia, String fechaHora, EstadoSesion estado) {
        this.idSesion = idSesion;
        this.estudianteId = estudianteId;
        this.tutorId = tutorId;
        this.materia = materia;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }
    //Metodos getters
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
    @Override
    public String toString() {
        return "Sesion{ " + idSesion + ", " + materia + ", " + estado + "}";
    }
}