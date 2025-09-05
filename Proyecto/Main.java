import java.io.IOException;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        // Crear una instancia de GestorDeDatos
        GestorDeDatos gestor = new GestorDeDatos();
        //Codigo de prueba de archivo CSV
        try {
            // Intentar cargar los usuarios desde el archivo CSV
            List<Usuario> usuarios = gestor.cargarUsuarios();

            // Verificar si se cargaron correctamente los usuarios
            if (usuarios.isEmpty()) {
                System.out.println("No se han cargado usuarios.");
            } else {
                System.out.println("Usuarios cargados desde el archivo CSV:");
                // Imprimir los usuarios cargados
                for (Usuario usuario : usuarios) {
                    System.out.println(usuario.toString());
                }
            }
        } catch (IOException e) {
            // Si ocurre un error al leer el archivo, se muestra el mensaje
            System.out.println("Ocurrió un error al cargar los usuarios: " + e.getMessage());
        }
    }
}
