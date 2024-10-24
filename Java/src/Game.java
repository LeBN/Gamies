import java.util.*;
import javax.swing.*;

public class Game extends JPanel {
    private JFrame frame;
    private int width, height;

    public Game(JFrame frame) {
        this.frame = frame;
        width = frame.getWidth();
        height = frame.getHeight();
        Collisions collisions = new Collisions(frame);
        loadLevel(0);
    }

    private void loadLevel(int levelNumber) {

    }

    private class Level {
        private int level;
        private String bgPath;

        protected Level(int level) {
            this.level = level;
        }
    }
}
