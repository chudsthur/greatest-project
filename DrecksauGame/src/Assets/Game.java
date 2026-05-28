// Das hier ist der Ordnername in dem diese Datei liegt
package Assets;

// Hier holen wir die Kartenklasse
import Cards.Card;
// Hier laden wir eine Liste herunter um mehrere Sachen nacheinander zu speichern
import java.util.ArrayList;
// Hier laden wir fertige Werkzeuge für Listen herunter
import java.util.Collections;
// Das ist die Vorlage für eine Liste in Java
import java.util.List;
// Hier holen wir einen Zufallsgenerator
import java.util.Random;

// Das ist die Spiel-Engine die das gesamte Drecksau-Spiel steuert
public class Game {

    // Hier speichern wir die Liste aller Spieler im aktuellen Spiel ab
    private final List<Player> players;
    
    // Hier speichern wir das gesamte Kartendeck inklusive Nachzieh- und Ablagestapel ab
    private final Deck deck;
    
    // Hier speichern wir die Nummer des Spielers ab der gerade an der Reihe ist
    private int currentPlayerIndex;
    
    // Hier speichern wir das gesamte Protokoll aller Spielereignisse für die Anzeige ab
    private final List<String> actionLog;
    
    // Hier speichern wir ab ob das Spiel bereits beendet ist weil jemand gewonnen hat
    private boolean gameOver;
    
    // Hier speichern wir den Gewinner ab wenn das Spiel vorbei ist
    private Player winner;

    // Das ist der Bauplan um ein neues Spiel mit einer Liste von Namen zu starten
    public Game(String[] playerNames) {
        // Wir erstellen eine neue leere Liste für die Spieler
        this.players = new ArrayList<>();
        // Wir erstellen eine neue leere Liste für das Spielprotokoll
        this.actionLog = new ArrayList<>();
        // Zu Beginn läuft das Spiel noch und ist nicht vorbei
        this.gameOver = false;
        // Zu Beginn gibt es noch keinen Gewinner
        this.winner = null;
        
        // Wir prüfen wie viele Spieler am Spiel teilnehmen wollen
        int playerCount = playerNames.length;
        // Wenn weniger als zwei Spieler teilnehmen wollen
        if (playerCount < 2) {
            // Wir brechen mit einer Fehlermeldung ab da man mindestens zwei Spieler braucht
            throw new IllegalArgumentException("Du brauchst mindestens 2 Spieler, um Drecksau zu spielen!");
        }
        // Wenn mehr als vier Spieler teilnehmen wollen
        if (playerCount > 4) {
            // Wir brechen mit einer Fehlermeldung ab da maximal vier Spieler mitspielen dürfen
            throw new IllegalArgumentException("Es können maximal 4 Spieler teilnehmen!");
        }

        // Wir bestimmen die Anzahl der Schweine pro Spieler laut den offiziellen Regeln
        int sauAmount = 3;
        // Wenn genau zwei Spieler teilnehmen
        if (playerCount == 2) {
            // Bei zwei Spielern erhält jeder Spieler genau fünf Schweine
            sauAmount = 5;
        // Wenn genau drei Spieler teilnehmen
        } else if (playerCount == 3) {
            // Bei drei Spielern erhält jeder Spieler genau vier Schweine
            sauAmount = 4;
        }

        // Wir gehen nacheinander alle Spielernamen durch
        for (String name : playerNames) {
            // Wir erstellen einen neuen Spieler mit dem Namen und der berechneten Schweine-Anzahl
            players.add(new Player(name, sauAmount));
        }

        // Wir erstellen ein neues Standard-Kartendeck mit dem Zufallsgenerator und mischen es
        this.deck = Deck.createStandardDeck(new Random());

        // Jeder Spieler muss zu Beginn verdeckt genau drei Karten auf die Hand ziehen
        for (Player spieler : players) {
            // Wir lassen den Spieler nacheinander drei Karten ziehen
            for (int i = 0; i < 3; i++) {
                // Der Spieler zieht eine Karte vom Nachziehstapel und nimmt sie auf die Hand
                spieler.addCard(deck.draw());
            }
        }

        // Der erste Spieler in der Liste darf das Spiel beginnen
        this.currentPlayerIndex = 0;
        
        // Wir schreiben eine Nachricht in das Protokoll dass das Spiel gestartet wurde
        logAction("Spiel gestartet! Jedes Schwein startet sauber im Freien.");
        // Wir schreiben in das Protokoll wer als nächstes an der Reihe ist
        logAction("Spieler an der Reihe: " + getActivePlayer().getUsername());
    }

    // Diese Funktion gibt uns die Liste aller Spieler zurück
    public List<Player> getPlayers() {
        // Wir geben die Spielerliste schreibgeschützt zurück
        return Collections.unmodifiableList(players);
    }

