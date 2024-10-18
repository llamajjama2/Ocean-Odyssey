import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

/**
 * The {@code gameMode} class extends {@code JFrame} and implements {@code ActionListener}
 * to provide a graphical interface for selecting game modes in Ocean Odyssey.
 * It manages the game mode selection screen, where users can choose between different modes of the game,
 * access settings, or return to the main menu.
 *
 * @author Haadi, Michael
 * @version 4.0
 */

public class gameMode extends JFrame implements ActionListener{
    private int screenNumber = 1;
    private ImageIcon levelTitle;
    private ImageIcon backImage;
    private ImageIcon settingImage;
    private ImageIcon level;
    private ImageIcon endless;
    private JButton backButton;
    private JButton settingButton;
    private JButton levelButton;
    private JButton endlessButton;

    /**
     * Constructs a new {@code gameMode} frame initializing the game mode selection screen.
     * Sets up the UI components including background, buttons, and action listeners for interaction.
     */
    public gameMode(){
        setTitle("Ocean Odyssey");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        level = new ImageIcon(getClass().getResource("levels.png"));
        endless = new ImageIcon(getClass().getResource("endless.png"));
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();

        int width = bounds.width;
        int height = bounds.height;
        int buttonX = (width - endless.getIconWidth() * 2) / 2;
        int buttonY = (height - endless.getIconHeight()) / 2;
        levelTitle = new ImageIcon(getClass().getResource("levelTitle.png"));
        JLabel titleImage = new JLabel(levelTitle);
        titleImage.setBounds((width - levelTitle.getIconWidth()) / 2, 0, levelTitle.getIconWidth(), levelTitle.getIconHeight()); // Set bounds for the label
        contentPane.add(titleImage);

        backButton = new CustomButton(backImage);
        backButton.setPreferredSize(new Dimension(backImage.getIconWidth(), backImage.getIconHeight()));
        backButton.addActionListener(this); // Add action listener to the button
        backButton.setContentAreaFilled(false);
        backButton.setBounds(20, 10, backImage.getIconWidth(), backImage.getIconHeight());
        contentPane.add(backButton);

        settingButton = new CustomButton(settingImage);
        settingButton.setPreferredSize(new Dimension(settingImage.getIconWidth(), settingImage.getIconHeight()));
        settingButton.addActionListener(this); // Add action listener to the button
        settingButton.setContentAreaFilled(false);
        settingButton.setBounds(width - settingImage.getIconWidth() - 20, 10, settingImage.getIconWidth(), settingImage.getIconHeight());
        contentPane.add(settingButton);

        levelButton = new CustomButton(level);
        levelButton.setPreferredSize(new Dimension(level.getIconWidth(), level.getIconHeight()));
        levelButton.addActionListener(this); // Add action listener to the button
        levelButton.setContentAreaFilled(false);
        levelButton.setBounds(buttonX, buttonY,level.getIconWidth(), level.getIconHeight());
        contentPane.add(levelButton);

        endlessButton = new CustomButton(endless);
        endlessButton.setPreferredSize(new Dimension(endless.getIconWidth(), endless.getIconHeight()));
        endlessButton.addActionListener(this); // Add action listener to the button
        endlessButton.setContentAreaFilled(false);
        endlessButton.setBounds(buttonX + 210, buttonY,endless.getIconWidth(), endless.getIconHeight());
        contentPane.add(endlessButton);
        setVisible(true);

    }
    /**
     * Constructs a new {@code gameMode} frame initializing the game mode selection screen.
     * Sets up the UI components including background, buttons, and action listeners for interaction.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            if (screenNumber== 1) {
                screenNumber = 0;
                setVisible(false);
                menu menuScreen = new menu();
            }
        } else if (e.getSource() == settingButton) {
            setting settingScreen = new setting(this);
            settingScreen.setVisible(true);
        } else if (e.getSource() == levelButton) {
            if (screenNumber == 1) {
                screenNumber = 7;
                setVisible(false);
                levelSelect levelScreen = new levelSelect();
            }
        } else if (e.getSource() == endlessButton) {
            this.dispose();
            level newLevel = new level();
        }
    }

}
