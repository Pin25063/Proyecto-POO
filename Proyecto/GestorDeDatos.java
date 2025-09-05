import java.io.*;
import java.nio.file.*;
import java.util.*;
public class GestorDeDatos {
    private static final Path USUARIOS = Paths.get("usuarios.csv");  // Ruta relativa al archivo CSV
    private static final String SEP = ";";
    private static final String HDR_USU = "idUsuario,nombre,correo,contrasena,rol";
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
}