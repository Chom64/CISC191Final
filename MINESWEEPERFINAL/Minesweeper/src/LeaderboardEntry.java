/**
 * Lead Author(s):
 * 
 * @Zhao Chen
 * @Christopher Hom
 *         <<add additional lead authors here, with a full first and last name>>
 * 
 * Other contributors:
 *         <<add additional contributors (mentors, tutors, friends) here, with
 *         contact information>>
 * 
 * 
 * Version/date: 12-12-2024
 * 
 * Responsibilities of class: 
 * The LeaderboardEntry class represents an individual entry in the leaderboard.
 * 
 */ 
public class LeaderboardEntry {
    private String name;
    private int timeElapsed;
    private String difficulty;

    /**
     * Constructor to initialize a new leaderboard entry.
     * 
     * @param name The name of the player.
     * @param timeElapsed The time the player took to complete the game (in seconds).
     * @param difficulty The difficulty level of the game (e.g., "Easy", "Medium", "Hard").
     */
    public LeaderboardEntry(String name, int timeElapsed, String difficulty) {
        this.name = name;
        this.timeElapsed = timeElapsed;
        this.difficulty = difficulty;
    }

    /**
     * Returns the player's name.
     * 
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the time the player took to finish the game.
     * 
     * @return The time elapsed in seconds.
     */
    public int getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * Returns the difficulty level of the game.
     * 
     * @return The difficulty level (e.g., "Easy", "Medium", "Hard").
     */
    public String getDifficulty() {
        return difficulty;
    }
}
