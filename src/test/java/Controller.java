import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.application.Platform;

public class Controller {

    @FXML
    private Label myLabel;
    private Button sq0;

    @FXML
    public void setSq0ColorBlue() {
        Platform.runLater(() -> {
            sq0.setStyle("-fx-background-color: blue;");
        });
    }
}