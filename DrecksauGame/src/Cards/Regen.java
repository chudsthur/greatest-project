// Das hier ist der Ordnername in dem diese Datei liegt
package Cards;

// Hier holen wir die Informationen über einen Spieler
import Assets.Player;
// Hier holen wir die Informationen über ein Schwein
import Assets.Sau;
// Hier holen wir die Informationen über den Ablauf des Spiels
import Assets.Game;

// Das ist die Regenkarte die ungeschützte Schweine wieder sauber wäscht
public class Regen extends Card {

    // Das ist der Bauplan für die Regenkarte
    public Regen() {
        // Wir erstellen die Karte mit dem Namen Regen und einer passenden Beschreibung
        super("Regen", "Wäscht alle Schweine im Freien wieder sauber (eigene und gegnerische).");
    }

    // Diese Karte ist ein allgemeines Ereignis und darf immer gespielt werden
    @Override
    public boolean kannGespieltWerden(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir geben immer ja zurück da es keine Einschränkungen zum Ausspielen gibt
        return true;
    }

    // Diese Funktion lässt es im Spiel regnen und wäscht alle ungeschützten Schweine sauber
    @Override
    public void ausfuehren(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir schreiben eine Nachricht in das Protokoll dass der Regen beginnt
        game.logAction("Es fängt an zu regnen! Der Schlamm wird weggewaschen...");
        
        // Wir gehen alle Spieler nacheinander durch um deren Schweine zu prüfen
        for (Player p : game.getPlayers()) {
            // Wir legen einen Zähler für die sauber gewaschenen Schweine dieses Spielers an
            int gesaeuberteSchweine = 0;
            
            // Wir gehen jedes einzelne Schwein des Spielers durch
            for (Sau sau : p.getSchweine()) {
                // Wenn das Schwein schmutzig ist und keinen schützenden Stall hat
                if (sau.isDirty() && !sau.hasStall()) {
                    // Das Schwein wird durch den Regen wieder komplett sauber gewaschen
                    sau.makeClean();
                    // Wir erhöhen unseren Zähler für gesäuberte Schweine um eins
                    gesaeuberteSchweine++;
                }
            }
            
            // Wenn bei diesem Spieler mindestens ein Schwein sauber gewaschen wurde
            if (gesaeuberteSchweine > 0) {
                // Wir protokollieren wie viele Schweine des Spielers sauber gewaschen wurden
                game.logAction("  -> " + p.getUsername() + ": " + gesaeuberteSchweine + " Schwein(e) wurden sauber gewaschen.");
            }
        }
    }
}
