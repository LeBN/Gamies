import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class LoadingPage extends JPanel {
    private JFrame frame;
    private Color bgColor = new Color(0,0,0);
    private Font font; // Font for the loading text

    public void loadingPage(JFrame frame, int state) {
        this.frame = frame;
        this.setBackground(bgColor);
        this.setLayout(null);

        // Loading assets
        try {
            // Load font
            URL fontURL = new URL("https://raw.githubusercontent.com/LeBN/Gamies/main/Assets/Fonts/" +
                    "PressStart2P.ttf");
            InputStream messageFontStream = fontURL.openStream();
            font = Font.createFont(Font.TRUETYPE_FONT, messageFontStream).deriveFont(21f);
        } catch (Exception e) {
            font = new Font("Serif", Font.PLAIN, 24);
            e.printStackTrace();
        }

        frame.add(this);
    }
}
