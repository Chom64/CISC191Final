import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameTimer {
    private JLabel timerLabel;  // Label to display time
    private Timer timer;  // Timer to update every second
    private int timeElapsed;  // Track time elapsed in seconds

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

    public int getTimeElapsed() {
        return timeElapsed;  // Return the elapsed time
    }
}
