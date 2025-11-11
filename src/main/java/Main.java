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

    public void mostrarLogin() {
        LoginVista loginVista = new LoginVista();
        controlador = new ControladorPrincipal(loginVista, this);
        loginVista.setControlador(controlador);
        loginVista.setOnCrearCuenta(() -> mostrarRegistro());
        
        Scene scene = new Scene(loginVista, 480, 620);  // Tamaño ajustado
        stage.setScene(scene);
        stage.setTitle("Gestor de Tutorías UVG - Iniciar Sesión");

        // Forzamos a que la ventana NO esté maximizada
        stage.setMaximized(false);
        
        stage.setResizable(false);  // NO REDIMENSIONABLE
        stage.centerOnScreen();     // CENTRAR EN PANTALLA
    }
    
    private void mostrarRegistro() {
        // Asumiendo que tienes una clase VistaRegistro
        // (El archivo VistaRegistro.java que subiste)
        VistaRegistro registroVista = new VistaRegistro(); 
        registroVista.setControlador(controlador);
        registroVista.setOnCancel(() -> mostrarLogin());
        
        Scene scene = new Scene(registroVista, 550, 680);  // Tamaño ajustado para registro
        stage.setScene(scene);
        stage.setTitle("Gestor de Tutorías UVG - Registro de Usuario");

        stage.setMaximized(false);
        
        stage.setResizable(false);  // NO REDIMENSIONABLE
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}