import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.List;

import javax.swing.text.View;

public class VistaPrincipalTutor extends VBox{
    private Tutor tutorActual;
    private ControladorPrincipal controlador;

    //Labels que muestran informacion
    private Label lblNombre, lblCorreo, lblTarifa, lblMaterias;

    //Lista que muestra las materias del tutor
    private ListView<String> listaMaterias;

    //Botones para distintas acciones
    private Button btnEditarPerfil, btnVerSesiones, btnVerResenas, btnGestionarSolicitudes;

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
    
    //Se crea la seccion que muestra un resumen de las solicitudes recientes
    private VBox crearSeccionSolicitudes() {
        //Contenedor para las solicitudes
        VBox seccion = new VBox(10);
        seccion.setPadding(new Insets(10));
        seccion.setStyle("-fx-background-color: #fff3cd; -fx-background-radius: 5;");
        seccion.setMaxWidth(600);

        Label tituloSolicitudes = new Label("Solicitudes Recientes");
        tituloSolicitudes.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        //Lista para mostrar las solicitudes
        ListView<String> listaSolicitudes = new ListView<>();
        listaSolicitudes.setPrefHeight(150);

        //Obtener solicitudes pendientes
        List<Sesion> pendientes = controlador.obtenerSesionesPendientes(tutorActual);

        //Verificar si no hay solicitudes pendientes
        if (pendientes.isEmpty()) {
            listaSolicitudes.getItems().add("No tienes solicitudes pendientes");
        } else {
            //Mostrar ultimas 5 solicitudes
            int limite = Math.min(5, pendientes.size());
            for (int i = 0; i < limite; i++) {
                Sesion s = pendientes.get(i);
                //Formatear la informacion de cada solicitud
                String fechaStr = s.getFechaHora() != null ? s.getFechaHora().toString() : "Por coordinar";
                String texto = String.format("%s - Estudiante: %s (%s)",
                    s.getMateria(),
                    s.getEstudianteId(),
                    s.getEstado());
                listaSolicitudes.getItems().add(texto);
            }
        }

        //Agregar todos los elementos a la seccion
        seccion.getChildren().addAll(tituloSolicitudes, listaSolicitudes);

        return seccion;
    }

    //Se crea la barra de botones de accion
    private HBox crearSeccionBotones() {
        //Contenedor vertical para los botones 
        HBox contenedor = new HBox(12);
        contenedor.setAlignment(Pos.CENTER);
        contenedor.setPadding(new Insets(10, 0, 10,0));

        //Se crean los botones dedicados a las acciones
        btnEditarPerfil = new Button("Editar Perfil");
        btnVerSesiones = new Button("Ver Sesiones");
        btnVerResenas = new Button("Ver Reseñas");
        btnGestionarSolicitudes = new Button("Gestionar Solicitudes");

        //Estilos para los botones
        String estiloBoton = "-fx-font-size: 13px; -fx-padding: 8 15 8 15;";
        btnEditarPerfil.setStyle(estiloBoton);
        btnVerSesiones.setStyle(estiloBoton);
        btnVerResenas.setStyle(estiloBoton);
        btnGestionarSolicitudes.setStyle(estiloBoton);

        //Se conectan los eventos de los botones con sus metodos correspondientes
        btnEditarPerfil.setOnAction(e -> abrirEdicionPerfil());
        btnVerSesiones.setOnAction(e -> mostrarSesiones());
        btnVerResenas.setOnAction(e -> mostrarResenas());
        btnGestionarSolicitudes.setOnAction(e -> abrirGestionSolicitudes());

        //Se agregan los botones al contenedor
        contenedor.getChildren().addAll(btnEditarPerfil, btnVerSesiones, btnVerResenas, btnGestionarSolicitudes);

        return contenedor;
    }

    //Se crea la seccion para que el tutor pueda cambiar su informacion de perfil
    private void abrirEdicionPerfil() {
        //Se crea una ventana Stage
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Editar Perfil");

        //Contenedor principal
        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));
        contenido.setAlignment(Pos.CENTER);

        Label titulo = new Label("Editar Información del Tutor");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        //Editar la tarifa
        Label lblNuevaTarifa = new Label("Tarifa por Hora (Q): ");
        TextField txtTarifa = new TextField(String.valueOf(tutorActual.getTarifa()));
        txtTarifa.setPrefWidth(300);

        //Nueva contraseña (opcional)
        Label lblNuevaPass = new Label("Nueva Contraseña (Opcional): ");
        PasswordField txtPass = new PasswordField();
        txtPass.setPrefWidth(300);
        txtPass.setPromptText("Dejar vacio para mantener la contraseña actual");

        //Contenedor para los botones
        HBox botones = new HBox(10);
        botones.setAlignment(Pos.CENTER);

        //Boton para guardar cambios
        Button btnGuardar = new Button("Guardar Cambios");
        Button btnCancelar = new Button("Cancelar");

        btnGuardar.setStyle("-fx-font-size: 13px; -fx-padding: 8 15 8 15;");
        btnCancelar.setStyle("-fx-font-size: 13px; -fx-padding: 8 15 8 15;");

        //Evento boton Guardar
        btnGuardar.setOnAction(e -> {
            //Se obtienen los valores de los campos a cambiar
            String nuevaTarifaStr = txtTarifa.getText().trim();
            String nuevaPass = txtPass.getText().trim();

            try {
                //Se convierte la tarifa a numero
                double nuevaTarifa = Double.parseDouble(nuevaTarifaStr);

                //Comprobar que la tarifa sea valida
                if (nuevaTarifa < 0) {
                    mostrarError("La tarifa no puede ser negativa");
                    return;
                }
                
                //Se actualiza el lbl de la tarifa
                if (nuevaTarifa != tutorActual.getTarifa()) {
                    lblTarifa.setText("Tarifa: Q" + nuevaTarifa + " por hora");
                }

                if (!nuevaPass.isEmpty()) {
                    mostrarInfo("Edicion", "Contraseña actualizada correctamente");
                }

                //Mostrar mensaje
                mostrarInfo("Exito", "Perfil actualizado correctamente");
                dialogStage.close();

            } catch (NumberFormatException ex) {
                //Error si la tarifa no es un numero valido
                mostrarError("Error","La tarifa debe ser un numero valido");
            }
        });

        //Evento del boton Cancelar
        btnCancelar.setOnAction(e -> dialogStage.close());

        //Agregar botones al contenedor
        botones.getChildren().addAll(btnGuardar, btnCancelar);

        //Agregar todos los elementos al contenido del dialogo
        contenido.getChildren().addAll(
            titulo,
            new Separator(), //Linea que separa
            lblNuevaTarifa, txtTarifa,
            lblNuevaPass, txtPass,
            new Separator(),
            botones
        );

        Scene scene = new Scene(contenido, 400, 450);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    
}