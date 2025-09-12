import java.lang.classfile.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class VistaPrincipalTutor extends VBox{
    private Tutor tutorActual;
    private Label lblNombre, lblCorreo, lblTarifas;
    private ListView<String> listaMaterias;
    private Button btnEditarPerfil, btnVerSesiones, btnVerResenas;
}
