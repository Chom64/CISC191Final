import java.util.Random;

public class GameBoard {
    private static final int EASY_GRID_SIZE = 8;
    private static final int MIDDLE_GRID_SIZE = 10;
    private static final int HARD_GRID_SIZE = 12;
    private int GRID_SIZE;
    private int NUM_MINES;
    private int[][] grid;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private int revealedCount = 0;

    public GameBoard(String difficulty) {
        // Set grid size and mines based on difficulty
        switch (difficulty) {
            case "middle":
                GRID_SIZE = MIDDLE_GRID_SIZE;
                NUM_MINES = 15;
                break;
            case "hard":
                GRID_SIZE = HARD_GRID_SIZE;
                NUM_MINES = 20;
                break;
            case "easy":
            default:
                GRID_SIZE = EASY_GRID_SIZE;
                NUM_MINES = 10;
                break;
        }
        
        grid = new int[GRID_SIZE][GRID_SIZE];
        revealed = new boolean[GRID_SIZE][GRID_SIZE];
        flagged = new boolean[GRID_SIZE][GRID_SIZE];
        initializeGame();  // Ensure the game is initialized after setting grid size and mines
    }

    // Method to initialize the game: place mines and calculate adjacent mine counts
    private void initializeGame() {
        Random rand = new Random();

        // Place mines randomly
        for (int i = 0; i < NUM_MINES; i++) {
            int row, col;
            do {
                row = rand.nextInt(GRID_SIZE);
                col = rand.nextInt(GRID_SIZE);
            } while (grid[row][col] == -1);  // Ensure mines don't overlap
            grid[row][col] = -1;  // -1 indicates a mine

            // Update adjacent cells to reflect the number of neighboring mines
            for (int r = -1; r <= 1; r++) {
                for (int c = -1; c <= 1; c++) {
                    if (row + r >= 0 && row + r < GRID_SIZE && col + c >= 0 && col + c < GRID_SIZE && grid[row + r][col + c] != -1) {
                        grid[row + r][col + c]++;
                    }
                }
            }
        }
    }

    // Getter methods
    public int getGridSize() {
        return GRID_SIZE;
    }

    public int getNumMines() {
        return NUM_MINES;
    }

    public int getCellValue(int row, int col) {
        return grid[row][col];
    }

    public boolean isRevealed(int row, int col) {
        return revealed[row][col];
    }

    public void revealCell(int row, int col) {
        revealed[row][col] = true;
        revealedCount++;
    }

    public void flagCell(int row, int col) {
        flagged[row][col] = !flagged[row][col];
    }

    public boolean isFlagged(int row, int col) {
        return flagged[row][col];
    }

    public int getRevealedCount() {
        return revealedCount;
    }

    public boolean isGameOver() {
        //int revealedNonMines = revealedCount;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == -1 && revealed[i][j]) {
                    return true; // A mine was revealed
                }
                if (grid[i][j] != -1 && !revealed[i][j]) {
                    return false; // A non-mine cell is still hidden
                }
            }
        }
        return true; // All non-mines are revealed
    }

    // Add methods to get row and column counts
    public int getRowCount() {
        return GRID_SIZE;
    }

    public int getColCount() {
        return GRID_SIZE;
    }
}
