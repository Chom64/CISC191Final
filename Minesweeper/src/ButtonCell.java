import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedList;
import java.awt.Color;
import java.io.File;
import java.awt.Image;
import javax.swing.ImageIcon;

public class ButtonCell extends MouseAdapter implements ActionListener {
    private GameBoard gameBoard;
    private JButton[][] buttons; // 2D array of buttons representing the cells
    private JLabel timerLabel;
    private GameTimer gameTimer; // Timer for the game duration
    private boolean gameOver = false; // Flag to check if the game is over
    private SoundManager soundManager; // Handling sound effects

    public ButtonCell(GameBoard gameBoard, JButton[][] buttons, JLabel timerLabel, GameTimer gameTimer, SoundManager soundManager) {
        this.gameBoard = gameBoard;
        this.buttons = buttons;
        this.timerLabel = timerLabel;
        this.gameTimer = gameTimer;
        this.soundManager = soundManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        JButton button = (JButton) e.getSource();
        int row = -1, col = -1;

        // Find the button's position in the grid
        outer:
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j] == button) {
                    row = i;
                    col = j;
                    break outer;
                }
            }
        }

        if (!gameBoard.isRevealed(row, col)) {
            gameBoard.revealCell(row, col);
            int value = gameBoard.getCellValue(row, col);

            // If the revealed cell is a mine, end the game and display mine image
            if (value == -1) {
                setMineImage(button);  // Set the mine image
                button.setBackground(Color.RED);  // Set red background for mines
                gameOver = true;
                JOptionPane.showMessageDialog(null, "Game Over! You hit a mine.");
                gameTimer.stop();
                return;
            }

            // Update the button text to show the number of adjacent mines
            if (value > 0) {
                button.setText(String.valueOf(value));
                setButtonTextColor(button, value);
            } else {
                button.setText("0");
                revealAdjacentEmptyCells(row, col);
            }

            // Check if the game is won
            if (gameBoard.isGameOver()) {
                JOptionPane.showMessageDialog(null, "Congratulations! You won!");
                gameTimer.stop();
                gameOver = true;

                String playerName = JOptionPane.showInputDialog("Enter your name:");
                String difficulty = "Easy"; 
                
                if (playerName != null && !playerName.trim().isEmpty()) {
                    Leaderboard leaderboard = new Leaderboard();
                    leaderboard.addEntry(playerName, gameTimer.getTimeElapsed(), difficulty);
                    leaderboard.displayLeaderboard();
                }
            }

            soundManager.playClickSound();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (gameOver) return;

        JButton button = (JButton) e.getSource();
        int row = -1, col = -1;

        // Find the button's position in the grid
        outer:
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j] == button) {
                    row = i;
                    col = j;
                    break outer;
                }
            }
        }

        if (e.getButton() == MouseEvent.BUTTON3) {
            if (!gameBoard.isRevealed(row, col)) {
                gameBoard.flagCell(row, col);
                button.setText(gameBoard.isFlagged(row, col) ? "F" : "");
            }
        }

        soundManager.playClickSound();
    }

    private void revealAdjacentEmptyCells(int row, int col) {
        // Boundary check and only reveal if the cell is not already revealed or not a 0
        if (row < 0 || row >= gameBoard.getGridSize() || col < 0 || col >= gameBoard.getGridSize()
            || gameBoard.isRevealed(row, col) || gameBoard.getCellValue(row, col) != 0) {
            return;
        }

        // Reveal the current cell
        revealCell(row, col);

        // Recursively check and reveal all adjacent cells in 8 directions
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;  // Skip the current cell itself
                int newRow = row + i;
                int newCol = col + j;
                // Recursively process each neighboring cell
                revealAdjacentEmptyCells(newRow, newCol);
            }
        }
    }
    
    private void revealCell(int row, int col) {
        if (!gameBoard.isRevealed(row, col)) {
            gameBoard.revealCell(row, col);
            JButton button = buttons[row][col];
            int value = gameBoard.getCellValue(row, col);

            if (value == -1) {
                setMineImage(button);  // If it's a mine, display the mine image
                button.setBackground(Color.RED);  // Set the background color for mines
            } else {
                button.setText(value > 0 ? String.valueOf(value) : "0");
                setButtonTextColor(button, value);
            }

            button.setEnabled(false);  // Disable the button after it's revealed
        }
    }




    private void setMineImage(JButton button) {
        File mineImageFile = new File("resources/images/mine.png");
        if (mineImageFile.exists()) {
            ImageIcon mineIcon = new ImageIcon(mineImageFile.getPath());

            // Scale the image
            int margin = 20;
            int targetWidth = button.getWidth() - margin;
            int targetHeight = button.getHeight() - margin;
            targetWidth = Math.max(targetWidth, 1);
            targetHeight = Math.max(targetHeight, 1);

            Image scaledImage = mineIcon.getImage().getScaledInstance(
                targetWidth, targetHeight, Image.SCALE_SMOOTH
            );
            mineIcon = new ImageIcon(scaledImage);

            button.setIcon(mineIcon);
        } else {
            System.err.println("Mine image not found.");
        }
    }

    /**
     * Sets the color of the button text based on the value of adjacent mines.
     * @param button The button to set the text color for.
     * @param value The number of adjacent mines.
     */
    private void setButtonTextColor(JButton button, int value) {
        switch (value) {
            case 1:
                button.setForeground(Color.BLUE);
                break;
            case 2:
                button.setForeground(Color.GREEN);
                break;
            case 3:
                button.setForeground(Color.RED);
                break;
            case 4:
                button.setForeground(Color.MAGENTA);
                break;
            case 5:
                button.setForeground(Color.DARK_GRAY);
                break;
            case 6:
                button.setForeground(Color.CYAN);
                break;
            case 7:
                button.setForeground(Color.PINK);
                break;
            case 8:
                button.setForeground(Color.ORANGE);
                break;
            default:
                button.setForeground(Color.BLACK);  // Default color for 0 or other values
                break;
        }
    }
}
