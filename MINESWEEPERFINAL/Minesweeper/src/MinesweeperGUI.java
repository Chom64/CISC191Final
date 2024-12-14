import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Lead Author(s):
 * 
 * @Zhao Chen
 * @Christopher Hom
 * Version/date: 12-12-2024
 * 
 * Responsibilities of class: The MinesweeperGUI class is responsible for initializing and managing the graphical user
 * interface (GUI) of the Minesweeper game.
 */
public class MinesweeperGUI {
    private JFrame frame;
    private JButton[][] buttons;
    private JLabel timerLabel;  // Initialize timerLabel
    private GameBoard gameBoard;
    private GameTimer gameTimer;
    private ButtonCell buttonCell;
    private JPanel panel;
    private SoundManager soundManager;  // Sound manager to handle music and click sounds

    private JButton pauseButton;   // Button to pause music
    private JButton resumeButton;  // Button to resume music
    private JLabel authorLabel;    // Label to display author information
    
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;

    public MinesweeperGUI() {
    	// Initialize the frame
        frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 600);

        // Initialize the sound manager
        soundManager = new SoundManager();

        // Initialize the difficulty selection dropdown
        initDifficultySelector();

        // Initialize the timerLabel (should be done before using it)
        timerLabel = new JLabel("Time: 0");

        // Create and add pause/resume buttons (aligned to the right of the timer)
        createPauseResumeButtons();

        // Create a panel to hold the timer and the pause/resume buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(timerLabel, BorderLayout.WEST);  // Add timer to the left side
        topPanel.add(createControlPanel(), BorderLayout.EAST);  // Add pause/resume buttons to the right

        // Add the topPanel to the frame
        frame.add(topPanel, BorderLayout.NORTH);

        // Initialize the game with default difficulty ("easy")
        setDifficulty("easy");

        // Create and add the bottom panel with difficulty and author information
        createBottomPanel();

        // Show the frame
        frame.setVisible(true);
    }

    private void initDifficultySelector() {
        // Create buttons for each difficulty level
        easyButton = new JButton("Easy");
        mediumButton = new JButton("Medium");
        hardButton = new JButton("Hard");

        // Add action listeners to each button
        easyButton.addActionListener(new ActionListener() {
            @Override
            // Sets the games difficulty to easy
            public void actionPerformed(ActionEvent e) {
                setDifficulty("easy");
            }
        });

        mediumButton.addActionListener(new ActionListener() {
            @Override
            // Sets the games difficulty to medium
            public void actionPerformed(ActionEvent e) {
                setDifficulty("middle");
            }
        });

        hardButton.addActionListener(new ActionListener() {
            @Override
            // Sets the games difficulty to hard
            public void actionPerformed(ActionEvent e) {
                setDifficulty("hard");
            }
        });

        // Create a panel for the difficulty selection and add it to the bottom of the frame
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.add(easyButton);
        difficultyPanel.add(mediumButton);
        difficultyPanel.add(hardButton);

        // Add difficulty selection panel to the bottom of the frame
        frame.add(difficultyPanel, BorderLayout.SOUTH); // Move difficulty panel to SOUTH or another suitable position
    }

    // Method to set the difficulty and initialize the game board
    public void setDifficulty(String difficulty) {
        // Stop current timer and reset it
        if (gameTimer != null) {
            gameTimer.stop();
        }

        // Initialize a new GameBoard based on the chosen difficulty
        gameBoard = new GameBoard(difficulty);

        // Initialize the timerLabel and gameTimer
        timerLabel.setText("Time: 0");  // Reset time label
        gameTimer = new GameTimer(timerLabel);  // Create a new timer instance

        // Initialize the buttons array
        buttons = new JButton[gameBoard.getGridSize()][gameBoard.getGridSize()];

        // Create UI components and reset the game board
        resetUI();

        // Initialize the ButtonCell for event handling
        buttonCell = new ButtonCell(gameBoard, buttons, timerLabel, gameTimer, soundManager, difficulty);  // Pass soundManager to ButtonCell

        // Add listeners to all buttons
        addListeners();

        // Start the game timer
        gameTimer.start();

        // Start playing background music
        soundManager.startBackgroundMusic();
    }

    private void resetUI() {
        // If the panel already exists, remove it from the frame
        if (panel != null) {
            frame.remove(panel);
        }

        // Create the panel for the grid of buttons
        panel = new JPanel();
        panel.setLayout(new GridLayout(gameBoard.getGridSize(), gameBoard.getGridSize()));

        // Create the buttons for the grid
        for (int i = 0; i < gameBoard.getGridSize(); i++) {
            for (int j = 0; j < gameBoard.getGridSize(); j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(40, 40));
                buttons[i][j] = button;  // Assign the button to the array
                panel.add(button);
            }
        }

        // Add the grid panel to the center of the frame
        frame.add(panel, BorderLayout.CENTER);

        // Revalidate the frame layout after removing and adding the panel
        frame.revalidate();
        frame.repaint();
    }

    private void addListeners() {
        // Add the action listener and mouse listener to each button
        for (int i = 0; i < gameBoard.getGridSize(); i++) {
            for (int j = 0; j < gameBoard.getGridSize(); j++) {
                JButton button = buttons[i][j];
                button.addActionListener(buttonCell);  // Action listener for left-click
                button.addMouseListener(buttonCell);  // Mouse listener for right-click (flagging)
            }
        }
    }

    // Create the Pause/Resume buttons
    private void createPauseResumeButtons() {
        pauseButton = new JButton("Pause Music");
        resumeButton = new JButton("Resume Music");

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.pauseBackgroundMusic();  // Pause background music
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.resumeBackgroundMusic();  // Resume background music
            }
        });
    }

    // Create a panel with Pause and Resume buttons
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));  // Align buttons to the right
        controlPanel.add(pauseButton);
        controlPanel.add(resumeButton);
        return controlPanel;
    }

 // Create a panel to display the bottom controls (difficulty dropdown and author info)
    private void createBottomPanel() {
        // Create a panel for the bottom with FlowLayout (left for combo box, right for author info)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Add difficulty combo box to the left of the bottom panel
        bottomPanel.add(new JLabel("Select Difficulty: "));
        bottomPanel.add(easyButton);
        bottomPanel.add(mediumButton);
        bottomPanel.add(hardButton);

        // Create a label for author information
        authorLabel = new JLabel("Author: Zhao Chen, Christopher Hom", JLabel.CENTER);

        // Create another panel for author info to be placed on the right
        JPanel authorPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        authorPanel.add(authorLabel);

        // Add both panels (difficulty and author) to the bottom section
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(bottomPanel, BorderLayout.WEST);  // Place difficulty combo box on the left
        southPanel.add(authorPanel, BorderLayout.EAST);  // Place author info on the right

        // Add the south panel to the frame's SOUTH region
        frame.add(southPanel, BorderLayout.SOUTH);
    }
    
    public static void main(String[] args) {
        new MinesweeperGUI();
    }
}
