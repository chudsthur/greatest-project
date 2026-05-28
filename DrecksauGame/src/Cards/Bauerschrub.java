// Das hier ist der Ordnername in dem diese Datei liegt
package Cards;

// Hier holen wir die Informationen über einen Spieler
import Assets.Player;
// Hier holen wir die Informationen über ein Schwein
import Assets.Sau;
// Hier holen wir die Informationen über den Ablauf des Spiels
import Assets.Game;

// Das ist die Karte mit der man ein Schwein eines Gegenspielers sauber putzen lassen kann
public class Bauerschrub extends Card {

    // Das ist der Bauplan für die Bauern-Karte
    public Bauerschrub() {
        // Wir erstellen die Karte mit dem Namen Bauer schrubbt und einer passenden Beschreibung
        super("Bauer schrubbt", "Putzt eine gegnerische Drecksau sauber (außer sie ist in einem verriegelten Stall).");
    }

    // Diese Funktion prüft ob der Bauer das ausgewählte gegnerische Schwein waschen darf
    @Override
    public boolean kannGespieltWerden(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir prüfen ob das Ziel einem Mitspieler gehört
        if (targetPlayer == spieler || targetPlayer == null) {
            // Man darf den Bauern nicht zu den eigenen Schweinen schicken
            return false;
        }
        
        // Wir prüfen ob die Nummer des Schweins beim Mitspieler existiert
        if (targetSauIndex < 0 || targetSauIndex >= targetPlayer.getSchweine().size()) {
            // Wenn die Nummer ungültig ist brechen wir ab
            return false;
        }
        
        // Wir holen uns das ausgewählte gegnerische Schwein
        Sau sau = targetPlayer.getSau(targetSauIndex);
        
        // Wir prüfen ob das gegnerische Schwein schmutzig ist
        if (!sau.isDirty()) {
            // Ein bereits sauberes Schwein kann der Bauer nicht noch mal waschen
            return false;
        }
        
        // Der Bauer darf das Schwein nur waschen wenn der Stall nicht verriegelt ist
        return !sau.bauerSchutz();
    }

    // Diese Funktion schickt den Bauern los um das gegnerische Schwein sauber zu waschen
    @Override
    public void ausfuehren(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir holen uns das ausgewählte gegnerische Schwein
        Sau sau = targetPlayer.getSau(targetSauIndex);
        
        // Der Bauer wäscht das Schwein wieder komplett sauber
        sau.makeClean();
        
        // Wir schreiben eine Nachricht in das Protokoll dass das Schwein sauber geschrubbt wurde
        game.logAction(spieler.getUsername() + " schickt den Bauern zu " + targetPlayer.getUsername() + 
                       ". Der Bauer ruft: Ich putz dich! -> Schwein Nummer " + (targetSauIndex + 1) + " ist wieder blitzblank sauber.");
    }
}
