import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Card {
    String name;
    ImageIcon cardImage;
    ImageIcon backImage;
    int width;
    int height;
    boolean chosen;

    public Card(String name, String imagePath, String backImagePath) {
        width = setWidth();
        height = setHeight();
        this.name = name;
        try {
            Image img = ImageIO.read(new File(imagePath));
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            this.cardImage = new ImageIcon(scaledImg);

            img = ImageIO.read(new File(backImagePath));
            scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            this.backImage = new ImageIcon(scaledImg);

        } catch (IOException e) {
            e.printStackTrace();
            this.cardImage = new ImageIcon();
        }
    }

    public int setWidth() {
        return this.width = 105;
    }

    public int setHeight() {
        return this.height = 144;
    }

    public void setChosen(boolean x) {
        this.chosen = x;
    }

    public boolean getChosen() {
        return this.chosen;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeigth() {
        return height;
    }

    public ImageIcon getCardImage() {
        return cardImage;
    }

    public ImageIcon getBackImage() {
        return backImage;

    }
}
