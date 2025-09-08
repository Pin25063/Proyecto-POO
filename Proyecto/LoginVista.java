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

        //layout base  
        setPadding(new Insets(24));
        setSpacing(16);
        setAlignment(Pos.CENTER);
        setFillWidth(false);
        setMaxWidth(460);

        //Controles
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

        // Boton por defecto y cancel asi como el foco inicial
        btnIngresar.setDefaultButton(true);
        btnLimpiar.setCancelButton(true);
        txtCorreo.requestFocus();


        getChildren().addAll(title, txtCorreo, txtPass, acciones);


        //Validaciones en vivo 
        //en donde o = observador, a = antiguo, b = nuevo
        actualizarEstadoBoton();
        txtCorreo.textProperty().addListener((o, a, b) -> actualizarEstadoBoton());
        txtPass.textProperty().addListener((o, a, b) -> actualizarEstadoBoton());

        //Atajos y acciones basicas 
        btnIngresar.setOnAction(e -> intentarInicioSesion());
        btnLimpiar.setOnAction(e -> limpiarCampos());
        btnCrarCuenta.setOnAction(e -> mostrarInfo("Registro", "La pantalla de registro se implementará más adelante"));

        txtPass.setOnAction(e -> intentarInicioSesion()); // Enter en contraseña

        setOnKeyPressed(e -> { // ESC limpia
            if (e.getCode() == javafx.scene.input.KeyCode.ESCAPE) limpiarCampos();
        });
    }

    // Habilita o deshabilita el boton de ingresar segun los campos
    private void actualizarEstadoBoton() {
        boolean ok = !txtCorreo.getText().isBlank() && !txtPass.getText().isBlank();
        btnIngresar.setDisable(!ok);
    }

    // Intenta iniciar sesion con las credenciales proporcionadas
    private void intentarInicioSesion(){
        if (txtCorreo.getText().isBlank() || txtPass.getText().isBlank()) {
            mostrarError("Sus credenciales estan incompletas", "Ingresa tu correo y contraseña");
            return;
        }
        // la autenticación real la hace el Controlador
        mostrarInfo("Acción pendiente", "La autenticación se concetara al controlador");
    }
    
    // Muestra una alerta de error
    private void mostrarError(String encabezado, String contenido){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(encabezado);
        a.setContentText(contenido);
        a.showAndWait();
    }

    // Muestra una alerta de información
    private void mostrarInfo(String encabezado, String contenido){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(encabezado);
        a.setContentText(contenido);
        a.showAndWait();
    }

    // Limpia los campos 
    private void limpiarCampos(){
        txtCorreo.clear();
        txtPass.clear();
        txtCorreo.requestFocus();
    }
}

