import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.net.URL;

public class Start_Menu extends JPanel implements KeyListener {
    private Image start_menu; // Image displayed
    private Image titleImage; // Title image
    private Font messageFont;
    private boolean showUnderscore = true; // Underscore visibility
    private JFrame frame;

    // Constructor
    public Start_Menu(JFrame frame) {
        this.frame = frame;
        try {
            // Load the start menu image
            URL imgURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/Levels/Start_Menu.png");
            start_menu = new ImageIcon(imgURL).getImage(); // URL to image

            // Load the title image
            URL titleImageURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/Title.png");
            titleImage = new ImageIcon(titleImageURL).getImage(); // URL to image

            // Load the message font
            URL messageFontURL = new URL("https://raw.githubusercontent.com/LeBN/Gamies/main/Assets/Fonts/PressStart2P.ttf");
            InputStream messageFontStream = messageFontURL.openStream();
            messageFont = Font.createFont(Font.TRUETYPE_FONT, messageFontStream).deriveFont(24f);

        } catch (Exception e) {
            // Error Handle
            start_menu = null;
            titleImage = null;
            messageFont = new Font("Serif", Font.PLAIN, 24);
            e.printStackTrace();
        }
        // KeyListener setup
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();

        // Timer underscore animation
        Timer timer = new Timer(500, e -> {
            showUnderscore = !showUnderscore;
            repaint();
        });
        timer.start();
    }

    // Paint the image and text on the JPanel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (start_menu != null) {
            // Image drawing
            g.drawImage(start_menu, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0, 0, 0, 145)); // 45% opacity
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            // Image not load error
            g.setColor(Color.RED);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // Title image
        int defaultWidth = 1920;
        int defaultHeight = 1080;
        if (titleImage != null) {
            int titleWidth = titleImage.getWidth(this);
            int titleHeight = titleImage.getHeight(this);
            double scaleFactor = Math.min(getWidth() / (double) defaultWidth, getHeight() / (double) defaultHeight);
            int scaledWidth = (int) (titleWidth * scaleFactor);
            int scaledHeight = (int) (titleHeight * scaleFactor);
            int x = (getWidth() - scaledWidth) / 2;
            int y = (getHeight() - scaledHeight) / 2 - 50; // Center vertically, adjust y as needed
            g.drawImage(titleImage, x, y, scaledWidth, scaledHeight, this);
        }

        // "Press Enter" message
        g.setFont(messageFont.deriveFont((float) (getWidth() * 0.0125))); // Scale font size
        g.setColor(Color.WHITE);
        String message = "Press Enter" + (showUnderscore ? "_" : "");
        double scaleX = getWidth() / (double) defaultWidth;
        double scaleY = getHeight() / (double) defaultHeight;
        // Default positions and sizes
        double defaultMessageX = 945.3;
        int messageX = (int) (defaultMessageX * scaleX);
        double defaultMessageY = 597.2;
        int messageY = (int) (defaultMessageY * scaleY);
        g.drawString(message, messageX, messageY);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // KeyListener
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            main.switchToMainMenu(frame);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
