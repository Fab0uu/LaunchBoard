import fr.fabou.launchboard.Display_Controller;
import fr.fabou.launchboard.Launchpad_Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.sound.midi.MidiUnavailableException;
import java.util.Objects;



public class Display_Init extends Application {
    public static Display_Controller display_control = new Display_Controller();
    public static FXMLLoader loader = new FXMLLoader(Display_Init.class.getResource("/assets/ui.fxml"));
    private Launchpad_Controller launchpad_control;

    public void display() {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        this.launchpad_control = new Launchpad_Controller();

        Parent root = loader.load();


        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo.png"))));

        Scene scene = new Scene(root, 900, 460);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("LaunchBoard");
        stage.show();

        new Thread(() -> {
            try {
            } catch (MidiUnavailableException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
