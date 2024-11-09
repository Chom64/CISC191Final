import javax.swing.*;

public class MainGame {
    private MineBoard board;            // MainGame HAS-A MineBoard
    private GameTimer timer;            // MainGame HAS-A GameTimer
    private ScoreManager scoreManager;  // MainGame HAS-A ScoreManager
    private MineSweeperGUI gui;         // MainGame HAS-A MineSweeperGUI

    // Initializes the game
    public MainGame() {
        this.scoreManager = new ScoreManager(); // Initialize the ScoreManager
        this.gui = new MineSweeperGUI(this);    // Initialize the GUI and pass this game instance
    }

    // Getter for the current MineBoard
    public MineBoard getBoard() {
        return board; // Return the current MineBoard
    }

    // Starts a new game with specified dimensions and mine count
    public void startGame(int width, int height, int mineCount) {
        board = new MineBoard(width, height, mineCount); // MainGame HAS-A new MineBoard
        gui.initializeButtons(width, height); // Initialize buttons in the GUI for the new game size
        timer = new GameTimer(this); // MainGame HAS-A new GameTimer
        timer.start(); // Start the timer
        gui.updateBoard(board); // Update the GUI to reflect the new game state
    }

    // Reveals a cell at the specified coordinates
    public void revealCell(int x, int y) {
        if (board.revealCell(x, y)) { // Try to reveal the cell on the MineBoard
            gui.updateBoard(board); // Update the GUI with the new board state
            if (board.isGameOver()) { // Check if the game is over
                gui.showGameOver("Game Over! Hit a mine!"); // Display game over message
                timer.stop(); // Stop the timer
            }
        }
    }

    // Resets the game with specified difficulty
    public void resetGame(String difficulty) {
        if (timer != null) {
            timer.stop(); // Stop the timer if it's running
        }

        // Start a new game based on the selected difficulty
        switch (difficulty) {
            case "Easy":
                startGame(8, 8, 10); // Easy game settings
                break;
            case "Medium":
                startGame(10, 10, 20); // Medium game settings
                break;
            case "Hard":
                startGame(12, 12, 30); // Hard game settings
                break;
            default:
                break;
        }
    }

    // Main method to launch the game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGame game = new MainGame(); // Create a new MainGame instance
            game.resetGame("Medium"); // Start with Medium difficulty by default
        });
    }

    // Getter for the ScoreManager
    public ScoreManager getScoreManager() {
        return scoreManager; // Return the ScoreManager instance
    }
}
