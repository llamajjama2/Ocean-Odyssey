import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * The {@code CrashDialog} class represents a modal dialog that is displayed
 * when a crash event occurs in the game. It challenges the player with a math
 * problem related to the game's difficulty level. The player must answer correctly
 * to continue. Incorrect answers result in a penalty, such as decrementing lives.
 * This class supports single-player, multiplayer, and generic game control modes
 * through its overloaded constructors.
 *
 * @author Brian Y, Michael
 * @version 3.0
 *
 */
public class CrashDialog extends JDialog {
    private MathProblem mathProblem;
    private JPanel choicesPanel;
    private GameControl gameControl;
    private GameControllerSingle gameControllerSingle;
    private GameControllerMulti gameControllerMulti;

    /**
     * Constructs a CrashDialog for a generic game control interface.
     *
     * @param parent The parent frame to which this dialog is attached.
     * @param difficulty The difficulty level of the math problem to generate.
     * @param gameControl The game control interface for managing game actions.
     */
    public CrashDialog(JFrame parent, int difficulty, GameControl gameControl) {
        super(parent, "Ocean Odyssey", true);
        setModal(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        this.gameControl = gameControl;
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        mathProblem = new MathProblem(difficulty);
        JLabel questionLabel = new JLabel(mathProblem.getQuestion());
        add(questionLabel, BorderLayout.NORTH);

        choicesPanel = new JPanel(new GridLayout(0, 1));
        createChoiceButtons(mathProblem.getChoices());
        add(choicesPanel, BorderLayout.CENTER);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode());
            }
        });
    }
    /**
     * Constructs a crash dialog for a single-player game controller.
     *
     * @param parent The parent frame to which this dialog is attached.
     * @param difficulty The difficulty level of the math problem to generate.
     * @param gameControllerSingle The single-player game controller for managing game actions.
     */
    public CrashDialog(JFrame parent, int difficulty, GameControllerSingle gameControllerSingle) {
        super(parent, "Ocean Odyssey", true);
        setModal(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        this.gameControllerSingle = gameControllerSingle;
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        mathProblem = new MathProblem(difficulty);
        JLabel questionLabel = new JLabel(mathProblem.getQuestion());
        add(questionLabel, BorderLayout.NORTH);

        choicesPanel = new JPanel(new GridLayout(0, 1));
        createChoiceButtons(mathProblem.getChoices());
        add(choicesPanel, BorderLayout.CENTER);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode());
            }
        });
    }
    /**
     * Constructs a crash dialog for a multiplayer game controller.
     *
     * @param parent The parent frame to which this dialog is attached.
     * @param difficulty The difficulty level of the math problem to generate.
     * @param gameControllerMulti The multiplayer game controller for managing game actions.
     */
    public CrashDialog(JFrame parent, int difficulty, GameControllerMulti gameControllerMulti) {
        super(parent, "Ocean Odyssey", true);
        setModal(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        this.gameControllerMulti = gameControllerMulti;
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        mathProblem = new MathProblem(difficulty);
        JLabel questionLabel = new JLabel(mathProblem.getQuestion());
        add(questionLabel, BorderLayout.NORTH);

        choicesPanel = new JPanel(new GridLayout(0, 1));
        createChoiceButtons(mathProblem.getChoices());
        add(choicesPanel, BorderLayout.CENTER);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode());
            }
        });
    }

    /**
     * Creates and adds choice buttons based on the possible answers to the math problem.
     * Each button is configured with an action listener that checks the answer when clicked.
     *
     * @param choices A list of integer choices to be presented as possible answers.
     */
    private void createChoiceButtons(List<Integer> choices) {
        int index = 1;
        for (int choice : choices) {
            String buttonText = "(" + index + ") " + choice;
            JRadioButton choiceButton = new JRadioButton(buttonText);
            choiceButton.setActionCommand(String.valueOf(choice));
            choiceButton.addActionListener(e -> checkAnswer(choice));
            choicesPanel.add(choiceButton);
            choiceButton.setMnemonic(KeyEvent.getExtendedKeyCodeForChar(String.valueOf(index).charAt(0)));
            index++;
        }
    }


    /**
     * Handles key press events to allow keyboard input for selecting an answer.
     * Maps number keys to the corresponding answer choices.
     *
     * @param keyCode The code of the key that was pressed.
     */
    private void handleKeyPress(int keyCode) {
        int choiceIndex = keyCode - KeyEvent.VK_1;
        if (choiceIndex >= 0 && choiceIndex < mathProblem.getChoices().size()) {
            checkAnswer(mathProblem.getChoices().get(choiceIndex));
        }
    }

    /**
     * Checks if the selected answer is correct. If so, the dialog is disposed of.
     * Otherwise, an error message is shown, and a life may be decremented based
     * on the game controller's rules. Supports both generic game control and
     * specific single-player or multiplayer controllers.
     *
     * @param selectedAnswer The answer selected by the player.
     */
    private void checkAnswer(int selectedAnswer) {
        if (selectedAnswer == mathProblem.getAnswer()) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect answer", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            if (gameControl != null)
                gameControl.decrementLives();
            if (gameControllerSingle != null)
                gameControllerSingle.decrementLives();
        }
    }


}
