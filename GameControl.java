import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * The {@code GameControl} class manages the game loop, rendering, and game state updates.
 * It handles the game's initialization, starting, stopping, and the main game loop logic,
 * including rendering and updating game objects like the player, obstacles, and background.
 * It also manages the game's UI elements such as the settings button, score display, and lives counter.
 *
 *  @author Brian Hsu
 *  @version 5.0
 */
public class GameControl extends Canvas implements Runnable, MouseListener {
    private boolean running = false;
    private boolean isPaused = false;
    private Thread thread;
    private Player player1;
    private int countdownTimer = 3;
    private boolean showReady = true;
    private long readyStartTime;
    private long lastTimeCheck = System.currentTimeMillis();
    private BufferedImage submarineImage;
    private BufferedImage obstacleImage;
    private BufferedImage backgroundImage;
    private BufferedImage backgroundImage2;
    private BufferedImage finishLineImage;
    private float bgX = 0;
    private float bgX2 = WIDTH;
    public static int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private Levels currentLevel;
    private final ObstacleGenerator obstacleGenerator;
    private BufferedImage settingsButtonImage;
    private BufferedImage heartImage;
    private Rectangle settingsButtonBounds;
    private long currentTimeMillis = System.currentTimeMillis();
    private long lastTimeMillis = System.currentTimeMillis();
    private long timeElapsed = 0;
    private long currentTimeInterval = 0;
    private int score = 0;
    private int lives;
    private boolean crashDialogShown = false;
    private int progressBarWidth;
    private int progressBarHeight = 40;
    private int progressBarX = 200;
    private int progressBarY = 30;
    private double progress = 0.0;
    private int hitObstacles = 0;

    /**
     * Initializes a new instance of the game control with the specified level.
     * Loads game resources, initializes game objects, and sets up the UI elements.
     *
     * @param level The level configuration to initialize the game with.
     */
    public GameControl(Levels level) {
        loadResources();
        settingsButtonBounds = new Rectangle(WIDTH - 100, 10, 80, 80);
        this.addMouseListener(this);
        readyStartTime = System.currentTimeMillis();
        player1 = new Player(1000, 250, submarineImage);
        lives = 3;
        currentLevel = level;
        obstacleGenerator = new ObstacleGenerator(currentLevel, obstacleImage);
        progressBarWidth = WIDTH - 400 - progressBarX;
        this.addKeyListener(new KeyInput(this));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
    }

