import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
 * GameTimer class is responsible for displaying the game timer.
 * 
 */ 

public class GameTimer {
    private JLabel timerLabel;  // Label to display time
    private Timer timer;  // Timer to update every second
    private int timeElapsed;  // Track time elapsed in seconds

    /**
     * Constructor initializes the game timer.
     * 
     * @param timerLabel The JLabel where the timer will be displayed.
     * This constructor sets up the timer to update the label every second.
     * It also initializes the timeElapsed to 0, as the timer starts from 0.
     */
    public GameTimer(JLabel timerLabel) {
        this.timerLabel = timerLabel;  // Pass the timerLabel to update it
        this.timeElapsed = 0;
        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeElapsed++;  // Increment the time
                timerLabel.setText("Time: " + timeElapsed);  // Update the label text with the time
            }
        });
    }

    public void start() {
        timer.start();  // Start the timer
    }

    public void stop() {
        timer.stop();  // Stop the timer
    }

    /**
     * Gets the time that has elapsed since the timer started.
     * 
     * @return The number of seconds that have passed since the timer started.
     */
    public int getTimeElapsed() {
        return timeElapsed;  // Return the elapsed time
    }
}
