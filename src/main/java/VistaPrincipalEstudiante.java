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
    
    // Paneles temporales (se implementarán en los siguientes commits)
    private VBox crearPanelMiPerfil() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));
        Label temp = new Label("Panel Mi Perfil - En construcción...");
        temp.setFont(Font.font("Arial", 18));
        panel.getChildren().add(temp);
        return panel;
    }
    
    private VBox crearPanelBuscarTutores() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));
        Label temp = new Label("Panel Buscar Tutores - En construcción...");
        temp.setFont(Font.font("Arial", 18));
        panel.getChildren().add(temp);
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