    /**
     * Loads game resources like images for the submarine, obstacles, background, etc.
     */
    private void loadResources() {
        try {
            Skin sub = Save.getUser().getCurrentSkin();
            submarineImage = ImageIO.read(getClass().getResource(sub.getName()));
            obstacleImage = ImageIO.read(getClass().getResource("db.png"));
            backgroundImage = ImageIO.read(getClass().getResource("background.png"));
            backgroundImage2 = ImageIO.read(getClass().getResource("background2.png"));
            settingsButtonImage = ImageIO.read(getClass().getResource("Sbutton.png"));
            heartImage = ImageIO.read(getClass().getResource("heart.png"));
            finishLineImage = ImageIO.read(getClass().getResource("finishLine.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the game thread if the game is not already running.
     */
    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stops the game thread if the game is running and exits the application.
     */
    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    /**
     * The main game loop method that runs while the game is active.
     * Handles game state updates, rendering, and transition between different game states.
     */
    @Override
    public void run() {
        while (running) {
            long now = System.currentTimeMillis();
            if (isPaused) {
                player1.setVelX(0);
                player1.setVelY(0);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                render();
            } else {
                if (showReady) {
                    if (now - readyStartTime >= 1500) {
                        showReady = false;
                        lastTimeCheck = now;
                    }
                    player1.tick();
                    renderReady();
                } else if (countdownTimer > 0) {
                    if (now - lastTimeCheck >= 1000) {
                        countdownTimer--;
                        lastTimeCheck = now;
                    }
                    player1.tick();
                    renderCountdown();
                } else {
                    if (timeElapsed == 0 && score == 0) {
                        lastTimeMillis = now;
                        tick();
                    }
                    tick();
                    if (checkForCollision()) {
                        player1.setVelX(0);
                        player1.setVelY(0);
                        continue;
                    }
                    render();
                }
            }
        }
        stop();
    }

    /**
     * Renders the "READY" screen at the start of the game.
     */
    private void renderReady() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        renderInitialState(g);
        drawSettingsButton(g);
        player1.render(g);
        drawLives(g);
        drawScore(g);
        drawProgressBar(g);
        drawOverlayAndText(g, "READY");
        g.dispose();
        bs.show();
    }

    /**
     * Renders the countdown timer at the start of each game.
     */
    private void renderCountdown() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        renderInitialState(g);
        drawSettingsButton(g);
        player1.render(g);
        drawLives(g);
        drawScore(g);
        drawProgressBar(g);
        drawOverlayAndText(g, String.valueOf(countdownTimer));
        g.dispose();
        bs.show();
    }

    /**
     * Draws an overlay and text on the screen, used for displaying messages like "READY".
     *
     * @param g The graphics context to use for rendering.
     * @param text The text to display on the overlay.
     */
    private void drawOverlayAndText(Graphics g, String text) {
        g.setColor(new Color(0, 0, 0, 123));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setFont(new Font("Comic Sans", Font.BOLD, 50));
        g.setColor(Color.WHITE);
        if (text == "READY") {
            g.drawString(text, WIDTH / 2 - 100, HEIGHT / 2);
        } else {
            g.drawString(text, WIDTH / 2, HEIGHT / 2);
        }
    }

    /**
     * Draws the settings button on the screen.
     *
     * @param g The graphics context to use for rendering.
     */
    private void drawSettingsButton(Graphics g) {
        if (settingsButtonImage != null) {
            g.drawImage(settingsButtonImage, settingsButtonBounds.x, settingsButtonBounds.y, settingsButtonBounds.width, settingsButtonBounds.height, null);
        }
    }

    /**
     * Renders the initial state of the game, including the background images.
     *
     * @param g The graphics context to use for rendering.
     */
    private void renderInitialState(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, (int) bgX, 0, WIDTH, HEIGHT, null);
        }
        if (backgroundImage2 != null) {
            g.drawImage(backgroundImage2, (int) bgX2, 0, WIDTH, HEIGHT, null);
        }
    }

    /**
     * Updates the game objects and checks for game events like collision and level completion.
     */
    private void tick() {
        currentTimeMillis = System.currentTimeMillis();
        if (lastTimeMillis == 0) {
            lastTimeMillis = currentTimeMillis;
        }
        currentTimeInterval = currentTimeMillis - lastTimeMillis;
        lastTimeMillis = currentTimeMillis;
        timeElapsed += currentTimeInterval;
        double speedIncrease = (((double) currentLevel.getInitialSpeed()) / 10000) + ((((double) currentLevel.getSpeedMultiplier()) / 1000000) * timeElapsed);
        float speed = (float) (0.5 + speedIncrease);
        score += Math.round(speed * currentTimeInterval);
        progress = Math.min((double) score / currentLevel.getLength(), 1.0);
        bgX += speed;
        bgX2 += speed;
        if (bgX >= WIDTH) {
            bgX = -WIDTH;
        }
        if (bgX2 >= WIDTH) {
            bgX2 = -WIDTH;
        }
        player1.tick();
        obstacleGenerator.tick(speed);
        // Update game logic here
        if (score >= currentLevel.getLength()) {
            running = false;
            showGameWinnerScreen();
        }
    }

    /**
     * Renders the game frame, including game objects and UI elements.
     */
    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        renderInitialState(g);
        drawSettingsButton(g);
        player1.render(g);
        obstacleGenerator.render(g);
        drawLives(g);
        // Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans", Font.BOLD, 40));
        g.drawString(" " + score, 20, 65);
        // Progress bar
        g.setColor(Color.GRAY);
        g.fillRect(progressBarX, progressBarY, progressBarWidth, progressBarHeight);
        g.setColor(Color.BLUE);
        int fillWidth = (int) (progress * progressBarWidth);
        g.fillRect(progressBarX, progressBarY, fillWidth, progressBarHeight);
        // Settings
        if (settingsButtonImage != null) {
            g.drawImage(settingsButtonImage, settingsButtonBounds.x, settingsButtonBounds.y, settingsButtonBounds.width, settingsButtonBounds.height, null);
        }

