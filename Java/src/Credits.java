import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.net.URL;

public class Credits extends JPanel {
    private Image start_menu; // Background image
    private Image creditsBGPanel; // Panel background image
    private Image subtitleBack; // Subtitle background image
    private Font subtitleFont; // Font for the subtitle
    private Font creditsFont; // Font for credits
    private JFrame frame; // Reference to the frame

    public Credits(JFrame frame) {
        this.frame = frame;

        try {
            // Load the start menu image
            URL imgURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/Levels/Start_Menu.png");
            start_menu = new ImageIcon(imgURL).getImage(); // URL to image

            // Load the credits background panel image
            URL creditsBGPanelURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/options_BG_panel.png");
            creditsBGPanel = new ImageIcon(creditsBGPanelURL).getImage(); // URL to image

            // Load the subtitle background image
            URL subtitleBackURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Subtitle_Back_Longer.png");
            subtitleBack = new ImageIcon(subtitleBackURL).getImage(); // URL to image

            // Load the subtitle font
            URL fontURL = new URL("https://raw.githubusercontent.com/LeBN/Gamies/main/Assets/Fonts/PressStart2P.ttf");
            InputStream fontStream = fontURL.openStream();
            subtitleFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(21f); // Set font size to 21

            // Load the credits font
            creditsFont = subtitleFont.deriveFont(21f); // Use same font for credits

            // Load and resize the cross image
            Image buttonImage = new ImageIcon(new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Button_Off_Released.png")).getImage();
            Image buttonIcon = buttonImage.getScaledInstance(99, 99, Image.SCALE_SMOOTH);
            Image crossImage = new ImageIcon(new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Cross.png")).getImage();
            Image crossIcon = crossImage.getScaledInstance(48, 42, Image.SCALE_SMOOTH);

            // Create the cross button
            JButton crossButton = new JButton(new ImageIcon(buttonIcon));
            crossButton.setBorderPainted(false);
            crossButton.setContentAreaFilled(false);
            crossButton.setFocusPainted(false);
            crossButton.setOpaque(false);
            crossButton.setBounds(1575, 100, 99, 99);
            crossButton.setLayout(new BorderLayout());
            JLabel crossLabel = new JLabel(new ImageIcon(crossIcon));
            crossLabel.setHorizontalAlignment(SwingConstants.CENTER);
            crossLabel.setVerticalAlignment(SwingConstants.CENTER);
            crossButton.add(crossLabel, BorderLayout.CENTER);

            crossButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Main_Menu.switchToMainMenu(frame); // Retour au Main Menu
                }
            });

            // Set layout to null for absolute positioning
            setLayout(null);

            // Add cross button
            add(crossButton);

        } catch (Exception e) {
            start_menu = null;
            creditsBGPanel = null;
            subtitleBack = null;
            subtitleFont = new Font("Serif", Font.PLAIN, 26);
            creditsFont = new Font("Serif", Font.PLAIN, 21);
            e.printStackTrace();
            setLayout(null);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        if (start_menu != null) {
            // Draw the background image
            g.drawImage(start_menu, 0, 0, width, height, this);
        } else {
            g.setColor(Color.RED);
            g.fillRect(0, 0, width, height);
        }

        // Credits background panel centered
        if (creditsBGPanel != null) {
            int panelWidth = 1829;
            int panelHeight = 1155;
            double panelScale = Math.min(width / (double) panelWidth, height / (double) panelHeight);
            panelWidth *= panelScale;
            panelHeight *= panelScale;
            int panelX = (width - panelWidth) / 2;
            int panelY = (height - panelHeight) / 2;
            g.drawImage(creditsBGPanel, panelX, panelY, panelWidth, panelHeight, this);

            // Subtitle background image
            if (subtitleBack != null) {
                int subtitleWidth = 313;
                int subtitleHeight = 122;
                double subtitleScale = Math.min(panelWidth / 1829.0, panelHeight / 1155.0);
                subtitleWidth *= subtitleScale;
                subtitleHeight *= subtitleScale;
                int subtitleX = panelX + (int) (99.6 * subtitleScale) - 80;
                int subtitleY = panelY + (int) (77 * subtitleScale) + 20;
                g.drawImage(subtitleBack, subtitleX, subtitleY, subtitleWidth, subtitleHeight, this);

                // Draw "Credits" text centered on the subtitle background
                g.setFont(subtitleFont.deriveFont((float) (26 * subtitleScale)));
                g.setColor(Color.decode("#262b44"));
                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth("Credits");
                int textHeight = fm.getAscent();
                int textX = subtitleX + (subtitleWidth - textWidth) / 2;
                int textY = subtitleY + (subtitleHeight + textHeight) / 2 - fm.getDescent();

                g.drawString("Credits", textX, textY);
            }
        }

        // Draw creators information
        g.setFont(creditsFont);
        g.setColor(Color.WHITE); // Set text color to white
        g.drawString("Creators:", 256, 272); // Position for "Creators"

        int creatorsY = 312; // Increase spacing here
        g.drawString("Gaël Floquet - Efrei Paris - UCI DCE Student", 256, creatorsY);
        creatorsY += 40; // Move down for next line
        g.drawString("Natis Beaurepère - Efrei Paris - UCI DCE Student", 256, creatorsY);

        // Add extra space for Sources
        creatorsY += 100; // Doubled the space to 100

        // Draw sources information
        g.setColor(Color.WHITE);
        g.drawString("Sources:", 256, creatorsY);
        g.drawString("Images by Midjourney (Midjourney.com)", 256, creatorsY + 30);
        g.drawString("Game assets from Itch (itch.io)", 256, creatorsY + 60);

        // Add new sources
        g.drawString("Pixelarium Interface by LukeThePolice", 256, creatorsY + 90);
        g.drawString("Free Cyberpunk Resource Pixel Icons by Free Game Assets", 256, creatorsY + 120);

        // Underline "Creators" and "Sources"
        drawUnderline(g, "Creators:", 256, 272);
        drawUnderline(g, "Sources:", 256, creatorsY);
    }

    private void drawUnderline(Graphics g, String text, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g.drawLine(x, y + 2, x + textWidth, y + 2); // Draw underline below the text
    }
}
