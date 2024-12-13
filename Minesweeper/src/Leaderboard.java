import java.util.*;
import javax.swing.*;

public class Leaderboard {
    private LinkedList<LeaderboardEntry> entries;

    public Leaderboard() {
        entries = new LinkedList<>();
    }

    public void addEntry(String playerName, int timeElapsed, String difficulty) {
        LeaderboardEntry entry = new LeaderboardEntry(playerName, timeElapsed, difficulty);
        entries.add(entry);
        Collections.sort(entries);  // Sort entries based on time
        if (entries.size() > 5) {
            entries.removeLast();  // Keep only top 5 entries
        }
    }

    public void displayLeaderboard() {
        StringBuilder leaderboardText = new StringBuilder("Leaderboard:\n");
        int rank = 1;
        for (LeaderboardEntry entry : entries) {
            leaderboardText.append(rank++)
                    .append(". ")
                    .append(entry.getPlayerName())
                    .append(" - Time: ")
                    .append(entry.getFormattedTime())
                    .append(" - Difficulty: ")
                    .append(entry.getDifficulty())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(null, leaderboardText.toString());
    }

    // Leaderboard entry class to hold player data
    private static class LeaderboardEntry implements Comparable<LeaderboardEntry> {
        private String playerName;
        private int timeElapsed;
        private String difficulty;

        public LeaderboardEntry(String playerName, int timeElapsed, String difficulty) {
            this.playerName = playerName;
            this.timeElapsed = timeElapsed;
            this.difficulty = difficulty;
        }

        public String getPlayerName() {
            return playerName;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public int getFormattedTime() {
            return timeElapsed;
        }

        @Override
        public int compareTo(LeaderboardEntry other) {
            return Long.compare(this.timeElapsed, other.timeElapsed);
        }
    }
}
