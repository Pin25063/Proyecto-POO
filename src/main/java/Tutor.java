import java.util.ArrayList;
import java.util.List;

public class Tutor extends Usuario {
    private ArrayList<String> materias;
    private ArrayList<Resena> resenas;
    private double tarifa;

    // Constructor original (mantener compatibilidad)
    public Tutor(int idUsuario, String nombre, String correo, String contrasena, Rol rol, ArrayList<String> materias, double tarifa) {
        super(idUsuario, nombre, correo, contrasena, rol);
        this.materias = materias != null ? materias : new ArrayList<>();
        this.resenas = new ArrayList<>();
        this.tarifa = tarifa;
    }
    
    // Constructor nuevo que acepta List (para VistaRegistro)
    public Tutor(int idUsuario, String nombre, String correo, String contrasena, Rol rol, List<String> materias, double tarifa) {
        super(idUsuario, nombre, correo, contrasena, rol);
        this.materias = new ArrayList<>(materias);
        this.resenas = new ArrayList<>();
        this.tarifa = tarifa;
    }

    // Métodos de gestión de sesiones
    public void aceptarSolicitud(Sesion sesion) {
        sesion.setEstado(EstadoSesion.AGENDADA);
    }

    public void rechazarSolicitud(Sesion sesion) {
        sesion.setEstado(EstadoSesion.NEGADA);
    }
    
    // Getters
    public ArrayList<String> getMaterias() {
        return this.materias;
    }

    public double getTarifa() {
        return tarifa;
    }
    
    public ArrayList<Resena> getResenas() {
        return resenas != null ? resenas : new ArrayList<>();
    }
    
    public int getTotalResenas() {
        return resenas != null ? resenas.size() : 0;
    }
    
    public double calcularPromedioCalificacion() {
        if (resenas == null || resenas.isEmpty()) return 0.0;
        
        double suma = 0.0;
        for (Resena resena : resenas) {
            suma += resena.getCalificacion();
        }
        return suma / resenas.size();
    }

    // Setters
    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }
    
    public void agregarResena(Resena resena) {
        if (resenas == null) resenas = new ArrayList<>();
        resenas.add(resena);
    }

    @Override
    public String toString() {
        return super.toString() + ", Materias: " + materias + ", Tarifa: Q" + tarifa;
    }
}