import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Deck {

    // Das Spiel hat 54 Spezielle Karten die nicht die Saukarten sind
    private Card[] cards;
    private int cardsInDeck;

    public Deck() {
        cards = new Card[54];
    }

    // Methode um das Kartendeck zu initialisieren
    public void reset() {
        Card.Special[] specials = Card.Special.values();
        cardsInDeck = 0;

        // Wir gehen alle values im Array durch und fügen sie zu unserem Deck hinzu

        Card.Special[] values = new Card.Special[]{Card.Special.Matschkarte, Card.Special.Regenkarte, Card.Special.Stallkarte,
                Card.Special.Blitzkarte, Card.Special.Blitzableiterkarte, Card.Special.Bauerschrubtkarte, Card.Special.Baueraergerkarte};
        for(Card.Special value : values) {
            cards[cardsInDeck++] = new Card(value);
        }
    }

    // ersetzt deck mit arraylist von karten

    public void replaceDeckWith(ArrayList<Card> cards ) {
        this.cards = cards.toArray(new Card[cards.size()]);
        this.cardsInDeck = this.cards.length;
    }
    // Kontrolliert ob Deck leer ist
    public boolean isEmpty() {
        return cardsInDeck == 0;
    }

    // Deck shuffler
    public void shuffle() {
        int a = cards.length;
        Random random = new Random();

        for(int i = 0; i < cards.length; i++) {
            // random index of array past current index
            // argument is exclusive bound
            // swaps random element with present element

            int randomValue = i + random.nextInt(a - i);
            Card randomCard = cards[randomValue];
            cards[randomValue] = cards[i];
            cards[i] = randomCard;
        }
    }

    // Kartem ziehen
    public Card drawCard() throws IllegalArgumentException {
        if (isEmpty()) {
            throw new IllegalArgumentException("Deck ist leer"); // Kann nicht Karten ziehen wenn deck leer ist
        }
        return cards[--cardsInDeck];
    }

    // Grafische Darstellung Karten ziehen
    public ImageIcon drawCardImage() throws IllegalArgumentException {
        if (isEmpty()) {
            throw new IllegalArgumentException("Deck ist leer"); // Kann nicht Karten ziehen wenn deck leer ist
        }
        return new ImageIcon(cards[--cardsInDeck].toString() + ".png");
    }
}

