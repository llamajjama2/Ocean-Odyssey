import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The {@code profile} class extends {@link JFrame} and serves as the user's profile
 * page in the "Ocean Odyssey" game, displaying the user's current skin, high score, highest level reached,
 * and providing options to navigate back, change settings, or change the skin.
 * <p>
 * This class is responsible for setting up and displaying the profile UI, including
 * loading and displaying images representing the player's achievements and current settings,
 * as well as handling actions for buttons like the back button, settings button, and skin button.
 * </p>
 *
 * @author Haadi, Justin, Michael
 * @version 4.0
 */
public class profile extends JFrame implements ActionListener {

    private int screenNumber = 4; // Identifies the profile screen

    private int width = 1264; // Width of the frame
    private int length = 700; // Height of the frame

    // ImageIcons for various UI elements
    private ImageIcon profileTitle;
    private ImageIcon backImage;
    private ImageIcon settingImage;
    private ImageIcon skinImage;
    private ImageIcon HScore;
    private ImageIcon Hlevel;

    // Additional skin icons not used directly but loaded for potential future use
    private ImageIcon pirateImage;
    private ImageIcon steamImage;
    private ImageIcon neonImage;
    private ImageIcon aquaImage;
    private ImageIcon titanicImage;
    private ImageIcon defaultImage;

    // Buttons for interacting with the profile page
    private JButton backButton;
    private JButton settingButton;
    private JButton skinButton;
    private JButton leaderBoardButton;

    /**
     * Constructs a new profile window, initializing UI components and
     * setting up event listeners for button interactions.
     */
    public profile(){

        setTitle("Ocean Odyssey");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(width,length);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel() {

            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("Ocean background.jpg"));

                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);

            }

        };

        setContentPane(contentPane);

        contentPane.setLayout(null);

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

        Rectangle bounds = env.getMaximumWindowBounds();

        int width = bounds.width;

        int height = bounds.height;

        Hlevel = new ImageIcon(getClass().getResource("Hlevel.png"));

        JLabel hLevelImage = new JLabel(Hlevel);

        hLevelImage.setBounds((width - Hlevel.getIconWidth() * 3 ), (height - Hlevel.getIconHeight() * 3) / 2, Hlevel.getIconWidth(), Hlevel.getIconHeight()); // Set bounds for the label

        contentPane.add(hLevelImage);

        HScore = new ImageIcon(getClass().getResource("HScore.png"));

        JLabel HScoreImage = new JLabel(HScore);

        HScoreImage.setBounds(width - HScore.getIconWidth() * 3, (((height - Hlevel.getIconHeight() * 3) / 2) + 200), HScore.getIconWidth(), HScore.getIconHeight()); // Set bounds for the label

        contentPane.add(HScoreImage);

        profileTitle = new ImageIcon(getClass().getResource("profileTitle.png"));

        JLabel titleImage = new JLabel(profileTitle);

        titleImage.setBounds((width - profileTitle.getIconWidth()) / 2,0 , profileTitle.getIconWidth(), profileTitle.getIconHeight()); // Set bounds for the label

        contentPane.add(titleImage);

        String shipName = Save.getUser().getCurrentSkin().getName();

        pirateImage = new ImageIcon(getClass().getResource(shipName));

        JLabel PImage = new JLabel(pirateImage);

        PImage.setBounds((width - pirateImage.getIconWidth() * 2) / 3,(width - pirateImage.getIconWidth() * 2) / 3 , pirateImage.getIconWidth(), pirateImage.getIconHeight()); // Set bounds for the label

        contentPane.add(PImage);
        Integer HS = Save.getUser().getHighScore();
        Integer HL = Save.getUser().getMaxLevel();
        String HIGHSCORE = HS.toString();
        String HIGHLEVEL = HL.toString();
        Font font = new Font("Arial", Font.BOLD, 50);

        JLabel highScore = new JLabel(HIGHSCORE);

        highScore.setFont(font);

        highScore.setBounds(width - HScore.getIconWidth() * 3, (((height - Hlevel.getIconHeight() * 3) / 2) + 200) + 100, HScore.getIconWidth(), HScore.getIconHeight());

        JLabel highLevel = new JLabel(HIGHLEVEL);

        highLevel.setFont(font);

        highLevel.setBounds(width - Hlevel.getIconWidth() * 3,(height - Hlevel.getIconHeight() * 3) / 2 + 100, Hlevel.getIconWidth(), Hlevel.getIconHeight() );

        contentPane.add(highLevel);

        contentPane.add(highScore);

        backImage = new ImageIcon(getClass().getResource("bbutton.png"));

        settingImage = new ImageIcon(getClass().getResource("Sbutton.png"));

        skinImage = new ImageIcon(getClass().getResource("skinImage.png"));

        backButton = new CustomButton(backImage);

        backButton.setPreferredSize(new Dimension(backImage.getIconWidth(), backImage.getIconHeight()));

        backButton.addActionListener(this); // Add action listener to the button

        backButton.setContentAreaFilled(false);

        backButton.setBounds(20, 10, backImage.getIconWidth(), backImage.getIconHeight());

        contentPane.add(backButton);

        settingButton = new CustomButton(settingImage);

        settingButton.setPreferredSize(new Dimension(settingImage.getIconWidth(), settingImage.getIconHeight()));

        settingButton.addActionListener(this);

        settingButton.setContentAreaFilled(false);

        settingButton.setBounds(width - settingImage.getIconWidth() - 20, 10, settingImage.getIconWidth(), settingImage.getIconHeight());

        contentPane.add(settingButton);

        skinButton = new CustomButton(skinImage);

        skinButton.setPreferredSize(new Dimension(skinImage.getIconWidth(), skinImage.getIconHeight()));

        skinButton.addActionListener(this);

        skinButton.setContentAreaFilled(false);

        skinButton.setBounds((width - skinImage.getIconWidth() * 2) / 3, (height - skinImage.getIconHeight() * 3),skinImage.getIconWidth(), skinImage.getIconHeight());

        contentPane.add(skinButton);

        setVisible(true);

    }

    /**
     * Handles action events triggered by the user's interaction with
     * buttons on the profile page, such as navigating back to the main menu,
     * opening the settings, or changing the player's skin.
     *
     * @param e The {@link ActionEvent} that was triggered.
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == backButton) {

            if (screenNumber== 4) {

                screenNumber = 0;

                setVisible(false);

                menu menuScreen = new menu();

            }

        } else if (e.getSource() == settingButton) {

            setting settingScreen = new setting(this);

            settingScreen.setVisible(true);

        } else if (e.getSource() == skinButton) {

            skins skinScreen = new skins(this);

            skinScreen.setVisible(true);

        }

    }
}

