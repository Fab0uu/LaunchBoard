package fr.fabou.launchboard;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import org.jetbrains.annotations.NotNull;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;


public class Display_Controller extends Application {

    private MidiDevice launchpadDevice;
    private boolean connexion_status;
    private boolean last_connexion_status;

    @FXML
    public Label connected_label;

    public void display() throws MidiUnavailableException {

        new Thread(() -> {
            try {
                this.status();
            } catch (MidiUnavailableException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        launch();

    }

    @Override
    public void start(@NotNull Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/ui.fxml"));
        loader.setController(this);
        Parent root = loader.load();


        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo.png"))));

        Scene scene = new Scene(root, 900, 460);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("LaunchBoard");
        stage.show();
    }

    protected void status() throws MidiUnavailableException, InterruptedException {
        connexion_status = false;

        while (true) {
            MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
            for (MidiDevice.Info info : infos) {
                MidiDevice device = MidiSystem.getMidiDevice(info);

                if (device.getDeviceInfo().getName().contains("Launchpad")) {
                    launchpadDevice = device;
                }
            }
            if (!Arrays.toString(infos).contains("Launchpad")){
                launchpadDevice = null;
            }

            if (launchpadDevice != null) {this.connexion_status = true;}
            else {this.connexion_status = false;};

            if (last_connexion_status != connexion_status) {this.display_status();last_connexion_status = connexion_status;};

            Thread.sleep(500);
        }
    }

    @FXML
    protected void display_status() {
        System.out.println(connexion_status);
        System.out.println(connected_label);
        if (connected_label != null) {
            if (connexion_status) {
                System.out.println("Connected");
                Platform.runLater(() -> this.connected_label.setText("Connected"));
            } else {
                System.out.println("Disconnected");
                Platform.runLater(() -> this.connected_label.setText("Disconnected"));
            }
            ;
        }
    }
}