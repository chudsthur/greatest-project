// Das hier ist der Ordnername in dem diese Datei liegt
package Cards;

// Hier holen wir die Informationen über einen Spieler
import Assets.Player;
// Hier holen wir die Informationen über ein Schwein
import Assets.Sau;
// Hier holen wir die Informationen über den Ablauf des Spiels
import Assets.Game;

// Das ist die Stallkarte mit der man einen Stall für ein Schwein bauen kann
public class Stall extends Card {

    // Das ist der Bauplan für die Stallkarte
    public Stall() {
        // Wir erstellen die Karte mit dem Namen Stall und einer passenden Beschreibung
        super("Stall", "Baut einen schützenden Stall für eines deiner Schweine.");
    }

    // Diese Funktion prüft ob man auf das ausgewählte Schwein einen Stall bauen darf
    @Override
    public boolean kannGespieltWerden(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir prüfen ob das Zielschwein dem Spieler gehört der am Zug ist
        if (targetPlayer != spieler) {
            // Ein Stall darf nicht auf ein Schwein eines anderen Spielers gebaut werden
            return false;
        }
        
        // Wir prüfen ob die Nummer des Schweins in der Liste existiert
        if (targetSauIndex < 0 || targetSauIndex >= spieler.getSchweine().size()) {
            // Wenn die Nummer ungültig ist brechen wir ab
            return false;
        }
        
        // Wir holen uns das ausgewählte eigene Schwein
        Sau sau = spieler.getSau(targetSauIndex);
        // Wir dürfen den Stall nur bauen wenn das Schwein bisher noch keinen Stall hat
        return !sau.hasStall();
    }

    // Diese Funktion baut den Stall für das ausgewählte Schwein auf
    @Override
    public void ausfuehren(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir holen uns das ausgewählte Schwein des aktiven Spielers
        Sau sau = spieler.getSau(targetSauIndex);
        // Wir bauen den Stall für das Schwein auf
        sau.buildStall();
        
        // Wir schreiben eine Nachricht in das Protokoll dass der Stall gebaut wurde
        game.logAction(spieler.getUsername() + " baut einen gemütlichen Stall für Schwein Nummer " + (targetSauIndex + 1));
    }
}
