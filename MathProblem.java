import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
/**
 * Represents the math problem generated in our game, including its difficulty, the question, possible answers, and the correct answer.
 * It supports generating problems of varying types and difficulties, tailored to the game level specified.
 *
 * @author Haadi
 * @version 3.0
 */
public class MathProblem {
    private int difficulty; // Difficulty level of the problem.
    private int answer; // Correct answer to the problem.
    private String question; // Textual representation of the problem.
    private List<Integer> choices; // List of answer choices presented to the player.
    private int healthPenalty; // Health penalty for incorrect answer.

    /**
     * Constructs a {@code MathProblem} with a specified level of difficulty. Depending on the difficulty,
     * different types of math problems are generated.
     *
     * @param level The difficulty level of the math problem.
     */
    public MathProblem(int level) {
        difficulty = level; // set difficulty to level
        healthPenalty = 1;
        if (difficulty < 5) {
            genProblem(0);
        } else if (difficulty < 10) {
            genProblem(1);
        } else if (difficulty < 15) {
            genProblem(2);
        } else if (difficulty < 20) {
            genProblem(3);
        } else {
            genProblem(4);
        }
    }
    /**
     * Constructs a {@code MathProblem} with a specified level of difficulty and health penalty for incorrect answers.
     *
     * @param level The difficulty level of the math problem.
     * @param healthPenalty The health penalty for incorrect answers.
     */
    public MathProblem(int level, int healthPenalty) {
        difficulty = level; // set difficulty to level
        this.healthPenalty = healthPenalty;
        if (difficulty < 5) {
            genProblem(0);
        } else if (difficulty < 10) {
            genProblem(1);
        } else if (difficulty < 15) {
            genProblem(2);
        } else if (difficulty < 20) {
            genProblem(3);
        } else {
            genProblem(4);
        }
    }
    /**
     * Generates a {@code MathProblem} based on the specified type. This method supports creating
     * problems of addition, subtraction, multiplication, division, or mixed operations.
     *
     * @param type An integer representing the type of math problem to generate.
     */
    private void genProblem(int type) {
        Random random = new Random();
        this.choices = new ArrayList<>();
        int num = random.nextInt(99) + 1;
        int num1 = random.nextInt(20) + 1;
        int num2 = random.nextInt(20) + 1;
        int num3 = random.nextInt(20) + 1;
        if (type == 0) {
            // generate an addition problem
            this.question = String.format("What is %d + %d", num, num1);
            this.answer = num + num1;
            this.choices.addAll(generateChoices(answer));
        } else if (type == 1) {
            // generate a subtraction problem
            this.question = String.format("What is %d - %d", num, num1);
            this.answer = num - num1;
            this.choices.addAll(generateChoices(answer));
        } else if (type == 2) {
            // generate a multiplication problem
            this.question = String.format("What is %d * %d", num2, num3);
            this.answer = num2 * num3;
            this.choices.addAll(generateChoices(answer));
        } else if (type == 3) {
            // generate a division problem
            num2 = num2 * num3;
            this.question = String.format("What is %d / %d", num2, num3);
            this.answer = num2 / num3;
            this.choices.addAll(generateChoices(answer));
        } else if (type == 4) {
            // generate a mixed problem
            int mix = random.nextInt(6);
            if (mix == 1) {
                // addition and subtraction
                this.question = String.format("What is %d + %d - %d", num, num1, num2);
                this.answer = num + num1 - num2;
                this.choices.addAll(generateChoices(answer));
            } else if (mix == 2) {
                // addition and multiplication
                this.question = String.format("What is %d + (%d * %d)", num, num1, num2);
                this.answer = num + (num1 * num2);
                this.choices.addAll(generateChoices(answer));
            } else if (mix == 3) {
                // addition and division
                num1 = num1 * num2;
                this.question = String.format("What is %d + (%d / %d)", num, num1, num2);
                this.answer = num + (num1 / num2);
                this.choices.addAll(generateChoices(answer));
            } else if (mix == 4) {
                // subtraction and multiplication
                this.question = String.format("What is %d - (%d * %d)", num, num1, num2);
                this.answer = num - (num1 * num2);
                this.choices.addAll(generateChoices(answer));
            } else if (mix == 5) {
                // subtraction and division
                num1 = num1 * num2;
                this.question = String.format("What is %d - (%d / %d)", num, num1, num2);
                this.answer = num - (num1 / num2);
                this.choices.addAll(generateChoices(answer));
            } else if (mix == 6) {
                // multiplication and division
                num1 = num1 * num2;
                this.question = String.format("What is %d * (%d / %d)", num, num1, num2);
                this.answer = num * (num1 / num2);
                this.choices.addAll(generateChoices(answer));
            }
        }
    }

    /**
     * Returns the math problem as a formatted question.
     *
     * @return The question text.
     */
    public String getQuestion() {
        return this.question;
    }
    /**
     * Returns a list of answer choices for the math problem.
     *
     * @return The list of choices.
     */
    public List<Integer> getChoices() {
        return this.choices;
    }
    /**
     * Returns the correct answer to the math problem.
     *
     * @return The correct answer.
     */
    public int getAnswer() {
        return this.answer;
    }
    /**
     * Returns the health penalty for incorrect answers.
     *
     * @return The health penalty.
     */
    public int getHealthPenalty() {
        return this.healthPenalty;
    }
    /**
     * Returns the difficulty level of the math problem.
     *
     * @return The difficulty level.
     */
    public int getDifficulty() {
        return this.difficulty;
    }
    /**
     * Sets the difficulty level for the math problem.
     *
     * @param level The new difficulty level.
     */
    public void setDifficulty(int level) {
        this.difficulty = level;
    }
    /**
     * Sets the health penalty for incorrect answers.
     *
     * @param healthPenalty The new health penalty.
     */
    public void setHealthPenalty(int healthPenalty) {
        this.healthPenalty = healthPenalty;
    }
    /**
     * Sets the question text for the math problem.
     *
     * @param question The new question text.
     */
    public void setQuestion(String question) {
        this.question = question;
    }
    /**
     * Sets the list of answer choices for the math problem.
     *
     * @param choices The new list of answer choices.
     */
    public void setChoices(List<Integer> choices) {
        this.choices = choices;
    }
    /**
     * Sets the correct answer for the math problem.
     *
     * @param answer The new correct answer.
     */
    public void setAnswer(int answer) {
        this.answer = answer;
    }
    /**
     * Generates a set of possible answers for the math problem, including the correct answer
     * and three plausible but incorrect choices.
     *
     * @param answer The correct answer to the problem.
     * @return A list of integers containing the correct answer and three incorrect choices.
     */
    private List<Integer> generateChoices(int answer) {
        Set<Integer> uniqueChoices = new HashSet<>();
        uniqueChoices.add(answer);
        Random random = new Random();

        while(uniqueChoices.size() < 4) {
            int randomChoice = answer + random.nextInt(20) - 10; // range from answer - 10 to answer + 10
            uniqueChoices.add(randomChoice);
        }

        List<Integer> choices = new ArrayList<>(uniqueChoices);
        Collections.shuffle(choices);
        return choices;
    }
}
