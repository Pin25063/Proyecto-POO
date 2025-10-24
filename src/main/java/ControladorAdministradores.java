import java.util.ArrayList;
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
    public Sesion asignarTutoria(int estudianteId, int tutorId, Curso curso) {
        // Validaciones
        if (curso == null) {
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
        Sesion nuevaSesion = new Sesion(nuevoId, estudianteId, tutorId, curso.getNombreCurso(), "Por definir", EstadoSesion.PENDIENTE);
        
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
    public void generarReporteDesempenoTutores() {
    }

    // METODO: para generar un reporte del estado de las tutorías de un curso específico (IMPLEMENTADO MÁS ADELANTE)
    public void generarReporteConsolidadoCurso(Curso curso) {
    }
}