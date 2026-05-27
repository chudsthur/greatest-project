import Assets.Deck;
import Assets.Sau;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {

    // Game engine, bitte noch machen
    // Meine Eingaben sind nur rough drafts, nichts finales, kann komplett ersetzt werden

    private int currentPlayer; // Wer gerade dran ist ("keep track of")
    private String[] playerIds; // Spielernamen
    private int playerCount;
    private int maxPigs;

    private Deck deck; // einfach das Assets.Deck
    private ArrayList<ArrayList<Sau>> playerHand; // arraylist von arraylist weil alle spieler ein eigenes arraylist von karten in der "hand" haben
    private ArrayList<Sau stockpile; // zum Karten ablegen

    boolean gameDirection; // Spielrichtung

    public Game(String[] pids) {
        deck = new Deck();
        deck.shuffle();
        stockpile = new ArrayList<Sau>();

        playerCount = pids.length;
        maxPigs = 2;

        playerIds = pids;
        currentPlayer = 0;
        gameDirection = false;

        playerHand = new ArrayList<ArrayList<Sau>>();

        if (playerCount == 2) {
            maxPigs = 5;
        } else if (playerCount == 3) {
            maxPigs = 4;
        } else if (playerCount == 4) {
            maxPigs = 3;
        } else if (playerCount <= 1) {
            throw new IllegalArgumentException("Such dir Freunde");
        } else {
            throw new IllegalArgumentException("Zu viele Spieler, bitte jemand rauswerfen");
        }
    }
}
