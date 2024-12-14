import java.util.Random;

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
 * GameBoard class represents the underlying game logic for Minesweeper.
 * 
 */ 

public class GameBoard {
	// Constants for different difficulty levels
    private static final int EASY_GRID_SIZE = 8;
    private static final int MIDDLE_GRID_SIZE = 10;
    private static final int HARD_GRID_SIZE = 12;
    
    // Instance variables to hold game data
    private int GRID_SIZE;
    private int NUM_MINES;
    private int[][] grid;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private int revealedCount = 0;

    /**
     * Constructor to initialize the game board with a specified difficulty.
     * 
     * @param difficulty The difficulty level ("easy", "middle", "hard")
     */
    public GameBoard(String difficulty) {
        // Set grid size and mines based on difficulty
        switch (difficulty) {
            case "medium":
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
        
        // Initialize grid and auxiliary arrays
        grid = new int[GRID_SIZE][GRID_SIZE];
        revealed = new boolean[GRID_SIZE][GRID_SIZE];
        flagged = new boolean[GRID_SIZE][GRID_SIZE];
        initializeGame();  // Ensure the game is initialized after setting grid size and mines
    }

    /**
     * Initializes the game by placing mines randomly on the grid and calculating 
     * the number of adjacent mines for each non-mine cell.
     */
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

    //Getter methods for accessing game data

    /**
     * Gets the grid size (width or height) of the game.
     * 
     * @return The size of the grid 
     */
    public int getGridSize() {
        return GRID_SIZE;
    }

    /**
     * Gets the number of mines in the grid.
     * 
     * @return The number of mines on the game board.
     */
    public int getNumMines() {
        return NUM_MINES;
    }

    /**
     * Gets the value of a specific cell.
     * 
     * @param row Row index of the cell.
     * @param col Column index of the cell.
     * @return The value of the cell: -1 for a mine, otherwise the number of adjacent mines.
     */
    public int getCellValue(int row, int col) {
        return grid[row][col];
    }

    /**
     * Checks if a cell has been revealed.
     * 
     * @param row Row index of the cell.
     * @param col Column index of the cell.
     * @return True if the cell has been revealed, otherwise false.
     */
    public boolean isRevealed(int row, int col) {
        return revealed[row][col];
    }

    /**
     * Reveals a specific cell on the grid.
     * 
     * @param row Row index of the cell.
     * @param col Column index of the cell.
     */
    public void revealCell(int row, int col) {
        revealed[row][col] = true;
        revealedCount++;
    }

    /**
     * Flags or un-flags a specific cell.
     * 
     * @param row Row index of the cell.
     * @param col Column index of the cell.
     */
    public void flagCell(int row, int col) {
        flagged[row][col] = !flagged[row][col];
    }

    /**
     * Checks if a cell is flagged.
     * 
     * @param row Row index of the cell.
     * @param col Column index of the cell.
     * @return True if the cell is flagged, otherwise false.
     */
    public boolean isFlagged(int row, int col) {
        return flagged[row][col];
    }

    /**
     * Gets the count of revealed cells.
     * 
     * @return The number of cells that have been revealed.
     */
    public int getRevealedCount() {
        return revealedCount;
    }

    /**
     * Checks if the game is over. 
     * 
     * @return True if the game is over, otherwise false.
     */
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
    /**
     * Gets the number of rows in the grid.
     * 
     * @return The number of rows in the grid (equal to GRID_SIZE).
     */
    public int getRowCount() {
        return GRID_SIZE;
    }

    /**
     * Gets the number of columns in the grid.
     * 
     * @return The number of columns in the grid (equal to GRID_SIZE).
     */
    public int getColCount() {
        return GRID_SIZE;
    }
}
