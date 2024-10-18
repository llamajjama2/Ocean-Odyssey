/**
 * Represents the settings menu of a game, which allows the user to adjust various settings such as
 * sound and music volume, game resolution, brightness, and control sensitivity. This class provides
 * both getters and setters for these settings, enforcing appropriate ranges for each setting value.
 * <p>
 * Default settings are provided through an overload constructor, establishing a standard starting
 * point for user customization.
 * </p>
 *
 * @author Brian H, Laadi
 * @version 2.0
 */
public class Settings {
    private int soundLevel;
    private int musicLevel;
    private int resolutionLevel; // Assuming 0=low, 1=medium, 2=high - this could map to actual resolutions
    private int brightnessLevel;
    private int sensitivityLevel;
    /**
     * Creates a new Settings object with specified levels for sound, music, resolution, brightness,
     * and sensitivity. These settings control various aspects of the game's audio-visual and input response.
     *
     * @param soundLevel The initial volume level of sound effects (0 to 100).
     * @param musicLevel The initial volume level of background music (0 to 100).
     * @param resolutionLevel The initial resolution setting of the game (0=low, 1=medium, 2=high).
     * @param brightnessLevel The initial brightness level of the game (0 to 100).
     * @param sensitivityLevel The initial sensitivity level of game controls (0 to 100).
     */
    public Settings(int soundLevel, int musicLevel, int resolutionLevel, int brightnessLevel, int sensitivityLevel) {
        this.soundLevel = soundLevel;
        this.musicLevel = musicLevel;
        this.resolutionLevel = resolutionLevel;
        this.brightnessLevel = brightnessLevel;
        this.sensitivityLevel = sensitivityLevel;
    }
    public Settings() {
        this.soundLevel = 50;
        this.musicLevel = 50;
        this.resolutionLevel = 2;
        this.brightnessLevel = 50;
        this.sensitivityLevel = 75;
    }
    /**
     * Gets the volume level of sound effects.
     *
     * @return The volume level of sound effects.
     */
    public int getSoundLevel() {
        return soundLevel;
    }
    /**
     * Gets the volume level of background music.
     *
     * @return The volume level of background music.
     */
    public int getMusicLevel() {
        return musicLevel;
    }
    /**
     * Gets the resolution level of the game.
     *
     * @return The resolution level of the game.
     */
    public int getResolutionLevel() {
        return resolutionLevel;
    }
    /**
     * Gets the brightness level of the game.
     *
     * @return The brightness level of the game.
     */
    public int getBrightnessLevel() {
        return brightnessLevel;
    }
    /**
     * Gets the sensitivity level of the game.
     *
     * @return The sensitivity level of the game.
     */
    public int getSensitivityLevel() {
        return sensitivityLevel;
    }
    /**
     * Sets the volume level of sound effects.
     */
    public void setSoundLevel(int soundLevel) {
        if (soundLevel >= 0 && soundLevel <= 100) {
            this.soundLevel = soundLevel;
        }
    }
    /**
     * Sets the volume level of background music.
     */
    public void setMusicLevel(int musicLevel) {
        if (musicLevel >= 0 && musicLevel <= 100) {
            this.musicLevel = musicLevel;
        }
    }
    /**
     * Sets the resolution level of the game.
     */
    public void setResolutionLevel(int resolutionLevel) {
        if (resolutionLevel >= 0 && resolutionLevel <= 2) {
            this.resolutionLevel = resolutionLevel;
        }
    }
    /**
     * Sets the brightness level of the game.
     */
    public void setBrightnessLevel(int brightnessLevel) {
        if (brightnessLevel >= 0 && brightnessLevel <= 100) {
            this.brightnessLevel = brightnessLevel;
        }
    }
    /**
     * Sets the sensitivity level of the game.
     */
    public void setSensitivityLevel(int sensitivityLevel) {
        if (sensitivityLevel >= 0 && sensitivityLevel <= 100) {
            this.sensitivityLevel = sensitivityLevel;
        }
    }
}