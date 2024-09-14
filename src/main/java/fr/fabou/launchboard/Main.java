package fr.fabou.launchboard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import javax.sound.midi.ShortMessage;

public class Main extends Application {

    private LaunchpadListener launchpadListener;
    private Controller controller;

    public void start(Stage primaryStage) throws Exception {
        System.out.println("start");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/ui.fxml"));
        Parent root = loader.load();

        // Obtenir l'instance du contrôleur
        controller = loader.getController();

        // Initialiser le listener pour le Launchpad
        launchpadListener = new LaunchpadListener() {
            @Override
            public void changeButtonColor() {
                // Appel à la méthode dans le contrôleur pour changer la couleur
                System.out.println("change");
                controller.setSq0ColorBlue();
            }
        };
    }

    public static void main(String[] args) {
        System.out.println("main");
        Display.main(args);

    }
}