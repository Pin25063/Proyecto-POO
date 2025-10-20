import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VistaRegistro extends VBox {

    private Runnable onCancel;

    public VistaRegistro() {
        Label label = new Label("Pantalla de Registro");
        getChildren().add(label);
    }

    public void setOnCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }

    public void setControlador(ControladorPrincipal controlador) {
        
    }
}