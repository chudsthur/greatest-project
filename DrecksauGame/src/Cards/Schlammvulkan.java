// Das hier ist der Ordnername in dem diese Datei liegt
package Cards;

// Hier holen wir die Informationen über einen Spieler
import Assets.Player;
// Hier holen wir die Informationen über ein Schwein
import Assets.Sau;
// Hier holen wir die Informationen über den Ablauf des Spiels
import Assets.Game;

// Das ist die Schlammvulkankarte die alle ungeschützten Schweine im Spiel einsaut
public class Schlammvulkan extends Card {

    // Das ist der Bauplan für die Vulkankarte
    public Schlammvulkan() {
        // Wir erstellen die Karte mit dem Namen Schlammvulkan und einer passenden Beschreibung
        super("Schlammvulkan", "Macht ALLE Schweine im Freien (ohne Stall) sofort schmutzig.");
    }

    // Der Vulkanausbruch ist ein globales Ereignis und darf immer ausgelöst werden
    @Override
    public boolean kannGespieltWerden(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir geben immer ja zurück da es keine Einschränkungen zum Ausspielen gibt
        return true;
    }

    // Diese Funktion lässt den Vulkan ausbrechen und alle ungeschützten Schweine im Matsch suhlen
    @Override
    public void ausfuehren(Game game, Player spieler, int targetSauIndex, Player targetPlayer) {
        // Wir schreiben eine Nachricht in das Protokoll dass der Vulkan ausbricht und Schlamm spuckt
        game.logAction("VULKANAUSBRUCH! Der Schlammvulkan spuckt tonnenweise feinsten Matsch!");
        
        // Wir gehen nacheinander alle Spieler im Spiel durch
        for (Player p : game.getPlayers()) {
            // Wir legen einen Zähler für die schmutzig gewordenen Schweine dieses Spielers an
            int eingesauteSchweine = 0;
            
            // Wir gehen jedes einzelne Schwein des Spielers durch
            for (Sau sau : p.getSchweine()) {
                // Wenn das Schwein sauber ist und keinen schützenden Stall besitzt
                if (sau.isClean() && !sau.hasStall()) {
                    // Das Schwein wird durch den Schlammregen komplett dreckig gemacht
                    sau.makeDirty();
                    // Wir erhöhen unseren Zähler für schmutzige Schweine um eins
                    eingesauteSchweine++;
                }
            }
            
            // Wenn bei diesem Spieler mindestens ein Schwein schmutzig geworden ist
            if (eingesauteSchweine > 0) {
                // Wir protokollieren wie viele Schweine des Spielers nun zu Drecksäuen geworden sind
                game.logAction("  -> " + p.getUsername() + ": " + eingesauteSchweine + " Schwein(e) wurden zu glücklichen Drecksäuen!");
            }
        }
    }
}
