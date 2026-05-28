// Das hier ist der Ordnername in dem diese Datei liegt
package Assets;

// Hier holen wir die Informationen über eine Spielkarte
import Cards.Card;

// Hier laden wir eine Liste um mehrere Sachen nacheinander abzuspeichern
import java.util.ArrayList;
// Hier laden wir fertige Werkzeuge für Listen herunter
import java.util.Collections;
// Das ist die Vorlage für eine Liste in Java
import java.util.List;

// Das ist die Klasse die einen Spieler im Spiel darstellt
public class Player {

    // Hier speichern wir den Namen des Spielers ab
    private final String username;
    
    // Hier speichern wir alle Schweine ab die vor dem Spieler auf dem Tisch liegen
    private final List<Sau> schweine;
    
    // Hier speichern wir alle Handkarten ab die der Spieler verdeckt auf der Hand hält
    private final List<Card> personalDeck;

    // Das ist der Bauplan um einen neuen Spieler mit Namen und einer Startanzahl an Schweinen zu erstellen
    public Player(String username, int sauAmount) {
        // Wir speichern den Namen des Spielers ab
        this.username = username;
        // Wir erstellen eine neue leere Liste für die Schweine
        this.schweine = new ArrayList<>();
        
        // Wir erstellen nacheinander so viele Schweine wie übergeben wurden
        for (int i = 0; i < sauAmount; i++) {
            // Jedes neu erstellte Schwein wird in die Liste des Spielers gelegt
            this.schweine.add(new Sau());
        }
        
        // Wir erstellen eine neue leere Liste für die Handkarten
        this.personalDeck = new ArrayList<>();
    }

    // Diese Funktion gibt uns den Namen des Spielers zurück
    public String getUsername() {
        // Wir geben den gespeicherten Namen zurück
        return username;
    }

    // Diese Funktion gibt uns die Liste mit allen Schweinen des Spielers zurück
    public List<Sau> getSchweine() {
        // Wir geben die Liste so zurück dass man sie von außen nur lesen aber nicht direkt manipulieren kann
        return Collections.unmodifiableList(schweine);
    }

    // Diese Funktion gibt uns ein bestimmtes Schwein anhand seiner Nummer in der Liste zurück
    public Sau getSau(int index) {
        // Wir holen das Schwein an der gewünschten Position aus der Liste
        return schweine.get(index);
    }

    // Diese Funktion gibt uns die Liste mit allen Handkarten des Spielers zurück
    public List<Card> getPersonalDeck() {
        // Wir geben die Handkarten-Liste schreibgeschützt zurück
        return Collections.unmodifiableList(personalDeck);
    }

    // Das ist eine einfache Hilfsfunktion um die Handkarten abzurufen
    public List<Card> getHand() {
        // Wir rufen die obere Funktion auf und geben die Handkarten zurück
        return getPersonalDeck();
    }

    // Diese Funktion fügt eine neu gezogene Karte zu den Handkarten des Spielers hinzu
    public void addCard(Card card) {
        // Wir prüfen ob die Karte überhaupt existiert
        if (card != null) {
            // Wir legen die gezogene Karte in die Handkarten-Liste des Spielers
            personalDeck.add(card);
        }
    }

    // Diese Funktion nimmt eine Karte aus den Handkarten des Spielers weg
    public void removeCard(Card card) {
        // Wir entfernen die ausgewählte Karte aus der Liste
        personalDeck.remove(card);
    }

    // Diese Funktion prüft ob der Spieler gewonnen hat
    public boolean hasOnlyDrecksau() {
        // Wenn der Spieler überhaupt keine Schweine besitzt hat er auch nicht gewonnen
        if (schweine.isEmpty()) {
            // Wir geben nein zurück
            return false;
        }
        
        // Wir gehen nacheinander jedes einzelne Schwein des Spielers durch
        for (Sau sau : schweine) {
            // Wenn wir auch nur ein einziges Schwein finden das sauber ist
            if (sau.isClean()) {
                // Dann hat der Spieler noch nicht alle Schweine eingesaut und hat noch nicht gewonnen
                return false;
            }
        }
        // Wenn alle Schweine dreckig sind geben wir ja zurück da der Spieler gewonnen hat
        return true;
    }
}
