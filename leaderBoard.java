import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.Font;
import java.util.PriorityQueue;

/**
 * The {@code leaderBoard} class extends {@link JFrame} and displays the game's leaderboard,
 * showing player scores in a graphical interface. It implements {@link ActionListener} to handle
 * actions such as pressing the back button to return to the main menu.
 * <p>
 * This class sets up the leaderboard UI, including a background image, title image for the leaderboard,
 * and lists for usernames and their corresponding scores. It fetches and displays scores stored in a
 * {@link PriorityQueue} provided by a {@link Leaderboard_B} instance.
 * </p>
 *
 * @author Justin, Haadi
 * @version 4.0
 */
public class leaderBoard extends JFrame implements ActionListener {

	private int screenNumber = 3; // Screen number used to identify the leaderboard screen
	private int width = 1264; // Width of the leaderboard window
	private int length = 700; // Height of the leaderboard window
	private ImageIcon backImage; // Icon for the back button
	private ImageIcon leaderboardTitle; // Icon for the leaderboard title
	private JButton backButton; // Button to return to the previous screen

	/**
	 * Constructs a new {@code leaderBoard} window and initializes its components,
	 * including the leaderboard title, back button, and score display panels.
	 * The window is maximized to fill the screen and displays the leaderboard information
	 * centered within the window.
	 */

	public leaderBoard(){

		setTitle("Ocean Odyssey");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(width,length);

		setExtendedState(JFrame.MAXIMIZED_BOTH);

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

		contentPane.setLayout(null);

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Rectangle bounds = env.getMaximumWindowBounds();

		int width = bounds.width;

		int height = bounds.height;

		leaderboardTitle = new ImageIcon(getClass().getResource("leaderboardTitle.png"));

		JLabel titleImage = new JLabel(leaderboardTitle);

		titleImage.setBounds((width - leaderboardTitle.getIconWidth()) / 2, 0, leaderboardTitle.getIconWidth(), leaderboardTitle.getIconHeight()); // Set bounds for the label

		contentPane.add(titleImage);

		backImage = new ImageIcon(getClass().getResource("bbutton.png"));

		backButton = new CustomButton(backImage);

		backButton.setPreferredSize(new Dimension(backImage.getIconWidth(), backImage.getIconHeight()));

		backButton.addActionListener(this);

		backButton.setContentAreaFilled(false);

		backButton.setBounds(10, 10, backImage.getIconWidth(), backImage.getIconHeight());

		contentPane.add(backButton);

		JPanel usernamePanel = new JPanel(new GridLayout(10, 1));

		usernamePanel.setOpaque(false);

		usernamePanel.setBounds(475, 250, 300, 400);

		contentPane.add(usernamePanel);

		Font font = new Font("Arial", Font.BOLD, 30);

		JPanel scorePanel = new JPanel(new GridLayout(10, 1));

		scorePanel.setOpaque(false);

		scorePanel.setBounds(850, 250, 300, 400);

		contentPane.add(scorePanel);
		Leaderboard_B leaderboard = Save.getLeaderboard();
		PriorityQueue<Score> scores = leaderboard.getScores();


		for (int i = 0; i <= scores.size(); i++) {
			Score score = scores.poll();

			JLabel userNameLabel = new JLabel("User:  " + score.getPlayerName());

			JLabel scoreLabel = new JLabel("Score:  " + score.getScore());

			userNameLabel.setFont(font);

			usernamePanel.add(userNameLabel);

			scoreLabel.setFont(font);

			scorePanel.add(scoreLabel);

		}

		setVisible(true);

	}

	/**
	 * Handles action events triggered within the {@code leaderBoard} window.
	 * It currently supports the functionality of the back button, which returns the user
	 * to the main menu screen when pressed.
	 *
	 * @param e The {@link ActionEvent} that was triggered.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == backButton) {

			if (screenNumber== 3) {

				screenNumber= 0;

				setVisible(false);

				menu menuScreen = new menu();

			}

		}

	}
}


