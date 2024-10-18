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
 * The {@code GameControllerSingle} class extends {@code Canvas} and implements {@code Runnable} and {@code MouseListener}
 * to create a single-player game controller. This class is responsible for initializing the game, loading resources,
 * handling game state, updating game elements, and rendering graphics on the screen.
 *
 *  @author Brian Hsu
 *  @version 4.0
 */
public class GameControllerSingle extends Canvas implements Runnable, MouseListener {
    private boolean running = false;
    private boolean isPaused = false;
    private Thread thread;
    private Player player;
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
    private int lives;
    private boolean crashDialogShown = false;
    private Levels currentLevel;
    private float speed;

    /**
     * Initializes a new {@code GameControllerSingle} instance, loads game resources,
     * sets up the game environment, and prepares the initial game state.
     */
    public GameControllerSingle() {
        loadResources();
        settingsButtonBounds = new Rectangle(WIDTH - 100, 10, 80, 80);
        this.addMouseListener(this);
        readyStartTime = System.currentTimeMillis();
        player = new Player(1000, 250, submarineImage);
        lives = 3;
        currentLevel = new Levels(1, 1, 10, 10, 58000);
        obstacleGenerator = new ObstacleGenerator(currentLevel, obstacleImage);
        this.addKeyListener(new KeyInput(this));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
    }

    /**
     * Loads images and other resources necessary for the game to run.
     */
    private void loadResources() {
        try {
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
     * Renders the initial "Ready" screen at the start of the game.
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
        player.render(g);
        drawLives(g);
        drawScore(g);
        drawOverlayAndText(g, "READY");
        g.dispose();
        bs.show();
    }

    /**
     * Renders the countdown screen before starting actual gameplay.
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
        player.render(g);
        drawLives(g);
        drawScore(g);
        drawOverlayAndText(g, String.valueOf(countdownTimer));
        g.dispose();
        bs.show();
    }

    /**
     * Draws an overlay and text on the screen.
     *
     * @param g    the {@code Graphics} object used for drawing
     * @param text the text to be drawn
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
     * @param g the {@code Graphics} object used for drawing
     */
    private void drawSettingsButton(Graphics g) {
        if (settingsButtonImage != null) {
            g.drawImage(settingsButtonImage, settingsButtonBounds.x, settingsButtonBounds.y, settingsButtonBounds.width, settingsButtonBounds.height, null);
        }
    }

    /**
     * Renders the initial state of the game background.
     *
     * @param g the {@code Graphics} object used for drawing
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
     * Invoked when the mouse button has been clicked (pressed and released) on a component.
     *
     * @param e the event to be processed
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
     * Updates the game logic, player, and obstacles based on the time elapsed.
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
        player.tick();
        obstacleGenerator.tick(speed);
    }

    /**
     * The main game loop that handles rendering and updating the game state.
     */
    @Override
    public void run() {
        while (running) {
            long now = System.currentTimeMillis();
            if (isPaused) {
                player.setVelX(0);
                player.setVelY(0);
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
                    player.tick();
                    renderReady();
                } else if (countdownTimer > 0) {
                    if (now - lastTimeCheck >= 1000) {
                        countdownTimer--;
                        lastTimeCheck = now;
                    }
                    player.tick();
                    renderCountdown();
                } else {
                    if (timeElapsed == 0 && score == 0) {
                        lastTimeMillis = now;
                        tick();
                    }
                    tick();
                    if (checkForCollision()) {
                        player.setVelX(0);
                        player.setVelY(0);
                        continue;
                    }
                    render();
                }
            }
        }
        stop();
    }

    /**
     * Renders the game elements including the background, player, obstacles, and UI elements like score and lives.
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
        player.render(g);
        obstacleGenerator.render(g);
        drawLives(g);
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
     * Draws the player's remaining lives on the screen.
     *
     * @param g the {@code Graphics} object used for drawing
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
     * @param g the {@code Graphics} object used for drawing
     */
    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans", Font.BOLD, 40));
        g.drawString(" " + score, 20, 65);
    }

    /**
     * Responds to key press events to control the player's movement.
     *
     * @param e the event to be processed
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            player.setVelY(-2);
        } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            player.setVelY(2);
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            player.setVelX(-2);
        } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            player.setVelX(2);
        }
    }

    /**
     * Responds to key release events to stop the player's movement.
     *
     * @param e the event to be processed
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            player.setVelY(0);
        } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            player.setVelY(0);
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            player.setVelX(0);
        } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            player.setVelX(0);
        }
    }

    /**
     * Checks for collisions between the player and obstacles, updating the game state accordingly.
     *
     * @return {@code true} if a collision occurs, {@code false} otherwise
     */
    private boolean checkForCollision() {
        Rectangle playerBounds = player.getBounds();
        for (Obstacle obstacle : obstacleGenerator.getObstacles()) {
            if (obstacle.isActive() && playerBounds.intersects(obstacle.getBounds())) {
                obstacle.setActive(false);
                if (!crashDialogShown) {
                    crashDialogShown = true;
                    showCrashDialog();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Shows the crash dialog when the player collides with an obstacle.
     */
    private void showCrashDialog() {
        isPaused = true;
        SwingUtilities.invokeLater(() -> {
            int difficulty = 1;
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
     * Decrements the player's lives and checks for game over condition.
     */
    public void decrementLives() {
        lives--;
        if (lives <= 0) {
            running = false;
            showGameOverScreen();
        }
    }

    /**
     * Displays the game over screen and provides options to restart or exit the game.
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
     * Restarts the game, resetting the game state and elements to their initial conditions.
     */
    private void restartGame() {
        lives = 3;
        score = 0;
        player.resetPosition();
        speed = 1;
        obstacleGenerator.resetObstacles();
        start();
    }
}
