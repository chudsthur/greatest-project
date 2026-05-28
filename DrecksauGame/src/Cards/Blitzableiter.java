// Das hier ist der Ordnername in dem diese Datei liegt
package Cards;

// Hier holen wir die Informationen über einen Spieler
import Assets.Player;
// Hier holen wir die Informationen über ein Schwein
import Assets.Sau;
// Hier holen wir die Informationen über den Ablauf des Spiels
import Assets.Game;

// Das ist die Blitzableiterkarte die einen Stall vor Blitzeinschlägen schützt
public class Blitzableiter extends Card {

    // Das ist der Bauplan für die Blitzableiterkarte
    public Blitzableiter() {
        // Wir erstellen die Karte mit dem Namen Blitzableiter und einer passenden Beschreibung
        super("Blitzableiter", "Schützt einen deiner Ställe dauerhaft vor Blitzen.");
    }

    // Diese Funktion prüft ob man an den Stall einen Blitzableiter anbringen darf
    @Override
    public boolean kannGespieltWerden(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir prüfen ob das Zielschwein dem Spieler gehört der die Karte ausspielen will
        if (targetPlayer != spieler) {
            // Ein Blitzableiter darf nur am eigenen Stall angebracht werden
            return false;
        }
        
        // Wir prüfen ob die Nummer des Schweins in der eigenen Liste existiert
        if (targetSauIndex < 0 || targetSauIndex >= spieler.getSchweine().size()) {
            // Wenn die Nummer ungültig ist brechen wir ab
            return false;
        }
        
        // Wir holen uns das ausgewählte eigene Schwein
        Sau sau = spieler.getSau(targetSauIndex);
        
        // Wir prüfen ob das Schwein überhaupt einen Stall hat an den man etwas anbauen kann
        if (!sau.hasStall()) {
            // Ohne Stall kann man auch keinen Blitzableiter anbringen
            return false;
        }
        
        // Wir dürfen den Blitzableiter nur anbauen wenn noch keiner am Stall existiert
        return !sau.blitzSchutz();
    }

    // Diese Funktion baut den Blitzableiter an den Stall des Schweins an
    @Override
    public void ausfuehren(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir holen uns das ausgewählte eigene Schwein
        Sau sau = spieler.getSau(targetSauIndex);
        
        // Wir fügen den Blitzableiter zum Stall des Schweins hinzu
        sau.getStall().addBlitzableiter();
        
        // Wir schreiben eine Nachricht in das Protokoll dass der Stall nun gesichert ist
        game.logAction(spieler.getUsername() + " sichert den Stall von Schwein Nummer " + 
                       (targetSauIndex + 1) + " mit einem Blitzableiter ab.");
    }
}
