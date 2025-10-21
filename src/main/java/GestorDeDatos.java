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

public class GestorDeDatos {

    // Rutas relativas a los archivos CSV en resources
    private static final Path USUARIOS = Paths.get("src/main/resources/data/usuarios.csv"); //path en el cual se encuentra el archivo CSV de los usuarios
    private static final Path SESIONES = Paths.get("src/main/resources/data/sesiones.csv"); //path en el cual se encuentra el archivo CSV de las sesiones

    private static final String SEP = ";"; //Separador utilizado en el archivo CSV
    private static final String HDR_USU = "idUsuario;nombre;correo;contrasena;rol;materias"; //Formato en el que se encuentran los dato s de los usuarios
    private static final String HDR_SES = "idSesion;estudianteId;tutorId;materia;fechaHora;estado"; //Formato en el que se encuentran los datos de las sesiones
    private static final String NL  = System.lineSeparator(); // Separador de fin de línea según el sistema operativo. Se utiliza al escribir en los archivos CSV para añadir saltos de línea correctos.
    private static final Pattern SEP_PATTERN = Pattern.compile(Pattern.quote(SEP)); // Patrón compilado para dividir las líneas CSV usando el separador SEP. Se usa Pattern.quote(SEP) para escapar caracteres especiales del separador.

    // USUARIOS CSV
    public synchronized List<Usuario> cargarUsuarios() throws IOException {
        List<Usuario> out = new ArrayList<>(); // Lista de salida donde se almacenarán los usuarios leídos
        if (!Files.exists(USUARIOS)) { // Comprueba si el archivo de usuarios existe
            System.out.println("El archivo de usuarios no existe."); // Mensaje informativo si no existe el archivo
            return out; // Devuelve la lista vacía si no existe el archivo
        }

        try (BufferedReader br = Files.newBufferedReader(USUARIOS, StandardCharsets.UTF_8)) { // Abre un BufferedReader con codificación UTF-8 para leer el archivo CSV
            String line = br.readLine();  // Lee la primera línea y la descarta
            while ((line = br.readLine()) != null) { // Lee línea por línea hasta el final del archivo
                if (line.isBlank()) continue; // Omite líneas en blanco
                String[] raw = SEP_PATTERN.split(line, -1); // Divide la línea usando el separador definido (SEP), conservando campos vacíos
                if (raw.length < 5) continue; // Si la línea no tiene suficientes columnas, se ignora

                int id = Integer.parseInt(raw[0].trim()); // Parse del ID de usuario desde la primera columna
                String nombre  = raw[1].trim(); // Nombre del usuario desde la segunda columna (recortado)
                String correo  = raw[2].trim(); // Correo del usuario desde la tercera columna (recortado)
                String pass    = raw[3].trim(); // Contraseña desde la cuarta columna (recortada)
                Rol rol = Rol.valueOf(raw[4].trim().toUpperCase()); // Convierte la quinta columna a un valor del enum Rol (mayúsculas)

                // Soporte para materias (columna 6, si existe)
                ArrayList<String> materias = new ArrayList<>();
                if (raw.length >= 6 && !raw[5].isBlank()) { //Validacion de datos en el CSV
                    for (String mat : raw[5].split(",")) {
                        materias.add(mat.trim());
                    }
                }

            Usuario u;
            switch (rol) {
                case TUTOR -> u = new Tutor(id, nombre, correo, pass, rol, materias, 0.0);
                case ESTUDIANTE -> u = new Estudiante(id, nombre, correo, pass, rol);
                default -> u = new Usuario(id, nombre, correo, pass, rol);
            }

                out.add(new Usuario(id, nombre, correo, pass, rol)); // Crea un objeto Usuario y lo añade a la lista de salida
            }
        }
        return out;  // Devuelve la lista con los usuarios cargados
    }

    // SESIONES CSV
    public synchronized List<Sesion> cargarSesiones() throws IOException {
        List<Sesion> out = new ArrayList<>(); // Lista de salida para las sesiones leídas
        if (!Files.exists(SESIONES)) { // Comprueba si el archivo de sesiones existe
            System.out.println("El archivo de sesiones no existe.");
            return out;  // Devuelve lista vacía si no existe el archivo
        }

        try (BufferedReader br = Files.newBufferedReader(SESIONES, StandardCharsets.UTF_8)) { // Abre BufferedReader con codificación UTF-8
            String line = br.readLine(); //Lee la primera línea y la descarta
            while ((line = br.readLine()) != null) { // Lee línea por línea hasta el final del archivo
                if (line.isBlank()) continue; // Omite líneas en blanco
                String[] raw = SEP_PATTERN.split(line, -1); // Divide la línea usando el separador SEP conservando campos vacíos
                if (raw.length < 6) continue; // Si no hay suficientes columnas, ignora la línea
                String id       = raw[0].trim(); // Lee y recorta el id de la sesión
                int estId       = Integer.parseInt(raw[1].trim()); // Lee y convierte el id del estudiante a entero
                int tutId       = Integer.parseInt(raw[2].trim()); // Lee y convierte el id del tutor a entero
                String materia  = raw[3].trim(); // Lee y recorta la materia
                String fechaHora= raw[4].trim(); // Lee y recorta la fecha y hora
                EstadoSesion estado = EstadoSesion.valueOf(raw[5].trim().toUpperCase()); // Convierte el estado a enum (en mayúsculas)

                out.add(new Sesion(id, estId, tutId, materia, fechaHora, estado)); // Crea la sesión y la añade a la lista
            }
        }
        return out; // Devuelve la lista de sesiones cargadas
    }

    // APPEND SESION
    public synchronized void appendSesion(Sesion s) throws IOException {
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

    public synchronized void appendUsuario(Usuario u) throws IOException { 
        if (!Files.exists(USUARIOS)) { 
            Files.write(USUARIOS, (HDR_USU + NL).getBytes(StandardCharsets.UTF_8)); // escribe cabecera con campo materias y nueva línea en UTF-8
        } 

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

            if (u instanceof Tutor tutor) {
                linea.append(SEP).append(String.join(",", tutor.getMaterias())); // añade separador y las materias unidas por comas
            } else { 
                linea.append(SEP); // añade campo vacío para materias
            }

            bw.write(linea.toString()); // escribe la línea construida en el archivo
            bw.write(NL); // escribe nueva línea
        } 
    } 
}