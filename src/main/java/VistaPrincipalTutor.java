import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
    }
}
