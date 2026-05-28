// Das hier ist der Ordnername in dem diese Datei liegt
package Cards;

// Hier holen wir die Informationen über einen Spieler
import Assets.Player;
// Hier holen wir die Informationen über ein Schwein
import Assets.Sau;
// Hier holen wir die Informationen über den Ablauf des Spiels
import Assets.Game;

// Das ist die Matschkarte die ein Schwein schmutzig macht
public class Matsch extends Card {

    // Das ist der Bauplan für die Matschkarte
    public Matsch() {
        // Wir erstellen die Karte mit dem Namen Matsch und einer passenden Beschreibung
        super("Matsch", "Macht eines deiner sauberen Schweine schmutzig.");
    }

    // Diese Funktion prüft ob die Matschkarte auf ein bestimmtes Schwein gelegt werden darf
    @Override
    public boolean kannGespieltWerden(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir prüfen ob das Zielschwein dem Spieler gehört der die Karte gerade ausspielt
        if (targetPlayer != spieler) {
            // Wenn es nicht das eigene Schwein ist darf die Karte nicht gespielt werden
            return false;
        }
        
        // Wir prüfen ob die eingegebene Nummer des Schweins überhaupt existiert
        if (targetSauIndex < 0 || targetSauIndex >= spieler.getSchweine().size()) {
            // Wenn die Nummer ungültig ist darf die Karte nicht gespielt werden
            return false;
        }
        
        // Wir holen uns das ausgewählte Schwein aus der Liste des Spielers
        Sau sau = spieler.getSau(targetSauIndex);
        // Die Karte darf nur gespielt werden wenn das Schwein aktuell noch sauber ist
        return sau.isClean();
    }

    // Diese Funktion macht das Schwein schmutzig wenn die Karte ausgespielt wird
    @Override
    public void ausfuehren(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir holen uns das ausgewählte eigene Schwein
        Sau sau = spieler.getSau(targetSauIndex);
        // Wir machen das Schwein schmutzig so dass es zu einer Drecksau wird
        sau.makeDirty();
        
        // Wir schreiben eine Nachricht in das Spielprotokoll dass das Schwein nun im Matsch ist
        game.logAction(spieler.getUsername() + " schickt ein Schwein in den Matsch: Drecksau!");
    }
}
