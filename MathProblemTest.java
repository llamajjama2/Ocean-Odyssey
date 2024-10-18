import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MathProblemTest {

        @Test
        void getQuestion() {
            MathProblem example = new MathProblem(1, 1);

            String question = example.getQuestion();

            // Regular expression to match your question format
            String questionPattern = "What is \\d+ \\+ \\d+";

            Pattern pattern = Pattern.compile(questionPattern);
            Matcher matcher = pattern.matcher(question);

            // Assert that the question matches the pattern
            assertTrue(matcher.matches());
        }

    @Test
    void getChoices() {
        MathProblem example = new MathProblem(1, 1);
        List<Integer> choices = example.getChoices();

        assertEquals(4, choices.size());

        for (Integer choice : choices) {
            assertTrue(choice instanceof Integer);
        }
    }

    @Test
    void getAnswer() {
        MathProblem example = new MathProblem(1, 1);
        Integer answer = example.getAnswer();
        assertTrue(answer instanceof Integer);
    }

    @Test
    void getHealthPenalty() {
        MathProblem example = new MathProblem(1, 1);
        int expResult = 1;
        assertEquals(expResult,example.getHealthPenalty());
    }

    @Test
    void getDifficulty() {
        MathProblem example = new MathProblem(1, 1);
        int expResult = 1;
        assertEquals(expResult, example.getDifficulty());

    }

    /**
     * test for setDifficulty method
     */
    @Test
    void setDifficulty() {
        MathProblem example = new MathProblem(1, 1);
        example.setDifficulty(2);
        assertEquals(2,example.getDifficulty());
    }

    @Test
    void setHealthPenalty() {
        MathProblem example = new MathProblem(1, 1);
        example.setHealthPenalty(2);
        assertEquals(2,example.getHealthPenalty());
    }

    @Test
    void setQuestion() {
        MathProblem example = new MathProblem(1, 1);
        example.setQuestion("This is a question");
        assertEquals("This is a question",example.getQuestion());
    }

    @Test
    void setChoices() {
        MathProblem example = new MathProblem(1, 1);
        List<Integer> choices = example.getChoices();
        List<Integer> i = new ArrayList<>();
        i.add(12);
        i.add(12);
        i.add(12);
        i.add(12);

        example.setChoices(i);

        assertEquals(i,example.getChoices());

    }

    @Test
    void setAnswer() {
        MathProblem example = new MathProblem(1, 1);
        example.setAnswer(12);

        assertEquals(12,example.getAnswer());

    }
}