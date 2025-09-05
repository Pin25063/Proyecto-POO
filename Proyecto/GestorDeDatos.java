import java.io.*;
import java.nio.file.*;
import java.util.*;
public class GestorDeDatos {
    private static final Path USUARIOS = Paths.get("usuarios.csv");  // Ruta relativa al archivo CSV
    private static final Path SESIONES = Paths.get("sesiones.csv");  // Ruta relativa al archivo CSV
    private static final String SEP = ";";
    private static final String HDR_USU = "idUsuario,nombre,correo,contrasena,rol";
    private static final String HDR_SES = "idSesion,estudianteId,tutorId,materia,fechaHora,estado";
    // Método para cargar usuarios desde el archivo CSV
    public synchronized List<Usuario> cargarUsuarios() throws IOException {
        List<Usuario> out = new ArrayList<>();
        // Verificación si el archivo existe
        if (!Files.exists(USUARIOS)) {
            System.out.println("El archivo de usuarios no existe.");
            return out;
        }
        // Lectura del archivo CSV
        try (BufferedReader br = new BufferedReader(new FileReader(USUARIOS.toString()))) { // Convertir Path a String
            String line = br.readLine(); // header (salta la primera línea que contiene los encabezados)
            // Leer las líneas siguientes
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;  // Saltar líneas en blanco
                String[] raw = line.split(SEP);  // Dividir la línea por las comas
                if (raw.length < 5) continue;  // Si la línea no tiene suficientes campos, saltar
                // Parsear los valores leídos
                int id = Integer.parseInt(raw[0]);  // Convertir el primer valor a int (idUsuario)
                String nombre = raw[1];  // El segundo valor es el nombre
                String correo = raw[2];  // El tercer valor es el correo
                String pass = raw[3];    // El cuarto valor es la contraseña
                Rol rol = Rol.valueOf(raw[4].toUpperCase());  // Convertir el rol a enum Rol
                // Crear el objeto Usuario y agregarlo a la lista
                out.add(new Usuario(id, nombre, correo, pass, rol));
            }
        } catch (IOException e) {
            // Si ocurre un error al leer el archivo
            System.out.println("Error al leer el archivo de usuarios: " + e.getMessage());
            throw e;
        }
        return out;
    }
    public synchronized List<Sesion> cargarSesiones() throws IOException {
        List<Sesion> out = new ArrayList<>();

        // Verificación si el archivo existe
        if (!Files.exists(SESIONES)) {
            System.out.println("El archivo de sesiones no existe.");
            return out;
        }
        // Lectura del archivo CSV
        try (BufferedReader br = new BufferedReader(new FileReader(SESIONES.toString()))) { // Convertir Path a String
            String line = br.readLine(); // header (salta la primera línea que contiene los encabezados)
            // Leer las líneas siguientes
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;  // Saltar líneas en blanco
                String[] raw = line.split(SEP);  // Dividir la línea por las comas
                if (raw.length < 6) continue;  // Si la línea no tiene suficientes campos, saltar
                // Parsear los valores leídos
                String id = raw[0];  // El primer valor es el idSesion
                int estId = Integer.parseInt(raw[1]);  // Convertir el segundo valor a int (estudianteId)
                int tutId = Integer.parseInt(raw[2]);  // Convertir el tercer valor a int (tutorId)
                String materia = raw[3];  // El cuarto valor es la materia
                String fechaHora = raw[4];  // Se mantiene como String
                EstadoSesion estado = EstadoSesion.valueOf(raw[5].toUpperCase());  // Convertir el estado a enum EstadoSesion
                // Crear el objeto Sesion y agregarlo a la lista
                out.add(new Sesion(id, estId, tutId, materia, fechaHora, estado));
            }
        } catch (IOException e) {
            // Si ocurre un error al leer el archivo
            System.out.println("Error al leer el archivo de sesiones: " + e.getMessage());
            throw e;
        }
        return out;
    }
}