
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a tutorial window in the "Ocean Odyssey" game, providing instructions
 * and guidance to players on how to play the game. This window includes graphical
 * representations of game elements, instructions on player movement, and information
 * on avoiding obstacles.
 *
 * @author Haadi
 * @version 3.0
 */

public class Tutorial extends JFrame implements ActionListener{
	private int screenNumber = 2;  
	private String background = "Ocean background.jpg";
	private ImageIcon backImage; 
	private ImageIcon tutorialTitle; 
	private ImageIcon settingImage;
    private ImageIcon obstacleImage;
    private ImageIcon youImage;
	private JButton backButton; 
	private JButton settingButton;

    /**
     * Constructs a Tutorial frame and initializes all graphical components
     * including background, instructions, and interactive buttons.
     */
    public Tutorial(){
	setTitle("Ocean Odyssey"); 
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	
	setExtendedState(JFrame.MAXIMIZED_BOTH);
	setLocationRelativeTo(null); 
	JPanel contentPane = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the background image
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
   
    tutorialTitle = new ImageIcon(getClass().getResource("tutorialTitle.png"));
    JLabel titleImage = new JLabel(tutorialTitle);
    titleImage.setBounds((width - tutorialTitle.getIconWidth()) / 2, 0, tutorialTitle.getIconWidth(), tutorialTitle.getIconHeight()); // Set bounds for the label
    contentPane.add(titleImage);
    
    settingImage = new ImageIcon(getClass().getResource("Sbutton.png"));
	
    backImage = new ImageIcon(getClass().getResource("bbutton.png"));
    backButton = new CustomButton(backImage);
    backButton.setPreferredSize(new Dimension(backImage.getIconWidth(), backImage.getIconHeight())); 
    backButton.addActionListener(this); // Add action listener to the button
    backButton.setContentAreaFilled(false);
    backButton.setBounds(10, 10, backImage.getIconWidth(), backImage.getIconHeight()); 
    contentPane.add(backButton);
    
    settingButton = new CustomButton(settingImage);
    settingButton.setPreferredSize(new Dimension(settingImage.getIconWidth(), settingImage.getIconHeight())); 
    settingButton.addActionListener(this); 
    settingButton.setContentAreaFilled(false);
    settingButton.setBounds(width - settingImage.getIconWidth() - 20, 10, settingImage.getIconWidth(), settingImage.getIconHeight()); 
    contentPane.add(settingButton);

    Font font = new Font("Arial", Font.BOLD, 20);
    JPanel instructionPanel = new JPanel(new GridLayout( 3,1));
    instructionPanel.setOpaque(false);
    instructionPanel.setBounds(450, 200, 500, 100);
    contentPane.add(instructionPanel);
    JLabel instructions = new JLabel("Instructions:");
    instructions.setFont(font);
    instructionPanel.add(instructions);
    JLabel wasd = new JLabel(" - Use WASD or Arrow Keys to move");
    wasd.setFont(font);
    instructionPanel.add(wasd);
    JLabel mine = new JLabel(" - Avoid the mines");
    mine.setFont(font);
    instructionPanel.add(mine);

    obstacleImage = new ImageIcon(getClass().getResource("obstacle.png"));
    JLabel obstacle = new JLabel(obstacleImage);
    obstacle.setBounds(300, 350, obstacleImage.getIconWidth(), obstacleImage.getIconHeight());
    contentPane.add(obstacle);
    youImage = new ImageIcon(getClass().getResource("you.png"));
    JLabel you = new JLabel(youImage);
    you.setBounds(800, 350, youImage.getIconWidth(), youImage.getIconHeight());
	contentPane.add(you);
    setVisible(true);
    }

    /**
     * Handles action events triggered by button clicks within the tutorial window.
     * This includes navigating back to the main menu or opening the settings menu.
     *
     * @param e The {@link ActionEvent} that was triggered.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            // Toggle between menu and game screens
            if (screenNumber== 2) {
                screenNumber = 0;
                setVisible(false);
                menu menuScreen = new menu(); 
            } 
        } else if (e.getSource() == settingButton) {
        	setting settingScreen = new setting(this);
            settingScreen.setVisible(true);
        }
    }
}
