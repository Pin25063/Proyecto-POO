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

    


}
