import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.net.URL;

public class Start_Menu extends JPanel implements KeyListener {
    private Image start_menu; // Image displayed
    private boolean start = false; // Start flag
    private Font titleFont;
    private Font messageFont;
    private boolean showUnderscore = true; // Flag to toggle underscore visibility

    // Constructor
    public Start_Menu() {
        try {
            // Load the image
            URL imgURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/Levels/Start_Menu.png");
            start_menu = new ImageIcon(imgURL).getImage(); // URL to image

            // Load the title font
            try {
                URL titleFontURL = new URL("https://raw.githubusercontent.com/LeBN/Gamies/main/Assets/Fonts/Saved%20by%20Zero%20Rg.otf");
                InputStream titleFontStream = titleFontURL.openStream();
                titleFont = Font.createFont(Font.TRUETYPE_FONT, titleFontStream).deriveFont(83.5f);
            } catch (FontFormatException e) {
                titleFont = new Font("Serif", Font.BOLD, 83);
            }

            // Load the message font
            try {
                URL messageFontURL = new URL("https://raw.githubusercontent.com/LeBN/Gamies/main/Assets/Fonts/PressStart2P.ttf");
                InputStream messageFontStream = messageFontURL.openStream();
                messageFont = Font.createFont(Font.TRUETYPE_FONT, messageFontStream).deriveFont(24f);
            } catch (FontFormatException e) {
                messageFont = new Font("Serif", Font.PLAIN, 24);
            }

        } catch (Exception e) {
            // Error Handle
            start_menu = null;
            titleFont = new Font("Serif", Font.BOLD, 83);
            messageFont = new Font("Serif", Font.PLAIN, 24);
            e.printStackTrace();
        }
        // KeyListener setup
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();

        // Timer to toggle underscore visibility
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUnderscore = !showUnderscore;
                repaint();
            }
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

        // Draw the title
        g.setFont(titleFont);
        g.setColor(new Color(0xc175ff));
        FontMetrics fm = g.getFontMetrics(titleFont);
        int titleWidth = fm.stringWidth("Purple Heart");
        int x = (getWidth() - titleWidth) / 2;
        g.drawString("Purple Heart", x, (int) 407.3);

        // Draw the "Press Enter" message
        g.setFont(messageFont);
        g.setColor(Color.WHITE);
        String message = "Press Enter" + (showUnderscore ? "_" : "");
        g.drawString(message, (int) 945.3, (int) 597.2);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // KeyListener
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            start = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    // Start getter
    public boolean getStart() {
        return start;
    }

    // Main method
    public static void main(String[] args) {
        JFrame frame = new JFrame("Start Menu");
        Start_Menu start_menu = new Start_Menu();
        frame.add(start_menu);
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        start_menu.requestFocusInWindow();

        // Wait for Enter key to be pressed
        while (!start_menu.getStart()) {
            try {
                Thread.sleep(100); // CPU sleep
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Start Menu Closed");
    }
}