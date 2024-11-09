public class ScoreManager {
    private int currentScore;
    private int highScore;

    public ScoreManager() {
        currentScore = 0;
        highScore = 0;
    }

    public void updateScore(int score) {
        currentScore += score;
        if (currentScore > highScore) {
            highScore = currentScore;
        }
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getHighScore() {
        return highScore;
    }
}
