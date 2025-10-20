import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ControladorPrincipal {
    
    // ATRIBUTOS
    private GestorDeDatos gestorDeDatos;
    private Usuario usuarioActual;
    private LoginVista loginVista;
    private List<Usuario> listaDeUsuarios;
    private List<Sesion> listaDeSesiones;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");

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

    //Registro de un nuevo usuario
    public void registrar(Usuario nuevoUsuario){
        boolean correoExiste = false;
        for (Usuario usuario : listaDeUsuarios){
            if (usuario.getCorreo().equalsIgnoreCase(nuevoUsuario.getCorreo())) { 
                correoExiste = true;
                break;
            }
        }
        if (correoExiste){ 
            loginVista.mostrarError("Error", "Correo no válido.");
        } else {
            this.listaDeUsuarios.add(nuevoUsuario); // Agrega el nuevo usuario a la lista
            try {
                gestorDeDatos.appendUsuario(nuevoUsuario);
            } catch (IOException e) { 
                loginVista.mostrarError("Error", "No se pudo guardar el usuario.");
                return; // Sale del método si no se pudo guardar
            }
            loginVista.mostrarInfo("Registro exitoso", "Usuario registrado correctamente.");
            loginVista.limpiarCampos(); // Limpia los campos del formulario de registro en la vista
        }
    }

    //Flujo Estudiante 

    public List<Usuario> manejarBusquedaTutor(String materia) {
        String criterio = normLower(materia);
        List<Usuario> tutores = new ArrayList<>();
        for (Usuario u : listaDeUsuarios) if (u.getRol() == Rol.TUTOR) tutores.add(u);
        System.out.println("[BuscarTutor] materia='" + criterio + "' → " + tutores.size() + " tutor(es)");
        return tutores;
    }

    //AGENDAMIENTO DE SESIÓN 

    public Sesion manejarAgendamientoSesion(int estudianteId, int tutorId, String materia, String fechaHora) {
        String mat = norm(materia);
        String fh  = norm(fechaHora);

        System.out.println("[Agendar] est=" + estudianteId + " tut=" + tutorId + " mat='" + mat + "' fh='" + fh + "'");

        Usuario est = buscarUsuarioPorId(estudianteId);
        if (est == null || est.getRol() != Rol.ESTUDIANTE) {
            System.out.println("Agendar ERROR: estudiante no válido.");
            if (loginVista != null) loginVista.mostrarError("Agendamiento", "Estudiante no válido.");
            return null;
        }

        Usuario tut = buscarUsuarioPorId(tutorId);
        if (tut == null || tut.getRol() != Rol.TUTOR) {
            System.out.println("Agendar ERROR: tutor no válido.");
            if (loginVista != null) loginVista.mostrarError("Agendamiento", "Tutor no válido.");
            return null;
        }
        
        if (mat.isBlank() || fh.isBlank()) {
            System.out.println("Agendar ERROR: materia/fecha vacías.");
            if (loginVista != null) loginVista.mostrarError("Agendamiento", "Materia y fecha/hora son obligatorias.");
            return null;
        }

        // Validar formato de fecha/hora y que no sea en el pasado (usa FMT y parseFechaHora)
        LocalDateTime dt = parseFechaHora(fh);
        if (dt == null) {
            System.out.println("Agendar ERROR: formato de fecha/hora inválido (esperado HH:mm dd/MM/yy).");
            if (loginVista != null) loginVista.mostrarError("Agendamiento", "Formato inválido. Usa: HH:mm dd/MM/yy (ej. 14:45 10/09/25).");
            return null;
        }
        if (dt.isBefore(LocalDateTime.now())) {
            System.out.println("Agendar ERROR: se intentó agendar en el pasado (" + fh + ").");
            if (loginVista != null) loginVista.mostrarError("Agendamiento", "No puedes agendar una sesión en el pasado.");
            return null;
        }

        // Colisión simple: mismo tutor y misma fecha/hora
        if (tutorOcupadoEnFecha(tutorId, fh)) {
            System.out.println("Agendar ERROR: tutor ocupado en " + fh);
            if (loginVista != null) loginVista.mostrarError("Agendamiento", "El tutor ya tiene una sesión en ese horario.");
            return null;
        }

        // Evitar doble reserva del estudiante en el mismo horario
        if (estudianteOcupadoEnFecha(estudianteId, fh)) {
            System.out.println("Agendar ERROR: estudiante ya tiene una sesión en " + fh);
            if (loginVista != null) loginVista.mostrarError("Agendamiento", "Ya tienes una sesión en ese horario.");
            return null;
        }

        String nuevoId = generarIdSesion();
        Sesion nueva = new Sesion(nuevoId, estudianteId, tutorId, mat, fh, EstadoSesion.PROGRAMADA);
        listaDeSesiones.add(nueva);
        System.out.println("Agendada en memoria: " + nueva);

        // Persistir en CSV
        try {
            gestorDeDatos.appendSesion(nueva);
        } catch (Exception ex) {
            System.out.println("AVISO: sesión creada en memoria pero no se pudo guardar en CSV: " + ex.getMessage());
            if (loginVista != null) loginVista.mostrarError("Persistencia", "Sesión guardada en memoria, pero no en archivo.");
        }

        if (loginVista != null) {
            loginVista.mostrarInfo("Agendamiento", "Sesión programada: " + mat + " – " + fh);
        }
        return nueva;
    }

    //HELPERS 
    private Usuario buscarUsuarioPorId(int id) {
        for (Usuario u : listaDeUsuarios) if (u.getIdUsuario() == id) return u;
        return null;
    }

    // Verifica si el tutor ya tiene una sesión en ese horario exacto
    private boolean tutorOcupadoEnFecha(int tutorId, String fechaHora) {
        String fh = norm(fechaHora);
        for (Sesion s : listaDeSesiones) {
            if (s.getTutorId() == tutorId && fh.equalsIgnoreCase(s.getFechaHora())) {
                return true;
            }
        }
        return false;
    }

    private boolean estudianteOcupadoEnFecha(int estudianteId, String fechaHora) {
        String fh = norm(fechaHora);
        for (Sesion s : listaDeSesiones) {
            if (s.getEstudianteId() == estudianteId && fh.equalsIgnoreCase(s.getFechaHora())) {
                return true;
            }
        }
        return false;
    }

    private String generarIdSesion() {
        int max = 0;
        for (Sesion s : listaDeSesiones) {
            try { max = Math.max(max, Integer.parseInt(s.getIdSesion())); }
            catch (NumberFormatException ignored) {}
        }
        return String.valueOf(max + 1);
    }

    private String norm(String s) { return (s == null) ? "" : s.trim(); }
    private String normLower(String s) { return norm(s).toLowerCase(); }

    private LocalDateTime parseFechaHora(String fh) {
        try {
            return LocalDateTime.parse(fh, FMT);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    //Metodo para recorrer la lista de usuarios y obtener un nuevo ID
    public int generarNuevoIdUsuario() { 
        int max = 0; 
        for (Usuario u : listaDeUsuarios) { 
            max = Math.max(max, u.getIdUsuario()); // actualiza max con el mayor entre max y el id del usuario actual
        } 
        return max + 1; //Devuelve el nuevo ID
    }
}