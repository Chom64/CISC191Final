public class LeaderboardEntry {
    private String name;
    private int timeElapsed;
    private String difficulty;

    public LeaderboardEntry(String name, int timeElapsed, String difficulty) {
        this.name = name;
        this.timeElapsed = timeElapsed;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
