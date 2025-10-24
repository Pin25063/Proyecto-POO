import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.List;

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

        configurarVista();
    }

    private void configurarVista() {
        setPadding(new Insets(24));
        setSpacing(16);
        setAlignment(Pos.TOP_CENTER);
        setFillWidth(true);

        Label titulo = new Label("Bienvenido, " +tutorActual.getNombre());
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        //Secciones que existiten para tutor
        VBox seccionPerfil = crearSeccionPerfil();
        //VBox seccionEstadisticas = crearSeccionEstadisticas();
        //HBox seccionBotones = crearSeccionBotones();
        //VBox seccionSolicitudes = crearSeccionSolicitudes();

        getChildren().addAll(titulo, seccionPerfil /*seccionEstadisticas, seccionBotones, seccionSolicitudes*/);
    }

    private VBox crearSeccionPerfil() {
        VBox seccion = new VBox(10);
        seccion.setPadding(new Insets(15));
        seccion.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5;");
        seccion.setMaxWidth(600);

        Label tituloPerfil = new Label("Información del Perfil");
        tituloPerfil.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        lblNombre = new Label("Nombre: " + tutorActual.getNombre());
        lblCorreo = new Label("Correo: " + tutorActual.getCorreo());
        lblTarifa = new Label("Tarifa: Q" + tutorActual.getTarifa() + " por hora");

        lblNombre.setStyle("-fx-font-size: 14px;");
        lblCorreo.setStyle("-fx-font-size: 14px;");
        lblTarifa.setStyle("-fx-font-size: 14px;");

        Label lblMaterias = new Label("Materias que imparte:");
        lblMaterias.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        listaMaterias = new ListView<>();
        listaMaterias.getItems().addAll(tutorActual.getMaterias());
        listaMaterias.setPrefHeight(100);

        seccion.getChildren().addAll(tituloPerfil, lblNombre, lblCorreo, lblTarifa, lblMaterias, listaMaterias);

        return seccion;
    }
    
}