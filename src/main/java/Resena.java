public class Resena {
    private String autor;
    private int calificacion;
    private String comentario;
    
    public Resena(String autor, int calificacion, String comentario) {
        this.autor = autor;
        this.calificacion = calificacion;
        this.comentario = comentario;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public int getCalificacion() {
        return calificacion;
    }
    
    public String getComentario() {
        return comentario;
    }
    
    @Override
    public String toString() {
        String estrellas = "★".repeat(calificacion) + "☆".repeat(5 - calificacion);
        return String.format("%s (%s)\n\"%s\"\n— %s", 
            estrellas, 
            calificacion + "/5", 
            comentario != null ? comentario : "Sin comentario", 
            autor != null ? autor : "Anónimo");
    }
}