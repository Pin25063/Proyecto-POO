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
    private Stage dialogStage;
    
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

        // 1. Preparamos la VENTANA para que PUEDA maximizarse
        // Eliminar restricciones de tamaño previas (del login/registro)
        stage.setMinWidth(Double.MIN_VALUE);
        stage.setMaxWidth(Double.MAX_VALUE);
        stage.setMinHeight(Double.MIN_VALUE);
        stage.setMaxHeight(Double.MAX_VALUE);
        
        stage.setResizable(true);
        stage.setMinWidth(900);
        stage.setMinHeight(600);

        // 2. Creamos y ponemos la ESCENA 
        Scene escena = new Scene(layoutPrincipal, 1100, 700);
        stage.setScene(escena);
        stage.setTitle("Panel del Tutor - Gestor de Tutorías UVG");

        // 3. Maximizamos la VENTANA  DESPUÉS de poner la escena
        stage.setMaximized(true);

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

        btnCerrarSesion.setOnAction(e -> {
            // Verificar si la ventana de edición existe y está abierta
            if (dialogStage != null && dialogStage.isShowing()) {
                dialogStage.close(); // La cerramos a la fuerza
            }
            
            // Volver al login
            mainApp.mostrarLogin();
        });

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

        // se utiliza el atributo de la clase en lugar de variable local
        if (this.dialogStage == null) {
            this.dialogStage = new Stage();
        }

        dialogStage.setTitle("Editar Perfil - Tutor");

        // se hace que la ventana dialogStage pertenezca a la ventana principal
        dialogStage.initOwner(this.stage);

        VBox contenidoEdicion = new VBox(15);
        contenidoEdicion.setPadding(new Insets(40));
        contenidoEdicion.setAlignment(Pos.CENTER);
        contenidoEdicion.setStyle("-fx-background-color: #f4f4f4;");

        Label titulo = new Label("Actualizar Información");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titulo.setStyle("-fx-text-fill: #2c3e50;");

        // GridPane para el formulario
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);

        // CAMPOS

        // Nombre
        Label lblNombre = new Label("Nombre Completo:");
        TextField txtNombre = new TextField(tutorActual.getNombre());
        txtNombre.setPrefWidth(250);
        // No se puede editar el nombre por seguridad
        txtNombre.setDisable(true);

        // Tarifa
        Label lblTarifa = new Label("Tarifa por Hora (Q):");
        TextField txtTarifa = new TextField(String.valueOf(tutorActual.getTarifa()));
        txtTarifa.setPromptText("Ej: 150.0");

        // Contraseña Actual
        Label lblPassActual = new Label("Contraseña Actual:");
        PasswordField txtPassActual = new PasswordField();
        txtPassActual.setPromptText("Requerido para guardar");

        // Nueva Contraseña
        Label lblPassNueva = new Label("Nueva Contraseña:");
        PasswordField txtPassNueva = new PasswordField();
        txtPassNueva.setPromptText("Opcional");

        // Confirmar Nueva Contraseña
        Label lblPassConfirm = new Label("Confirmar Nueva:");
        PasswordField txtPassConfirm = new PasswordField();
        txtPassConfirm.setPromptText("Repetir nueva contraseña");
        
        // Añadir al Grid
        grid.addRow(0, lblNombre, txtNombre);
        grid.addRow(1, lblTarifa, txtTarifa);
        grid.addRow(2, new Separator(), new Separator());
        grid.addRow(3, lblPassActual, txtPassActual);
        grid.addRow(4, lblPassNueva, txtPassNueva);
        grid.addRow(5, lblPassConfirm, txtPassConfirm);

        HBox botones = new HBox(15);
        botones.setAlignment(Pos.CENTER);

        Button btnGuardar = new Button("Guardar Cambios");
        btnGuardar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");

        botones.getChildren().addAll(btnGuardar, btnCancelar);
        
        // Lógica de guardar
        btnGuardar.setOnAction(e -> {
            String tarifaTexto = txtTarifa.getText().trim();
            String passActual = txtPassActual.getText();
            String passNueva = txtPassNueva.getText();
            String passConfirm = txtPassConfirm.getText();

            // Validaciones básicas
            if (tarifaTexto.isEmpty()) {
                mostrarError("Error", "La tarifa es obligatoria.");
                return;
            }

            // Validación de Tarifa (Numérica)
            double nuevaTarifa;
            try {
                nuevaTarifa = Double.parseDouble(tarifaTexto);
                if (nuevaTarifa < 0) {
                    mostrarError("Error", "La tarifa no puede ser negativa.");
                    return;
                }
            } catch (NumberFormatException ex) {
                mostrarError("Error", "La tarifa debe ser un número válido (Ej: 150.00).");
                return;
            }


            // Se detecta si el usuario quiere cambiar la contraseña
            // Si el campo de nueva contraseña NO ESTÁ VACÍO, se asume que quiere cambiarla
            boolean intentandoCambiarPass = !passNueva.isEmpty();

            if (intentandoCambiarPass) {

                // VALIDACIONES DE SEGURIDAD (Solo si cambia contraseña)
                // Contraseña actual obligatoria
                if (passActual.isEmpty()) {
                    mostrarError("Seguridad", "Para cambiar tu contraseña, debes confirmar tu contraseña actual");
                    return;
                }

                // Verificar contraseña actual
                if (!tutorActual.verificarContrasena(passActual)) {
                    mostrarError("Seguridad", "La contraseña actual es incorrecta");
                    return;
                }

                // Validar coincidencia
                if (!passNueva.equals(passConfirm)) {
                    mostrarError("Error", "Las nuevas contraseñas no coinciden");
                    return;
                }

                // Validar longitud
                if (passNueva.length() < 6) {
                    mostrarError("Seguridad", "La nueva contraseña debe tener al menos 6 caracteres");
                    return;
                }
            }

            // APLICAR CAMBIOS
            tutorActual.setTarifa(nuevaTarifa); // Se guarda la tarifa
            if (intentandoCambiarPass) {
                tutorActual.setContrasena(passNueva);
            }

            // Persistencia (Guardar en el CSV)
            boolean exito = controlador.actualizarUsuario(tutorActual);

            if (exito) {
                mostrarInfo("Éxito", "Perfil actualizado correctamente.");
                dialogStage.close();
                // Se recarga el panel central para ver los cambios reflejados (nombre/tarifa)
                layoutPrincipal.setCenter(crearPanelPerfil());
            } else {
                mostrarError("Error Crítico", "No se pudo guardar en el archivo.");
            }
        });

        btnCancelar.setOnAction(e -> dialogStage.close());

        contenidoEdicion.getChildren().addAll(titulo, grid, botones);
        

        Scene scene = new Scene(contenidoEdicion, 550, 400);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);

        dialogStage.showAndWait();
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