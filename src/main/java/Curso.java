public class Curso {

    // ATRIBUTOS
    private String nombreCurso;    
    private String codigoCurso; 

    // CONSTRUCTOR
    public Curso(String nombreCurso, String codigoCurso) {
        
        // Programación defensiva
        if (nombreCurso == null || nombreCurso.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del curso no puede estar vacío.");
        }
        if (codigoCurso == null || codigoCurso.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del curso no puede estar vacío.");
        }

        this.nombreCurso = nombreCurso.trim();
        this.codigoCurso = codigoCurso.trim().toUpperCase(); // Códigos en mayúsculas por convención
    }

    // GETTERS Y SETTERS

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        if (nombreCurso == null || nombreCurso.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del curso no puede estar vacío.");
        }
        this.nombreCurso = nombreCurso.trim();
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        if (codigoCurso == null || codigoCurso.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del curso no puede estar vacío.");
        }
        this.codigoCurso = codigoCurso.trim().toUpperCase();
    }

    // METODO PERSISTENCIA: para convertir el curso a formato CSV para guardarlo en un archivo
    public String toCSV() {
        return String.format("%s;%s", codigoCurso, nombreCurso);
        // cada %s es un marcador de posición que es reemplazado por los Strings de abajo
    }

    // METODO PERSISTENCIA: para crear un objeto Curso a partir de texto en CSV
    public static Curso fromCSV(String csv) {
        try {
            if (csv == null || csv.trim().isEmpty()) { // .trim() elimina los espacios al inicio y al final de un String
                throw new IllegalArgumentException("La línea CSV no puede estar vacía.");
            }
            String[] partes = csv.split(";"); // corta la cadena CSV de entrada en cada ";", que devuelve un array de Strings
            if (partes.length < 2) {
                throw new IllegalArgumentException("Formato CSV de curso inválido. Se esperan 2 campos.");
            }
            // partes[0] es el código, partes[1] es el nombre
            return new Curso(partes[1], partes[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al parsear curso desde CSV: " + csv, e);
        }
    }

    
    @Override
    public String toString() {
        return String.format("%s: %s", codigoCurso, nombreCurso);
    }

    // Al método predeterminado "equals" que dice que dos objetos son iguales solo si son exactamente el mismo objeto en memoria.
    // se le hace OVERRIDE para agregar funcionalidades
    @Override
    public boolean equals(Object obj) {
        // Comprobar si es el mismo objeto en memoria.
        if (this == obj) return true;
        // Comprobar si el otro objeto es nulo o de una clase diferente.
        if (obj == null || getClass() != obj.getClass()) return false;
        
        // Convertir el objeto y comparar el atributo clave.
        Curso otro = (Curso) obj;
        return codigoCurso.equalsIgnoreCase(otro.codigoCurso);
    }

}
