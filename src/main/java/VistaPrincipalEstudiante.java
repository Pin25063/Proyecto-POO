import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList; 

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
    
    // NUEVO: Sección de información del perfil
    VBox seccionPerfil = crearSeccionPerfil();
    
    // NUEVO: Sección de historial de sesiones
    VBox seccionHistorial = crearSeccionHistorial();
    
    getChildren().addAll(titulo, seccionPerfil, seccionHistorial);
}

// MÉTODO NUEVO: Crear sección de perfil
private VBox crearSeccionPerfil() {
    VBox seccion = new VBox(10);
    seccion.setPadding(new Insets(15));
    seccion.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5;");
    seccion.setMaxWidth(600);
    
    Label tituloPerfil = new Label("📋 Información del Perfil");
    tituloPerfil.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    
    lblId = new Label("ID: " + estudianteActual.getIdUsuario());
    lblNombre = new Label("Nombre: " + estudianteActual.getNombre());
    lblCorreo = new Label("Correo: " + estudianteActual.getCorreo());
    
    lblId.setStyle("-fx-font-size: 14px;");
    lblNombre.setStyle("-fx-font-size: 14px;");
    lblCorreo.setStyle("-fx-font-size: 14px;");
    
    seccion.getChildren().addAll(tituloPerfil, lblId, lblNombre, lblCorreo);
    
    return seccion;
}

// MÉTODO NUEVO: Crear sección de historial
private VBox crearSeccionHistorial() {
    VBox seccion = new VBox(10);
    seccion.setPadding(new Insets(15));
    seccion.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 5;");
    seccion.setMaxWidth(600);
    
    Label tituloHistorial = new Label("Últimas Sesiones");
    tituloHistorial.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    
    ListView<String> listaSesiones = new ListView<>();
    listaSesiones.setPrefHeight(150);
    
    ArrayList<Sesion> historial = estudianteActual.getHistorialSesiones();
    
    if (historial == null || historial.isEmpty()) {
        listaSesiones.getItems().add("No tienes sesiones registradas aún");
    } else {
        // Mostrar las últimas 5 sesiones
        int limite = Math.min(5, historial.size());
        for (int i = historial.size() - 1; i >= historial.size() - limite; i--) {
            Sesion s = historial.get(i);
            String texto = String.format("%s - %s (%s)", 
                s.getMateria(), 
                s.getFechaHora(), 
                s.getEstado());
            listaSesiones.getItems().add(texto);
        }
    }
    
    seccion.getChildren().addAll(tituloHistorial, listaSesiones);
    
    return seccion;
 }
}