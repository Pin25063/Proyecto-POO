import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.ArrayList;
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
    private ArrayList<Usuario> listaDeUsuarios;
    private ArrayList<Sesion> listaDeSesiones;
    private ArrayList<Curso> listaDeCursos;
}
