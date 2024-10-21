import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class main extends JPanel {
    private Image start_menu; // Image displayed
    private Image titleImage; // Title image
    private Font buttonFont; // Font for the buttons
    private JFrame frame;

    // Constructor
    public main(JFrame frame) {
        this.frame = frame;
        try {
            // Load the start menu image
            URL imgURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/Levels/Start_Menu.png");
            start_menu = new ImageIcon(imgURL).getImage(); // URL to image

            // Load the title image
            URL titleImageURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/Title.png");
            titleImage = new ImageIcon(titleImageURL).getImage(); // URL to image

            // Load the button font
            URL fontURL = new URL("https://raw.githubusercontent.com/LeBN/Gamies/main/Assets/Fonts/PressStart2P.ttf");
            InputStream fontStream = fontURL.openStream();
            buttonFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(21f); // Set font size to 21

            // Load button images and resize
            Image buttonImage = new ImageIcon(new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Menu_Quit_Button.png")).getImage();
            Image buttonIconQuit = buttonImage.getScaledInstance(520, 138, Image.SCALE_SMOOTH);
            Image buttonIcon = buttonImage.getScaledInstance(825, 138, Image.SCALE_SMOOTH);

            // Create the QUIT button
            JButton quitButton = new JButton("Quit", new ImageIcon(buttonIconQuit));
            quitButton.setFont(buttonFont);
            quitButton.setBounds((1920 - 520) / 2, 4, 520, 138);
            styleButton(quitButton);
            quitButton.addActionListener(e -> System.exit(0));

            // Create the Credits button
            JButton creditsButton = new JButton("Credits", new ImageIcon(buttonIcon));
            creditsButton.setFont(buttonFont);
            creditsButton.setBounds((1920 - 825) / 2, 126, 825, 138);
            styleButton(creditsButton);
            creditsButton.addActionListener(e -> System.out.println("Credits clicked"));

            // Create the Options button
            JButton optionsButton = new JButton("Options", new ImageIcon(buttonIcon));
            optionsButton.setFont(buttonFont);
            optionsButton.setBounds((1920 - 825) / 2, 248, 825, 138);
            styleButton(optionsButton);
            optionsButton.addActionListener(e -> switchToOptionsMenu());

            // Create the Start Game button
            JButton startGameButton = new JButton("Start Game", new ImageIcon(buttonIcon));
            startGameButton.setFont(buttonFont);
            startGameButton.setBounds((1920 - 825) / 2, 365, 825, 138);
            styleButton(startGameButton);
            startGameButton.addActionListener(e -> System.out.println("Start clicked"));

            // Set layout to null for absolute positioning
            setLayout(null);

            // Add buttons to the panel
            add(quitButton);
            add(creditsButton);
            add(optionsButton);
            add(startGameButton);

        } catch (Exception e) {
            // Error Handle
            start_menu = null;
            titleImage = null;
            e.printStackTrace();
        }
    }

    public main() {}

    // Method to style buttons
    private void styleButton(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setForeground(Color.WHITE);
    }

    // Paint the image and text on the JPanel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        if (start_menu != null) {
            // Image drawing
            g.drawImage(start_menu, 0, 0, width, height, this);
            g.setColor(new Color(0, 0, 0, 105)); // 5% opacity
            g.fillRect(0, 0, width, height);
        } else {
            // Image not load error
            g.setColor(Color.RED);
            g.fillRect(0, 0, width, height);
        }

        // Draw the title image at the specified Y-coordinate and centered
        if (titleImage != null) {
            int originalTitleWidth = titleImage.getWidth(this);
            int originalTitleHeight = titleImage.getHeight(this);
            double scale = Math.min(width / 1920.0, height / 1080.0);
            int titleWidth = (int) (originalTitleWidth * scale);
            int titleHeight = (int) (originalTitleHeight * scale);
            int titleX = (width - titleWidth) / 2;
            int titleY = (int) (950 * (height / 1080.0)) - titleHeight / 2; // Adjusted Y-coordinate to keep the title at the same position
            g.drawImage(titleImage, titleX, titleY, titleWidth, titleHeight, this);
        }
    }

    // Method to switch to the Options Menu
    private void switchToOptionsMenu() {
        frame.getContentPane().removeAll();
        Options_Menu options_menu = new Options_Menu(frame);
        frame.add(options_menu);
        frame.revalidate();
        frame.repaint();
        options_menu.requestFocusInWindow();
    }

    // Method to switch to the Main Menu
    public static void switchToMainMenu(JFrame frame) {
        frame.getContentPane().removeAll();
        main main_menu = new main(frame);
        frame.add(main_menu);
        frame.revalidate();
        frame.repaint();
        main_menu.requestFocusInWindow();
    }

    // Main method
    public static void main(String[] args) {
        JFrame frame = new JFrame("Start Menu");
        Start_Menu start_menu = new Start_Menu(frame);
        frame.add(start_menu);
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}