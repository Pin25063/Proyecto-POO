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
        VBox seccionEstadisticas = crearSeccionEstadisticas();
        HBox seccionBotones = crearSeccionBotones();
        VBox seccionSolicitudes = crearSeccionSolicitudes();

        getChildren().addAll(titulo, seccionPerfil, seccionSolicitudes, seccionEstadisticas, seccionBotones);
    }

    private VBox crearSeccionPerfil() {
        //Contenedor de la seccion perfil
        VBox seccion = new VBox(10);
        seccion.setPadding(new Insets(15));
        seccion.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5;");
        seccion.setMaxWidth(600);

        Label tituloPerfil = new Label("Información del Perfil");
        tituloPerfil.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        //Labels con la info del tutor
        lblNombre = new Label("Nombre: " + tutorActual.getNombre());
        lblCorreo = new Label("Correo: " + tutorActual.getCorreo());
        lblTarifa = new Label("Tarifa: Q" + tutorActual.getTarifa() + " por hora");

        lblNombre.setStyle("-fx-font-size: 14px;");
        lblCorreo.setStyle("-fx-font-size: 14px;");
        lblTarifa.setStyle("-fx-font-size: 14px;");

        //label para las materias
        Label lblMaterias = new Label("Materias que imparte:");
        lblMaterias.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        //ListView para poder mostrar las lista de las materias
        listaMaterias = new ListView<>();
        listaMaterias.getItems().addAll(tutorActual.getMaterias());
        listaMaterias.setPrefHeight(100);

        //Se utiliza para agregar todos los elementos a la seccion
        seccion.getChildren().addAll(tituloPerfil, lblNombre, lblCorreo, lblTarifa, lblMaterias, listaMaterias);

        return seccion;
    }

    private VBox crearSeccionEstadisticas(){
        //Se crea contenedor para las estadisticas
        VBox seccion = new VBox(10);
        seccion.setPadding(new Insets(15));
        seccion.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 5;");
        seccion.setMaxWidth(600);
        
        Label tituloEstadisticas = new Label("Estadisticas");
        tituloEstadisticas.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        //Obtener datos estadisticos del tutor
        double calificacion = tutorActual.calcularPromedioCalificacion();
        int totalResenas = tutorActual.getTotalResenas();
        List<Sesion> pendientes = controlador.obtenerSesionesPendientes(tutorActual);
        int solicitudesPendientes = pendientes.size();

        //Se crean labels con las estadisticas
        Label lblCalificacion = new Label(String.format("Calificación Promedio: %.1f/5.0", calificacion));
        Label lblResenas = new Label("Total de Reseñas: " + totalResenas);
        Label lblSolicitudes = new Label("Solicitudes Pendientes: " + solicitudesPendientes);

        lblCalificacion.setStyle("-fx-font-size: 14px;");
        lblResenas.setStyle("-fx-font-size: 14px;");
        lblSolicitudes.setStyle("-fx-font-size: 14px;");

        //Se agregan todos los elementos a la seccion
        seccion.getChildren().addAll(tituloEstadisticas, lblCalificacion, lblResenas, lblSolicitudes);

        return seccion;
    }
    
}