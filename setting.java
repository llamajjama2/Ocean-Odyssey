import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.io.IOException;

/**
 * The {@code setting} class extends {@link JDialog} and is used to display
 * and adjust game settings such as control sensitivity. It provides a graphical
 * user interface for changing settings and immediately applies and saves these changes.
 *
 * The dialog includes a slider for adjusting sensitivity and buttons for closing the dialog
 * or navigating back to the main menu.
 *
 * @author Haadi
 * @version 4.0
 */
public class setting extends JDialog implements ActionListener{
    private int width = 1000;
    private int length = 600;
    private JPanel previousOverlay = null;
    private JSlider sensitivitySlider ;
    private ImageIcon settingTitle;
    private ImageIcon closeImage;
    private ImageIcon menu;
    private JButton closeButton;
    JButton menuButton;
    private JPanel popupPanel;
    private JFrame parent;
    /**
     * Constructs a new {@code setting} dialog with a specified parent frame.
     *
     * @param parent The parent {@link JFrame} from which this dialog is launched.
     */
    public setting(JFrame parent) {
        super(parent, "Custom Popup", true);
        this.parent = parent;
        setSize(width, length);
        setLocationRelativeTo(parent);
        setUndecorated(true);
        popupPanel = new JPanel() {

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("popupBackground.jpg"));
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
            }

        };
        popupPanel.setLayout(null);
        settingTitle = new ImageIcon(getClass().getResource("settingTitle.png"));
        JLabel titleImage = new JLabel(settingTitle);
        titleImage.setBounds((width - settingTitle.getIconWidth()) / 2, 0, settingTitle.getIconWidth(), settingTitle.getIconHeight()); // Set bounds for the label
        popupPanel.add(titleImage);
        closeImage = new ImageIcon(getClass().getResource("close.png"));
        menu = new ImageIcon(getClass().getResource("menu.png"));

        closeButton = new CustomButton(closeImage);
        closeButton.setPreferredSize(new Dimension(closeImage.getIconWidth(), closeImage.getIconHeight()));
        closeButton.addActionListener(e -> dispose());
        closeButton.setContentAreaFilled(false);
        closeButton.setBounds(0, 0, closeImage.getIconWidth(), closeImage.getIconHeight());
        popupPanel.add(closeButton);

        sensitivitySlider = new JSlider(JSlider.HORIZONTAL, 0, 99, Save.getSettings().getSensitivityLevel()-1);
        sensitivitySlider.setBounds((width - menu.getIconWidth()) / 2 , (length - menu.getIconHeight()) / 2 + 100, 200, 50);
        sensitivitySlider.setOpaque(false);
        sensitivitySlider.setValue(Save.getSettings().getSensitivityLevel() - 1);
        sensitivitySlider.setSnapToTicks(true);
        sensitivitySlider.setPaintTicks(true);
        sensitivitySlider.setMajorTickSpacing(1);
        sensitivitySlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int value = source.getValue();
                    Save.getSettings().setSensitivityLevel(value + 1);
                    try {
                        Save.saveData();
                    } catch (IOException ex) {
                        System.out.println("Error saving data");
                    }
                }
            }
        });

        JLabel sense = new JLabel("Sensitivity: ");
        Font font = new Font("Arial", Font.BOLD, 25);
        sense.setBounds(230, 125, 500, 500);
        sense.setFont(font);
        popupPanel.add(sense);
        menuButton = new CustomButton(menu);
        menuButton.setPreferredSize(new Dimension(menu.getIconWidth(), menu.getIconHeight()));
        menuButton.addActionListener(this);
        menuButton.setContentAreaFilled(false);
        menuButton.setBounds((width - menu.getIconWidth()) / 2, (length - menu.getIconHeight()) / 2, menu.getIconWidth(), menu.getIconHeight());
        popupPanel.add(menuButton);
        popupPanel.add(sensitivitySlider);
        setContentPane(popupPanel);
    }

    /**
     * Handles action events for the dialog's components, specifically the menu button.
     * When the menu button is clicked, this dialog is hidden, and the main menu is displayed.
     *
     * @param e The {@link ActionEvent} that was triggered.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton) {
            this.dispose();
            Window topWindow = SwingUtilities.getWindowAncestor(this);
            topWindow.dispose();
            menu menuScreen = new menu();
        }
    }
}

