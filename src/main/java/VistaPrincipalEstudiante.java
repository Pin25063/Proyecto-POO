import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class VistaPrincipalEstudiante extends VBox {
    
    private Estudiante estudianteActual;
    private ControladorPrincipal controlador;
    
    // Labels para mostrar información
    private Label lblNombre, lblCorreo, lblId;
    
    // Botones de acción
    private Button btnEditarPerfil, btnBuscarTutores, btnVerHistorial, btnAgendarSesion;
    
    // Constructor
    public VistaPrincipalEstudiante(Estudiante estudiante, ControladorPrincipal controlador) {
        this.estudianteActual = estudiante;
        this.controlador = controlador;
        
        configurarVista();
    }
    
    private void configurarVista() {
        setPadding(new Insets(24));
        setSpacing(16);
        setAlignment(Pos.TOP_CENTER);
        setFillWidth(true);
        
        // Título de bienvenida
        Label titulo = new Label("Bienvenido, " + estudianteActual.getNombre());
        titulo.getStyleClass().add("titulo");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        getChildren().add(titulo);
    }   
}
