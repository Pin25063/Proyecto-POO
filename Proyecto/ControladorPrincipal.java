import java.util.ArrayList;
import java.util.List;

public class ControladorPrincipal {
    
    // ATRIBUTOS
    private GestorDeDatos gestorDeDatos;
    private Usuario usuarioActual;
    private LoginVista loginVista;
    private ArrayList<Usuario> listaDeUsuarios;

    // CONSTRUCTOR: para inicializar el controlador
    public ControladorPrincipal(LoginVista loginVista) {
        this.gestorDeDatos = new GestorDeDatos();
        this.loginVista = loginVista;
    }
}
