import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.regex.Pattern;

public class LoginVista extends VBox {

    private final Label title = new Label("Iniciar Sesión");
    private final TextField txtCorreo = new TextField();
    private final PasswordField txtPass = new PasswordField();

    // Es un toggle para mostrar/ocultar contraseña
    private final TextField txtPassVisible = new TextField();
    private final CheckBox chkMostrar = new CheckBox("Mostrar");

    private final Button btnIngresar = new Button("Ingresar");
    private final Button btnCrarCuenta = new Button("Crear Cuenta");
    private final Button btnLimpiar = new Button("Limpiar");

    // Esta es una fila de contraseña que iremos reemplazando 
    private HBox filaPass;
    
    // validacion de correo 
    private static final Pattern EMAIL = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");


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

        // estado inicial del visible
        txtPassVisible.setManaged(false);
        txtPassVisible.setVisible(false);

        // esto sirve para sincornizar los textos de ambos campos
        txtPassVisible.textProperty().bindBidirectional(txtPass.textProperty());

        // fila de contraseña inicia con el campo oculto    
        filaPass = new HBox(8, txtPass, chkMostrar);
        filaPass.setAlignment(Pos.CENTER);

        HBox acciones = new HBox(10, btnIngresar, btnCrarCuenta, btnLimpiar);
        acciones.setAlignment(Pos.CENTER);

        // Boton por defecto y cancel asi como el foco inicial
        btnIngresar.setDefaultButton(true);
        btnLimpiar.setCancelButton(true);
        txtCorreo.requestFocus();


        //tooltips
        txtCorreo.setTooltip(new Tooltip("Usa tu correo institucional (por ejemplo nombre@uvg.edu.gt)"));
        btnIngresar.setTooltip(new Tooltip("Inicia sesión"));
        btnCrarCuenta.setTooltip(new Tooltip("Crear cuenta (todavia falta que lo integren"));
        btnLimpiar.setTooltip(new Tooltip("Limpiar campos"));
        chkMostrar.setTooltip(new Tooltip("Mostrar/ocultar contraseña"));

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


        // este es el toggle de mostrar y ocultar
        chkMostrar.selectedProperty().addListener((o, was, show) -> {
            int idx = getChildren().indexOf(filaPass);
            if (show) {
                HBox nueva = new HBox(8, txtPassVisible, chkMostrar);
                nueva.setAlignment(Pos.CENTER);
                getChildren().set(idx, nueva);
                filaPass = nueva;
                txtPassVisible.setManaged(true);
                txtPassVisible.setVisible(true);
            } else {
                HBox nueva = new HBox(8, txtPass, chkMostrar);
                nueva.setAlignment(Pos.CENTER);
                getChildren().set(idx, nueva);
                filaPass = nueva;
                txtPassVisible.setManaged(false);
                txtPassVisible.setVisible(false);
            }
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
        if (!EMAIL.matcher(txtCorreo.getText().trim()).find()) {
            mostrarError("Correo inválido", "Usa un formato de correo válido (ej. nombre@uvg.edu.gt).");
            return;
        }
        if (txtPass.getText().isBlank()) {
            mostrarError("Contraseña vacía", "Ingresa tu contraseña.");
            return;
        }
        mostrarInfo("Acción pendiente", "La autenticación se conectará al controlador.");
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

