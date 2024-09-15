import javafx.application.Application;
import javafx.stage.Stage;
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Création d'une instance du contrôleur et passage du stage
        Controller controleur = new Controller(primaryStage);
        // Affichage de l'interface via la méthode display()
        controleur.display();
    }
    public static void main(String[] args) {
        launch(args);
    }
}