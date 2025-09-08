import java.io.IO;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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

    // PROCESAMIENTO únicamente del inicio de sesión
    public void manejarLogin(String correo, String contrasena){
        Usuario usuarioEncontrado = null;

        // Se busca al usuario en la lista cargada
        for (Usuario usuario : listaDeUsuarios){
            if (usuario.getCorreo().equalsIgnoreCase(correo)){ // Ignora mayúsculas/minúsculas en el correo al comparar
                usuarioEncontrado = usuario;
                break;
            }
        }

        // Validación del usuario y la contraseña
        if (usuarioEncontrado != null && usuarioEncontrado.verificarContrasena(contrasena)){

            // Si la validacion es correcta
            this.usuarioActual = usuarioEncontrado;
            System.out.println("Login EXITOSO. BIENVENIDO " + usuarioActual.getNombre());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Inicio de sesión EXITOSO");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenido, " + usuarioActual.getNombre());
            alert.showAndWait();
        } else {
            // Si la validacion falla
            System.out.println("ERROR: Credenciales inválidas.");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error de inicio de sesión");
            alert.setHeaderText(null);
            alert.setContentText("Correo o contraseña incorrectos.");
            alert.showAndWait();
        }
    }

    // PROCESO de registro de un nuevo usuario
    public void registrar(Usuario nuevoUsuario){
        // confirmar si no se está usando el correo
        boolean correoExiste = false;
        for (Usuario usuario : listaDeUsuarios){
            if (usuario.getCorreo().equalsIgnoreCase(nuevoUsuario.getCorreo()))
            {
                correoExiste = true;
                break;
            }
        }
    }

}
