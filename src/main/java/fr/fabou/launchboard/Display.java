package fr.fabou.launchboard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class Display extends Application {
    @Override
    public void start(@NotNull Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/assets/ui.fxml"));

        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo.png"))));
        root.setStyle("-fx-background-color: #363636;");
        Scene scene = new Scene(root, 900, 500);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("LaunchBoard");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}