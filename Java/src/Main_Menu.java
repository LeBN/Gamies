import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.net.URL;

public class Main_Menu extends JPanel {
    private Image start_menu; // Image displayed
    private Image titleImage; // Title image
    private Font buttonFont; // Font for the buttons
    private JFrame frame;
    private int selectedButtonIndex = 0; // Index for tracking the selected button
    private JButton[] buttons; // Array to hold the buttons
    private Image selectionCrystal; // Image for selection crystal

    // Constructor
    public Main_Menu(JFrame frame) {
        this.frame = frame;
        try {
            // Load the start menu image
            URL imgURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/Levels/Start_Menu.png");
            start_menu = new ImageIcon(imgURL).getImage(); // URL to image

            // Load the title image
            URL titleImageURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/Title.png");
            titleImage = new ImageIcon(titleImageURL).getImage(); // URL to image

            // Load the button font
            URL fontURL = new URL("https://raw.githubusercontent.com/LeBN/Gamies/main/Assets/Fonts/PressStart2P.ttf");
            InputStream fontStream = fontURL.openStream();
            buttonFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(21f); // Set font size to 21

            // Load button images and resize
            Image buttonImage = new ImageIcon(new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Menu_Quit_Button.png")).getImage();
            Image buttonIconQuit = buttonImage.getScaledInstance(520, 138, Image.SCALE_SMOOTH);
            Image buttonIcon = buttonImage.getScaledInstance(825, 138, Image.SCALE_SMOOTH);

            // Load the selection crystal
            URL crystalURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/selection_crystal.png");
            selectionCrystal = new ImageIcon(crystalURL).getImage().getScaledInstance(49, 93, Image.SCALE_SMOOTH);

            // Create the buttons and add them to an array for easy access
            buttons = new JButton[4];

            // Create the QUIT button
            JButton quitButton = new JButton("Quit", new ImageIcon(buttonIconQuit));
            quitButton.setFont(buttonFont);
            quitButton.setBounds((1920 - 520) / 2, 4, 520, 138);
            styleButton(quitButton);
            quitButton.addActionListener(e -> System.exit(0));
            buttons[0] = quitButton; // Add to the array

            // Create the Credits button
            JButton creditsButton = new JButton("Credits", new ImageIcon(buttonIcon));
            creditsButton.setFont(buttonFont);
            creditsButton.setBounds((1920 - 825) / 2, 126, 825, 138);
            styleButton(creditsButton);
            creditsButton.addActionListener(e -> System.out.println("Credits clicked"));
            buttons[1] = creditsButton; // Add to the array

            // Create the Options button
            JButton optionsButton = new JButton("Options", new ImageIcon(buttonIcon));
            optionsButton.setFont(buttonFont);
            optionsButton.setBounds((1920 - 825) / 2, 248, 825, 138);
            styleButton(optionsButton);
            optionsButton.addActionListener(e -> switchToOptionsMenu());
            buttons[2] = optionsButton; // Add to the array

            // Create the Start Game button
            JButton startGameButton = new JButton("Start Game", new ImageIcon(buttonIcon));
            startGameButton.setFont(buttonFont);
            startGameButton.setBounds((1920 - 825) / 2, 365, 825, 138);
            styleButton(startGameButton);
            startGameButton.addActionListener(e -> System.out.println("Start clicked"));
            buttons[3] = startGameButton; // Add to the array

            // Set layout to null for absolute positioning
            setLayout(null);

            // Add buttons to the panel
            for (JButton button : buttons) {
                add(button);
            }

            // Add MouseListener for hover effects
            for (int i = 0; i < buttons.length; i++) {
                final int index = i; // For the lambda expression
                buttons[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        selectedButtonIndex = index;
                        repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        // Optional: reset the selection when the mouse exits the button
                        // selectedButtonIndex = 0; // Uncomment this if you want to reset to "Quit"
                        repaint();
                    }
                });
            }

            // Add KeyListener for navigation
            setFocusable(true);
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        selectedButtonIndex = (selectedButtonIndex - 1 + buttons.length) % buttons.length;
                        repaint();
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        selectedButtonIndex = (selectedButtonIndex + 1) % buttons.length;
                        repaint();
                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        buttons[selectedButtonIndex].doClick();
                    }
                }
            });

            repaint(); // Call repaint here to show the initial selection

        } catch (Exception e) {
            // Error Handle
            start_menu = null;
            titleImage = null;
            e.printStackTrace();
        }
    }

    // Method to style buttons
    private void styleButton(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false); // Make buttons transparent to see crystals behind
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setForeground(Color.WHITE);
    }

    // Paint the image and text on the JPanel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        if (start_menu != null) {
            // Image drawing
            g.drawImage(start_menu, 0, 0, width, height, this);
            g.setColor(new Color(0, 0, 0, 105)); // 5% opacity
            g.fillRect(0, 0, width, height);
        } else {
            // Image not load error
            g.setColor(Color.RED);
            g.fillRect(0, 0, width, height);
        }

        // Draw the title image
        if (titleImage != null) {
            int originalTitleWidth = titleImage.getWidth(this);
            int originalTitleHeight = titleImage.getHeight(this);
            double scale = Math.min(width / 1920.0, height / 1080.0);
            int titleWidth = (int) (originalTitleWidth * scale);
            int titleHeight = (int) (originalTitleHeight * scale);
            int titleX = (width - titleWidth) / 2;
            int titleY = (int) (950 * (height / 1080.0)) - titleHeight / 2;
            g.drawImage(titleImage, titleX, titleY, titleWidth, titleHeight, this);
        }

        // Draw the selection crystals based on the selected button
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        switch (selectedButtonIndex) {
            case 0: // Quit
                drawCrystal(g2d, 700, 70, 90);   // Right crystal
                drawCrystal(g2d, 1200, 70, -90);  // Left crystal
                break;
            case 1: // Credits
                drawCrystal(g2d, 620, 195, 90);   // Right crystal
                drawCrystal(g2d, 1300, 195, -90);  // Left crystal
                break;
            case 2: // Options
                drawCrystal(g2d, 620, 315, 90);   // Right crystal
                drawCrystal(g2d, 1300, 315, -90);  // Left crystal
                break;
            case 3: // Start Game
                drawCrystal(g2d, 620, 435, 90);   // Right crystal
                drawCrystal(g2d, 1300, 435, -90);  // Left crystal
                break;
        }
    }

    // Method to draw the selection crystal with rotation
    private void drawCrystal(Graphics2D g2d, double x, double y, int angle) {
        g2d.translate(x, y);
        g2d.rotate(Math.toRadians(angle));
        g2d.drawImage(selectionCrystal, -selectionCrystal.getWidth(null) / 2, -selectionCrystal.getHeight(null) / 2, null);
        g2d.rotate(-Math.toRadians(angle)); // Reset rotation
        g2d.translate(-x, -y); // Reset translation
    }

    // Method to switch to the Options Menu
    private void switchToOptionsMenu() {
        frame.getContentPane().removeAll();
        Options_Menu options_menu = new Options_Menu(frame);
        frame.add(options_menu);
        frame.revalidate();
        frame.repaint();
        options_menu.requestFocusInWindow();
    }

    // Method to switch to the Main Menu
    public static void switchToMainMenu(JFrame frame) {
        frame.getContentPane().removeAll();
        Main_Menu main_menu = new Main_Menu(frame);
        frame.add(main_menu);
        frame.revalidate();
        frame.repaint();
        main_menu.requestFocusInWindow();
    }

    // Main method
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Menu");
        Main_Menu mainMenu = new Main_Menu(frame);
        frame.add(mainMenu);
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        mainMenu.requestFocusInWindow();
    }
}
