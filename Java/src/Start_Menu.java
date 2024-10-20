import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class Start_Menu extends JPanel implements KeyListener {
    private Image start_menu; // Image displayed
    private boolean start = false; // Start flag

    // Constructor
    public Start_Menu() {
        try {
            // Load the image
            URL imgURL = new URL("https://github.com/LeBN/Gamies/raw/ac0dcd3abd768b9d297312f8d66b51b29e000085/Assets/Levels/Start_Menu.png");
            start_menu = new ImageIcon(imgURL).getImage(); // URL to image
            System.out.println("Image loaded successfully!");
        } catch (Exception e) {
            // Error Handle
            System.out.println("Image not found or error loading!");
            start_menu = null;
            e.printStackTrace();
        }
        // KeyListener setup
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    // Paint the image on the JPanel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (start_menu != null) {
            // Image drawing
            g.drawImage(start_menu, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Image not load error
            g.setColor(Color.RED);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
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
        System.out.println("Enter key pressed");
    }
}
