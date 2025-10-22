import java.util.ArrayList;

public class Catedratico extends Usuario {
    private ArrayList<Curso> cursosACargo;

    public Catedratico(int idUsuario, String nombre, String correo, String contrasena) {
        super(idUsuario, nombre, correo, contrasena, Rol.CATEDRATICO);
    }

    public ArrayList<Curso> getCursos(){
        return this.cursosACargo;
    }

    public void asignarTutoria(Estudiante estudiante, Tutor tutor, Curso curso){

    }
}