import java.util.ArrayList;
import java.util.List;

public class Catedratico extends Usuario {
    private ArrayList<Curso> cursosACargo;
    private ArrayList<String> materias;

    // Constructor original
    public Catedratico(int idUsuario, String nombre, String correo, String contrasena, Rol rol) {
        super(idUsuario, nombre, correo, contrasena, rol);
        this.cursosACargo = new ArrayList<>();
        this.materias = new ArrayList<>();
    }
    
    // Constructor nuevo con materias
    public Catedratico(int idUsuario, String nombre, String correo, String contrasena, Rol rol, List<String> materias) {
        super(idUsuario, nombre, correo, contrasena, rol);
        this.cursosACargo = new ArrayList<>();
        this.materias = new ArrayList<>(materias);
    }
    
    // Constructor con ArrayList
    public Catedratico(int idUsuario, String nombre, String correo, String contrasena, Rol rol, ArrayList<String> materias) {
        super(idUsuario, nombre, correo, contrasena, rol);
        this.cursosACargo = new ArrayList<>();
        this.materias = materias != null ? materias : new ArrayList<>();
    }

    public ArrayList<Curso> getCursosACargo(){
        return this.cursosACargo != null ? this.cursosACargo : new ArrayList<>();
    }
    
    public ArrayList<String> getMaterias() {
        return this.materias != null ? this.materias : new ArrayList<>();
    }

    public void asignarTutoria(Estudiante estudiante, Tutor tutor, Curso curso){
        // Implementación pendiente
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Materias: " + materias;
    }
}