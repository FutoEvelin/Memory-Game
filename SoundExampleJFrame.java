import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class SoundExampleJFrame extends JFrame {
    public SoundExampleJFrame() {
        try {
            //File URL relative to project folder
            AudioInputStream audioInputStream  = AudioSystem.getAudioInputStream(new File("res/bubblepop.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("IO error");
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100,100,500,500);
        setVisible(true);
    }
    public static void main(String[] args) {
        new SoundExampleJFrame();
    }
}