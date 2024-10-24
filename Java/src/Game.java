import java.awt.*;
import javax.swing.*;
import java.net.URL;

public class Game extends JPanel {
    private JFrame frame;
    private int width, height;
    private Level level;
    private Player player;
    private PauseMenu pauseMenu;
    private Collisions collisions;
    private double playerX=1012, playerY=646;

    public Game(JFrame frame) {
        this.frame = frame;
        this.width = frame.getWidth();
        this.height = frame.getHeight();

        // Set the layout manager to null so we can use absolute positioning
        this.setLayout(null);

        // Initialize the level and add it to the game panel
        level = new Level(0);
        level.setBounds(0, 0, width, height); // Make the level panel fill the entire game area
        add(level); // Add the level panel to the game panel

        collisions = new Collisions(frame, playerX, playerY);
        collisions.setBounds(0, 0, width, height);
        frame.add(collisions);
        collisions.setFocusable(true);  // Make sure it can receive key events
        frame.revalidate();
        collisions.repaint();
        collisions.requestFocusInWindow();

        // Adding a delay to ensure the frame is visible before requesting focus
        SwingUtilities.invokeLater(() -> {
            collisions.requestFocusInWindow();
        });

        player = new Player(frame, playerX, playerY);
        player.setBounds(0, 0, width, height);
        frame.add(player);
        player.setFocusable(true);  // Make sure it can receive key events
        frame.revalidate();
        player.repaint();
        player.requestFocusInWindow();

        // Adding a delay to ensure the frame is visible before requesting focus
        SwingUtilities.invokeLater(() -> {
            player.requestFocusInWindow();
        });
    }

    private class Level extends JPanel {
        private int level;
        private Image bgImage;

        protected Level(int level) {
            this.level = level;

            // Attempt to load the background image
            try {
                URL imgURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/Levels/Level_0_0.png");
                bgImage = new ImageIcon(imgURL).getImage();
                System.out.println("Image loaded successfully"); // Debugging message
            } catch (Exception e) {
                System.err.println("Failed to load image: " + e.getMessage());
                e.printStackTrace();
            }

            // Set the size of this panel to match the frame
            this.setPreferredSize(new Dimension(width, height));

            // Force the panel to repaint after loading the image
            this.revalidate();
            this.repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Fill the background with black (to ensure something is drawn)
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, width, height); // Fill background with black

            // If the image is loaded, draw it, otherwise, print a fallback message
            if (bgImage != null) {
                g2d.drawImage(bgImage, 0, 0, width, height, this);
            } else {
                g2d.setColor(Color.RED); // Fallback color in case the image doesn't load
                g2d.drawString("Background image not loaded", 50, 50);
            }
        }
    }

    // Options menu switch
    private void switchToOptionsMenu() {
        frame.getContentPane().removeAll();
        Options_Menu options_menu = new Options_Menu(frame);
        frame.add(options_menu);
        frame.revalidate();
        frame.repaint();
        options_menu.requestFocusInWindow();
    }

    // Main menu switch
    public static void switchToMainMenu(JFrame frame) {
        frame.getContentPane().removeAll();
        Main_Menu main_menu = new Main_Menu(frame);
        frame.add(main_menu);
        frame.revalidate();
        frame.repaint();
        main_menu.requestFocusInWindow();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game testing");
        Game game = new Game(frame);
        frame.add(game);
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.revalidate(); // Revalidate the layout
        frame.repaint(); // Repaint to ensure it's drawn
        game.requestFocusInWindow(); // Ensure focus is on the game
    }
}
