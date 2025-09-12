import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Controlador para la vista del Catedrático y roles de administrador
public class ControladorAdministradores {
    // Se hace la inyección de componentes FXML

    // @FXML enlaza las variables con los componentes del archivo FXML
    @FXML
    private ComboBox<Estudiante> estudianteComboBox;
    
    @FXML
    private ComboBox<Tutor> tutorComboBox;

    @FXML 
    private ComboBox<Curso> cursoComboBox;

    @FXML
    private Button asignarButton;

    @FXML
    private Label statusLabel;

    // ATRIBUTOS
    private GestorDeDatos gestorDatos;
    private List<Usuario> listaDeUsuarios;
    private List<Sesion> listaDeSesiones;
    private ArrayList<Curso> listaDeCursos;

    // CONSTRUCTOR 
    public ControladorAdministradores(){
        this.gestorDatos = new GestorDeDatos();
    }

    // Método que se manda a llamar automáticamente por JavaFX
    @FXML
    public void initialize(){
        
        // cargar datos del modelo
        try {
            this.listaDeUsuarios = gestorDatos.cargarUsuarios();
            this.listaDeSesiones = gestorDatos.cargarSesiones();
            
        } catch (Exception e) {
            // Alertar al usuario si existe un error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de carga");
            alert.setHeaderText("No se pudieron cargar los datos");
            alert.setContentText("Ocurrió un error al cargar la información: " + e.getMessage());
            alert.showAndWait();
        }

        // Filtrar estudiantes y tutores
        ObservableList<Estudiante> estudiantes = FXCollections.observableArrayList(
            listaDeUsuarios.stream()
                .filter(u -> u instanceof Estudiante)
                .map(u -> (Estudiante) u)
                .collect(Collectors.toList())
        );

        ObservableList<Tutor> tutores = FXCollections.observableArrayList(
            listaDeUsuarios.stream()
                .filter(u -> u instanceof Tutor)
                .map(u -> (Tutor) u)
                .collect(Collectors.toList())
        );

        // Mostrar datos filtrados en los ComboBox
        estudianteComboBox.setItems(estudiantes);
        tutorComboBox.setItems(tutores);
        
        
    }
}
