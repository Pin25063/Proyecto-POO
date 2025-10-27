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

    //Vbox donde se encontraran las materias
    private final VBox contenedorMaterias = new VBox(5);

    //Campo para ingresar la tarifa de los tutores
    private final TextField txtTarifa = new TextField();
    private final VBox contenedorTarifa = new VBox(5);

    //Botones
    private final Button btnRegistrar = new Button("Registrar");
    private final Button btnCancelar = new Button("Cancelar");

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
        setPadding(new Insets(24));
        setSpacing(16);
        setAlignment(Pos.CENTER);
        setFillWidth(false);
        setMaxWidth(460);

        //Lables de texto de la pantalla
        Label titulo = new Label("Registro de Usuario");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        //Placeholder visual de los campos bases
        txtNombre.setPromptText("Nombre completo");
        txtCorreo.setPromptText("correo@uvg.edu.gt");
        txtContrasena.setPromptText("Contraseña");

        //Selector de Rol
        comboRol.getItems().setAll(Rol.values());
        comboRol.setPromptText("Selecciona tu rol");

        //Selector de materias si el tipo de usuario aplica
        contenedorMaterias.setPadding(new Insets(10));
        contenedorMaterias.setAlignment(Pos.CENTER_LEFT);
        contenedorMaterias.setVisible(false); // Oculto por defecto

        //Cuadro para ingresar tarifa de tutores
        contenedorTarifa.setPadding(new Insets(5));
        contenedorTarifa.setAlignment(Pos.CENTER_LEFT);
        contenedorTarifa.setVisible(false);

        // Contenedor para los botones de registro y cancelacion
        HBox botones = new HBox(10, btnRegistrar, btnCancelar);
        botones.setAlignment(Pos.CENTER);
        
        //Se agregan todos los componenetes a la vista
        getChildren().addAll(
            titulo,
            txtNombre,
            txtCorreo,
            txtContrasena,
            comboRol,
            contenedorMaterias,
            contenedorTarifa,
            botones
        );

        // Al presionar Cancelar, se regresa a login
        btnCancelar.setOnAction(e -> onCancel.run());

        //Cuando el comboRol se active, actualizar las materias dependiendo del tipo de usuario
        comboRol.setOnAction(e -> actualizarOpciones());

        //Registro y validacion de los datos para creacion de usuario
        //Registro y validacion de los datos para creacion de usuario
