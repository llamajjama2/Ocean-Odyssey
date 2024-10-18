/**
 * Represents a level in the game, including its difficulty settings, obstacles,
 * and other characteristics that define how the level plays.
 *
 * @author Haadi
 * @version 2.0
 */
public class Levels {
    private int level; // The level number
    private Obstacle[] obstacles; // Array of obstacles within the level
    private int initialSpeed; // The initial speed of the game at this level
    private int speedMultiplier; // A multiplier affecting the speed increase over time
    private int density; // The density of obstacles within the level
    private int length; // The length of the level

    /**
     * Constructs a new {@code Levels} object with specified characteristics.
     *
     * @param level The level number.
     * @param initialSpeed The initial speed at which the game starts in this level.
     * @param speedMultiplier The speed multiplier affecting how speed increases over time in this level.
     * @param density The density of obstacles, affecting how many obstacles are present.
     * @param length The length of the level, which can determine its duration.
     */
    public Levels(int level, int initialSpeed, int speedMultiplier, int density, int length) {
        this.level = level;
        this.initialSpeed = initialSpeed;
        this.speedMultiplier = speedMultiplier;
        this.density = density;
        this.length = length;

        // Initialize obstacles based on level characteristics
        int temp = length / 10 * density;
        this.obstacles = new Obstacle[temp];
        for (int i = 0; i < temp; i++) {
            obstacles[i] = new Obstacle(level); // Example instantiation, assumes Obstacle constructor takes level as parameter
        }
    }
    /**
     * Gets the level number.
     *
     * @return The level number.
     */
    public int getLevel() { return level; }

    /**
     * Gets the array of obstacles within the level.
     *
     * @return An array of {@code Obstacle} objects.
     */
    public Obstacle[] getObstacles() { return obstacles; }

    /**
     * Gets the initial speed of the game at this level.
     *
     * @return The initial speed.
     */
    public int getInitialSpeed() { return initialSpeed; }

    /**
     * Gets the speed multiplier for the level.
     *
     * @return The speed multiplier.
     */
    public int getSpeedMultiplier() { return speedMultiplier; }

    /**
     * Gets the density of obstacles within the level.
     *
     * @return The obstacle density.
     */
    public int getDensity() { return density; }

    /**
     * Gets the length of the level.
     *
     * @return The level length.
     */
    public int getLength() { return length; }

    /**
     * Sets the level number.
     *
     * @param level The new level number.
     */
    public void setLevel(int level) { this.level = level; }

    /**
     * Sets the array of obstacles for the level.
     *
     * @param obstacles An array of {@code Obstacle} objects.
     */
    public void setObstacles(Obstacle[] obstacles) { this.obstacles = obstacles; }

    /**
     * Sets the initial speed for the level.
     *
     * @param initialSpeed The new initial speed.
     */
    public void setInitialSpeed(int initialSpeed) { this.initialSpeed = initialSpeed; }

    /**
     * Sets the speed multiplier for the level.
     *
     * @param speedMultiplier The new speed multiplier.
     */
    public void setSpeedMultiplier(int speedMultiplier) { this.speedMultiplier = speedMultiplier; }

    /**
     * Sets the density of obstacles in the level.
     *
     * @param density The new obstacle density.
     */
    public void setDensity(int density) { this.density = density; }

    /**
     * Sets the length of the level.
     *
     * @param length The new level length.
     */
    public void setLength(int length) { this.length = length; }
}
