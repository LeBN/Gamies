import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class main {
    private static boolean fullscreen;
    private static int volume;
    private static int soundEffect;
    private static int fpsLimit;
    private static boolean showCollision;

    public static void loadOptions() {
        String query = "SELECT fullscreen, volume, sound_effect, fps_limit, show_collision FROM Options LIMIT 1";
        System.out.println("Attempting to load options from the database..."); // Debug statement
        try (Connection conn = DbManagement.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Database connection established and query executed."); // Debug statement

            if (rs.next()) {
                fullscreen = rs.getBoolean("fullscreen");
                volume = rs.getInt("volume");
                soundEffect = rs.getInt("sound_effect");
                fpsLimit = rs.getInt("fps_limit");
                showCollision = rs.getBoolean("show_collision");

                // Debug: Print the loaded options
                System.out.println("Loaded Options:");
                System.out.println("Fullscreen: " + fullscreen);
                System.out.println("Volume: " + volume);
                System.out.println("Sound Effect: " + soundEffect);
                System.out.println("FPS Limit: " + fpsLimit);
                System.out.println("Show Collision: " + showCollision);
            } else {
                System.out.println("No options found in the database."); // Debug statement
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Error handling
        }
    }

    public static void limitFPS() {
        int fps = fpsLimit; // Assuming fpsLimit is set somewhere in your code
        if (fps <= 0) {
            fps = 60; // Default to 60 FPS if fpsLimit is zero or negative
        }
        long frameDuration = 1000 / fps;
        long startTime = System.currentTimeMillis();

        while (true) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime < frameDuration) {
                try {
                    Thread.sleep(frameDuration - elapsedTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            startTime = System.currentTimeMillis();
        }
    }

    public static void switchToMainMenu(JFrame frame) {
        // Implement the logic to switch to the main menu
        frame.getContentPane().removeAll();
        Main_Menu mainMenu = new Main_Menu(frame);
        frame.add(mainMenu);
        frame.revalidate();
        frame.repaint();
        mainMenu.requestFocusInWindow();
    }

    public static void main(String[] args) {
        DbManagement.initializeDatabase(); // Ensure database is created

        // Debug: Print statement before loading options
        System.out.println("Before loading options");

        loadOptions(); // Load options from database

        // Debug: Print statement after loading options
        System.out.println("After loading options");

        JFrame frame = new JFrame("Purple Heart");

        try {
            URL iconURL = new URL(
                    "https://github.com/LeBN/Gamies/raw/" +
                            "6c73e2c2216df9f27596bc9e29fc6d88c524424e/Assets/UI/Purple_Heart_Logo.png"
            );
            Image icon = new ImageIcon(iconURL).getImage();
            frame.setIconImage(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Start_Menu startMenu = new Start_Menu(frame);
        frame.add(startMenu);
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        startMenu.requestFocusInWindow(); // Focus for key events

        // Add key listener for Enter key
        startMenu.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    switchToMainMenu(frame);
                }
            }
        });
        startMenu.setFocusable(true);

        if (fullscreen) {
            frame.dispose(); // Close the window
            frame.setUndecorated(true); // Remove window decorations
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
            frame.setVisible(true); // Show the window
        }
        limitFPS();
    }
}