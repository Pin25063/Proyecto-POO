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




}
