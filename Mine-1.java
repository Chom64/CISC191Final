public class Mine {
    private boolean isMarked; // Mine HAS-A boolean check is marked

    // Constructor initializes the mine; by default, it is not marked
    public Mine() {
        this.isMarked = false; // Set the mine's marked status to false
    }

    // Returns whether this mine is marked
    public boolean isMarked() {
        return isMarked; // Return the value of isMarked
    }

    // Marks this mine as suspected by the player
    public void mark() {
        isMarked = true; // Set the marked status to true
    }

    // Unmarks this mine, indicating that the player no longer suspects it
    public void unmark() {
        isMarked = false; // Set the marked status to false
    }
}
