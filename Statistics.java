public class Statistics {
    private int gamesPlayed;
    private int gamesWon;

    public Statistics() {
        gamesPlayed = 0;
        gamesWon = 0;
    }

    public void gamePlayed() {
        gamesPlayed++;
    }

    public void gameWon() {
        gamesWon++;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }
}
