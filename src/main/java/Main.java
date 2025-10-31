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

    private void mostrarRegistro() {
        VistaRegistro registroVista = new VistaRegistro();
        registroVista.setControlador(controlador);

        //Si el usuario presiona Cancelar, volvera a login
        registroVista.setOnCancel(() -> mostrarLogin());

        Scene scene = new Scene(registroVista, 460, 520);
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}    