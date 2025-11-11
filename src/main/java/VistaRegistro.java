import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
public class VistaRegistro extends VBox {
    //Campos de entrada de texto
    private final TextField txtNombre = new TextField();
    private final TextField txtCorreo = new TextField();
    private final PasswordField txtContrasena = new PasswordField();

    //Combox para seleccionar rol
    private final ComboBox<Rol> comboRol = new ComboBox<>();

    //Vbox donde se encontraran las materias
    private final VBox contenedorMaterias = new VBox(10);

    //Campo para ingresar la tarifa de los tutores
    private final TextField txtTarifa = new TextField();
    private final VBox contenedorTarifa = new VBox(10);

    //Botones
    private final Button btnRegistrar = new Button("Registrar Cuenta");
    private final Button btnCancelar = new Button("Volver al Login");

    //Referencias al controlador y navegador
    private Runnable onCancel;
    private ControladorPrincipal controlador;

    //Array de las materias disponibles hasta ahora
    private static final String[] MATERIAS = {
        "Matematica", "Fisica", "Quimica", "Programacion", "Estadistica"
    };
    // Constantes para validación defensiva
    private static final int MIN_LONGITUD_CONTRASENA = 6;
    private static final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public VistaRegistro() {
        //Estilo del layout
        setPadding(new Insets(20));
        setSpacing(15);
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-background-color: linear-gradient(to bottom, #ecf0f1, #bdc3c7);");
        
             // Crear tarjeta principal con scroll
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setPrefViewportHeight(600);
        
        VBox tarjetaRegistro = new VBox(18);
        tarjetaRegistro.setPadding(new Insets(30));
        tarjetaRegistro.setAlignment(Pos.TOP_CENTER);
        tarjetaRegistro.setMaxWidth(500);
        tarjetaRegistro.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 15; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0, 0, 3);"
        );
        
        // Título
        Label titulo = new Label("Crear Nueva Cuenta");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titulo.setStyle("-fx-text-fill: #2c3e50;");
        
        Label subtitulo = new Label("Completa el formulario para registrarte");
        subtitulo.setFont(Font.font("Arial", 14));
        subtitulo.setStyle("-fx-text-fill: #7f8c8d;");
        
        Separator separador1 = new Separator();
        separador1.setMaxWidth(400);
        
        // === CAMPOS BÁSICOS ===
        VBox seccionBasica = crearSeccionBasica();
        
        // === SELECTOR DE ROL ===
        VBox seccionRol = crearSeccionRol();
        
        // === CAMPOS CONDICIONALES ===
        configurarCamposCondicionales();
        
        // === BOTONES ===
        HBox botones = crearBotones();
        
        tarjetaRegistro.getChildren().addAll(
            titulo,
            subtitulo,
            separador1,
            seccionBasica,
            seccionRol,
            contenedorMaterias,
            contenedorTarifa,
            new Separator(),
            botones
        );
        
        scrollPane.setContent(tarjetaRegistro);
        getChildren().add(scrollPane);
        
