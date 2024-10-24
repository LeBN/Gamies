import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Options_Menu extends JPanel {
    private Image start_menu; // Background image
    private Image optionsBGPanel; // Panel background image
    private Image subtitleBack; // Subtitle background image
    private Font subtitleFont; // Font for the subtitle
    private JButton fullscreenButton; // Fullscreen toggle button
    private JButton showCollisionButton; // Show collision toggle button
    private boolean isFullscreenOn = false; // Fullscreen state
    private boolean isShowCollisionOn = false; // Show collision state
    private JFrame frame;
    private JSlider volumeSlider; // Volume slider
    private JSlider effectsSlider; // Effects slider
    private JSlider fpsSlider; // FPS limit slider
    private int fpsLimit = 60; // Default FPS limit
    private JLabel fpsValueLabel; // Label to display FPS value

    // Load options from the database
    private void loadOptions() {
        String query = "SELECT fullscreen, show_collision, volume, sound_effect, fps_limit FROM Options";
        try (Connection conn = DbManagement.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                isFullscreenOn = rs.getBoolean("fullscreen");
                fullscreenButton.setText(isFullscreenOn ? "ON" : "OFF");

                isShowCollisionOn = rs.getBoolean("show_collision");
                showCollisionButton.setText(isShowCollisionOn ? "ON" : "OFF");

                volumeSlider.setValue(rs.getInt("volume"));
                effectsSlider.setValue(rs.getInt("sound_effect"));
                fpsLimit = rs.getInt("fps_limit");
                fpsSlider.setValue(fpsLimit);
                fpsValueLabel.setText(fpsLimit == 241 ? "Unlimited" : fpsLimit + " FPS");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save options to the database
    private void saveOptions() {
        String updateQuery = "UPDATE Options SET fullscreen = ?, show_collision = ?, volume = ?, sound_effect = ?, fps_limit = ?";
        String insertQuery = "INSERT INTO Options (fullscreen, show_collision, volume, sound_effect, fps_limit) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbManagement.connect();
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // Set parameters for update statement
            updateStmt.setBoolean(1, isFullscreenOn);
            updateStmt.setBoolean(2, isShowCollisionOn);
            updateStmt.setInt(3, volumeSlider.getValue());
            updateStmt.setInt(4, effectsSlider.getValue());
            updateStmt.setInt(5, fpsSlider.getValue());

            // Execute update statement
            int rowsUpdated = updateStmt.executeUpdate();

            // If no rows were updated, execute insert statement
            if (rowsUpdated == 0) {
                insertStmt.setBoolean(1, isFullscreenOn);
                insertStmt.setBoolean(2, isShowCollisionOn);
                insertStmt.setInt(3, volumeSlider.getValue());
                insertStmt.setInt(4, effectsSlider.getValue());
                insertStmt.setInt(5, fpsSlider.getValue());

                insertStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Error handling
        }
    }

    // Constructor
    public Options_Menu(JFrame frame) {
        this.frame = frame;

        try {
            start_menu = loadImage("https://github.com/LeBN/Gamies/raw/main/Assets/Levels/Start_Menu.png");
            optionsBGPanel = loadImage("https://github.com/LeBN/Gamies/raw/main/Assets/UI/options_BG_panel.png");
            subtitleBack = loadImage("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Subtitle_Back_Longer.png");
            subtitleFont = loadFont("https://raw.githubusercontent.com/LeBN/Gamies/main/Assets/Fonts/PressStart2P.ttf", 21f);
            Font buttonFont = subtitleFont.deriveFont(15f); // Set font size to 15 for the button
            Image buttonIcon = loadImage("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Button_Off_Released.png").getScaledInstance(99, 99, Image.SCALE_SMOOTH);

            // Initialize buttons and sliders
            initializeButtons(buttonFont, buttonIcon);
            initializeSliders();

            setLayout(null);
            addComponents();

            loadOptions(); // Load options from the database

        } catch (Exception e) {
            handleInitializationError(e);
        }
    }

    // Load an image from a URL
    private Image loadImage(String url) throws Exception {
        return new ImageIcon(new URL(url)).getImage();
    }

    // Load a font from a URL
    private Font loadFont(String url, float size) throws Exception {
        InputStream fontStream = new URL(url).openStream();
        return Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(size);
    }

    // Initialize buttons
    private void initializeButtons(Font buttonFont, Image buttonIcon) {
        fullscreenButton = createToggleButton("OFF", buttonFont, buttonIcon, new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isFullscreenOn = !isFullscreenOn;
                fullscreenButton.setText(isFullscreenOn ? "ON" : "OFF");
                setFullscreen(isFullscreenOn);
            }
        });

        showCollisionButton = createToggleButton("OFF", buttonFont, buttonIcon, new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isShowCollisionOn = !isShowCollisionOn;
                showCollisionButton.setText(isShowCollisionOn ? "ON" : "OFF");
            }
        });
    }

    // Create a toggle button
    private JButton createToggleButton(String text, Font font, Image icon, MouseAdapter mouseAdapter) {
        JButton button = new JButton(text, new ImageIcon(icon));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setPreferredSize(new Dimension(99, 99));
        button.setFont(font);
        button.setForeground(Color.decode("#c0cbdc"));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.addMouseListener(mouseAdapter);
        return button;
    }

    // Initialize sliders
    private void initializeSliders() throws Exception {
        Image sliderBackImage = loadImage("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Slider_Back.png");
        Image sliderFrontImage = loadImage("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Slider_Front.png");

        volumeSlider = createSlider(sliderBackImage, sliderFrontImage);
        volumeSlider.setBounds(1364, 353, 264, 81);

        effectsSlider = createSlider(sliderBackImage, sliderFrontImage);
        effectsSlider.setBounds(1352, 459, 264, 81);

        fpsSlider = createSlider(sliderBackImage, sliderFrontImage);
        fpsSlider.setMinimum(30);
        fpsSlider.setMaximum(241);
        fpsSlider.setValue(fpsLimit);
        fpsSlider.setBounds(1352, 671, 264, 81);

        fpsValueLabel = new JLabel(fpsLimit == 241 ? "Unlimited" : fpsLimit + " FPS");
        fpsValueLabel.setFont(subtitleFont);
        fpsValueLabel.setForeground(Color.WHITE);
        int fpsValueLabelX = fpsSlider.getX() + (fpsSlider.getWidth() - fpsValueLabel.getPreferredSize().width) / 2;
        fpsValueLabel.setBounds(fpsValueLabelX, fpsSlider.getY() + fpsSlider.getHeight() + 10, fpsValueLabel.getPreferredSize().width, 20);

        fpsSlider.addChangeListener(e -> {
            int value = fpsSlider.getValue();
            fpsValueLabel.setText(value == 241 ? "Unlimited" : value + " FPS");
            int updatedFpsValueLabelX = fpsSlider.getX() + (fpsSlider.getWidth() - fpsValueLabel.getPreferredSize().width) / 2;
            fpsValueLabel.setBounds(updatedFpsValueLabelX, fpsSlider.getY() + fpsSlider.getHeight() + 10, fpsValueLabel.getPreferredSize().width, 20);
        });
    }

    // Handle initialization errors
    private void handleInitializationError(Exception e) {
        start_menu = null;
        optionsBGPanel = null;
        subtitleBack = null;
        subtitleFont = new Font("Serif", Font.PLAIN, 26);
        e.printStackTrace();
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
        slider.setPreferredSize(new Dimension(264, 81));
        return slider;
    }

    // Create arrow button for sliders
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

    // Add components to the panel
    private void addComponents() throws Exception {
        // Add labels and buttons
        addLabel("Fullscreen", 285, 270);
        addLabel("Volume", 285, 376);
        addLabel("Sound Effects", 285, 482);
        addLabel("Show Collision", 285, 588);
        addLabel("FPS Limit", 285, 694);

        addDashesLabel(593, 270);
        addDashesLabel(453, 377);
        addDashesLabel(649, 483);
        addDashesLabel(649, 589);
        addDashesLabel(649, 695);

        add(fullscreenButton);
        fullscreenButton.setBounds(1350, 237, 99, 99);

        int arrowButtonHeight = 47; // Height of the arrow button
        int sliderHeight = 81; // Height of the slider
        int arrowButtonY = (sliderHeight - arrowButtonHeight) / 2; // Center the arrow button vertically

        add(volumeSlider);
        add(createArrowButton(loadImage("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Arrow_Left.png").getScaledInstance(41, 47, Image.SCALE_SMOOTH), -1, volumeSlider)).setBounds(1300, 353 + arrowButtonY, 41, 47);
        add(createArrowButton(loadImage("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Arrow_Right.png").getScaledInstance(41, 47, Image.SCALE_SMOOTH), 1, volumeSlider)).setBounds(1630, 353 + arrowButtonY, 41, 47);

        add(effectsSlider);
        add(createArrowButton(loadImage("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Arrow_Left.png").getScaledInstance(41, 47, Image.SCALE_SMOOTH), -1, effectsSlider)).setBounds(1300, 459 + arrowButtonY, 41, 47);
        add(createArrowButton(loadImage("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Arrow_Right.png").getScaledInstance(41, 47, Image.SCALE_SMOOTH), 1, effectsSlider)).setBounds(1630, 459 + arrowButtonY, 41, 47);

        add(showCollisionButton);
        showCollisionButton.setBounds(1350, 565, 99, 99);

        add(fpsSlider);
        add(fpsValueLabel);
        add(createArrowButton(loadImage("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Arrow_Left.png").getScaledInstance(41, 47, Image.SCALE_SMOOTH), -1, fpsSlider)).setBounds(1300, 671 + arrowButtonY, 41, 47);
        add(createArrowButton(loadImage("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Arrow_Right.png").getScaledInstance(41, 47, Image.SCALE_SMOOTH), 1, fpsSlider)).setBounds(1630, 671 + arrowButtonY, 41, 47);

        // Add cross button
        Image crossIcon = loadImage("https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Cross.png").getScaledInstance(48, 42, Image.SCALE_SMOOTH);
        JButton crossButton = createToggleButton("", subtitleFont, crossIcon, new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                saveOptions();
                Main_Menu.switchToMainMenu(frame);
            }
        });
        crossButton.setBounds(1575, 100, 99, 99);
        add(crossButton);
    }

    // Add a label to the panel
    private void addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(subtitleFont);
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, 308, 33);
        add(label);
    }

    // Add a dashes label to the panel
    private void addDashesLabel(int x, int y) {
        JLabel dashesLabel = new JLabel("______________________");
        dashesLabel.setFont(subtitleFont);
        dashesLabel.setForeground(Color.GRAY);
        dashesLabel.setBounds(x, y, 980, 33);
        add(dashesLabel);
    }

    // Paint on the JPanel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        if (start_menu != null) {
            g.drawImage(start_menu, 0, 0, width, height, this);
        } else {
            g.setColor(Color.RED);
            g.fillRect(0, 0, width, height);
        }

        if (optionsBGPanel != null) {
            int panelWidth = 1829;
            int panelHeight = 1155;
            double panelScale = Math.min(width / (double) panelWidth, height / (double) panelHeight);
            panelWidth *= panelScale;
            panelHeight *= panelScale;
            int panelX = (width - panelWidth) / 2;
            int panelY = (height - panelHeight) / 2;
            g.drawImage(optionsBGPanel, panelX, panelY, panelWidth, panelHeight, this);

            if (subtitleBack != null) {
                int subtitleWidth = 313;
                int subtitleHeight = 122;
                double subtitleScale = Math.min(panelWidth / 1829.0, panelHeight / 1155.0);
                subtitleWidth *= subtitleScale;
                subtitleHeight *= subtitleScale;
                int subtitleX = panelX + (int) (99.6 * subtitleScale) - 80; // Move subtitle 60 pixels further to the left
                int subtitleY = panelY + (int) (77 * subtitleScale) + 20; // Lower the subtitle by 20 pixels
                g.drawImage(subtitleBack, subtitleX, subtitleY, subtitleWidth, subtitleHeight, this);

                g.setFont(subtitleFont.deriveFont((float) (26 * subtitleScale)));
                g.setColor(Color.decode("#262b44")); // Set color to #262b44
                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth("Options");
                int textHeight = fm.getAscent();
                int textX = subtitleX + (subtitleWidth - textWidth) / 2;
                int textY = subtitleY + (subtitleHeight + textHeight) / 2 - fm.getDescent();

                g.setFont(subtitleFont.deriveFont((float) (21 * subtitleScale)));
                g.setColor(Color.WHITE);

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