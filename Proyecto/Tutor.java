import java.util.ArrayList;

public class Tutor extends Usuario {
    private ArrayList<String> materias;
    private ArrayList<Horario> disponibilidad; //Horario todavia no esta creado
    private ArrayList<Resena> resenas;
    private final double tarifa;

    // Constructor
    public Tutor(int idUsuario, String nombre, String correo, String contrasena, Rol rol, ArrayList<String> materias, double tarifa) {
        super(idUsuario, nombre, correo, contrasena, rol);
        this.materias = materias;
        this.tarifa = tarifa;
    }

}