        // Configurar acciones
        configurarAcciones();
    }
    
    // Crear sección de campos básicos
    private VBox crearSeccionBasica() {
        VBox seccion = new VBox(15);
        
        Label lblSeccion = new Label("📋 Información Personal");
        lblSeccion.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblSeccion.setStyle("-fx-text-fill: #34495e;");
        
        // Campo Nombre
        Label lblNombre = new Label("Nombre Completo *");
        lblNombre.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lblNombre.setStyle("-fx-text-fill: #2c3e50;");
        txtNombre.setPromptText("Ej: Juan Pérez");
        txtNombre.setPrefHeight(40);
        txtNombre.setStyle("-fx-font-size: 14px; -fx-background-radius: 8; -fx-border-color: #bdc3c7; -fx-border-radius: 8; -fx-border-width: 2;");
        
        // Campo Correo
        Label lblCorreo = new Label("Correo Institucional *");
        lblCorreo.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lblCorreo.setStyle("-fx-text-fill: #2c3e50;");
        txtCorreo.setPromptText("correo@uvg.edu.gt");
        txtCorreo.setPrefHeight(40);
        txtCorreo.setStyle("-fx-font-size: 14px; -fx-background-radius: 8; -fx-border-color: #bdc3c7; -fx-border-radius: 8; -fx-border-width: 2;");
        
        // Campo Contraseña
        Label lblContrasena = new Label("Contraseña *");
        lblContrasena.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lblContrasena.setStyle("-fx-text-fill: #2c3e50;");
        txtContrasena.setPromptText("Mínimo 6 caracteres");
        txtContrasena.setPrefHeight(40);
        txtContrasena.setStyle("-fx-font-size: 14px; -fx-background-radius: 8; -fx-border-color: #bdc3c7; -fx-border-radius: 8; -fx-border-width: 2;");
        
        Label lblNota = new Label("* Campos obligatorios");
        lblNota.setFont(Font.font("Arial", 10));
        lblNota.setStyle("-fx-text-fill: #e74c3c; -fx-font-style: italic;");

        seccion.getChildren().addAll(
            lblSeccion,
            lblNombre, txtNombre,
            lblCorreo, txtCorreo,
            lblContrasena, txtContrasena
        );

        seccion.getChildren().add(lblNota);

        return seccion;
        
    }
    
    // Crear sección de selección de rol
    private VBox crearSeccionRol() {
        VBox seccion = new VBox(10);
        
        Label lblSeccion = new Label("👤 Tipo de Usuario");
        lblSeccion.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblSeccion.setStyle("-fx-text-fill: #34495e;");
        
        Label lblRol = new Label("Selecciona tu rol *");
        lblRol.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lblRol.setStyle("-fx-text-fill: #2c3e50;");
        
        comboRol.setPromptText("¿Qué tipo de usuario eres?");
        comboRol.getItems().setAll(Rol.ESTUDIANTE, Rol.TUTOR);
        comboRol.setPrefHeight(40);
        comboRol.setPrefWidth(400);
        comboRol.setStyle("-fx-font-size: 14px;");
        
        // Descripciones de roles
        Label lblDescripcion = new Label(
            "• ESTUDIANTE: Buscar tutores y agendar sesiones\n" +
            "• TUTOR: Ofrecer tutorías y gestionar sesiones"
        );
        lblDescripcion.setFont(Font.font("Arial", 11));
        lblDescripcion.setStyle("-fx-text-fill: #7f8c8d; -fx-padding: 10 0 0 10;");
        lblDescripcion.setWrapText(true);
        
        seccion.getChildren().addAll(lblSeccion, lblRol, comboRol, lblDescripcion);
        return seccion;
    }
    
    // Configurar campos condicionales
    private void configurarCamposCondicionales() {
        // === CONTENEDOR DE MATERIAS ===
        contenedorMaterias.setPadding(new Insets(15));
        contenedorMaterias.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 8;");
        contenedorMaterias.setVisible(false);
        contenedorMaterias.setManaged(false);
        
        Label lblMaterias = new Label("📚 Materias que Impartes *");
        lblMaterias.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblMaterias.setStyle("-fx-text-fill: #2c3e50;");
        
        Label lblInstruccion = new Label("Selecciona al menos una materia:");
        lblInstruccion.setFont(Font.font("Arial", 12));
        lblInstruccion.setStyle("-fx-text-fill: #34495e;");
        
        // Grid de checkboxes para materias
        GridPane gridMaterias = new GridPane();
        gridMaterias.setHgap(15);
        gridMaterias.setVgap(10);
        gridMaterias.setPadding(new Insets(10, 0, 0, 10));
        
        int columna = 0;
        int fila = 0;
        for (String materia : MATERIAS) {
            CheckBox cb = new CheckBox(materia);
            cb.setFont(Font.font("Arial", 13));
            cb.setStyle("-fx-text-fill: #2c3e50;");
            gridMaterias.add(cb, columna, fila);
            
            columna++;
            if (columna > 1) {
                columna = 0;
                fila++;
            }
        }
        
        contenedorMaterias.getChildren().addAll(lblMaterias, lblInstruccion, gridMaterias);
        
        // === CONTENEDOR DE TARIFA ===
        contenedorTarifa.setPadding(new Insets(15));
        contenedorTarifa.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 8;");
        contenedorTarifa.setVisible(false);
        contenedorTarifa.setManaged(false);
        
        Label lblTarifa = new Label("💰 Tarifa por Hora *");
        lblTarifa.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblTarifa.setStyle("-fx-text-fill: #2c3e50;");
        
        Label lblInstruccionTarifa = new Label("Ingresa tu tarifa en Quetzales:");
        lblInstruccionTarifa.setFont(Font.font("Arial", 12));
        lblInstruccionTarifa.setStyle("-fx-text-fill: #34495e;");
        
        txtTarifa.setPromptText("Ej: 50.00");
        txtTarifa.setPrefHeight(40);
        txtTarifa.setPrefWidth(200);
        txtTarifa.setStyle("-fx-font-size: 14px; -fx-background-radius: 8; -fx-border-color: #bdc3c7; -fx-border-radius: 8; -fx-border-width: 2;");
        
        HBox contenedorTarifaInput = new HBox(10);
        contenedorTarifaInput.setAlignment(Pos.CENTER_LEFT);
        Label lblQuetzal = new Label("Q");
        lblQuetzal.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblQuetzal.setStyle("-fx-text-fill: #2c3e50;");
        contenedorTarifaInput.getChildren().addAll(lblQuetzal, txtTarifa);
        
        contenedorTarifa.getChildren().addAll(lblTarifa, lblInstruccionTarifa, contenedorTarifaInput);
        
        // Listener para cambios en el ComboBox de rol
        comboRol.setOnAction(e -> actualizarCamposSegunRol());
    }
    
    // Actualizar visibilidad de campos según el rol seleccionado
    private void actualizarCamposSegunRol() {
        Rol rolSeleccionado = comboRol.getValue();
        
        if (rolSeleccionado == null) {
            contenedorMaterias.setVisible(false);
            contenedorMaterias.setManaged(false);
            contenedorTarifa.setVisible(false);
            contenedorTarifa.setManaged(false);
            return;
        }
        
        // Mostrar campos según el rol
        switch (rolSeleccionado) {
            case TUTOR:
                contenedorMaterias.setVisible(true);
                contenedorMaterias.setManaged(true);
                contenedorTarifa.setVisible(true);
                contenedorTarifa.setManaged(true);
                break;
                
            case ESTUDIANTE:
            default:
                contenedorMaterias.setVisible(false);
                contenedorMaterias.setManaged(false);
                contenedorTarifa.setVisible(false);
                contenedorTarifa.setManaged(false);
                break;
        }
    }
    
    // Crear botones de acción
    private HBox crearBotones() {
        HBox contenedor = new HBox(15);
        contenedor.setAlignment(Pos.CENTER);
        contenedor.setPadding(new Insets(10, 0, 0, 0));
        
        btnRegistrar.setPrefWidth(200);
        btnRegistrar.setPrefHeight(45);
        btnRegistrar.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        btnRegistrar.setStyle(
            "-fx-background-color: #27ae60; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8;"
        );
        btnRegistrar.setTooltip(new Tooltip(
            "Crear cuenta con la información proporcionada.\n" +
            "Asegúrate de completar todos los campos obligatorios."
        ));
        
        btnCancelar.setPrefWidth(200);
        btnCancelar.setPrefHeight(45);
        btnCancelar.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        btnCancelar.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-text-fill: #95a5a6; " +
            "-fx-border-color: #95a5a6; " +
            "-fx-border-width: 2; " +
            "-fx-border-radius: 8;"
        );
        btnCancelar.setTooltip(new Tooltip(
            "Cancelar el registro y volver a la pantalla de inicio de sesión."
        ));
        
        // Efectos hover
        btnRegistrar.setOnMouseEntered(e -> btnRegistrar.setStyle(
            "-fx-background-color: #229954; -fx-text-fill: white; -fx-background-radius: 8;"
        ));
        btnRegistrar.setOnMouseExited(e -> btnRegistrar.setStyle(
            "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 8;"
        ));
        
        btnCancelar.setOnMouseEntered(e -> btnCancelar.setStyle(
            "-fx-background-color: #ecf0f1; -fx-text-fill: #7f8c8d; -fx-border-color: #7f8c8d; -fx-border-width: 2; -fx-border-radius: 8;"
        ));
        btnCancelar.setOnMouseExited(e -> btnCancelar.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: #95a5a6; -fx-border-color: #95a5a6; -fx-border-width: 2; -fx-border-radius: 8;"
        ));
        
        contenedor.getChildren().addAll(btnRegistrar, btnCancelar);
        return contenedor;
    }
    
    // Configurar acciones de botones
    private void configurarAcciones() {
        btnCancelar.setOnAction(e -> {
            if (onCancel != null) onCancel.run();
        });
        
        btnRegistrar.setOnAction(e -> procesarRegistro());
    }
    
    // Procesar el registro
    private void procesarRegistro() {
        if (controlador == null) {
            mostrarError("Error del sistema", "El controlador no está configurado.");
            return;
        }

        String nombre = txtNombre.getText() != null ? txtNombre.getText().trim() : "";
        String correo = txtCorreo.getText() != null ? txtCorreo.getText().trim() : "";
        String contrasena = txtContrasena.getText() != null ? txtContrasena.getText() : "";
        Rol rol = comboRol.getValue();

        // Validaciones básicas
        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || rol == null) {
            mostrarError("Campos Incompletos", "Por favor completa todos los campos obligatorios (*)");
            return;
        }

        if (!correo.matches(REGEX_EMAIL)) {
            mostrarError("Correo Inválido", "Por favor ingresa un correo válido (ej: nombre@uvg.edu.gt)");
            return;
        }

        if (contrasena.length() < MIN_LONGITUD_CONTRASENA) {
            mostrarError("Contraseña Débil", "La contraseña debe tener al menos " + MIN_LONGITUD_CONTRASENA + " caracteres");
            return;
        }

        if (contrasena.contains(" ")) {
            mostrarError("Contraseña Inválida", "La contraseña no puede contener espacios");
            return;
        }

        int id;
        try {
            id = controlador.generarNuevoIdUsuario();
        } catch (Exception ex) {
            mostrarError("Error de sistema", "No se pudo generar un ID de usuario");
            return;
        }

        Usuario nuevoUsuario;
        
        try {
            switch (rol) {
                case TUTOR -> {
                    List<String> materias = getMateriasSeleccionadas();
                    if (materias.isEmpty()) {
                        mostrarError("Materias Faltantes", "Selecciona al menos una materia que impartes");
                        return;
                    }

                    String tarifaTexto = txtTarifa.getText() != null ? txtTarifa.getText().trim() : "";
                    if (tarifaTexto.isEmpty()) {
                        mostrarError("Tarifa Requerida", "Ingresa tu tarifa por hora");
                        return;
                    }

                    double tarifa;
                    try {
                        tarifa = Double.parseDouble(tarifaTexto);
                        if (tarifa < 0) {
                            mostrarError("Tarifa Inválida", "La tarifa debe ser un número positivo");
                            return;
                        }
                    } catch (NumberFormatException nfe) {
                        mostrarError("Tarifa Inválida", "Ingresa un número válido (ej: 50.00)");
                        return;
                    }

                    nuevoUsuario = new Tutor(id, nombre, correo, contrasena, new ArrayList<>(materias), tarifa);
                }
                case ESTUDIANTE -> {
                    nuevoUsuario = new Estudiante(id, nombre, correo, contrasena);
                }
                default -> {
                    mostrarError("Rol Inválido", "El rol seleccionado no es válido");
                    return;
                }
            }

            controlador.registrar(nuevoUsuario);
            
            mostrarInfo("¡Registro Exitoso!", 
                "Tu cuenta ha sido creada correctamente.\n\n" +
                "Nombre: " + nombre + "\n" +
                "Rol: " + rol + "\n\n" +
                "Ahora puedes iniciar sesión con tu correo y contraseña.");
            
            limpiarCampos();
            if (onCancel != null) onCancel.run();

        } catch (IllegalArgumentException ex) {
            mostrarError("Error de Validación", "Datos inválidos: " + ex.getMessage());
        } catch (Exception ex) {
            mostrarError("Error Inesperado", "Ocurrió un error al registrar el usuario");
        }
    }
    
    // Obtener materias seleccionadas
    private List<String> getMateriasSeleccionadas() {
        List<String> seleccionadas = new ArrayList<>();
        
        if (!contenedorMaterias.isVisible()) return seleccionadas;
        
        for (javafx.scene.Node node : contenedorMaterias.getChildren()) {
            if (node instanceof GridPane) {
                GridPane grid = (GridPane) node;
                for (javafx.scene.Node gridNode : grid.getChildren()) {
                    if (gridNode instanceof CheckBox) {
                        CheckBox cb = (CheckBox) gridNode;
                        if (cb.isSelected()) {
                            String materia = cb.getText();
                            if (materia != null && !materia.trim().isEmpty()) {
                                seleccionadas.add(materia.trim());
                            }
                        }
                    }
                }
            }
        }
        
        return seleccionadas;
    }
    
    // Limpiar campos
    private void limpiarCampos() {
        txtNombre.clear();
        txtCorreo.clear();
        txtContrasena.clear();
        txtTarifa.clear();
        comboRol.setValue(null);
        
        for (javafx.scene.Node node : contenedorMaterias.getChildren()) {
            if (node instanceof GridPane) {
                GridPane grid = (GridPane) node;
                for (javafx.scene.Node gridNode : grid.getChildren()) {
                    if (gridNode instanceof CheckBox) {
                        ((CheckBox) gridNode).setSelected(false);
                    }
                }
            }
        }
        
        actualizarCamposSegunRol();
    }
    
    // Mostrar alertas
    private void mostrarError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    private void mostrarInfo(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    // Setters
    public void setControlador(ControladorPrincipal controlador) {
        if (controlador == null) {
            throw new IllegalArgumentException("El controlador no puede ser null");
        }
        this.controlador = controlador;
    }
    
    public void setOnCancel(Runnable onCancel) {
        if (onCancel == null) {
            throw new IllegalArgumentException("El callback onCancel no puede ser null");
        }
        this.onCancel = onCancel;
    }
}