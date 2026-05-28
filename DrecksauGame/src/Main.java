// Das ist das Hauptprogramm zum Starten des Spiels
import javax.swing.*;
// Hier holen wir die grafische Oberfläche des Spiels
import Assets.GUIGame;

// Das ist die Hauptklasse des Spiels
public class Main {
    // Das ist der Startpunkt für den Computer um das Programm auszuführen
    public static void main(String[] args) {
        
        // Wir starten die grafische Oberfläche so dass das Spielfenster flüssig und stabil reagiert
        SwingUtilities.invokeLater(() -> {
            // Wir versuchen das Aussehen des Fensters an das Betriebssystem anzupassen
            try {
                // Hier wird das Design des Betriebssystems für das Spielfenster geladen
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Wenn das nicht klappt fangen wir den Fehler ab und machen einfach weiter
            } catch (Exception e) {
                // Wenn das Design nicht geladen werden kann nutzen wir einfach das Standarddesign
            }

            // Wir erstellen das Hauptfenster für das Spiel
            GUIGame gameWindow = new GUIGame();
            
            // Wir machen das Spielfenster auf dem Bildschirm sichtbar
            gameWindow.setVisible(true);
        });
    }
}