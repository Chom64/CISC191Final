import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    private Clip backgroundClip;  
    private Clip clickClip;       

    public SoundManager() {
        try {
            // Load background music using relative path
            File backgroundSoundFile = new File("resources/sounds/backgroundmusic.wav");
            if (backgroundSoundFile.exists()) {
                AudioInputStream backgroundAudioStream = AudioSystem.getAudioInputStream(backgroundSoundFile);
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(backgroundAudioStream);
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop background music
            } else {
                System.err.println("Background music file not found.");
            }

            // Load click sound using relative path
            File clickSoundFile = new File("resources/sounds/clicksound.wav");
            if (clickSoundFile.exists()) {
                AudioInputStream clickAudioStream = AudioSystem.getAudioInputStream(clickSoundFile);
                clickClip = AudioSystem.getClip();
                clickClip.open(clickAudioStream);
            } else {
                System.err.println("Click sound file not found.");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Start background music
    public void startBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.start();
        }
    }

    // Stop background music
    public void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
        }
    }

    // Pause background music
    public void pauseBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
        }
    }

    // Resume background music
    public void resumeBackgroundMusic() {
        if (backgroundClip != null && !backgroundClip.isRunning()) {
            backgroundClip.start();
        }
    }

    // Play click sound
    public void playClickSound() {
        if (clickClip != null) {
            clickClip.setFramePosition(0);  // Always start from the beginning
            clickClip.start();
        }
    }
}
