import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VistaPrincipalTutor extends VBox{
    private Tutor tutorActual;
    private Label lblNombre, lblCorreo, lblTarifa, lblMaterias;
    private ListView<String> listaMaterias;
    private Button btnEditarPerfil, btnVerSesiones, btnVerResenas;

    public VistaPrincipalTutor(Tutor tutor){
        this.tutorActual = tutor;

        Label titulo = new Label("Bienvenido, " + tutor.getNombre());
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        lblNombre = new Label("Nombre: " + tutor.getNombre());
        lblCorreo = new Label("Correo: " + tutor.getCorreo());
        lblTarifa = new Label("Tarifa: Q" + tutor.getTarifa() + " por hora");
        
        // dentro del constructor:
        Label lblMaterias = new Label("Materias que imparte:");
        listaMaterias = new ListView<>();
        listaMaterias.getItems().addAll(tutor.getMaterias());
        listaMaterias.setPrefHeight(100);

        btnEditarPerfil = new Button("Editar Perfil");
        btnVerSesiones = new Button("Ver Sesiones");
        btnVerResenas = new Button("Ver Reseñas");

        HBox botones = new HBox(10, btnEditarPerfil, btnVerSesiones, btnVerResenas);
        botones.setAlignment(Pos.CENTER);

        setSpacing(10);
        setPadding(new Insets(20));
        getChildren().addAll(titulo, lblNombre, lblCorreo, lblTarifa, lblMaterias, listaMaterias, botones);

        btnEditarPerfil.setOnAction(e -> editarPerfil());
        btnVerResenas.setOnAction(e -> verResenas());
        btnVerSesiones.setOnAction(e -> verSesiones());
    }
    
    private void editarPerfil() {
        mostrarInfo("Editar Perfil", "Esta Funcion esta actualmente en desarrollo.");
    }

    private void verResenas() {
        mostrarInfo("Ver Reseñas","Aqui se muestran las reseñas que ha recibido.");
    }

    private void verSesiones() {
        mostrarInfo("Sesiones","Aqui se muestran sus sesiones activas o pasadas");
    }

    private void mostrarInfo(String header, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(header);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
