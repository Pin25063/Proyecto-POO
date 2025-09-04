public class Usuario {
    private final int idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private Rol rol;
    //Constructor del objeto Usuario
    public Usuario(int idUsuario, String nombre, String correo, String contrasena, Rol rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }
    //Metodos getters
    public int getIdUsuario() { 
        return idUsuario; 
    }
    public String getNombre() { 
        return nombre; 
    }
    public String getCorreo() { 
        return correo; 
    }
    public String getContrasena() { 
        return contrasena; 
    }
    public Rol getRol() { 
        return rol; 
    }
}