import java.util.ArrayList;

public class Estudiante extends Usuario {
    private ArrayList<Sesion> historialSesiones;
    public Estudiante(int idUsuario, String nombre, String correo, String contrasena) {
        super(idUsuario, nombre, correo, contrasena, Rol.ESTUDIANTE); 
        this.historialSesiones = new ArrayList<>();
    };
    
    public void agendarSesion(Sesion sesion) {
        historialSesiones.add(sesion);  // Agregar la sesión al historial
    }

    public ArrayList<Sesion> getHistorialSesiones() {
        return historialSesiones;
    }

    public void verHistorial() {
        if (historialSesiones.isEmpty()) {
            System.out.println("No tienes sesiones registradas.");
        } else {
            System.out.println("Historial de tus sesiones:");
            for (Sesion sesion : historialSesiones) {
                System.out.println(sesion);
            }
        }
    }
    //Los getters en Usuario ya estan heredados a Estudiante
    @Override
    public String toString() {
        return super.toString() + ", Historial de sesiones: " + historialSesiones.size(); //Verificar si se utilizara asi
    }
}