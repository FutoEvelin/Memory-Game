import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MenuFrame {
    JButton startGameButton;
    JButton exitButton;
    JButton instructionButton;

    private Image loadBackgroundImage(String path) {
        try {
            Image img = ImageIO.read(new File(path));
            return img.getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public MenuFrame() {
        JFrame menuFrame = new JFrame("Memory Game");
        menuFrame.setSize(1920, 1080);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);

        final Image scaledImg = loadBackgroundImage("img/background.png");

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (scaledImg != null) {
                    g.drawImage(scaledImg, 0, 0, this);
                }
            }
        };

        backgroundPanel.setLayout(null);
        menuFrame.setContentPane(backgroundPanel);
        playEffect(menuFrame,160,300);
        playEffect(menuFrame,1040,300);
        setMenuFrame(backgroundPanel, menuFrame);
    }

    void setMenuFrame(JPanel backgroundPanel, JFrame menuFrame) {
        startGameButton = createButton(
                "Start Game", 660, 400, 200, 80,
                new Color(253, 253, 150), new Color(184, 215, 213), new Color(119, 158, 203),
                e -> {
                    // Options dialog to choose time limit
                    String[] options = {"1 minute", "3 minutes", "5 minutes"};
                    int choice = JOptionPane.showOptionDialog(menuFrame,
                            "Select Time Limit",
                            "Time Limit",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            options,
                            options[0]);

                    int timeLimit = 60;
                    if (choice == 1) timeLimit = 180;
                    if (choice == 2) timeLimit = 300;

                    menuFrame.setVisible(false); // Hide the menu
                    new CardFrame("img/background.png", timeLimit); // Start the game with selected time
                }
        );

        exitButton = createButton(
                "Exit", 660, 500, 200, 80,
                new Color(203, 153, 201), new Color(184, 215, 213), new Color(119, 158, 203),
                e -> System.exit(0)
        );

        instructionButton = createButton(
                "Instructions", 660, 300, 200, 80,
                new Color(119, 221, 119), new Color(184, 215, 213), new Color(119, 158, 203),
                e -> new InstructionsFrame("files/instructions.txt")
        );

        backgroundPanel.add(startGameButton);
        backgroundPanel.add(exitButton);
        backgroundPanel.add(instructionButton);

        menuFrame.setVisible(true);
    }

    private JButton createButton(String text, int x, int y, int width, int height, Color backgroundColor, Color foregroundColor, Color borderColor, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Broadway", Font.BOLD, 20));
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setBounds(x, y, width, height);
        button.setBorder(BorderFactory.createLineBorder(borderColor, 7));
        button.addActionListener(actionListener);
        return button;
    }

    public void playEffect(JFrame frame,int x,int y) {
        ImageIcon gif = new ImageIcon("res/stars.gif");
        Image scaledImage = gif.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        ImageIcon scaledGif = new ImageIcon(scaledImage);

        JLabel gifLabel = new JLabel(scaledGif);
        gifLabel.setBounds(x, y, 300, 300);

        frame.getContentPane().add(gifLabel, 0);
        frame.revalidate();
        frame.repaint();

    }
    }
