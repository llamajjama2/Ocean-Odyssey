import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The {@code menu} class extends {@code JFrame} and implements {@code ActionListener}
 * to create the main menu interface for the Ocean Odyssey game. This interface includes
 * buttons for navigating to different parts of the game such as tutorials, leaderboards,
 * game levels, player profiles, and settings. It also includes a unique easter egg feature
 * that is activated by clicking on the game title multiple times.
 *
 * @author Justin, Haadi
 * @version 5.0
 */
public class menu extends JFrame implements ActionListener {
	private int screenNumber = 0;  
	private int easterEgg = 0; 
	public int getTitanic = 0;
	private ImageIcon tutorialImage;
	private ImageIcon leaderBoardImage;
	private ImageIcon levelImage;
	private ImageIcon profileImage;
	private ImageIcon settingImage;
	private ImageIcon gameTitle;
	private JButton tutorialButton; 
	private JButton leaderBoardButton; 
	private JButton levelButton; 
	private JButton profileButton; 
	private JButton settingButton; 
	private JButton gameTitleButton;
    private JButton studentButton;
    private ImageIcon studentImage;

    /**
     * Constructs the main menu interface, initializing all UI components and setting up
     * the event listeners for user interactions. It dynamically adds a student button if
     * the current user is identified as an instructor. It also prepares the easter egg feature
     * associated with the game title button.
     */
    public menu(){
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

	gameTitle = new ImageIcon(getClass().getResource("title.png"));
    tutorialImage = new ImageIcon(getClass().getResource("Tbutton.png"));
    levelImage = new ImageIcon(getClass().getResource("Pbutton.png"));
    leaderBoardImage = new ImageIcon(getClass().getResource("Lbutton.png"));
    profileImage = new ImageIcon(getClass().getResource("ProfileButton.png"));
    settingImage = new ImageIcon(getClass().getResource("Sbutton.png"));
    studentImage = new ImageIcon(getClass().getResource("studentsButton.png"));

    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Rectangle bounds = env.getMaximumWindowBounds();    
    int width = bounds.width;
    int height = bounds.height;
    int buttonX = (width - levelImage.getIconWidth()) / 2;
    int buttonY = (height - levelImage.getIconHeight()) / 2;

    levelButton = new CustomButton(levelImage);
    levelButton.setPreferredSize(new Dimension(levelImage.getIconWidth(), levelImage.getIconHeight())); 
    levelButton.addActionListener(this); 
    levelButton.setContentAreaFilled(false);
    levelButton.setBounds(buttonX, buttonY, levelImage.getIconWidth(), levelImage.getIconHeight()); 
    contentPane.add(levelButton);

    tutorialButton = new CustomButton(tutorialImage);
    tutorialButton.setPreferredSize(new Dimension(tutorialImage.getIconWidth(), tutorialImage.getIconHeight())); 
    tutorialButton.addActionListener(this); 
    tutorialButton.setContentAreaFilled(false);
    tutorialButton.setBounds(buttonX, buttonY + 85, tutorialImage.getIconWidth(), tutorialImage.getIconHeight()); 
    contentPane.add(tutorialButton);

    leaderBoardButton = new CustomButton(leaderBoardImage);
    leaderBoardButton.setPreferredSize(new Dimension(leaderBoardImage.getIconWidth(), leaderBoardImage.getIconHeight())); 
    leaderBoardButton.addActionListener(this); 
    leaderBoardButton.setContentAreaFilled(false);
    leaderBoardButton.setBounds(buttonX, buttonY + 170, leaderBoardImage.getIconWidth(), leaderBoardImage.getIconHeight()); 
    contentPane.add(leaderBoardButton);

    profileButton = new CustomButton(profileImage);
    profileButton.setPreferredSize(new Dimension(profileImage.getIconWidth(), profileImage.getIconHeight())); 
    profileButton.addActionListener(this); 
    profileButton.setContentAreaFilled(false);
    profileButton.setBounds(buttonX, buttonY + 255,profileImage.getIconWidth(), profileImage.getIconHeight()); 
    contentPane.add(profileButton);

    settingButton = new CustomButton(settingImage);
    settingButton.setPreferredSize(new Dimension(settingImage.getIconWidth(), settingImage.getIconHeight())); 
    settingButton.addActionListener(this); 
    settingButton.setContentAreaFilled(false);
    settingButton.setBounds(width - settingImage.getIconWidth() - 20, 10, settingImage.getIconWidth(), settingImage.getIconHeight()); 
    contentPane.add(settingButton);

    gameTitleButton = new CustomButton(gameTitle);
    gameTitleButton.setPreferredSize(new Dimension(settingImage.getIconWidth(), settingImage.getIconHeight())); 
    gameTitleButton.addActionListener(this); 
    gameTitleButton.setContentAreaFilled(false);
    gameTitleButton.setBounds((width - gameTitle.getIconWidth()) / 2, 30, gameTitle.getIconWidth(), gameTitle.getIconHeight()); 
    contentPane.add(gameTitleButton);

    if (Save.getUser().getUsername().equals("instructor")) {
        studentButton = new CustomButton(studentImage);
        studentButton.setPreferredSize(new Dimension(studentImage.getIconWidth(), studentImage.getIconHeight()));
        studentButton.addActionListener(this);
        studentButton.setContentAreaFilled(false);
        studentButton.setBounds(buttonX, buttonY + 335,studentImage.getIconWidth(), studentImage.getIconHeight());
        contentPane.add(studentButton);
    }
    setVisible(true);
	}

    /**
     * Handles action events generated by button clicks within the main menu. This includes
     * navigating to different parts of the game, such as the tutorial, leaderboard, level
     * selection, and profile screens. Additionally, it manages the easter egg activation
     * and the revealing of the Titanic skin.
     *
     * @param e The action event triggered by user interaction.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tutorialButton) {
            // Toggle between menu and game screens
            if (screenNumber== 0) {
                screenNumber= 2;
                setVisible(false);
                Tutorial tutorialscreen = new Tutorial(); 
            }
        } else if(e.getSource() == leaderBoardButton) {   
                if (screenNumber== 0) {
                    screenNumber= 2;
                    setVisible(false);
                    leaderBoard leaderBoardScreen = new leaderBoard();
                }
        } else if(e.getSource() == levelButton) {
                if (screenNumber == 0) {
                    screenNumber = 1;
                    setVisible(false);
                    gameMode gameModeScreen = new gameMode();
                }
        } else if(e.getSource() == profileButton) {
                if (screenNumber== 0) {
                    screenNumber = 4;
                    setVisible(false);
                    profile profileScreen = new profile();
                  
                }
        } else if (e.getSource() == settingButton) {
                setting settingScreen = new setting(this);
                settingScreen.setVisible(true);
        
        } else if (e.getSource() == studentButton){
                setVisible(false);
                instructorView screen = new instructorView();
        } else {
        	if (e.getSource() == gameTitleButton) {
        		easterEgg += 1; 
        		System.out.println(easterEgg);
        		if (easterEgg == 5) {
                    if (Save.getUser().getUsername().equals("Guest")) {
                        Save.getUser().addSkin(new Skin("Titanic.png", 0, "Legendary"));
                    } else {
                        Save.getUser().addSkin(new Skin("Titanic.png", 0, "Legendary"));
                        try {
                            Save.saveData();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
        			easterEgg titanicUnlock = new easterEgg(this); 
        			titanicUnlock.setVisible(true);
        			getTitanic = 1;
        		}
        	}
        }
    }
}
