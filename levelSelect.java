import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Represents a level selection screen for the Ocean Odyssey game.
 * Allows users to select a level to play that they have unlocked. 
 * Extends JFrame and implements ActionListener.
 * @author Justin, Michael
 * @version 5.0
 */


public class levelSelect extends JFrame implements ActionListener {
    private int screenNumber = 0;
    //Images
    private ImageIcon backImage;
    private ImageIcon settingImage;
    private ImageIcon levelImage;

    //buttons
    private JButton backButton;
    private JButton settingButton;

    private int numButtons = 1;
    private JButton[] buttons = new JButton[30];

    /**
     * Constructs a level selection screen.
     * Loads necessary images and initializes buttons.
     * Takes user data to determine unlocked and locked levels 
     */
    public levelSelect(){
        if (!Save.getUser().getUsername().equals("Guest")) {
            numButtons = Save.getUser().getMaxLevel();
        }
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

        //load images
        backImage = new ImageIcon(getClass().getResource("bbutton.png"));
        settingImage = new ImageIcon(getClass().getResource("Sbutton.png"));
        levelImage = new ImageIcon(getClass().getResource("level.png"));


        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        int width = bounds.width;
        int height = bounds.height;
        int rows = 6; 
        int cols = 5; 
        int distance = 10; 
        int totalDistanceWidth = (cols - 1) * distance;
        int totalDistanceHeight = (rows - 1) * distance;
        int totalButtonWidth = cols * levelImage.getIconWidth();
        int totalButtonHeight = rows * levelImage.getIconHeight();
        int gridWidth = totalDistanceWidth + totalButtonWidth;
        int gridHeight = totalDistanceHeight + totalButtonHeight;

        int startX = (width - gridWidth) / 2;
        int startY = (height - gridHeight) / 2;


        for (int i = 0; i < 30; i++) {
            int x = startX + (i % cols) * (levelImage.getIconWidth() + distance);
            int y = startY + (i / cols) * (levelImage.getIconHeight() + distance);
            JButton button = new CustomButton(levelImage);
            button.setBounds(x, y, levelImage.getIconWidth(), levelImage.getIconHeight());
            button.setContentAreaFilled(false);
            button.addActionListener(this);
            if (i >= numButtons) {
                button.setEnabled(false);
            }
            contentPane.add(button);
            buttons[i] = button;

            if (i < numButtons) {
                JLabel buttonLabel = new JLabel(Integer.toString(i + 1));
                buttonLabel.setBounds(x + 40, y+50, levelImage.getIconWidth(), levelImage.getIconHeight());

                contentPane.add(buttonLabel);
            }
        }

        System.out.println(buttons[0]);
        backButton = new CustomButton(backImage);
        backButton.setPreferredSize(new Dimension(backImage.getIconWidth(), backImage.getIconHeight()));
        backButton.addActionListener(this); 
        backButton.setContentAreaFilled(false);
        backButton.setBounds(20, 10, backImage.getIconWidth(), backImage.getIconHeight());
        contentPane.add(backButton);

        //settings
        settingButton = new CustomButton(settingImage);
        settingButton.setPreferredSize(new Dimension(settingImage.getIconWidth(), settingImage.getIconHeight()));
        settingButton.addActionListener(this); 
        settingButton.setContentAreaFilled(false);
        settingButton.setBounds(width - settingImage.getIconWidth() - 20, 10, settingImage.getIconWidth(), settingImage.getIconHeight());
        contentPane.add(settingButton);
        setVisible(true);

    }

    /**
     * Handles actions performed by buttons.
     * Loads the games after a level is selected 
     * @param e The ActionEvent object representing the event.
     */
    public void actionPerformed(ActionEvent e) {
        Config config = new Config();
        try {
            config.loadConfig();
        } catch (IOException a) {
            System.out.println(a.getMessage());
        }
        if (e.getSource() == backButton) {
            setVisible(false);
            gameMode gameModeScreen = new gameMode();
        } else if (e.getSource() == settingButton){
            setting settingScreen = new setting(this);
            settingScreen.setVisible(true);
        } else {
            for(int i = 0; i < numButtons; i++){
               if(e.getSource() == buttons[i] ) {
                   System.out.println(i);
                   Levels level = config.getLevels().get(i);
                System.out.println(i);
                GameControl gameControll = new GameControl(level);
                JFrame frame = new JFrame("Ocean Odyssey");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setUndecorated(true);
                frame.add(gameControll);
                frame.setVisible(true);
                this.dispose();
                gameControll.start();
               } 
            } 
            
            
        }









    }
}
