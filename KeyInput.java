import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Handles keyboard input for different types of game controllers. This class extends
 * {@link KeyAdapter} to provide custom responses to key events. It is designed to work
 * with multiple types of game controllers, including single-player, multiplayer, and generic
 * game control interfaces.
 *
 * <p>Depending on the constructor used to create an instance of {@code KeyInput}, this handler
 * will forward key pressed and key released events to the appropriate game controller instance
 * provided at construction.</p>
 *
 * @author Justin, Brian
 * @version 4.0
 */
public class KeyInput extends KeyAdapter {

    private GameControllerMulti gameControllerMulti; // Handles key events for multiplayer controller
    private GameControl gameControl; // Handles key events for generic game control interface
    private GameControllerSingle gameControllerSingle; // Handles key events for single-player controller

    /**
     * Constructs a {@code KeyInput} instance that forwards key events to a multiplayer game controller.
     *
     * @param gameControllerMulti The multiplayer game controller to receive key events.
     */
    public KeyInput(GameControllerMulti gameControllerMulti) {
        this.gameControllerMulti = gameControllerMulti;
    }

    /**
     * Constructs a {@code KeyInput} instance that forwards key events to a generic game control interface.
     *
     * @param gameControl The generic game control interface to receive key events.
     */
    public KeyInput(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    /**
     * Constructs a {@code KeyInput} instance that forwards key events to a single-player game controller.
     *
     * @param gameControllerSingle The single-player game controller to receive key events.
     */
    public KeyInput(GameControllerSingle gameControllerSingle) {
        this.gameControllerSingle = gameControllerSingle;
    }

    /**
     * Called when a key is pressed. This method forwards the key event to the
     * appropriate game controller if it has been set.
     *
     * @param e The key event triggered by a key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameControllerMulti != null)
            gameControllerMulti.keyPressed(e);
        if (gameControl != null)
            gameControl.keyPressed(e);
        if (gameControllerSingle != null)
            gameControllerSingle.keyPressed(e);
    }

    /**
     * Called when a key is released. This method forwards the key event to the
     * appropriate game controller if it has been set.
     *
     * @param e The key event triggered by a key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (gameControllerMulti != null)
            gameControllerMulti.keyReleased(e);
        if (gameControl != null)
            gameControl.keyReleased(e);
        if (gameControllerSingle != null)
            gameControllerSingle.keyReleased(e);
    }
}
