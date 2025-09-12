import javafx.geometry.Insets;
import javafx.scene.control.Label;
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
    }


}