    // Diese Funktion gibt das aktuelle Kartendeck zurück
    public Deck getDeck() {
        // Wir geben das Deck-Objekt zurück
        return deck;
    }

    // Diese Funktion gibt den Spieler zurück der aktuell an der Reihe ist
    public Player getActivePlayer() {
        // Wir holen den Spieler an der aktuellen Position aus der Liste
        return players.get(currentPlayerIndex);
    }

    // Diese Funktion gibt die Nummer des aktuellen Spielers zurück
    public int getCurrentPlayerIndex() {
        // Wir geben den aktuellen Positions-Wert zurück
        return currentPlayerIndex;
    }

    // Diese Funktion gibt das gesamte Protokoll aller Ereignisse im Spiel zurück
    public List<String> getActionLog() {
        // Wir geben das Protokoll schreibgeschützt zurück
        return Collections.unmodifiableList(actionLog);
    }

    // Diese Funktion schreibt ein Ereignis in das Protokoll und gibt es auf der Konsole aus
    public void logAction(String message) {
        // Wir fügen die Nachricht zur Protokoll-Liste hinzu
        actionLog.add(message);
        // Wir geben die Nachricht auf der Konsole aus damit Entwickler sie sehen können
        System.out.println(message);
    }

    // Diese Funktion sagt uns ob das Spiel vorbei ist
    public boolean isGameOver() {
        // Wir geben den Zustand ob das Spiel vorbei ist zurück
        return gameOver;
    }

    // Diese Funktion gibt den Gewinner des Spiels zurück
    public Player getWinner() {
        // Wir geben das Gewinner-Objekt zurück
        return winner;
    }

    // Diese Funktion prüft ob eine bestimmte Karte für den aktiven Spieler überhaupt irgendwo spielbar ist
    public boolean istKarteIrgendwoSpielbar(Player spieler, Card c) {
        // Wir gehen alle eigenen Schweine des Spielers nacheinander durch
        for (int sIndex = 0; sIndex < spieler.getSchweine().size(); sIndex++) {
            // Wir prüfen ob die Karte auf dieses eigene Schwein gespielt werden darf
            if (c.kannGespieltWerden(this, spieler, sIndex, spieler)) {
                // Wenn es erlaubt ist geben wir ja zurück
                return true;
            }
        }

        // Wir gehen nacheinander alle gegnerischen Spieler durch
        for (Player gegner : players) {
            // Wir dürfen die Aktion nur prüfen wenn der ausgewählte Spieler ein Gegner ist und nicht man selbst
            if (gegner != spieler) {
                // Wir gehen alle Schweine dieses Gegners durch
                for (int sIndex = 0; sIndex < gegner.getSchweine().size(); sIndex++) {
                    // Wir prüfen ob die Karte auf das Schwein des Gegners gespielt werden darf
                    if (c.kannGespieltWerden(this, spieler, sIndex, gegner)) {
                        // Wenn es erlaubt ist geben wir ja zurück
                        return true;
                    }
                }
            }
        }

        // Wenn es eine globale Karte ist prüfen wir ob sie einfach auf den Ablagestapel gespielt werden darf
        return c.kannGespieltWerden(this, spieler, -1, null);
    }

    // Diese Funktion prüft ob der aktive Spieler aktuell überhaupt irgendeinen gültigen Spielzug machen kann
    public boolean hatSpielerGueltigeZuege(Player spieler) {
        // Wir gehen alle Karten auf der Hand des Spielers durch
        for (Card c : spieler.getHand()) {
            // Wir prüfen ob diese eine Karte auf irgendein Ziel im Spiel gelegt werden darf
            if (istKarteIrgendwoSpielbar(spieler, c)) {
                // Wenn wir eine spielbare Karte finden geben wir ja zurück
                return true;
            }
        }
        // Wenn keine einzige Karte gespielt werden kann geben wir nein zurück
        return false;
    }

