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

import org.jetbrains.annotations.NotNull;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;
import java.util.Objects;


public class Display_Controller extends Application {
    private MidiDevice launchpadDevice;
    private boolean connexion_status;
    private boolean last_connexion_status;

    @FXML
    public Label connected_label;

    public void display() throws MidiUnavailableException {

        launch();

    }

    @Override
    public void start(@NotNull Stage stage) throws IOException {

        System.out.println("start");


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/ui.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        //this.display_status();

        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo.png"))));

        Scene scene = new Scene(root, 900, 460);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("LaunchBoard");
        stage.show();

        new Thread(() -> {
            try {
                this.status();
            } catch (MidiUnavailableException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    protected void status() throws MidiUnavailableException, InterruptedException {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

        for (MidiDevice.Info info : infos) {
            MidiDevice device = MidiSystem.getMidiDevice(info);
            if (device.getDeviceInfo().getName().contains("Launchpad")) {
                launchpadDevice = device;
            }
            else {
                launchpadDevice = null;
            }
        }

        this.display_status();
        last_connexion_status = connexion_status;

        if (launchpadDevice != null) {this.connexion_status = true;}
        else {this.connexion_status = false;};

        while (true) {

            for (MidiDevice.Info info : infos) {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                if (device.getDeviceInfo().getName().contains("Launchpad")) {
                    launchpadDevice = device;
                }
                else {
                    launchpadDevice = null;
                }
            }

            if (launchpadDevice != null) {this.connexion_status = true;}
            else {this.connexion_status = false;};

            if (last_connexion_status != connexion_status) {this.display_status();last_connexion_status = connexion_status;};

            Thread.sleep(500);
        }
    }

    @FXML
    protected void display_status() {
        if (connected_label != null) {
            if (connexion_status) {
                Platform.runLater(() -> this.connected_label.setText("Connected"));
                System.out.println("Connected");
            } else {
                Platform.runLater(() -> this.connected_label.setText("Disconnected"));
                System.out.println("Disconnected");
            };
        };
    }
}