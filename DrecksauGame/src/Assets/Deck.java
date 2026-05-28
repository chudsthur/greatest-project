// Das hier ist der Ordnername in dem diese Datei liegt
package Assets;

// Hier holen wir alle Kartenklassen aus dem Kartenordner
import Cards.*;

// Hier laden wir eine Liste zum Speichern von Karten herunter
import java.util.ArrayList;
// Hier laden wir fertige Funktionen zum Mischen und Sortieren herunter
import java.util.Collections;
// Das ist die Vorlage für eine Liste in Java
import java.util.List;
// Hier holen wir einen Zufallsgenerator zum Mischen der Karten
import java.util.Random;

// Das ist die Klasse die das Kartendeck mit Nachzieh- und Ablagestapel darstellt
public class Deck {

    // Hier speichern wir die Karten die noch im Nachziehstapel liegen
    private final List<Card> drawPile;
    
    // Hier speichern wir die Karten die bereits auf dem Ablagestapel liegen
    private final List<Card> discardPile;
    
    // Hier speichern wir den Zufallsgenerator zum Mischen der Karten
    private final Random randomizer;

    // Das ist der Bauplan um ein neues Deck mit einem automatischen Zufallsgenerator zu erstellen
    public Deck() {
        // Wir rufen den detaillierten Bauplan mit einem neuen Zufallsgenerator auf
        this(new Random());
    }

    // Das ist der detaillierte Bauplan mit einem vorgegebenen Zufallsgenerator
    public Deck(Random randomizer) {
        // Wir erstellen eine neue leere Liste für den Nachziehstapel
        this.drawPile = new ArrayList<>();
        // Wir erstellen eine neue leere Liste für den Ablagestapel
        this.discardPile = new ArrayList<>();
        // Wir speichern den Zufallsgenerator ab
        this.randomizer = randomizer;
    }

    // Diese Funktion mischt den gesamten Nachziehstapel einmal kräftig durch
    public void shuffle() {
        // Wir nutzen das Werkzeug zum Mischen und übergeben unseren Zufallsgenerator
        Collections.shuffle(drawPile, randomizer);
    }

    // Diese Funktion holt alle Karten vom Ablagestapel zurück wenn der Nachziehstapel leer ist
    private void discard2draw() {
        // Wir fügen alle Karten vom Ablagestapel wieder zum Nachziehstapel hinzu
        drawPile.addAll(discardPile);
        // Wir leeren den Ablagestapel komplett
        discardPile.clear();
        // Wir mischen den Nachziehstapel neu durch
        shuffle();
    }

    // Diese Funktion zieht eine Karte oben vom Nachziehstapel ab
    public Card draw() {
        // Wenn keine Karten mehr zum Ziehen da sind
        if (drawPile.isEmpty()) {
            // Wir legen alle Karten vom Ablagestapel zurück und mischen neu
            discard2draw();
        }
        
        // Wenn selbst danach keine einzige Karte im Spiel ist
        if (drawPile.isEmpty()) {
            // Wir brechen mit einer Fehlermeldung ab da keine Karten mehr existieren
            throw new IllegalStateException("Keine Karten mehr im Spiel vorhanden!");
        }
        
        // Wir entfernen die oberste Karte vom Nachziehstapel und geben sie zurück
        return drawPile.remove(drawPile.size() - 1);
    }

    // Diese Funktion sagt uns wie viele Karten noch auf dem Nachziehstapel liegen
    public int drawPileSize() {
        // Wir geben die Größe der Nachziehstapel-Liste zurück
        return drawPile.size();
    }

    // Diese Funktion sagt uns wie viele Karten bereits auf dem Ablagestapel liegen
    public int discardPileSize() {
        // Wir geben die Größe der Ablagestapel-Liste zurück
        return discardPile.size();
    }

    // Diese Funktion fügt eine neue Karte zum Nachziehstapel hinzu
    public void add(Card card) {
        // Wir legen die Karte in die Liste des Nachziehstapels
        drawPile.add(card);
    }

    // Diese Funktion legt eine bereits gespielte Karte auf den Ablagestapel
    public void discard(Card card) {
        // Wir legen die Karte in die Liste des Ablagestapels
        discardPile.add(card);
    }

    // Diese Funktion erstellt ein spielbereites Standarddeck mit allen offiziellen Spielkarten
    public static Deck createStandardDeck(Random randomizer) {
        // Wir erstellen ein neues leeres Deck mit dem Zufallsgenerator
        Deck deck = new Deck(randomizer);

        // Wir fügen genau einundzwanzig Matschkarten hinzu
        for (int i = 0; i < 21; i++) {
            // Wir erstellen eine neue Matschkarte und fügen sie hinzu
            deck.add(new Matsch());
        }
        
        // Wir fügen genau vier Regenkarten hinzu
        for (int i = 0; i < 4; i++) {
            // Wir erstellen eine neue Regenkarte und fügen sie hinzu
            deck.add(new Regen());
        }
        
        // Wir fügen genau neun Stallkarten hinzu
        for (int i = 0; i < 9; i++) {
            // Wir erstellen eine neue Stallkarte und fügen sie hinzu
            deck.add(new Stall());
        }
        
        // Wir fügen genau vier Blitzkarten hinzu
        for (int i = 0; i < 4; i++) {
            // Wir erstellen eine neue Blitzkarte und fügen sie hinzu
            deck.add(new Blitz());
        }
        
        // Wir fügen genau vier Blitzableiterkarten hinzu
        for (int i = 0; i < 4; i++) {
            // Wir erstellen eine neue Blitzableiterkarte und fügen sie hinzu
            deck.add(new Blitzableiter());
        }
        
        // Wir fügen genau acht Karten hinzu mit denen der Bauer ein Schwein sauber wäscht
        for (int i = 0; i < 8; i++) {
            // Wir erstellen eine neue Karte für das Waschen durch den Bauern und fügen sie hinzu
            deck.add(new Bauerschrub());
        }
        
        // Wir fügen genau vier Karten hinzu mit denen man die Stalltür verriegeln kann
        for (int i = 0; i < 4; i++) {
            // Wir erstellen eine neue Verriegelungskarte und fügen sie hinzu
            deck.add(new Baueraerger());
        }

        // Wir fügen genau vier Schlammvulkankarten als kreative Zusatzkarten hinzu
        for (int i = 0; i < 4; i++) {
            // Wir erstellen eine neue Schlammvulkankarte und fügen sie hinzu
            deck.add(new Schlammvulkan());
        }

        // Wir mischen das gefüllte Deck einmal kräftig durch
        deck.shuffle();
        // Wir geben das spielbereite Deck zurück
        return deck;
    }
}
