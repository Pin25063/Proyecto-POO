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
    // Se utilizara para actualizar las sesiones que se necesita que sean aceptadas y rechazadas por un tutor
    public void actualizarSesionEnCSV(Sesion sesionActualizada) throws IOException {
        // Se leen todas las sesiones del csv
        // Esta lista guardará TODAS las líneas del CSV (incluido el encabezado)
        List<String> todasLasLineas = new ArrayList<>();

        // Abrir el archivo para lectura
        try (BufferedReader br =  Files.newBufferedReader(SESIONES, StandardCharsets.UTF_8)) {
            String linea;

            // Leer linea  por linea hasta la ultima sesion
            while ((linea = br.readLine()) != null) {
                todasLasLineas.add(linea); 
                
            }
        }
        
        boolean sesionEncontrada = false;

        // Se recorren todas las lineas menos el encabezado
        for (int i = 1; i < todasLasLineas.size(); i++) {

            // Obtener la linea actual
            String lineaActual = todasLasLineas.get(i);

            // Dividir la línea por el separador ";" para obtener cada campo
            String[] campos = SEP_PATTERN.split(lineaActual, -1);

            // El ID de la sesión está en la posición 0
            String idEnLinea = campos[0].trim();

            // Comparar el ID de esta línea con el ID de la sesión que queremos actualizar
            if (idEnLinea.equals(sesionActualizada.getIdSesion())) {

                // Construir la nueva linea con datos actualizados
                String lineaNueva = sesionActualizada.getIdSesion() + SEP
                                + sesionActualizada.getEstudianteId() + SEP
                                + sesionActualizada.getTutorId() + SEP
                                + sesionActualizada.getMateria() + SEP
                                + sesionActualizada.getFecha() + SEP
                                + sesionActualizada.getHora() + SEP
                                + sesionActualizada.getEstado();
                
                // Reemplazar la línea vieja con la nueva en la lista
                todasLasLineas.set(i, lineaNueva);

                // Marcar que encontramos la sesión
                sesionEncontrada = true;
            
                // Salir del bucle porque ya encontramos lo que buscábamos
                break;

            }
        }

        if (!sesionEncontrada) {
            throw new IOException("No se encontró la sesión con ID: " + sesionActualizada.getIdSesion());
        }

        // Abrir el arcivo para escritura
        try (BufferedWriter bw = Files.newBufferedWriter(
            SESIONES,
            StandardCharsets.UTF_8,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING)) {
                // Escribir cada línea (incluida la modificada) de vuelta al archivo
            for (String linea : todasLasLineas) {
                bw.write(linea);      // Escribir la línea
                bw.write(NL);         // Escribir salto de línea
                }
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

    // Actualiza la información de un usuario específico dentro del archivo CSV
    // lee todo el archivo, modifica la línea del usuario y reescribe el archivo completo.
    public void actualizarUsuarioEnCSV(Usuario usuarioActualizado) throws IOException {

        // Lista temporal para guardar todas las líneas del archivo original
        List<String> todasLasLineas = new ArrayList<>();
        boolean usuarioEncontrado = false;

        // lee el archivo completo en memoria
        // se usa try-with-resources para asegurar que el lector se cierre automáticamente
        // un try-with-resources declara recursos que se cierran automáticamente al salir del bloque
        // BufferedReader br declara un lector con buffering que facilita leer el archivo línea por línea
        try (BufferedReader br = Files.newBufferedReader(USUARIOS, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = br.readLine()) != null) { // br.readLine lee la siguiente línea del archivo y la devuelve como String
                todasLasLineas.add(linea);
            }
        }

        // Buscar y reemplazar la línea del usuario
        for (int i = 0; i < todasLasLineas.size(); i++) {
            String linea = todasLasLineas.get(i);
            // se divide la línea por punto y coma para analizar sus campos
            String[] campos = SEP_PATTERN.split(linea, -1);
            
            // El ID está en la posición 0 se valida que no sea una línea vacía.
            if (campos.length > 0 && !campos[0].isBlank()) {
                try {
                    // El ID del usuario siempre está en la primera posición
                    int idEnLinea = Integer.parseInt(campos[0].trim());
                    
                    // se compara si el ID de la línea coincide con el del usuario que queremos editar.
                    if (idEnLinea == usuarioActualizado.getIdUsuario()) {
                        // Reconstruir la línea con los datos nuevos
                        StringBuilder nuevaLinea = new StringBuilder();
                        nuevaLinea.append(usuarioActualizado.getIdUsuario()).append(SEP)
                                .append(usuarioActualizado.getNombre()).append(SEP) // Nuevo nombre
                                .append(usuarioActualizado.getCorreo()).append(SEP)
                                .append(usuarioActualizado.getContrasena()).append(SEP) // nueva contraseña
                                .append(usuarioActualizado.getRol());

                        // Manejo específico para recuperar datos extra que no se editan aquí
                        // pero deben mantenerse en el CSV
                        if (usuarioActualizado instanceof Tutor) {
                            Tutor t = (Tutor) usuarioActualizado;
                            // se vuelve a unir las materias y se añade la tarifa
                            nuevaLinea.append(SEP).append(String.join(",", t.getMaterias())).append(SEP).append(t.getTarifa());

                        } else if (usuarioActualizado instanceof Catedratico) {
                            Catedratico c = (Catedratico) usuarioActualizado;
                            // se vuelven a unir los cursos a cargo
                            nuevaLinea.append(SEP).append(String.join(",", c.getCursosACargo())).append(SEP); // la tarifa se queda vacía

                        } else {
                            // Estudiante
                            nuevaLinea.append(SEP).append(SEP);
                        }

                        // se reemplaza la línea antigua por la nueva en nuestra lista en memoria
                        todasLasLineas.set(i, nuevaLinea.toString());
                        usuarioEncontrado = true;
                        break;
                    }
                } catch (NumberFormatException e) {
                    continue; // Si el ID no es un número se salta la línea
                }
            }
        }

        if (!usuarioEncontrado) {
            throw new IOException("Usuario no encontrado para actualizar");
        }

        // Reescribir el archivo
        // TRUNCATE_EXISTING borra el contenido anterior para escribir el nuevo
        try (BufferedWriter bw = Files.newBufferedWriter(USUARIOS, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (String linea : todasLasLineas) {
                bw.write(linea);
                bw.write(NL); // se añade el salto de línea al final
            }
        }
    }
}