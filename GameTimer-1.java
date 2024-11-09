import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimer {
    private Timer timer;
    private int elapsedTime;
    private MainGame game;

    public GameTimer(MainGame game) {
        this.game = game;
        elapsedTime = 0;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime++;
            }
        });
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public int getElapsedTime() {
        return elapsedTime;
    }
}
