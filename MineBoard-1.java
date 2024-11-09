import java.util.Random;

public class MineBoard {
    private Cell[][] cells;  // MineBoard HAS-A 2D array of Cell objects
    private int width;       // MineBoard HAS-A width
    private int height;      // MineBoard HAS-A height
    private int mineCount;   // MineBoard HAS-A mineCount

    // Constructor initializes the board with given dimensions and number of mines
    public MineBoard(int width, int height, int mineCount) {
        this.width = width;          // Set the width
        this.height = height;        // Set the height
        this.mineCount = mineCount;  // Set the mine count
        initializeBoard();           // Call method to initialize the board
    }

    // Initializes the board with cells and places mines
    private void initializeBoard() {
        cells = new Cell[width][height]; // Create a new 2D array of Cell objects
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(i, j); // Initialize each cell with its coordinates
            }
        }
        placeMines();             // Call method to randomly place mines on the board
        calculateAdjacentMines(); // Call method to calculate the number of adjacent mines for each cell
    }

    // Randomly places mines on the board
    private void placeMines() {
        Random random = new Random(); // Create a Random object for generating random positions
        for (int i = 0; i < mineCount; i++) { // Loop until the specified number of mines is placed
            int x, y; // Coordinates for the mine
            do {
                x = random.nextInt(width);  // Generate a random x-coordinate
                y = random.nextInt(height); // Generate a random y-coordinate
            } while (cells[x][y].isMine()); // Ensure a mine is not placed on an already mined cell
            cells[x][y].setMine(true); // Set the cell as a mine
        }
    }

    // Calculates the number of adjacent mines for each cell
    private void calculateAdjacentMines() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!cells[i][j].isMine()) { // Only calculate for non-mine cells
                    cells[i][j].setAdjacentMines(countAdjacentMines(i, j)); // Set the adjacent mine count
                }
            }
        }
    }

    // Counts the number of mines adjacent to the specified cell
    private int countAdjacentMines(int x, int y) {
        int count = 0; // Counter for adjacent mines
        for (int i = -1; i <= 1; i++) { // Loop through neighboring cells
            for (int j = -1; j <= 1; j++) {
                if (isInBounds(x + i, y + j) && cells[x + i][y + j].isMine()) { // Check bounds and if it's a mine
                    count++; // Increment the counter if a mine is found
                }
            }
        }
        return count; // Return the total count of adjacent mines
    }

    // Checks if the given coordinates are within the bounds of the board
    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height; // Return true if within bounds
    }

    // Reveals the cell at the specified coordinates
    public boolean revealCell(int x, int y) {
        if (isInBounds(x, y) && !cells[x][y].isRevealed()) { // Check bounds and if the cell is not already revealed
            boolean revealed = cells[x][y].reveal(); // Attempt to reveal the cell
            if (revealed) {
                if (cells[x][y].getAdjacentMines() == 0) { // If the cell has no adjacent mines
                    for (int i = -1; i <= 1; i++) { // Reveal adjacent cells
                        for (int j = -1; j <= 1; j++) {
                            if (isInBounds(x + i, y + j) && !(i == 0 && j == 0)) { // Avoid revealing the current cell
                                revealCell(x + i, y + j); // Recursively reveal adjacent cells
                            }
                        }
                    }
                }
                return true; // Return true if the cell was successfully revealed
            }
        }
        return false; // Return false if the cell could not be revealed
    }

    // Checks if the game is over (i.e., a mine has been revealed)
    public boolean isGameOver() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (cells[i][j].isRevealed() && cells[i][j].isMine()) { // Check if any revealed cell is a mine
                    return true; // Return true if the game is over
                }
            }
        }
        return false; // Return false if the game is still ongoing
    }

    // Resets the board for a new game
    public void reset() {
        initializeBoard(); // Reinitialize the board
    }

    // Getter for the cells array
    public Cell[][] getCells() {
        return cells; // Return the 2D array of cells
    }
}
