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
        return this.idUsuario; 
    }
    public String getNombre() { 
        return this.nombre; 
    }
    public String getCorreo() { 
        return this.correo; 
    }
    public String getContrasena() { 
        return this.contrasena; 
    }
    public Rol getRol() { 
        return this.rol; 
    }

    public boolean verificarContrasena(String contrasena){
        return this.contrasena.equals(contrasena);
    }

    @Override
    public String toString() {
        return "Usuario{ " + idUsuario + ", " + nombre + ", " + rol + "}";
    }
}