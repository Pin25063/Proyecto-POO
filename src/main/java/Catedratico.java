import java.util.ArrayList;
import java.util.List;

public class Catedratico extends Usuario {

    // ATRIBUTOS (actualizados)
    private List<Curso> cursosACargo;

    public Catedratico(int idUsuario, String nombre, String correo, String contrasena) {
        super(idUsuario, nombre, correo, contrasena, Rol.CATEDRATICO);
        this.cursosACargo = new ArrayList<>();
    }

    // GETTERS Y SETTERS
    public List<Curso> getCursosACargo(){
        return new ArrayList<>(cursosACargo); // creamos nueva lista y copiamos los elementos de la lista original
        // recibe una copia para proteger la lista original
    }

    public void setCursosACargo(List<Curso> cursosACargo) {
        if (cursosACargo == null) {
            this.cursosACargo = new ArrayList<>();
        } else {
            this.cursosACargo = new ArrayList<>(cursosACargo);
        }
    }

    // METODO: para agregar cursos a un catedrático
    public void agregarCurso(Curso curso) {
        if (curso != null && !this.cursosACargo.contains(curso)) {
            this.cursosACargo.add(curso);
        }
    }

    // METODO: para eliminar un curso de la lista del catedrático por su código
    public boolean eliminarCurso(String codigoCurso) {
        if (codigoCurso == null || codigoCurso.trim().isEmpty()) {
            return false;
        }
        return cursosACargo.removeIf(c -> c.getCodigoCurso().equalsIgnoreCase(codigoCurso.trim()));
    }
    
    // Sobrescribe el método de la superclase para añadir información específica
    @Override
    public String getPerfil() {
        StringBuilder perfil = new StringBuilder(super.getPerfil());
        perfil.append("\n--- Información del Catedrático ---");
        perfil.append("\nCursos a cargo: ").append(cursosACargo.size());
        for (Curso curso : cursosACargo) {
            perfil.append("\n  - ").append(curso.toString());
        }
        return perfil.toString();
    }

    // METODO PERSISTENCIA: convierte el catedrático a formato CSV, incluyendo los códigos de sus cursos
    @Override
    public String toCSV() {
        StringBuilder csv = new StringBuilder(super.toCSV());
        csv.append(";"); // separador para los cursos

        if (!cursosACargo.isEmpty()) {
            List<String> codigosCursos = new ArrayList<>();
            for (Curso curso : cursosACargo) {
                codigosCursos.add(curso.getCodigoCurso());
            }
            csv.append(String.join(",", codigosCursos));
        }
        return csv.toString();
    }

    // Metodo para representar en texto a un catedrático
    @Override
    public String toString() {
        // Se usa el toString() de la superclase y agregamos la cantidad de cursos.
        return String.format("%s - Cursos: %d", super.toString(), cursosACargo.size());
    }
}