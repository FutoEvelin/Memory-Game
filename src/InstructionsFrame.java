import javax.swing.*;
import java.awt.*;
import java.io.*;

public class InstructionsFrame {
    JFrame frame;

    public InstructionsFrame(String fileName) {
        String content = displayFileContent(fileName);
        if (content == null) {
            JOptionPane.showMessageDialog(null, "Error loading instructions.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        frame = new JFrame("Instructions");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JTextArea textArea = new JTextArea(content);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private String displayFileContent(String fileName) {
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
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }


}
