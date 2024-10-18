import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The level class represents the singleplayer or multiplayer options of the Ocean Odyssey game.
 * It extends JFrame and implements ActionListener to handle button clicks.
 * @author Brian Y, Haadi
 * @version 3.0 
 */
public class level extends JFrame implements ActionListener{

    private int screenNumber = 1;
    private ImageIcon levelTitle;
    private ImageIcon backImage;
    private ImageIcon settingImage;
    private ImageIcon singleP;
    private ImageIcon multiP;
    private ImageIcon profileImage;

    private JButton backButton;
    private JButton settingButton;
    private JButton singlePButton;
    private JButton multiPButton;
    private JButton profileButton;


    /**
     * Constructs a new level JPanel, initializes UI elements such as buttons and sets up the level Screen.
     * The clickable area is defined as the area occupied by the ImageIcon.
     * This method is useful for determining if a mouse click occurred within the bounds
     * of the ImageIcon, rather than just anywhere on the button's surface.
     * 
     */
    public level(){
        setTitle("Ocean Odyssey");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Panel to hold UI elements
        JPanel contentPane = new JPanel() {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon backgroundImage = new ImageIcon(getClass().getResource("Ocean background.jpg"));
            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        setContentPane(contentPane);
        contentPane.setLayout(null);

        backImage = new ImageIcon(getClass().getResource("bbutton.png"));
        settingImage = new ImageIcon(getClass().getResource("Sbutton.png"));
        singleP = new ImageIcon(getClass().getResource("singleP.png"));
        multiP = new ImageIcon(getClass().getResource("multiP.png"));
        profileImage = new ImageIcon(getClass().getResource("ProfileButton.png"));

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        int width = bounds.width;
        int height = bounds.height;
        int buttonX = (width - multiP.getIconWidth() * 2) / 2;
        int buttonY = (height - multiP.getIconHeight()) / 2;

        levelTitle = new ImageIcon("levelTitle.png");
        JLabel titleImage = new JLabel(levelTitle);
        titleImage.setBounds((width - levelTitle.getIconWidth()) / 2, 0, levelTitle.getIconWidth(), levelTitle.getIconHeight()); 
        contentPane.add(titleImage);

        //back button
        backButton = new CustomButton(backImage);
        backButton.setPreferredSize(new Dimension(backImage.getIconWidth(), backImage.getIconHeight()));
        backButton.addActionListener(this); // Add action listener to the button
        backButton.setContentAreaFilled(false);
        backButton.setBounds(20, 10, backImage.getIconWidth(), backImage.getIconHeight());
        contentPane.add(backButton);


        //setting
        settingButton = new CustomButton(settingImage);
        settingButton.setPreferredSize(new Dimension(settingImage.getIconWidth(), settingImage.getIconHeight()));
        settingButton.addActionListener(this); // Add action listener to the button
        settingButton.setContentAreaFilled(false);
        settingButton.setBounds(width - settingImage.getIconWidth() - 20, 10, settingImage.getIconWidth(), settingImage.getIconHeight());
        contentPane.add(settingButton);

        //newgame button
        singlePButton = new CustomButton(singleP);
        singlePButton.setPreferredSize(new Dimension(singleP.getIconWidth(), singleP.getIconHeight()));
        singlePButton.addActionListener(this); // Add action listener to the button
        singlePButton.setContentAreaFilled(false);
        singlePButton.setBounds(buttonX, buttonY,singleP.getIconWidth(), singleP.getIconHeight());
        contentPane.add(singlePButton);

        //continue button
        multiPButton = new CustomButton(multiP);
        multiPButton.setPreferredSize(new Dimension(multiP.getIconWidth(), multiP.getIconHeight()));
        multiPButton.addActionListener(this); // Add action listener to the button
        multiPButton.setContentAreaFilled(false);
        multiPButton.setBounds(buttonX + 210, buttonY,multiP.getIconWidth(), multiP.getIconHeight());
        contentPane.add(multiPButton);

        //profile button
        profileButton = new CustomButton(profileImage);
        profileButton.setPreferredSize(new Dimension(profileImage.getIconWidth(), profileImage.getIconHeight()));
        profileButton.addActionListener(this); // Add action listener to the button
        profileButton.setContentAreaFilled(false);
        profileButton.setBounds(buttonX + (profileImage.getIconWidth() / 2), buttonY + 90 ,profileImage.getIconWidth(), profileImage.getIconHeight());
        contentPane.add(profileButton);
        
        setVisible(true);
    }

    /**
     * Handles action events generated by button clicks.
     * Creates new screens to load after a button is clicked.
     * @param e The ActionEvent generated by the user's interaction with UI elements.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            if (screenNumber== 1) {
                screenNumber = 0;
                setVisible(false);
                gameMode gameModeScreen = new gameMode();
            }

        } else if (e.getSource() == settingButton) {
            setting settingScreen = new setting(this);
            settingScreen.setVisible(true);
        } else if (e.getSource() == singlePButton) {
            if (screenNumber == 1) {
                screenNumber = 7;
                setVisible(false);
                GameControllerSingle gameControllerSingle = new GameControllerSingle();
                JFrame frame = new JFrame("Ocean Odyssey");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setUndecorated(true);
                frame.add(gameControllerSingle);
                frame.setVisible(true);
                gameControllerSingle.start();
            }
        } else if (e.getSource() == multiPButton) {
            setVisible(false);
            GameControllerMulti gameControllerMulti = new GameControllerMulti();
            JFrame frame = new JFrame("Ocean Odyssey");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
            frame.add(gameControllerMulti);
            frame.setVisible(true);
            gameControllerMulti.start();
        } else if (e.getSource() == profileButton) {
            if (screenNumber == 1) {
                screenNumber = 4;
                setVisible(false);
                profile profileScreen = new profile();
            }
        }
    }
}
