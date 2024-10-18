import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * A customized JButton that displays an ImageIcon in its center.
 * This button overrides the default button appearance and functionality
 * to create a more customized user experience. The button's clickable area
 * is defined by the bounds of the ImageIcon, allowing for more precise
 * interaction based on the icon's dimensions.
 *
 * <p>This class extends {@link JButton} and utilizes {@link ImageIcon} for
 * rendering an image in the center of the button. The {@code contains} method
 * is overridden to ensure that clicks are only registered when they occur
 * within the bounds of the ImageIcon, providing a tailored clickable area.</p>
 *
 * @author Haadi, Justin
 * @version 4.0
 *
 */
class CustomButton extends JButton {
    private ImageIcon icon; // The icon displayed on the button

    /**
     * Constructs a CustomButton with a specified ImageIcon.
     * The button is initialized with no border for a cleaner appearance,
     * focusing the user's attention on the icon itself.
     *
     * @param icon The {@link ImageIcon} to display on this button.
     */
    public CustomButton(ImageIcon icon) {
        this.icon = icon;
        setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * Determines whether a specified point is within the clickable area of the button.
     * The clickable area is defined as the area occupied by the ImageIcon.
     * This method is useful for determining if a mouse click occurred within the bounds
     * of the ImageIcon, rather than just anywhere on the button's surface.
     *
     * @param x The x-coordinate of the point to check.
     * @param y The y-coordinate of the point to check.
     * @return {@code true} if the point is within the bounds of the ImageIcon,
     *         {@code false} otherwise.
     */
    @Override
    public boolean contains(int x, int y) {
        int iconX = (getWidth() - icon.getIconWidth()) / 2;
        int iconY = (getHeight() - icon.getIconHeight()) / 2;
        return (x >= iconX && x <= iconX + icon.getIconWidth() && y >= iconY && y <= iconY + icon.getIconHeight());
    }

    /**
     * Paints the component by rendering the ImageIcon at the center of the button.
     * This method is called as part of the painting process and is responsible for
     * drawing the button's icon. It ensures the icon is visually centered on the
     * button's surface.
     *
     * @param g The {@link Graphics} context used for painting the button.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (getWidth() - icon.getIconWidth()) / 2;
        int y = (getHeight() - icon.getIconHeight()) / 2;
        icon.paintIcon(this, g, x, y);
    }
}
