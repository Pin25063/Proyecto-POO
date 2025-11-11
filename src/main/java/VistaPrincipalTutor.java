import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.List;

public class VistaPrincipalTutor {
    private Tutor tutorActual;
    private ControladorPrincipal controlador;
    private Stage stage;
    private Main mainApp;
    private BorderPane layoutPrincipal;
    
    // Colores de la aplicación
    private final String COLOR_PRIMARIO = "#2c3e50"; // Azul marino
    private final String COLOR_SECUNDARIO = "#27ae60"; // Verde
    private final String COLOR_FONDO = "#ecf0f1"; // Gris claro
    private final String COLOR_RECHAZO = "#e74c3c"; // Rojo

    public VistaPrincipalTutor(ControladorPrincipal controlador, Tutor tutor, Stage stage, Main mainApp) {
        this.controlador = controlador;
        this.tutorActual = tutor;
        this.stage = stage;
        this.mainApp = mainApp;
    }
    
    public void mostrar() {
        layoutPrincipal = new BorderPane();
        layoutPrincipal.setStyle("-fx-background-color: " + COLOR_FONDO + ";");

        HBox barraSuperior = crearBarraSuperior();
        VBox menuLateral = crearMenuLateral(layoutPrincipal);
        VBox contenidoInicial = crearPanelInicio();

        layoutPrincipal.setTop(barraSuperior);
        layoutPrincipal.setLeft(menuLateral);
        layoutPrincipal.setCenter(contenidoInicial);

        Scene escena = new Scene(layoutPrincipal, 1100, 700);
        stage.setScene(escena);
        stage.setTitle("Panel del Tutor - Gestor de Tutorías UVG");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        // Iniciar maximizado para simular pantalla completa pero permitiendo redimensionar
        stage.setMaximized(true);
        stage.setResizable(true);
        // asegurar que la ventana quede en primer plano
        stage.toFront();
        stage.requestFocus();
        stage.show();
    }

