import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {
    private Score score;

    @BeforeEach
    void setUp() {
        // Initialize a Score object before each test with a known player name and score.
        score = new Score("TestPlayer", 100);
    }

    @AfterEach
    void tearDown() {
        // Reset the score object to null to clean up after each test.
        score = null;
    }

    @Test
    void getPlayerName() {
        // Test that getPlayerName correctly returns the player name set in setUp.
        String expectedPlayerName = "TestPlayer";
        String actualPlayerName = score.getPlayerName();
        assertEquals(expectedPlayerName, actualPlayerName, "getPlayerName should return the correct player name.");
    }

    @Test
    void getScore() {
        // Test that getScore correctly returns the score set in setUp.
        int expectedScore = 100;
        int actualScore = score.getScore();
        assertEquals(expectedScore, actualScore, "getScore should return the correct score.");
    }
}
