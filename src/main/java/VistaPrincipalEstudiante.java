import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList; 
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    
    Label titulo = new Label("Bienvenido, " + estudianteActual.getNombre());
    titulo.getStyleClass().add("titulo");
    titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
    
    VBox seccionPerfil = crearSeccionPerfil();
    
    // Sección de botones
    HBox seccionBotones = crearSeccionBotones();
    
    VBox seccionHistorial = crearSeccionHistorial();
    
    getChildren().addAll(titulo, seccionPerfil, seccionBotones, seccionHistorial);
}

// Crear sección de perfil
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

// Crear sección de historial
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

 // Crear barra de botones
    private HBox crearSeccionBotones() {
    HBox contenedor = new HBox(12);
    contenedor.setAlignment(Pos.CENTER);
    contenedor.setPadding(new Insets(10, 0, 10, 0));
    
    btnEditarPerfil = new Button("Editar Perfil");
    btnBuscarTutores = new Button("Buscar Tutores");
    btnAgendarSesion = new Button("Agendar Sesión");
    btnVerHistorial = new Button("Ver Historial");
    
    // Estilo de botones
    String estiloBoton = "-fx-font-size: 13px; -fx-padding: 8 15 8 15;";
    btnEditarPerfil.setStyle(estiloBoton);
    btnBuscarTutores.setStyle(estiloBoton);
    btnAgendarSesion.setStyle(estiloBoton);
    btnVerHistorial.setStyle(estiloBoton);
    
    // Conectar acciones (por ahora a métodos que crearemos)
    btnEditarPerfil.setOnAction(e -> abrirEdicionPerfil());
    btnBuscarTutores.setOnAction(e -> abrirBusquedaTutores());
    btnAgendarSesion.setOnAction(e -> abrirAgendamiento());
    btnVerHistorial.setOnAction(e -> mostrarHistorialCompleto());
    
    contenedor.getChildren().addAll(btnEditarPerfil, btnBuscarTutores, btnAgendarSesion, btnVerHistorial);
    
    return contenedor;
    
    }

    // MÉTODO NUEVO: Abrir diálogo de edición
private void abrirEdicionPerfil() {
    Stage dialogStage = new Stage();
    dialogStage.setTitle("Editar Perfil");
    
    VBox contenido = new VBox(15);
    contenido.setPadding(new Insets(20));
    contenido.setAlignment(Pos.CENTER);
    
    Label titulo = new Label("Editar Información Personal");
    titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    
    // Campos editables
    Label lblNuevoNombre = new Label("Nombre:");
    TextField txtNombre = new TextField(estudianteActual.getNombre());
    txtNombre.setPrefWidth(300);
    
    Label lblNuevoCorreo = new Label("Correo:");
    TextField txtCorreo = new TextField(estudianteActual.getCorreo());
    txtCorreo.setPrefWidth(300);
    txtCorreo.setDisable(true);
    txtCorreo.setTooltip(new Tooltip("El correo no puede modificarse"));
    
    Label lblNuevaPass = new Label("Nueva Contraseña (opcional):");
    PasswordField txtPass = new PasswordField();
    txtPass.setPrefWidth(300);
    txtPass.setPromptText("Dejar vacío para mantener la actual");
    
    // Botones
    HBox botones = new HBox(10);
    botones.setAlignment(Pos.CENTER);
    
    Button btnGuardar = new Button("Guardar Cambios");
    Button btnCancelar = new Button("Cancelar");
    
    btnGuardar.setStyle("-fx-font-size: 13px; -fx-padding: 8 15 8 15;");
    btnCancelar.setStyle("-fx-font-size: 13px; -fx-padding: 8 15 8 15;");
    
    btnGuardar.setOnAction(e -> {
        String nuevoNombre = txtNombre.getText().trim();
        String nuevaPass = txtPass.getText().trim();
        
        if (nuevoNombre.isEmpty()) {
            mostrarError("Error", "El nombre no puede estar vacío");
            return;
        }
        
        if (!nuevoNombre.equals(estudianteActual.getNombre())) {
            mostrarInfo("Edición", "Nombre actualizado correctamente");
            lblNombre.setText("Nombre: " + nuevoNombre);
        }
        
        if (!nuevaPass.isEmpty()) {
            mostrarInfo("Edición", "Contraseña actualizada correctamente");
        }
        
        dialogStage.close();
    });
    
    btnCancelar.setOnAction(e -> dialogStage.close());
    
    botones.getChildren().addAll(btnGuardar, btnCancelar);
    
    contenido.getChildren().addAll(
        titulo,
        new Separator(),
        lblNuevoNombre, txtNombre,
        lblNuevoCorreo, txtCorreo,
        lblNuevaPass, txtPass,
        new Separator(),
        botones
    );
    
    Scene scene = new Scene(contenido, 400, 400);
    dialogStage.setScene(scene);
    dialogStage.show();
}

// MÉTODOS AUXILIARES para alertas
private void mostrarError(String titulo, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
}

private void mostrarInfo(String titulo, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
}

}