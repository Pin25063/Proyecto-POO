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
            contenedorTarifa,
            contenedorMaterias,
            botones
        );

        // Al presionar Cancelar, se regresa a login
        btnCancelar.setOnAction(e -> onCancel.run());

        //Cuando el comboRol se active, actualizar las materias dependiendo del tipo de usuario
        comboRol.setOnAction(e -> actualizarOpciones());

        //Registro y validacion de los datos para creacion de usuario
        btnRegistrar.setOnAction(e -> {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String contrasena = txtContrasena.getText();
            Rol rol = comboRol.getValue();

            // Validacion de campos vacios
            if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || rol == null) {
                mostrarInfo("Campos incompletos", "Por favor complete todos los campos.");
                return;
            }

            int id = controlador.generarNuevoIdUsuario();
            Usuario nuevoUsuario;
            
            //Construccion de usuario dependiendo de su rol
            switch (rol) {
                case TUTOR -> {
                    List<String> materias = getMateriasSeleccionadas();
                    if (materias.isEmpty()) {
                        mostrarInfo("Materias faltantes", "Selecciona al menos una materia.");
                        return;
                    }

                    String tarifaTexto = txtTarifa.getText().trim();
                    if (tarifaTexto.isEmpty()) {
                        mostrarInfo("Tarifa requerida", "Ingresa la tarifa por hora.");
                        return;
                    }

                    double tarifa;
                    try {
                        tarifa = Double.parseDouble(tarifaTexto);
                        if (tarifa < 0) throw new NumberFormatException();
                    } catch (NumberFormatException nfe) {
                        mostrarInfo("Tarifa inválida", "Ingresa un número válido mayor o igual a 0.");
                        return;
                    }
                    nuevoUsuario = new Tutor(id, nombre, correo, contrasena, new ArrayList<>(materias), tarifa);
                }
                case CATEDRATICO -> nuevoUsuario = new Catedratico(id, nombre, correo, contrasena);

                default -> nuevoUsuario = new Estudiante(id, nombre, correo, contrasena);
            }

            controlador.registrar(nuevoUsuario);

            mostrarInfo("Cuenta creada", "Tu cuenta ha sido registrada con éxito.");
            if (onCancel != null) onCancel.run(); // Volver a login
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

    //Metodo para actualizar las materias dependiendo del tipo de usuario que se este creando
    private void actualizarOpciones() {
        contenedorMaterias.getChildren().clear();
        contenedorTarifa.getChildren().clear();

        Rol rolSeleccionado = comboRol.getValue();
        if (rolSeleccionado == Rol.TUTOR) {
            // Materias
            Label lblMaterias = new Label("Selecciona las materias que enseñas:");
            contenedorMaterias.getChildren().add(lblMaterias);

            for (String materia : MATERIAS) {
                CheckBox cb = new CheckBox(materia);
                contenedorMaterias.getChildren().add(cb);
            }
            contenedorMaterias.setVisible(true);

            // Tarifa
            Label lblTarifa = new Label("Tarifa por hora:");
            txtTarifa.setPromptText("Ej: 150.0");
            contenedorTarifa.getChildren().addAll(lblTarifa, txtTarifa);
            contenedorTarifa.setVisible(true);
        } else {
            contenedorMaterias.setVisible(false);
            contenedorTarifa.setVisible(false);
        }
    }


    public List<String> getMateriasSeleccionadas() {
        List<String> materias = new ArrayList<>();

        // Solo aplica si el usuario seleccionó el rol Tutor
        if (comboRol.getValue() == Rol.TUTOR) {
            for (javafx.scene.Node node : contenedorMaterias.getChildren()) {
                if (node instanceof CheckBox cb && cb.isSelected()) {
                    materias.add(cb.getText());
                }
            }
        }
        return materias;
    }
}