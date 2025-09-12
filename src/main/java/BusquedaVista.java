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

        TableColumn<Tutor, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Tutor, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        TableColumn<Tutor, String> colMateria = new TableColumn<>("Primera Materia");
        colMateria.setCellValueFactory(data -> {
            ArrayList<String> materias = data.getValue().getMaterias();
            String materia = (materias != null && !materias.isEmpty()) ? materias.get(0) : "—";
            return new javafx.beans.property.SimpleStringProperty(materia);

        
        });

        tablaResultados.getColumns().addAll(colNombre, colCorreo, colMateria);
        tablaResultados.setPrefHeight(220);
        tablaResultados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        btnBuscar.setOnAction(e -> buscarTutores());

        getChildren().addAll(titulo, filaBusqueda, tablaResultados);
    }

        private void buscarTutores() {
        String filtro = campoMateria.getText().trim().toLowerCase();
        ObservableList<Tutor> resultados = FXCollections.observableArrayList();

        for (Tutor t : tutores) {
            for (String materia : t.getMaterias()) {
                if (materia.toLowerCase().contains(filtro)) {
                    resultados.add(t);
                    break;
                }
            }
        }

        if (resultados.isEmpty()) {
            mostrarInfo("Sin resultados", "No se encontraron tutores para esa materia.");
        }

        tablaResultados.setItems(resultados);
    }

    private void mostrarInfo(String header, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(header);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
