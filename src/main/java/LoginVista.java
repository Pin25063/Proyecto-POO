import java.util.regex.Pattern;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginVista extends VBox {

    private final TextField txtCorreo = new TextField();
    private final PasswordField txtPass = new PasswordField();
    private final TextField txtPassVisible = new TextField();
    private final CheckBox chkMostrar = new CheckBox();

    private final Button btnIngresar = new Button("Iniciar Sesión");
    private final Button btnCrearCuenta = new Button("Crear Cuenta");
    private final Button btnLimpiar = new Button("Limpiar Campos");

    private BorderPane filaPass;
    private ControladorPrincipal controlador;
    private Runnable onCrearCuenta;

    private static final Pattern EMAIL = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

    public LoginVista() { 
        // Layout base con degradado
        setPadding(new Insets(30));
        setSpacing(20);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: linear-gradient(to bottom, #ecf0f1, #bdc3c7);");
        
        // Tarjeta central para el formulario
        VBox tarjetaLogin = new VBox(18);
        tarjetaLogin.setPadding(new Insets(35));
        tarjetaLogin.setAlignment(Pos.CENTER);
        tarjetaLogin.setMaxWidth(420);
        tarjetaLogin.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 15; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0, 0, 3);"
        );

        // Título principal
        Label titulo = new Label("Gestor de Tutorías UVG");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titulo.setStyle("-fx-text-fill: #319e34ff;");
        
        // Subtítulo
        Label subtitulo = new Label("Iniciar Sesión");
        subtitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        subtitulo.setStyle("-fx-text-fill: #7f8c8d;");

        // Separador decorativo
        Separator separador = new Separator();
        separador.setMaxWidth(300);

        // Campo de correo con label
        VBox contenedorCorreo = new VBox(8);
        Label lblCorreo = new Label("📧 Correo Institucional");
        lblCorreo.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lblCorreo.setStyle("-fx-text-fill: #34495e;");
        // Campo de texto para correo
        txtCorreo.setPromptText("correo@uvg.edu.gt");
        txtCorreo.setPrefWidth(340);
        txtCorreo.setPrefHeight(40);
        txtCorreo.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-background-radius: 8; " +
            "-fx-border-color: #bdc3c7; " +
            "-fx-border-radius: 8; " +
            "-fx-border-width: 2;"
        );

        txtCorreo.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                txtCorreo.setStyle(
                    "-fx-font-size: 14px; " +
                    "-fx-background-radius: 8; " +
                    "-fx-border-color: #3498db; " +
                    "-fx-border-radius: 8; " +
                    "-fx-border-width: 2;"
                );
            } else {
                txtCorreo.setStyle(
                    "-fx-font-size: 14px; " +
                    "-fx-background-radius: 8; " +
                    "-fx-border-color: #bdc3c7; " +
                    "-fx-border-radius: 8; " +
                    "-fx-border-width: 2;"
                );
            }
        });

        txtCorreo.setTooltip(new Tooltip("Ingresa tu correo institucional"));
        
        contenedorCorreo.getChildren().addAll(lblCorreo, txtCorreo);

        // Campo de contraseña con label
        VBox contenedorPass = new VBox(8);
        Label lblPass = new Label("🔒 Contraseña");
        lblPass.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lblPass.setStyle("-fx-text-fill: #34495e;");
        // Campo de texto oculto para contraseña
        txtPass.setPromptText("Ingresa tu contraseña");
        txtPass.setPrefWidth(340);
        txtPass.setPrefHeight(40);
        txtPass.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-background-radius: 8; " +
            "-fx-border-color: #bdc3c7; " +
            "-fx-border-radius: 8; " +
            "-fx-border-width: 2;"
        );
        // Campo de texto visible para mostrar contraseña
        txtPassVisible.setPromptText("Ingresa tu contraseña");
        txtPassVisible.setPrefWidth(340);
        txtPassVisible.setPrefHeight(40);
        txtPassVisible.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-background-radius: 8; " +
            "-fx-border-color: #bdc3c7; " +
            "-fx-border-radius: 8; " +
            "-fx-border-width: 2;"
        );

        txtPass.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                txtPass.setStyle(
                    "-fx-font-size: 14px; " +
                    "-fx-background-radius: 8; " +
                    "-fx-border-color: #3498db; " +
                    "-fx-border-radius: 8; " +
                    "-fx-border-width: 2;"
                );
            } else {
                txtPass.setStyle(
                    "-fx-font-size: 14px; " +
                    "-fx-background-radius: 8; " +
                    "-fx-border-color: #bdc3c7; " +
                    "-fx-border-radius: 8; " +
                    "-fx-border-width: 2;"
                );
            }
        });
        
        txtPassVisible.setManaged(false);
        txtPassVisible.setVisible(false);
        txtPassVisible.textProperty().bindBidirectional(txtPass.textProperty());

        // BorderPane para contraseña y checkbox
        filaPass = new BorderPane();
        filaPass.setCenter(txtPass);
        
        // Checkbox de mostrar contraseña
        chkMostrar.setText("Mostrar contraseña");
        chkMostrar.setFont(Font.font("Arial", 11));
        chkMostrar.setStyle("-fx-text-fill: #7f8c8d;");
        
        HBox contenedorCheckbox = new HBox(chkMostrar);
        contenedorCheckbox.setAlignment(Pos.CENTER_RIGHT);
        contenedorCheckbox.setPadding(new Insets(5, 0, 0, 0));
        
        contenedorPass.getChildren().addAll(lblPass, filaPass, contenedorCheckbox);

        // Botones con colores distintivos
        btnIngresar.setPrefWidth(340);
        btnIngresar.setPrefHeight(45);
        btnIngresar.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        btnIngresar.setStyle(
            "-fx-background-color: #27ae60; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand;"
        );
        btnIngresar.setTooltip(new Tooltip("Ingresar al sistema"));
        // Inicialmente deshabilitado
        btnCrearCuenta.setPrefWidth(340);
        btnCrearCuenta.setPrefHeight(45);
        btnCrearCuenta.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        btnCrearCuenta.setStyle(
            "-fx-background-color: #3498db; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand;"
        );
        btnCrearCuenta.setTooltip(new Tooltip("Registrar nueva cuenta"));
        // Botón limpiar campos
        btnLimpiar.setPrefWidth(340);
        btnLimpiar.setPrefHeight(40);
        btnLimpiar.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        btnLimpiar.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-text-fill: #95a5a6; " +
            "-fx-border-color: #95a5a6; " +
            "-fx-border-width: 2; " +
            "-fx-border-radius: 8; " +
            "-fx-cursor: hand;"
        );
        btnLimpiar.setTooltip(new Tooltip("Limpiar todos los campos"));

        // Efectos hover para botones
        btnIngresar.setOnMouseEntered(e -> btnIngresar.setStyle(
            "-fx-background-color: #229954; -fx-text-fill: white; -fx-background-radius: 8; -fx-cursor: hand;"
        ));
        btnIngresar.setOnMouseExited(e -> btnIngresar.setStyle(
            "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 8; -fx-cursor: hand;"
        ));
        
        btnCrearCuenta.setOnMouseEntered(e -> btnCrearCuenta.setStyle(
            "-fx-background-color: #2980b9; -fx-text-fill: white; -fx-background-radius: 8; -fx-cursor: hand;"
        ));
        btnCrearCuenta.setOnMouseExited(e -> btnCrearCuenta.setStyle(
            "-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 8; -fx-cursor: hand;"
        ));
        
        btnLimpiar.setOnMouseEntered(e -> btnLimpiar.setStyle(
            "-fx-background-color: #ecf0f1; -fx-text-fill: #7f8c8d; -fx-border-color: #7f8c8d; -fx-border-width: 2; -fx-border-radius: 8; -fx-cursor: hand;"
        ));
        btnLimpiar.setOnMouseExited(e -> btnLimpiar.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: #95a5a6; -fx-border-color: #95a5a6; -fx-border-width: 2; -fx-border-radius: 8; -fx-cursor: hand;"
        ));

        // Añadir elementos a la tarjeta
        tarjetaLogin.getChildren().addAll(
            titulo,
            subtitulo,
            separador,
            contenedorCorreo,
            contenedorPass,
            btnIngresar,
            btnCrearCuenta,
            btnLimpiar
        );

        getChildren().add(tarjetaLogin);

        // Configurar botones y validaciones
        btnIngresar.setDefaultButton(true);
        btnLimpiar.setCancelButton(true);
        txtCorreo.requestFocus();

        // Validaciones en vivo
        actualizarEstadoBoton();
        txtCorreo.textProperty().addListener((o, a, b) -> actualizarEstadoBoton());
        txtPass.textProperty().addListener((o, a, b) -> actualizarEstadoBoton());

        // Acciones
        btnIngresar.setOnAction(e -> intentarInicioSesion());
        btnLimpiar.setOnAction(e -> limpiarCampos());
        btnCrearCuenta.setOnAction(e -> {
            if (onCrearCuenta != null) onCrearCuenta.run();
        });
        txtPass.setOnAction(e -> intentarInicioSesion());

        // Toggle mostrar/ocultar contraseña
        chkMostrar.selectedProperty().addListener((o, was, show) -> {
            filaPass.setCenter(show ? txtPassVisible : txtPass);
            txtPassVisible.setManaged(show);
            txtPassVisible.setVisible(show);
            if (show) txtPassVisible.requestFocus();
            else txtPass.requestFocus();
        });

        txtPassVisible.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                txtPassVisible.setStyle(
                    "-fx-font-size: 14px; " +
                    "-fx-background-radius: 8; " +
                    "-fx-border-color: #3498db; " +
                    "-fx-border-radius: 8; " +
                    "-fx-border-width: 2;"
                );
            } else {
                txtPassVisible.setStyle(
                    "-fx-font-size: 14px; " +
                    "-fx-background-radius: 8; " +
                    "-fx-border-color: #bdc3c7; " +
                    "-fx-border-radius: 8; " +
                    "-fx-border-width: 2;"
                );
            }
        });

        // ESC para limpiar
        setOnKeyPressed(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.ESCAPE) limpiarCampos();
        });
    }
    // Método para actualizar el estado del botón de ingresar
    private void actualizarEstadoBoton() {
        boolean ok = !txtCorreo.getText().isBlank()
                && EMAIL.matcher(txtCorreo.getText().trim()).find()
                && !txtPass.getText().isBlank();
        btnIngresar.setDisable(!ok);
        
        if (!ok) {
            btnIngresar.setStyle(
                "-fx-background-color: #bdc3c7; -fx-text-fill: white; -fx-background-radius: 8;"
            );
        } else {
            btnIngresar.setStyle(
                "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 8; -fx-cursor: hand;"
            );
        }
    }
    // Método para intentar iniciar sesión
    private void intentarInicioSesion(){
        String correo = txtCorreo.getText().trim();
        String pass = txtPass.getText();

        if (!EMAIL.matcher(correo).find()) {
            mostrarError("Correo Inválido", 
                "El formato del correo no es válido.\n\n" +
                "Asegúrate de usar tu correo institucional.\n" +
                "Ejemplo: nombre@uvg.edu.gt");
            txtCorreo.requestFocus();
            return;
        }
        if (pass.isBlank()) {
            mostrarError("Contraseña Vacía", 
                "Por favor ingresa tu contraseña para continuar.");
            txtPass.requestFocus();
            return;
        }

        if (controlador != null) {
            controlador.manejarLogin(correo, pass);
        } else {
            mostrarInfo("Sistema no Disponible", 
                "El sistema no está listo. Por favor contacta al administrador.");
        }
    }

    public void setControlador(ControladorPrincipal controlador) {
        this.controlador = controlador;
    }
    // Método para mostrar errores
    public void mostrarError(String encabezado, String contenido){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(encabezado);
        a.setContentText(contenido);
        a.showAndWait();
    }
    // Método para mostrar información al usuario
    public void mostrarInfo(String encabezado, String contenido){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(encabezado);
        a.setContentText(contenido);
        a.showAndWait();
    }
    // Método para limpiar los campos del formulario
    public void limpiarCampos(){
        txtCorreo.clear();
        txtPass.clear();
        chkMostrar.setSelected(false);
        txtCorreo.requestFocus();
    }
    // Setter para la acción de crear cuenta
    public void setOnCrearCuenta(Runnable onCrearCuenta) {
        this.onCrearCuenta = onCrearCuenta;
    }

    // Método adicional para mensajes más amigables
    private void mostrarMensajeAmigable(String tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(
            tipo.equals("ERROR") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION
        );
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        
        // Personalizar botones
        if (tipo.equals("ERROR")) {
            alerta.setGraphic(null);
        }
        
        alerta.showAndWait();
    }
}