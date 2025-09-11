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
    private static final Path USUARIOS = Paths.get("src/main/resources/data/usuarios.csv");
    private static final Path SESIONES = Paths.get("src/main/resources/data/sesiones.csv");

    private static final String SEP = ";";
    private static final String HDR_USU = "idUsuario;nombre;correo;contrasena;rol";
    private static final String HDR_SES = "idSesion;estudianteId;tutorId;materia;fechaHora;estado";
    private static final String NL  = System.lineSeparator();
    private static final Pattern SEP_PATTERN = Pattern.compile(Pattern.quote(SEP));

    // USUARIOS CSV
    public synchronized List<Usuario> cargarUsuarios() throws IOException {
        List<Usuario> out = new ArrayList<>();
        if (!Files.exists(USUARIOS)) {
            System.out.println("El archivo de usuarios no existe.");
            return out; 
        }

        try (BufferedReader br = Files.newBufferedReader(USUARIOS, StandardCharsets.UTF_8)) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] raw = SEP_PATTERN.split(line, -1);
                if (raw.length < 5) continue;

                int id = Integer.parseInt(raw[0].trim());
                String nombre  = raw[1].trim();
                String correo  = raw[2].trim();
                String pass    = raw[3].trim();
                Rol rol = Rol.valueOf(raw[4].trim().toUpperCase());

                out.add(new Usuario(id, nombre, correo, pass, rol));
                System.out.println("Usuario cargado: " + nombre + ", " + correo); // Verificación temporal de carga de archivo CSV
            }
        }
        return out;
    }

    // SESIONES CSV
    public synchronized List<Sesion> cargarSesiones() throws IOException {
        List<Sesion> out = new ArrayList<>();
        if (!Files.exists(SESIONES)) {
            System.out.println("El archivo de sesiones no existe.");
            return out;  
        }

        try (BufferedReader br = Files.newBufferedReader(SESIONES, StandardCharsets.UTF_8)) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] raw = SEP_PATTERN.split(line, -1);
                if (raw.length < 6) continue;
                String id       = raw[0].trim();
                int estId       = Integer.parseInt(raw[1].trim());
                int tutId       = Integer.parseInt(raw[2].trim());
                String materia  = raw[3].trim();
                String fechaHora= raw[4].trim(); 
                EstadoSesion estado = EstadoSesion.valueOf(raw[5].trim().toUpperCase());

                out.add(new Sesion(id, estId, tutId, materia, fechaHora, estado));
                System.out.println("Sesion cargada: " + id + ", " + materia + ", " + estado); // Verificación temporal de carga de archivo CSV
            }
        }
        return out;
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
}
