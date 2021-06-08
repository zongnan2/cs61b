package byow.Core;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Scanner;

public class SoundEffect {
    private Clip clip;

    public void setFile(String fileName) {
        try {
            File file = new File(fileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(sound);

        } catch (Exception e) {
            System.out.print("Error play sound");
        }
    }

    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }


}
