import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class Collisions extends JPanel {
    protected double playerX = 1012, playerY = 646, playerRadius = 25;  // Circle player properties
    private List<Polygon> obstacles = new ArrayList<>();  // Obstacles list
    private int playerSpeed = 5;  // Player movement speed
    protected Vector2D playerMovement = new Vector2D(); // Player movement
    protected int up=0, down=0, left=0, right=0;
    private int mouseX = 0; // Variable to store the mouse X position
    private int mouseY = 0; // Variable to store the mouse Y position
    private double friction = 0.75;
    private int opacity = 0;

    public Collisions(Frame frame, double playerX, double playerY) {
        this.playerX = playerX;
        this.playerY = playerY;

        obstacles.add(new Polygon(
                new int[]{827, 760, 835, 883, 907, 910},
                new int[]{869, 826, 735, 744, 779, 823},
                6
        ));

        /*obstacles.add(new Polygon(
                new int[]{1222, 1406, 1227, 1009, 911, 1026, 946, 810, 372, 147, 140,   0, 0, 1920, 1920, 1505, 1540,
                1566, 1510, 1314},
                new int[]{ 707,  585,  473,  586, 528,  446, 396, 431, 405, 563, 878, 878, 0,    0, 1080,  898,  870,
                 670,  647,  773},
                20
        ));*/

        obstacles.add(new Polygon(
                new int[]{1009, 915, 915, 1133, 1227},
                new int[]{ 591, 549, 512,  373,  473},
                5
        ));

        obstacles.add(new Polygon(
                new int[]{1202, 1404, 1418},
                new int[]{ 473,  584,  431},
                3
        ));

        obstacles.add(new Polygon(
                new int[]{1310, 1228, 1228, 1464, 1574},
                new int[]{779, 727, 703,  524,  598},
                5
        ));

        if (main.showCollision) {
            opacity = 100;
        }

        // KeyListener to move the player
        setFocusable(true);

        // Mouse motion listener to track mouse movements
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        setFocusable(true); // Make sure the panel can be focused
        requestFocusInWindow(); // Request focus for this panel


        setOpaque(false);

        // Repaint the screen regularly
        Timer timer = new Timer(16, e -> {
            movePlayer();
            repaint();
        });  // ~60 FPS
        timer.start();
    }

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    // Handle player movement and check for collisions
    public void movePlayer() {
        // Store the collisions
        List<Polygon> collisions = new ArrayList<>();
        int numberOfCollisions = 0;

        // Store the original movement
        double moveX = playerMovement.x;
        double moveY = playerMovement.y;

        // Move the player
        playerX += moveX;
        playerY += moveY;
        if (playerX < 0) playerX = 0;
        if (playerY < 0) playerY = 0;
        if (playerX > 1920) playerX = 1920;
        if (playerY > 1080) playerY = 1080;

        // Collision check for every obstacle
        for (Polygon obstacle : obstacles) {
            if (SATCirclePolygonCollision(playerX, playerY, playerRadius, obstacle)) {
                collisions.add(obstacle);
                numberOfCollisions++;
            }
        }

        if (numberOfCollisions > 1) {
            // Block the player
            playerX -= moveX;
            playerY -= moveY;
        } else if (numberOfCollisions != 0) {
            resolveCollisionWithSliding(collisions.getFirst(), playerMovement);
        }
    }

    // Render the player and obstacles
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the player (circle)
        g2d.setColor(new Color(0, 0, 255, opacity));
        g2d.fillOval((int) (playerX - playerRadius), (int) (playerY - playerRadius), (int) (playerRadius * 2),
                (int) (playerRadius * 2));

        // Draw obstacles
        g2d.setColor(new Color(100, 0, 0, opacity));
        for (Polygon obstacle : obstacles) {
            g2d.fill(obstacle);
        }

        // Set color and font for displaying mouse position
        if (opacity != 0){
            g2d.setColor(Color.WHITE); // Set text color
            g2d.setFont(new Font("Arial", Font.PLAIN, 50)); // Set font style and size

            // Draw the mouse position in the bottom right corner
            String mousePositionText = "Mouse: (" + mouseX + ", " + mouseY + ")";
            FontMetrics metrics = g2d.getFontMetrics();
            int textWidth = metrics.stringWidth(mousePositionText);
            g2d.drawString(mousePositionText, getWidth() - textWidth - 10, getHeight() - 10);
        }
    }

    // Resolve the collision by sliding the player along the surface
    private void resolveCollisionWithSliding(Polygon polygon, Vector2D move) {
        // Get the closest edge to the player that caused the collision or the information that 2 or more edges
        // are in collision with the player
        int closestEdgeIndex = getClosestEdgeToCircle(polygon, playerX, playerY, playerRadius);

        if (closestEdgeIndex != -1) {
            double x1 = polygon.xpoints[closestEdgeIndex];
            double y1 = polygon.ypoints[closestEdgeIndex];
            double x2 = polygon.xpoints[(closestEdgeIndex + 1) % polygon.npoints];
            double y2 = polygon.ypoints[(closestEdgeIndex + 1) % polygon.npoints];

            // Edge vector
            Vector2D edge = new Vector2D(x2 - x1, y2 - y1);

            // Sliding vector
            Vector2D slide = Vector2D.mul(edge, Vector2D.dot(edge, playerMovement)/edge.dot(edge));
            //slide.mul(friction);

            // Apply the sliding movement
            playerX += slide.x;
            playerY += slide.y;
        }

        playerX -= move.x;
        playerY -= move.y;
        if (playerX < 0) playerX = 0;
        if (playerY < 0) playerY = 0;
        if (playerX > 1920) playerX = 1920;
        if (playerY > 1080) playerY = 1080;
    }

    // SAT Circle-to-Polygon Collision Detection
    private boolean SATCirclePolygonCollision(double circleX, double circleY, double radius, Polygon polygon) {
        // Check collision on the axes formed by the polygon edges
        if (!checkCollisionOnAxesForCircle(polygon, circleX, circleY, radius)) {
            return false;
        }

        // Check collision on the axis from the circle center to the closest polygon point
        int closestPointIndex = findClosestPointOnPolygon(circleX, circleY, polygon);
        int closestX = polygon.xpoints[closestPointIndex];
        int closestY = polygon.ypoints[closestPointIndex];

        // Create an axis from the circle's center to the closest polygon point
        int axisX = (int) (closestX - circleX);
        int axisY = (int) (closestY - circleY);

        double[] projectionCircle = projectCircle(circleX, circleY, radius, axisX, axisY);
        double[] projectionPolygon = projectShape(polygon, axisX, axisY);

        return projectionCircle[1] >= projectionPolygon[0] && projectionPolygon[1] >= projectionCircle[0];
    }

    // Check collision on the polygon's axes (as before)
    private boolean checkCollisionOnAxesForCircle(Polygon polygon, double circleX, double circleY, double radius) {
        int numPoints = polygon.npoints;

        for (int i = 0; i < numPoints; i++) {
            int x1 = polygon.xpoints[i];
            int y1 = polygon.ypoints[i];
            int x2 = polygon.xpoints[(i + 1) % numPoints];
            int y2 = polygon.ypoints[(i + 1) % numPoints];

            // Get the axis perpendicular to the edge
            int axisX = -(y2 - y1);
            int axisY = x2 - x1;

            double[] projectionCircle = projectCircle(circleX, circleY, radius, axisX, axisY);
            double[] projectionPolygon = projectShape(polygon, axisX, axisY);

            if (projectionCircle[1] < projectionPolygon[0] || projectionPolygon[1] < projectionCircle[0]) {
                return false;  // No collision
            }
        }

        return true;  // Collision detected
    }

    // Project a circle onto an axis (as before)
    private double[] projectCircle(double circleX, double circleY, double radius, double axisX, double axisY) {
        double centerProjection = (circleX * axisX + circleY * axisY) / Math.sqrt(axisX * axisX + axisY * axisY);
        return new double[]{centerProjection - radius, centerProjection + radius};
    }

    // Project a polygon onto an axis (as before)
    private double[] projectShape(Polygon shape, int axisX, int axisY) {
        double min = Double.MAX_VALUE, max = -Double.MAX_VALUE;

        for (int i = 0; i < shape.npoints; i++) {
            int x = shape.xpoints[i];
            int y = shape.ypoints[i];

            // Project the point onto the axis
            double projection = (x * axisX + y * axisY) / Math.sqrt(axisX * axisX + axisY * axisY);

            min = Math.min(min, projection);
            max = Math.max(max, projection);
        }

        return new double[]{min, max};
    }

    // Find the closest point on the polygon to the circle's center (as before)
    private int findClosestPointOnPolygon(double circleX, double circleY, Polygon polygon) {
        int closestIndex = 0;
        double closestDistance = Double.MAX_VALUE;

        for (int i = 0; i < polygon.npoints; i++) {
            int x = polygon.xpoints[i];
            int y = polygon.ypoints[i];

            double distance = Math.sqrt((x - circleX) * (x - circleX) + (y - circleY) * (y - circleY));
            if (distance < closestDistance) {
                closestDistance = distance;
                closestIndex = i;
            }
        }

        return closestIndex;
    }

    // Get the closest edge to the circle's center and return -1 if 2 or more edges are at the same distance
    private int getClosestEdgeToCircle(Polygon polygon, double circleX, double circleY, double circleR) {
        int closestEdgeIndex = 0;
        double[] distances = new double[polygon.npoints];
        double closestDistance = Double.MAX_VALUE;

        for (int i = 0; i < polygon.npoints; i++) {
            int x1 = polygon.xpoints[i];
            int y1 = polygon.ypoints[i];
            int x2 = polygon.xpoints[(i + 1) % polygon.npoints];
            int y2 = polygon.ypoints[(i + 1) % polygon.npoints];

            // Calculate the distance from the circle's center to the edge
            double distance = pointToLineDistance(circleX, circleY, x1, y1, x2, y2);
            distances[i] = distance;
            if (distance < closestDistance) {
                closestDistance = distance;
                closestEdgeIndex = i;
            }
        }

        for (int i = 0; i < polygon.npoints; i++) {
            if (i != closestEdgeIndex && distances[i] == closestDistance) {
                return -1;
            }
        }

        return closestEdgeIndex;
    }

    // Helper function to calculate distance from a point to a line segment
    private double pointToLineDistance(double px, double py, double x1, double y1, double x2, double y2) {
        double lengthSquared = Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
        if (lengthSquared == 0) return Math.sqrt(Math.pow(px - x1, 2) + Math.pow(py - y1, 2));

        double t = ((px - x1) * (x2 - x1) + (py - y1) * (y2 - y1)) / lengthSquared;
        t = Math.max(0, Math.min(1, t));

        double projX = x1 + t * (x2 - x1);
        double projY = y1 + t * (y2 - y1);

        return Math.sqrt(Math.pow(projX - px, 2) + Math.pow(projY - py, 2));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Collisions Testing");
        Collisions panel = new Collisions(frame, 1012, 646);
        frame.add(panel);
        frame.setSize(1920, 1080);  // Set the window size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
