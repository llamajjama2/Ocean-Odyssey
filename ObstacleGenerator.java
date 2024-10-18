import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the generation and updating of obstacles in the game. This includes spawning obstacles at
 * appropriate intervals based on the game level's density, updating their positions, and rendering them
 * on the screen.
 *
 * @author Haadi, Brian H, Michael
 * @version 4.0
 */
public class ObstacleGenerator {
    private ArrayList<Obstacle> obstacles; // List of active obstacles
    private Levels level; // Current game level
    private int spawnCounter; // Counter to track when to spawn the next obstacle
    private BufferedImage obstacleImage; // Image used for obstacles

    /**
     * Constructs an {@code ObstacleGenerator} with a specific game level and obstacle image.
     *
     * @param level The current level of the game, affecting obstacle generation.
     * @param obstacleImage The image to use for each obstacle.
     */
    public ObstacleGenerator(Levels level, BufferedImage obstacleImage) {
        this.level = level;
        this.obstacleImage = obstacleImage;
        this.obstacles = new ArrayList<>();
        this.spawnCounter = 0;
    }

    /**
     * Updates the state of the obstacle generator each game tick. This includes spawning new obstacles
     * when appropriate and updating the positions of existing obstacles.
     *
     * @param speed The current speed of the game, affecting obstacle movement.
     */
    public void tick(float speed) {
        spawnCounter++;
        if (spawnCounter >= calculateSpawnFrequency()) {
            spawnObstacle(speed);
            spawnCounter = 0;
        }
        updateObstacles();
    }

    /**
     * Calculates the frequency at which obstacles should be spawned based on the level's density.
     *
     * @return An integer representing the number of ticks between spawns.
     */
    private int calculateSpawnFrequency() {
        return 500 + level.getDensity() * 100; //ADJUST ACCORDINGLY
    }

    /**
     * Spawns a new obstacle with specified speed and adds it to the list of active obstacles.
     *
     * @param speed The speed at which the new obstacle will move.
     */
    private void spawnObstacle(float speed) {
        // Calculate position and size based on level settings
        BufferedImage lol = null;
        try {
            lol = ImageIO.read(getClass().getResource("db.png"));
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
        double y = Math.random() * (GameControl.HEIGHT - 30);  // ADJUST ACCORDINGLY
        Obstacle obstacle = new Obstacle(-100, y, level.getLevel(), lol, speed);
        obstacles.add(obstacle);
    }

    /**
     * Updates the positions of all active obstacles and removes any that have moved off-screen.
     */
    private void updateObstacles() {
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.tick();
            if (obstacle.getX() > GameControl.WIDTH) {
                obstacles.remove(i);
                i--;
            }
        }
    }

    /**
     * Renders all active obstacles on the screen.
     *
     * @param g The Graphics context used for drawing the obstacles.
     */
    public void render(Graphics g) {
        for (Obstacle obstacle : obstacles) {
            obstacle.render(g);
        }
    }
    /**
     * Returns a list of all currently active obstacles.
     *
     * @return A list containing all active obstacles.
     */
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    /**
     * Clears all obstacles from the game and resets the spawn counter. This can be used when starting
     * a new level or restarting the current level.
     */
    public void resetObstacles() {
        obstacles.clear();
        spawnCounter = 0;
    }

}