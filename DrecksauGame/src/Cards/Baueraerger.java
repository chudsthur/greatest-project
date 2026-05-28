// Das hier ist der Ordnername in dem diese Datei liegt
package Cards;

// Hier holen wir die Informationen über einen Spieler
import Assets.Player;
// Hier holen wir die Informationen über ein Schwein
import Assets.Sau;
// Hier holen wir die Informationen über den Ablauf des Spiels
import Assets.Game;

// Das ist die Bauer-ärgere-dich-Karte mit der man die Stalltür verriegeln kann
public class Baueraerger extends Card {

    // Das ist der Bauplan für die Verriegelungskarte
    public Baueraerger() {
        // Wir erstellen die Karte mit dem Namen Bauer ärgere dich und einer passenden Beschreibung
        super("Bauer ärgere dich", "Verriegelt die Stalltür einer Drecksau gegen den Bauern.");
    }

    // Diese Funktion prüft ob die Stalltür verriegelt werden darf
    @Override
    public boolean kannGespieltWerden(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir prüfen ob das Zielschwein dem Spieler gehört der am Zug ist
        if (targetPlayer != spieler) {
            // Die Stalltür darf nur bei eigenen Schweinen verriegelt werden
            return false;
        }
        
        // Wir prüfen ob die Nummer des Schweins in der eigenen Liste existiert
        if (targetSauIndex < 0 || targetSauIndex >= spieler.getSchweine().size()) {
            // Wenn die Nummer ungültig ist brechen wir ab
            return false;
        }
        
        // Wir holen uns das ausgewählte eigene Schwein
        Sau sau = spieler.getSau(targetSauIndex);
        
        // Wir prüfen ob das Schwein überhaupt einen Stall hat der verriegelt werden kann
        if (!sau.hasStall()) {
            // Ohne Stall kann man auch keine Stalltür verriegeln
            return false;
        }
        
        // Wir prüfen ob in dem Stall ein schmutziges Schwein steht
        if (!sau.isDirty()) {
            // Die Stalltür darf laut Regelwerk nur verriegelt werden wenn das Schwein bereits eine Drecksau ist
            return false;
        }
        
        // Wir dürfen die Tür nur verriegeln wenn sie nicht bereits verriegelt ist
        return !sau.bauerSchutz();
    }

    // Diese Funktion verriegelt die Stalltür des Schweins so dass der Bauer nicht rein kann
    @Override
    public void ausfuehren(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir holen uns das ausgewählte eigene Schwein
        Sau sau = spieler.getSau(targetSauIndex);
        
        // Wir riegeln die Stalltür ab
        sau.getStall().addBauerAerger();
        
        // Wir schreiben eine Nachricht in das Protokoll dass die Tür verriegelt wurde und der Bauer sich ärgert
        game.logAction(spieler.getUsername() + " verriegelt die Stalltür von Schwein Nummer " + 
                       (targetSauIndex + 1) + ". Der Bauer ärgert sich grün und blau!");
    }
}
