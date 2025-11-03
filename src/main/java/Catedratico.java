import java.util.ArrayList;
import java.util.List;


public class Catedratico extends Usuario {

    // ATRIBUTOS: se cambió a String la lista para que coincida con la 
    // e la clase Tutor y facilite la carga desde el CSV.
    private List<String> cursosACargo;

    // CONSTRUCTOR
    public Catedratico(int idUsuario, String nombre, String correo, String contrasena, List<String> cursos) {
        super(idUsuario, nombre, correo, contrasena, Rol.CATEDRATICO);

        // se agrega programación defensiva
        if (cursos == null) {
            this.cursosACargo = new ArrayList<>();
        } else {
            this.cursosACargo = new ArrayList<>(cursos);
        }
    }

    // GETTERS Y SETTERS
    public List<String> getCursosACargo(){
        return new ArrayList<>(cursosACargo); // creamos nueva lista y copiamos los elementos de la lista original
        // recibe una copia para proteger la lista original
    }

    public void setCursosACargo(List<String> cursosACargo) {
        if (cursosACargo == null) {
            this.cursosACargo = new ArrayList<>();
        } else {
            this.cursosACargo = new ArrayList<>(cursosACargo);
        }
    }

    // METODO: para agregar cursos a un catedrático
    public void agregarCurso(String curso) {
        if (curso != null && !curso.trim().isEmpty() && !this.cursosACargo.contains(curso.trim())) {
            this.cursosACargo.add(curso.trim());
        }
    }

    // METODO: para eliminar un curso de la lista del catedrático por su nombre
    public boolean eliminarCurso(String curso) {
        if (curso == null || curso.trim().isEmpty()) {
            return false;
        }
        return cursosACargo.removeIf(c -> c.equalsIgnoreCase(curso.trim()));
    }
    
    // Sobrescribe el método de la superclase para añadir información específica
    @Override
    public String getPerfil() {
        StringBuilder perfil = new StringBuilder(super.getPerfil());
        perfil.append("\n--- Información del Catedrático ---");
        perfil.append("\nCursos a cargo: ").append(cursosACargo.size());

        // Actualizar bucle para iterar sobre Strings
        for (String curso : cursosACargo) {
            perfil.append("\n  - ").append(curso);
        }
        return perfil.toString();
    }

    // METODO PERSISTENCIA: convierte el catedrático a formato CSV, incluyendo los códigos de sus cursos
    @Override
    public String toCSV() {

        // Se necesita insertar las materias antes del final por lo que se reconstruye el csv base aquí
        StringBuilder csv = new StringBuilder();
        csv.append(getIdUsuario()).append(";")
            .append(getNombre()).append(";")
            .append(getCorreo()).append(";")
            .append(getContrasena()).append(";")
            .append(getRol()).append(";"); // separador para los cursos

        // Se une la lista de string con comas, al igual que en tutor
        if (!cursosACargo.isEmpty()) {
            csv.append(String.join(",", cursosACargo));
        }

        // Se le agrega el separador final para la columna de tarifa para mantener la estructura del CSV consistente con Tutor
        csv.append(";");
        
        return csv.toString();
    }

    // Metodo para representar en texto a un catedrático
    @Override
    public String toString() {
        // Se usa el toString() de la superclase y agregamos la cantidad de cursos.
        return String.format("%s - Cursos: %d", super.toString(), cursosACargo.size());
    }
}