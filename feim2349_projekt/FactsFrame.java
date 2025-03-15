import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class FactsFrame {

    private Image loadBackgroundImage(String path) {
        try {
            Image img = ImageIO.read(new File(path));
            return img.getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void displayFileContent(String fileName) {
        String content = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading file: " + fileName, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        JFrame contentFrame = new JFrame("Facts - " + fileName);
        contentFrame.setSize(600, 400);
        contentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Csak ezt az ablakot zárja be
        contentFrame.setLocationRelativeTo(null);
        contentFrame.setBackground(new Color(184, 215, 213));

        JTextArea textArea = new JTextArea(content);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        contentFrame.add(scrollPane);

        contentFrame.setVisible(true);
    }



    public FactsFrame() {

        JFrame factsFrame = new JFrame("Facts");
        factsFrame.setSize(1920, 1080);
        factsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        factsFrame.setLocationRelativeTo(null);

        final Image scaledImg = loadBackgroundImage("img/menubackground.png");
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (scaledImg != null) {
                    g.drawImage(scaledImg, 0, 0, this);
                }
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        JButton backToTheFutureButton = new JButton("Back To The Future");
        backToTheFutureButton.setBackground(new Color(188, 175, 207));
        backToTheFutureButton.setForeground(Color.WHITE);
        backToTheFutureButton.setFont(new Font("Arial", Font.BOLD, 20));

        JButton titanicButton = new JButton("Titanic");
        titanicButton.setBackground(new Color(188, 175, 207));
        titanicButton.setForeground(Color.WHITE);
        titanicButton.setFont(new Font("Arial", Font.BOLD, 20));

        JButton harrypotterButton = new JButton("Harry Potter");
        harrypotterButton.setBackground(new Color(188, 175, 207));
        harrypotterButton.setForeground(Color.WHITE);
        harrypotterButton.setFont(new Font("Arial", Font.BOLD, 20));

        JButton theGodfatherButton = new JButton("The Godfather");
        theGodfatherButton.setBackground(new Color(188, 175, 207));
        theGodfatherButton.setForeground(Color.WHITE);
        theGodfatherButton.setFont(new Font("Arial", Font.BOLD, 20));

        JButton barbieButton = new JButton("Barbie");
        barbieButton.setBackground(new Color(188, 175, 207));
        barbieButton.setForeground(Color.WHITE);
        barbieButton.setFont(new Font("Arial", Font.BOLD, 20));

        JButton oppenheimerButton = new JButton("Oppenheimer");
        oppenheimerButton.setBackground(new Color(188, 175, 207));
        oppenheimerButton.setForeground(Color.WHITE);
        oppenheimerButton.setFont(new Font("Arial", Font.BOLD, 20));

        JButton piratesButton = new JButton("Pirates of the Caribbean");
        piratesButton.setBackground(new Color(188, 175, 207));
        piratesButton.setForeground(Color.WHITE);
        piratesButton.setFont(new Font("Arial", Font.BOLD, 20));

        JButton forestGumpButton = new JButton("Forest Gump");
        forestGumpButton.setBackground(new Color(188, 175, 207));
        forestGumpButton.setForeground(Color.WHITE);
        forestGumpButton.setFont(new Font("Arial", Font.BOLD, 20));

        JButton rockyButton = new JButton("Rocky");
        rockyButton.setBackground(new Color(188, 175, 207));
        rockyButton.setForeground(Color.WHITE);
        rockyButton.setFont(new Font("Arial", Font.BOLD, 20));


        backgroundPanel.add(backToTheFutureButton);
        backgroundPanel.add(titanicButton);
        backgroundPanel.add(harrypotterButton);
        backgroundPanel.add(theGodfatherButton);
        backgroundPanel.add(barbieButton);
        backgroundPanel.add(oppenheimerButton);
        backgroundPanel.add(piratesButton);
        backgroundPanel.add(forestGumpButton);
        backgroundPanel.add(rockyButton);

        backToTheFutureButton.addActionListener(e -> displayFileContent("files/back_to_the_future.txt"));
        titanicButton.addActionListener(e -> displayFileContent("files/titanic.txt"));
        harrypotterButton.addActionListener(e -> displayFileContent("files/harry_potter.txt"));
        theGodfatherButton.addActionListener(e -> displayFileContent("files/the_godfather.txt"));
        barbieButton.addActionListener(e -> displayFileContent("files/barbie.txt"));
        oppenheimerButton.addActionListener(e -> displayFileContent("files/oppenheimer.txt"));
        piratesButton.addActionListener(e -> displayFileContent("files/pirates_of_the_caribbean.txt"));
        forestGumpButton.addActionListener(e -> displayFileContent("files/forest_gump.txt"));
        rockyButton.addActionListener(e -> displayFileContent("files/rocky.txt"));

        factsFrame.add(backgroundPanel);
        factsFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new FactsFrame();
    }
}
