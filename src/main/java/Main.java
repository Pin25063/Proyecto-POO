import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override 
    public void start(Stage stage) {
        BusquedaVista root = new BusquedaVista();

        Scene scene = new Scene(root, 420, 520);

        URL cssUrl = getClass().getResource("styles.css");
        if (cssUrl != null) scene.getStylesheets().add(cssUrl.toExternalForm());

        stage.setTitle("Buscar Tutores");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}    