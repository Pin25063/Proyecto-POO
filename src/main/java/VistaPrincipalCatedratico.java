import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class VistaPrincipalCatedratico extends VBox{
    
    private Catedratico catedraticoActual;
    private Label lblNombre, lblCorreo;

    public VistaPrincipalCatedratico(Catedratico catedratico) {
        this.catedraticoActual = catedratico;

        Label titulo = new Label("Perfil de catedratico");
        
        lblNombre = new Label("Nombre: " + catedratico.getNombre());
        lblCorreo = new Label("Correo: " + catedratico.getCorreo());

        setSpacing(10);
        setPadding(new Insets(20));

        getChildren().addAll(titulo, lblNombre, lblCorreo);

        Label lblCursos = new Label("Cursos a cargo:");
        ListView<String> listaCursos = new ListView<>();

        if (catedratico.getCursos() != null) {
            for (Curso curso : catedratico.getCursos()) {
                listaCursos.getItems().add(curso.toString());
            }
        }

        listaCursos.setPrefHeight(120);

        getChildren().addAll(lblCursos, listaCursos);
    }


}
