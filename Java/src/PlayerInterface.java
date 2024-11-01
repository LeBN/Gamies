import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PlayerInterface extends JPanel {
    private JFrame frame;
    private JButton KW, KA, KS, KD;
    private Image button, buttonPressed;
    private Image minimap;
    private Image itemsIndic;
    private Image[] items;
    private JLabel itemCount;
    private Image chatBox;

    public PlayerInterface(JFrame frame) {
        this.frame = frame;

        try {
            // Load the Button released
            button = loadImageURL(this,
                    "https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Button_Off_Released.png", 3);
            buttonPressed = loadImageURL(this,
                    "https://github.com/LeBN/Gamies/raw/main/Assets/UI/UI_Button_Off_Pressed.png", 3);
            minimap = loadImageURL(this,
                    "https://github.com/LeBN/Gamies/raw/main/Assets/UI/Minimap/UI_Minimap_0011.png", 3);
            itemsIndic = loadImageURL(this,
                    "https://github.com/LeBN/Gamies/raw/main/Assets/UI/Item_Indic.png", 3);
            items[0] = loadImageURL(this,
                    "https://github.com/LeBN/Gamies/raw/main/Assets/Items/Coin.png", 3);
            chatBox = loadImageURL(this,
                    "https://github.com/LeBN/Gamies/raw/main/Assets/UI/ChatBox.png", 3);
            

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("PlayerInterface");
    }
}
