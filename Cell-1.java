public class Cell {
    private int x;                    // Cell HAS-A x-column index
    private int y;                    // Cell HAS-A y-row index
    private boolean isMine;           // Cell HAS-A boolean check is a mine
    private boolean isRevealed;       // Cell HAS-A boolean check is revealed
    private int adjacentMines;        // Cell HAS-A count of adjacent mines
    private Mine mine;                // Cell HAS-A Mine object

    // Constructor initializes the cell with its coordinates
    public Cell(int x, int y) {
        this.x = x;                  // Set the x-coordinate
        this.y = y;                  // Set the y-coordinate
        this.isMine = false;         // Default to not being a mine
        this.isRevealed = false;     // Default to not being revealed
        this.adjacentMines = 0;      // Initialize adjacent mines count to zero
        this.mine = new Mine();       // Initialize a new Mine object
    }

    // Returns whether this cell is a mine
    public boolean isMine() {
        return isMine; // Return the value of isMine
    }

    // Sets whether this cell is a mine
    public void setMine(boolean isMine) {
        this.isMine = isMine; // Set the value of isMine
    }

    // Returns whether this cell has been revealed
    public boolean isRevealed() {
        return isRevealed; // Return the value of isRevealed
    }

    // Reveals this cell; returns true if it was successfully revealed
    public boolean reveal() {
        if (!isRevealed) { // Check if the cell is not already revealed
            isRevealed = true; // Set the cell as revealed
            return true; // Indicate success
        }
        return false; // Indicate that the cell was already revealed
    }

    // Sets the count of adjacent mines for this cell
    public void setAdjacentMines(int count) {
        this.adjacentMines = count; // Set the count of adjacent mines
    }

    // Returns the count of adjacent mines for this cell
    public int getAdjacentMines() {
        return adjacentMines; // Return the count of adjacent mines
    }

    // Checks if this cell is marked (flagged)
    public boolean isMarked() {
        return mine.isMarked(); // Delegate the check to the Mine object
    }

    // Marks (flags) this cell as a mine (for player to indicate suspicion)
    public void mark() {
        mine.mark(); // Delegate the marking to the Mine object
    }

    // Unmarks (removes the flag from) this cell
    public void unmark() {
        mine.unmark(); // Delegate the unmarking to the Mine object
    }
}
