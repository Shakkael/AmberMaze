package ambermaze;

import java.net.URL;
import javax.sound.sampled.*;

/**
 *
 * @author Shakkael
 */
public class SoundManager {
    
    
    int volumeSetting = 20;
    String casket = "CasketTemp.wav", hit = "hit.wav", hit2 = "hit2.wav";
    String startMonic = "start.wav", startCasper = "start2.wav";
    String bgMusic = "background.wav";
    
    public synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/sounds/" + url));
                    clip.open(inputStream);
                    FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    volume.setValue(-1 * volumeSetting);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
}
}
