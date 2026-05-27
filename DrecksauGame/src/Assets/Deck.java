package Assets;

import Cards.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {

    private final List<Card> drawPile;
    private final List<Card> discardPile;
    private final Random randomizer;

    // Karten durchmischler
    public void shuffle() {
        Collections.shuffle(drawPile, randomizer);
    }

    // Setzt randomizer
    public Deck(Random randomizer) {
        this.drawPile = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.randomizer = randomizer;
    }

    // Wenn Nachziehdeck leer ist, Ablagedeck durchmischeln und als neues Nachziehdeck benutzen
    private void discard2draw() {
        drawPile.addAll(discardPile);
        discardPile.clear();
        Collections.shuffle(drawPile, randomizer);
    }

    // Neue Karte ziehen
    public Card draw() {
        if(drawPile.isEmpty()) {
            discard2draw();
            throw new IllegalArgumentException("Deck ist leer, Ablagedeck wird neu durchmischelt...");
        }
        return drawPile.removeLast();
    }

    // Grösse des Nachziehstapels
    public int drawPileSize() {
        return drawPile.size();
    }
    // Grösse des Ablegestapels
    public int discardPileSize() {
        return discardPile.size();
    }

    // Wenn man Karte aufnimmt, nimmt man Karte vom drawpile weg
    public void add(Card card) {
        drawPile.remove(card);
    }
    // Wenn man Karte hinlegt, wird diese dem discardpile hinzugefügt
    public void discard(Card card) {
        discardPile.add(card);
    }

    // Kreeirt das Kartendeck
    public static Deck createStandardDeck(Random randomizer) {
        Deck deck = new Deck(randomizer);

        // Fügt alle Karten zum Deck hinzu
        for (int i = 0; i <= 21; i++) deck.add(new Matsch());
        for (int i = 0; i <= 4; i++) deck.add(new Regen());
        for (int i = 0; i <= 9; i++) deck.add(new Stall());
        for (int i = 0; i <= 4; i++) deck.add(new Blitz());
        for (int i = 0; i <= 4; i++) deck.add(new Blitzableiter());
        for (int i = 0; i <= 8; i++) deck.add(new Bauerschrub());
        for (int i = 0; i <= 4; i++) deck.add(new Baueraerger());

        // Nach hinzufügen der Karten, alle Karten randomizer durchmischen
        deck.shuffle();
        return deck;
    }
}

