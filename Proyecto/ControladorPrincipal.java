import java.util.ArrayList;
import java.util.List;

public class ControladorPrincipal {
    
    // ATRIBUTOS
    private GestorDeDatos gestorDeDatos;
    private Usuario usuarioActual;
    private LoginVista loginVista;
    private List<Usuario> listaDeUsuarios;
     private List<Sesion> listaDeSesiones;

    // CONSTRUCTOR: para inicializar el controlador
    public ControladorPrincipal(LoginVista loginVista) {
        this.gestorDeDatos = new GestorDeDatos();
        this.loginVista = loginVista;
        // se tienen que cargar los usuarios una sola vez para no leer muchas veces el archivo
        try {
            this.listaDeUsuarios = gestorDeDatos.cargarUsuarios();
        } catch (Exception e) {
            System.out.println("Error al cargar los usuarios: " + e.getMessage());
            // Se crea una lista vacía en caso de error
            this.listaDeUsuarios = new ArrayList<>(); 
        }
        // Cargar sesiones una sola vez
        try {
            this.listaDeSesiones = gestorDeDatos.cargarSesiones();
        } catch (Exception e) {
            System.out.println("Error al cargar las sesiones: " + e.getMessage());
            this.listaDeSesiones = new ArrayList<>();
        }
    }

    // PROCESAMIENTO únicamente del inicio de sesión
    public void manejarLogin(String correo, String contrasena){
        Usuario usuarioEncontrado = null;

        // Se busca al usuario en la lista cargada
        for (Usuario usuario : listaDeUsuarios){
            if (usuario.getCorreo().equalsIgnoreCase(correo)){ // Ignora mayúsculas y minúsculas en el correo al comparar
                usuarioEncontrado = usuario;
                break;
            }
        }

        // Validación del usuario y la contraseña
        if (usuarioEncontrado != null && usuarioEncontrado.verificarContrasena(contrasena)){

            // Si la validacion es correcta
            this.usuarioActual = usuarioEncontrado;
            System.out.println("Login EXITOSO. BIENVENIDO " + usuarioActual.getNombre());
            loginVista.mostrarInfo("Inicio de sesión EXITOSO", "Bienvenido, " + usuarioActual.getNombre());
            // loginVista.IrAPerfil(this.usuarioActual); // Ir a la vista del perfil del usuario
        } else {
            // Si la validacion falla
            System.out.println("ERROR: Credenciales inválidas.");
            loginVista.mostrarError("Error de autenticación", "Correo o contraseña no válidos");
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

        if (correoExiste){
            // notificar que el correo ya está en uso sin revelar información delicada
            System.out.println("Error, información no válida para registrar. Ingrese otro correo.");
            loginVista.mostrarError("Error", "Correo no válido.");
        } else {
            // si es un nuevo usuario, agregarlo a la lista y se guarda
            this.listaDeUsuarios.add(nuevoUsuario);

            // Pasar persistencia al gestor de datos
            // gestorDeDatos.guardarUsuarios(this.listaDeUsuarios);

            loginVista.mostrarInfo("Registro exitoso", "Usuario registrado correctamente. Ahora puede iniciar sesión"); 
            
            // limpiar los campos de la vista
            loginVista.limpiarCampos();

        }
    }

    //Flujo Estudiante 

    public List<Usuario> manejarBusquedaTutor(String materia) {
        List<Usuario> tutores = new ArrayList<>();
        for (Usuario u : listaDeUsuarios) {
            if (u.getRol() == Rol.TUTOR) {
                tutores.add(u);
            }
        }
        return tutores;
    }

    //GENDAMIENTO DE SESIÓN 
    
    public Sesion manejarAgendamientoSesion(int estudianteId, int tutorId, String materia, String fechaHora) {
        System.out.println("Agendar (stub) est=" + estudianteId + " tut=" + tutorId + " mat=" + materia + " fh=" + fechaHora);
        return null;
    }

    //HELPERS 

    private Usuario buscarUsuarioPorId(int id) {
        for (Usuario u : listaDeUsuarios) if (u.getIdUsuario() == id) return u;
        return null;
    }

}
