import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class Leaderboard_BTest {

    @Test
    void addScore() {
        Leaderboard_B leaderboard = new Leaderboard_B();
        leaderboard.addScore("Player1", 100);
        leaderboard.addScore("Player2", 200);
        leaderboard.addScore("Player3", 150);
        assertEquals(3, leaderboard.getScores().size());
    }

    @Test
    void isNewHighScore() {
        Leaderboard_B leaderboard = new Leaderboard_B();
        leaderboard.addScore("Player1", 100);
        leaderboard.addScore("Player2", 200);
        leaderboard.addScore("Player3", 150);
        assertTrue(leaderboard.isNewHighScore(50));
        assertTrue(leaderboard.isNewHighScore(175));
    }

    @Test
    void getLeaderboard() {
        Leaderboard_B leaderboard = new Leaderboard_B();
        leaderboard.addScore("Player1", 100);
        leaderboard.addScore("Player2", 200);
        leaderboard.addScore("Player3", 150);
        String expectedLeaderboard = "Leaderboard:\n" +
                "1. Player2 - 200\n" +
                "2. Player1 - 100\n" +
                "3. Player3 - 150\n";
        System.out.println(leaderboard.getLeaderboard());
        assertEquals(expectedLeaderboard, leaderboard.getLeaderboard());

    }

    @Test
    void getScores() {
        Leaderboard_B leaderboard = new Leaderboard_B();
        leaderboard.addScore("Player1", 100);
        leaderboard.addScore("Player2", 200);
        leaderboard.addScore("Player3", 150);
        PriorityQueue<Score> scores = leaderboard.getScores();
        assertEquals(3, scores.size());
        Score firstScore = scores.poll();
        assertEquals("Player2", firstScore.getPlayerName());
        assertEquals(200, firstScore.getScore());
    }
}