    // Diese Sonderaktion wirft alle Handkarten ab und zieht drei neue Karten wenn der Spieler nicht legen kann
    public void handAbwerfenUndNeuZiehen() {
        // Wenn das Spiel bereits vorbei ist brechen wir ab
        if (gameOver) return;

        // Wir holen uns den aktiven Spieler
        Player active = getActivePlayer();
        
        // Wir prüfen zur Sicherheit noch einmal ob der Spieler wirklich keine gültigen Züge hat
        if (hatSpielerGueltigeZuege(active)) {
            // Wenn er legen kann darf er seine Hand nicht abwerfen und wir brechen ab
            logAction("HINWEIS: " + active.getUsername() + " hat spielbare Karten und darf seine Hand nicht abwerfen!");
            // Wir beenden die Funktion vorzeitig
            return;
        }

        // Wir protokollieren dass der Spieler seine Handkarten mangels Spielzügen abwirft
        logAction(active.getUsername() + " zeigt seine Hand vor (keine Zuege moeglich) und wirft alle Karten ab!");
        
        // Wir erstellen eine Kopie der aktuellen Handkarten um sie sicher abzuwerfen
        List<Card> hand = new ArrayList<>(active.getHand());
        // Wir gehen jede Karte auf der Hand durch
        for (Card c : hand) {
            // Wir nehmen dem Spieler die Karte aus der Hand weg
            active.removeCard(c);
            // Wir legen die Karte auf den Ablagestapel des Decks
            deck.discard(c);
        }

        // Der Spieler zieht nun nacheinander drei neue Karten vom Nachziehstapel nach
        for (int i = 0; i < 3; i++) {
            // Die gezogene Karte wird in die Hand des Spielers gelegt
            active.addCard(deck.draw());
        }

        // Wir protokollieren dass der Spieler neue Karten gezogen hat
        logAction(active.getUsername() + " zieht 3 neue Handkarten nach.");
        
        // Wir übergeben den Spielzug an den nächsten Spieler
        naechsterSpieler();
    }

    // Diese Funktion wirft eine ausgewählte Handkarte freiwillig ab um den Zug auszusetzen
    public void karteFreiwilligAbwerfen(Card card) {
        // Wenn das Spiel bereits vorbei ist brechen wir ab
        if (gameOver) return;

        // Wir holen uns den aktiven Spieler
        Player active = getActivePlayer();
        
        // Wir nehmen die Karte aus der Hand des Spielers
        active.removeCard(card);
        // Wir legen die Karte auf den Ablagestapel
        deck.discard(card);
        
        // Wir schreiben in das Protokoll welche Karte ungenutzt abgeworfen wurde
        logAction(active.getUsername() + " wirft eine Karte ungenutzt ab: " + card.getName());
        
        // Der Spieler zieht sofort eine neue Karte vom Nachziehstapel nach
        active.addCard(deck.draw());
        
        // Wir übergeben den Spielzug an den nächsten Spieler
        naechsterSpieler();
    }

    // Diese Funktion führt einen kompletten Spielzug aus
    public void spieleZug(Card card, int targetSauIndex, Player targetPlayer) {
        // Wenn das Spiel bereits vorbei ist brechen wir ab
        if (gameOver) return;

        // Wir holen uns den aktiven Spieler
        Player active = getActivePlayer();
        
        // Wir prüfen ob der Spieler diese Karte überhaupt auf der Hand hat
        if (!active.getHand().contains(card)) {
            // Wenn nicht brechen wir mit einer Fehlermeldung ab
            throw new IllegalArgumentException("Diese Karte befindet sich nicht auf deiner Hand!");
        }

        // Wir prüfen ob diese Karte auf das gewählte Ziel gespielt werden darf
        if (!card.kannGespieltWerden(this, active, targetSauIndex, targetPlayer)) {
            // Wenn der Zug nicht erlaubt ist brechen wir mit einer Fehlermeldung ab
            throw new IllegalArgumentException("Dieser Zug ist nach den Regeln nicht erlaubt!");
        }

        // Wir führen den speziellen Effekt der Spielkarte aus
        card.ausfuehren(this, active, targetSauIndex, targetPlayer);

        // Wir nehmen die Karte aus der Hand des Spielers weg
        active.removeCard(card);
        // Wir legen die gespielte Karte auf den Ablagestapel
        deck.discard(card);

        // Der Spieler zieht sofort eine neue Karte vom Nachziehstapel nach
        active.addCard(deck.draw());

        // Wir prüfen ob der Spieler nun gewonnen hat (alle seine Schweine sind Drecksäue)
        if (active.hasOnlyDrecksau()) {
            // Wir setzen den Zustand auf wahr da das Spiel beendet ist
            gameOver = true;
            // Wir tragen den aktuellen Spieler als Gewinner ein
            winner = active;
            // Wir protokollieren den Sieg des Spielers
            logAction("Sieg! " + active.getUsername() + " hat als Erstes alle Schweine in Drecksaeue verwandelt!");
            // Wir beenden die Funktion vorzeitig
            return;
        }

        // Wir übergeben den Spielzug an den nächsten Spieler
        naechsterSpieler();
    }

    // Diese Funktion wechselt zum nächsten Spieler im Uhrzeigersinn
    private void naechsterSpieler() {
        // Wir erhöhen die Positions-Nummer und fangen bei Erreichen der Spieleranzahl wieder bei Null an
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        // Wir protokollieren wer nun am Zug ist
        logAction("Spieler an der Reihe: " + getActivePlayer().getUsername());
    }
}
