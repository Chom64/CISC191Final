import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * Lead Author(s):
 * @Zhao Chen
 * @Christopher Hom
 * Other contributors:
 * <<add additional contributors (mentors, tutors, friends) here, with contact information>>
 * Version/date: 12-13-2024
 * Responsibilities of class:
 * The Leaderboard class manages and displays the top 5 players for each difficulty level.
 */
public class Leaderboard {
    private LinkedList<LeaderboardEntry> easyEntries;
    private LinkedList<LeaderboardEntry> mediumEntries;
    private LinkedList<LeaderboardEntry> hardEntries;

    public Leaderboard() {
        easyEntries = new LinkedList<>();
        mediumEntries = new LinkedList<>();
        hardEntries = new LinkedList<>();
        loadLeaderboardFromFile();
    }

    /**
     * Adds a new entry to the leaderboard.
     * @param playerName The name of the player.
     * @param timeElapsed The time elapsed by the player to finish the game.
     * @param difficulty The difficulty level of the game (e.g., Easy, Medium, Hard).
     */
    public void addEntry(String playerName, int timeElapsed, String difficulty) {
        LeaderboardEntry entry = new LeaderboardEntry(playerName, timeElapsed, difficulty);

        // Add entry to the appropriate difficulty list
        try {
            if (difficulty.equalsIgnoreCase("Easy")) {
                easyEntries.add(entry);
                Collections.sort(easyEntries);
                if (easyEntries.size() > 5) easyEntries.removeLast();
            } else if (difficulty.equalsIgnoreCase("Medium")) {
                mediumEntries.add(entry);
                Collections.sort(mediumEntries);
                if (mediumEntries.size() > 5) mediumEntries.removeLast();
            } else if (difficulty.equalsIgnoreCase("Hard")) {
                hardEntries.add(entry);
                Collections.sort(hardEntries);
                if (hardEntries.size() > 5) hardEntries.removeLast();
            } else {
                throw new IllegalArgumentException("Invalid difficulty level: " + difficulty);
            }

            saveLeaderboardToFile();
        } catch (Exception e) {
            System.err.println("Error adding entry: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Displays the leaderboard for a given difficulty.
     * @param difficulty The difficulty level of the leaderboard to display (e.g., "Easy", "Medium", "Hard").
     */
    public void displayLeaderboard(String difficulty) {
        try {
            StringBuilder leaderboardText = new StringBuilder("Leaderboard (" + difficulty + "):\n");

            LinkedList<LeaderboardEntry> entries = getLeaderboardEntriesForDifficulty(difficulty);
            int rank = 1;
            for (LeaderboardEntry entry : entries) {
                leaderboardText.append(rank++)
                        .append(". ")
                        .append(entry.getPlayerName())
                        .append(" - Time: ")
                        .append(entry.getFormattedTime())
                        .append("\n");
            }

            JOptionPane.showMessageDialog(null, leaderboardText.toString());
        } catch (Exception e) {
            System.err.println("Error displaying leaderboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Get leaderboard entries for a specific difficulty
    private LinkedList<LeaderboardEntry> getLeaderboardEntriesForDifficulty(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                return easyEntries;
            case "medium":
                return mediumEntries;
            case "hard":
                return hardEntries;
            default:
                throw new IllegalArgumentException("Invalid difficulty level: " + difficulty);
        }
    }

    // Save all leaderboard data to file
    private void saveLeaderboardToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("leaderboard.txt"))) {
            saveEntriesToFile(writer, "Easy", easyEntries);
            saveEntriesToFile(writer, "Medium", mediumEntries);
            saveEntriesToFile(writer, "Hard", hardEntries);
        } catch (IOException e) {
            System.err.println("Error saving leaderboard to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveEntriesToFile(BufferedWriter writer, String difficulty, LinkedList<LeaderboardEntry> entries) throws IOException {
        for (LeaderboardEntry entry : entries) {
            writer.write(entry.getDifficulty() + "," + entry.getPlayerName() + "," + entry.getFormattedTime());
            writer.newLine();
        }
    }

    private void loadLeaderboardFromFile() {
        File file = new File("leaderboard.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating leaderboard file: " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length != 3) {
                    System.err.println("Invalid leaderboard entry format: " + line);
                    continue;
                }

                try {
                    String difficulty = data[0];
                    String playerName = data[1];
                    int timeElapsed = Integer.parseInt(data[2]);

                    LeaderboardEntry entry = new LeaderboardEntry(playerName, timeElapsed, difficulty);
                    switch (difficulty.toLowerCase()) {
                        case "easy":
                            easyEntries.add(entry);
                            break;
                        case "medium":
                            mediumEntries.add(entry);
                            break;
                        case "hard":
                            hardEntries.add(entry);
                            break;
                        default:
                            System.err.println("Unknown difficulty level: " + difficulty);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing time for entry: " + line);
                }
            }

            easyEntries.sort(null);
            mediumEntries.sort(null);
            hardEntries.sort(null);

            truncateList(easyEntries);
            truncateList(mediumEntries);
            truncateList(hardEntries);
        } catch (IOException e) {
            System.err.println("Error loading leaderboard from file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void truncateList(LinkedList<LeaderboardEntry> entries) {
        while (entries.size() > 5) {
            entries.removeLast();
        }
    }

    // Leaderboard entry class
    private static class LeaderboardEntry implements Comparable<LeaderboardEntry> {
        private final String playerName;
        private final int timeElapsed;
        private final String difficulty;

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
            return Integer.compare(this.timeElapsed, other.timeElapsed);
        }
    }
}