public class Sesion {
    private final String idSesion;
    private final String estudianteId;
    private final String tutorId;
    private String materia;
    private String fechaHora;
    private EstadoSesion estado;
    //Constructor 
    public Sesion(String idSesion, String estudianteId, String tutorId,
                  String materia, String fechaHora, EstadoSesion estado) {
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
    public String getEstudianteId() { 
        return estudianteId; 
    }
    public String getTutorId() { 
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
}