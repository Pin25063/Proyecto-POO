public class Curso {
    private String nombreCurso;
    private int codigoCurso;

    //constructor

    public Curso(int codigoCurso, String nombreCurso) {
        this.codigoCurso = codigoCurso;
        this.nombreCurso = nombreCurso;
    }

    //constructor adicional solo con codigo 

    public Curso(int codigoCurso) {
        this.codigoCurso = codigoCurso;
        this.nombreCurso = "";
    }

    //getters 
    public String getNombreCurso() {
        return nombreCurso;
    }
    public int getCodigoCurso(){
        return codigoCurso; 
    }

    //setters

    public void setNombreCurso(String nombreCurso){
        this.nombreCurso = nombreCurso;
    }
    public void setCodigoCrusto(int codigoCurso){
        this.codigoCurso = codigoCurso;
    }

    //Metodo para validar si el curso esta completo

    public boolean esValido(){
        return codigoCurso > 0 && nombreCurso != null && !nombreCurso.trim().isEmpty();
    }


    // Obtener representacion corta del curso
    public String getCodigoNombre(){
        return codigoCurso + " - " + nombreCurso;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "codigo=" + codigoCurso +
                ", nombre='" + nombreCurso + '\'' +
                '}';
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(codigoCurso);
    }
}
