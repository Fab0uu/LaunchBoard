package fr.fabou.launchboard;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.gluonhq.charm.glisten.control.CardPane;
import javafx.scene.control.Alert.AlertType;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class Display_Controller extends Application{
    private Launchpad_Controller launchpad_control;
    public static boolean connexion_status;
    public static boolean last_connexion_status;
    public static Map<Integer, Button> mapButton = new HashMap<>();

    @FXML public Label connected_label;
    @FXML public Button Bs0;@FXML public Button Bs1;@FXML public Button Bs2;@FXML public Button Bs3;@FXML public Button Bs4;@FXML public Button Bs5;@FXML public Button Bs6;@FXML public Button Bs7;@FXML public Button Bc8;
    @FXML public Button Bs16;@FXML public Button Bs17;@FXML public Button Bs18;@FXML public Button Bs19;@FXML public Button Bs20;@FXML public Button Bs21;@FXML public Button Bs22;@FXML public Button Bs23;@FXML public Button Bc24;
    @FXML public Button Bs32;@FXML public Button Bs33;@FXML public Button Bs34;@FXML public Button Bs35;@FXML public Button Bs36;@FXML public Button Bs37;@FXML public Button Bs38;@FXML public Button Bs39;@FXML public Button Bc40;
    @FXML public Button Bs48;@FXML public Button Bs49;@FXML public Button Bs50;@FXML public Button Bs51;@FXML public Button Bs52;@FXML public Button Bs53;@FXML public Button Bs54;@FXML public Button Bs55;@FXML public Button Bc56;
    @FXML public Button Bs64;@FXML public Button Bs65;@FXML public Button Bs66;@FXML public Button Bs67;@FXML public Button Bs68;@FXML public Button Bs69;@FXML public Button Bs70;@FXML public Button Bs71;@FXML public Button Bc72;
    @FXML public Button Bs80;@FXML public Button Bs81;@FXML public Button Bs82;@FXML public Button Bs83;@FXML public Button Bs84;@FXML public Button Bs85;@FXML public Button Bs86;@FXML public Button Bs87;@FXML public Button Bc88;
    @FXML public Button Bs96;@FXML public Button Bs97;@FXML public Button Bs98;@FXML public Button Bs99;@FXML public Button Bs100;@FXML public Button Bs101;@FXML public Button Bs102;@FXML public Button Bs103;@FXML public Button Bc104;
    @FXML public Button Bs112;@FXML public Button Bs113;@FXML public Button Bs114;@FXML public Button Bs115;@FXML public Button Bs116;@FXML public Button Bs117;@FXML public Button Bs118;@FXML public Button Bs119;@FXML public Button Bc120;
    @FXML public Button CC104;@FXML public Button CC105;@FXML public Button CC106;@FXML public Button CC107;@FXML public Button CC108;@FXML public Button CC109;@FXML public Button CC110;@FXML public Button CC111;

    @FXML private CardPane<Label> menu; @FXML private Button menu_create_button;

    public void display() {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.setProperty("javafx.platform", "desktop");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/ui.fxml"));
        loader.setController(this);

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
                launchpad_control.status();
            } catch (MidiUnavailableException | InterruptedException | InvalidMidiDataException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                this.display_status();
            } catch (InterruptedException | MidiUnavailableException | InvalidMidiDataException e) {
                throw new RuntimeException(e);
            }
        }).start();

        this.ButtonMapping();
    }

    @FXML
    protected void display_status() throws InterruptedException, MidiUnavailableException, InvalidMidiDataException {
        while (true) {
            if (Display_Controller.last_connexion_status != Display_Controller.connexion_status) {
                if (connexion_status) {
                    System.out.println("Connected");
                    Platform.runLater(() -> this.connected_label.setText("Connected"));
                    launchpad_control.startup();
                } else {
                    System.out.println("Disconnected");
                    Platform.runLater(() -> this.connected_label.setText("Disconnected"));
                }
                Display_Controller.last_connexion_status = Display_Controller.connexion_status;
            }
            Thread.sleep(500);
        }
    }

    public void ButtonMapping() {
        // 1 à 7
        mapButton.put(0, Bs0);mapButton.put(1, Bs1);mapButton.put(2, Bs2);mapButton.put(3, Bs3);mapButton.put(4, Bs4);mapButton.put(5, Bs5);mapButton.put(6, Bs6);mapButton.put(7, Bs7); mapButton.put(8, Bc8);
        // 16 à 23
        mapButton.put(16, Bs16);mapButton.put(17, Bs17);mapButton.put(18, Bs18);mapButton.put(19, Bs19);mapButton.put(20, Bs20);mapButton.put(21, Bs21);mapButton.put(22, Bs22);mapButton.put(23, Bs23);mapButton.put(24, Bc24);
        // 32 à 39
        mapButton.put(32, Bs32);mapButton.put(33, Bs33);mapButton.put(34, Bs34);mapButton.put(35, Bs35);mapButton.put(36, Bs36);mapButton.put(37, Bs37);mapButton.put(38, Bs38);mapButton.put(39, Bs39);mapButton.put(40, Bc40);
        // 48 à 55
        mapButton.put(48, Bs48);mapButton.put(49, Bs49);mapButton.put(50, Bs50);mapButton.put(51, Bs51);mapButton.put(52, Bs52);mapButton.put(53, Bs53);mapButton.put(54, Bs54);mapButton.put(55, Bs55);mapButton.put(56, Bc56);
        // 64 à 71
        mapButton.put(64, Bs64);mapButton.put(65, Bs65);mapButton.put(66, Bs66);mapButton.put(67, Bs67);mapButton.put(68, Bs68);mapButton.put(69, Bs69);mapButton.put(70, Bs70);mapButton.put(71, Bs71);mapButton.put(72, Bc72);
        // 80 à 87
        mapButton.put(80, Bs80);mapButton.put(81, Bs81);mapButton.put(82, Bs82);mapButton.put(83, Bs83);mapButton.put(84, Bs84);mapButton.put(85, Bs85);mapButton.put(86, Bs86);mapButton.put(87, Bs87);mapButton.put(88, Bc88);
        // 96 à 103
        mapButton.put(96, Bs96);mapButton.put(97, Bs97);mapButton.put(98, Bs98);mapButton.put(99, Bs99);mapButton.put(100, Bs100);mapButton.put(101, Bs101);mapButton.put(102, Bs102);mapButton.put(103, Bs103);mapButton.put(104, Bc104);
        // 112 à 119
        mapButton.put(112, Bs112);mapButton.put(113, Bs113);mapButton.put(114, Bs114);mapButton.put(115, Bs115);mapButton.put(116, Bs116);mapButton.put(117, Bs117);mapButton.put(118, Bs118);mapButton.put(119, Bs119);mapButton.put(120, Bc120);
        // Control Change
        mapButton.put(1104, CC104);mapButton.put(1105, CC105);mapButton.put(1106, CC106);mapButton.put(1107, CC107);mapButton.put(1108, CC108);mapButton.put(1109, CC109);mapButton.put(1110, CC110);mapButton.put(1111, CC111);
    }

    public void button_showing(int note, int velocity, String type) {
        Button button = mapButton.get(note);
        if (Objects.equals(type, "note")) {
            if (note == 8 || note == 24 || note == 40 || note == 56 || note == 72 || note == 88 || note == 104 || note == 120) {
                if (velocity > 0) {
                    Platform.runLater(() -> button.getStyleClass().clear());
                    Platform.runLater(() -> button.getStyleClass().add("button_circle_showing"));
                } else {
                    Platform.runLater(() -> button.getStyleClass().clear());
                    Platform.runLater(() -> button.getStyleClass().add("button_circle_unactivated"));
                }
            } else {
                if (velocity > 0) {
                    Platform.runLater(() -> button.getStyleClass().clear());
                    Platform.runLater(() -> button.getStyleClass().add("button_square_showing"));
                } else {
                    Platform.runLater(() -> button.getStyleClass().clear());
                    Platform.runLater(() -> button.getStyleClass().add("button_square_unactivated"));
                }
            }
        } else if (Objects.equals(type, "CC")) {
            if (velocity > 0) {
                Platform.runLater(() -> button.getStyleClass().clear());
                Platform.runLater(() -> button.getStyleClass().add("button_circle_showing"));
            } else {
                Platform.runLater(() -> button.getStyleClass().clear());
                Platform.runLater(() -> button.getStyleClass().add("button_circle_unactivated"));
            }
        }
    }

    public void Menu_Create() {
        Label newLabel = new Label("Menu " + menu.getItems().size());
        Platform.runLater(() -> menu.getItems().add(newLabel));
        Platform.runLater(() -> newLabel.getStyleClass().add("menu_button"));
    }
    public void Button_Reset() {
        Label newLabel = new Label("Default");
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Warning !");
        alert.setContentText("You are about to erase all datas !");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(cancelButton, okButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == okButton) {
            System.out.println("OK");
            Platform.runLater(() -> menu.getItems().clear());
            Platform.runLater(() -> menu.getItems().add(newLabel));
            Platform.runLater(() -> newLabel.getStyleClass().add("menu_button"));
        } else if (result.isPresent() && result.get() == cancelButton) {
            System.out.println("Cancel");
            return;
        }
    }
}
