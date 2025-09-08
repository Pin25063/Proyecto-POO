import java.io.IO;
import java.util.ArrayList;
import java.util.List;

public class ControladorPrincipal {
    
    // ATRIBUTOS
    private GestorDeDatos gestorDeDatos;
    private Usuario usuarioActual;
    private LoginVista loginVista;
    private List<Usuario> listaDeUsuarios;

    // CONSTRUCTOR: para inicializar el controlador
    public ControladorPrincipal(LoginVista loginVista) {
        this.gestorDeDatos = new GestorDeDatos();
        this.loginVista = loginVista;
        // Se cargan los usuarios una sola vez para evitar múltiples lecturas del archivo
        try {
            this.listaDeUsuarios = gestorDeDatos.cargarUsuarios();
        } catch (Exception e) {
            System.out.println("Error al cargar los usuarios: " + e.getMessage());
            // Se crea una lista vacía en caso de error
            this.listaDeUsuarios = new ArrayList<>(); 
        }
    }

    // Procesamiento únicamente del inicio de sesión
    public void manejarLogin(String correo, String contrasena){
        Usuario usuarioEncontrado = null;

        // Se busca al usuario en la lista cargada
        for (Usuario usuario : listaDeUsuarios){
            if (usuario.getCorreo().equalsIgnoreCase(correo)){ // Ignora mayúsculas/minúsculas en el correo al comparar
                usuarioEncontrado = usuario;
                break;
            }
        }
    }
}
