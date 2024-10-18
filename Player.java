import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents the player character in the game. This class manages the player's position,
 * movement, and rendering on the game screen.
 *
 * @author Brian H, Brian Y, Haadi, Michael
 * @version 6.0
 */
public class Player {
    private double x; // The x-coordinate of the player's position.
    private double y; // The y-coordinate of the player's position.
    private BufferedImage image; // The image used to represent the player.
    private int width; // The width of the player's image.
    private int height; // The height of the player's image.
    private double velX = 0; // The horizontal velocity of the player.
    private double velY = 0; // The vertical velocity of the player.

    /**
     * Constructs a new Player object at specified coordinates with a given image.
     *
     * @param x The initial x-coordinate of the player.
     * @param y The initial y-coordinate of the player.
     * @param image The image to represent the player on the screen.
     */
    public Player(double x, double y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = image.getWidth();  // Adjust the scaling factor as needed
        this.height = image.getHeight();  // Adjust the scaling factor as needed
    }

    /**
     * Updates the player's position based on its current velocity. This method also
     * constrains the player's movement within the game area.
     */
    public void tick() {
        int sensitivity = Save.getSettings().getSensitivityLevel();
        x += velX/150 * sensitivity;
        y += velY/113 * sensitivity;

        if (x <= 0) x = 0;
        if (y <= 100) y = 100;
        if (x >= GameControl.WIDTH - width + 50) x = GameControl.WIDTH - width + 50;
        if (y >= GameControl.HEIGHT - height) y = GameControl.HEIGHT - height;
    }

    /**
     * Renders the player's image at its current position on the game screen.
     *
     * @param g The Graphics context used for drawing the player.
     */
    public void render(Graphics g) {
        g.drawImage(image, (int)x, (int)y, width, height, null);
    }

    /**
     * Gets the player's current x-coordinate.
     *
     * @return The x-coordinate of the player.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the player's current y-coordinate.
     *
     * @return The y-coordinate of the player.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the player's x-coordinate.
     *
     * @param x The new x-coordinate for the player.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the player's y-coordinate.
     *
     * @param y The new y-coordinate for the player.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Sets the horizontal velocity of the player.
     *
     * @param velX The new horizontal velocity for the player.
     */
    public void setVelX(double velX) {
        this.velX = velX;
    }

    /**
     * Sets the vertical velocity of the player.
     *
     * @param velY The new vertical velocity for the player.
     */
    public void setVelY(double velY) {
        this.velY = velY;
    }

    /**
     * Returns the player's bounding box, useful for collision detection.
     *
     * @return A Rectangle that represents the player's current bounding box.
     */
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    /**
     * Resets the player's position to a predefined starting location and sets
     * the player's velocity to zero. This is useful for restarting the game or
     * after the player loses a life.
     */
    public void resetPosition() {
        this.x = 1000;
        this.y = 250;
        this.velX = 0;
        this.velY = 0;
    }


}