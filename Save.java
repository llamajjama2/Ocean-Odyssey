import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Manages the persistence of game data, including settings, user information,
 * and leaderboard scores, by saving to and loading from a JSON file.
 * This class provides static methods to save all current game data to a file
 * and load it back into the application upon startup or when needed.
 *
 * @author Haadi
 * @version 4.0
 */
public class Save {
    private static Settings settings; // Game settings
    private static List<User> users; // List of users
    private static User currentUser; // Currently active user
    private static Leaderboard_B leaderboard; // Leaderboard data
    private static JSONObject saveData; // JSON object for compiling save data

    /**
     * Constructs a new Save instance, initializing it with game data.
     *
     * @param settings The game settings to be saved.
     * @param users The list of users to be saved.
     * @param leaderboard The game leaderboard to be saved.
     */
    public Save(Settings settings, List<User> users, Leaderboard_B leaderboard) {
        Save.settings = settings;
        Save.currentUser = null;
        Save.users = users;
        Save.leaderboard = leaderboard;
        Save.saveData = new JSONObject();
    }
    /**
     * Default constructor for the Save class.
     * Initializes a new Save instance with default values.
     */
    public Save() {
        settings = new Settings();
        currentUser = null;
        users = new ArrayList<User>();
        leaderboard = new Leaderboard_B();
        saveData = new JSONObject();
    }

    /**
     * Saves the current state of game data, including settings, users, and leaderboard
     * scores to a JSON file named "saveData.json".
     *
     * @throws IOException If an I/O error occurs during saving.
     */
    public static void saveData() throws IOException {
        JSONArray settingsArray = new JSONArray();
        settingsArray.put(settings.getSoundLevel());
        settingsArray.put(settings.getMusicLevel());
        settingsArray.put(settings.getResolutionLevel());
        settingsArray.put(settings.getBrightnessLevel());
        settingsArray.put(settings.getSensitivityLevel());
        saveData.put("settings", settingsArray);
        JSONArray userArray = new JSONArray(users);
        saveData.put("users", userArray);
        JSONArray leaderboardArray = new JSONArray();
        for (Score score : leaderboard.getScores()) {
            JSONObject scoreObject = new JSONObject();
            scoreObject.put("playerName", score.getPlayerName());
            scoreObject.put("score", score.getScore());
            leaderboardArray.put(scoreObject);
        }
        saveData.put("leaderboard", leaderboardArray);
        // Write the JSON object to a file
        try (FileWriter file = new FileWriter("saveData.json")) {
            file.write(saveData.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Loads game data from a JSON file named "saveData.json" into the application,
     * restoring settings, user information, and leaderboard scores.
     *
     * @throws IOException If an I/O error occurs during loading.
     */
    public static void loadData() throws IOException {
        // Read the JSON file
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get("saveData.json")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        JSONObject saveData = new JSONObject(data);
        // Extract the data from the JSON object
        JSONArray settingsArray = saveData.getJSONArray("settings");
        Settings settings = new Settings(settingsArray.getInt(0), settingsArray.getInt(1), settingsArray.getInt(2), settingsArray.getInt(3), settingsArray.getInt(4));
        JSONArray userArray = saveData.getJSONArray("users");
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < userArray.length(); i++) {
            JSONObject userObject = userArray.getJSONObject(i);
            JSONObject currentSkinObject = userObject.getJSONObject("currentSkin");
            User user = new User(userObject.getString("username"), userObject.getString("password"), new Skin(currentSkinObject.getString("name"), currentSkinObject.getInt("cost"), currentSkinObject.getString("rarity")));
            user.setMaxLevel(userObject.getInt("maxLevel"));
            user.setHighScore(userObject.getInt("highScore"));
            JSONArray skinsArray = userObject.getJSONArray("skins");
            for (int j = 0; j < skinsArray.length(); j++) {
                JSONObject skinObject = skinsArray.getJSONObject(j);
                user.addSkin(new Skin(skinObject.getString("name"), skinObject.getInt("cost"), skinObject.getString("rarity")));
            }
            users.add(user);
        }
        JSONArray leaderboardArray = saveData.getJSONArray("leaderboard");
        Leaderboard_B leaderboard = new Leaderboard_B();
        for (int i = 0; i < leaderboardArray.length(); i++) {
            JSONObject scoreObject = leaderboardArray.getJSONObject(i);
            String playerName = scoreObject.getString("playerName");
            int score = scoreObject.getInt("score");
            leaderboard.addScore(playerName, score);
        }
        Save.setLeaderboard(leaderboard);
        Save.setUsers(users);
        Save.setSettings(settings);
        Save.setLeaderboard(leaderboard);
        Save.saveData = new JSONObject();
    }
    /**
     * Gets the game settings.
     *
     * @return The current game settings.
     */
    public static Settings getSettings() {
        return settings;
    }
    /**
     * Gets the game settings.
     *
     * @return The current game settings.
     */
    public static User getUser() {
        return currentUser;
    }
    /**
     * Gets the game leaderboard.
     *
     * @return The current game leaderboard.
     */
    public static Leaderboard_B getLeaderboard() {
        return leaderboard;
    }
    /**
     * Sets the game settings.
     *
     * @param settings The new game settings.
     */
    public static void setSettings(Settings settings) {
        Save.settings = settings;
    }
    /**
     * Sets the list of users.
     *
     * @param users The new list of users.
     */
    public static void setUsers(List<User> users) {
        Save.users = users;
    }
    /**
     * Sets the current user.
     *
     * @param user The user to be set as the current user.
     */
    public static void setUser(User user) {
        Save.currentUser = user;
    }
    /**
     * Sets the game leaderboard.
     *
     * @param leaderboard The new game leaderboard.
     */
    public static void setLeaderboard(Leaderboard_B leaderboard) {
        Save.leaderboard = leaderboard;
    }
    /**
     * Adds a user to the list of users.
     *
     * @param user The user to add.
     */
    public static void addUser (User user) {
        users.add(user);
    }

    /**
     * Gets the list of users.
     *
     * @return The list of users.
     */
    public static List<User> getUsers() {
        return users;
    }
}
