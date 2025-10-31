import java.util.ArrayList;

public class Estudiante extends Usuario {
    private ArrayList<Sesion> historialSesiones;
    
    // Constructor completo (mantener el existente)
    public Estudiante(int idUsuario, String nombre, String correo, String contrasena, Rol rol, ArrayList<Sesion> historialSesiones) {
        super(idUsuario, nombre, correo, contrasena, rol); 
        this.historialSesiones = historialSesiones != null ? historialSesiones : new ArrayList<>();
    }
    
    // Constructor simplificado (NUEVO - para VistaRegistro)
    public Estudiante(int idUsuario, String nombre, String correo, String contrasena, Rol rol) {
        super(idUsuario, nombre, correo, contrasena, rol);
        this.historialSesiones = new ArrayList<>();
    }
    
    public void agendarSesion(Sesion sesion) {
        if (historialSesiones == null) {
            historialSesiones = new ArrayList<>();
        }
        historialSesiones.add(sesion);
    }

    public ArrayList<Sesion> getHistorialSesiones() {
        return historialSesiones != null ? historialSesiones : new ArrayList<>();
    }

    public void verHistorial() {
        if (historialSesiones == null || historialSesiones.isEmpty()) {
            System.out.println("No tienes sesiones registradas.");
        } else {
            System.out.println("Historial de tus sesiones:");
            for (Sesion sesion : historialSesiones) {
                System.out.println(sesion);
            }
        }
    }
    
    @Override
    public String toString() {
        int numSesiones = (historialSesiones != null) ? historialSesiones.size() : 0;
        return super.toString() + ", Historial de sesiones: " + numSesiones;
    }
}