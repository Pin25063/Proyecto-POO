public class Resena {
    private String autor;
    private int calificacion;
    private String comentario;

    public Resena(String autor, int calificacion, String comentario) {
        this.autor = autor;
        this.calificacion = calificacion;
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Reseña de " + autor + " - Calificación: " + calificacion + " Comentario: " + comentario;
    }
}
