import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class main {
    private static boolean fullscreen = false;
    private static int volume = 50; // Default value
    private static int soundEffect = 50; // Default value

    public static void loadSettings() {
        try (BufferedReader reader = new BufferedReader(new FileReader("options.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("fullscreen=")) {
                    fullscreen = Boolean.parseBoolean(line.split("=")[1]);
                } else if (line.startsWith("volume=")) {
                    volume = Integer.parseInt(line.split("=")[1]);
                } else if (line.startsWith("sound_effect=")) {
                    soundEffect = Integer.parseInt(line.split("=")[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Error handling
        }
    }

    public static void switchToMainMenu(JFrame frame) {
        frame.getContentPane().removeAll();
        Main_Menu mainMenu = new Main_Menu(frame); // New instance of Main_Menu
        frame.add(mainMenu);
        frame.revalidate();
        frame.repaint();
        mainMenu.requestFocusInWindow(); // Focus for key events
    }

    public static void main(String[] args) {
        loadSettings(); // Load settings from file

        JFrame frame = new JFrame("Start Menu");
        Start_Menu startMenu = new Start_Menu(frame);
        frame.add(startMenu);
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        startMenu.requestFocusInWindow(); // Focus for key events

        //
        if (fullscreen) {
            frame.dispose(); // Close the window
            frame.setUndecorated(true); // Remove window decorations
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
            frame.setVisible(true); // Show the window
        }
    }
}
