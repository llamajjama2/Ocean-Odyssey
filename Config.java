import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The Config class is responsible for managing the configuration of levels in the game.
 * It supports adding and removing levels, and loading/storing these configurations to a JSON file.
 * This allows for dynamic configuration of game levels without needing to recompile the application.
 *
 * @author Haadi
 * @version 4.0
 */
public class Config {
    /**
     * A list of Levels that represents the game's levels configurations.
     */
    private List<Levels> levels;

    /**
     * Constructs a new Config instance with an empty list of levels.
     */
    public Config() {
        this.levels = new ArrayList<>();
    }

    /**
     * Adds a new level configuration to the list of levels.
     *
     * @param level The levels object representing the level configuration to add.
     */
    public void addLevel(Levels level) {
        this.levels.add(level);
    }

    /**
     * Removes a level configuration from the list of levels.
     *
     * @param level The levels object representing the level configuration to remove.
     */
    public void removeLevel(Levels level) {
        this.levels.remove(level);
    }

    /**
     * Retrieves the list of level configurations.
     *
     * @return A list containing all the level configurations.
     */
    public List<Levels> getLevels() {
        return this.levels;
    }

    /**
     * Stores the current level configurations into a JSON file named "config.json".
     * This method serializes the list of level configurations into JSON format and writes it to the file.
     *
     * @throws IOException If there is an error writing to the file.
     */
    public void storeConfig() throws IOException {
        JSONObject config = new JSONObject();
        JSONArray levelsArray = new JSONArray();
        for (Levels level : levels) {
            JSONObject levelObject = new JSONObject();
            levelObject.put("level", level.getLevel());
            levelObject.put("initialSpeed", level.getInitialSpeed());
            levelObject.put("speedMultiplier", level.getSpeedMultiplier());
            levelObject.put("density", level.getDensity());
            levelObject.put("length", level.getLength());
            levelsArray.put(levelObject);
        }
        config.put("levels", levelsArray);
        try (FileWriter file = new FileWriter("config.json")) {
            file.write(config.toString());
        }
    }

    /**
     * Loads the level configurations from a JSON file named "config.json".
     * This method reads the file, parses the JSON, and populates the list of level configurations accordingly.
     *
     * @throws IOException If there is an error reading from the file.
     */
    public void loadConfig() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("config.json")));
        JSONObject config = new JSONObject(content);
        JSONArray levelsArray = config.getJSONArray("levels");
        for (int i = 0; i < levelsArray.length(); i++) {
            JSONObject levelObject = levelsArray.getJSONObject(i);
            Levels level = new Levels(levelObject.getInt("level"), levelObject.getInt("initialSpeed"), levelObject.getInt("speedMultiplier"), levelObject.getInt("density"), levelObject.getInt("length"));
            this.addLevel(level);
        }
    }
}


/**
public static void main(String[] args) {
    Config config = new Config();
    Levels level1 = new Levels(1, 10, 2, 5, 100);
    Levels level2 = new Levels(2, 15, 3, 7, 150);
    config.addLevel(level1);
    config.addLevel(level2);
    try {
        config.storeConfig();
        config.loadConfig();
        for(Levels level : config.getLevels()){
            System.out.println("Levels: " + level.getLevel());
            System.out.println("Initial Speed: " + level.getInitialSpeed());
            System.out.println("Speed Multiplier: " + level.getSpeedMultiplier());
            System.out.println("Density: " + level.getDensity());
            System.out.println("Length: " + level.getLength());
        }
    } catch (IOException e) {
        System.out.println(e.getMessage());
    }
}
 */

