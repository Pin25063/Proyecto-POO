import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class BusquedaVista extends VBox{
    private final TextField campoMateria = new TextField();
    private final TableView<Tutor> tablaResultados = new TableView<>();
    private final Button btnBuscar = new Button("Buscar");

    private final ArrayList<Tutor> tutores = new ArrayList<>();

    public BusquedaVista() {
        setPadding(new Insets(24));
        setSpacing(16);
        setAlignment(Pos.CENTER);
        setFillWidth(true);

        Label titulo = new Label("Buscar Tutores por Materia");
        titulo.getStyleClass().add("titulo");

        campoMateria.setPromptText("Escribe una materia...");
        campoMateria.setPrefWidth(300);
        btnBuscar.setPrefWidth(100);
        HBox filaBusqueda = new HBox(10, campoMateria, btnBuscar);
        filaBusqueda.setAlignment(Pos.CENTER);
    }
    
}
