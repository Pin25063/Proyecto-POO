import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    private void mostrarLogin() {
        LoginVista loginVista = new LoginVista();
        controlador = new ControladorPrincipal(loginVista);
        loginVista.setControlador(controlador);

        // Al hacer clic en Crear cuenta, se ira a VistaRegistro
        loginVista.setOnCrearCuenta(() -> mostrarRegistro());

        Scene scene = new Scene(loginVista, 420, 520);
        stage.setScene(scene);
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