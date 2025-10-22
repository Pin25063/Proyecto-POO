import java.util.ArrayList;

public class Catedratico extends Usuario {
    private ArrayList<String> materias;

    public Catedratico(int idUsuario, String nombre, String correo, String contrasena, ArrayList<String> materias) {
        super(idUsuario, nombre, correo, contrasena, Rol.CATEDRATICO);
        this.materias = materias;
    }

    public ArrayList<String> getMaterias(){
        return this.materias;
    }

    public void asignarTutoria(Estudiante estudiante, Tutor tutor, Curso curso){

    }
}