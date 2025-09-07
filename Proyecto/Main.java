import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override 
    public void start(Stage stage) {
        LoginVista root = new LoginVista();
        Scene scene = new Scene(root, 420, 520);
        stage.setTitle("Gestor de Tutorías - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}    