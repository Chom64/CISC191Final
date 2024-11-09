import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class MineSweeperGUI {
    private JFrame frame;
    private JButton[][] buttons;
    private MainGame game;
    private JRadioButton easyButton;
    private JRadioButton mediumButton;
    private JRadioButton hardButton;
    private JButton newGameButton;

    public MineSweeperGUI(MainGame game) {
        this.game = game;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("MineSweeper");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        buttons = new JButton[10][10]; // Initial size, will adjust later

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(10, 10));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j] = new JButton();
                final int x = i;
                final int y = j;

                buttons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) { // Right-click to mark
                            if (!game.getBoard().getCells()[x][y].isRevealed()) {
                                if (game.getBoard().getCells()[x][y].isMarked()) {
                                    game.getBoard().getCells()[x][y].unmark();
                                    buttons[x][y].setText(""); // Clear the button text
                                } else {
                                    game.getBoard().getCells()[x][y].mark();
                                    buttons[x][y].setText("M"); // Mark with "M"
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON1) { // Left-click to reveal
                            game.revealCell(x, y);
                        }
                    }
                });
                buttonPanel.add(buttons[i][j]);
            }
        }

        // Create difficulty selection panel
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new FlowLayout());

        ButtonGroup difficultyGroup = new ButtonGroup();

        easyButton = new JRadioButton("Easy");
        mediumButton = new JRadioButton("Medium", true); // Default selection
        hardButton = new JRadioButton("Hard");

        difficultyGroup.add(easyButton);
        difficultyGroup.add(mediumButton);
        difficultyGroup.add(hardButton);

        difficultyPanel.add(easyButton);
        difficultyPanel.add(mediumButton);
        difficultyPanel.add(hardButton);

        // New Game button
        newGameButton = new JButton("Start New Game");
        newGameButton.addActionListener(e -> startNewGame());
        difficultyPanel.add(newGameButton); // Add the button to the difficulty panel

        frame.add(difficultyPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void startNewGame() {
        String difficulty = "Medium"; // Default value
        if (easyButton.isSelected()) {
            difficulty = "Easy";
        } else if (hardButton.isSelected()) {
            difficulty = "Hard";
        }
        game.resetGame(difficulty); // Pass the selected difficulty level
    }

    public void initializeButtons(int width, int height) {
        buttons = new JButton[width][height]; // Use dynamic sizes
        JPanel buttonPanel = (JPanel) frame.getContentPane().getComponent(1); // Get the button panel
        buttonPanel.removeAll();
        buttonPanel.setLayout(new GridLayout(height, width));

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                buttons[i][j] = new JButton();
                final int x = i;
                final int y = j;

                buttons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) { // Right-click to mark
                            if (!game.getBoard().getCells()[x][y].isRevealed()) {
                                if (game.getBoard().getCells()[x][y].isMarked()) {
                                    game.getBoard().getCells()[x][y].unmark();
                                    buttons[x][y].setText(""); // Clear the button text
                                } else {
                                    game.getBoard().getCells()[x][y].mark();
                                    buttons[x][y].setText("M"); // Mark with "M"
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON1) { // Left-click to reveal
                            game.revealCell(x, y);
                        }
                    }
                });
                buttonPanel.add(buttons[i][j]);
            }
        }

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    public void updateBoard(MineBoard board) {
        Cell[][] cells = board.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].isRevealed()) {
                    if (cells[i][j].isMine()) {
                        buttons[i][j].setText("M"); // Show mine symbol
                    } else {
                        int adjacentMines = cells[i][j].getAdjacentMines();
                        buttons[i][j].setText(String.valueOf(adjacentMines)); // Show the number of adjacent mines

                        // Change the button's text color based on the number of adjacent mines
                        switch (adjacentMines) {
                            case 1:
                                buttons[i][j].setForeground(Color.BLUE); // Color for 1 adjacent mine
                                break;
                            case 2:
                                buttons[i][j].setForeground(Color.GREEN.darker()); // Color for 2 adjacent mines
                                break;
                            case 3:
                                buttons[i][j].setForeground(Color.RED); // Color for 3 adjacent mines
                                break;
                            case 4:
                                buttons[i][j].setForeground(Color.MAGENTA); // Color for 4 adjacent mines
                                break;
                            case 5:
                                buttons[i][j].setForeground(Color.ORANGE); // Color for 5 adjacent mines
                                break;
                            case 6:
                                buttons[i][j].setForeground(Color.CYAN); // Color for 6 adjacent mines
                                break;
                            case 7:
                                buttons[i][j].setForeground(Color.BLACK); // Color for 7 adjacent mines
                                break;
                            case 8:
                                buttons[i][j].setForeground(Color.GRAY); // Color for 8 adjacent mines
                                break;
                            default:
                                buttons[i][j].setForeground(Color.BLACK); // Default color for other cases
                                break;
                        }
                    }
                } else {
                    if (cells[i][j].isMarked()) {
                        buttons[i][j].setText("M"); // If marked, show "M"
                    } else {
                        buttons[i][j].setText(""); // Clear the button text if not revealed
                    }
                }
            }
        }
    }

    public void showGameOver(String message) {
        int option = JOptionPane.showConfirmDialog(frame, message + "\nWould you like to start a new game?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Get the selected difficulty from the radio buttons
            String difficulty = "Medium"; // Default value
            if (easyButton.isSelected()) {
                difficulty = "Easy";
            } else if (hardButton.isSelected()) {
                difficulty = "Hard";
            }
            game.resetGame(difficulty); // Pass the selected difficulty level
        } else {
            frame.dispose(); // Close the game
        }
    }
}
