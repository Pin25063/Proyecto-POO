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
    private ControladorPrincipal controlador;

    //Labels que muestran informacion
    private Label lblNombre, lblCorreo, lblTarifa, lblMaterias;

    //Lista que muestra las materias del tutor
    private ListView<String> listaMaterias;

    //Botones para distintas acciones
    private Button btnEditarPerfil, btnVerSesiones, btnVerResenas;

    //Constructor
    public VistaPrincipalTutor(ControladorPrincipal controlador, Tutor tutor){
        this.controlador = controlador;
        this.tutorActual = tutor;

        inicializarVista();
    }

    private void inicializarVista() {
        Label titulo = new Label("Bienvenido, " +tutorActual.getNombre());
        titulo.getStyleClass().add("titulo");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        //Panel de estadisticas
        VBox panelEstadisticas = crearPanelEstadisticas();

        //Informacion basica
        

    }
    
}