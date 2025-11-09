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
        
    private static final String NL  = System.lineSeparator(); // Separador de fin de línea según el sistema operativo. Se utiliza al escribir en los archivos CSV para añadir saltos de línea correctos.
    private static final Pattern SEP_PATTERN = Pattern.compile(Pattern.quote(SEP)); // Patrón compilado para dividir las líneas CSV usando el separador SEP. Se usa Pattern.quote(SEP) para escapar caracteres especiales del separador.

    // USUARIOS CSV
    public List<Usuario> cargarUsuarios() throws IOException {
        List<Usuario> out = new ArrayList<>(); // Lista de salida donde se almacenarán los usuarios leídos

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

                // Soporte para tarifa (columna 7, si existe)
                double tarifa = 0.0;
                if (rol == Rol.TUTOR && raw.length >= 7 && !raw[6].isBlank()) {
                    try {
                        tarifa = Double.parseDouble(raw[6].trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Tarifa inválida para tutor con ID " + id);
                    }
                }
                Usuario u;
                switch (rol) {
                    case TUTOR -> u = new Tutor(id, nombre, correo, pass, materias, tarifa);
                    case CATEDRATICO -> u = new Catedratico(id, nombre, correo, pass, materias);
                    default -> u = new Estudiante(id, nombre, correo, pass);
                }
                    out.add(u); // Crea un objeto Usuario y lo añade a la lista de salida
                }
            }
            return out;  // Devuelve la lista con los usuarios cargados
        }

    // SESIONES CSV
    public List<Sesion> cargarSesiones() throws IOException {
        List<Sesion> out = new ArrayList<>(); // Lista de salida para las sesiones leídas

        try (BufferedReader br = Files.newBufferedReader(SESIONES, StandardCharsets.UTF_8)) { // Abre BufferedReader con codificación UTF-8
            String line = br.readLine(); //Lee la primera línea y la descarta
            while ((line = br.readLine()) != null) { // Lee línea por línea hasta el final del archivo
                if (line.isBlank()) continue; // Omite líneas en blanco
                String[] raw = SEP_PATTERN.split(line, -1); // Divide la línea usando el separador SEP conservando campos vacíos
                
                // CAMBIO: Se esperan 7 columnas (id;estId;tutId;materia;fecha;hora;estado)
                if (raw.length < 7) {
                    System.out.println("Saltando línea de sesión mal formateada: " + line);
                    continue; // Si no hay suficientes columnas, ignora la línea
                }
                
                String id        = raw[0].trim(); // Lee y recorta el id de la sesión
                int estId        = Integer.parseInt(raw[1].trim()); // Lee y convierte el id del estudiante a entero
                int tutId        = Integer.parseInt(raw[2].trim()); // Lee y convierte el id del tutor a entero
                String materia   = raw[3].trim(); // Lee y recorta la materia
                
                // CAMBIO: Se leen fecha y hora de columnas separadas
                String fecha     = raw[4].trim(); // Lee y recorta la fecha
                String hora      = raw[5].trim(); // Lee y recorta la hora
                
                // CAMBIO: El estado ahora está en la posición 6
                EstadoSesion estado = EstadoSesion.valueOf(raw[6].trim().toUpperCase()); // Convierte el estado a enum (en mayúsculas)

                // CAMBIO: Se llama al nuevo constructor de Sesion
                out.add(new Sesion(id, estId, tutId, materia, fecha, hora, estado)); // Crea la sesión y la añade a la lista
            }
        }
        return out; // Devuelve la lista de sesiones cargadas
    }

    // APPEND SESION
    public void appendSesion(Sesion s) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(
                SESIONES,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

            String linea = s.getIdSesion() + SEP // id de la sesión
                    + s.getEstudianteId() + SEP // id del estudiante
                    + s.getTutorId() + SEP // id del tutor
                    + s.getMateria() + SEP // materia de la sesión
                    + s.getFecha() + SEP // fecha de la sesión
                    + s.getHora() + SEP // hora de la sesión
                    + s.getEstado(); // estado de la sesión
            
            // escribe la línea, y LUEGO el salto de línea.
            bw.write(linea);
            bw.write(NL);
        }
    }

    public void appendUsuario(Usuario u) throws IOException { 
        try (BufferedWriter bw = Files.newBufferedWriter( // abre un BufferedWriter
            USUARIOS, // archivo
            StandardCharsets.UTF_8, // codificación UTF-8
            StandardOpenOption.CREATE, // Añadido
            StandardOpenOption.APPEND)) { // abre en modo append para añadir al final

            StringBuilder linea = new StringBuilder(); // crea un StringBuilder para construir la línea CSV
            linea.append(u.getIdUsuario()).append(SEP) // añade id de usuario y separador
                .append(u.getNombre()).append(SEP) // añade nombre y separador
                .append(u.getCorreo()).append(SEP) // añade correo y separador
                .append(u.getContrasena()).append(SEP) // añade contraseña y separador
                .append(u.getRol()); // añade rol (sin separador final aun)
            
            if (u instanceof Tutor tutor) {
                    linea.append(SEP).append(String.join(",", tutor.getMaterias()))
                    .append(SEP).append(tutor.getTarifa());

            } else if(u instanceof Catedratico cat){ 
                String cursos = String.join(",", cat.getCursosACargo());
                linea.append(SEP).append(cursos)
                    .append(SEP); //No asignar tarifa
                    
            } else {
                linea.append(SEP).append(SEP).append(SEP); // añade campo vacío para materias y tarifa
            }

            bw.write(linea.toString()); // escribe la línea construida en el archivo
            bw.write(NL); // escribe nueva línea
        } 
    } 
}