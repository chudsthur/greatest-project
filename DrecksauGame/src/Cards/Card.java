// Das hier ist der Ordnername in dem diese Datei liegt
package Cards;

// Hier holen wir die Informationen über einen Spieler
import Assets.Player;
// Hier holen wir die Informationen über den Ablauf des Spiels
import Assets.Game;

// Das ist die Grundvorlage für alle Spielkarten im Spiel
public abstract class Card {

    // Hier wird der Name der Spielkarte gespeichert
    private final String name;
    
    // Hier wird der Text gespeichert der erklärt was die Karte macht
    private final String beschreibung;

    // Das ist der Bauplan um eine neue Karte mit Name und Beschreibung zu erstellen
    public Card(String name, String beschreibung) {
        // Wir speichern den übergebenen Namen in der Karte ab
        this.name = name;
        // Wir speichern die übergebene Beschreibung in der Karte ab
        this.beschreibung = beschreibung;
    }

    // Diese Funktion gibt uns den Namen der Karte zurück
    public String getName() {
        // Wir geben den gespeicherten Namen zurück
        return name;
    }

    // Diese Funktion gibt uns die Beschreibung der Karte zurück
    public String getBeschreibung() {
        // Wir geben den gespeicherten Beschreibungstext zurück
        return beschreibung;
    }

    // Diese Funktion prüft ob die Karte unter den aktuellen Bedingungen gelegt werden darf
    public abstract boolean kannGespieltWerden(Game game, Player spieler, int targetSauIndex, Player targetPlayer);

    // Diese Funktion führt die Aktion der Spielkarte im Spiel aus
    public abstract void ausfuehren(Game game, Player spieler, int targetSauIndex, Player targetPlayer);

    // Diese Funktion wandelt die Karte in einen lesbaren Text um
    @Override
    public String toString() {
        // Wir geben den Namen der Karte gefolgt von der Beschreibung in Klammern zurück
        return name + " (" + beschreibung + ")";
    }
}
