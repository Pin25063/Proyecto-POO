import java.util.ArrayList;

public class Estudiante extends Usuario {
private ArrayList<Sesion> historialSesiones;
    public Estudiante(int idUsuario, String nombre, String correo, String contrasena, Rol rol, ArrayList<Sesion> historialSesiones) {
        super(idUsuario, nombre, correo, contrasena, rol); 
        this.historialSesiones = historialSesiones;
    };
    
    public void agendarSesion(Sesion sesion) {
        historialSesiones.add(sesion);  // Agregar la sesión al historial
    }

    public ArrayList<Sesion> getHistorialSesiones() {
        return historialSesiones;
    }

    public void setHistorialSesiones(ArrayList<Sesion> historialSesiones) {
        this.historialSesiones = historialSesiones;
    }
    //Los getters en Usuario ya estan heredados a Estudiante
    @Override
    public String toString() {
        return super.toString() + ", Historial de sesiones: " + historialSesiones.size(); //Verificar si se utilizara asi
    }
}