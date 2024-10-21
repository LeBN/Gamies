import javax.swing.*;

public class main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game Menu");
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

        // Enter key pressed, switch to Options_Menu
        System.out.println("Enter Pressed");
        switchToMainMenu(frame);
    }
// function to switch from Start_Menu to Main_Menu
    public static void switchToMainMenu(JFrame frame) {
        // Remove the Start_Menu
        frame.getContentPane().removeAll();

        // Add the Main_Menu
        Main_Menu main_menu = new Main_Menu();
        frame.add(main_menu);

        // Refresh the frame
        frame.revalidate();
        frame.repaint();

        // Request focus for key events in Main_Menu if needed
        main_menu.requestFocusInWindow();
    }

    // Function to switch from Start_Menu to Options_Menu
    public static void switchToOptionsMenu(JFrame frame) {
        // Remove the Start_Menu
        frame.getContentPane().removeAll();

        // Add the Options_Menu, passing the frame to the constructor
        Options_Menu options_menu = new Options_Menu(frame); // Pass frame as argument
        frame.add(options_menu);

        // Refresh the frame
        frame.revalidate();
        frame.repaint();

        // Request focus for key events in Options_Menu if needed
        options_menu.requestFocusInWindow();
    }
}