    private HBox crearBarraSuperior() {
        HBox barra = new HBox(20);
        barra.setPadding(new Insets(15));
        barra.setStyle("-fx-background-color: " + COLOR_PRIMARIO + ";");
        barra.setAlignment(Pos.CENTER_LEFT);

        Label lblTitulo = new Label("Gestor de Tutorías UVG");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblTitulo.setStyle("-fx-text-fill: white;");

        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        Label lblUsuario = new Label("Tutor: " + tutorActual.getNombre());
        lblUsuario.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblUsuario.setStyle("-fx-text-fill: white;");

        Button btnCerrarSesion = new Button("Cerrar Sesión");
        btnCerrarSesion.setStyle("-fx-background-color: " + COLOR_SECUNDARIO + "; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCerrarSesion.setOnAction(e -> mainApp.mostrarLogin());

        barra.getChildren().addAll(lblTitulo, espaciador, lblUsuario, btnCerrarSesion);
        return barra;
    }

    private VBox crearMenuLateral(BorderPane layout) {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(220);
        menu.setStyle("-fx-background-color: " + COLOR_PRIMARIO + ";");

        Label lblMenu = new Label("MENÚ");
        lblMenu.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblMenu.setStyle("-fx-text-fill: white;");

        Button btnInicio = crearBotonMenu("Inicio", () -> layout.setCenter(crearPanelInicio()));
        Button btnPerfil = crearBotonMenu("Mi Perfil", () -> layout.setCenter(crearPanelPerfil()));
        Button btnSesiones = crearBotonMenu("Sesiones", () -> layout.setCenter(crearPanelSesiones()));
        Button btnResenas = crearBotonMenu("Reseñas", () -> layout.setCenter(crearPanelResenas()));
        Button btnSolicitudes = crearBotonMenu("Solicitudes", () -> layout.setCenter(crearPanelSolicitudes()));

        menu.getChildren().addAll(lblMenu, new Separator(), btnInicio, btnPerfil, btnSesiones, btnResenas, btnSolicitudes);
        return menu;
    }

    private Button crearBotonMenu(String texto, Runnable accion) {
        Button btn = new Button(texto);
        btn.setPrefWidth(200);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: " + COLOR_SECUNDARIO + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;"));
        btn.setOnAction(e -> accion.run());
        return btn;
    }

    private VBox crearPanelInicio() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));
        panel.setAlignment(Pos.TOP_CENTER);

        Label lblBienvenida = new Label("Bienvenido, " + tutorActual.getNombre());
        lblBienvenida.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        Label lblInfo = new Label("Use el menú de la izquierda para navegar por las diferentes secciones.\nPuede gestionar sus sesiones, ver reseñas y actualizar su perfil.");
        lblInfo.setFont(Font.font("Arial", 16));
        lblInfo.setWrapText(true);

        VBox caja = new VBox(20);
        caja.setStyle("-fx-background-color: white; -fx-padding: 20px; -fx-background-radius: 5px;");
        caja.getChildren().addAll(lblInfo);

        panel.getChildren().addAll(lblBienvenida, caja);
        return panel;
    }

    private void cargarSesionesAceptadas(ListView<String> lista) {
        // Obtener sesiones aceptadas desde el controlador
        List<Sesion> sesionesAceptadas = controlador.obtenerSesionesAceptadasPorTutor(
            tutorActual.getIdUsuario()
        );
        
        if (sesionesAceptadas.isEmpty()) {
            return;
        }
        
        for (Sesion sesion : sesionesAceptadas) {
            Usuario estudiante = controlador.buscarUsuarioPorId(sesion.getEstudianteId());
            String nombreEstudiante = (estudiante != null) ? estudiante.getNombre() : "Desconocido";
            
            String item = String.format("%s - %s %s | Estudiante: %s | %s",
                sesion.getMateria(),
                sesion.getFecha(),
                sesion.getHora(),
                nombreEstudiante,
                sesion.getEstado()
            );
            
            lista.getItems().add(item);
        }
    }

    private VBox crearPanelPerfil() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(40));
        panel.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label("Mi Perfil");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        VBox info = new VBox(10);
        info.setStyle("-fx-background-color: white; -fx-padding: 20px; -fx-background-radius: 5px;");

        Label lblNombre = new Label("Nombre: " + tutorActual.getNombre());
        Label lblCorreo = new Label("Correo: " + tutorActual.getCorreo());
        Label lblTarifa = new Label("Tarifa: Q" + tutorActual.getTarifa() + " por hora");

        lblNombre.setStyle("-fx-font-size: 14px;");
        lblCorreo.setStyle("-fx-font-size: 14px;");
        lblTarifa.setStyle("-fx-font-size: 14px;");

        Label lblMaterias = new Label("Materias que imparte:");
        lblMaterias.setStyle("-fx-font-weight: bold;");

        ListView<String> listaMaterias = new ListView<>();
        listaMaterias.getItems().addAll(tutorActual.getMaterias());
        listaMaterias.setPrefHeight(150);

        Button btnEditar = new Button("Editar Información");
        btnEditar.setStyle("-fx-background-color: " + COLOR_SECUNDARIO + "; -fx-text-fill: white; -fx-font-weight: bold;");
        btnEditar.setOnAction(e -> mostrarEdicionPerfil());

        info.getChildren().addAll(lblNombre, lblCorreo, lblTarifa, lblMaterias, listaMaterias, btnEditar);
        panel.getChildren().addAll(titulo, info);
        return panel;
    }

    private VBox crearPanelSesiones() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(40));
        panel.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label("Mis Sesiones");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        VBox contenido = new VBox(10);
        contenido.setStyle("-fx-background-color: white; -fx-padding: 20px; -fx-background-radius: 5px;");

        Label lblProximas = new Label("Próximas Sesiones");
        lblProximas.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        ListView<String> lista = new ListView<>();
        lista.setPlaceholder(new Label("No hay sesiones programadas"));
        lista.setPrefHeight(300);

        cargarSesionesAceptadas(lista);
        contenido.getChildren().addAll(lblProximas, lista);
        panel.getChildren().addAll(titulo, contenido);
        return panel;
    }

    private VBox crearPanelResenas() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(40));
        panel.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label("Mis Reseñas");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        VBox contenido = new VBox(10);
        contenido.setStyle("-fx-background-color: white; -fx-padding: 20px; -fx-background-radius: 5px;");

        Label lblProm = new Label("Calificación Promedio: -");
        Label lblTotal = new Label("Total de Reseñas: 0");

        ListView<String> lista = new ListView<>();
        lista.setPlaceholder(new Label("No hay reseñas disponibles"));
        lista.setPrefHeight(300);

        contenido.getChildren().addAll(lblProm, lblTotal, lista);
        panel.getChildren().addAll(titulo, contenido);
        return panel;
    }

    private void cargarSolicitudesPendientes(VBox container) {
        container.getChildren().clear();
        
        // Obtener solicitudes pendientes desde el controlador
        List<Sesion> solicitudesPendientes = controlador.obtenerSesionesPendientesPorTutor(
            tutorActual.getIdUsuario()
        );
        
        if (solicitudesPendientes.isEmpty()) {
            Label lblVacio = new Label("No hay solicitudes pendientes");
            lblVacio.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");
            container.getChildren().add(lblVacio);
            return;
        }
        
        for (Sesion solicitud : solicitudesPendientes) {
            VBox tarjeta = crearTarjetaSolicitud(solicitud);
            container.getChildren().add(tarjeta);
        }
    }

    private VBox crearTarjetaSolicitud(Sesion solicitud) {
        VBox tarjeta = new VBox(10);
        tarjeta.setPadding(new Insets(15));
        tarjeta.setStyle("-fx-background-color: #ffffff; " +
                        "-fx-border-color: #bdc3c7; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 5; " +
                        "-fx-background-radius: 5;");
        
        // Obtener información del estudiante
        Usuario estudiante = controlador.buscarUsuarioPorId(solicitud.getEstudianteId());
        String nombreEstudiante = (estudiante != null) ? estudiante.getNombre() : "Estudiante desconocido";
        
        // Información de la solicitud
        Label lblEstudiante = new Label("Estudiante: " + nombreEstudiante);
        lblEstudiante.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Label lblMateria = new Label("Materia: " + solicitud.getMateria());
        lblMateria.setStyle("-fx-font-size: 13px;");
        
        Label lblFecha = new Label("Fecha: " + solicitud.getFecha());
        lblFecha.setStyle("-fx-font-size: 13px;");
        
        Label lblHora = new Label("Hora: " + solicitud.getHora());
        lblHora.setStyle("-fx-font-size: 13px;");
        
        Label lblEstado = new Label("Estado: " + solicitud.getEstado());
        lblEstado.setStyle("-fx-font-size: 13px; -fx-text-fill: #f39c12; -fx-font-weight: bold;");
        
        // Botones de acción
        HBox botonesAccion = new HBox(10);
        botonesAccion.setAlignment(Pos.CENTER_RIGHT);
        
        Button btnAceptar = new Button("✓ Aceptar");
        btnAceptar.setStyle("-fx-background-color: " + COLOR_SECUNDARIO + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-cursor: hand;");
        btnAceptar.setOnAction(e -> manejarAceptarSolicitud(solicitud));
        
        Button btnRechazar = new Button("✗ Rechazar");
        btnRechazar.setStyle("-fx-background-color: " + COLOR_RECHAZO + "; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-weight: bold; " +
                            "-fx-cursor: hand;");
        btnRechazar.setOnAction(e -> manejarRechazarSolicitud(solicitud));
        
        botonesAccion.getChildren().addAll(btnAceptar, btnRechazar);
        
        // Agregar separador visual
        Separator separador = new Separator();
        
        tarjeta.getChildren().addAll(
            lblEstudiante, 
            lblMateria, 
            lblFecha, 
            lblHora, 
            lblEstado,
            separador,
            botonesAccion
        );
        
        return tarjeta;
    }

    private void manejarAceptarSolicitud(Sesion solicitud) {
        // Confirmar con el tutor
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Aceptación");
        confirmacion.setHeaderText("¿Desea aceptar esta solicitud de tutoría?");
        confirmacion.setContentText(
            "Materia: " + solicitud.getMateria() + "\n" +
            "Fecha: " + solicitud.getFecha() + "\n" +
            "Hora: " + solicitud.getHora()
        );
        
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Guardar estado anterior por si falla
                EstadoSesion estadoAnterior = solicitud.getEstado();
                
                // Cambiar el estado a AGENDADA
                solicitud.setEstado(EstadoSesion.AGENDADA);
                
                // Actualizar usando el controlador
                boolean exito = controlador.actualizarEstadoSesion(solicitud);
                
                if (exito) {
                    mostrarInfo("Éxito", "La solicitud ha sido aceptada correctamente.");
                    layoutPrincipal.setCenter(crearPanelSolicitudes());
                } else {
                    // Revertir el cambio si falló
                    solicitud.setEstado(estadoAnterior);
                    mostrarError("Error", "No se pudo actualizar la sesión. Intente nuevamente.");
                }
            }
        });
    }

    private void manejarRechazarSolicitud(Sesion solicitud) {
        // Confirmar con el tutor
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Rechazo");
        confirmacion.setHeaderText("¿Desea rechazar esta solicitud de tutoría?");
        confirmacion.setContentText(
            "Materia: " + solicitud.getMateria() + "\n" +
            "Fecha: " + solicitud.getFecha() + "\n" +
            "Hora: " + solicitud.getHora() + "\n\n" +
            "Esta acción no se puede deshacer."
        );
        
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Guardar estado anterior por si falla
                EstadoSesion estadoAnterior = solicitud.getEstado();
                
                // Cambiar el estado a CANCELADA
                solicitud.setEstado(EstadoSesion.CANCELADA);
                
                // Actualizar usando el controlador
                boolean exito = controlador.actualizarEstadoSesion(solicitud);
                
                if (exito) {
                    mostrarInfo("Solicitud Rechazada", "La solicitud ha sido rechazada.");
                    layoutPrincipal.setCenter(crearPanelSolicitudes());
                } else {
                    // Revertir el cambio si falló
                    solicitud.setEstado(estadoAnterior);
                    mostrarError("Error", "No se pudo actualizar la sesión. Intente nuevamente.");
                }
            }
        });
    }

    private VBox crearPanelSolicitudes() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(40));
        panel.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label("Solicitudes de Tutoría");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        VBox contenido = new VBox(10);
        contenido.setStyle("-fx-background-color: white; -fx-padding: 20px; -fx-background-radius: 5px;");

        Label lblPend = new Label("Solicitudes Pendientes");
        lblPend.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // ScrollPane para contener las tarjetas de solicitudes
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
        scrollPane.setStyle("-fx-background-color: transparent;");

        VBox listaSolicitudes = new VBox(15);
        listaSolicitudes.setPadding(new Insets(10));
        
        // Cargar solicitudes pendientes
        cargarSolicitudesPendientes(listaSolicitudes);
        
        scrollPane.setContent(listaSolicitudes);

        contenido.getChildren().addAll(lblPend, scrollPane);
        panel.getChildren().addAll(titulo, contenido);
        return panel;
    }

    private void mostrarEdicionPerfil() {
        VBox contenidoEdicion = new VBox(15);
        contenidoEdicion.setPadding(new Insets(40));
        contenidoEdicion.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label("Editar Información");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        VBox formulario = new VBox(10);
        formulario.setStyle("-fx-background-color: white; -fx-padding: 20px; -fx-background-radius: 5px;");

        Label lblTarifa = new Label("Tarifa por Hora (Q):");
        TextField txtTarifa = new TextField(String.valueOf(tutorActual.getTarifa()));

        Label lblPass = new Label("Nueva Contraseña (Opcional):");
        PasswordField txtPass = new PasswordField();

        HBox botones = new HBox(10);
        botones.setAlignment(Pos.CENTER);

        Button btnGuardar = new Button("Guardar Cambios");
        btnGuardar.setStyle("-fx-background-color: " + COLOR_SECUNDARIO + "; -fx-text-fill: white; -fx-font-weight: bold;");
        btnGuardar.setOnAction(e -> {
            try {
                double nuevaTarifa = Double.parseDouble(txtTarifa.getText());
                tutorActual.setTarifa(nuevaTarifa);
                if (!txtPass.getText().isEmpty()) {
                    tutorActual.setContrasena(txtPass.getText());
                }
                mostrarInfo("Éxito", "Perfil actualizado correctamente");
                layoutPrincipal.setCenter(crearPanelPerfil());
            } catch (NumberFormatException ex) {
                mostrarError("Error", "La tarifa debe ser un número válido");
            }
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> layoutPrincipal.setCenter(crearPanelPerfil()));

        botones.getChildren().addAll(btnGuardar, btnCancelar);

        formulario.getChildren().addAll(lblTarifa, txtTarifa, lblPass, txtPass, botones);
        contenidoEdicion.getChildren().addAll(titulo, formulario);

        layoutPrincipal.setCenter(contenidoEdicion);
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}