        g.dispose();
        bs.show();
    }

    /**
     * Draws the player's remaining lives on the screen.
     *
     * @param g The graphics context to use for rendering.
     */
    private void drawLives(Graphics g) {
        int heartWidth = settingsButtonBounds.width;
        int heartHeight = settingsButtonBounds.height;
        int gap = 10;
        int totalWidth = (heartWidth + gap) * 3;
        int startX = WIDTH - totalWidth - 100;
        for (int i = 0; i < lives; i++) {
            if (heartImage != null) {
                int x = startX + (heartWidth + gap) * i;
                g.drawImage(heartImage, x, settingsButtonBounds.y, heartWidth, heartHeight, null);
            }
        }
    }

    /**
     * Draws the current score on the screen.
     *
     * @param g The graphics context to use for rendering.
     */
    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans", Font.BOLD, 40));
        g.drawString(" " + score, 20, 65);
    }

    /**
     * Draws the progress bar representing the player's progress through the level.
     *
     * @param g The graphics context to use for rendering.
     */
    private void drawProgressBar(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(progressBarX, progressBarY, progressBarWidth, progressBarHeight);
        g.setColor(Color.BLUE);
        int fillWidth = (int) (progress * progressBarWidth);
        g.fillRect(progressBarX, progressBarY, fillWidth, progressBarHeight);
    }

    /**
     * Handles key press events to control the player's submarine.
     *
     * @param e The key event that occurred.
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            player1.setVelY(-2);
        } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            player1.setVelY(2);
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            player1.setVelX(-2);
        } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            player1.setVelX(2);
        }
    }

    /**
     * Handles key release events to stop the player's submarine movement.
     *
     * @param e The key event that occurred.
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            player1.setVelY(0);
        } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            player1.setVelY(0);
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            player1.setVelX(0);
        } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            player1.setVelX(0);
        }
    }

    /**
     * Handles mouse click events, primarily for interacting with the settings button.
     *
     * @param e The mouse event that occurred.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (settingsButtonBounds.contains(e.getPoint())) {
            isPaused = true;
            SwingUtilities.invokeLater(() -> {
                setting settingsDialog = new setting(null);
                settingsDialog.setVisible(true);
                settingsDialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        isPaused = false;
                        lastTimeMillis = System.currentTimeMillis();
                    }
                });
            });
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Checks for collisions between the player's submarine and obstacles.
     *
     * @return {@code true} if a collision occurred, {@code false} otherwise.
     */
    private boolean checkForCollision() {
        Rectangle playerBounds = player1.getBounds();
        for (Obstacle obstacle : obstacleGenerator.getObstacles()) {
            if (obstacle.isActive() && playerBounds.intersects(obstacle.getBounds())) {
                obstacle.setActive(false);
                if (!crashDialogShown) {
                    crashDialogShown = true;
                    hitObstacles++;
                    showCrashDialog();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Shows a dialog when the player's submarine crashes into an obstacle.
     */
    private void showCrashDialog() {
        isPaused = true;
        SwingUtilities.invokeLater(() -> {
            int difficulty = currentLevel.getLevel();
            CrashDialog crashDialog = new CrashDialog(null, difficulty, this);
            crashDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    crashDialogShown = false;
                    isPaused = false;
                    lastTimeMillis = System.currentTimeMillis();
                }
            });
            crashDialog.setVisible(true);
        });
    }

    /**
     * Decrements the number of lives the player has left and checks for game over.
     */
    public void decrementLives() {
        lives--;
        if (lives <= 0) {
            running = false;
            showGameOverScreen();
        }
    }

    /**
     * Shows the game over screen when the player runs out of lives.
     */
    private void showGameOverScreen() {
        JDialog gameOverDialog = new JDialog();
        gameOverDialog.setSize(300, 200);
        gameOverDialog.setLayout(new BorderLayout());
        gameOverDialog.setLocationRelativeTo(null);
        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverDialog.add(gameOverLabel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        gameOverDialog.add(buttonPanel, BorderLayout.SOUTH);
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> {
            gameOverDialog.dispose();
            restartGame();
        });
        buttonPanel.add(playAgainButton);
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(e -> {
            gameOverDialog.dispose();
            menu menuScreen = new menu();
            menuScreen.setVisible(true);
            Window topWindow = SwingUtilities.getWindowAncestor(this);
            topWindow.dispose();
        });
        buttonPanel.add(mainMenuButton);
        gameOverDialog.setModal(true);
        gameOverDialog.setVisible(true);
    }

    /**
     * Restarts the game, resetting the game state and objects to their initial conditions.
     */
    private void restartGame() {
        lives = 3;
        score = 0;
        player1.resetPosition();
        obstacleGenerator.resetObstacles();
        hitObstacles = 0;
        start();
    }

    /**
     * Shows the winner screen when the player completes the level, updating the leaderboard if necessary.
     */
    private void showGameWinnerScreen() {
        int finalScore = score + (2000 * (lives)) - (500 * hitObstacles);
        if (!Save.getUser().getUsername().equals("Guest")) {
            Save.getUser().setMaxLevel(currentLevel.getLevel() + 1);
            for (User user : Save.getUsers()) {
                if (user.getUsername().equals(Save.getUser().getUsername())) {
                    user.setMaxLevel(currentLevel.getLevel() + 1);
                }
            }
            if (finalScore > Save.getUser().getHighScore()) {
                Save.getUser().setHighScore(finalScore);
                for (User user : Save.getUsers()) {
                    if (user.getUsername().equals(Save.getUser().getUsername())) {
                        user.setHighScore(finalScore);
                    }
                }
            }
            String username = Save.getUser().getUsername();
            Leaderboard_B leaderboard = Save.getLeaderboard();
            if (leaderboard.isNewHighScore(finalScore)) {
                leaderboard.addScore(username, finalScore);
            }
            try {
                Save.saveData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        JDialog winnerDialog = new JDialog();
        winnerDialog.setSize(400, 300);
        winnerDialog.setLocationRelativeTo(null);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        winnerDialog.add(labelPanel, BorderLayout.CENTER);

        Font fontBig = new Font("Arial", Font.BOLD, 24);
        Font fontNormal = new Font("Arial", Font.PLAIN, 20);

        labelPanel.add(Box.createVerticalGlue());

        JLabel wellDoneLabel = new JLabel("Well Done!", SwingConstants.CENTER);
        wellDoneLabel.setFont(fontBig);
        wellDoneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPanel.add(wellDoneLabel);

        labelPanel.add(Box.createVerticalStrut(20));

        JLabel totalScoreLabel = new JLabel("Total Score: " + score, SwingConstants.CENTER);
        totalScoreLabel.setFont(fontNormal);
        totalScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPanel.add(totalScoreLabel);

        JLabel obstaclesHitLabel = new JLabel("Obstacles Hit: " + hitObstacles + " (Score - " + (500 * hitObstacles) + ")", SwingConstants.CENTER);
        obstaclesHitLabel.setFont(fontNormal);
        obstaclesHitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPanel.add(obstaclesHitLabel);

        JLabel livesLostLabel = new JLabel("Lives Left: " + (lives) + " (Score + " + (2000 * (lives)) + ")", SwingConstants.CENTER);
        livesLostLabel.setFont(fontNormal);
        livesLostLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPanel.add(livesLostLabel);

        JLabel finalScoreLabel = new JLabel("Final Score: " + finalScore, SwingConstants.CENTER);
        finalScoreLabel.setFont(fontNormal);
        finalScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPanel.add(finalScoreLabel);

        labelPanel.add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel();
        winnerDialog.add(buttonPanel, BorderLayout.PAGE_END);

        JButton levelsButton = new JButton("Next Level");
        levelsButton.addActionListener(e -> {
            winnerDialog.dispose();
            Window topWindow = SwingUtilities.getWindowAncestor(this);
            topWindow.dispose();
            int current = currentLevel.getLevel();
            Config config = new Config();
            try {
                config.loadConfig();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            Levels level = config.getLevels().get(current);
            GameControl gameControll = new GameControl(level);
            JFrame frame = new JFrame("Ocean Odyssey");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
            frame.add(gameControll);
            frame.setVisible(true);
            gameControll.start();
        });

        buttonPanel.add(levelsButton);

        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(e -> {
            winnerDialog.dispose();
            Window topWindow = SwingUtilities.getWindowAncestor(this);
            topWindow.dispose();

            menu menuScreen = new menu();
            menuScreen.setVisible(true);
        });
        buttonPanel.add(mainMenuButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> {
            winnerDialog.dispose();
            System.exit(0);
        });
        buttonPanel.add(quitButton);

        winnerDialog.setModal(true);
        winnerDialog.setVisible(true);
    }

}