public class Card {
// Diese Klasse ist nur zuständig für die Karten-Attribute


    // Die verschiedenen Kartenarten erstellen
    // enum ist eine feste, unveränderliche Menge an vordefinierter Konstanten, in diesem Falle unsere Karten
    enum Special {
        Matschkarte, Regenkarte, Stallkarte, Blitzkarte,
        Blitzableiterkarte, Bauerschrubtkarte, Baueraergerkarte;

        private static final Special[] specials = Special.values();
        public static Special getSpecial(int i) {
            return Special.specials[i];
        }
    }

    private final Special special;

    // Setter
    public Card(final Special special) {
        this.special = special;
    }

    // Getter
    public Special getSpecial() {
        return this.special;
    }

}
