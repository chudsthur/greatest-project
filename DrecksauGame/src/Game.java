import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    private int currentPlayer; // Wer gerade dran ist ("keep track of")
    private String[] playerIds; // Spielernamen
    private int playerCount;
    private int maxCards;

    private Deck deck; // einfach das Deck
    private ArrayList<ArrayList<Card>> playerHand; // arraylist von arraylist weil alle spieler ein eigenes arraylist von karten in der "hand" haben
    private ArrayList<Card> stockpile; // zum Karten ablegen

    boolean gameDirection; // Spielrichtung

    public Game(String[] pids) {
        deck = new Deck();
        deck.shuffle();
        stockpile = new ArrayList<Card>();

        playerCount = pids.length;
        maxCards = 2;

        playerIds = pids;
        currentPlayer = 0;
        gameDirection = false;

        playerHand = new ArrayList<ArrayList<Card>>();


        for (int i = 0; i < pids.length; i++) {
            ArrayList<Card> hand = new ArrayList<Card>(Arrays.asList(deck.drawCard(maxCards)));
            playerHand.add(hand);
        }

        if (playerCount == 2) {
            maxCards = 5;
        } else if (playerCount == 3) {
            maxCards = 4;
        } else if (playerCount == 4) {
            maxCards = 3;
        } else if (playerCount <= 1) {
            throw new IllegalArgumentException("Such dir Freunde");
        } else {
            throw new IllegalArgumentException("Zu viele Spieler, bitte jemand rauswerfen");
        }
    }
}
