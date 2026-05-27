package Assets;

import Cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Player {

    // Erstellen Spieler username
    // Erstellen Schweine Array für personal-deck pro player
    // Erstellen personal-deck für jeden Spieler
    private final String username;
    private final List<Sau> schweine;
    private final List<Card> personalDeck;

    // Initialisierung des Spielers (username, schweine, karten personal-deck)
    public Player(String username, int sauAmount) {
        this.username = username;
        this.schweine = new ArrayList<>();
        for (int i = 0; i < sauAmount; i++) {
            this.schweine.add(new Sau());
        }
        this.personalDeck = new ArrayList<>();
    }

    // getter username
    public String getUsername() {
        return username;
    }

    // getter schweine
    public List<Sau> getSchweine() {
        return Collections.unmodifiableList(schweine);
    }

    // getter sau
    public Sau getSau(int index) {
        return schweine.get(index);
    }

    // getter personal-deck
    public List<Card> getPersonalDeck() {
        return Collections.unmodifiableList(personalDeck);
    }

    // Karte zu personal-deck hinzufügen
    public void addCard(Card card) {
        personalDeck.add(card);
    }

    // Karte von personal-deck entfernen
    public void removeCard(Card card) {
        personalDeck.remove(card);
    }

    // Checkt ob ein Spieler nur noch Drecksäue hat (win condition)
    public boolean hasOnlyDrecksau() {
        for (Sau sau : schweine) {
            if (!sau.isDirty()) return false;
        }
        return true;
    }

    // Kreeirung der Spieler
    public static void createPlayers(ArrayList<Player> players, Scanner sc, int count, int sauAmount) {
        sc.nextLine();

        for (int i = 0; i < count; i++) {
            System.out.println("Username für Player " + (i + 1) + " angeben");
            players.add(new Player(sc.nextLine(), sauAmount));
        }
    }
}
