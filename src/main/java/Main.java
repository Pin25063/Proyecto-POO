import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class Main extends Application {
    @Override 
    public void start(Stage stage) {
        LoginVista root = new LoginVista();

        ControladorPrincipal ctrl = new ControladorPrincipal(root);
        root.setControlador(ctrl);

        Scene scene = new Scene(root, 420, 520);

        URL cssUrl = getClass().getResource("styles.css");
        if (cssUrl != null) scene.getStylesheets().add(cssUrl.toExternalForm());

        stage.setTitle("Gestor de Tutorías - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}    