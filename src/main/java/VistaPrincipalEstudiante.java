import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList; 

public class VistaPrincipalEstudiante {
    
    private Estudiante estudianteActual;
    private ControladorPrincipal controlador;
    private Stage stage;
    private Main mainApp;
    
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
        BorderPane layoutPrincipal = new BorderPane();

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
        Scene escena = new Scene(layoutPrincipal, 1100, 700);
        stage.setScene(escena);  // establecer la escena en la ventana principal
        stage.setTitle("Panel del Estudiante - Gestor de Tutorías UVG"); // Título de ventana
        // establecer tamaño mínimo para evitar deformaciones
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show(); // mostrar ventana al cliente
    }

    // Métodos privados para construir las partes de la UI
    private HBox crearBarraSuperior() {
        HBox barra = new HBox(20);
        barra.setPadding(new Insets(15));
        barra.setStyle("-fx-background-color: #0a2e5a;"); // Color azul marino para estudiante
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
        btnEditarPerfil.setStyle("-fx-font-size: 13px; -fx-padding: 8 15 8 15;");
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
        btnVerHistorial.setStyle("-fx-font-size: 13px; -fx-padding: 8 15 8 15;");
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
                String texto = String.format("%s - %s (%s)", 
                    s.getMateria(), 
                    s.getFechaHora(), 
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

        // Efecto hover
        boton.setOnMouseEntered(e -> boton.setStyle("-fx-background-color: #1a3f6d; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;"));
        boton.setOnMouseExited(e -> boton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;"));
        
        return boton;
    }

    // Abrir diálogo de edición
    private void abrirEdicionPerfil() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Editar Perfil");
        
        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));
        contenido.setAlignment(Pos.CENTER);
        
        Label titulo = new Label("Editar Información Personal");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Campos editables
        Label lblNuevoNombre = new Label("Nombre:");
        TextField txtNombre = new TextField(estudianteActual.getNombre());
        txtNombre.setPrefWidth(300);
        
        Label lblNuevoCorreo = new Label("Correo:");
        TextField txtCorreo = new TextField(estudianteActual.getCorreo());
        txtCorreo.setPrefWidth(300);
        txtCorreo.setDisable(true);
        txtCorreo.setTooltip(new Tooltip("El correo no puede modificarse"));
        
        Label lblNuevaPass = new Label("Nueva Contraseña (opcional):");
        PasswordField txtPass = new PasswordField();
        txtPass.setPrefWidth(300);
        txtPass.setPromptText("Dejar vacío para mantener la actual");
        
        // Botones
        HBox botones = new HBox(10);
        botones.setAlignment(Pos.CENTER);
        
        Button btnGuardar = new Button("Guardar Cambios");
        Button btnCancelar = new Button("Cancelar");
        
        btnGuardar.setStyle("-fx-font-size: 13px; -fx-padding: 8 15 8 15;");
        btnCancelar.setStyle("-fx-font-size: 13px; -fx-padding: 8 15 8 15;");
        
        btnGuardar.setOnAction(e -> {
            String nuevoNombre = txtNombre.getText().trim();
            String nuevaPass = txtPass.getText().trim();
            
            if (nuevoNombre.isEmpty()) {
                mostrarError("Error", "El nombre no puede estar vacío");
                return;
            }
            
            if (!nuevoNombre.equals(estudianteActual.getNombre())) {
                mostrarInfo("Edición", "Nombre actualizado correctamente");
                lblNombre.setText("Nombre: " + nuevoNombre);
            }
            
            if (!nuevaPass.isEmpty()) {
                mostrarInfo("Edición", "Contraseña actualizada correctamente");
            }
            
            dialogStage.close();
        });
        
        btnCancelar.setOnAction(e -> dialogStage.close());
        
        botones.getChildren().addAll(btnGuardar, btnCancelar);
        
        contenido.getChildren().addAll(
            titulo,
            new Separator(),
            lblNuevoNombre, txtNombre,
            lblNuevoCorreo, txtCorreo,
            lblNuevaPass, txtPass,
            new Separator(),
            botones
        );
        
        Scene scene = new Scene(contenido, 400, 400);
        dialogStage.setScene(scene);
        dialogStage.show();
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
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Buscar Tutores");
        
        BusquedaVista busquedaVista = new BusquedaVista();
        
        Scene scene = new Scene(busquedaVista, 700, 500);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    // MÉTODO: Abrir formulario de agendamiento
    private void abrirAgendamiento() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Agendar Sesión de Tutoría");
        
        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));
        contenido.setAlignment(Pos.CENTER);
        
        Label titulo = new Label("Agendar Nueva Sesión");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Campos del formulario
        Label lblTutorId = new Label("ID del Tutor:");
        TextField txtTutorId = new TextField();
        txtTutorId.setPromptText("Ej: 2");
        txtTutorId.setPrefWidth(300);
        
        Label lblMateria = new Label("Materia:");
        TextField txtMateria = new TextField();
        txtMateria.setPromptText("Ej: Matemática, Física, Programación");
        txtMateria.setPrefWidth(300);
        
        Label lblFecha = new Label("Fecha y Hora:");
        TextField txtFecha = new TextField();
        txtFecha.setPromptText("Formato: HH:mm dd/MM/yy (Ej: 14:30 25/10/25)");
        txtFecha.setPrefWidth(300);
        
        Label lblAyuda = new Label("💡 Tip: Busca tutores antes de agendar para obtener su ID");
        lblAyuda.setStyle("-fx-font-size: 11px; -fx-text-fill: #666;");
        
        // Botones
        HBox botones = new HBox(10);
        botones.setAlignment(Pos.CENTER);
        
        Button btnAgendar = new Button("Agendar");
        Button btnCancelar = new Button("Cancelar");
        
        btnAgendar.setStyle("-fx-font-size: 13px; -fx-padding: 8 15 8 15;");
        btnCancelar.setStyle("-fx-font-size: 13px; -fx-padding: 8 15 8 15;");
        
        btnAgendar.setOnAction(e -> {
            String tutorIdStr = txtTutorId.getText().trim();
            String materia = txtMateria.getText().trim();
            String fechaHora = txtFecha.getText().trim();
            
            if (tutorIdStr.isEmpty() || materia.isEmpty() || fechaHora.isEmpty()) {
                mostrarError("Error", "Todos los campos son obligatorios");
                return;
            }
            
            try {
                int tutorId = Integer.parseInt(tutorIdStr);
                
                // Llamar al controlador para agendar
                Sesion nuevaSesion = controlador.manejarAgendamientoSesion(
                    estudianteActual.getIdUsuario(),
                    tutorId,
                    materia,
                    fechaHora
                );
                
                if (nuevaSesion != null) {
                    estudianteActual.agendarSesion(nuevaSesion);
                    mostrarInfo("Éxito", "Sesión agendada correctamente");
                    dialogStage.close();
                }
                
            } catch (NumberFormatException ex) {
                mostrarError("Error", "El ID del tutor debe ser un número válido");
            }
        });
        
        btnCancelar.setOnAction(e -> dialogStage.close());
        
        botones.getChildren().addAll(btnAgendar, btnCancelar);
        
        contenido.getChildren().addAll(
            titulo,
            new Separator(),
            lblTutorId, txtTutorId,
            lblMateria, txtMateria,
            lblFecha, txtFecha,
            lblAyuda,
            new Separator(),
            botones
        );
        
        Scene scene = new Scene(contenido, 450, 450);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    // MÉTODO: Mostrar historial completo en tabla
    private void mostrarHistorialCompleto() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Historial Completo de Sesiones");
        
        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));
        
        Label titulo = new Label("📊 Historial Completo");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        TableView<Sesion> tabla = new TableView<>();
        tabla.setPrefHeight(400);
        
        // Columnas
        TableColumn<Sesion, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getIdSesion()));
        colId.setPrefWidth(50);
        
        TableColumn<Sesion, String> colMateria = new TableColumn<>("Materia");
        colMateria.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getMateria()));
        colMateria.setPrefWidth(150);
        
        TableColumn<Sesion, String> colFecha = new TableColumn<>("Fecha y Hora");
        colFecha.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getFechaHora()));
        colFecha.setPrefWidth(150);
        
        TableColumn<Sesion, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getEstado().toString()));
        colEstado.setPrefWidth(120);
        
        tabla.getColumns().addAll(colId, colMateria, colFecha, colEstado);
        
        ArrayList<Sesion> historial = estudianteActual.getHistorialSesiones();
        if (historial != null && !historial.isEmpty()) {
            tabla.getItems().addAll(historial);
        }
        
        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setOnAction(e -> dialogStage.close());
        
        HBox botones = new HBox(btnCerrar);
        botones.setAlignment(Pos.CENTER);
        botones.setPadding(new Insets(10, 0, 0, 0));
        
        contenido.getChildren().addAll(titulo, tabla, botones);
        
        Scene scene = new Scene(contenido, 550, 500);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

}
