import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
public class Controller {
    @FXML
    private Label myLabel; // Élément FXML injecté
    private Stage primaryStage; // Fenêtre principale
    private VBox root; // Racine du fichier FXML
    // Constructeur : reçoit le stage (fenêtre principale)
    public Controller(Stage stage) {
        this.primaryStage = stage;
    }
    // Méthode pour charger l'interface et l'afficher
    public void display() {
        try {
            // Chargement du fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/ui.fxml"));
            loader.setController(this); // Associe ce contrôleur au fichier FXML
            // Charge la racine du fichier FXML
            root = loader.load();
            // Création de la scène
            Scene scene = new Scene(root);
            // Configuration et affichage de la fenêtre
            primaryStage.setTitle("Interface JavaFX");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Méthode pour changer le texte dans l'interface
    public void changerTexte(String nouveauTexte) {
        myLabel.setText(nouveauTexte); // Modification du texte du Label
    }
    // Initialisation après chargement des éléments FXML
    @FXML
    public void initialize() throws InterruptedException {
        // Ici, vous pouvez initialiser des éléments ou modifier l'interface après le chargement
        myLabel.setText("Texte par défaut");
        Thread.sleep(2000);
        myLabel.setText("Texte modifié");
    }
}