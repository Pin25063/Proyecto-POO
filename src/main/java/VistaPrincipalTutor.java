import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;

public class VistaPrincipalTutor {

    //Atributos de la clase
    private ControladorPrincipal controlador;
    private Tutor tutorActual;
    private Stage stage;
    private Main mainApp;

    //Constructor de la clase
    public VistaPrincipalTutor(ControladorPrincipal controlador, Tutor tutor, Stage stage, Main mainApp) {
        this.controlador = controlador;
        this.tutorActual = tutor;
        this.stage = stage;
        this.mainApp = mainApp;
        
    }

    //Metodo de vizualizacion
    // Se construye y se muestra la interfaz del tutor

    public void mostrar() {
        //BorderPane divide la pagina en 5 zonas
        BorderPane layoutPrincipal = new BorderPane();
        layoutPrincipal.setStyle("-fx-background-color: #ecf0f1;");

        // Componentes de la UI
        HBox barraSuperior = crearBarraSup();
        VBox menuLateral = crearMenuLateral(layoutPrincipal);
        VBox contenidoInicial = crearPanelInicio();

        //Se arma el layout principal
        layouPrincipal.setTop(barraSuperior);
        //layouPrincipal.setLeft(menuLateral);
        //layouPrincipal.setCenter(contenidoInicial);

        //Se crea la escena y se configura la ventana
        Scene escena = new Scene(layouPrincipal, 1100, 700);
        stage.setScene(escena);
        stage.setTitle("Panel del Tutor - Gestor de Tutorías UVG");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    // Componentes de la interfaz
    //Se crea la barra superior con titulo, nombre de usuario y boton de cerrar sesion

    private HBox crearBarraSup() {
        HBox barra = new HBox(20);
        barra.setPadding(new Insets(15));
        barra.setStyle("-fx-background-color: #3d8335ff;");
        barra.setAlignment(Pos.CENTER_LEFT);

        // Titulo de la ventana
        Label lblTitulo =  new Label("Gestor de Tutorias UVG");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblTitulo.setStyle("-fx-text-fill: white;");

        // Espaciador para empujar elementos a la derecha
        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        // Información del usuario actual
        Label lblBienvenida = new Label("Tutor: " + tutorActual.getNombre());
        lblBienvenida.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblBienvenida.setStyle("-fx-text-fill: white;");

        // Botón de cerrar sesión
        Button btnCerrarSesion = new Button("Cerrar Sesión");
        btnCerrarSesion.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCerrarSesion.setOnAction(e -> mainApp.mostrarLogin());
        
        barra.getChildren().addAll(lblTitulo, espaciador, lblBienvenida, btnCerrarSesion);
        return barra;
    }

    // Se crea el menu lateral de navegacion con botones para cambiar de panel

    private VBox crearMenuLateral(BorderPane layoutPrincipal) {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(220);
        menu.setStyle("-fx-background-color: #3d8335ff;");

        Label lblMenu = new Label("MENÚ");
        lblMenu.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblMenu.setStyle("-fx-text-fill: white;");
        lblMenu.setAlignment(Pos.CENTER);

        //Se crean los botones de navegacion de la barra lateral
        Button btnInicio = crearBotonMenu("INICIO");
        Button btnPerfil = crearBotonMenu("Mi Perfil");
        Button btnSolicitudes = crearBotonMenu("Gestionar Solicitudes");
        Button btnSesiones = crearBotonMenu("Mis Sesiones");
        Button btnResenas = crearBotonMenu("Mis Reseñas");

        // Cada botón cambia el panel central del BorderPane
        btnInicio.setOnAction(e -> layoutPrincipal.setCenter(crearPanelInicio()));
        btnPerfil.setOnAction(e -> layoutPrincipal.setCenter(crearPanelPerfil()));
        btnSolicitudes.setOnAction(e -> layoutPrincipal.setCenter(crearPanelSolicitudes()));
        btnSesiones.setOnAction(e -> layoutPrincipal.setCenter(crearPanelSesiones()));
        btnResenas.setOnAction(e -> layoutPrincipal.setCenter(crearPanelResenas()));
        
        menu.getChildren().addAll(
            lblMenu, 
            new Separator(), 
            btnInicio, 
            btnPerfil, 
            btnSolicitudes, 
            btnSesiones, 
            btnResenas
        );
        
        return menu;
    }
}