btnRegistrar.setOnAction(e -> {
            // Validación del controlador
            if (controlador == null) {
                mostrarError("Error del sistema", "El controlador no está configurado.");
                return;
            }

            String nombre = txtNombre.getText() != null ? txtNombre.getText().trim() : "";
            String correo = txtCorreo.getText() != null ? txtCorreo.getText().trim() : "";
            String contrasena = txtContrasena.getText() != null ? txtContrasena.getText() : "";
            Rol rol = comboRol.getValue();

            // Validacion de campos vacios
            if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || rol == null) {
                mostrarError("Campos incompletos", "Por favor complete todos los campos.");
                return;
            }

            // Validación de formato de correo
            if (!correo.matches(REGEX_EMAIL)) {
                mostrarError("Correo inválido", "Ingresa un correo válido (ejemplo: usuario@uvg.edu.gt).");
                return;
            }

            // Validación de contraseña
            if (contrasena.length() < MIN_LONGITUD_CONTRASENA) {
                mostrarError("Contraseña débil", "La contraseña debe tener al menos " + MIN_LONGITUD_CONTRASENA + " caracteres.");
                return;
            }

            if (contrasena.contains(" ")) {
                mostrarError("Contraseña inválida", "La contraseña no puede contener espacios.");
                return;
            }

            int id;
            try {
                id = controlador.generarNuevoIdUsuario();
            } catch (Exception ex) {
                mostrarError("Error de sistema", "No se pudo generar un ID de usuario.");
                return;
            }

            Usuario nuevoUsuario;
            
            //Construccion de usuario dependiendo de su rol
            try {
                switch (rol) {
                    case TUTOR -> {
                        List<String> materias = getMateriasSeleccionadas();
                        if (materias.isEmpty()) {
                            mostrarError("Materias faltantes", "Selecciona al menos una materia.");
                            return;
                        }

                        String tarifaTexto = txtTarifa.getText() != null ? txtTarifa.getText().trim() : "";
                        if (tarifaTexto.isEmpty()) {
                            mostrarError("Tarifa requerida", "Ingresa la tarifa por hora.");
                            return;
                        }

                        double tarifa;
                        try {
                            tarifa = Double.parseDouble(tarifaTexto);
                            if (tarifa < 0) {
                                mostrarError("Tarifa inválida", "La tarifa debe ser mayor o igual a 0.");
                                return;
                            }
                        } catch (NumberFormatException nfe) {
                            mostrarError("Tarifa inválida", "Ingresa un número válido (ejemplo: 150.0).");
                            return;
                        }
                        nuevoUsuario = new Tutor(id, nombre, correo, contrasena, new ArrayList<>(materias), tarifa);
                    }
                    case CATEDRATICO -> {
                        List<String> materias = getMateriasSeleccionadas();
                        if (materias.isEmpty()) {
                            mostrarError("Materias faltantes", "Selecciona al menos una materia.");
                            return;
                        }
                        nuevoUsuario = new Catedratico(id, nombre, correo, contrasena);
                    }
                    case ESTUDIANTE -> {
                        nuevoUsuario = new Estudiante(id, nombre, correo, contrasena);
                    }
                    default -> {
                        mostrarError("Rol inválido", "El rol seleccionado no es válido.");
                        return;
                    }
                }

                controlador.registrar(nuevoUsuario);
                mostrarInfo("Cuenta creada", "Tu cuenta ha sido registrada con éxito.");
                if (onCancel != null) onCancel.run(); // Volver a login

            } catch (IllegalArgumentException ex) {
                mostrarError("Error de validación", "Datos inválidos: " + ex.getMessage());
            } catch (Exception ex) {
                mostrarError("Error inesperado", "Ocurrió un error al registrar el usuario.");
            }
        });
    }
    // Metodo para mostrar alertas de error
    private void mostrarError(String header, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(header);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
    
    //Permite definir que hace cuando se presiona cancelar (Main lo define)
    public void setOnCancel(Runnable r) {
        if (r == null) {
            throw new IllegalArgumentException("El callback onCancel no puede ser null");
        }
        this.onCancel = r;
    }

    //Conecta el controlador principal
    public void setControlador(ControladorPrincipal controlador) {
        if (controlador == null) {
            throw new IllegalArgumentException("El controlador no puede ser null");
        }
        this.controlador = controlador;
    }

    // Metodo para mostrar alertas
    private void mostrarInfo(String header, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(header);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    //Metodo para actualizar las materias dependiendo del tipo de usuario que se este creando
    private void actualizarOpciones() {
        contenedorMaterias.getChildren().clear();
        contenedorTarifa.getChildren().clear();

        Rol rolSeleccionado = comboRol.getValue();
        
        // validacion null
        if (rolSeleccionado == null) {
            contenedorMaterias.setVisible(false);
            contenedorTarifa.setVisible(false);
            return;
        }
        if (rolSeleccionado != Rol.ESTUDIANTE) {
            // Materias
            Label lblMaterias = new Label("Selecciona las materias que imparte:");
            contenedorMaterias.getChildren().add(lblMaterias);

            for (String materia : MATERIAS) {
                CheckBox cb = new CheckBox(materia);
                contenedorMaterias.getChildren().add(cb);
            }
            contenedorMaterias.setVisible(true);
            if (rolSeleccionado == Rol.TUTOR) {
                // Tarifa
                Label lblTarifa = new Label("Tarifa por hora:");
                txtTarifa.setPromptText("Ej: 150.0");
                contenedorTarifa.getChildren().addAll(lblTarifa, txtTarifa);
                contenedorTarifa.setVisible(true);
            }
        } else {
            contenedorMaterias.setVisible(false);
            contenedorTarifa.setVisible(false);
        }
    }


    public List<String> getMateriasSeleccionadas() {
        List<String> materias = new ArrayList<>();

        // Solo aplica si el usuario seleccionó un rol que no sea estudiante
        if (comboRol.getValue() == null || comboRol.getValue() == Rol.ESTUDIANTE) {
            return materias;
        }

        try {
            for (javafx.scene.Node node : contenedorMaterias.getChildren()) {
                if (node instanceof CheckBox cb && cb.isSelected()) {
                    String materia = cb.getText();
                    if (materia != null && !materia.trim().isEmpty()) {
                        materias.add(materia.trim());
                    }
                }
            }
        } catch (Exception e) {
            // En caso de error, retornar lista vacía
            materias.clear();
        }
        
        return materias;
    }
}