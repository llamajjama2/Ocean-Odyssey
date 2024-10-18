import javax.swing.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@code easterEgg} represents a special, hidden feature within an application,
 * usually displayed as a modal dialog. This class extends {@link JDialog} to
 * show a custom graphical pop-up, potentially including images and a close button,
 * when an easter egg is triggered within the application.
 * <p>
 * The dialog is undecorated and contains custom imagery, including a title image,
 * a main image (e.g., the Titanic), and a custom button to close the dialog. The
 * images are loaded from specified file paths, and their placement is manually
 * adjusted within the dialog's panel.
 * </p>
 *
 * @author Brian
 * @version 3.0
 */
public class easterEgg extends JDialog {
    private int width = 600; // The width of the dialog.
    private int length = 500; // The height of the dialog.

    private ImageIcon unlockImage; // ImageIcon for the title.
    private ImageIcon closeImage; // ImageIcon for the close button.
    private ImageIcon titanic; // ImageIcon for the main image displayed in the dialog.

    private JButton closeButton; // The button to close the dialog.

    /**
     * Constructs an {@code easterEgg} dialog relative to the parent frame.
     * Initializes the dialog with specified images and a custom layout,
     * including a title image, a main image, and a close button.
     *
     * @param parent The parent frame to which this dialog is attached. The dialog
     * is positioned relative to this frame.
     */
    public easterEgg(JFrame parent) {
		setSize(width, length);
        setLocationRelativeTo(parent);
        setUndecorated(true);
        JPanel popupPanel = new JPanel() {
        	   
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("popupBackground.jpg"));
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        popupPanel.setLayout(null);
        
       
   
        
        unlockImage = new ImageIcon(getClass().getResource("unlockTitle.png"));
        JLabel titleImage = new JLabel(unlockImage);
        titleImage.setBounds(50, 0, unlockImage.getIconWidth(), unlockImage.getIconHeight()); 
        System.out.println(unlockImage.getIconHeight());
        System.out.println(unlockImage.getIconWidth());
        popupPanel.add(titleImage);
        
        titanic = new ImageIcon(getClass().getResource("unlockTitanic.png"));
        JLabel titanicImage = new JLabel(titanic);
        titanicImage.setBounds(15, 60, titanic.getIconWidth(), titanic.getIconHeight()); 
        popupPanel.add(titanicImage);
        
        
        
        closeImage = new ImageIcon(getClass().getResource("close.png"));
        
        closeButton = new CustomButton(closeImage);
        closeButton.setPreferredSize(new Dimension(closeImage.getIconWidth(), closeImage.getIconHeight())); 
        closeButton.addActionListener(e -> dispose()); 
        closeButton.setContentAreaFilled(false);
        closeButton.setBounds(0, 0, closeImage.getIconWidth(), closeImage.getIconHeight()); 
        popupPanel.add(closeButton);
        
        setContentPane(popupPanel);

	}
}
