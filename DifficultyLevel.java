import java.util.HashMap;
import java.util.Map;

public class DifficultyLevel {
    private String level;
    private Map<String, DifficultySettings> difficultySettings;

    public DifficultyLevel() {
        difficultySettings = new HashMap<>();
        initializeSettings();
        setDifficulty("Easy"); // Set default difficulty level
    }

    private void initializeSettings() {
        difficultySettings.put("Easy", new DifficultySettings(8, 8, 10));
        difficultySettings.put("Medium", new DifficultySettings(16, 16, 40));
        difficultySettings.put("Hard", new DifficultySettings(24, 24, 99));
    }

    public void setDifficulty(String level) {
        this.level = level;
    }

    public DifficultySettings getCurrentSettings() {
        return difficultySettings.get(level);
    }

    public String getCurrentLevel() {
        return level;
    }

    public static class DifficultySettings {
        private int width;
        private int height;
        private int mineCount;

        public DifficultySettings(int width, int height, int mineCount) {
            this.width = width;
            this.height = height;
            this.mineCount = mineCount;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getMineCount() {
            return mineCount;
        }
    }
}
