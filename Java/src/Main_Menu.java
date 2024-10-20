import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class Main_Menu extends JPanel {
    private Image start_menu; // Image displayed
    private Image titleImage; // Title image

    // Constructor
    public Main_Menu() {
        try {
            // Load the start menu image
            URL imgURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/Levels/Start_Menu.png");
            start_menu = new ImageIcon(imgURL).getImage(); // URL to image

            // Load the title image
            URL titleImageURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/Title.png");
            titleImage = new ImageIcon(titleImageURL).getImage(); // URL to image

        } catch (Exception e) {
            // Error Handle
            start_menu = null;
            titleImage = null;
            e.printStackTrace();
        }
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

        // Draw the title image at the specified Y-coordinate and centered
        if (titleImage != null) {
            int titleWidth = titleImage.getWidth(this);
            int titleHeight = titleImage.getHeight(this);
            int titleX = (getWidth() - titleWidth) / 2;
            double scaleY = getHeight() / 1080.0;
            int titleY = (int) (950 * scaleY) - titleHeight / 2; // Adjusted Y-coordinate
            g.drawImage(titleImage, titleX, titleY, titleWidth, titleHeight, this);
        }
    }

    // Main method
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Menu");
        Main_Menu main_menu = new Main_Menu();
        frame.add(main_menu);
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}