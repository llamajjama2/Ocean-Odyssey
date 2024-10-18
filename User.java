import java.util.List;
import java.util.ArrayList;

/**
 * Represents a user of the game, including their personal information, game statistics,
 * and skins they have acquired.
 *
 * @author Haadi
 * @version 3.0
 */
public class User {
    private String username;
    private String password;
    private String email;
    private int highScore;
    private List<Skin> skins;
    private Skin currentSkin;
    private int maxLevel;

    /**
     * Constructs a User with basic information and default skins.
     *
     * @param username The user's username.
     * @param password The user's password.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.email = "Null";
        this.highScore = 0; // Initial high score
        this.skins = new ArrayList<Skin>();
        this.skins.add(new Skin("Pirate.png", 0, "Common")); // Default skin
        this.skins.add(new Skin("Steampunk.png", 0, "Common")); // Default skin
        this.skins.add(new Skin("NeonGlow.png", 0, "Common")); // Default skin
        this.skins.add(new Skin("AquaticExplorer.png", 0, "Common")); // Default skin
        this.maxLevel = 1;
        this.currentSkin = new Skin("Pirate.png", 0, "Common");
    }
    /**
     * Constructs a User with basic information, default skins, and a specified current skin.
     *
     * @param username The user's username.
     * @param password The user's password.
     * @param currentSkin The user's current selected skin.
     */
    public User(String username, String password, Skin currentSkin) {
        this.username = username;
        this.password = password;
        this.email = "Null";
        this.highScore = 0; // Initial high score
        this.skins = new ArrayList<Skin>();
        this.skins.add(new Skin("Pirate.png", 0, "Common")); // Default skin
        this.skins.add(new Skin("Steampunk.png", 0, "Common")); // Default skin
        this.skins.add(new Skin("NeonGlow.png", 0, "Common")); // Default skin
        this.skins.add(new Skin("AquaticExplorer.png", 0, "Common")); // Default skin
        this.maxLevel = 1;
        this.currentSkin = currentSkin;
    }
    /**
     * Gets the user's username.
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the user's password.
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the user's email address.
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the user's high score.
     * @return The high score of the user.
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Gets the list of skins owned by the user.
     * @return A list of {@link Skin} objects representing the skins owned by the user.
     */
    public List<Skin> getSkins() {
        return skins;
    }

    /**
     * Gets the maximum level reached by the user.
     * @return The maximum level reached by the user.
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Sets the user's high score to a new value.
     * @param highScore The new high score to set.
     */
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    /**
     * Adds a new skin to the user's collection if it is not already owned.
     * @param skin The {@link Skin} to add to the user's collection.
     */
    public void addSkin(Skin skin) {
        for (Skin s : skins) {
            if (s.getName().equals(skin.getName())) {
                return; // Skin already exists
            }
        }
        skins.add(skin);
    }

    /**
     * Sets the user's maximum level to a new value.
     * @param maxLevel The new maximum level to set.
     */
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     * Sets the user's current skin to a new {@link Skin}.
     * @param skin The new {@link Skin} to set as the user's current skin.
     */
    public void setCurrentSkin(Skin skin) {
        this.currentSkin = skin;
    }

    /**
     * Gets the user's current skin.
     * @return The current {@link Skin} of the user.
     */
    public Skin getCurrentSkin() {
        return currentSkin;
    }

}
