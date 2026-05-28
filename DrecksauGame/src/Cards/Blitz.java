// Das hier ist der Ordnername in dem diese Datei liegt
package Cards;

// Hier holen wir die Informationen über einen Spieler
import Assets.Player;
// Hier holen wir die Informationen über ein Schwein
import Assets.Sau;
// Hier holen wir die Informationen über den Ablauf des Spiels
import Assets.Game;

// Das ist die Blitzkarte die den ungeschützten Stall eines Gegenspielers zerstört
public class Blitz extends Card {

    // Das ist der Bauplan für die Blitzkarte
    public Blitz() {
        // Wir erstellen die Karte mit dem Namen Blitz und einer passenden Beschreibung
        super("Blitz", "Zerstört den Stall eines Gegners (es sei denn, er hat einen Blitzableiter).");
    }

    // Diese Funktion prüft ob der Blitz auf das ausgewählte Schwein geschleudert werden darf
    @Override
    public boolean kannGespieltWerden(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir prüfen ob das Ziel einem Mitspieler gehört und nicht einem selbst
        if (targetPlayer == spieler || targetPlayer == null) {
            // Man darf keinen Blitz auf die eigenen Schweine schleudern
            return false;
        }
        
        // Wir prüfen ob die Nummer des Schweins beim Mitspieler existiert
        if (targetSauIndex < 0 || targetSauIndex >= targetPlayer.getSchweine().size()) {
            // Wenn die Nummer ungültig ist brechen wir ab
            return false;
        }
        
        // Wir holen uns das ausgewählte gegnerische Schwein
        Sau sau = targetPlayer.getSau(targetSauIndex);
        
        // Wir prüfen ob dieses Schwein überhaupt einen Stall besitzt
        if (!sau.hasStall()) {
            // Ohne Stall kann auch kein Stall vom Blitz getroffen werden
            return false;
        }
        
        // Der Blitz darf nur einschlagen wenn der Stall keinen schützenden Blitzableiter hat
        return !sau.blitzSchutz();
    }

    // Diese Funktion lässt den Blitz einschlagen und den Stall zerstören
    @Override
    public void ausfuehren(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir holen uns das ausgewählte gegnerische Schwein
        Sau sau = targetPlayer.getSau(targetSauIndex);
        
        // Wir zerstören den Stall dieses Schweins komplett
        sau.destroyStall();
        
        // Wir schreiben eine Nachricht in das Protokoll dass der Stall vom Blitz getroffen und zerstört wurde
        game.logAction("KABUMM! Ein Blitz schlägt bei " + targetPlayer.getUsername() + 
                       " bei Schwein Nummer " + (targetSauIndex + 1) + " ein und zerstört den Stall!");
    }
}
