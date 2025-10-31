import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    private Stage stage; // Se guarda referencia al escenario principal
    private ControladorPrincipal controlador;
    private Stage primaryStage;

        @Override 
    public void start(Stage stage) {
        this.primaryStage = stage;
        
        LoginVista root = new LoginVista();
        ControladorPrincipal ctrl = new ControladorPrincipal(root, this);
        root.setControlador(ctrl);
        root.setOnCrearCuenta(() -> mostrarRegistro());

        Scene scene = new Scene(root, 480, 620);  // Tamaño fijo

        URL cssUrl = getClass().getResource("styles.css");
        if (cssUrl != null) scene.getStylesheets().add(cssUrl.toExternalForm());

        stage.setTitle("Gestor de Tutorías UVG - Iniciar Sesión");
        stage.setScene(scene);
        stage.setResizable(false);  // ← NO REDIMENSIONABLE
        stage.centerOnScreen();     // ← CENTRAR EN PANTALLA
        stage.show();
    }

    public void mostrarLogin() {
        LoginVista root = new LoginVista();
        ControladorPrincipal ctrl = new ControladorPrincipal(root, this);
        root.setControlador(ctrl);
        root.setOnCrearCuenta(() -> mostrarRegistro());
        
        Scene scene = new Scene(root, 480, 620);
        
        URL cssUrl = getClass().getResource("styles.css");
        if (cssUrl != null) scene.getStylesheets().add(cssUrl.toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestor de Tutorías UVG - Iniciar Sesión");
        primaryStage.setResizable(false);  // ← NO REDIMENSIONABLE
        primaryStage.centerOnScreen();
    }

    // MÉTODO PARA MOSTRAR REGISTRO
    public void mostrarRegistro() {
        VistaRegistro root = new VistaRegistro();
        ControladorPrincipal ctrl = new ControladorPrincipal(new LoginVista(), this);
        root.setControlador(ctrl);
        root.setOnCancel(() -> mostrarLogin());
        
        Scene scene = new Scene(root, 550, 680);  // Tamaño ajustado
        
        URL cssUrl = getClass().getResource("styles.css");
        if (cssUrl != null) scene.getStylesheets().add(cssUrl.toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestor de Tutorías UVG - Registro de Usuario");
        primaryStage.setResizable(false);  // No redimensionable
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}    