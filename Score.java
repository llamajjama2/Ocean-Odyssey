/**
 * Represents a score entry in a game, encapsulating the player's name and their score.
 * This class provides a straightforward way to manage score data, making it easy to
 * store, retrieve, and display scores within a leaderboard or similar game feature.
 *
 * @author Haadi
 * @version 3.0
 */
public class Score {
    private String playerName; // The name of the player associated with the score
    private int score; // The numerical score achieved by the player

    /**
     * Constructs a new Score object with the specified player name and score.
     *
     * @param playerName The name of the player.
     * @param score The score achieved by the player.
     */
    public Score(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    /**
     * Returns the name of the player associated with this score.
     *
     * @return The player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Returns the score achieved by the player.
     *
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }
}
