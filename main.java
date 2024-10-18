import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * The main class serves as the entry point for the application.
 * It initializes the game by attempting to load saved game data before
 * presenting the login screen to the user. If there is an issue loading
 * the saved data, it captures and prints the exception message to the console.
 * @author Micheal, Brian 
 * @version 2.0 
 */

public class main {
    /**
     * The main method that serves as the entry point of the application.
     * It first attempts to load any saved game data. If successful, it proceeds
     * to initialize and display the login screen to the user. In the case of an
     * IOException during data loading, the error message is printed to the console.
     *
     * @param args The command line arguments passed to the program.
     */
    public static void main(String[] args) {
        checkAndCreateFile("saveData.json");
        checkAndCreateFile("config.json");
        try {
            Save.loadData();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        login loginScreen = new login();
    }
    private static void checkAndCreateFile(String filename) {
        Path filePath = Paths.get(filename);
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
                if (filename.equals("config.json")) {
                    String content = "{\"levels\":[{\"density\":10,\"level\":1,\"speedMultiplier\":10,\"length\":58000,\"initialSpeed\":1},{\"density\":10,\"level\":2,\"speedMultiplier\":13,\"length\":61500,\"initialSpeed\":1},{\"density\":10,\"level\":3,\"speedMultiplier\":16,\"length\":65000,\"initialSpeed\":1},{\"density\":10,\"level\":4,\"speedMultiplier\":19,\"length\":68500,\"initialSpeed\":1},{\"density\":10,\"level\":5,\"speedMultiplier\":22,\"length\":72000,\"initialSpeed\":1},{\"density\":8,\"level\":6,\"speedMultiplier\":13,\"length\":60000,\"initialSpeed\":1},{\"density\":8,\"level\":7,\"speedMultiplier\":16,\"length\":63500,\"initialSpeed\":1},{\"density\":8,\"level\":8,\"speedMultiplier\":19,\"length\":67000,\"initialSpeed\":1},{\"density\":8,\"level\":9,\"speedMultiplier\":22,\"length\":70500,\"initialSpeed\":1},{\"density\":8,\"level\":10,\"speedMultiplier\":25,\"length\":74000,\"initialSpeed\":1},{\"density\":6,\"level\":11,\"speedMultiplier\":16,\"length\":62000,\"initialSpeed\":1},{\"density\":6,\"level\":12,\"speedMultiplier\":19,\"length\":65500,\"initialSpeed\":1},{\"density\":6,\"level\":13,\"speedMultiplier\":22,\"length\":69000,\"initialSpeed\":1},{\"density\":6,\"level\":14,\"speedMultiplier\":25,\"length\":72500,\"initialSpeed\":1},{\"density\":6,\"level\":15,\"speedMultiplier\":28,\"length\":76000,\"initialSpeed\":1},{\"density\":4,\"level\":16,\"speedMultiplier\":19,\"length\":64000,\"initialSpeed\":1},{\"density\":4,\"level\":17,\"speedMultiplier\":22,\"length\":67500,\"initialSpeed\":1},{\"density\":4,\"level\":18,\"speedMultiplier\":25,\"length\":71000,\"initialSpeed\":1},{\"density\":4,\"level\":19,\"speedMultiplier\":28,\"length\":74500,\"initialSpeed\":1},{\"density\":4,\"level\":20,\"speedMultiplier\":31,\"length\":78000,\"initialSpeed\":1},{\"density\":1,\"level\":21,\"speedMultiplier\":10,\"length\":58000,\"initialSpeed\":1},{\"density\":1,\"level\":22,\"speedMultiplier\":12,\"length\":62333,\"initialSpeed\":1},{\"density\":1,\"level\":23,\"speedMultiplier\":14,\"length\":66666,\"initialSpeed\":1},{\"density\":1,\"level\":24,\"speedMultiplier\":16,\"length\":71000,\"initialSpeed\":1},{\"density\":1,\"level\":25,\"speedMultiplier\":18,\"length\":75333,\"initialSpeed\":1},{\"density\":1,\"level\":26,\"speedMultiplier\":20,\"length\":79666,\"initialSpeed\":1},{\"density\":1,\"level\":27,\"speedMultiplier\":22,\"length\":84000,\"initialSpeed\":1},{\"density\":1,\"level\":28,\"speedMultiplier\":24,\"length\":88333,\"initialSpeed\":1},{\"density\":1,\"level\":29,\"speedMultiplier\":26,\"length\":92666,\"initialSpeed\":1},{\"density\":1,\"level\":30,\"speedMultiplier\":28,\"length\":97000,\"initialSpeed\":1}]}";
                    try {
                        Files.write(filePath, content.getBytes());
                    } catch (IOException e) {
                        System.out.println("Error writing to file " + filename + ": " + e.getMessage());
                    }
                } else if (filename.equals("saveData.json")) {
                    String content = "{\"settings\":[80,60,1,70,75],\"leaderboard\":[{\"score\":97011,\"playerName\":\"dwai\"}],\"users\":[{\"skins\":[{\"cost\":0,\"name\":\"Pirate.png\",\"rarity\":\"Common\"},{\"cost\":0,\"name\":\"Steampunk.png\",\"rarity\":\"Common\"},{\"cost\":0,\"name\":\"NeonGlow.png\",\"rarity\":\"Common\"},{\"cost\":0,\"name\":\"AquaticExplorer.png\",\"rarity\":\"Common\"}],\"maxLevel\":1,\"password\":\"instructor\",\"highScore\":0,\"currentSkin\":{\"cost\":0,\"name\":\"AquaticExplorer.png\",\"rarity\":\"Common\"},\"achievements\":{\"achievements\":\"Null\"},\"email\":\"Null\",\"username\":\"instructor\"},{\"skins\":[{\"cost\":0,\"name\":\"Pirate.png\",\"rarity\":\"Common\"},{\"cost\":0,\"name\":\"Steampunk.png\",\"rarity\":\"Common\"},{\"cost\":0,\"name\":\"NeonGlow.png\",\"rarity\":\"Common\"},{\"cost\":0,\"name\":\"AquaticExplorer.png\",\"rarity\":\"Common\"},{\"cost\":0,\"name\":\"Titanic.png\",\"rarity\":\"Legendary\"}],\"maxLevel\":30,\"password\":\"debugger\",\"highScore\":149024,\"currentSkin\":{\"cost\":0,\"name\":\"Titanic.png\",\"rarity\":\"Legendary\"},\"achievements\":{\"achievements\":\"Null\"},\"email\":\"Null\",\"username\":\"debugger\"}]}";
                    try {
                        Files.write(filePath, content.getBytes());
                    } catch (IOException e) {
                        System.out.println("Error writing to file " + filename + ": " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.out.println("Error creating file " + filename + ": " + e.getMessage());
            }
        }
    }
}
