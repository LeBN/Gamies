import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;
import java.util.Properties;

public class Options_Menu extends JPanel {
    private Image start_menu; // Background image
    private Image optionsBGPanel; // Panel background image
    private Image subtitleBack; // Subtitle background image
    private Font subtitleFont; // Font for the subtitle
    private JButton fullscreenButton; // Fullscreen toggle button
    private boolean isFullscreenOn = false; // Fullscreen state
    private JFrame frame;
    private JSlider volumeSlider; // Volume slider
    private JSlider effectsSlider; // Effects slider

    private void loadOptions() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("options.txt")) {
            properties.load(input);
            isFullscreenOn = Boolean.parseBoolean(properties.getProperty("fullscreen", "false"));
            volumeSlider.setValue(Integer.parseInt(properties.getProperty("volume", "50")));
            effectsSlider.setValue(Integer.parseInt(properties.getProperty("sound_effect", "50")));
            fullscreenButton.setText(isFullscreenOn ? "ON" : "OFF");
        } catch (IOException e) {
            System.err.println("Failed to load options: " + e.getMessage());
        }
    }


    private void saveOptions() {
        Properties properties = new Properties();
        properties.setProperty("fullscreen", Boolean.toString(isFullscreenOn));
        properties.setProperty("volume", Integer.toString(volumeSlider.getValue()));
        properties.setProperty("sound_effect", Integer.toString(effectsSlider.getValue()));
        try (OutputStream output = new FileOutputStream("options.txt")) {
            properties.store(output, null);
        } catch (IOException e) {
            // Error Handle
            System.err.println("Failed to save options: " + e.getMessage());
        }
    }
    // Constructor
    public Options_Menu(JFrame frame) {
        this.frame = frame;

        try {
            // Start menu background image
            URL imgURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/Levels/Start_Menu.png");
            start_menu = new ImageIcon(imgURL).getImage(); // URL to image

            // Options menu background image
            URL optionsBGPanelURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/options_BG_panel.png");
            optionsBGPanel = new ImageIcon(optionsBGPanelURL).getImage(); // URL to image

            // Subtitle background image
            URL subtitleBackURL = new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Subtitle_Back_Longer.png");
            subtitleBack = new ImageIcon(subtitleBackURL).getImage(); // URL to image

            // Subtitle font
            URL fontURL = new URL("https://raw.githubusercontent.com/LeBN/Gamies/main/Assets/Fonts/PressStart2P.ttf");
            InputStream fontStream = fontURL.openStream();
            subtitleFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(21f); // Set font size to 21
            Font buttonFont = subtitleFont.deriveFont(15f); // Set font size to 15 for the button

            // Button image
            Image buttonImage = new ImageIcon(new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Button_Off_Released.png")).getImage();
            Image buttonIcon = buttonImage.getScaledInstance(99, 99, Image.SCALE_SMOOTH);

            // Fullscreen toggle button
            fullscreenButton = new JButton("OFF", new ImageIcon(buttonIcon));
            fullscreenButton.setBorderPainted(false);
            fullscreenButton.setContentAreaFilled(false);
            fullscreenButton.setFocusPainted(false);
            fullscreenButton.setOpaque(false);
            fullscreenButton.setPreferredSize(new Dimension(99, 99));
            fullscreenButton.setFont(buttonFont); // Set the font to PressStart2P
            fullscreenButton.setForeground(Color.decode("#c0cbdc")); // Set default text color to #c0cbdc
            fullscreenButton.setHorizontalTextPosition(SwingConstants.CENTER); // Center text horizontally
            fullscreenButton.setVerticalTextPosition(SwingConstants.CENTER); // Center text vertically

            // Mouse listener full screen
            fullscreenButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    isFullscreenOn = !isFullscreenOn;
                    fullscreenButton.setText(isFullscreenOn ? "ON" : "OFF");
                    setFullscreen(isFullscreenOn);
                }
            });

            // Load slider images
            Image sliderBackImage = new ImageIcon(new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Slider_Back.png")).getImage();
            Image sliderFrontImage = new ImageIcon(new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Slider_Front.png")).getImage();
            Image arrowLeftImage = new ImageIcon(new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Arrow_Left.png")).getImage().getScaledInstance(41, 47, Image.SCALE_SMOOTH);
            Image arrowRightImage = new ImageIcon(new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Arrow_Right.png")).getImage().getScaledInstance(41, 47, Image.SCALE_SMOOTH);

            // Create volume slider
            volumeSlider = createSlider(sliderBackImage, sliderFrontImage);
            volumeSlider.setBounds(1364, 353, 264, 81); // Position the volume slider

            // Create effects slider
            effectsSlider = createSlider(sliderBackImage, sliderFrontImage);
            effectsSlider.setBounds(1352, 459, 264, 81); // Position the effects slider

            // Create labels for the options
            JLabel fullscreenLabel = new JLabel("Fullscreen");
            fullscreenLabel.setFont(subtitleFont);
            fullscreenLabel.setForeground(Color.WHITE);
            fullscreenLabel.setBounds(285, 270, 308, 33);

            JLabel dashesLabel = new JLabel("__________________________________");
            dashesLabel.setFont(subtitleFont);
            dashesLabel.setForeground(Color.GRAY);
            dashesLabel.setBounds(593, 270, 980, 33);

            JLabel volumeLabel = new JLabel("Volume");
            volumeLabel.setFont(subtitleFont);
            volumeLabel.setForeground(Color.WHITE);
            volumeLabel.setBounds(285, 376, 308, 33);

            JLabel volumeDashesLabel = new JLabel("___________________________");
            volumeDashesLabel.setFont(subtitleFont);
            volumeDashesLabel.setForeground(Color.GRAY);
            volumeDashesLabel.setBounds(453, 377, 980, 33);

            JLabel effectsLabel = new JLabel("Sound Effects");
            effectsLabel.setFont(subtitleFont);
            effectsLabel.setForeground(Color.WHITE);
            effectsLabel.setBounds(285, 482, 308, 33);

            JLabel effectsDashesLabel = new JLabel("______________________");
            effectsDashesLabel.setFont(subtitleFont);
            effectsDashesLabel.setForeground(Color.GRAY);
            effectsDashesLabel.setBounds(649, 483, 980, 33);

            // Create arrow buttons for volume slider
            JButton volumeLeftArrow = createArrowButton(arrowLeftImage, -1, volumeSlider);
            volumeLeftArrow.setBounds(1316, 370, 41, 47);
            JButton volumeRightArrow = createArrowButton(arrowRightImage, 1, volumeSlider);
            volumeRightArrow.setBounds(1635, 370, 41, 47);

            // Create arrow buttons for effects slider
            JButton effectsLeftArrow = createArrowButton(arrowLeftImage, -1, effectsSlider);
            effectsLeftArrow.setBounds(1305, 476, 41, 47);
            JButton effectsRightArrow = createArrowButton(arrowRightImage, 1, effectsSlider);
            effectsRightArrow.setBounds(1624, 476, 41, 47);

            // Load and resize the cross image
            Image crossImage = new ImageIcon(new URL("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Cross.png")).getImage();
            Image crossIcon = crossImage.getScaledInstance(48, 42, Image.SCALE_SMOOTH);

            // Create the cross button
            JButton crossButton = new JButton(new ImageIcon(buttonIcon));
            crossButton.setBorderPainted(false);
            crossButton.setContentAreaFilled(false);
            crossButton.setFocusPainted(false);
            crossButton.setOpaque(false);
            crossButton.setBounds(1575, 100, 99, 99);
            crossButton.setLayout(new BorderLayout());
            JLabel crossLabel = new JLabel(new ImageIcon(crossIcon));
            crossLabel.setHorizontalAlignment(SwingConstants.CENTER);
            crossLabel.setVerticalAlignment(SwingConstants.CENTER);
            crossButton.add(crossLabel, BorderLayout.CENTER);
            crossButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    saveOptions(); // Save options to file
                    Main_Menu.switchToMainMenu(frame);
                }
            });


            setLayout(null);

            // Add components to the panel
            add(fullscreenLabel);
            add(dashesLabel);
            fullscreenButton.setBounds(1350, 237, 99, 99); // Updated coordinates
            add(fullscreenButton);
            add(volumeLabel);
            add(volumeDashesLabel);
            add(volumeSlider);
            add(volumeLeftArrow);
            add(volumeRightArrow);
            add(effectsLabel);
            add(effectsDashesLabel);
            add(effectsSlider);
            add(effectsLeftArrow);
            add(effectsRightArrow);
            add(crossButton);

            loadOptions(); // Load options from file

        } catch (Exception e) {
            // Error Handle
            start_menu = null;
            optionsBGPanel = null;
            subtitleBack = null;
            subtitleFont = new Font("Serif", Font.PLAIN, 26);
            e.printStackTrace();
        }
    }

    // Custom slider creation method
    private JSlider createSlider(Image backImage, Image frontImage) {
        JSlider slider = new JSlider() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the slider background
                g.drawImage(backImage, 0, 0, getWidth(), getHeight(), this);
                // Calculate the position of the thumb
                int thumbX = (int) (getValue() / (double) getMaximum() * (getWidth() - frontImage.getWidth(null)));
                // Draw the slider thumb
                g.drawImage(frontImage, thumbX, 0, frontImage.getWidth(null), getHeight(), this);
            }
        };
        slider.setOpaque(false);
        slider.setMinimum(0);
        slider.setMaximum(100);
        slider.setValue(50);
        slider.setPreferredSize(new Dimension(264, 81)); // Set slider size
        slider.addChangeListener(e -> System.out.println("Slider value: " + slider.getValue()));
        return slider;
    }

    private JButton createArrowButton(Image arrowImage, int direction, JSlider slider) {
        JButton arrowButton = new JButton(new ImageIcon(arrowImage));
        arrowButton.setBorderPainted(false);
        arrowButton.setContentAreaFilled(false);
        arrowButton.setFocusPainted(false);
        arrowButton.setOpaque(false);
        arrowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int value = slider.getValue() + direction * 10;
                slider.setValue(Math.max(slider.getMinimum(), Math.min(slider.getMaximum(), value)));
            }
        });
        return arrowButton;
    }

    // Paint on the JPanel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        if (start_menu != null) {
            // Draw the background image
            g.drawImage(start_menu, 0, 0, width, height, this);
        } else {
            // Image not load error
            g.setColor(Color.RED);
            g.fillRect(0, 0, width, height);
        }

        // Draw the options background panel centered
        if (optionsBGPanel != null) {
            int panelWidth = 1829;
            int panelHeight = 1155;
            double panelScale = Math.min(width / (double) panelWidth, height / (double) panelHeight);
            panelWidth *= panelScale;
            panelHeight *= panelScale;
            int panelX = (width - panelWidth) / 2;
            int panelY = (height - panelHeight) / 2;
            g.drawImage(optionsBGPanel, panelX, panelY, panelWidth, panelHeight, this);

            // Draw the subtitle background
            if (subtitleBack != null) {
                int subtitleWidth = 313;
                int subtitleHeight = 122;
                double subtitleScale = Math.min(panelWidth / 1829.0, panelHeight / 1155.0);
                subtitleWidth *= subtitleScale;
                subtitleHeight *= subtitleScale;
                int subtitleX = panelX + (int) (99.6 * subtitleScale) - 80; // Move subtitle 60 pixels further to the left
                int subtitleY = panelY + (int) (77 * subtitleScale) + 20; // Lower the subtitle by 20 pixels
                g.drawImage(subtitleBack, subtitleX, subtitleY, subtitleWidth, subtitleHeight, this);

                // Draw the "Options" text
                g.setFont(subtitleFont.deriveFont((float) (26 * subtitleScale)));
                g.setColor(Color.decode("#262b44")); // Set color to #262b44
                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth("Options");
                int textHeight = fm.getAscent();
                int textX = subtitleX + (subtitleWidth - textWidth) / 2;
                int textY = subtitleY + (subtitleHeight + textHeight) / 2 - fm.getDescent();

                // Adjust text position
                if (textX < 0 || textX + textWidth > width) {
                    textX = Math.max(0, Math.min(textX, width - textWidth));
                }

                g.drawString("Options", textX, textY);
            }
        }
    }

    // Fullscreen method
    private void setFullscreen(boolean fullscreen) {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (fullscreen) {
            frame.dispose();
            frame.setUndecorated(true);
            device.setFullScreenWindow(frame);
            frame.setVisible(true);
        } else {
            device.setFullScreenWindow(null);
            frame.dispose();
            frame.setUndecorated(false);
            frame.setVisible(true);
        }
    }
}