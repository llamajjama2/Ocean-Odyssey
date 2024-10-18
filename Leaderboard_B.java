import java.util.PriorityQueue;

/**
 * The {@code Leaderboard_B} class manages the game's leaderboard, keeping track of the top scores.
 * It uses a {@code PriorityQueue} to maintain scores in descending order, ensuring that the leaderboard
 * is always up-to-date with the highest scores.
 *
 * @author Haadi
 * @version 2.0
 */
public class Leaderboard_B {
    private PriorityQueue<Score> scores; // Holds Score objects
    private final int MAX_SIZE = 10; // Maximum size of the leaderboard

    /**
     * Constructs a {@code Leaderboard_B} instance. Initializes the {@code PriorityQueue}
     * to maintain scores in descending order based on the score value.
     */
    public Leaderboard_B() {
        scores = new PriorityQueue<>((a, b) -> b.getScore() - a.getScore());
    }

    /**
     * Adds a new score to the leaderboard. If the leaderboard exceeds its maximum size,
     * the lowest score is removed to maintain the leaderboard's size limit.
     *
     * @param playerName The name of the player.
     * @param score The score achieved by the player.
     */
    public void addScore(String playerName, int score) {
        scores.add(new Score(playerName, score));
        if (scores.size() > MAX_SIZE) {
            scores.poll(); // Remove the lowest score
        }
    }

    /**
     * Checks if a given score qualifies as a new high score eligible for inclusion in the leaderboard.
     *
     * @param score The score to check against the current leaderboard scores.
     * @return {@code true} if the score qualifies as a new high score, {@code false} otherwise.
     */
    public boolean isNewHighScore(int score) {
        if (scores.size() < MAX_SIZE) {
            return true; // Leaderboard isn't full yet
        } else {
            return score > scores.peek().getScore(); // Must beat the lowest high score
        }
    }

    /**
     * Generates a formatted string representation of the leaderboard, showing player names and scores.
     *
     * @return A string representing the formatted leaderboard.
     */
    public String getLeaderboard() {
        StringBuilder sb = new StringBuilder("Leaderboard:\n");
        int rank = 1;
        for (Score highScore : scores) {
            sb.append(rank++).append(". ").append(highScore.getPlayerName())
                    .append(" - ").append(highScore.getScore()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Provides a copy of the scores in the leaderboard.
     *
     * @return A {@code PriorityQueue} copy of the scores.
     */
    public PriorityQueue<Score> getScores() {
        return new PriorityQueue<>(scores);
    }
}
