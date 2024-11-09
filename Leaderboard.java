import java.util.ArrayList;

public class Leaderboard {
    private ArrayList<String> highScores;

    public Leaderboard() {
        highScores = new ArrayList<>();
    }

    public void addScore(String score) {
        highScores.add(score);
    }

    public ArrayList<String> getHighScores() {
        return highScores;
    }
}
