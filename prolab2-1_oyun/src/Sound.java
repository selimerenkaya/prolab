import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

// Oyun içi ses çalmayı sağlayan sınıf
public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    // Constructor
    Sound() {
        soundURL[0] = getClass().getResource("Sound/gameMusic.wav");
    }

    // Müzik çalmak için kullanılan metot
    public void setFile(int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }



}
