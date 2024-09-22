package fr.fabou.launchboard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Menu_Controller {
    private static final String PROGRAM_DATA = System.getenv("ProgramData");
    private static final String LAUNCHBOARD_FOLDER = PROGRAM_DATA + File.separator + "LaunchBoard";
    private static final String USER1_FILE = LAUNCHBOARD_FOLDER + File.separator + "user1.json";
    private static final String USER2_FILE = LAUNCHBOARD_FOLDER + File.separator + "user2.json";

    private Gson gson = new Gson();

    public void createLaunchBoardDirectory() {
        Path path = Paths.get(LAUNCHBOARD_FOLDER);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path); // Crée le dossier LaunchBoard dans ProgramData
                System.out.println("Dossier LaunchBoard créé à l'emplacement : " + LAUNCHBOARD_FOLDER);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> loadPresets(String PRESET_FILE) {
        this.createLaunchBoardDirectory();
        File user = new File(PRESET_FILE);
        if (!user.exists()) {
            System.out.println("Aucun fichier de presets trouvé.");
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(PRESET_FILE)) {
            Type presetListType = new TypeToken<ArrayList<User>>(){}.getType();
            return gson.fromJson(reader, presetListType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
