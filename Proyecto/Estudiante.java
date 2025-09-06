import java.util.ArrayList;

public class Estudiante extends Usuario {
private ArrayList<Sesion> historialSesiones;
    public Estudiante(int idUsuario, String nombre, String correo, String contrasena, Rol rol, ArrayList<Sesion> historialSesiones) {
        super(idUsuario, nombre, correo, contrasena, rol); 
        this.historialSesiones = historialSesiones;
    };
}
