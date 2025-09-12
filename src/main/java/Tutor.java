import java.util.ArrayList;

public class Tutor extends Usuario {
    private ArrayList<String> materias;
    //private ArrayList<Horario> disponibilidad; //Horario todavia no esta creado
    private ArrayList<Resena> resenas;
    private double tarifa;

    // Constructor
    public Tutor(int idUsuario, String nombre, String correo, String contrasena, Rol rol, ArrayList<String> materias, double tarifa) {
        super(idUsuario, nombre, correo, contrasena, rol);
        this.materias = materias;
        this.tarifa = tarifa;
    }

    //Cambiar los estados de sesion
    public void aceptarSolicitud(Sesion sesion) {
        sesion.setEstado(EstadoSesion.AGENDADA);
    }

    public void rechazarSolicitud(Sesion sesion) {
        sesion.setEstado(EstadoSesion.NEGADA);
    }
    
    //Eliminados getters redundantes 

    public ArrayList<String> getMaterias() {
        return this.materias;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    @Override
    public String toString() {
        return super.toString() + ", Materias: " + materias + ", Tarifa: " + tarifa;
    }
}