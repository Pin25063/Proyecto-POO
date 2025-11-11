import java.util.List;
import java.util.stream.Collectors;


// Controlador especializado para las funciones administrativas del Catedrático.

public class ControladorAdministradores {
    
    private ControladorPrincipal controladorPrincipal;

    public ControladorAdministradores(ControladorPrincipal controladorPrincipal) {
        if (controladorPrincipal == null) {
            throw new IllegalArgumentException("El ControladorPrincipal no puede ser nulo.");
        }
        this.controladorPrincipal = controladorPrincipal;
    }

    // METODO: asignar una nueva tutoría a un estudiante con un tutor para un curso específico
    public Sesion asignarTutoria(int estudianteId, int tutorId, String nombreCurso) {
        // Validaciones
        if (nombreCurso == null) {
            System.err.println("Error de asignación: El curso no puede ser nulo.");
            return null;
        }

        Usuario estUsuario = controladorPrincipal.buscarUsuarioPorId(estudianteId);
        if (!(estUsuario instanceof Estudiante)) {
            System.err.println("Error de asignación: No se encontró un estudiante con ID: " + estudianteId);
            return null;
        }

        Usuario tutUsuario = controladorPrincipal.buscarUsuarioPorId(tutorId);
        if (!(tutUsuario instanceof Tutor)) {
            System.err.println("Error de asignación: No se encontró un tutor con ID: " + tutorId);
            return null;
        }

        // La fecha se deja "Por definir" para que el estudiante y tutor la coordinen
        String nuevoId = controladorPrincipal.generarIdSesion();
        Sesion nuevaSesion = new Sesion(nuevoId, estudianteId, tutorId, nombreCurso, "Por definir", "Por definir", EstadoSesion.PENDIENTE);
        
        // Actualización de datos en memoria
        controladorPrincipal.getListaDeSesiones().add(nuevaSesion);
        ((Estudiante) estUsuario).getHistorialSesiones().add(nuevaSesion); // Se añade al historial del estudiante

        // Persistencia en archivo
        try {
            controladorPrincipal.getGestorDeDatos().appendSesion(nuevaSesion);
            System.out.println("Tutoría asignada y guardada en CSV exitosamente.");
        } catch (Exception e) {
            System.err.println("AVISO: La tutoría fue asignada en memoria pero no se pudo guardar en el archivo: " + e.getMessage());
        }

        return nuevaSesion;
    }

    // METODO: Generar reporte detallado del desempeño de todos los tutores del sistema (IMPLEMENTADO MÁS ADELANTE)
    public String generarReporteDesempenoTutores() {
        // StringBuilder es un "Constructor de Textos" usado para crear Strings de manera más eficiente
        // ya que es mutable y sirve mucho al querer construir un String, pieza por pieza y se usa más cuando hay bucles de por medio
        StringBuilder reporte = new StringBuilder("=== REPORTE DE DESEMPEÑO DE TUTORES ===\n\n");
        
        // Obtenemos solo los tutores de la lista de usuarios.
        List<Tutor> tutores = controladorPrincipal.getListaDeUsuarios().stream().filter(u -> u instanceof Tutor).map(u -> (Tutor) u).collect(Collectors.toList());

        if (tutores.isEmpty()) {
            return "No hay tutores registrados en el sistema.";
        }
        
        for (Tutor tutor : tutores) {
            reporte.append("Tutor: ").append(tutor.getNombre()).append(" (ID: ").append(tutor.getIdUsuario()).append(")\n");
            reporte.append("  Materias: ").append(String.join(", ", tutor.getMaterias())).append("\n");
            reporte.append("  Tarifa: Q").append(String.format("%.2f", tutor.getTarifa())).append("\n");

            // Se cuetan cuántas sesiones ha completado este tutor.
            long sesionesCompletadas = controladorPrincipal.getListaDeSesiones().stream()
                .filter(s -> s.getTutorId() == tutor.getIdUsuario() && s.getEstado() == EstadoSesion.COMPLETADA)
                .count();
            
            reporte.append("  Total Sesiones Completadas: ").append(sesionesCompletadas).append("\n\n");
        }
        return reporte.toString();

    }

    // METODO: para generar un reporte del estado de las tutorías de un curso específico (IMPLEMENTADO MÁS ADELANTE)
    public String generarReporteConsolidadoCurso(String nombreCurso) {
        if (nombreCurso == null) return "Por favor, selecciona un curso para generar el reporte.";
        
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DEL CURSO: ").append(nombreCurso).append(" ===\n\n");

        // Filtar las sesiones que pertenecen a este curso.
        List<Sesion> sesionesDelCurso = controladorPrincipal.getListaDeSesiones().stream().filter(s -> s.getMateria().equalsIgnoreCase(nombreCurso)).collect(Collectors.toList());

        if (sesionesDelCurso.isEmpty()) {
            return reporte.append("No hay sesiones registradas para este curso.").toString();
        }

        // Contamos las sesiones por cada estado.
        long pendientes = sesionesDelCurso.stream().filter(s -> s.getEstado() == EstadoSesion.PENDIENTE).count();
        long programadas = sesionesDelCurso.stream().filter(s -> s.getEstado() == EstadoSesion.PROGRAMADA).count();
        long completadas = sesionesDelCurso.stream().filter(s -> s.getEstado() == EstadoSesion.COMPLETADA).count();
        long canceladas = sesionesDelCurso.stream().filter(s -> s.getEstado() == EstadoSesion.CANCELADA).count();

        reporte.append("Total de sesiones registradas: ").append(sesionesDelCurso.size()).append("\n");
        reporte.append("  - Pendientes: ").append(pendientes).append("\n");
        reporte.append("  - Programadas: ").append(programadas).append("\n");
        reporte.append("  - Completadas: ").append(completadas).append("\n");
        reporte.append("  - Canceladas: ").append(canceladas).append("\n");

        return reporte.toString();
    }
}