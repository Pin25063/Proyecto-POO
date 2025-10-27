import java.util.regex.Pattern;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
//Al guardar el archivo se extienden los imports en lugar de mantener el *

public class LoginVista extends VBox {

    Label title = new Label("Iniciar Sesión");
    private final TextField txtCorreo = new TextField();
    private final PasswordField txtPass = new PasswordField();

    // Es un toggle para mostrar/ocultar contraseña
    private final TextField txtPassVisible = new TextField();
    private final CheckBox chkMostrar = new CheckBox("Mostrar");

    private final Button btnIngresar = new Button("Ingresar");
    private final Button btnCrearCuenta = new Button("Crear Cuenta");
    private final Button btnLimpiar = new Button("Limpiar");

    // Esta es una fila de contraseña que iremos reemplazando 
    // (ahora usamos BorderPane para centrar el campo y mandar el toggle a la derecha)
    private BorderPane filaPass;

    private ControladorPrincipal controlador;

    // validacion de correo 
    private static final Pattern EMAIL = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

    //Variable runnable para la ventana de registro de usuario    
    private Runnable onCrearCuenta;

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
        txtPassVisible.setPrefWidth(280);
        btnIngresar.setPrefWidth(100);
        btnCrearCuenta.setPrefWidth(110);
        btnLimpiar.setPrefWidth(90);

        // estado inicial del visible
        txtPassVisible.setManaged(false);
        txtPassVisible.setVisible(false);

        // esto sirve para sincornizar los textos de ambos campos
        txtPassVisible.textProperty().bindBidirectional(txtPass.textProperty());

        // quitar texto del toggle y evitar mnemónicos (para que se vea solo la cajita)
        chkMostrar.setText("");
        chkMostrar.setMnemonicParsing(false);
        chkMostrar.setTooltip(new Tooltip("Mostrar/ocultar contraseña"));

        // fila de contraseña inicia con el campo oculto (centrado) y el toggle a la derecha
        filaPass = new BorderPane();
        filaPass.setPrefWidth(txtPass.getPrefWidth());
        filaPass.setMaxWidth(Region.USE_PREF_SIZE);
        filaPass.setCenter(txtPass);
        filaPass.setRight(chkMostrar);
        BorderPane.setAlignment(chkMostrar, Pos.CENTER_RIGHT);

        HBox acciones = new HBox(10, btnIngresar, btnCrearCuenta, btnLimpiar);
        acciones.setAlignment(Pos.CENTER);

        // Boton por defecto y cancel asi como el foco inicial
        btnIngresar.setDefaultButton(true);
        btnLimpiar.setCancelButton(true);
        txtCorreo.requestFocus();

        //tooltips
        txtCorreo.setTooltip(new Tooltip("Usa tu correo institucional (por ejemplo nombre@uvg.edu.gt)"));
        btnIngresar.setTooltip(new Tooltip("Inicia sesión"));
        btnCrearCuenta.setTooltip(new Tooltip("Crear cuenta nueva"));
        btnLimpiar.setTooltip(new Tooltip("Limpiar campos"));

        getChildren().addAll(title, txtCorreo, filaPass, acciones);

        //Validaciones en vivo 
        //en donde o = observador, a = antiguo, b = nuevo
        actualizarEstadoBoton();
        txtCorreo.textProperty().addListener((o, a, b) -> actualizarEstadoBoton());
        txtPass.textProperty().addListener((o, a, b) -> actualizarEstadoBoton());

        //Atajos y acciones basicas 
        btnIngresar.setOnAction(e -> intentarInicioSesion());
        btnLimpiar.setOnAction(e -> limpiarCampos());
        btnCrearCuenta.setOnAction(e -> onCrearCuenta.run()); // Cambiar de escena a VistaRegistro

        txtPass.setOnAction(e -> intentarInicioSesion()); // Enter en contraseña

        setOnKeyPressed(e -> { // ESC limpia
            if (e.getCode() == javafx.scene.input.KeyCode.ESCAPE) limpiarCampos();
        });

        // este es el toggle de mostrar y ocultar
        // (ahora cambiamos el centro del BorderPane para alternar entre PasswordField y TextField)
        chkMostrar.selectedProperty().addListener((o, was, show) -> {
            filaPass.setCenter(show ? txtPassVisible : txtPass);
            txtPassVisible.setManaged(show);
            txtPassVisible.setVisible(show);
        });
    }

    // Habilita o deshabilita el boton de ingresar segun los campos
    private void actualizarEstadoBoton() {
        boolean ok = !txtCorreo.getText().isBlank()
                && EMAIL.matcher(txtCorreo.getText().trim()).find()
                && !txtPass.getText().isBlank();
        btnIngresar.setDisable(!ok);
    }

    // Intenta iniciar sesion con las credenciales proporcionadas
    private void intentarInicioSesion(){
        String correo = txtCorreo.getText().trim();
        String pass = txtPass.getText();

        if (!EMAIL.matcher(correo).find()) {
            mostrarError("Correo inválido", "Usa un formato de correo válido (ej. nombre@uvg.edu.gt).");
            return;
        }
        if (pass.isBlank()) {
            mostrarError("Contraseña vacía", "Ingresa tu contraseña.");
            return;
        }

        // Conexión con el controlador (si ya fue inyectado desde Main)
        if (controlador != null) {
            controlador.manejarLogin(correo, pass);
        } else {
            mostrarInfo("Acción pendiente", "La autenticación se conectará al controlador.");
        }
    }

    public void setControlador(ControladorPrincipal controlador) {
        this.controlador = controlador;
    }

    // Muestra una alerta de error
    public void mostrarError(String encabezado, String contenido){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(encabezado);
        a.setContentText(contenido);
        a.showAndWait();
    }

    // Muestra una alerta de información
    public void mostrarInfo(String encabezado, String contenido){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(encabezado);
        a.setContentText(contenido);
        a.showAndWait();
    }

    // Limpia los campos 
    public void limpiarCampos(){
        txtCorreo.clear();
        txtPass.clear();
        txtCorreo.requestFocus();
    }

    //Setter de la variable de la ventana de registro de cuenta
    public void setOnCrearCuenta(Runnable onCrearCuenta) {
    this.onCrearCuenta = onCrearCuenta;
    }
}

