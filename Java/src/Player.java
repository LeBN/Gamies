import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Player extends JPanel {
    private double playerX = 1012, playerY = 646;
    private Collisions collisions;
    private Image playerShadow;
    private BufferedImage playerIdle, playerWalk;
    private Image playerFrame;
    private int animation=5;
    private boolean walk=false;
    private int timer1 = 0, timer2 = 0;
    private MediaTracker tracker;

    public Player(JFrame frame, double x, double y, Collisions collisions) {
        this.playerX = x;
        this.playerY = y;
        this.collisions = collisions;
        tracker = new MediaTracker(this);

        setOpaque(false);

        try {
            // Load the player Shadow
            URL PShadowURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/Player/Shadow.png");
            playerShadow = new ImageIcon(PShadowURL).getImage(); // URL to image
            playerShadow = playerShadow.getScaledInstance(playerShadow.getWidth(this)*3,
                    playerShadow.getHeight(this)*3, Image.SCALE_SMOOTH);

            // Load the player character image
            URL PIdleURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/Player/idle.png");
            Image PIdle = new ImageIcon(PIdleURL).getImage(); // URL to image
            playerIdle = toBufferedImage(PIdle.getScaledInstance(PIdle.getWidth(this)*3,
                    PIdle.getHeight(this)*3, Image.SCALE_SMOOTH));

            // Load the player character image
            URL PWalkURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/Player/walk.png");
            Image PWalk = new ImageIcon(PWalkURL).getImage(); // URL to image
            playerWalk = toBufferedImage(PWalk.getScaledInstance(PWalk.getWidth(this)*3,
                    PWalk.getHeight(this)*3, Image.SCALE_SMOOTH));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Repaint the screen regularly
        Timer timer = new Timer(16, e -> {
            repaint();
            playerX = collisions.playerX;
            playerY = collisions.playerY;
            timer1++;
            if (timer1 > 9) {
                timer2++;
                timer1 = 0;
            }
            if (timer2 > 7) timer2 = 0;
        });  // ~60 FPS
        timer.start();
    }

    public BufferedImage toBufferedImage(Image img) {
        tracker.addImage(img, 0);
        try {
            tracker.waitForAll(); // Wait for the image to load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bImage = new BufferedImage(img.getWidth(this), img.getHeight(this),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image onto the buffered image
        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bImage;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        walk = false;

        /*
        *  2 3 4
        * 1  P  5
        *  1 0 5
        */
        if (collisions.playerMovement.x > 1 && collisions.playerMovement.y == 0) {
            animation = 5;
            walk = true;
        } else if (collisions.playerMovement.x > 1 && collisions.playerMovement.y > 1) {
            animation = 5;
            walk = true;
        } else if (collisions.playerMovement.x == 0 && collisions.playerMovement.y > 1) {
            animation = 0;
            walk = true;
        } else if (collisions.playerMovement.x < -1 && collisions.playerMovement.y > 1) {
            animation = 1;
            walk = true;
        } else if (collisions.playerMovement.x < -1 && collisions.playerMovement.y == 0) {
            animation = 1;
            walk = true;
        } else if (collisions.playerMovement.x < -1 && collisions.playerMovement.y < -1) {
            animation = 2;
            walk = true;
        } else if (collisions.playerMovement.x == 0 && collisions.playerMovement.y < -1) {
            animation = 3;
            walk = true;
        } else if (collisions.playerMovement.x > 1 && collisions.playerMovement.y < -1) {
            animation = 4;
            walk = true;
        }

        /*walk = false;
        animation = 0;*/

        if (playerShadow != null) {
            g2d.drawImage(playerShadow, (int) playerX-playerShadow.getWidth(this) + 71,
                    (int) playerY-playerShadow.getHeight(this) + 67, this);
        }

        if (playerIdle != null || playerWalk != null) {
            if (walk) {
                playerFrame = playerWalk.getSubimage(
                        timer2*playerWalk.getWidth()/8, animation*playerWalk.getHeight()/6,
                        playerWalk.getWidth()/8, playerWalk.getHeight()/6
                );
            } else {
                playerFrame = playerIdle.getSubimage(
                        timer2*playerIdle.getWidth()/8, animation*playerIdle.getHeight()/6,
                        playerIdle.getWidth()/8, playerIdle.getHeight()/6
                );
            }

            g2d.drawImage(playerFrame, (int) playerX-playerFrame.getWidth(this) + 71,
                    (int) playerY-playerFrame.getHeight(this) + 67, this);
        }

        /*g2d.setColor(Color.BLACK); // Set text color
        g2d.setFont(new Font("Arial", Font.PLAIN, 50)); // Set font style and size

        // Draw the mouse position in the bottom right corner
        String mousePositionText = "Player: ("+playerX+", "+playerY+")";
        FontMetrics metrics = g2d.getFontMetrics();
        int textWidth = metrics.stringWidth(mousePositionText);
        g2d.drawString(mousePositionText, getWidth() - textWidth - 10, getHeight() - 100);*/
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Player p = new Player(frame, 1920/2, 1080/2, new Collisions(frame, 1920/2, 1080/2));
        frame.add(p);
    }

}
