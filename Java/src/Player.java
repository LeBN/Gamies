import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;

public class Player extends JPanel {
    private double playerX, playerY;
    private int playerSpeed = 5;  // Player movement speed
    private Vector2D playerMovement = new Vector2D(); // Player movement
    private int up=0, down=0, left=0, right=0;
    private Image playerShadow;
    private Image playerImage;

    public Player(JFrame frame, double x, double y) {
        playerX = x;
        playerY = y;

        try {
            // Load the start menu image
            URL imgURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/Player/Shadow.png");
            playerShadow = new ImageIcon(imgURL).getImage(); // URL to image
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (playerImage != null) {
            g2d.drawImage(playerShadow, (int) playerX-playerImage.getWidth(this),
                    (int) playerY-playerImage.getHeight(this), this);
        }
    }

    // Set the movement direction for the player based on the key pressed
    private void setPlayerMovement(int keyCode, boolean pressed) {
        int movement = pressed ? playerSpeed : 0;

        if (keyCode == KeyEvent.VK_UP) up = -movement;
        if (keyCode == KeyEvent.VK_DOWN) down = movement;
        if (keyCode == KeyEvent.VK_LEFT) left = -movement;
        if (keyCode == KeyEvent.VK_RIGHT) right = movement;

        playerMovement.x = right + left;
        playerMovement.y = up + down;
    }
}
