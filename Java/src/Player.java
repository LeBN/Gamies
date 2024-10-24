import javax.swing.*;
import java.awt.event.KeyEvent;

public class Player {
    private double playerX, playerY;
    private int playerSpeed = 5;  // Player movement speed
    private Vector2D playerMovement = new Vector2D(); // Player movement
    private int up=0, down=0, left=0, right=0;

    public Player(JFrame frame, double x, double y) {
        playerX = x;
        playerY = y;
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
