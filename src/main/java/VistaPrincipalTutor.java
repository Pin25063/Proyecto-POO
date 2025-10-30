import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;

public class VistaPrincipalTutor {

    //Atributos de la clase
    private ControladorPrincipal controlador;
    private Tutor tutorActual;
    private Stage stage;
    private Main mainApp;

    //Constructor de la clase
    public VistaPrincipalTutor(ControladorPrincipal controlador, Tutor tutor, Stage stage, Main mainApp) {
        this.controlador = controlador;
        this.tutorActual = tutor;
        this.stage = stage;
        this.mainApp = mainApp;
        
    }

    //Metodo de vizualizacion
    // Se construye y se muestra la interfaz del tutor

    public void mostrar() {
        //BorderPane divide la pagina en 5 zonas
        BorderPane layoutPrincipal = new BorderPane();
        layoutPrincipal.setStyle("-fx-background-color: #ecf0f1;");

        // Componentes de la UI
        HBox barraSuperior = crearBarraSup();
        VBox menuLateral = crearMenuLateral(layoutPrincipal);
        VBox contenidoInicial = crearPanelInicio();

        //Se arma el layout principal
        layouPrincipal.setTop(barraSuperior);
        //layouPrincipal.setLeft(menuLateral);
        //layouPrincipal.setCenter(contenidoInicial);

        //Se crea la escena y se configura la ventana
        Scene escena = new Scene(layouPrincipal, 1100, 700);
        stage.setScene(escena);
        stage.setTitle("Panel del Tutor - Gestor de Tutorías UVG");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    // Componentes de la interfaz
    //Se crea la barra superior con titulo, nombre de usuario y boton de cerrar sesion

    private HBox crearBarraSup() {
        HBox barra = new HBox(20);
        barra.setPadding(new Insets(15));
        barra.setStyle("-fx-background-color: #3d8335ff;");
        barra.setAlignment(Pos.CENTER_LEFT);

        // Titulo de la ventana
        Label lblTitulo =  new Label("Gestor de Tutorias UVG");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblTitulo.setStyle("-fx-text-fill: white;");

        // Espaciador para empujar elementos a la derecha
        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        // Información del usuario actual
        Label lblBienvenida = new Label("Tutor: " + tutorActual.getNombre());
        lblBienvenida.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblBienvenida.setStyle("-fx-text-fill: white;");

        // Botón de cerrar sesión
        Button btnCerrarSesion = new Button("Cerrar Sesión");
        btnCerrarSesion.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCerrarSesion.setOnAction(e -> mainApp.mostrarLogin());
        
        barra.getChildren().addAll(lblTitulo, espaciador, lblBienvenida, btnCerrarSesion);
        return barra;
    }

    // Se crea el menu lateral de navegacion con botones para cambiar de panel

    private VBox crearMenuLateral(BorderPane layoutPrincipal) {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(220);
        menu.setStyle("-fx-background-color: #3d8335ff;");

        Label lblMenu = new Label("MENÚ");
        lblMenu.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblMenu.setStyle("-fx-text-fill: white;");
        lblMenu.setAlignment(Pos.CENTER);

        //Se crean los botones de navegacion de la barra lateral
        Button btnInicio = crearBotonMenu("INICIO");
        Button btnPerfil = crearBotonMenu("Mi Perfil");
        Button btnSolicitudes = crearBotonMenu("Gestionar Solicitudes");
        Button btnSesiones = crearBotonMenu("Mis Sesiones");
        Button btnResenas = crearBotonMenu("Mis Reseñas");

        // Cada botón cambia el panel central del BorderPane
        btnInicio.setOnAction(e -> layoutPrincipal.setCenter(crearPanelInicio()));
        btnPerfil.setOnAction(e -> layoutPrincipal.setCenter(crearPanelPerfil()));
        btnSolicitudes.setOnAction(e -> layoutPrincipal.setCenter(crearPanelSolicitudes()));
        btnSesiones.setOnAction(e -> layoutPrincipal.setCenter(crearPanelSesiones()));
        btnResenas.setOnAction(e -> layoutPrincipal.setCenter(crearPanelResenas()));
        
        menu.getChildren().addAll(
            lblMenu, 
            new Separator(), 
            btnInicio, 
            btnPerfil, 
            btnSolicitudes, 
            btnSesiones, 
            btnResenas
        );
        
        return menu;
    }

    // Paneles de contenido

    private VBox crearPanelInicio() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));
        panel.setAlignment(Pos.TOP_CENTER);
        
        Label lblTitulo = new Label("¡Bienvenido, " + tutorActual.getNombre() + "!");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        // Panel de estadisticas rapidas
        GridPane gridEstadisticas = new GridPane();
        gridEstadisticas.setHgap(20);
        gridEstadisticas.setVgap(20);
        gridEstadisticas.setAlignment(Pos.CENTER);

        // Obtener las estadisticas
        double calificacion = tutorActual.calcularPromedioCalificacion();
        int totalResenas = tutorActual.getTotalResenas();
        int numMaterias = tutorActual.getMaterias().size();

        // Crear tarjetas de estadistica
        VBox tarjetaCalificacion = crearTarjetaCalificacion(
            String.format("%.1f/5", calificacion),
            "Calificación",
            "#f39c12"
        );

        VBox tarjetaResenas = crearTarjetaEstadistica(
            String.valueOf(totalResenas),
            "Reseñas",
            "#3498db"
        );
        VBox tarjetaMaterias = crearTarjetaEstadistica(
            String.valueOf(numMaterias),
            "Materias",
            "#27ae60"
        );

        gridEstadisticas.add(tarjetaCalificacion, 0, 0);
        gridEstadisticas.add(tarjetaResenas, 1, 0);
        gridEstadisticas.add(tarjetaMaterias, 2, 0);

        Label lblInfo = new Label(
            "Use el menú de la izquierda para navegar por las diferentes secciones.\n" +
            "Puede gestionar sus solicitudes, ver sesiones y revisar sus reseñas."
        );
        lblInfo.setFont(Font.font("Arial", 14));
        lblInfo.setWrapText(true);
        lblInfo.setStyle("-fx-text-alignment: center;");
        
        panel.getChildren().addAll(lblTitulo, new Separator(), gridEstadisticas, lblInfo);
        return panel;
    }

    // Panel de perfil del tutor con toda su informacion

    private VBox crearPanelPerfil() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));

        Label lblTitulo = new Label("Mi Perfil");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // GridPane para informacion del perfil
        GridPane gridPerfil = new GridPane();
        gridPerfil.setHgap(15);
        gridPerfil.setVgap(15);
        gridPerfil.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20; -fx-background-radius: 5;");

        // Información del perfil
        gridPerfil.add(new Label("Nombre:"), 0, 0);
        gridPerfil.add(new Label(tutorActual.getNombre()), 1, 0);
        
        gridPerfil.add(new Label("Correo:"), 0, 1);
        gridPerfil.add(new Label(tutorActual.getCorreo()), 1, 1);
        
        gridPerfil.add(new Label("ID:"), 0, 2);
        gridPerfil.add(new Label(String.valueOf(tutorActual.getIdUsuario())), 1, 2);
        
        gridPerfil.add(new Label("Tarifa:"), 0, 3);
        gridPerfil.add(new Label("Q" + tutorActual.getTarifa() + " por hora"), 1, 3);

        // Sección de materias
        Label lblMaterias = new Label("📚 Materias que imparte:");
        lblMaterias.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        ListView<String> listaMaterias = new ListView<>();
        listaMaterias.getItems().addAll(tutorActual.getMaterias());
        listaMaterias.setPrefHeight(150);

        // Botón para editar perfil
        Button btnEditar = new Button("Editar Perfil");
        btnEditar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        btnEditar.setOnAction(e -> abrirDialogoEditarPerfil());
        
        panel.getChildren().addAll(lblTitulo, gridPerfil, lblMaterias, listaMaterias, btnEditar);
        return panel;
    }

    // Panel para gestionar las solicitudes de tutoria

    private VBox crearPanelSolicitudes() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));

        Label lblTitulo = new Label("Gestionar Solicitudes");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // area para mostrar informacion
        TextArea txtInfo = new TextArea();
        txtInfo.setEditable(false);
        txtInfo.setPrefHeight(400);
        txtInfo.setFont(Font.font("Monospaced", 12));

        String info ="   GESTIÓN DE SOLICITUDES\n" +
                     "Esta funcionalidad estará disponible próximamente.\n\n" +
                     "Podrás:\n" +
                     "• Ver solicitudes pendientes de estudiantes\n" +
                     "• Aceptar o rechazar solicitudes\n" +
                     "• Programar horarios de sesiones\n" +
                     "• Notificar a los estudiantes\n\n" +
                     "Las solicitudes aparecerán automáticamente\n" +
                     "cuando los estudiantes las generen en el sistema.";
        txtInfo.setText(info);
        
        panel.getChildren().addAll(lblTitulo, txtInfo);
        return panel;
    }

    // Panel para sesiones programadas
    private VBox crearPanelSesiones() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));

        Label lblTitulo = new Label("Mis Sesiones");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        TextArea txtSesiones = new TextArea();
        txtSesiones.setEditable(false);
        txtSesiones.setPrefHeight(400);
        txtSesiones.setFont(Font.font("Monospaced", 12));
        
        String info ="   MIS SESIONES\n" +
                     "Aquí verás todas tus sesiones de tutoría.\n\n" +
                     "Funcionalidad en desarrollo:\n" +
                     "• Sesiones programadas\n" +
                     "• Sesiones completadas\n" +
                     "• Historial de tutorías\n" +
                     "• Detalles de cada sesión\n" +
                     "• Calificaciones de estudiantes";
        
        txtSesiones.setText(info);
        
        panel.getChildren().addAll(lblTitulo, txtSesiones);
        return panel;
    }

    // Panel para ver reseñas recibidas
    private VBox crearPanelResenas() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));

        Label lblTitulo = new Label("Mis Reseñas y Calificaciones");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Resumen de calificaciones
        Label lblResumen = new Label(String.format(
            "Calificación Promedio: %.1f/5.0  |  Total de Reseñas: %d",
            tutorActual.calcularPromedioCalificacion(),
            tutorActual.getTotalResenas()
        ));
        lblResumen.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblResumen.setStyle("-fx-text-fill: #34495e;");

        // TextArea para mostrar reseñas
        TextArea txtResenas = new TextArea();
        txtResenas.setEditable(false);
        txtResenas.setPrefHeight(400);
        txtResenas.setFont(Font.font("Monospaced", 12));
        
        List<Resena> resenas = tutorActual.getResenas();
        
        if (resenas.isEmpty()) {
            txtResenas.setText("═══════════════════════════════════════\n" +
                             "   SIN RESEÑAS AÚN\n" +
                             "═══════════════════════════════════════\n\n" +
                             "Aún no tienes reseñas de estudiantes.\n\n" +
                             "Las reseñas aparecerán aquí cuando los\n" +
                             "estudiantes califiquen tus tutorías.");
        } else {
            StringBuilder contenido = new StringBuilder();
            contenido.append("═══════════════════════════════════════\n");
            contenido.append("   RESEÑAS RECIBIDAS\n");
            contenido.append("═══════════════════════════════════════\n\n");
            
            int count = 1;
            for (Resena resena : resenas) {
                contenido.append("Reseña #").append(count++).append("\n");
                contenido.append("─────────────────────────────────\n");
                contenido.append(resena.toString()).append("\n\n");
            }
            
            txtResenas.setText(contenido.toString());
        }
        
        panel.getChildren().addAll(lblTitulo, lblResumen, txtResenas);
        return panel;
    }

    // Metodos Auxiliares y dialogos
    //Se crea una tarjeta de estadistica

    private VBox crearTarjetaEstadistica(String valor, String titulo, String color) {
        VBox tarjeta = new VBox(10);
        tarjeta.setPrefSize(180, 120);
        tarjeta.setAlignment(Pos.CENTER);
        tarjeta.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 10; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);"
        );
        tarjeta.setPadding(new Insets(20));

        Label lblValor = new Label(valor);
        lblValor.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        lblValor.setStyle("-fx-text-fill: " + color + ";");

        Label lblTitulo = new Label(titulo);
        lblTitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        lblTitulo.setStyle("-fx-text-fill: #34495e;");

        tarjeta.getChildren().addAll(lblValor, lblTitulo);
        return tarjeta;
    }

    // Se crea boton estilizado
    private Button crearBotonMenu(String texto) {
        Button boton = new Button(texto);
        boton.setPrefWidth(200);
        boton.setPrefHeight(40);
        boton.setAlignment(Pos.CENTER_LEFT);
        boton.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold;"
        );

        // Efectos hover
        boton.setOnMouseEntered(e -> 
            boton.setStyle(
                "-fx-background-color: #3498db; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold;"
            )
        );
        boton.setOnMouseExited(e -> 
            boton.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold;"
            )
        );
        
        return boton;
    }

    // Abre dialogo para editar perfil de tutor
    private void abrirDialogoEditarPerfil() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Editar Perfil");

        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));
        contenido.setAlignment(Pos.CENTER);

        Label titulo = new Label("Editar Información del Tutor");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Campo para tarifa
        Label lblTarifa = new Label("Tarifa por Hora (Q): ");
        TextField txtTarifa = new TextField(String.valueOf(tutorActual.getTarifa()));
        txtTarifa.setPrefWidth(300);

        // Campo para contraseña
        Label lblPass = new Label("Nueva Contraseña (opcional):");
        PasswordField txtPass = new PasswordField();
        txtPass.setPrefWidth(300);
        txtPass.setPromptText("Dejar vacío para mantener la actual");

        // Botones
        HBox botones = new HBox(10);
        botones.setAlignment(Pos.CENTER);

        Button btnGuardar = new Button("Guardar Cambios");
        Button btnCancelar = new Button("Cancelar");

        btnGuardar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCancelar.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold;");

        btnGuardar.setOnAction(e -> {
            try {
                double nuevaTarifa = Double.parseDouble(txtTarifa.getText().trim());
                if (nuevaTarifa < 0) {
                    mostrarAlerta("Error", "La tarifa no puede ser negativa", Alert.AlertType.ERROR);
                    return;
                }

                tutorActual.setTarifa(nuevaTarifa);
                mostrarAlerta("Exíto", "Perfil actualizado correctamente", Alert.AlertType.INFORMATION);
                dialogStage.close();

            } catch (NumberFormatException ex) {
                mostrarAlerta("Error", "La tarifa debe ser un número válido", Alert.AlertType.ERROR);
            }
        });

        btnCancelar.setOnAction(e -> dialogStage.close());

        botones.getChildren().addAll(btnGuardar, btnCancelar);

        contenido.getChildren().addAll(
            titulo,
            new Separator(),
            lblTarifa, txtTarifa,
            lblPass, txtPass,
            new Separator(),
            botones
        );

        Scene scene = new Scene(contenido, 400, 350);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    // Muestra una alerta genérica
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();

    }
}