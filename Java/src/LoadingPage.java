import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class LoadingPage extends JPanel {
    private JFrame frame;
    private Color bgColor = new Color(0,0,0);
    private Font font; // Font for the loading text

    public LoadingPage(JFrame frame, int state) {
        this.frame = frame;

        try {
            // Load font
            URL fontURL = new URL("https://raw.githubusercontent.com/LeBN/Gamies/main/Assets/Fonts/" +
                    "PressStart2P.ttf");
            InputStream messageFontStream = fontURL.openStream();
            font = Font.createFont(Font.TRUETYPE_FONT, messageFontStream).deriveFont(21f);
            setLayout(null);
        } catch (Exception e) {
            font = new Font("Serif", Font.PLAIN, 24);
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        g.setColor(bgColor);
        g.setFont(font);
        g.setColor(Color.WHITE); // Set text color to white
        g.drawString("Loading", 100, 100);
    }
}
