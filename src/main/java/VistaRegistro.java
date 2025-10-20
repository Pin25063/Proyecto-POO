import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class VistaRegistro extends VBox {
    private final TextField txtNombre = new TextField();
    private final TextField txtCorreo = new TextField();
    private final PasswordField txtContrasena = new PasswordField();
    private Runnable onCancel;

    public VistaRegistro() {
        setPadding(new Insets(24));
        setSpacing(16);
        setAlignment(Pos.CENTER);
        setMaxWidth(460);

        Label titulo = new Label("Registro de Usuario");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        txtNombre.setPromptText("Nombre completo");
        txtCorreo.setPromptText("correo@uvg.edu.gt");
        txtContrasena.setPromptText("Contraseña");

        getChildren().addAll(
            titulo,
            txtNombre,
            txtCorreo,
            txtContrasena
        );
    }

    public void setOnCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }

    public void setControlador(ControladorPrincipal controlador) {
        
    }
}