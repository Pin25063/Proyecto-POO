import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LoginVista extends VBox {

    private final Label title = new Label("Iniciar Sesión");
    private final TextField txtCorreo = new TextField();
    private final PasswordField txtPass = new PasswordField();
    private final Button btnIngresar = new Button("Ingresar");
    private final Button btnCrarCuenta = new Button("Crear Cuenta");
    private final Button btnLimpiar = new Button("Limpiar");

    public LoginVista() {   
        setPadding(new Insets(24));
        setSpacing(16);
        setAlignment(Pos.CENTER);

        title.getStyleClass().add("titulo");
        txtCorreo.setPromptText("correo@uvg.edu.gt");
        txtPass.setPromptText("Contraseña");

        txtCorreo.setPrefWidth(280);
        txtPass.setPrefWidth(280);
        btnIngresar.setPrefWidth(100);
        btnCrarCuenta.setPrefWidth(110);
        btnLimpiar.setPrefWidth(90);

        HBox acciones = new HBox(10, btnIngresar, btnCrarCuenta, btnLimpiar);
        acciones.setAlignment(Pos.CENTER);

        getChildren().addAll(title, txtCorreo, txtPass, acciones);
    }
}

