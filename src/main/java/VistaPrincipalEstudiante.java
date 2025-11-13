import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class VistaPrincipalEstudiante {
    
    private Estudiante estudianteActual;
    private ControladorPrincipal controlador;
    private Stage stage;
    private Main mainApp;
    private BorderPane layoutPrincipal;
    
    // Labels para mostrar información
    private Label lblNombre, lblCorreo, lblId;
    
    // Botones de acción
    private Button btnEditarPerfil, btnBuscarTutores, btnVerHistorial, btnAgendarSesion;
    
    // Constructor
    public VistaPrincipalEstudiante(Estudiante estudiante, ControladorPrincipal controlador, Stage stage, Main mainApp) {
        this.estudianteActual = estudiante;
        this.controlador = controlador;
        this.stage = stage;
        this.mainApp = mainApp;
    }

    // Construye y muestra la interfaz del estudiante
    public void mostrar() {
        // BorderPane es un layout que divide la pantalla en 5 zonas: Top, Left, Center, Right, Bottom
    this.layoutPrincipal = new BorderPane();

        // color de fondo base a toda la ventana
        layoutPrincipal.setStyle("-fx-background-color: #ecf0f1;");

        // Componentes Principales de la UI
        HBox barraSuperior = crearBarraSuperior(); // título, nombre de usuario, botón de cerrar sesión
        VBox menuLateral = crearMenuLateral(layoutPrincipal); // botones para cambiar de panel
        VBox contenidoInicial = crearPanelInicio(); // Bienvenida
        
        // Ensamblaje del Layout principal
        // Colocar cada componente en su zona correspondiente del BorderPane
        layoutPrincipal.setTop(barraSuperior); // barra superior arriba
        layoutPrincipal.setLeft(menuLateral); // menú a la izquierda
        layoutPrincipal.setCenter(contenidoInicial); // Panel de contenido en el centro

        // Crear escena con el layout principal y definimos su tamaño inicial
        // Eliminar restricciones de tamaño previas (del login/registro)
        stage.setMinWidth(Double.MIN_VALUE);
        stage.setMaxWidth(Double.MAX_VALUE);
        stage.setMinHeight(Double.MIN_VALUE);
        stage.setMaxHeight(Double.MAX_VALUE);
        
        stage.setResizable(true);
        stage.setMinWidth(900);
        stage.setMinHeight(600);

        // 2. Creamos y ponemos la ESCENA 
        Scene escena = new Scene(layoutPrincipal, 1100, 700);
        stage.setScene(escena);
        stage.setTitle("Panel del Estudiante - Gestor de Tutorías UVG");

        // 3. Maximizamos la VENTANA DESPUÉS de poner la escena
        stage.setMaximized(true);
        stage.toFront();
        stage.requestFocus();
        
        stage.show(); // mostrar ventana al cliente 
    }

    // Métodos privados para construir las partes de la UI
    private HBox crearBarraSuperior() {
        HBox barra = new HBox(20);
        barra.setPadding(new Insets(15));
        barra.setStyle("-fx-background-color: #0a2e5a;"); // Color azul marino predominante para barra superior
        barra.setAlignment(Pos.CENTER_LEFT);

        // Crear el Label para el título de la aplicación
        Label lblTitulo = new Label("Gestor de Tutorías UVG");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblTitulo.setStyle("-fx-text-fill: white;");
        
        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        // Crear label de bienvenida con el nombre del estudiante actual
        Label lblBienvenida = new Label("Estudiante: " + estudianteActual.getNombre());
        lblBienvenida.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblBienvenida.setStyle("-fx-text-fill: white;");
        
        // Crear botón para cerrar sesión
        Button btnCerrarSesion = new Button("Cerrar Sesión");
        btnCerrarSesion.setStyle("-fx-background-color: #d12a17ff; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCerrarSesion.setOnAction(e -> mainApp.mostrarLogin());

        // Añadir los componentes al HBox
        barra.getChildren().addAll(lblTitulo, espaciador, lblBienvenida, btnCerrarSesion);
        return barra;
    }

    // MENU DE NAVEGACION LATERAL
    private VBox crearMenuLateral(BorderPane layoutPrincipal) {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(220);
        menu.setStyle("-fx-background-color: #0a1f3d;"); // Azul marino más oscuro para menú lateral
    
        Label lblMenu = new Label("MENÚ");
        lblMenu.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblMenu.setStyle("-fx-text-fill: white;");
        lblMenu.setAlignment(Pos.CENTER);

        // Crear los botones de navegación
        Button btnInicio = crearBotonMenu("INICIO");
        Button btnPerfil = crearBotonMenu("Mi Perfil");
        Button btnBuscarTutores = crearBotonMenu("Buscar Tutores");
        Button btnAgendarSesion = crearBotonMenu("Agendar Sesión");
        Button btnHistorial = crearBotonMenu("Ver Historial");

        // Cada botón cambia el panel central del BorderPane
        btnInicio.setOnAction(e -> layoutPrincipal.setCenter(crearPanelInicio()));
        btnPerfil.setOnAction(e -> layoutPrincipal.setCenter(crearPanelPerfil()));
        btnBuscarTutores.setOnAction(e -> abrirBusquedaTutores());
        btnAgendarSesion.setOnAction(e -> abrirAgendamiento());
        btnHistorial.setOnAction(e -> layoutPrincipal.setCenter(crearPanelHistorial()));
        
        // Añadir los componentes al VBox
        menu.getChildren().addAll(lblMenu, new Separator(), btnInicio, btnPerfil, btnBuscarTutores, btnAgendarSesion, btnHistorial);
        return menu;
    }
    
    // Paneles de Contenido
    private VBox crearPanelInicio() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));
        panel.setAlignment(Pos.TOP_CENTER);
        
        // Titulo de bienvenida con el nombre del estudiante actual
        Label lblTitulo = new Label("Bienvenido, " + estudianteActual.getNombre());
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        
        // Texto de instrucciones para el usuario
        Label lblInfoTexto = new Label(
            "Use el menú de la izquierda para navegar por las diferentes herramientas de la aplicación.\n" +
            "Puede buscar tutores, agendar sesiones de tutoría y ver su historial de sesiones."
        );
        lblInfoTexto.setFont(Font.font("Arial", 16));
        lblInfoTexto.setWrapText(true);
        lblInfoTexto.setStyle("-fx-text-alignment: center;");

        // Se añaden los componentes al panel
        panel.getChildren().addAll(lblTitulo, new Separator(), lblInfoTexto);
        return panel;
    }

    private VBox crearPanelPerfil() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));
        
        Label lblTitulo = new Label("Mi Perfil");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        VBox seccionPerfil = crearSeccionPerfil();
        
        HBox seccionBotones = new HBox(12);
        seccionBotones.setAlignment(Pos.CENTER);
        seccionBotones.setPadding(new Insets(20, 0, 0, 0));
        
        btnEditarPerfil = new Button("Editar Perfil");
        btnEditarPerfil.setStyle("-fx-font-size: 13px; -fx-padding: 8 15 8 15; -fx-background-color: #0a2e5a; -fx-text-fill: white; -fx-font-weight: bold;");
        btnEditarPerfil.setOnAction(e -> abrirEdicionPerfil());
        
        seccionBotones.getChildren().addAll(btnEditarPerfil);
        
        panel.getChildren().addAll(lblTitulo, seccionPerfil, seccionBotones);
        return panel;
    }

    private VBox crearPanelHistorial() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));
        
        Label lblTitulo = new Label("Historial de Sesiones");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        VBox seccionHistorial = crearSeccionHistorial();
        
        HBox seccionBotones = new HBox(12);
        seccionBotones.setAlignment(Pos.CENTER);
        seccionBotones.setPadding(new Insets(20, 0, 0, 0));
        
        btnVerHistorial = new Button("Ver Historial Completo");
        btnVerHistorial.setStyle("-fx-font-size: 13px; -fx-padding: 8 15 8 15; -fx-background-color: #0a2e5a; -fx-text-fill: white; -fx-font-weight: bold;");
        btnVerHistorial.setOnAction(e -> mostrarHistorialCompleto());
        
        seccionBotones.getChildren().addAll(btnVerHistorial);
        
        panel.getChildren().addAll(lblTitulo, seccionHistorial, seccionBotones);
        return panel;
    }
    
    // Crear sección de perfil
    private VBox crearSeccionPerfil() {
        VBox seccion = new VBox(10);
        seccion.setPadding(new Insets(15));
        seccion.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5;");
        seccion.setMaxWidth(600);
        
        Label tituloPerfil = new Label("📋 Información del Perfil");
        tituloPerfil.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        lblId = new Label("ID: " + estudianteActual.getIdUsuario());
        lblNombre = new Label("Nombre: " + estudianteActual.getNombre());
        lblCorreo = new Label("Correo: " + estudianteActual.getCorreo());
        
        lblId.setStyle("-fx-font-size: 14px;");
        lblNombre.setStyle("-fx-font-size: 14px;");
        lblCorreo.setStyle("-fx-font-size: 14px;");
        
        seccion.getChildren().addAll(tituloPerfil, lblId, lblNombre, lblCorreo);
        
        return seccion;
    }

    // Crear sección de historial
    private VBox crearSeccionHistorial() {
        VBox seccion = new VBox(10);
        seccion.setPadding(new Insets(15));
        seccion.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 5;");
        seccion.setMaxWidth(600);
        
        Label tituloHistorial = new Label("Últimas Sesiones");
        tituloHistorial.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        ListView<String> listaSesiones = new ListView<>();
        listaSesiones.setPrefHeight(150);
        
        ArrayList<Sesion> historial = estudianteActual.getHistorialSesiones();
        
        if (historial == null || historial.isEmpty()) {
            listaSesiones.getItems().add("No tienes sesiones registradas aún");
        } else {
            // Mostrar las últimas 5 sesiones
            int limite = Math.min(5, historial.size());
            for (int i = historial.size() - 1; i >= historial.size() - limite; i--) {
                Sesion s = historial.get(i);
                // CAMBIO: Se usa getFecha() y getHora()
                String texto = String.format("%s - %s %s (%s)", 
                    s.getMateria(), 
                    s.getFecha(), // Nuevo
                    s.getHora(),   // Nuevo
                    s.getEstado());
                listaSesiones.getItems().add(texto);
            }
        }
        
        seccion.getChildren().addAll(tituloHistorial, listaSesiones);
        
        return seccion;
    }

    // HELPERS
    private Button crearBotonMenu(String texto) {
        Button boton = new Button(texto);
        boton.setPrefWidth(200);
        boton.setPrefHeight(40);
        boton.setAlignment(Pos.CENTER_LEFT);
        boton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        // Efecto hover con azul marino más claro
        boton.setOnMouseEntered(e -> boton.setStyle("-fx-background-color: #1a3f6d; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;")); // Azul marino más claro en hover
        boton.setOnMouseExited(e -> boton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;"));
        
        return boton;
    }

    // Abrir diálogo de edición
    private void abrirEdicionPerfil() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Editar Perfil - Estudiante");
        
        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));
        contenido.setAlignment(Pos.CENTER);
        contenido.setStyle("-fx-background-color: #f4f4f4;");
        
        Label titulo = new Label("Editar Información Personal");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Gridpane para ordenar los campos
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);
        
        // Campos editables
        Label lblNuevoNombre = new Label("Nombre Completo:");
        TextField txtNombre = new TextField(estudianteActual.getNombre());
        txtNombre.setPrefWidth(250);
        
        Label lblNuevoCorreo = new Label("Correo:");
        TextField txtCorreo = new TextField(estudianteActual.getCorreo());
        txtCorreo.setPrefWidth(300);
        txtCorreo.setDisable(true);
        txtCorreo.setTooltip(new Tooltip("El correo no puede modificarse"));

        // Contraseña actual (por seguridad)
        Label lblPassActual = new Label("Contraseña Actual:");
        PasswordField txtPassActual = new PasswordField();
        txtPassActual.setPromptText("Requerido para guardar cambios");
        
        // Nueva Contraseña
        Label lblNuevaPass = new Label("Nueva Contraseña:");
        PasswordField txtNuevaPass = new PasswordField();
        txtNuevaPass.setPromptText("Repita la nueva contraseña");

        // Confirmar Nueva Contraseña
        Label lblConfirmPass = new Label("Confirmar Nueva:");
        PasswordField txtConfirmPass = new PasswordField();
        txtConfirmPass.setPromptText("Repita la nueva contraseña");

        // Añadir componentes al GRID
        grid.addRow(0, lblNombre, txtNombre);
        grid.addRow(1, new Separator(), new Separator()); // Separador visual
        grid.addRow(2, lblPassActual, txtPassActual);
        grid.addRow(3, lblNuevaPass, txtNuevaPass);
        grid.addRow(4, lblConfirmPass, txtConfirmPass);

        
        // Botones
        HBox botones = new HBox(15);
        botones.setAlignment(Pos.CENTER);
        
        Button btnGuardar = new Button("Guardar Cambios");
        btnGuardar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        
        btnGuardar.setOnAction(e -> {
            String nuevoNombre = txtNombre.getText().trim();
            String passActual = txtPassActual.getText();
            String passNueva = txtNuevaPass.getText();
            String passConfirm = txtConfirmPass.getText();
            
            if (nuevoNombre.isEmpty()) {
                mostrarError("Error", "El nombre no puede estar vacío");
                return;
            }

            if (passActual.isEmpty()) {
                mostrarError("Seguridad", "Debe ingresar su contraseña actual para confirmar los cambios.");
                return;
            }

            if (!estudianteActual.verificarContrasena(passActual)) {
                mostrarError("Error", "La contraseña actual es incorrecta.");
                return;
            }
            
            boolean cambioPass = false;
            if (!passNueva.isEmpty()) {
                if (!passNueva.equals(passConfirm)) {
                    mostrarError("Error", "Las nuevas contraseñas no coinciden.");
                    return;
                }
                if (passNueva.length() < 6) { // Ejemplo de regla de negocio
                    mostrarError("Seguridad", "La nueva contraseña debe tener al menos 6 caracteres.");
                    return;
                }
                cambioPass = true;
            }

            // Aplicar cambios
            estudianteActual.setNombre(nuevoNombre);
            if (cambioPass) {
                estudianteActual.setContrasena(passNueva);
            }

            // Persistencia
            boolean exito = controlador.actualizarUsuario(estudianteActual);

            if (exito) {
                mostrarError("Éxito", "Perfil actualizado correctamente.");
                dialogStage.close();
                // Refrescar vista principal si es necesario (ej: el nombre en la barra superior)
                mostrar(); 
            } else {
                mostrarError("Error Crítico", "No se pudo guardar en el archivo.");
            }
        });
        
        btnCancelar.setOnAction(e -> dialogStage.close());
        
        contenido.getChildren().addAll(titulo, grid, botones);
        Scene scene = new Scene(contenido, 500, 400);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    // MÉTODOS AUXILIARES para alertas
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // MÉTODO: Abrir búsqueda de tutores
    private void abrirBusquedaTutores() {
        // Mostrar panel de búsqueda dentro del centro del BorderPane
        layoutPrincipal.setCenter(crearPanelBusqueda());
    }

    // MÉTODO: Abrir formulario de agendamiento
    private void abrirAgendamiento() {
        layoutPrincipal.setCenter(crearPanelAgendamiento());
    }

    // Panel embebido: búsqueda de tutores
    private VBox crearPanelBusqueda() {
        VBox cont = new VBox(15);
        cont.setPadding(new Insets(24));
        cont.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label("Buscar Tutores por Materia");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        TextField campoMateria = new TextField();
        campoMateria.setPromptText("Escribe una materia...");
        campoMateria.setPrefWidth(300);

        Button btnBuscar = new Button("Buscar");
        HBox fila = new HBox(10, campoMateria, btnBuscar);
        fila.setAlignment(Pos.CENTER);

        TableView<Tutor> tabla = new TableView<>();
        TableColumn<Tutor, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<Tutor, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        TableColumn<Tutor, String> colMateria = new TableColumn<>("Primera Materia");
        colMateria.setCellValueFactory(data -> {
            List<String> materias = data.getValue().getMaterias();
            String m = (materias != null && !materias.isEmpty()) ? materias.get(0) : "—";
            return new javafx.beans.property.SimpleStringProperty(m);
        });
        tabla.getColumns().addAll(colNombre, colCorreo, colMateria);
        tabla.setPrefHeight(300);

        List<Usuario> todos = controlador.getListaDeUsuarios();
        for (Usuario u : todos) if (u.getRol() == Rol.TUTOR) tabla.getItems().add((Tutor) u);

        btnBuscar.setOnAction(e -> {
            String filtro = campoMateria.getText().trim().toLowerCase();
            tabla.getItems().clear();
            for (Usuario u : todos) {
                if (u.getRol() == Rol.TUTOR) {
                    Tutor t = (Tutor) u;
                    for (String mat : t.getMaterias()) {
                        if (mat.toLowerCase().contains(filtro)) {
                            tabla.getItems().add(t);
                            break;
                        }
                    }
                }
            }
        });

        cont.getChildren().addAll(titulo, fila, tabla);
        return cont;
    }

    // Panel embebido: agendamiento
    private VBox crearPanelAgendamiento() {
        VBox cont = new VBox(20);
        cont.setPadding(new Insets(30));
        cont.setAlignment(Pos.TOP_CENTER);
        cont.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10;");
        cont.setMaxWidth(800);

        // Título principal
        Label titulo = new Label("Agendar Nueva Sesión");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setStyle("-fx-text-fill: #0a2e5a;");

        Separator separador = new Separator();
        separador.setPrefWidth(750);

        // PASO 1: Selección de Materia
        VBox seccionMateria = new VBox(10);
        seccionMateria.setPadding(new Insets(15));
        seccionMateria.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 8; -fx-border-color: #dee2e6; -fx-border-width: 1; -fx-border-radius: 8;");

        Label lblPaso1 = new Label("PASO 1: Selecciona la Materia");
        lblPaso1.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblPaso1.setStyle("-fx-text-fill: #0a2e5a;");

        Label lblInstruccion = new Label("Elige la materia para la cual necesitas tutoría:");
        lblInstruccion.setFont(Font.font("Arial", 13));
        lblInstruccion.setStyle("-fx-text-fill: #6c757d;");

        ComboBox<String> comboMaterias = new ComboBox<>();
        comboMaterias.setPromptText("Selecciona una materia...");
        comboMaterias.setPrefWidth(400);
        comboMaterias.setPrefHeight(40);
        comboMaterias.setStyle("-fx-font-size: 14px;");

        // Cargar materias disponibles
        Set<String> materiasDisponibles = obtenerMateriasDisponibles();
        comboMaterias.getItems().addAll(materiasDisponibles);

        seccionMateria.getChildren().addAll(lblPaso1, lblInstruccion, comboMaterias);

        // PASO 2: Selección de Tutor con Tabla (inicialmente oculto)
        VBox seccionTutor = new VBox(10);
        seccionTutor.setPadding(new Insets(15));
        seccionTutor.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 8; -fx-border-color: #dee2e6; -fx-border-width: 1; -fx-border-radius: 8;");
        seccionTutor.setVisible(false);
        seccionTutor.setManaged(false);

        Label lblPaso2 = new Label("PASO 2: Selecciona un Tutor");
        lblPaso2.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblPaso2.setStyle("-fx-text-fill: #0a2e5a;");

        Label lblInfoTutor = new Label("Haz clic en un tutor de la tabla para seleccionarlo:");
        lblInfoTutor.setFont(Font.font("Arial", 12));
        lblInfoTutor.setStyle("-fx-text-fill: #6c757d; -fx-font-style: italic;");

        // Tabla de tutores filtrados
        TableView<Tutor> tablaTutores = new TableView<>();
        tablaTutores.setPrefHeight(200);
        tablaTutores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<Tutor, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colId.setPrefWidth(60);

        TableColumn<Tutor, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(200);

        TableColumn<Tutor, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colCorreo.setPrefWidth(180);

        TableColumn<Tutor, Double> colTarifa = new TableColumn<>("Tarifa (Q)");
        colTarifa.setCellValueFactory(new PropertyValueFactory<>("tarifa"));
        colTarifa.setPrefWidth(100);

        tablaTutores.getColumns().addAll(colId, colNombre, colCorreo, colTarifa);

        seccionTutor.getChildren().addAll(lblPaso2, lblInfoTutor, tablaTutores);

        // PASO 3: Fecha y Hora (inicialmente oculto)
        VBox seccionFecha = new VBox(15);
        seccionFecha.setPadding(new Insets(15));
        seccionFecha.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 8; -fx-border-color: #dee2e6; -fx-border-width: 1; -fx-border-radius: 8;");
        seccionFecha.setVisible(false);
        seccionFecha.setManaged(false);

        Label lblPaso3 = new Label("PASO 3: Selecciona Fecha y Hora");
        lblPaso3.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblPaso3.setStyle("-fx-text-fill: #0a2e5a;");

        // === SELECTOR DE FECHA ===
        Label lblFecha = new Label("Fecha de la sesión:");
        lblFecha.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Selecciona una fecha");
        datePicker.setPrefWidth(250);
        datePicker.setPrefHeight(35);
        datePicker.setStyle("-fx-font-size: 14px;");

        // SELECTOR DE HORA CON SPINNER
        Label lblHora = new Label("Hora de la sesión:");
        lblHora.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        // Spinner de horas (0-23)
        Spinner<Integer> spinnerHora = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactoryHora = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        spinnerHora.setValueFactory(valueFactoryHora);
        spinnerHora.setPrefWidth(80);
        spinnerHora.setPrefHeight(35);
        spinnerHora.setEditable(true);
        spinnerHora.setStyle("-fx-font-size: 14px;");
        
        Label lblDosPuntos = new Label(":");
        lblDosPuntos.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblDosPuntos.setStyle("-fx-text-fill: #2c3e50;");
        
        // Spinner de minutos (0-59)
        Spinner<Integer> spinnerMinuto = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactoryMinuto = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        spinnerMinuto.setValueFactory(valueFactoryMinuto);
        spinnerMinuto.setPrefWidth(80);
        spinnerMinuto.setPrefHeight(35);
        spinnerMinuto.setEditable(true);
        spinnerMinuto.setStyle("-fx-font-size: 14px;");

        // Layout para fecha
        VBox contenedorFecha = new VBox(5);
        contenedorFecha.getChildren().addAll(lblFecha, datePicker);

        // Layout para hora con spinners
        HBox spinnersHora = new HBox(5);
        spinnersHora.setAlignment(Pos.CENTER_LEFT);
        spinnersHora.getChildren().addAll(spinnerHora, lblDosPuntos, spinnerMinuto);
        
        VBox contenedorHora = new VBox(5);
        contenedorHora.getChildren().addAll(lblHora, spinnersHora);


        // Contenedor horizontal para fecha y hora
        HBox contenedorFechaHora = new HBox(20);
        contenedorFechaHora.setAlignment(Pos.CENTER_LEFT);
        contenedorFechaHora.getChildren().addAll(contenedorFecha, contenedorHora);

        seccionFecha.getChildren().addAll(lblPaso3, contenedorFechaHora);

        // Botón de agendar
        Button btnAgendar = new Button("Agendar Sesión");
        btnAgendar.setPrefWidth(250);
        btnAgendar.setPrefHeight(45);
        btnAgendar.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        btnAgendar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 8;");
        btnAgendar.setDisable(true);

        // Efectos hover
        btnAgendar.setOnMouseEntered(e -> {
            if (!btnAgendar.isDisabled()) {
                btnAgendar.setStyle("-fx-background-color: #218838; -fx-text-fill: white; -fx-background-radius: 8;");
            }
        });
        btnAgendar.setOnMouseExited(e -> {
            if (!btnAgendar.isDisabled()) {
                btnAgendar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 8;");
            }
        });

        HBox contenedorBoton = new HBox(btnAgendar);
        contenedorBoton.setAlignment(Pos.CENTER);
        contenedorBoton.setPadding(new Insets(20, 0, 0, 0));

        // Logica filtrado dinámico de tutores
        // Cuando se selecciona una materia
        comboMaterias.setOnAction(e -> {
            String materiaSeleccionada = comboMaterias.getValue();
            if (materiaSeleccionada != null && !materiaSeleccionada.isEmpty()) {
                // Filtrar tutores por materia
                List<Tutor> tutoresFiltrados = obtenerTutoresPorMateria(materiaSeleccionada);
                tablaTutores.getItems().clear();
                tablaTutores.getItems().addAll(tutoresFiltrados);
                
                // Mostrar siguiente paso
                seccionTutor.setVisible(true);
                seccionTutor.setManaged(true);
                
                // Resetear selección
                seccionFecha.setVisible(false);
                seccionFecha.setManaged(false);
                btnAgendar.setDisable(true);
            } else {
                seccionTutor.setVisible(false);
                seccionTutor.setManaged(false);
                seccionFecha.setVisible(false);
                seccionFecha.setManaged(false);
                btnAgendar.setDisable(true);
            }
        });

        // Cuando se selecciona un tutor de la tabla
        tablaTutores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {                
                // Mostrar paso de fecha
                seccionFecha.setVisible(true);
                seccionFecha.setManaged(true);
                btnAgendar.setDisable(false);
            }
        });

        // Acción del botón agendar
        btnAgendar.setOnAction(e -> {
            String materiaSeleccionada = comboMaterias.getValue();
            Tutor tutorSeleccionado = tablaTutores.getSelectionModel().getSelectedItem();
            LocalDate fechaSeleccionada = datePicker.getValue();
            Integer hora = spinnerHora.getValue();
            Integer minuto = spinnerMinuto.getValue();

            // Validaciones
            if (materiaSeleccionada == null || materiaSeleccionada.isEmpty() || tutorSeleccionado == null || fechaSeleccionada == null ) {
                mostrarError("Error", "Debe de llenar todos los espacios");
                return;
            }

            // Construir fecha y hora en el formato que espera el sistema
            DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("dd/MM/yy");
            String fechaFormateada = fechaSeleccionada.format(formatterFecha);
            String horaFormateada = String.format("%02d:%02d", hora, minuto);
            String fechaHoraCompleta = horaFormateada + " " + fechaFormateada;

            Sesion s = controlador.manejarAgendamientoSesion( estudianteActual.getIdUsuario(), tutorSeleccionado.getIdUsuario(), materiaSeleccionada, fechaHoraCompleta );
            
            if (s != null) {
                mostrarInfo("Éxito", 
                    "Sesión agendada correctamente\n\n" + 
                    "Materia: " + materiaSeleccionada + "\n" + 
                    "Tutor: " + tutorSeleccionado.getNombre() + "\n" + 
                    "Fecha: " + fechaFormateada + "\n" +
                    "Hora: " + horaFormateada + "\n\n" +
                    "El tutor debe aprobar tu solicitud.");                                
                // Limpiar campos después de agendar
                comboMaterias.setValue(null);
                seccionTutor.setVisible(false);
                seccionTutor.setManaged(false);
                seccionFecha.setVisible(false);
                seccionFecha.setManaged(false);
                btnAgendar.setDisable(true);
                
            } else {
                mostrarError("Error al Agendar", 
                    "No se pudo agendar la sesión.\n\n" +
                    "Posibles causas:\n" +
                    "- El tutor ya está ocupado en ese horario.\n" +
                    "- Ya tienes otra sesión a esa hora.\n" +
                    "- La fecha seleccionada está en el pasado.");
            }
        });
        cont.getChildren().addAll( titulo, separador, seccionMateria, seccionTutor, seccionFecha, contenedorBoton );
        return cont;
    }

    // MÉTODO: Mostrar historial completo en tabla (embebido en la vista principal)
    private void mostrarHistorialCompleto() {
        // Mostrar el historial completo dentro del panel central del BorderPane
        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));

        Label titulo = new Label("📊 Historial Completo");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<Sesion> tabla = new TableView<>();
        tabla.setPrefHeight(400);

        // Columnas
        TableColumn<Sesion, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdSesion()));
        colId.setPrefWidth(50);

        TableColumn<Sesion, String> colMateria = new TableColumn<>("Materia");
        colMateria.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMateria()));
        colMateria.setPrefWidth(150);

        // CAMBIO: Se usa un CellValueFactory para combinar fecha y hora
        TableColumn<Sesion, String> colFecha = new TableColumn<>("Fecha y Hora");
        colFecha.setCellValueFactory(data -> {
            String fechaHoraStr = data.getValue().getFecha() + " " + data.getValue().getHora();
            return new javafx.beans.property.SimpleStringProperty(fechaHoraStr);
        });
        colFecha.setPrefWidth(150);

        TableColumn<Sesion, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEstado().toString()));
        colEstado.setPrefWidth(120);

        tabla.getColumns().addAll(colId, colMateria, colFecha, colEstado);

        ArrayList<Sesion> historial = estudianteActual.getHistorialSesiones();
        if (historial != null && !historial.isEmpty()) tabla.getItems().addAll(historial);

        Button btnVolver = new Button("Volver");
        btnVolver.setOnAction(e -> layoutPrincipal.setCenter(crearPanelHistorial()));

        HBox botones = new HBox(btnVolver);
        botones.setAlignment(Pos.CENTER);
        botones.setPadding(new Insets(10, 0, 0, 0));

        contenido.getChildren().addAll(titulo, tabla, botones);

        layoutPrincipal.setCenter(contenido);
    }

    //Obtiene un conjunto de todas las materias únicas que imparten los tutores registrados
    private Set<String> obtenerMateriasDisponibles() {
        Set<String> materias = new HashSet<>();
        
        List<Usuario> usuarios = controlador.getListaDeUsuarios();
        for (Usuario u : usuarios) {
            if (u.getRol() == Rol.TUTOR) {
                Tutor tutor = (Tutor) u;
                List<String> materiasTutor = tutor.getMaterias();
                if (materiasTutor != null) {
                    materias.addAll(materiasTutor);
                }
            }
        }
        return materias;
    }

    //Obtiene una lista de tutores que imparten una materia específica
    private List<Tutor> obtenerTutoresPorMateria(String materia) {
        List<Tutor> tutoresFiltrados = new ArrayList<>();
        
        List<Usuario> usuarios = controlador.getListaDeUsuarios();
        for (Usuario u : usuarios) {
            if (u.getRol() == Rol.TUTOR) {
                Tutor tutor = (Tutor) u;
                List<String> materiasTutor = tutor.getMaterias();
                if (materiasTutor != null && materiasTutor.contains(materia)) {
                    tutoresFiltrados.add(tutor);
                }
            }
        }
        return tutoresFiltrados;
    }
}