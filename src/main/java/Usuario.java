public class Usuario {
    private int idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private Rol rol;
    //Constructor del objeto Usuario
    public Usuario(int idUsuario, String nombre, String correo, String contrasena, Rol rol) {
        // Programación defensiva
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (correo == null || !correo.contains("@")) {
            throw new IllegalArgumentException("El formato del correo electrónico es inválido");
        }
        if (contrasena == null || contrasena.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede estar vacío");
        }

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

    // SETTERS
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void setCorreo(String correo) {
        if (correo == null || !correo.contains("@")) {
            throw new IllegalArgumentException("El formato del correo electrónico es inválido.");
        }
        this.correo = correo.trim().toLowerCase();
    }

    public void setContrasena(String contrasena) {
        if (contrasena == null || contrasena.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        this.contrasena = contrasena;
    }

    // METODO: verificar que coincida la contraseña proporcionada con la almacenada
    public boolean verificarContrasena(String contrasena){
        return this.contrasena.equals(contrasena);
    }

    // METODO: para obtener el perfil básico del usuario como string
    public String getPerfil() {
        return String.format("ID: %d | Nombre: %s | Correo: %s | Rol: %s", idUsuario, nombre, correo, rol);
        // cada %s es un marcador de posición que es reemplazado por los Strings de abajo
        // cada %d es un marcador de posición que indica que se debe insertar un valor entero en esa posición
    }

    // METODO PERSISTENCIA: Convierte el usuario a formato CSV
    public String toCSV() {
        return String.format("%d;%s;%s;%s;%s",idUsuario, nombre, correo, contrasena, rol);
        // cada %s es un marcador de posición que es reemplazado por los Strings de abajo
        // cada %d es un marcador de posición que indica que se debe insertar un valor entero en esa posición
    }


    @Override
    public String toString() {
        return "Usuario{ " + idUsuario + ", " + nombre + ", " + rol + "}";
    }

    // Al método predeterminado "equals" que dice que dos objetos son iguales solo si son exactamente el mismo objeto en memoria.
    // se le hace OVERRIDE para agregar funcionalidades
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        // se verifica que los objetos estén apuntando al mismo espacio de memoria
        if (obj == null || getClass() != obj.getClass()) return false;
        // se valida que la variable del tipo objeto no apunte a null para evitar errores
        Usuario otro = (Usuario) obj;
        // se hace casting para acceder a los atributos del objeto
        return idUsuario == otro.idUsuario;
    }
    
}