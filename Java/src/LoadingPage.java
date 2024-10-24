import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class LoadingPage extends JPanel {
    private JFrame frame;
    private Color bgColor = new Color(0, 0, 0); // Black background
    private Font font; // Font for loading text

    public LoadingPage(JFrame frame, int state) {
        this.frame = frame;

        try {
            // Load custom font from URL
            URL fontURL = new URL("https://raw.githubusercontent.com/LeBN/Gamies/main/Assets/Fonts/PressStart2P.ttf");
            InputStream messageFontStream = fontURL.openStream();
            font = Font.createFont(Font.TRUETYPE_FONT, messageFontStream).deriveFont(21f);
            setLayout(null);
        } catch (Exception e) {
            font = new Font("Serif", Font.PLAIN, 24); // Default font
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        // Fill background with black
        g.setColor(bgColor);
        g.fillRect(0, 0, width, height); // Fill panel

        g.setFont(font);
        g.setColor(Color.WHITE); // Set text color to white

        // Center text
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth("Loading");
        int x = (width - textWidth) / 2; // X position
        int y = height / 2; // Y position

        g.drawString("Loading", x, y); // Draw text
    }
}
