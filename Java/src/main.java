import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class main {
    private static boolean fullscreen = false;
    private static int volume = 50; // Default value
    private static int soundEffect = 50; // Default value
    private static int fpsLimit = 60; // Default FPS limit
    private static boolean showCollision = false; // Default show_collision

    public static void loadOptions() {
        try (BufferedReader reader = new BufferedReader(new FileReader("options.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("fullscreen=")) {
                    fullscreen = Boolean.parseBoolean(line.split("=")[1]);
                } else if (line.startsWith("volume=")) {
                    volume = Integer.parseInt(line.split("=")[1]);
                } else if (line.startsWith("sound_effect=")) {
                    soundEffect = Integer.parseInt(line.split("=")[1]);
                } else if (line.startsWith("fps_limit=")) {
                    fpsLimit = Integer.parseInt(line.split("=")[1]);
                } else if (line.startsWith("show_collision=")) {
                    showCollision = Boolean.parseBoolean(line.split("=")[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Error handling
        }
    }

    public static void limitFPS() {
        // Logic to limit FPS can be added here
        // This is a basic placeholder logic. You can enhance it further.
        long targetTime = 1000 / fpsLimit; // Time per frame in milliseconds
        long startTime;
        long elapsedTime;

        while (true) {
            startTime = System.currentTimeMillis();
            elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = targetTime - elapsedTime;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
        loadOptions(); // Load options from file
        loadOptions();

        JFrame frame = new JFrame("Purple Heart");

        // Définir l'icône de la fenêtre
        try {
            URL iconURL = new URL(
                    "https://github.com/LeBN/Gamies/raw/" +
                            "6c73e2c2216df9f27596bc9e29fc6d88c524424e/Assets/UI/Purple_Heart_Logo.png"
            );
            Image icon = new ImageIcon(iconURL).getImage();
            frame.setIconImage(icon);
        } catch (Exception e) {
            e.printStackTrace(); // Gestion d'erreur si l'image ne peut pas être chargée
        }

        Start_Menu startMenu = new Start_Menu(frame);
        frame.add(startMenu);
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        startMenu.requestFocusInWindow(); // Focus for key events

        if (fullscreen) {
            frame.dispose(); // Close the window
            frame.setUndecorated(true); // Remove window decorations
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
            frame.setVisible(true); // Show the window
        }

        // Start limiting FPS after setting up the frame
        limitFPS();
    }
}
