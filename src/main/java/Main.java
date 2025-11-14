import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;

public class Main extends Application {
    
    private Stage stage; // Se guarda referencia al escenario principal
    private ControladorPrincipal controlador;

    
    @Override 
    public void start(Stage stage) {
        this.stage = stage;
        mostrarLogin(); // Iniciar con la ventana de login
        stage.setTitle("Sistema de Tutorías UVG");
        stage.show();
    }

    public void mostrarLogin() {
        LoginVista loginVista = new LoginVista();
        controlador = new ControladorPrincipal(loginVista, this);
        loginVista.setControlador(controlador);
        loginVista.setOnCrearCuenta(() -> mostrarRegistro());

        stage.setMaximized(false); // Quitar maximizado
        stage.setResizable(false); // Quitar redimensionable
        stage.setMinWidth(480);
        stage.setMaxWidth(480);
        stage.setMinHeight(620);
        stage.setMaxHeight(620);
        
        Scene scene = new Scene(loginVista, 480, 620);
        stage.setScene(scene);
        stage.setTitle("Gestor de Tutorías UVG - Iniciar Sesión");

        // Usar Thread.sleep en un hilo separado para evitar bloquear la UI
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Platform.runLater(() -> {
                stage.setWidth(480);
                stage.setHeight(620);
                stage.centerOnScreen();
            });
        }).start();
    }
    
    private void mostrarRegistro() {
        VistaRegistro registroVista = new VistaRegistro(); 
        registroVista.setControlador(controlador);
        registroVista.setOnCancel(() -> mostrarLogin());
        
        stage.setMaximized(false); // Quitar maximizado
        stage.setResizable(false); // Quitar redimensionable
        stage.setMinWidth(550);
        stage.setMaxWidth(550);
        stage.setMinHeight(680);
        stage.setMaxHeight(680);

        Scene scene = new Scene(registroVista, 550, 680);
        stage.setScene(scene);
        stage.setTitle("Gestor de Tutorías UVG - Registro de Usuario");

        // Usar Thread.sleep en un hilo separado para evitar bloquear la UI
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Platform.runLater(() -> {
                stage.setWidth(550);
                stage.setHeight(680);
                stage.centerOnScreen();
            });
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}