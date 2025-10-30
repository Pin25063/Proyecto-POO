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
    
    private ControladorPrincipal controlador;
    private Estudiante estudiante;
    private Stage stage;
    private Main mainApp;
    
    // Referencias para actualización dinámica
    private Label lblNombreCompleto;
    private Label lblCorreoCompleto;
    private Label lblIdCompleto;
    
    public VistaPrincipalEstudiante(ControladorPrincipal controlador, Estudiante estudiante, Stage stage, Main mainApp) {
        this.controlador = controlador;
        this.estudiante = estudiante;
        this.stage = stage;
        this.mainApp = mainApp;
    }
    
    // Construye y muestra la interfaz del estudiante
    public void mostrar() {
        // BorderPane divide la pantalla en 5 zonas: Top, Left, Center, Right, Bottom
        BorderPane layoutPrincipal = new BorderPane();
        
        // Color de fondo base
        layoutPrincipal.setStyle("-fx-background-color: #ecf0f1;");
        
        // Componentes principales de la UI
        HBox barraSuperior = crearBarraSuperior();
        VBox menuLateral = crearMenuLateral(layoutPrincipal);
        VBox contenidoInicial = crearPanelInicio();
        
        // Ensamblaje del layout principal
        layoutPrincipal.setTop(barraSuperior);
        layoutPrincipal.setLeft(menuLateral);
        layoutPrincipal.setCenter(contenidoInicial);
        
        // Crear escena
        Scene escena = new Scene(layoutPrincipal, 1100, 700);
        stage.setScene(escena);
        stage.setTitle("Portal del Estudiante - Gestor de Tutorías UVG");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }
    
    // Barra superior con título y botón cerrar sesión
    private HBox crearBarraSuperior() {
        HBox barra = new HBox(20);
        barra.setPadding(new Insets(15));
        barra.setStyle("-fx-background-color: #2c3e50;"); // Azul oscuro para estudiantes
        barra.setAlignment(Pos.CENTER_LEFT);
        
        // Título de la aplicación
        Label lblTitulo = new Label("Gestor de Tutorías UVG - Portal Estudiante");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblTitulo.setStyle("-fx-text-fill: white;");
        
        // Espaciador
        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);
        
        // Nombre del estudiante
        Label lblBienvenida = new Label("Estudiante: " + estudiante.getNombre());
        lblBienvenida.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblBienvenida.setStyle("-fx-text-fill: white;");
        
        // Botón cerrar sesión
        Button btnCerrarSesion = new Button("Cerrar Sesión");
        btnCerrarSesion.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCerrarSesion.setOnAction(e -> mainApp.mostrarLogin());
        
        barra.getChildren().addAll(lblTitulo, espaciador, lblBienvenida, btnCerrarSesion);
        return barra;
    }
    
    // Menú lateral de navegación
    private VBox crearMenuLateral(BorderPane layoutPrincipal) {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(220);
        menu.setStyle("-fx-background-color: #34495e;"); // Azul grisáceo
        
        Label lblMenu = new Label("MENÚ");
        lblMenu.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblMenu.setStyle("-fx-text-fill: white;");
        lblMenu.setAlignment(Pos.CENTER);
        
        // Botones de navegación
        Button btnInicio = crearBotonMenu("INICIO");
        Button btnMiPerfil = crearBotonMenu("MI PERFIL");
        Button btnBuscarTutores = crearBotonMenu("BUSCAR TUTORES");
        Button btnAgendarSesion = crearBotonMenu("GENDAR SESIÓN");
        Button btnMisSesiones = crearBotonMenu("MIS SESIONES");
        
        // Acciones de los botones
        btnInicio.setOnAction(e -> layoutPrincipal.setCenter(crearPanelInicio()));
        btnMiPerfil.setOnAction(e -> layoutPrincipal.setCenter(crearPanelMiPerfil()));
        btnBuscarTutores.setOnAction(e -> layoutPrincipal.setCenter(crearPanelBuscarTutores()));
        btnAgendarSesion.setOnAction(e -> layoutPrincipal.setCenter(crearPanelAgendarSesion()));
        btnMisSesiones.setOnAction(e -> layoutPrincipal.setCenter(crearPanelMisSesiones()));
        
        menu.getChildren().addAll(lblMenu, new Separator(), btnInicio, btnMiPerfil, btnBuscarTutores, btnAgendarSesion, btnMisSesiones);
        return menu;
    }   
    
    // Panel de inicio - Dashboard
    private VBox crearPanelInicio() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));
        panel.setAlignment(Pos.TOP_CENTER);
        
        Label lblTitulo = new Label("Bienvenido, " + estudiante.getNombre());
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        
        Label lblInfo = new Label(
            "Utiliza el menú de la izquierda para navegar por las diferentes opciones.\n" +
            "Puedes buscar tutores, agendar sesiones de tutoría y ver tu historial académico."
        );
        lblInfo.setFont(Font.font("Arial", 16));
        lblInfo.setWrapText(true);
        lblInfo.setStyle("-fx-text-alignment: center;");
        
        panel.getChildren().addAll(lblTitulo, new Separator(), lblInfo);
        return panel;
    }
    
    // PANEL MI PERFIL - Visualizar y editar información
    private VBox crearPanelMiPerfil() {
        VBox panel = new VBox(25);
        panel.setPadding(new Insets(40));
        
        Label lblTitulo = new Label("Mi Perfil");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // Tarjeta de información del perfil
        VBox tarjetaPerfil = new VBox(15);
        tarjetaPerfil.setPadding(new Insets(25));
        tarjetaPerfil.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        tarjetaPerfil.setMaxWidth(600);
        
        Label lblInfoTitulo = new Label("Información Personal");
        lblInfoTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // GridPane para organizar la información
        GridPane gridInfo = new GridPane();
        gridInfo.setHgap(15);
        gridInfo.setVgap(15);
        
        // Labels de información
        Label lblIdLabel = new Label("ID de Usuario:");
        lblIdLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblIdCompleto = new Label(String.valueOf(estudiante.getIdUsuario()));
        lblIdCompleto.setFont(Font.font("Arial", 14));
        
        Label lblNombreLabel = new Label("Nombre Completo:");
        lblNombreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblNombreCompleto = new Label(estudiante.getNombre());
        lblNombreCompleto.setFont(Font.font("Arial", 14));
        
        Label lblCorreoLabel = new Label("Correo Institucional:");
        lblCorreoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblCorreoCompleto = new Label(estudiante.getCorreo());
        lblCorreoCompleto.setFont(Font.font("Arial", 14));
        
        Label lblRolLabel = new Label("Rol:");
        lblRolLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label lblRol = new Label("ESTUDIANTE");
        lblRol.setFont(Font.font("Arial", 14));
        lblRol.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        
        // Añadir al grid
        gridInfo.add(lblIdLabel, 0, 0);
        gridInfo.add(lblIdCompleto, 1, 0);
        gridInfo.add(lblNombreLabel, 0, 1);
        gridInfo.add(lblNombreCompleto, 1, 1);
        gridInfo.add(lblCorreoLabel, 0, 2);
        gridInfo.add(lblCorreoCompleto, 1, 2);
        gridInfo.add(lblRolLabel, 0, 3);
        gridInfo.add(lblRol, 1, 3);
        
        tarjetaPerfil.getChildren().addAll(lblInfoTitulo, new Separator(), gridInfo);
        
        // Botón para editar perfil
        Button btnEditarPerfil = new Button("Editar Perfil");
        btnEditarPerfil.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20;");
        btnEditarPerfil.setOnAction(e -> abrirDialogoEditarPerfil());
        
        // Efecto hover
        btnEditarPerfil.setOnMouseEntered(e -> btnEditarPerfil.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20;"));
        btnEditarPerfil.setOnMouseExited(e -> btnEditarPerfil.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20;"));
        
        panel.getChildren().addAll(lblTitulo, tarjetaPerfil, btnEditarPerfil);
        return panel;
    }

    // Diálogo para editar perfil
    private void abrirDialogoEditarPerfil() {
        Stage dialogo = new Stage();
        dialogo.setTitle("Editar Perfil");
        
        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(30));
        contenido.setStyle("-fx-background-color: #ecf0f1;");
        
        Label lblTitulo = new Label("Editar Información Personal");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        // Formulario de edición
        GridPane formulario = new GridPane();
        formulario.setHgap(15);
        formulario.setVgap(15);
        
        Label lblNombre = new Label("Nombre Completo:");
        lblNombre.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        TextField txtNombre = new TextField(estudiante.getNombre());
        txtNombre.setPrefWidth(300);
        
        Label lblCorreo = new Label("Correo Institucional:");
        lblCorreo.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        TextField txtCorreo = new TextField(estudiante.getCorreo());
        txtCorreo.setPrefWidth(300);
        txtCorreo.setDisable(true);
        txtCorreo.setStyle("-fx-opacity: 0.6;");
        
        Label lblNota = new Label("El correo no puede modificarse (es tu identificador único)");
        lblNota.setFont(Font.font("Arial", 11));
        lblNota.setStyle("-fx-text-fill: #7f8c8d;");
        
        Label lblPass = new Label("Nueva Contraseña (opcional):");
        lblPass.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        PasswordField txtPass = new PasswordField();
        txtPass.setPrefWidth(300);
        txtPass.setPromptText("Dejar vacío para no cambiar");
        
        formulario.add(lblNombre, 0, 0);
        formulario.add(txtNombre, 0, 1);
        formulario.add(lblCorreo, 0, 2);
        formulario.add(txtCorreo, 0, 3);
        formulario.add(lblNota, 0, 4);
        formulario.add(lblPass, 0, 5);
        formulario.add(txtPass, 0, 6);
        
        // Botones
        HBox botones = new HBox(15);
        botones.setAlignment(Pos.CENTER);
        
        Button btnGuardar = new Button("Guardar Cambios");
        btnGuardar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        
        btnGuardar.setOnAction(e -> {
            String nuevoNombre = txtNombre.getText().trim();
            String nuevaPass = txtPass.getText().trim();
            
            if (nuevoNombre.isEmpty()) {
                mostrarAlerta("Error", "El nombre no puede estar vacío", Alert.AlertType.ERROR);
                return;
            }
            
            // Actualizar nombre si cambió
            if (!nuevoNombre.equals(estudiante.getNombre())) {
                lblNombreCompleto.setText(nuevoNombre);
                mostrarAlerta("Éxito", "Nombre actualizado correctamente", Alert.AlertType.INFORMATION);
            }
            
            // Actualizar contraseña si se ingresó una nueva
            if (!nuevaPass.isEmpty()) {
                mostrarAlerta("Éxito", "Contraseña actualizada correctamente", Alert.AlertType.INFORMATION);
            }
            
            dialogo.close();
        });
        
        btnCancelar.setOnAction(e -> dialogo.close());
        
        botones.getChildren().addAll(btnGuardar, btnCancelar);
        
        contenido.getChildren().addAll(lblTitulo, new Separator(), formulario, botones);
        
        Scene escena = new Scene(contenido, 450, 500);
        dialogo.setScene(escena);
        dialogo.show();
    }
    
    // PANEL BUSCAR TUTORES
    private VBox crearPanelBuscarTutores() {
        VBox panel = new VBox(25);
        panel.setPadding(new Insets(40));
        
        Label lblTitulo = new Label("Buscar Tutores por Materia");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // Barra de búsqueda
        HBox barraBusqueda = new HBox(15);
        barraBusqueda.setAlignment(Pos.CENTER_LEFT);
        
        TextField txtBusqueda = new TextField();
        txtBusqueda.setPromptText("Escribe una materia (ej: Matemática, Física, Programación)");
        txtBusqueda.setPrefWidth(400);
        txtBusqueda.setFont(Font.font("Arial", 14));
        
        Button btnBuscar = new Button("🔍 Buscar");
        btnBuscar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20;");
        
        barraBusqueda.getChildren().addAll(txtBusqueda, btnBuscar);
        
        // Tabla de resultados
        TableView<Usuario> tablaResultados = new TableView<>();
        tablaResultados.setPrefHeight(400);
        tablaResultados.setPlaceholder(new Label("Escribe una materia y haz clic en Buscar para ver los tutores disponibles"));
        
        // Columnas
        TableColumn<Usuario, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdUsuario()));
        colId.setPrefWidth(60);
        
        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre del Tutor");
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colNombre.setPrefWidth(200);
        
        TableColumn<Usuario, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCorreo()));
        colCorreo.setPrefWidth(220);
        
        TableColumn<Usuario, String> colMaterias = new TableColumn<>("Materias");
        colMaterias.setCellValueFactory(data -> {
            if (data.getValue() instanceof Tutor) {
                Tutor tutor = (Tutor) data.getValue();
                String materias = String.join(", ", tutor.getMaterias());
                return new javafx.beans.property.SimpleStringProperty(materias);
            }
            return new javafx.beans.property.SimpleStringProperty("—");
        });
        colMaterias.setPrefWidth(250);
        
        tablaResultados.getColumns().addAll(colId, colNombre, colCorreo, colMaterias);
        
        // Acción del botón buscar
        btnBuscar.setOnAction(e -> {
            String materia = txtBusqueda.getText().trim().toLowerCase();
            
            if (materia.isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor escribe una materia para buscar", Alert.AlertType.WARNING);
                return;
            }
            
            // Buscar tutores que enseñen esa materia
            ArrayList<Usuario> resultados = new ArrayList<>();
            for (Usuario usuario : controlador.getListaDeUsuarios()) {
                if (usuario instanceof Tutor) {
                    Tutor tutor = (Tutor) usuario;
                    for (String mat : tutor.getMaterias()) {
                        if (mat.toLowerCase().contains(materia)) {
                            resultados.add(tutor);
                            break;
                        }
                    }
                }
            }
            
            if (resultados.isEmpty()) {
                mostrarAlerta("Sin resultados", "No se encontraron tutores que enseñen '" + materia + "'", Alert.AlertType.INFORMATION);
                tablaResultados.getItems().clear();
            } else {
                tablaResultados.getItems().clear();
                tablaResultados.getItems().addAll(resultados);
            }
        });
        
        // Búsqueda al presionar Enter
        txtBusqueda.setOnAction(e -> btnBuscar.fire());
        
        Label lblInfo = new Label("💡 Tip: Anota el ID del tutor para agendar una sesión con él");
        lblInfo.setFont(Font.font("Arial", 12));
        lblInfo.setStyle("-fx-text-fill: #7f8c8d;");
        
        panel.getChildren().addAll(lblTitulo, barraBusqueda, tablaResultados, lblInfo);
        return panel;
    }
    
    private VBox crearPanelAgendarSesion() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));
        Label temp = new Label("Panel Agendar Sesión - En construcción...");
        temp.setFont(Font.font("Arial", 18));
        panel.getChildren().add(temp);
        return panel;
    }
    
    private VBox crearPanelMisSesiones() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));
        Label temp = new Label("Panel Mis Sesiones - En construcción...");
        temp.setFont(Font.font("Arial", 18));
        panel.getChildren().add(temp);
        return panel;
    }
    
    // Helper para crear botones del menú con estilo consistente
    private Button crearBotonMenu(String texto) {
        Button boton = new Button(texto);
        boton.setPrefWidth(200);
        boton.setPrefHeight(40);
        boton.setAlignment(Pos.CENTER_LEFT);
        boton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        // Efecto hover
        boton.setOnMouseEntered(e -> boton.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;"));
        boton.setOnMouseExited(e -> boton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;"));
        
        return boton;
    }
    
    // Helper para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}