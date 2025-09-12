import java.util.ArrayList;

public class Catedratico extends Usuario {
    private ArrayList<Curso> cursosACargo;

    public Catedratico(int idUsuario, String nombre, String correo, String contrasena, Rol rol) {
        super(idUsuario, nombre, correo, contrasena, rol);
    }

    public ArrayList<Curso> getCursos(){
        return this.cursosACargo;
    }

    public void asignarTutoria(Estudiante estudiante, Tutor tutor, Curso curso){

    }
}