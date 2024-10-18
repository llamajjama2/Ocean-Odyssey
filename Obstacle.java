import java.awt.*;
import java.util.Random;
import java.awt.image.BufferedImage;

/**
 * Represents an {@code Obstacle} in the game. Each obstacle has characteristics like speed, type, and size
 * that affect its behavior and appearance in the game world. Obstacles are generated based on the game level
 * and can move across the screen at varying speeds.
 *
 * @author Haadi, Brian H
 * @version 3.0
 */
public class Obstacle {
    private double speed; //s The speed at which the obstacle moves
    private int type; // The type of obstacle, determining its appearance or behavior
    private int size; // The size of the obstacle
    private double x, y; // The position of the obstacle
    private BufferedImage image; // The image representing the obstacle
    private boolean isActive; // Indicates if the obstacle is active and should be updated/rendered

    /**
     * Constructs an {@code Obstacle} with properties based on the game level. The size and type
     * are randomly generated within ranges that depend on the level.
     *
     * @param level The game level, influencing the obstacle's characteristics.
     */
    public Obstacle(int level) {
        this.speed = 0;
        this.isActive = true;
        Random random = new Random();
        if (level < 10) {
            this.size = random.nextInt(6) + 5;
            this.type = 1;
        } else if (level < 20) {
            this.size = random.nextInt(10) + 5;
            this.type = random.nextInt(2) + 1;
        } else if (level < 30) {
            this.size = random.nextInt(14) + 5;
            this.type = random.nextInt(2) + 2;
        } else {
            this.size = random.nextInt(18) + 5;
            this.type = random.nextInt(4);
        }
    }
    /**
     * Constructs an {@code Obstacle} at a specified position with a given image and speed.
     * The size and type are randomly generated based on the game level.
     *
     * @param x The x-coordinate of the obstacle.
     * @param y The y-coordinate of the obstacle.
     * @param level The game level, influencing the obstacle's characteristics.
     * @param image The image representing the obstacle.
     * @param speed The speed at which the obstacle moves.
     */
    public Obstacle(double x, double y, int level, BufferedImage image, float speed) {
        this.speed = (double) speed;
        this.x = x;
        this.y = y;
        this.image = image;
        this.isActive = true;
        Random random = new Random();
        if (level < 10) {
            this.size = random.nextInt(10) + 5;
            this.type = 1;
        } else if (level < 20) {
            this.size = random.nextInt(12) + 5;
            this.type = random.nextInt(2) + 1;
        } else if (level < 30) {
            this.size = random.nextInt(14) + 5;
            this.type = random.nextInt(2) + 2;
        } else {
            this.size = random.nextInt(16) + 5;
            this.type = random.nextInt(4) + 1;
        }
    }
    /**
     * Updates the obstacle's position based on its speed. This method is called each tick to
     * move the obstacle if it is active.
     */
    public void tick() {
        if (isActive) {
            x += speed;
        }
    }

    /**
     * Renders the obstacle on the screen at its current position. The obstacle is drawn
     * only if it is active.
     *
     * @param g The Graphics context used for drawing the obstacle.
     */
    public void render(Graphics g) {
        if (isActive) {
            g.drawImage(image, (int)x, (int)y, 10 * size, 10 * size, null);
        }
    }
    /**
     * Checks if the obstacle is currently active.
     *
     * @return true if the obstacle is active and false otherwise.
     */
    public boolean isActive() {
        return isActive;
    }
    /**
     * Sets the active state of the obstacle.
     *
     * @param active true to activate the obstacle and false to deactivate it.
     */
    public void setActive(boolean active) {
        isActive = active;
    }
    /**
     * Sets the type of the obstacle.
     *
     * @param Type The new type of the obstacle.
     */
    public void setType(int Type) {
        this.type = Type;
    }
    /**
     * Sets the size of the obstacle.
     *
     * @param Size The new size of the obstacle.
     */
    public void setSize(int Size) {
        this.size = Size*2;
    }

    /**
     * Retrieves the type of the obstacle. The type may determine the obstacle's appearance or behavior within the game.
     *
     * @return An integer representing the type of the obstacle.
     */
    public int getType() {
        return this.type;
    }

    /**
     * Retrieves the size of the obstacle, scaled by a factor of 2. This may be used to determine the obstacle's
     * dimensions in the game world.
     *
     * @return An integer representing the scaled size of the obstacle.
     */
    public int getSize() {
        return this.size * 2;
    }

    /**
     * Retrieves the x-coordinate of the obstacle's position. This indicates the horizontal position of the obstacle
     * within the game world.
     *
     * @return A double representing the x-coordinate of the obstacle.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the bounding box of the obstacle, useful for collision detection.
     *
     * @return A Rectangle representing the obstacle's bounding box.
     */
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, size*8, size*8);
    }
}