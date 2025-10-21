import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VistaRegistro extends VBox {
    //Campos de entrada de texto
    private final TextField txtNombre = new TextField();
    private final TextField txtCorreo = new TextField();
    private final PasswordField txtContrasena = new PasswordField();

    //Combox para seleccionar rol
    private final ComboBox<Rol> comboRol = new ComboBox<>();

    //Botones
    private final Button btnRegistrar = new Button("Registrar");
    private final Button btnCancelar = new Button("Cancelar");

    //Referencias al controlador y navegador
    private Runnable onCancel;
    private ControladorPrincipal controlador;

    public VistaRegistro() {
        //Estilo del layout
        setPadding(new Insets(24));
        setSpacing(16);
        setAlignment(Pos.CENTER);
        setFillWidth(false);
        setMaxWidth(460);

        //Titulo de la pantalla
        Label titulo = new Label("Registro de Usuario");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        //Placeholder visual de los campos
        txtNombre.setPromptText("Nombre completo");
        txtCorreo.setPromptText("correo@uvg.edu.gt");
        txtContrasena.setPromptText("Contraseña");

        comboRol.getItems().setAll(Rol.values());
        comboRol.setPromptText("Selecciona tu rol");

        // Contenedor para los botones
        HBox botones = new HBox(10, btnRegistrar, btnCancelar);
        botones.setAlignment(Pos.CENTER);
        
        //Se agregan todos los componenetes a la vista
        getChildren().addAll(
            titulo,
            txtNombre,
            txtCorreo,
            txtContrasena,
            comboRol,
            botones
        );

        // Al presionar Cancelar, se regresa a login
        btnCancelar.setOnAction(e -> onCancel.run());

        // Por ahora el botón Registrar solo muestra mensaje
        btnRegistrar.setOnAction(e -> {
            mostrarInfo("Función pendiente", "La funcionalidad de registro se agregará más adelante.");
        });
    }
    
    //Permite definir que hace cuando se presiona cancelar (Main lo define)
    public void setOnCancel(Runnable r) {
        this.onCancel = r;
    }

    //Conecta el controlador principal
    public void setControlador(ControladorPrincipal controlador) {
        this.controlador = controlador;
    }

    // Metodo para mostrar alertas
    private void mostrarInfo(String header, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(header);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}