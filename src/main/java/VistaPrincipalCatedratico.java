import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class VistaPrincipalCatedratico {
    
    private ControladorPrincipal controlador;
    private ControladorAdministradores controladorAdmin;
    private Catedratico catedratico;
    private Stage stage;
    private Main mainApp; // Referencia a la aplicación principal para poder cerrar sesión.

    public VistaPrincipalCatedratico(ControladorPrincipal controlador, Catedratico catedratico, Stage stage, Main mainApp) {
        this.controlador = controlador;
        this.catedratico = catedratico;
        this.stage = stage;
        this.mainApp = mainApp;
        // Creamos una nueva instancia del controlador de administradores, pasándole el controlador principal
        // Esto le da acceso a los datos que necesita para funcionar
        this.controladorAdmin = new ControladorAdministradores(controlador);
    }


    // Construye y muestra la interfaz del catedrático
    public void mostrar() {
        // BorderPane es un layout que divide la pantalla en 5 zonas: Top, Left, Center, Right, Bottom
        BorderPane layoutPrincipal = new BorderPane();

        // color de fondo base a toda la ventana
        layoutPrincipal.setStyle("-fx-background-color: #ecf0f1;");

        // Componentes Principales de la UI
        HBox barraSuperior = crearBarraSuperior(); // título, nombre de usuario, botón de cerrar sesión
        VBox menuLateral = crearMenuLateral(layoutPrincipal); // botones para cambiar de panel
        VBox contenidoInicial = crearPanelInicio(); // Bienvenida
        
        // Ensamblaje del Layout principal
        // Colocar cada componente en su zona correspondiente del BorderPane
        layoutPrincipal.setTop(barraSuperior); // barra superior arriba
        layoutPrincipal.setLeft(menuLateral); // menú a la izquierda
        layoutPrincipal.setCenter(contenidoInicial); // Panel de contenido en el centro

        // Crear escena con el layout principal y definimos su tamaño inicial
        Scene escena = new Scene(layoutPrincipal, 1100, 700);
        stage.setScene(escena);  // establecer la escena en la ventana principal
        stage.setTitle("Panel del Catedrático - Gestor de Tutorías UVG"); // Título de ventana
        // establecer tamaño mínimo para evitar deformaciones
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show(); // mostrar ventana al cliente
    }

    // Métodos privados para construir las partes de la UI
    private HBox crearBarraSuperior() {
        HBox barra = new HBox(20); // Organiza su contenido en una fila horizontal, el 20 es el espaciado
        barra.setPadding(new Insets(15)); // Padding es el relleno interior, para que los elementos no estén pegados a los bordes
        // barra.setStyle("-fx-background-color: #215015ff;"); // Color para la HBox
        barra.setAlignment(Pos.CENTER_LEFT); // Alinear elementos al la izquierda

        // Crear el Label para el título de la aplicación
        Label lblTitulo = new Label("Gestor de Tutorías UVG");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Estilo de fuente
        lblTitulo.setStyle("-fx-text-fill: black;"); // Color del texto
        
        Region espaciador = new Region(); // atajo para crear un espacio que empuja a todos los elementos a la derecha
        // se indica al HBox que ese espaciador debe crecer tanto como sea posible, empujando todo lo que viene después hacia la derecha.
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        // Crear label de bienvenida con el nombre del catedrático actual
        Label lblBienvenida = new Label("Catedrático: " + catedratico.getNombre());
        lblBienvenida.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblBienvenida.setStyle("-fx-text-fill: black;");
        
        // Crear botón para cerrar sesión
        Button btnCerrarSesion = new Button("Cerrar Sesión");
        // Estilo para hacerlo rojo y de texto blanco
        btnCerrarSesion.setStyle("-fx-background-color: #d12a17ff; -fx-text-fill: white; -fx-font-weight: bold;");
        // Se define la acción que se ejecuta al hacer clic llamando al método mostrarLogin() de la clase Main
        btnCerrarSesion.setOnAction(e -> mainApp.mostrarLogin());

        // Añadir los los componentes al HBox en el orden en que deben aparecer
        barra.getChildren().addAll(lblTitulo, espaciador, lblBienvenida, btnCerrarSesion);
        return barra;
    }

    // MENU DE NAVEGACION LATERAL
    private VBox crearMenuLateral(BorderPane layoutPrincipal) {
        VBox menu = new VBox(10); // Organiza su contenido en una fila vertical, el 10 es el espaciado
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(220); // Se le da un ancho fijo para que no cambie de tamaño
        // menu.setStyle("-fx-background-color: #3d8335ff;");
    
        Label lblMenu = new Label("MENÚ");
        lblMenu.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblMenu.setStyle("-fx-text-fill: black;");
        lblMenu.setAlignment(Pos.CENTER); // Centrar el texto del label

        // Crear los botones de navegación usando un método auxiliar para no repetir código
        Button btnInicio = crearBotonMenu("INICIO");
        Button btnAsignarTutorias = crearBotonMenu("Asignar Tutorías");
        Button btnReporteEstudiantes = crearBotonMenu("Reportes de Cursos");
        Button btnReporteTutores = crearBotonMenu("Reportes de Tutores");

        // Cada botón cambia el panel central del BorderPane
        btnInicio.setOnAction(e -> layoutPrincipal.setCenter(crearPanelInicio()));
        btnAsignarTutorias.setOnAction(e -> layoutPrincipal.setCenter(crearPanelAsignarTutorias()));
        btnReporteEstudiantes.setOnAction(e -> layoutPrincipal.setCenter(crearPanelReporteCursos()));
        btnReporteTutores.setOnAction(e -> layoutPrincipal.setCenter(crearPanelReporteTutores()));
        
        // Añadir los componentes al VBox, con un separador visual
        menu.getChildren().addAll(lblMenu, new Separator(), btnInicio, btnAsignarTutorias, btnReporteEstudiantes, btnReporteTutores);
        return menu;
    }
    
    // Paneles de Contenido
    private VBox crearPanelInicio() {
        VBox panel = new VBox(20); // Organizar contenido verticalmente
        panel.setPadding(new Insets(40));
        panel.setAlignment(Pos.TOP_CENTER); // Alinear contenido a la parte superior central
        
        // Titulo de bienvenida con el nombre del catedrático actual
        Label lblTitulo = new Label("Bienvenido, " + catedratico.getNombre());
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        
        // Texto de instrucciones para el usuario
        Label lblInfoTexto = new Label(
            "Use el menú de la izquierda para navegar por las diferentes herramientas de la aplicación.\n" +
            "Puede asignar tutorías, generar reportes de sus cursos y evaluar el desempeño de los tutores."
        );
        lblInfoTexto.setFont(Font.font("Arial", 16));
        lblInfoTexto.setWrapText(true); // Permite que el texto se divida en varias líneas si no cabe
        lblInfoTexto.setStyle("-fx-text-alignment: center;"); // Centra el texto de las líneas

        // Se añaden los componentes al panel
        panel.getChildren().addAll(lblTitulo, new Separator(), lblInfoTexto);
        return panel;
    }
    
    // PANEL ASIGNAR TUTORIAS
    private VBox crearPanelAsignarTutorias() {
        VBox panel = new VBox(25);
        panel.setPadding(new Insets(40));
        
        Label lblTitulo = new Label("Asignar Nueva Tutoría");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // GridPane es un layout de tipo cuadrícula que es EXCELENTE para formularios
        GridPane formulario = new GridPane();
        formulario.setHgap(15); // Espaciado horizontal entre columnas
        formulario.setVgap(15); // Espaciado vertical entre filas
        
        // Componentes Formulario
        // ComboBox: Menu desplegable para seleccionar Estudiantes
        ComboBox<Usuario> cmbEstudiante = new ComboBox<>();
        cmbEstudiante.setPromptText("Seleccionar ESTUDIANTE"); // texto mostrado por defecto en el ComboBox

        // Se filtra la lista de todos los usuarios para obtener solo a los estudiantes.
        List<Usuario> estudiantes = new ArrayList<>();
        List<Usuario> todosLosUsuarios = controlador.getListaDeUsuarios();

        // Recorrer la lista completa
        for (Usuario usuario : todosLosUsuarios) {
            if (usuario.getRol() == Rol.ESTUDIANTE) {
                estudiantes.add(usuario);
            }
        }
        cmbEstudiante.getItems().addAll(estudiantes); // Se añaden los estudiantes al menú
        
        // ComboBox para Tutores
        ComboBox<Usuario> cmbTutor = new ComboBox<>();
        cmbTutor.setPromptText("Seleccionar TUTOR");
        
        // Se filtra la lista de todos los usuarios para obtener solo a los tutores.
        List<Usuario> tutores = new ArrayList<>();

        for (Usuario usuario : todosLosUsuarios) {
            if (usuario.getRol() == Rol.TUTOR) {
                tutores.add(usuario);
            }
        }
        cmbTutor.getItems().addAll(tutores);
        
        // ComboBox para Cursos del Catedrático
        ComboBox<Curso> cmbCurso = new ComboBox<>();
        cmbCurso.setPromptText("Seleccionar Curso");
        cmbCurso.getItems().addAll(catedratico.getCursosACargo()); // se obtiene solo los cursos de este catedrático.

        // Boton para confirmar la asignacion
        Button btnAsignar = new Button("Confirmar Asignación");
        btnAsignar.setStyle("-fx-background-color: #0e853fff; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        // Logica del boton ASIGNAR
        btnAsignar.setOnAction(e -> {
            // obtener los valores seleccionados en el ComboBox
            Usuario est = cmbEstudiante.getValue();
            Usuario tut = cmbTutor.getValue();
            Curso cur = cmbCurso.getValue();
            
            // Validar que se haya seleccionado una opcione en cada menú
            if (est == null || tut == null || cur == null) {
                mostrarAlerta("CAMPOS INCOMPLETOS", "Por favor, selecciona un estudiante, un tutor y un curso", Alert.AlertType.WARNING);
                return; // se detiene si algo falla
            }

            // Llamar al método del controlador de administradores para que haga la lógica
            Sesion sesionAsignada = controladorAdmin.asignarTutoria(est.getIdUsuario(), tut.getIdUsuario(), cur);
            
            // Verificar si la asignacion fue exitosa
            if (sesionAsignada != null) {
                mostrarAlerta("EXITO", "Tutoría para '" + cur.getNombreCurso() + "' asignada a " + est.getNombre() + " con el tutor " + tut.getNombre() + ".", Alert.AlertType.INFORMATION);
                // Se limpian los ComboBox para una nueva asignación
                cmbEstudiante.setValue(null);
                cmbTutor.setValue(null);
                cmbCurso.setValue(null);
            } else {
                // Mensaje de error si algo falla
                mostrarAlerta("Error", "No se pudo asignar la tutoría. Verifica la consola para más detalles.", Alert.AlertType.ERROR);
            }
        });

        // Añadir componentes al GridPane en posiciones específicas (columna, fila)
        formulario.add(new Label("Estudiante:"), 0, 0);
        formulario.add(cmbEstudiante, 1, 0);
        formulario.add(new Label("Tutor:"), 0, 1);
        formulario.add(cmbTutor, 1, 1);
        formulario.add(new Label("Curso:"), 0, 2);
        formulario.add(cmbCurso, 1, 2);
        
        // Añadir los componentes principales al panel
        panel.getChildren().addAll(lblTitulo, formulario, btnAsignar);
        return panel;
    }

    // PANEL REPORTECURSOS
    private VBox crearPanelReporteCursos() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));

        Label lblTitulo = new Label("Reporte de Actividad por Curso");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // ComboBox para que el catedrático elija de cuál de sus cursos quiere ver el reporte
        ComboBox<Curso> cmbCurso = new ComboBox<>();
        cmbCurso.setPromptText("Seleccione uno de sus cursos para ver el reporte");
        cmbCurso.getItems().addAll(catedratico.getCursosACargo());
        cmbCurso.setMaxWidth(Double.MAX_VALUE); // Hacer que ocupe todo el ancho disponible

        // TextArea es un área de texto de múltiples líneas, ideal para mostrar reportes
        TextArea txtReporte = new TextArea();
        txtReporte.setEditable(false); // El usuario no puede modificar el texto
        txtReporte.setPrefHeight(400); // Altura preferida
        txtReporte.setFont(Font.font("Monospaced", 12)); // fuente y tamaño
        txtReporte.setPromptText("El reporte del curso seleccionado se mostrará aquí...");

        // Cuando el catedrático selecciona un curso, se genera el reporte.
        cmbCurso.setOnAction(e -> {
            Curso cursoSeleccionado = cmbCurso.getValue();
            // Chequear validez del curso
            if (cursoSeleccionado != null) {
                // Se llama al controlador para generar el reporte
                String reporte = controladorAdmin.generarReporteConsolidadoCurso(cursoSeleccionado);
                // Mostrar resultado en TextArea
                txtReporte.setText(reporte);
            }
        });

        panel.getChildren().addAll(lblTitulo, cmbCurso, txtReporte);
        return panel;
    }


    private VBox crearPanelReporteTutores() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));
        
        Label lblTitulo = new Label("Reporte General de Desempeño de Tutores");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        TextArea txtReporte = new TextArea();
        txtReporte.setEditable(false);
        txtReporte.setPrefHeight(500);
        txtReporte.setFont(Font.font("Monospaced", 12));
        
        // Generamos y mostramos el reporte inmediatamente al entrar a esta vista.
        String reporte = controladorAdmin.generarReporteDesempenoTutores();
        txtReporte.setText(reporte);

        panel.getChildren().addAll(lblTitulo, txtReporte);
        return panel;
    }
    
    // HELPERS
    private Button crearBotonMenu(String texto) {
        Button boton = new Button(texto);
        boton.setPrefWidth(200); // ancho preferido
        boton.setPrefHeight(40); // altura preferida
        boton.setAlignment(Pos.CENTER_LEFT); // alinear texto a la izquierda del botón
        // Estilo del botón
        boton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        // Efecto hover para una mejor experiencia de usuario.
        // setOnMouseEntered define qué pasa cuando el cursor entra en el área del botón
        boton.setOnMouseEntered(e -> boton.setStyle("-fx-background-color: #96794eff; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold;"));
        // setOnMouseExited define qué pasa cuando el cursor sale del área del botón.
        boton.setOnMouseExited(e -> boton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold;"));
        
        return boton;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        // Crear nueva instancia de alerta
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        // Muestra la alerta y espera que el usuario la cierre
        alerta.showAndWait();
    }
}