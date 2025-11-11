import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;

public class ControladorPrincipal {
    
    // ATRIBUTOS
    private GestorDeDatos gestorDeDatos;
    private Usuario usuarioActual;
    private LoginVista loginVista;
    private Main mainApp;

    // LISTAS CON LOS DATOS DEL SISTEMA
    private List<Usuario> listaDeUsuarios;
    private List<Sesion> listaDeSesiones;
    private List<Curso> listaDeCursos;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");

    // CONSTRUCTOR: para inicializar el controlador
    public ControladorPrincipal(LoginVista loginVista, Main mainApp) {
        this.gestorDeDatos = new GestorDeDatos();
        this.loginVista = loginVista;
        this.mainApp = mainApp;
        // se tienen que cargar los usuarios una sola vez para no leer muchas veces el archivo

        try {
            this.listaDeCursos = new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error al cargar los cursos: " + e.getMessage());
            this.listaDeSesiones = new ArrayList<>();
        }
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

            Stage stage = (Stage) loginVista.getScene().getWindow();

            switch (usuarioActual.getRol()) {
                case ESTUDIANTE:
                    Estudiante estudiante = (Estudiante) usuarioActual;
                    VistaPrincipalEstudiante vistaEst = new VistaPrincipalEstudiante(estudiante, this, stage, mainApp);
                    vistaEst.mostrar();
                    break;
                case TUTOR:
                    Tutor tutor = (Tutor) usuarioActual;
                    VistaPrincipalTutor vistaTut = new VistaPrincipalTutor(this, tutor, stage, mainApp);
                    vistaTut.mostrar();
                    break;
                case CATEDRATICO:
                    Catedratico catedratico = (Catedratico) usuarioActual;
                    // se actualiza para que catedrático reciba sus materias directamente desde la carga de datos
                    VistaPrincipalCatedratico vistaCat = new VistaPrincipalCatedratico(this, catedratico, stage, mainApp);
                    vistaCat.mostrar();
                    break;
            }

        } else {
            // Si la validacion falla
            System.out.println("ERROR: Credenciales inválidas.");
            loginVista.mostrarError("Error de autenticación", "Correo o contraseña no válidos");
    }
    // Aquí otros roles agregarán su navegación
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
            if (loginVista != null);
            return null;
        }

        // Colisión simple: mismo tutor y misma fecha/hora
        if (tutorOcupadoEnFecha(tutorId, fh)) {
            System.out.println("Agendar ERROR: tutor ocupado en " + fh);
            if (loginVista != null);
            return null;
        }

        // Evitar doble reserva del estudiante en el mismo horario
        if (estudianteOcupadoEnFecha(estudianteId, fh)) {
            System.out.println("Agendar ERROR: estudiante ya tiene una sesión en " + fh);
            if (loginVista != null);
            return null;
        }

        String nuevoId = generarIdSesion();
        // Dividir el string fh en fecha y hora
        String[] partes = fh.split(" ");
        String hora = partes[0];  // "HH:mm"
        String fecha = partes[1]; // "dd/MM/yy"

        // Llamar al nuevo constructor con 7 argumentos
        Sesion nueva = new Sesion(nuevoId, estudianteId, tutorId, mat, fecha, hora, EstadoSesion.PENDIENTE);
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
        }
        return nueva;
    }

    //HELPERS 
    public Usuario buscarUsuarioPorId(int id) {
        for (Usuario u : listaDeUsuarios) if (u.getIdUsuario() == id) return u;
        return null;
    }

    // Verifica si el tutor ya tiene una sesión en ese horario exacto
    private boolean tutorOcupadoEnFecha(int tutorId, String fechaHora) {
        String[] partes = norm(fechaHora).split(" ");
        String horaInput = partes[0];
        String fechaInput = partes[1];

        for (Sesion s : listaDeSesiones) {
            if (s.getTutorId() == tutorId && 
                fechaInput.equalsIgnoreCase(s.getFecha()) && 
                horaInput.equalsIgnoreCase(s.getHora())) {
                return true;
            }
        }
        return false;
    }

    private boolean estudianteOcupadoEnFecha(int estudianteId, String fechaHora) {
        String[] partes = norm(fechaHora).split(" ");
        String horaInput = partes[0];
        String fechaInput = partes[1];
        
        for (Sesion s : listaDeSesiones) {
            if (s.getEstudianteId() == estudianteId && 
                fechaInput.equalsIgnoreCase(s.getFecha()) && 
                horaInput.equalsIgnoreCase(s.getHora())) {
                return true;
            }
        }
        return false;
    }

    public String generarIdSesion() {
        int max = 0;
        for (Sesion s : listaDeSesiones) {
            try { max = Math.max(max, Integer.parseInt(s.getIdSesion())); }
            catch (NumberFormatException ignored) {}
        }
        return String.valueOf(max + 1);
    }

    // GETTERS
    public List<Sesion> getListaDeSesiones() {
        return listaDeSesiones;
    }

    public GestorDeDatos getGestorDeDatos() {
        return gestorDeDatos;
    }

    public List<Usuario> getListaDeUsuarios() {
        return listaDeUsuarios;
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

    // Actualiza el estado de una sesion especifica y lo guarda en el csv
    public boolean actualizarEstadoSesion(Sesion sesion) {
    
        try {
            // Variable para saber si encontramos la sesión
            boolean encontradaEnMemoria = false;
            
            // Recorrer todas las sesiones en la lista
            for (int i = 0; i < listaDeSesiones.size(); i++) {
                
                // Obtener la sesión en la posición i
                Sesion sesionActual = listaDeSesiones.get(i);
                
                // Comparar el ID de esta sesión con el ID que queremos actualizar
                if (sesionActual.getIdSesion().equals(sesion.getIdSesion())) {
                    
                    // Reemplazar la sesión vieja con la actualizada
                    listaDeSesiones.set(i, sesion);
                    
                    // Marcar que la encontramos
                    encontradaEnMemoria = true;
                    
                    // Salir del bucle
                    break;
                }
            }
            
            // Verificar si encontramos la sesión
            if (!encontradaEnMemoria) {
                System.out.println("ERROR: Sesión no encontrada en la lista en memoria");
                return false;
            }
            
            // Llamar al método del GestorDeDatos que actualiza solo esta sesión en el CSV
            gestorDeDatos.actualizarSesionEnCSV(sesion);
            
            // Indica que todo funciono correctamente
            return true;
            
        } catch (IOException e) {
        
            System.out.println("❌ Error al actualizar el archivo CSV:");
            System.out.println("   " + e.getMessage());
            e.printStackTrace();
            return false;
            
        } catch (Exception e) {
            System.out.println("❌ Error inesperado al actualizar sesión:");
            System.out.println("   " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Sesion> obtenerSesionesPendientesPorTutor(int tutorId) {
        List<Sesion> pendientes = new ArrayList<>();
        
        // Recorrer todas las sesiones
        for (Sesion sesion : listaDeSesiones) {
            // Filtrar por: (1) mismo tutor y (2) estado PENDIENTE
            if (sesion.getTutorId() == tutorId && 
                sesion.getEstado() == EstadoSesion.PENDIENTE) {
                pendientes.add(sesion);
            }
        }
        
        System.out.println("→ Sesiones pendientes para tutor " + tutorId + ": " + pendientes.size());
        return pendientes;
    }

    public List<Sesion> obtenerSesionesAceptadasPorTutor(int tutorId) {
        List<Sesion> aceptadas = new ArrayList<>();
        
        // Recorrer todas las sesiones
        for (Sesion sesion : listaDeSesiones) {
            // Filtrar por: (1) mismo tutor y (2) estado AGENDADA o PROGRAMADA
            if (sesion.getTutorId() == tutorId && 
                (sesion.getEstado() == EstadoSesion.AGENDADA || 
                sesion.getEstado() == EstadoSesion.PROGRAMADA)) {
                aceptadas.add(sesion);
            }
        }
        
        System.out.println("→ Sesiones aceptadas para tutor " + tutorId + ": " + aceptadas.size());
        return aceptadas;
    }

}