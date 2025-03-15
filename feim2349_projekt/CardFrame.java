import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CardFrame extends JFrame {
    private JPanel backgroundPanel;
    private JButton pointsButton;
    private JButton reshuffleButton;
    private JButton exitButton;
    private JLabel timerLabel;
    private Cards cardsInstance;
    private int points;
    private int elapsedTime;
    private Timer timer;
    private CardPanel cardPanel;
    final private int timeLimit;

    public CardFrame(String backgroundImagePath, int timeLimit) {
        this.timeLimit = timeLimit;
        setTitle("Card Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);

        backgroundPanel = new JPanel() {
            private Image backgroundImage;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    backgroundImage = ImageIO.read(new File(backgroundImagePath));
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        setFrame();
        startTimer();
    }

    private void setFrame() {
        setSize(1920, 1080);
        backgroundPanel.setLayout(null);
        add(backgroundPanel);
        initializeCardPanel();
        setButtons();
        addTimerLabel(50, 30, 200, 50);

        JLabel label = new JLabel();
        label.setText("Memory game");
        label.setFont(new Font("Broadway", Font.PLAIN, 35));
        label.setForeground(new Color(184, 215, 213));
        label.setBounds(633,130,350,40);
        backgroundPanel.add(label);
        setVisible(true);
    }

    private void initializeCardPanel() {
        cardsInstance = new Cards();
        cardPanel = new CardPanel(cardsInstance, getWidth(), getHeight(),this);
        cardPanel.setBounds(460, 220, 610, 450);
        backgroundPanel.add(cardPanel);
    }

    private JButton createButton(String text, int x, int y, int width, int height, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Broadway", Font.BOLD, 18));
        button.setBackground(new Color(184, 215, 213));
        button.setForeground(new Color(254, 150, 133));
        button.setBounds(x, y, width, height);
        button.setBorder(BorderFactory.createLineBorder(new Color(254, 140, 133), 3));
        button.addActionListener(actionListener);
        backgroundPanel.add(button);
        backgroundPanel.repaint();
        return button;
    }

    public void setButtons() {
        points = 0;
        pointsButton = createButton("Score: 0 / 9", 1200, 400, 150, 80, e -> {

        });

        reshuffleButton = createButton("Reshuffle", 1200, 550, 150, 80, e -> {
            cardsInstance.reshuffle();
            refreshCardPanel();
        });

        exitButton = createButton("Exit", 1200, 250, 150, 80, e -> {
            System.exit(0);
        });
    }

    public void refreshCardPanel() {
        backgroundPanel.remove(cardPanel);
        timerLabel.setText(" ");
        timer.stop();
        points = 0;
        pointsButton.setText("Score: 0 / 9");
        setFrame();
        startTimer();
        backgroundPanel.repaint();
    }

    public JButton getPointsButton() {
        return pointsButton;
    }

    private void addTimerLabel(int x, int y, int width, int height) {
        timerLabel = new JLabel(" ");
        //timerLabel.setText("Time: 0 s");
        timerLabel.setFont(new Font("Broadway", Font.PLAIN, 24));
        timerLabel.setForeground(new Color(254, 150, 133));
        timerLabel.setBounds(x, y, width, height);
        backgroundPanel.add(timerLabel);
    }

    private void startTimer() {
        timerLabel.setText(" ");
        elapsedTime = 0;
        timer = new Timer(1000, e -> {
            elapsedTime++;
            timerLabel.setText("Time: " + (timeLimit - elapsedTime) + "s");
            if (elapsedTime >= timeLimit) {
                playSound("res/sound/wah.wav");
                timer.stop();
                timerLabel.setText(" ");
                timerStopped();
            }
        });
        timer.start();
    }

    public void timerStopped()
    {
        Object[] options = {"Exit", "Reshuffle"};
        int choice = JOptionPane.showOptionDialog(this, "Time's up! What do you want to do?", "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);

        if (choice == 0) {
            System.exit(0);
        } else if (choice == 1) {
            cardsInstance.reshuffle();
            refreshCardPanel();
            backgroundPanel.repaint();
            elapsedTime = 0;

            startTimer();
        }
    }
    public void incrementPoints() {
        points++;
        SwingUtilities.invokeLater(() -> {
            pointsButton.setText("Score: " + points + "/9");
            pointsButton.repaint();
        });

        if (points == 9) {
            timer.stop();
            saveTimeToFile();
            JOptionPane.showMessageDialog(this, "Congratulations! You found all pairs in " + elapsedTime + " seconds.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }


    }

    private void saveTimeToFile() {
        try (FileWriter writer = new FileWriter("game_times.txt", true)) {
            writer.write("Elapsed time: " + elapsedTime + " seconds\n");
        } catch (IOException e) {
            e.printStackTrace();
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
