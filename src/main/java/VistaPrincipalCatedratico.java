import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VistaPrincipalCatedratico extends VBox{
    
    private Catedratico catedraticoActual;
    private Label lblNombre, lblCorreo;

    public VistaPrincipalCatedratico(Catedratico catedratico) {
        this.catedraticoActual = catedratico;

        Label titulo = new Label("Perfil de catedratico");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        lblNombre = new Label("Nombre: " + catedratico.getNombre());
        lblCorreo = new Label("Correo: " + catedratico.getCorreo());

        setSpacing(10);
        setPadding(new Insets(20));

        getChildren().addAll(titulo, lblNombre, lblCorreo);

        Label lblCursos = new Label("Cursos a cargo:");
        ListView<String> listaCursos = new ListView<>();

        if (catedratico.getMaterias() != null) {
            for (String curso : catedratico.getMaterias()) {
                listaCursos.getItems().add(curso);
            }
        }

        listaCursos.setPrefHeight(120);

        getChildren().addAll(lblCursos, listaCursos);

        Button btnAsignarTutoria = new Button("Asignar tutoria");
        Button btnVerSolicitudes = new Button("Ver solicitudes");

        HBox contenedorBotones = new HBox(10, btnAsignarTutoria, btnVerSolicitudes);

        contenedorBotones.setAlignment(Pos.CENTER);

        getChildren().addAll(contenedorBotones);

        btnAsignarTutoria.setOnAction(e -> mostrarInfo("Asignar Tutoria","Aqui se podran asignar tutorias a los estudiantes que lo requieran."));
        btnVerSolicitudes.setOnAction(e -> mostrarInfo("Ver Solicitudes","Aqui se visualizaran las solicitudes de los estudiantes."));
    }

    private void mostrarInfo(String titulo, String mensaje) {
    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    alerta.setHeaderText(titulo);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
}

}
