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
 * The {@code GameControllerMulti} class represents the control system for a multiplayer game.
 * It handles the game loop, rendering, and user input for two players controlling submarines.
 * The game includes features like a countdown before starting, collision detection, scoring, and lives tracking.
 * Additionally, it provides a settings menu and displays the game over screen.
 *
 *  @author Brian Hsu
 *  @version 5.0
 */
public class GameControllerMulti extends Canvas implements Runnable, MouseListener {
    private boolean running = false;
    private boolean isPaused = false;
    private Thread thread;
    private Player player1;
    private Player player2;
    public static int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private int countdownTimer = 3;
    private boolean showReady = true;
    private long readyStartTime;
    private long lastTimeCheck = System.currentTimeMillis();
    private BufferedImage submarineImage;
    private BufferedImage obstacleImage;
    private BufferedImage backgroundImage;
    private BufferedImage backgroundImage2;
    private BufferedImage settingsButtonImage;
    private BufferedImage heartImage;
    private float bgX = 0;
    private float bgX2 = WIDTH;
    private final ObstacleGenerator obstacleGenerator;
    private Rectangle settingsButtonBounds;
    private long currentTimeMillis = System.currentTimeMillis();
    private long lastTimeMillis = System.currentTimeMillis();
    private long timeElapsed = 0;
    private long currentTimeInterval = 0;
    private int score = 0;
    private int livesPlayer1;
    private int livesPlayer2;
    private boolean crashDialogShown = false;
    private Levels currentLevel;
    private float speed;

    /**
     * Creates a new GameControllerMulti instance, initializes game resources,
     * sets up the game environment, and prepares the initial game state.
     */
    public GameControllerMulti() {
        loadResources();
        settingsButtonBounds = new Rectangle(WIDTH - 100, 10, 80, 80); // Adjust size and position
        this.addMouseListener(this);
        readyStartTime = System.currentTimeMillis();
        player1 = new Player(1000, 250, submarineImage);
        player2 = new Player(250, 250, submarineImage);
        livesPlayer1 = 3;
        livesPlayer2 = 3;
        currentLevel = new Levels(1, 1, 10, 10, 58000); //ADJUST TO LEVEL
        obstacleGenerator = new ObstacleGenerator(currentLevel, obstacleImage);
        this.addKeyListener(new KeyInput(this));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
    }

