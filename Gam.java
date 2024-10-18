import javax.swing.*;
import java.awt.*;

/**
 * The {@code Gam} class extends {@link JFrame} and serves as the main window
 * for the "Ocean Odyssey" game. This class is responsible for initializing
 * the game window, setting its properties, and drawing a background image
 * to create a thematic atmosphere for the game.
 * <p>
 * The game window is set to a predefined size and includes a custom
 * {@link JPanel} as its content pane to allow for the drawing of a
 * background image. The window is configured to exit the application
 * upon closure.
 * </p>
 *
 * @author Justin
 * @version 3.0
 */
public class Gam extends JFrame {

    /**
     * Constructs a new game window with a title, size, and custom
     * background image. The window is centered on the screen and
     * configured to terminate the application when closed.
     */
    public Gam() {
        setTitle("Ocean Odyssey");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1264, 700);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("Ocean background.jpg"));
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());
        setVisible(true);
    }

    /**
     * The main method to run the game window. This serves as the
     * entry point for the "Ocean Odyssey" game application.
     *
     * @param args Command line argument.
     */
    public static void main(String[] args) {
        new Gam();
    }
}
