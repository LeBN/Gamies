import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class PlayerInterface extends JPanel {
    private JFrame frame;
    private JButton KW, KA, KS, KD;
    private Image button, buttonPressed;
    private Image minimap;
    private Image itemsIndic;
    private ArrayList<Image> items=new ArrayList<Image>();
    private JLabel itemCount;
    private Image chatBox;
    private Font pixelFont;

    public PlayerInterface(JFrame frame) {
        this.frame = frame;

        setOpaque(false);
        setLayout(null);

        try {
            // Load the Images
            button = loadImageURL(this,
                    "https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Button_Off_Released.png", 5);
            buttonPressed = loadImageURL(this,
                    "https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Button_Off_Pressed.png", 5);
            minimap = loadImageURL(this,
                    "https://github.com/LeBN/Gamies/raw/main/Assets/UI/Minimap/UI_Minimap_0011.png", 5);
            itemsIndic = loadImageURL(this,
                    "https://github.com/LeBN/Gamies/raw/main/Assets/UI/Item_Indic.png", 5);
            items.add(loadImageURL(this,
                    "https://github.com/LeBN/Gamies/raw/main/Assets/Items/Coin.png", 5));
            chatBox = loadImageURL(this,
                    "https://github.com/LeBN/Gamies/raw/main/Assets/UI/ChatBox.png", 5);

            // Load the swing components
            InputStream pixelFontStream = new URL(
                    "https://raw.githubusercontent.com/LeBN/Gamies/main/Assets/Fonts/PressStart2P.ttf").openStream();
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, pixelFontStream).deriveFont(23f);
            KW = createKeyButton("W", pixelFont, button);
            add(KW);
            KA = createKeyButton("A", pixelFont, button);
            add(KA);
            KS = createKeyButton("S", pixelFont, button);
            add(KS);
            KD = createKeyButton("D", pixelFont, button);
            add(KD);
            itemCount = new JLabel();
            itemCount.setFont(pixelFont);
            itemCount.setText("0/8");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int fHeight = frame.getHeight();
        int fWidth = frame.getWidth();

        g2d.drawImage(minimap, fWidth - minimap.getWidth(this) - 5,
                0, this);
        g2d.drawImage(itemsIndic, 0,
                0, this);
        g2d.drawImage(items.get(0), 5,
                15, this);
        g2d.setFont(pixelFont);
        g2d.setColor(Color.WHITE);
        g2d.drawString("0", 45, 48);
    }

    public static Image loadImageURL(JPanel p, String imgURL) throws Exception {
        Image img = new ImageIcon(new URL(imgURL)).getImage();
        return img.getScaledInstance(img.getWidth(p), img.getHeight(p), Image.SCALE_SMOOTH);
    }

    public static Image loadImageURL(JPanel p, String imgURL, double dimension) throws Exception {
        Image img = new ImageIcon(new URL(imgURL)).getImage();
        return img.getScaledInstance((int) (img.getWidth(p)*dimension), (int) (img.getHeight(p)*dimension), Image.SCALE_SMOOTH);
    }

    public static Image loadImageURL(JPanel p, String imgURL, double width, double height) throws Exception {
        Image img = new ImageIcon(new URL(imgURL)).getImage();
        return img.getScaledInstance((int) (img.getWidth(p)*width), (int) (img.getHeight(p)*height), Image.SCALE_SMOOTH);
    }

    public static JButton createKeyButton(String text, Font font, Image icon) {
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
        return button;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PlayerInterface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.getContentPane().add(new PlayerInterface(frame));
        frame.pack();
        frame.setVisible(true);
        PlayerInterface p = new PlayerInterface(frame);
        frame.add(p);
    }
}