    /**
     * Loads images and other resources needed for the game.
     */
    private void loadResources() {
        try {
            // Skin sub = Save.getUser().getCurrentSkin();
            // sub.getName()
            submarineImage = ImageIO.read(getClass().getResource("Pirate.png"));
            obstacleImage = ImageIO.read(getClass().getResource("db.png"));
            backgroundImage = ImageIO.read(getClass().getResource("background.png"));
            backgroundImage2 = ImageIO.read(getClass().getResource("background2.png"));
            settingsButtonImage = ImageIO.read(getClass().getResource("Sbutton.png"));
            heartImage = ImageIO.read(getClass().getResource("heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the game thread and sets the game state to running.
     */
    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stops the game thread and exits the game.
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
     * Renders the "Ready" screen at the start of the game.
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
        player2.render(g);
        drawLivesPlayer1(g);
        drawLivesPlayer2(g);
        drawScore(g);
        drawOverlayAndText(g, "READY");
        g.dispose();
        bs.show();
    }

    /**
     * Renders the countdown screen before the game starts.
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
        player2.render(g);
        drawLivesPlayer1(g);
        drawLivesPlayer2(g);
        drawScore(g);
        drawOverlayAndText(g, String.valueOf(countdownTimer));
        g.dispose();
        bs.show();
    }

    /**
     * Draws the overlay and text on the screen.
     *
     * @param g The graphics context to use for drawing.
     * @param text The text to be displayed.
     */
    private void drawOverlayAndText(Graphics g, String text) {
        g.setColor(new Color(0, 0, 0, 123));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setFont(new Font("Comic Sans", Font.BOLD, 50));
        g.setColor(Color.WHITE);
        if (text == "READY") {
            g.drawString(text, WIDTH / 2 - 100, HEIGHT / 2);
        }
        else {
            g.drawString(text, WIDTH / 2, HEIGHT / 2);
        }
    }

    /**
     * Draws the settings button on the screen.
     *
     * @param g The graphics context to use for drawing.
     */
    private void drawSettingsButton(Graphics g) {
        if (settingsButtonImage != null) {
            g.drawImage(settingsButtonImage, settingsButtonBounds.x, settingsButtonBounds.y, settingsButtonBounds.width, settingsButtonBounds.height, null);
        }
    }

    /**
     * Renders the initial state of the background.
     *
     * @param g The graphics context to use for drawing.
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
     * Method invoked when the mouse is clicked. Used to handle interactions with the settings button.
     *
     * @param e The MouseEvent containing details of the click.
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
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Updates the game state, including moving backgrounds, updating player positions, and handling collisions.
     */
    private void tick() {
        currentTimeMillis = System.currentTimeMillis();
        if (lastTimeMillis == 0) {
            lastTimeMillis = currentTimeMillis;
        }
        currentTimeInterval = currentTimeMillis - lastTimeMillis;
        lastTimeMillis = currentTimeMillis;
        timeElapsed += currentTimeInterval;
        double speedIncrease = (((double) currentLevel.getInitialSpeed()) /10000) + ((((double) currentLevel.getSpeedMultiplier())/1000000) * timeElapsed); //insert distance travelled instead of *1
        speed = (float) (0.5 + speedIncrease);
        score += Math.round(speed * currentTimeInterval);
        bgX += speed;
        bgX2 += speed;
        if (bgX >= WIDTH) {
            bgX = -WIDTH;
        }
        if (bgX2 >= WIDTH) {
            bgX2 = -WIDTH;
        }
        player1.tick();
        player2.tick();
        obstacleGenerator.tick(speed);
        // Update game logic here
    }

    /**
     * The main game loop that handles rendering and state updates.
     */
    @Override
    public void run() {
        while (running) {
            long now = System.currentTimeMillis();
            if (isPaused) {
                player1.setVelX(0);
                player1.setVelY(0);
                player2.setVelX(0);
                player2.setVelY(0);
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
                    player2.tick();
                    renderReady();
                } else if (countdownTimer > 0) {
                    if (now - lastTimeCheck >= 1000) {
                        countdownTimer--;
                        lastTimeCheck = now;
                    }
                    player1.tick();
                    player2.tick();
                    renderCountdown();
                } else {
                    if (timeElapsed == 0 && score == 0) {
                        lastTimeMillis = now;  // Initialize lastTimeMillis at the exact moment the gameplay starts
                        tick();
                    }
                    tick();
                    if (checkForCollisionPlayer1()) {
                        player1.setVelX(0);
                        player1.setVelY(0);
                        continue;
                    }
                    if (checkForCollisionPlayer2()) {
                        player2.setVelX(0);
                        player2.setVelY(0);
                        continue;
                    }
                    render();
                }
            }
        }
        stop();
    }

    /**
     * Renders the current game state to the screen.
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
        player2.render(g);
        obstacleGenerator.render(g);
        drawLivesPlayer1(g);
        drawLivesPlayer2(g);
        // Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans", Font.BOLD, 40));
        g.drawString(" " + score, 20, 65);
        // Settings
        if (settingsButtonImage != null) {
            g.drawImage(settingsButtonImage, settingsButtonBounds.x, settingsButtonBounds.y, settingsButtonBounds.width, settingsButtonBounds.height, null);
        }

        g.dispose();
        bs.show();
    }

    /**
     * Draws the remaining lives for Player 1 on the screen.
     *
     * @param g The graphics context to use for drawing.
     */
    private void drawLivesPlayer1(Graphics g) {
        int heartWidth = settingsButtonBounds.width;
        int heartHeight = settingsButtonBounds.height;
        int gap = 10;
        int totalWidth = (heartWidth + gap) * 3;
        int startX = WIDTH - totalWidth - 100;
        for (int i = 0; i < livesPlayer1; i++) {
            if (heartImage != null) {
                int x = startX + (heartWidth + gap) * i;
                g.drawImage(heartImage, x, settingsButtonBounds.y, heartWidth, heartHeight, null);
            }
        }
    }

    /**
     * Draws the remaining lives for Player 2 on the screen.
     *
     * @param g The graphics context to use for drawing.
     */
    private void drawLivesPlayer2(Graphics g) {
        int heartWidth = settingsButtonBounds.width;
        int heartHeight = settingsButtonBounds.height;
        int gap = 10;
        int totalWidth = (heartWidth + gap) * 3;
        int startX = WIDTH - totalWidth - 1000;
        for (int i = 0; i < livesPlayer2; i++) {
            if (heartImage != null) {
                int x = startX + (heartWidth + gap) * i;
                g.drawImage(heartImage, x, settingsButtonBounds.y, heartWidth, heartHeight, null);
            }
        }
    }

    /**
     * Draws the current score on the screen.
     *
     * @param g The graphics context to use for drawing.
     */
    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans", Font.BOLD, 40));
        g.drawString(" " + score, 20, 65);
    }

    /**
     * Handles keyboard input for game control.
     *
     * @param e The KeyEvent containing details of the key press.
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            player2.setVelY(-2);
        } else if (key == KeyEvent.VK_S) {
            player2.setVelY(2);
        } else if (key == KeyEvent.VK_A) {
            player2.setVelX(-2);
        } else if (key == KeyEvent.VK_D) {
            player2.setVelX(2);
        }
        if (key == KeyEvent.VK_UP) {
            player1.setVelY(-2);
        } else if (key == KeyEvent.VK_DOWN) {
            player1.setVelY(2);
        } else if (key == KeyEvent.VK_LEFT) {
            player1.setVelX(-2);
        } else if (key == KeyEvent.VK_RIGHT) {
            player1.setVelX(2);
        }
    }

    /**
     * Handles keyboard input when keys are released.
     *
     * @param e The KeyEvent containing details of the key release.
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            player2.setVelY(0);
        } else if (key == KeyEvent.VK_S) {
            player2.setVelY(0);
        } else if (key == KeyEvent.VK_A) {
            player2.setVelX(0);
        } else if (key == KeyEvent.VK_D) {
            player2.setVelX(0);
        }
        if (key == KeyEvent.VK_UP) {
            player1.setVelY(0);
        } else if (key == KeyEvent.VK_DOWN) {
            player1.setVelY(0);
        } else if (key == KeyEvent.VK_LEFT) {
            player1.setVelX(0);
        } else if (key == KeyEvent.VK_RIGHT) {
            player1.setVelX(0);
        }
    }

    /**
     * Checks and handles collisions for Player 1.
     *
     * @return {@code true} if a collision occurred, {@code false} otherwise.
     */
    private boolean checkForCollisionPlayer1() {
        Rectangle playerBounds = player1.getBounds();
        for (Obstacle obstacle : obstacleGenerator.getObstacles()) {
            if (obstacle.isActive() && playerBounds.intersects(obstacle.getBounds())) {
                obstacle.setActive(false);
                if (!crashDialogShown) {
                    crashDialogShown = true;
                    decrementLivesPlayer1();
                    return true; // Add return statement to exit the loop after decrementing lives
                }
            }
        }
        crashDialogShown = false; // Reset crashDialogShown flag
        return false;
    }

    /**
     * Checks and handles collisions for Player 2.
     *
     * @return {@code true} if a collision occurred, {@code false} otherwise.
     */
    private boolean checkForCollisionPlayer2() {
        Rectangle playerBounds = player2.getBounds();
        for (Obstacle obstacle : obstacleGenerator.getObstacles()) {
            if (obstacle.isActive() && playerBounds.intersects(obstacle.getBounds())) {
                obstacle.setActive(false);
                if (!crashDialogShown) {
                    crashDialogShown = true;
                    decrementLivesPlayer2();
                    return true; // Add return statement to exit the loop after decrementing lives
                }
            }
        }
        crashDialogShown = false; // Reset crashDialogShown flag
        return false;
    }


    /**
     * Decrements the life count for Player 1 and checks for game over conditions.
     */
    public void decrementLivesPlayer1() {
        livesPlayer1--;
        if (livesPlayer1 <= 0) {
            running = false;
            showGameOverScreenPlayer1Win();
        }
    }

    /**
     * Decrements the life count for Player 2 and checks for game over conditions.
     */
    public void decrementLivesPlayer2() {
        livesPlayer2--;
        if (livesPlayer2 <= 0) {
            running = false;
            showGameOverScreenPlayer2Win();
        }
    }

    /**
     * Displays the game over screen with the message that Player 1 has won.
     */
    private void showGameOverScreenPlayer1Win() {
        JDialog gameOverDialog = new JDialog();
        gameOverDialog.setSize(300, 200);
        gameOverDialog.setLayout(new BorderLayout());
        gameOverDialog.setLocationRelativeTo(null);
        JLabel gameOverLabel = new JLabel("Game Over, Player 1 Wins", SwingConstants.CENTER);
        gameOverDialog.add(gameOverLabel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        gameOverDialog.add(buttonPanel, BorderLayout.SOUTH);
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(e -> {
            gameOverDialog.dispose();
            menu menuScreen = new menu();
            menuScreen.setVisible(true);
            Window topWindow = SwingUtilities.getWindowAncestor(this);
            if (topWindow != null) {
                topWindow.setVisible(false);  // Hide the game window
            }
        });
        buttonPanel.add(mainMenuButton);
        gameOverDialog.setModal(true);
        gameOverDialog.setVisible(true);
    }

    /**
     * Displays the game over screen with the message that Player 2 has won.
     */
    private void showGameOverScreenPlayer2Win() {
        JDialog gameOverDialog = new JDialog();
        gameOverDialog.setSize(300, 200);
        gameOverDialog.setLayout(new BorderLayout());
        gameOverDialog.setLocationRelativeTo(null);
        JLabel gameOverLabel = new JLabel("Game Over, Player 2 Wins", SwingConstants.CENTER);
        gameOverDialog.add(gameOverLabel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        gameOverDialog.add(buttonPanel, BorderLayout.SOUTH);
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(e -> {
            gameOverDialog.dispose();
            Window topWindow = SwingUtilities.getWindowAncestor(this);
            topWindow.dispose();
            menu menuScreen = new menu();
            menuScreen.setVisible(true);
        });
        buttonPanel.add(mainMenuButton);
        gameOverDialog.setModal(true);
        gameOverDialog.setVisible(true);
    }

    /**
     * Resets the game to its initial state for a new game session.
     */
    private void restartGame() {
        // Reset game state
        livesPlayer1 = 3;
        livesPlayer2 = 3;
        score = 0;
        player1.resetPosition();
        player2.resetPosition();
        speed = 1;
        obstacleGenerator.resetObstacles();
        start();
    }
}
