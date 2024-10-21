import javax.swing.*;

public class main {
    public static void switchToMainMenu(JFrame frame) {
        frame.getContentPane().removeAll();
        Main_Menu mainMenu = new Main_Menu(frame); // Assuming Main_Menu is your main menu class
        frame.add(mainMenu);
        frame.revalidate();
        frame.repaint();
        mainMenu.requestFocusInWindow(); // Set focus to the new menu
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Start Menu");
        Start_Menu startMenu = new Start_Menu(frame);
        frame.add(startMenu);
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        startMenu.requestFocusInWindow(); // Request focus for key events
    }
}
