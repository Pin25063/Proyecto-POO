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
            // Intentar cargar las sesiones desde el archivo CSV
            List<Sesion> sesiones = gestor.cargarSesiones();
            // Verificar si se cargaron correctamente las sesiones
            if (sesiones.isEmpty()) {
                System.out.println("No se han cargado sesiones.");
            } else {
                System.out.println("Sesiones cargadas desde el archivo CSV:");
                // Imprimir las sesiones cargadas
                for (Sesion sesion : sesiones) {
                    System.out.println(sesion.toString());
                }
            }
        } catch (IOException e) {
            // Si ocurre un error al leer el archivo, se muestra el mensaje
            System.out.println("Ocurrió un error al cargar los usuarios: " + e.getMessage());
        }
    }
}