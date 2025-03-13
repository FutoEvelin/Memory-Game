import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.io.File;
import java.io.IOException;

public class CardPanel extends JPanel {
    private Card firstCard = null;
    private Card secondCard = null;
    private JButton firstButton = null;
    private JButton secondButton = null;
    private boolean isProcessing = false;
    CardFrame frame;

    int points;

    public CardPanel(Cards cardsInstance, int frameWidth, int frameHeight, CardFrame frame) {
        this.frame = frame;
        setLayout(new GridLayout(3, 6));
        setBounds((frameWidth - 1000) / 2, (frameHeight - 680) / 2, 610, 450);
        playSound("D:/2.ev/java/memoriajatek/res/sound/game.wav");

        points = 0;
        ArrayList<Card> cards = cardsInstance.getCards();

        cards.stream().forEach(card -> card.setChosen(false));
        createCardButtons(cards);
    }


    private void createCardButtons(ArrayList<Card> cards) {
        for (Card card : cards) {
            JButton button = new JButton(card.getBackImage());
            button.setPreferredSize(new Dimension(card.getWidth(), card.getHeigth()));
            button.setBorder(BorderFactory.createLineBorder(new Color(184, 215, 213), 3));
            button.addActionListener(e -> {
                if (isProcessing) {
                    return;
                }

                if (card.getChosen()) {
                    JPanel panel = new JPanel();
                    panel.setBackground(new Color(216, 167, 209));
                    JLabel label = new JLabel("Ez mar ki lett valasztva");
                    label.setForeground(new Color(211, 107, 125));
                    panel.add(label);

                    JOptionPane.showMessageDialog(this, panel, "Figyelmeztetés", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                playSound("D:/2.ev/java/memoriajatek/res/sound/bubblepop.wav");
                button.setIcon(card.getCardImage());

                if (firstCard == null) {
                    firstCard = card;
                    firstButton = button;
                } else if (secondCard == null) {
                    secondCard = card;
                    secondButton = button;


                    if (Stream.of(firstCard.getName(), secondCard.getName()).distinct().count() == 1) {
                        firstButton.setIcon(firstCard.cardImage);
                        secondButton.setIcon(secondCard.cardImage);
                        firstCard.setChosen(true);
                        secondCard.setChosen(true);
                        firstCard = null;
                        secondCard = null;

                        points++;
                        frame.incrementPoints();
                        playSound("D:/2.ev/java/memoriajatek/res/sound/swoosh.wav");
                        if (points == 9) {
                            ImageIcon gif = new ImageIcon("res/confetti.gif");
                            JLabel gifLabel = new JLabel(gif);

                            gifLabel.setSize(1920, 1080);
                            frame.getContentPane().add(gifLabel, 0);
                            frame.revalidate();
                            frame.repaint();

                            playSound("D:/2.ev/java/memoriajatek/res/sound/yay.wav");
                            new Timer(3000, ex -> {
                                frame.getContentPane().remove(gifLabel);
                                frame.revalidate();
                                frame.repaint();
                            }).start();
                        }
                    } else {

                        isProcessing = true;
                        Timer timer = new Timer(1000, ae -> {
                            if (firstCard != null && secondCard != null) {
                                firstButton.setIcon(firstCard.getBackImage());
                                secondButton.setIcon(secondCard.getBackImage());
                                firstCard.setChosen(false);
                                secondCard.setChosen(false);
                            }

                            firstCard = null;
                            secondCard = null;
                            firstButton = null;
                            secondButton = null;
                            isProcessing = false;
                        });
                        timer.setRepeats(false);
                        timer.start();
                    }
                }
            });
            add(button);
        }
    }

    private void playSound(String filePath) {
        try {
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                System.out.println("A fájl nem található: " + filePath);
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            System.out.println("Hiba a hang lejátszása közben: " + ex.getMessage());
        }
    }
}
