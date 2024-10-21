import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class main {
    private static boolean fullscreen = false;
    private static int volume = 63; // Valeur par défaut
    private static int soundEffect = 70; // Valeur par défaut

    public static void loadSettings() {
        try (BufferedReader reader = new BufferedReader(new FileReader("options.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("fullscreen=")) {
                    fullscreen = Boolean.parseBoolean(line.split("=")[1]);
                } else if (line.startsWith("volume=")) {
                    volume = Integer.parseInt(line.split("=")[1]);
                } else if (line.startsWith("sound_effect=")) {
                    soundEffect = Integer.parseInt(line.split("=")[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'erreur de lecture
        }
    }

    public static void switchToMainMenu(JFrame frame) {
        frame.getContentPane().removeAll();
        Main_Menu mainMenu = new Main_Menu(frame); // Assurez-vous que Main_Menu est votre classe de menu principal
        frame.add(mainMenu);
        frame.revalidate();
        frame.repaint();
        mainMenu.requestFocusInWindow(); // Mettre le focus sur le nouveau menu
    }

    public static void main(String[] args) {
        loadSettings(); // Charger les paramètres dès le démarrage

        JFrame frame = new JFrame("Start Menu");
        Start_Menu startMenu = new Start_Menu(frame);
        frame.add(startMenu);
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        startMenu.requestFocusInWindow(); // Demander le focus pour les événements de touche

        // Passer en plein écran si nécessaire
        if (fullscreen) {
            frame.dispose(); // Fermer la fenêtre actuelle
            frame.setUndecorated(true); // Supprimer les bordures
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiser la fenêtre
            frame.setVisible(true); // Afficher à nouveau la fenêtre
        }
    }
}
