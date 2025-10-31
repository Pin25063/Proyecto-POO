import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GestorDeDatos {

    // Rutas relativas a los archivos CSV en resources
    private static final Path USUARIOS = Paths.get("src/main/resources/data/usuarios.csv"); //path en el cual se encuentra el archivo CSV de los usuarios
    private static final Path SESIONES = Paths.get("src/main/resources/data/sesiones.csv"); //path en el cual se encuentra el archivo CSV de las sesiones

    private static final String SEP = ";"; //Separador utilizado en el archivo CSV
    private static final String HDR_USU = "idUsuario;nombre;correo;contrasena;rol";  // ← ESTA CONSTANTE
    private static final String HDR_SES = "idSesion;estudianteId;tutorId;materia;fechaHora;estado";
    private static final String NL  = System.lineSeparator(); // Separador de fin de línea según el sistema operativo. Se utiliza al escribir en los archivos CSV para añadir saltos de línea correctos.
    private static final Pattern SEP_PATTERN = Pattern.compile(Pattern.quote(SEP)); // Patrón compilado para dividir las líneas CSV usando el separador SEP. Se usa Pattern.quote(SEP) para escapar caracteres especiales del separador.

    // CARGAR USUARIOS CSV
    public synchronized List<Usuario> cargarUsuarios() throws IOException {
        List<Usuario> out = new ArrayList<>();
        if (!Files.exists(USUARIOS)) {
            System.out.println("El archivo de usuarios no existe.");
            return out;
        }

        try (BufferedReader br = Files.newBufferedReader(USUARIOS, StandardCharsets.UTF_8)) {
            String line = br.readLine();  // Leer y descartar header
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] raw = SEP_PATTERN.split(line, -1);
                if (raw.length < 5) continue;

                int id = Integer.parseInt(raw[0].trim());
                String nombre  = raw[1].trim();
                String correo  = raw[2].trim();
                String pass    = raw[3].trim();
                Rol rol = Rol.valueOf(raw[4].trim().toUpperCase());

                // Crear el tipo específico de usuario según el rol
                Usuario usuario;
                switch (rol) {
                    case ESTUDIANTE:
                        usuario = new Estudiante(id, nombre, correo, pass, rol, new ArrayList<>());
                        break;
                        
                    case TUTOR:
                        // Para tutores, crear con materias vacías y tarifa 0 (se pueden actualizar después)
                        usuario = new Tutor(id, nombre, correo, pass, rol, new ArrayList<>(), 0.0);
                        break;
                        
                    case CATEDRATICO:
                        // Para catedráticos, crear con materias vacías
                        usuario = new Catedratico(id, nombre, correo, pass, rol, new ArrayList<>());
                        break;
                        
                    default:
                        // Por defecto, crear Usuario genérico
                        usuario = new Usuario(id, nombre, correo, pass, rol);
                        break;
                }
                
                out.add(usuario);
                System.out.println("Usuario cargado: " + nombre + ", " + correo + " (" + rol + ")");
            }
        }
        return out;
    }

    // SESIONES CSV
    public List<Sesion> cargarSesiones() throws IOException {
        List<Sesion> out = new ArrayList<>(); // Lista de salida para las sesiones leídas

        try (BufferedReader br = Files.newBufferedReader(SESIONES, StandardCharsets.UTF_8)) { // Abre BufferedReader con codificación UTF-8
            String line = br.readLine(); //Lee la primera línea y la descarta
            while ((line = br.readLine()) != null) { // Lee línea por línea hasta el final del archivo
                if (line.isBlank()) continue; // Omite líneas en blanco
                String[] raw = SEP_PATTERN.split(line, -1); // Divide la línea usando el separador SEP conservando campos vacíos
                if (raw.length < 6) continue; // Si no hay suficientes columnas, ignora la línea
                String id        = raw[0].trim(); // Lee y recorta el id de la sesión
                int estId        = Integer.parseInt(raw[1].trim()); // Lee y convierte el id del estudiante a entero
                int tutId        = Integer.parseInt(raw[2].trim()); // Lee y convierte el id del tutor a entero
                String materia   = raw[3].trim(); // Lee y recorta la materia
                String fechaHora = raw[4].trim(); // Lee y recorta la fecha y hora
                EstadoSesion estado = EstadoSesion.valueOf(raw[5].trim().toUpperCase()); // Convierte el estado a enum (en mayúsculas)

                out.add(new Sesion(id, estId, tutId, materia, fechaHora, estado)); // Crea la sesión y la añade a la lista
            }
        }
        return out; // Devuelve la lista de sesiones cargadas
    }

    // APPEND SESION
    public void appendSesion(Sesion s) throws IOException {
        // Aquí solo se muestra un mensaje si el archivo no existe
        if (!Files.exists(SESIONES)) {
            System.out.println("El archivo de sesiones no existe.");
            return;
        }

        try (BufferedWriter bw = Files.newBufferedWriter(
                SESIONES,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

            String linea = s.getIdSesion() + SEP
                    + s.getEstudianteId() + SEP
                    + s.getTutorId() + SEP
                    + s.getMateria() + SEP
                    + s.getFechaHora() + SEP
                    + s.getEstado();
            bw.write(linea);
            bw.write(NL);
        }
    }

    public void appendUsuario(Usuario u) throws IOException { 
        try (BufferedWriter bw = Files.newBufferedWriter( // abre un BufferedWriter
            USUARIOS, // archivo
            StandardCharsets.UTF_8, // codificación UTF-8
            StandardOpenOption.APPEND)) { // abre en modo append para añadir al final

            StringBuilder linea = new StringBuilder(); // crea un StringBuilder para construir la línea CSV
            linea.append(u.getIdUsuario()).append(SEP) // añade id de usuario y separador
                .append(u.getNombre()).append(SEP) // añade nombre y separador
                .append(u.getCorreo()).append(SEP) // añade correo y separador
                .append(u.getContrasena()).append(SEP) // añade contraseña y separador
                .append(u.getRol()); // añade rol (sin separador final aun)
            //Si el nuevo usuario es tutor se asignan los parametros materias y tarifa
            if (u instanceof Tutor tutor) {
                    linea.append(SEP).append(String.join(",", tutor.getMaterias()))
                    .append(SEP).append(tutor.getTarifa());
            } else if(u instanceof Catedratico cat){ //Si el nuevo usuario es catedratico se asigna solo materias
                String codigosCursos = cat.getCursosACargo().stream().map(Curso::getCodigoCurso).collect(Collectors.joining(","));
                linea.append(SEP).append(codigosCursos)
                     .append(SEP); //No asignar tarifa
            } else {
                linea.append(SEP).append(SEP); // añade campo vacío para materias y tarifa
            }

            bw.write(linea.toString()); // escribe la línea construida en el archivo
            bw.write(NL); // escribe nueva línea
        } 
    } 

    // GUARDAR NUEVO USUARIO EN CSV
    public synchronized void guardarUsuario(Usuario usuario) throws IOException {
        // Verificar si el archivo existe, si no, crearlo con header
        if (!Files.exists(USUARIOS)) {
            try (BufferedWriter bw = Files.newBufferedWriter(USUARIOS, StandardCharsets.UTF_8)) {
                bw.write(HDR_USU);
                bw.write(NL);
            }
        }
        
        // Append el nuevo usuario
        try (BufferedWriter bw = Files.newBufferedWriter(
                USUARIOS,
                StandardCharsets.UTF_8,
                StandardOpenOption.APPEND)) {
            
            String linea = usuario.getIdUsuario() + SEP
                    + usuario.getNombre() + SEP
                    + usuario.getCorreo() + SEP
                    + usuario.getContrasena() + SEP
                    + usuario.getRol().toString();
            
            bw.write(linea);
            bw.write(NL);
            
            System.out.println("Usuario guardado en CSV: " + linea);
        }
    }
}