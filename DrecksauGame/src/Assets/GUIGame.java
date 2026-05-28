// Das hier ist der Ordnername in dem diese Datei liegt
package Assets;

// Hier holen wir die Informationen über eine Spielkarte
import Cards.Card;

// Hier laden wir fertige Fenster-Werkzeuge herunter
import javax.swing.*;
// Hier laden wir Werkzeuge für Abstände im Fenster herunter
import javax.swing.border.EmptyBorder;
// Hier laden wir Werkzeuge für Farben und Formen herunter
import java.awt.*;
// Hier laden wir Werkzeuge für Mausklicks herunter
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
// Hier laden wir Werkzeuge für zweidimensionale Formen herunter
import java.awt.geom.*;
// Hier laden wir eine Liste herunter um Sachen nacheinander abzuspeichern
import java.util.ArrayList;
// Das ist die Vorlage für eine Liste in Java
import java.util.List;

// Das ist die Hauptklasse für das Fenster und die grafische Oberfläche des Spiels
public class GUIGame extends JFrame {

    // Hier speichern wir das laufende Spiel ab
    private Game game;
    
    // Hier speichern wir den Hauptcontainer der die verschiedenen Ansichten hält
    private JPanel mainContainer;
    // Hier speichern wir das Werkzeug um zwischen den Ansichten hin und her zu wechseln
    private CardLayout cardLayout;
    
    // Hier speichern wir die Ansicht für die Spielereingabe vor dem Start
    private JPanel setupPanel;
    // Hier speichern wir die Ansicht für das eigentliche Spielfeld
    private JPanel gamePanel;
    // Hier speichern wir die Ansicht für den Sichtschutz beim Spielerwechsel
    private JPanel passPanel;
    // Hier speichern wir die Ansicht für den Siegesbildschirm
    private JPanel victoryPanel;

    // Hier speichern wir welche Handkarte der Spieler gerade angeklickt hat
    private Card selectedHandCard = null;
    // Hier speichern wir die Nummer der angeklickten Handkarte
    private int selectedHandCardIndex = -1;

    // Hier speichern wir das Spielfeld mit den Wiesen und Schweinen
    private JPanel pasturePanel;
    // Hier speichern wir die hölzerne Steuerungsleiste auf der rechten Seite
    private JPanel controlPanel;
    // Hier speichern wir die Leiste für die Handkarten ganz unten
    private JPanel handCardsPanel;
    // Hier speichern wir die Liste mit den Spielnachrichten für die Anzeige
    private DefaultListModel<String> logListModel;
    // Das ist das eigentliche Anzeigefeld für die Spielnachrichten
    private JList<String> logList;
    // Das ist der Knopf mit dem man seine Hand abwirft wenn man nicht legen kann
    private JButton discardEntireHandButton;
    // Das ist der Knopf um globale Karten auszuspielen oder abzuwerfen
    private JButton playGlobalButton;
    // Das ist das Textfeld das anzeigt welcher Spieler gerade am Zug ist
    private JLabel activePlayerLabel;
    // Das ist das Textfeld das anzeigt wie viele Karten noch im Deck liegen
    private JLabel deckInfoLabel;

    // Hier definieren wir ein frisches Wiesengrün
    private static final Color COLOR_GRASS = new Color(46, 125, 50);
    // Hier definieren wir ein warmes Schlammbraun
    private static final Color COLOR_MUD = new Color(121, 85, 72);
    // Hier definieren wir ein helles Holzbraun
    private static final Color COLOR_WOOD = new Color(215, 169, 114);
    // Hier definieren wir ein dunkles Holzbraun
    private static final Color COLOR_DARK_WOOD = new Color(93, 64, 55);
    // Hier definieren wir ein gemütliches Stallrot
    private static final Color COLOR_STABLE_RED = new Color(211, 47, 47);
    // Hier definieren wir ein niedliches Schweinerosa
    private static final Color COLOR_PIG_PINK = new Color(255, 182, 193);
    // Hier definieren wir ein dunkleres Rüsselrosa
    private static final Color COLOR_PIG_DARK_PINK = new Color(255, 105, 180);
    // Hier definieren wir ein glänzendes Goldgelb
    private static final Color COLOR_GOLD = new Color(255, 215, 0);
    // Hier definieren wir das edle Obsidian-Dunkelgrau für die Steuerungsleiste
    private static final Color COLOR_OBSIDIAN = new Color(24, 28, 36);

    // Das ist der Bauplan für unser Spielfenster
    public GUIGame() {
        // Wir setzen den Fenstertitel fest
        setTitle("DRECKSAU - Das schlammigste Kartenspiel");
        // Wir stellen ein dass das Programm komplett beendet wird wenn das Fenster geschlossen wird
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Wir legen die Standardgröße des Fensters fest
        setSize(1200, 850);
        // Wir legen die Mindestgröße des Fensters fest
        setMinimumSize(new Dimension(1100, 780));
        // Wir zentrieren das Fenster in der Mitte des Bildschirms
        setLocationRelativeTo(null);

        // Wir erstellen das Layout-Werkzeug zum Wechseln der Ansichten
        cardLayout = new CardLayout();
        // Wir erstellen den Hauptcontainer mit diesem Wechsel-Werkzeug
        mainContainer = new JPanel(cardLayout);
        // Wir fügen den Hauptcontainer zum Fenster hinzu
        add(mainContainer);

        // Wir erstellen die Eingabe-Ansicht vor dem Spielstart
        createSetupPanel();
        // Wir fügen die Eingabe-Ansicht zum Wechselcontainer hinzu
        mainContainer.add(setupPanel, "SETUP");

        // Wir erstellen die Sichtschutz-Ansicht für den Spielerwechsel
        createPassPanel();
        // Wir fügen die Sichtschutz-Ansicht zum Wechselcontainer hinzu
        mainContainer.add(passPanel, "PASS");

        // Wir erstellen die Sieges-Ansicht
        createVictoryPanel();
        // Wir fügen die Sieges-Ansicht zum Wechselcontainer hinzu
        mainContainer.add(victoryPanel, "VICTORY");

        // Wir zeigen zu Beginn die Eingabe-Ansicht an
        cardLayout.show(mainContainer, "SETUP");
    }

    // Diese Funktion baut die Eingabe-Ansicht für Spieleranzahl und Namen auf
    private void createSetupPanel() {
        // Wir erstellen die Ansicht und bemalen den Hintergrund mit einem modernen dunklen Verlauf
        setupPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                // Wir rufen die Standard-Zeichenfunktion auf
                super.paintComponent(g);
                // Wir holen uns ein zweidimensionales Zeichenwerkzeug
                Graphics2D g2d = (Graphics2D) g.create();
                // Wir schalten die Kantenglättung für schöne Rundungen ein
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Wir erstellen einen eleganten dreifachen Farbverlauf von Tiefblau zu Dunkelgrün
                GradientPaint gp = new GradientPaint(0, 0, new Color(18, 30, 49), 0, getHeight(), new Color(24, 52, 35));
                // Wir laden den Farbverlauf in den Pinsel
                g2d.setPaint(gp);
                // Wir bemalen die gesamte Fläche mit dem Farbverlauf
                g2d.fillRect(0, 0, getWidth(), getHeight());
                // Wir geben den Pinsel wieder frei
                g2d.dispose();
            }
        };

        // Wir erstellen ein Einstellungs-Werkzeug für das Anordnen der Texte und Eingaben
        GridBagConstraints gbc = new GridBagConstraints();
        // Wir legen Abstände um alle Elemente fest
        gbc.insets = new Insets(10, 15, 10, 15);
        // Die Elemente sollen sich in der Breite anpassen
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Wir erstellen den großen Spieletitel
        JLabel titleLabel = new JLabel("DRECKSAU", JLabel.CENTER);
        // Wir setzen eine große und fette moderne Schriftart fest
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 84));
        // Wir färben die Schrift goldgelb
        titleLabel.setForeground(COLOR_GOLD);
        // Wir fügen einen leeren Abstand nach unten hinzu
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        
        // Wir setzen die Position in der Tabelle fest
        gbc.gridx = 0;
        gbc.gridy = 0;
        // Der Titel soll sich über zwei Spalten erstrecken
        gbc.gridwidth = 2;
        // Wir fügen den Titel zur Ansicht hinzu
        setupPanel.add(titleLabel, gbc);

        // Wir erstellen einen eleganten Untertitel
        JLabel subtitleLabel = new JLabel("Das schlammigste Kartenspiel aller Zeiten", JLabel.CENTER);
        // Wir setzen eine moderne Schriftart fest
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        // Wir färben den Text hellgrün
        subtitleLabel.setForeground(new Color(156, 204, 101));
        // Wir gehen in die nächste Zeile der Tabelle
        gbc.gridy = 1;
        // Wir fügen den Untertitel hinzu
        setupPanel.add(subtitleLabel, gbc);

        // Wir erstellen einen wunderschönen halbtransparenten Glaskasten für die Einstellungen
        JPanel glassCard = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                // Wir rufen die Standard-Zeichenfunktion auf
                super.paintComponent(g);
                // Wir holen uns ein zweidimensionales Zeichenwerkzeug
                Graphics2D g2d = (Graphics2D) g.create();
                // Wir schalten die Kantenglättung ein
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Wir färben den Kasten mit einem halbtransparenten Weiß
                g2d.setColor(new Color(255, 255, 255, 15));
                // Wir zeichnen die Kiste mit abgerundeten Ecken
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                // Wir zeichnen einen hauchdünnen hellen Rand um die Kiste für den Glaseffekt
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                // Wir geben den Pinsel frei
                g2d.dispose();
            }
        };
        // Wir machen den Glaskasten im Hintergrund unsichtbar
        glassCard.setOpaque(false);
        // Wir legen einen Abstand innerhalb des Glaskastens fest
        glassCard.setBorder(new EmptyBorder(25, 25, 25, 25));

        // Wir erstellen ein Anordnungs-Werkzeug für den Glaskasten
        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.insets = new Insets(10, 10, 10, 10);
        cardGbc.fill = GridBagConstraints.HORIZONTAL;

        // Wir erstellen den beschreibenden Text für die Auswahl
        JLabel countLabel = new JLabel("Wähle Spieleranzahl: ");
        // Wir setzen eine moderne Schrift fest
        countLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        // Wir färben den Text weiß
        countLabel.setForeground(Color.WHITE);
        cardGbc.gridx = 0;
        cardGbc.gridy = 0;
        glassCard.add(countLabel, cardGbc);

        // Wir legen die Auswahlmöglichkeiten fest
        Integer[] choices = { 2, 3, 4 };
        // Wir erstellen ein Auswahlmenü mit diesen Zahlen
        JComboBox<Integer> countCombo = new JComboBox<>(choices);
        // Wir setzen die Schriftart fest
        countCombo.setFont(new Font("SansSerif", Font.PLAIN, 15));
        // Wir wählen standardmäßig drei Spieler aus
        countCombo.setSelectedItem(3);
        cardGbc.gridx = 1;
        glassCard.add(countCombo, cardGbc);

        // Wir erstellen einen Kasten für die Namenseingabefelder
        JPanel namesPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        // Wir machen den Kasten unsichtbar
        namesPanel.setOpaque(false);

        // Wir erstellen Listen für die Beschriftungen und die Eingabefelder
        JLabel[] playerLabels = new JLabel[4];
        JTextField[] playerFields = new JTextField[4];

        // Wir erstellen nacheinander vier Eingabefelder
        for (int i = 0; i < 4; i++) {
            // Wir erstellen die Beschriftung für den jeweiligen Spieler
            playerLabels[i] = new JLabel("Name Spieler " + (i + 1) + ":", JLabel.RIGHT);
            // Wir setzen eine fette Schrift fest
            playerLabels[i].setFont(new Font("SansSerif", Font.BOLD, 15));
            // Wir färben die Schrift weiß
            playerLabels[i].setForeground(Color.WHITE);
            
            // Wir erstellen das eigentliche Eingabefeld mit einem Standardnamen
            playerFields[i] = new JTextField("Spieler " + (i + 1));
            // Wir setzen die Schriftart fest
            playerFields[i].setFont(new Font("SansSerif", Font.PLAIN, 15));
            // Wir legen die Standardgröße fest
            playerFields[i].setPreferredSize(new Dimension(150, 32));
            // Wir setzen einen feinen Rahmen für das Eingabefeld
            playerFields[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 40), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
            ));
            // Wir färben den Hintergrund des Eingabefelds dunkel
            playerFields[i].setBackground(new Color(30, 35, 45));
            // Wir färben den geschriebenen Text weiß
            playerFields[i].setForeground(Color.WHITE);
            // Wir färben den Cursor weiß
            playerFields[i].setCaretColor(Color.WHITE);

            // Wir fügen die Beschriftung zum Kasten hinzu
            namesPanel.add(playerLabels[i]);
            // Wir fügen das Eingabefeld zum Kasten hinzu
            namesPanel.add(playerFields[i]);
        }

        // Da standardmäßig drei Spieler ausgewählt sind machen wir das vierte Feld unsichtbar
        playerLabels[3].setVisible(false);
        playerFields[3].setVisible(false);

        // Wir platzieren das Namenspanel in der nächsten Zeile des Glaskastens
        cardGbc.gridx = 0;
        cardGbc.gridy = 1;
        cardGbc.gridwidth = 2;
        glassCard.add(namesPanel, cardGbc);

        // Wir reagieren darauf wenn der Benutzer die Spieleranzahl im Menü ändert
        countCombo.addActionListener(e -> {
            // Wir holen uns die neu ausgewählte Spieleranzahl
            int selected = (int) countCombo.getSelectedItem();
            // Wir gehen alle vier Felder durch
            for (int i = 0; i < 4; i++) {
                // Ein Feld soll nur sichtbar sein wenn seine Nummer kleiner als die ausgewählte Anzahl ist
                boolean show = i < selected;
                // Wir stellen die Sichtbarkeit der Beschriftung ein
                playerLabels[i].setVisible(show);
                // Wir stellen die Sichtbarkeit des Eingabefelds ein
                playerFields[i].setVisible(show);
            }
            // Wir berechnen die Anordnung neu
            glassCard.revalidate();
            // Wir zeichnen neu
            glassCard.repaint();
        });

        // Wir fügen den gesamten Glaskasten zur Haupt-Ansicht hinzu
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        setupPanel.add(glassCard, gbc);

        // Wir erstellen einen wunderschön abgerundeten Startknopf
        JButton startBtn = new JButton("Spiel starten!");
        // Wir setzen eine große und fette moderne Schrift fest
        startBtn.setFont(new Font("SansSerif", Font.BOLD, 22));
        // Wir färben den Knopf goldgelb
        startBtn.setBackground(COLOR_GOLD);
        // Wir färben den Text dunkel
        startBtn.setForeground(new Color(25, 28, 36));
        // Wir entfernen den unschönen Fokusrahmen
        startBtn.setFocusPainted(false);
        // Wir erstellen einen Knopfrahmen mit runden Ecken und Abständen
        startBtn.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));
        // Der Mauszeiger soll sich beim Drüberfahren in eine zeigende Hand verwandeln
        startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Wir reagieren auf Maus-Schwebungen über dem Startknopf
        startBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Wir färben den Knopf beim Drüberfahren noch heller
                startBtn.setBackground(new Color(255, 235, 59));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                // Wir färben den Knopf wieder auf das normale Goldgelb zurück
                startBtn.setBackground(COLOR_GOLD);
            }
        });

        // Wir reagieren auf einen Klick auf den Startknopf
        startBtn.addActionListener(e -> {
            // Wir holen uns die aktuelle Spieleranzahl
            int count = (int) countCombo.getSelectedItem();
            // Wir erstellen eine Liste für die Namen
            String[] names = new String[count];
            // Wir lesen die Namen aus allen sichtbaren Eingabefeldern aus
            for (int i = 0; i < count; i++) {
                // Wir schneiden Leerzeichen am Anfang und Ende des Namens weg
                String text = playerFields[i].getText().trim();
                // Falls das Feld komplett leer gelassen wurde
                if (text.isEmpty()) {
                    // Wir vergeben einen Standardnamen
                    text = "Spieler " + (i + 1);
                }
                // Wir speichern den Namen in unserer Liste ab
                names[i] = text;
            }

            // Wir starten ein neues Spiel mit den eingelesenen Namen
            game = new Game(names);
            
            // Wir bauen zuerst die eigentliche Spieloberfläche komplett auf (damit das Datenmodell und logListModel initialisiert wird!)
            createGamePanel();

            // Jetzt können wir die Liste der Protokollnachrichten im Anzeigefeld leeren
            logListModel.clear();
            // Wir tragen alle bisherigen Startmeldungen in das Anzeigefeld ein
            for (String log : game.getActionLog()) {
                // Wir fügen die Nachricht hinzu
                logListModel.addElement(log);
            }

            // Wir fügen die Spieloberfläche zum Hauptcontainer hinzu
            mainContainer.add(gamePanel, "GAME");
            
            // Wir wechseln die Ansicht auf das Spielfeld
            cardLayout.show(mainContainer, "GAME");
        });

        // Wir gehen in die nächste Zeile der Tabelle
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        // Wir fügen einen großen Abstand vor dem Knopf hinzu
        gbc.insets = new Insets(25, 15, 10, 15);
        // Wir fügen den Startknopf hinzu
        setupPanel.add(startBtn, gbc);
    }

    // Diese Funktion baut den Sichtschutz-Wechselbildschirm auf
    private void createPassPanel() {
        // Wir erstellen die Ansicht und bemalen den Hintergrund dunkelgrau bis schwarz
        passPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                // Wir rufen die Standard-Zeichenfunktion auf
                super.paintComponent(g);
                // Wir holen uns ein zweidimensionales Zeichenwerkzeug
                Graphics2D g2d = (Graphics2D) g.create();
                // Wir erstellen einen feinen modernen Verlauf
                GradientPaint gp = new GradientPaint(0, 0, new Color(20, 24, 30), 0, getHeight(), new Color(10, 12, 15));
                // Wir laden den Verlauf in den Pinsel
                g2d.setPaint(gp);
                // Wir bemalen die gesamte Fläche
                g2d.fillRect(0, 0, getWidth(), getHeight());
                // Wir geben den Pinsel wieder frei
                g2d.dispose();
            }
        };

        // Wir erstellen das Anordnungs-Werkzeug
        GridBagConstraints gbc = new GridBagConstraints();
        // Wir setzen die Startposition fest
        gbc.gridx = 0;
        gbc.gridy = 0;
        // Wir legen große Abstände um alle Elemente fest
        gbc.insets = new Insets(15, 20, 15, 20);

        // Wir erstellen einen großen Glaskasten für den Sichtschutz
        JPanel glassCard = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                // Wir rufen die Standard-Zeichenfunktion auf
                super.paintComponent(g);
                // Wir holen uns ein zweidimensionales Zeichenwerkzeug
                Graphics2D g2d = (Graphics2D) g.create();
                // Wir schalten die Kantenglättung ein
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Wir bemalen die Kiste halbtransparent
                g2d.setColor(new Color(255, 255, 255, 10));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                // Feiner weißer Rahmen
                g2d.setColor(new Color(255, 255, 255, 20));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                g2d.dispose();
            }
        };
        // Wir machen den Glaskasten unsichtbar
        glassCard.setOpaque(false);
        // Wir legen einen Abstand innerhalb des Kastens fest
        glassCard.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Wir erstellen das Anordnungs-Werkzeug für das Glaspanel
        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.insets = new Insets(15, 10, 15, 10);
        cardGbc.gridx = 0;

        // Wir erstellen die Überschrift für den Spielerwechsel
        JLabel passTitle = new JLabel("SPIELERWECHSEL", JLabel.CENTER);
        // Wir setzen eine fette moderne Schrift fest
        passTitle.setFont(new Font("SansSerif", Font.BOLD, 36));
        // Wir färben den Text weiß
        passTitle.setForeground(Color.WHITE);
        cardGbc.gridy = 0;
        // Wir fügen die Überschrift hinzu
        glassCard.add(passTitle, cardGbc);

        // Wir erstellen den Hinweistext wer sich bereit machen soll
        JLabel nextPlayerText = new JLabel("Bereit machen, ...", JLabel.CENTER);
        // Wir setzen die Schriftart fest
        nextPlayerText.setFont(new Font("SansSerif", Font.PLAIN, 20));
        // Wir färben den Text hellgrün
        nextPlayerText.setForeground(new Color(139, 195, 74));
        // Wir gehen in die nächste Zeile
        cardGbc.gridy = 1;
        // Wir fügen den Text hinzu
        glassCard.add(nextPlayerText, cardGbc);

        // Wir erstellen den Knopf zum Aufdecken der Karten
        JButton showHandBtn = new JButton("Handkarten aufdecken");
        // Wir setzen die Schriftart fest
        showHandBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        // Wir färben den Knopf blau
        showHandBtn.setBackground(new Color(33, 150, 243));
        // Wir färben die Schrift weiß
        showHandBtn.setForeground(Color.WHITE);
        // Wir entfernen den unschönen Fokusrahmen
        showHandBtn.setFocusPainted(false);
        // Der Mauszeiger soll sich in eine Hand verwandeln
        showHandBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Wir fügen Abstände im Knopf hinzu
        showHandBtn.setBorder(BorderFactory.createEmptyBorder(12, 35, 12, 35));

        // Wir reagieren auf Mausberührungen über dem Knopf
        showHandBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Heller machen
                showHandBtn.setBackground(new Color(66, 165, 245));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                // Zurücksetzen
                showHandBtn.setBackground(new Color(33, 150, 243));
            }
        });

        // Wir reagieren auf den Klick um den Sichtschutz zu beenden
        showHandBtn.addActionListener(e -> {
            // Wir wechseln die Ansicht zurück auf das Spielfeld
            cardLayout.show(mainContainer, "GAME");
            // Wir bringen alle Anzeigen auf den allerneuesten Stand
            updateUIState();
        });
        cardGbc.gridy = 2;
        // Wir fügen den Knopf hinzu
        glassCard.add(showHandBtn, cardGbc);

        // Wir fügen den Glaskasten zur Sichtschutz-Ansicht hinzu
        passPanel.add(glassCard, gbc);
    }

    // Diese Funktion baut den Siegesbildschirm auf
    private void createVictoryPanel() {
        // Wir erstellen die Ansicht und bemalen den Hintergrund mit einem festlichen Verlauf
        victoryPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                // Wir rufen die Standard-Zeichenfunktion auf
                super.paintComponent(g);
                // Wir holen uns ein zweidimensionales Zeichenwerkzeug
                Graphics2D g2d = (Graphics2D) g.create();
                // Wir erstellen einen festlichen Farbverlauf von Dunkelgold zu Dunkelrot
                GradientPaint gp = new GradientPaint(0, 0, new Color(245, 124, 0), 0, getHeight(), new Color(136, 14, 79));
                // Wir laden den Verlauf
                g2d.setPaint(gp);
                // Wir bemalen die gesamte Fläche
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Wir schalten die Kantenglättung für schöne Formen ein
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Wir wählen ein leicht transparentes Weiß
                g2d.setColor(new Color(255, 255, 255, 30));
                // Wir zeichnen fünfzehn zufällig verteilte Punkte im Hintergrund
                for (int i = 0; i < 20; i++) {
                    // Wir würfeln eine zufällige Position in der Breite
                    int x = (int) (Math.random() * getWidth());
                    // Wir würfeln eine zufällige Position in der Höhe
                    int y = (int) (Math.random() * getHeight());
                    // Wir würfeln eine zufällige Größe
                    int size = 15 + (int) (Math.random() * 40);
                    // Wir zeichnen den Punkt auf den Hintergrund
                    g2d.fillOval(x, y, size, size);
                }
                // Wir geben den Pinsel wieder frei
                g2d.dispose();
            }
        };

        // Wir erstellen das Anordnungs-Werkzeug
        GridBagConstraints gbc = new GridBagConstraints();
        // Wir setzen die Startposition fest
        gbc.gridx = 0;
        gbc.gridy = 0;
        // Wir legen Abstände um alle Elemente fest
        gbc.insets = new Insets(10, 15, 10, 15);

        // Wir erstellen einen großen Glaskasten für den Sieg
        JPanel glassCard = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                // Wir rufen die Standard-Zeichenfunktion auf
                super.paintComponent(g);
                // Wir holen uns ein zweidimensionales Zeichenwerkzeug
                Graphics2D g2d = (Graphics2D) g.create();
                // Wir schalten die Kantenglättung ein
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Wir bemalen die Kiste halbtransparent
                g2d.setColor(new Color(255, 255, 255, 15));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                // Feiner Rahmen
                g2d.setColor(new Color(255, 255, 255, 25));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                g2d.dispose();
            }
        };
        // Wir machen den Glaskasten unsichtbar
        glassCard.setOpaque(false);
        // Wir legen einen Abstand innerhalb des Kastens fest
        glassCard.setBorder(new EmptyBorder(35, 45, 35, 45));

        // Wir erstellen das Anordnungs-Werkzeug für das Glaspanel
        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.insets = new Insets(12, 10, 12, 10);
        cardGbc.gridx = 0;

        // Wir erstellen den großen Sieges-Titel
        JLabel winTitle = new JLabel("HERZLICHEN GLUECKWUNSCH!", JLabel.CENTER);
        // Wir setzen eine sehr große und fette Schrift fest
        winTitle.setFont(new Font("SansSerif", Font.BOLD, 42));
        // Wir färben die Schrift weiß
        winTitle.setForeground(Color.WHITE);
        cardGbc.gridy = 0;
        glassCard.add(winTitle, cardGbc);

        // Wir erstellen den Gewinner-Text
        JLabel winnerText = new JLabel("Spieler hat gewonnen!", JLabel.CENTER);
        // Wir setzen eine fette Schrift fest
        winnerText.setFont(new Font("SansSerif", Font.BOLD, 30));
        // Wir färben den Text goldgelb
        winnerText.setForeground(COLOR_GOLD);
        // Wir gehen in die nächste Zeile
        cardGbc.gridy = 1;
        glassCard.add(winnerText, cardGbc);

        // Wir erstellen eine kurze Erklärung
        JLabel descText = new JLabel("Alle Schweine sind glueckliche Drecksaeue geworden!", JLabel.CENTER);
        // Wir setzen die Schriftart fest
        descText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        // Wir färben den Text weiß
        descText.setForeground(Color.WHITE);
        // Wir gehen in die nächste Zeile
        cardGbc.gridy = 2;
        glassCard.add(descText, cardGbc);

        // Wir erstellen den Knopf um ein neues Spiel zu starten
        JButton restartBtn = new JButton("Neues Spiel starten");
        // Wir setzen eine fette Schrift fest
        restartBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        // Wir färben den Knopf dunkelgrün
        restartBtn.setBackground(new Color(76, 175, 80));
        // Wir färben die Schrift weiß
        restartBtn.setForeground(Color.WHITE);
        // Wir entfernen den Fokusrahmen
        restartBtn.setFocusPainted(false);
        // Der Mauszeiger soll sich in eine zeigende Hand verwandeln
        restartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Wir fügen Abstände im Knopf hinzu
        restartBtn.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));

        // Wir reagieren auf Mausberührungen über dem Knopf
        restartBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Heller machen
                restartBtn.setBackground(new Color(102, 187, 106));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                // Zurücksetzen
                restartBtn.setBackground(new Color(76, 175, 80));
            }
        });

        // Wir reagieren auf einen Klick auf den Neustartknopf
        restartBtn.addActionListener(e -> {
            // Wir wechseln die Ansicht zurück auf die Spielereingabe
            cardLayout.show(mainContainer, "SETUP");
        });
        cardGbc.gridy = 3;
        glassCard.add(restartBtn, cardGbc);

        // Wir fügen den Glaskasten zur Sieges-Ansicht hinzu
        victoryPanel.add(glassCard, gbc);
    }

    // Diese Funktion baut die eigentliche Spielfeld-Ansicht auf
    private void createGamePanel() {
        // Wir erstellen das Spielfeld-Panel
        gamePanel = new JPanel(new BorderLayout());
        // Wir setzen das Wiesengrün als Hintergrundfarbe
        gamePanel.setBackground(COLOR_GRASS);

        // Wir erstellen die Weide im Zentrum des Bildschirms mit einem feinen Rasen-Farbverlauf
        pasturePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Wir rufen die Standard-Zeichenfunktion auf
                super.paintComponent(g);
                // Wir holen uns ein zweidimensionales Zeichenwerkzeug
                Graphics2D g2d = (Graphics2D) g.create();
                // Wir schalten die Kantenglättung für schöne Rundungen ein
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Wir erstellen einen wunderschönen strukturierten Farbverlauf für die Weide
                GradientPaint gp = new GradientPaint(0, 0, new Color(34, 112, 40), 0, getHeight(), new Color(46, 139, 53));
                // Wir laden den Verlauf
                g2d.setPaint(gp);
                // Wir bemalen die gesamte Wiese
                g2d.fillRect(0, 0, getWidth(), getHeight());
                // Wir geben den Pinsel wieder frei
                g2d.dispose();
            }
        };
        // Wir nutzen eine Tabelle für die Wiese
        pasturePanel.setLayout(new GridBagLayout());
        // Wir fügen die Wiese in die Mitte des Spielfelds ein
        gamePanel.add(pasturePanel, BorderLayout.CENTER);

        // Wir erstellen die Steuerungsleiste im Obsidian-Stil auf der rechten Seite
        createControlPanel();
        // Wir fügen die Leiste rechts an das Spielfeld an
        gamePanel.add(controlPanel, BorderLayout.EAST);

        // Wir erstellen einen Bereich ganz unten für die eigenen Karten
        JPanel bottomPanel = new JPanel(new BorderLayout());
        // Wir machen den Hintergrund unsichtbar
        bottomPanel.setOpaque(false);
        // Wir legen Abstände um das Kartenpanel fest
        bottomPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        // Wir erstellen das Panel für die drei Handkarten
        handCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 5));
        // Wir machen das Panel unsichtbar
        handCardsPanel.setOpaque(false);
        // Wir fügen das Handkartenpanel in die Mitte des unteren Bereichs ein
        bottomPanel.add(handCardsPanel, BorderLayout.CENTER);

        // Wir fügen den gesamten unteren Bereich ganz unten ins Spielfeld ein
        gamePanel.add(bottomPanel, BorderLayout.SOUTH);

        // Wir aktualisieren alle Elemente auf dem Spielfeld
        updateUIState();
    }

    // Diese Funktion baut die Obsidian-Steuerungsleiste rechts auf
    private void createControlPanel() {
        // Wir bemalen die Leiste mit einem extrem edlen Dark-Mode / Obsidian Design
        controlPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Wir rufen die Standard-Zeichenfunktion auf
                super.paintComponent(g);
                // Wir holen uns das zweidimensionale Zeichenwerkzeug
                Graphics2D g2d = (Graphics2D) g.create();
                // Wir schalten die Kantenglättung ein
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Wir bemalen die gesamte Steuerungsleiste im edlen Obsidian-Dunkelgrau
                g2d.setColor(COLOR_OBSIDIAN);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Wir zeichnen eine feine, neon-blaue vertikale Zierlinie an der linken Seite
                g2d.setColor(new Color(33, 150, 243, 150));
                g2d.setStroke(new BasicStroke(2.0f));
                g2d.drawLine(0, 0, 0, getHeight());
                
                // Wir geben den Pinsel frei
                g2d.dispose();
            }
        };
        // Die Steuerungselemente sollen untereinander angeordnet werden
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        // Wir legen die Breite der Leiste auf 320 Bildpunkte fest
        controlPanel.setPreferredSize(new Dimension(320, 0));
        // Wir legen einen inneren Abstand um die Elemente fest
        controlPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Wir erstellen die Überschrift für die Steuerungsleiste
        JLabel labelSpielfeld = new JLabel("SCHEUER UND STAPEL", JLabel.CENTER);
        // Wir setzen eine fette moderne Schrift fest
        labelSpielfeld.setFont(new Font("SansSerif", Font.BOLD, 18));
        // Wir färben den Text beige/weiß
        labelSpielfeld.setForeground(new Color(236, 240, 241));
        // Wir zentrieren die Überschrift mittig in der Leiste
        labelSpielfeld.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Wir fügen die Überschrift hinzu
        controlPanel.add(labelSpielfeld);
        // Wir fügen einen leeren Abstand nach unten ein
        controlPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Wir erstellen das Textfeld für Informationen zum Stapel
        deckInfoLabel = new JLabel("Karten im Deck: --", JLabel.CENTER);
        // Wir setzen eine fette Schrift fest
        deckInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        // Wir färben den Text hellgrau/blau
        deckInfoLabel.setForeground(new Color(144, 164, 174));
        // Wir zentrieren den Text mittig
        deckInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Wir fügen das Textfeld hinzu
        controlPanel.add(deckInfoLabel);
        // Wir fügen einen leeren Abstand ein
        controlPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Wir erstellen den Ablagestapel als visuelle Kiste mit Glaseffekt
        JPanel pilePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Wir rufen die Standard-Zeichenfunktion auf
                super.paintComponent(g);
                // Wir holen uns das zweidimensionale Zeichenwerkzeug
                Graphics2D g2d = (Graphics2D) g.create();
                // Wir schalten die Kantenglättung ein
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Wir zeichnen eine leicht transparente weiße Kiste
                g2d.setColor(new Color(255, 255, 255, 12));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                
                // Feiner weißer Rand
                g2d.setColor(new Color(255, 255, 255, 25));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                
                // Wir wählen eine weiße Schriftfarbe
                g2d.setColor(Color.WHITE);
                // Wir setzen eine fette Schrift fest
                g2d.setFont(new Font("SansSerif", Font.BOLD, 13));
                // Wir erstellen einen Standardtext für einen leeren Ablagestapel
                String text = "Ablagestapel leer";
                // Wenn das Spiel läuft und Karten auf dem Stapel liegen
                if (game != null && game.getDeck().discardPileSize() > 0) {
                    text = "Oben auf Ablagestapel";
                }
                FontMetrics fm = g2d.getFontMetrics();
                // Wir zeichnen den Text genau zentriert in die Kiste
                g2d.drawString(text, (getWidth() - fm.stringWidth(text)) / 2, 25);
                
                g2d.dispose();
            }
        };
        // Wir machen den Kasten unsichtbar
        pilePanel.setOpaque(false);
        // Wir begrenzen die Größe des Ablagestapels
        pilePanel.setMaximumSize(new Dimension(280, 140));
        // Wir legen die bevorzugte Größe fest
        pilePanel.setPreferredSize(new Dimension(280, 140));
        // Wir zentrieren alle Knöpfe in der Kiste
        pilePanel.setLayout(new GridBagLayout());

        // Wir erstellen den Knopf um Karten abzulegen oder global zu spielen
        playGlobalButton = new JButton("Karte hier spielen / abwerfen");
        // Wir setzen die Schriftart fest
        playGlobalButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        // Wir färben den Knopf modern blau
        playGlobalButton.setBackground(new Color(33, 150, 243));
        // Wir färben die Schrift weiß
        playGlobalButton.setForeground(Color.WHITE);
        // Wir entfernen den Fokusrahmen
        playGlobalButton.setFocusPainted(false);
        // Der Knopf soll zu Beginn unsichtbar sein da noch keine Karte ausgewählt ist
        playGlobalButton.setVisible(false);
        // Wir reagieren auf einen Klick
        playGlobalButton.addActionListener(e -> handleDiscardOrGlobalPlay());
        // Wir fügen den Knopf in die Kiste des Ablagestapels ein
        pilePanel.add(playGlobalButton);

        // Wir fügen den Ablagestapel zur Steuerungsleiste hinzu
        controlPanel.add(pilePanel);
        // Wir fügen einen Abstand ein
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Wir erstellen eine Überschrift für die Spielnachrichten
        JLabel logTitle = new JLabel("Hof-Nachrichten", JLabel.LEFT);
        // Wir setzen eine fette Schrift fest
        logTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        // Wir färben den Text beige/weiß
        logTitle.setForeground(new Color(236, 240, 241));
        // Wir zentrieren die Überschrift
        logTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Wir fügen die Überschrift hinzu
        controlPanel.add(logTitle);
        // Wir fügen einen kleinen Abstand ein
        controlPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Wir erstellen eine neue Liste für die Spielnachrichten
        logListModel = new DefaultListModel<>();
        // Wir erstellen das Anzeigefeld und verknüpfen es mit der Liste
        logList = new JList<>(logListModel);
        // Wir setzen eine moderne serifenlose Schriftart fest
        logList.setFont(new Font("SansSerif", Font.PLAIN, 12));
        // Wir färben den Hintergrund dunkelgrau
        logList.setBackground(new Color(33, 38, 48));
        // Wir färben den Text hellgrau/beige
        logList.setForeground(new Color(207, 216, 220));
        // Wir färben die Hintergrundfarbe für ausgewählte Zeilen
        logList.setSelectionBackground(new Color(33, 38, 48));
        logList.setSelectionForeground(new Color(207, 216, 220));
        
        // Wir erstellen eine Scrollbox
        JScrollPane logScroll = new JScrollPane(logList);
        // Wir zeichnen eine feine dunkle Umrandung um die Scrollbox
        logScroll.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 15), 1));
        // Wir begrenzen die Größe der Scrollbox
        logScroll.setMaximumSize(new Dimension(280, 250));
        // Wir legen die Standardgröße fest
        logScroll.setPreferredSize(new Dimension(280, 250));
        // Wir fügen die Scrollbox zur Steuerungsleiste hinzu
        controlPanel.add(logScroll);
        // Wir fügen einen leeren Abstand hinzu
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Wir erstellen den Notfallknopf zum Abwerfen der gesamten Hand
        discardEntireHandButton = new JButton("Hand abwerfen & neu ziehen");
        // Wir setzen die Schriftart fest
        discardEntireHandButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        // Wir färben den Knopf dunkelrot
        discardEntireHandButton.setBackground(new Color(183, 28, 28));
        // Wir färben den Text weiß
        discardEntireHandButton.setForeground(Color.WHITE);
        // Wir entfernen den Fokusrahmen
        discardEntireHandButton.setFocusPainted(false);
        // Wir zentrieren den Knopf in der Steuerungsleiste
        discardEntireHandButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Wir legen die maximale Größe fest
        discardEntireHandButton.setMaximumSize(new Dimension(280, 45));
        // Der Mauszeiger soll sich in eine Hand verwandeln
        discardEntireHandButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Der Knopf ist zu Beginn gesperrt
        discardEntireHandButton.setEnabled(false);
        // Wir reagieren auf einen Klick
        discardEntireHandButton.addActionListener(e -> handleDiscardEntireHand());
        // Wir fügen den Knopf zur Steuerungsleiste hinzu
        controlPanel.add(discardEntireHandButton);
    }

    // Diese Funktion aktualisiert das Spielfeld komplett
    public void updateUIState() {
        // Wenn das Spiel noch nicht gestartet ist brechen wir ab
        if (game == null) return;

        // Wir aktualisieren die Informationen auf dem Nachzieh- und Ablagestapel
        deckInfoLabel.setText("Nachziehstapel: " + game.getDeck().drawPileSize() + " | Ablage: " + game.getDeck().discardPileSize());

        // Wir holen uns den aktiven Spieler
        Player active = game.getActivePlayer();
        // Wir prüfen ob der aktive Spieler aktuell überhaupt einen erlaubten Zug machen kann
        boolean hasMoves = game.hatSpielerGueltigeZuege(active);
        // Der Knopf wird nur aktiv geschaltet wenn der Spieler keinen einzigen Zug mehr machen kann
        discardEntireHandButton.setEnabled(!hasMoves);
        // Wenn der Spieler blockiert ist
        if (!hasMoves) {
            // Wir färben den Knopf leuchtend rot als Warnung
            discardEntireHandButton.setBackground(new Color(229, 57, 53));
        // Wenn der Spieler noch normal legen kann
        } else {
            // Wir färben den Knopf dezent dunkelrot
            discardEntireHandButton.setBackground(new Color(110, 20, 20));
        }

        // Wenn der Spieler eine Karte auf der Hand angeklickt hat
        if (selectedHandCard != null) {
            // Wir blenden den Aktionsknopf auf dem Ablagestapel ein
            playGlobalButton.setVisible(true);
            // Wenn die Karte Regen oder Schlammvulkan heißt
            if (selectedHandCard.getName().equals("Regen") || selectedHandCard.getName().equals("Schlammvulkan")) {
                // Wir ändern den Text auf globales Ausspielen
                playGlobalButton.setText("Global ausspielen!");
                // Wir färben den Knopf wiesengrün
                playGlobalButton.setBackground(new Color(76, 175, 80));
            // Bei allen anderen Karten handelt es sich um ein normales Abwerfen
            } else {
                // Wir ändern den Text auf ungenutzt abwerfen
                playGlobalButton.setText("Ungenutzt abwerfen");
                // Wir färben den Knopf grau
                playGlobalButton.setBackground(new Color(96, 125, 139));
            }
        // Wenn keine Karte angeklickt ist
        } else {
            // Wir blenden den Aktionsknopf wieder aus
            playGlobalButton.setVisible(false);
        }

        // Wir leeren die Wiese komplett um sie sauber neu aufzubauen
        pasturePanel.removeAll();
        // Wir erstellen ein Anordnungs-Werkzeug
        GridBagConstraints gbc = new GridBagConstraints();
        // Die Weideflächen sollen die gesamte Breite und Höhe ausfüllen
        gbc.fill = GridBagConstraints.BOTH;
        // Wir legen Abstände fest
        gbc.insets = new Insets(8, 8, 8, 8);
        // Die Breite soll sich dynamisch anpassen
        gbc.weightx = 1.0;
        
        // Wir erstellen eine neue Liste für die Mitspieler
        List<Player> opponents = new ArrayList<>();
        // Wir gehen alle Spieler durch
        for (Player p : game.getPlayers()) {
            // Wenn der Spieler nicht der aktive Spieler ist handelt es sich um einen Gegner
            if (p != active) {
                // Wir fügen den Mitspieler zur Gegnerliste hinzu
                opponents.add(p);
            }
        }

        // Wir erstellen eine Zeile in der die Weideflächen aller Mitspieler nebeneinander liegen
        JPanel opponentsRow = new JPanel(new GridLayout(1, opponents.size(), 12, 0));
        // Wir machen den Hintergrund unsichtbar
        opponentsRow.setOpaque(false);
        
        // Wir gehen nacheinander alle Gegner durch
        for (Player opponent : opponents) {
            // Wir erstellen die Weidefläche für diesen Gegner in kleinerer Größe
            JPanel oppPanel = createPlayerPasturePanel(opponent, false);
            // Wir fügen die Fläche zur Zeile der Gegner hinzu
            opponentsRow.add(oppPanel);
        }

        // Wir platzieren die Gegner-Zeile ganz oben
        gbc.gridx = 0;
        gbc.gridy = 0;
        // Die Gegner-Zeile erhält 45 Prozent der gesamten Wiesenhöhe
        gbc.weighty = 0.45;
        // Wir fügen die Gegnerzeile hinzu
        pasturePanel.add(opponentsRow, gbc);

        // Wir erstellen einen feinen Holzzaun als optische Trennung zur eigenen Wiese
        JPanel separator = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Wir rufen die Standard-Zeichenfunktion auf
                super.paintComponent(g);
                // Wir holen uns das zweidimensionale Zeichenwerkzeug
                Graphics2D g2d = (Graphics2D) g.create();
                // Wir schalten die Kantenglättung ein
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Wir wählen ein leicht transparentes dunkles Holzbraun
                g2d.setColor(new Color(78, 52, 46, 160));
                // Wir wählen eine fette Linienstärke von 4 Punkten
                g2d.setStroke(new BasicStroke(4.0f));
                // Wir zeichnen eine Querlinie als Holzzaun
                g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
                g2d.dispose();
            }
        };
        // Wir machen den Zaun im Hintergrund unsichtbar
        separator.setOpaque(false);
        // Wir gehen in die mittlere Zeile
        gbc.gridy = 1;
        // Der Zaun erhält nur 5 Prozent der Wiesenhöhe
        gbc.weighty = 0.05;
        // Wir fügen den Zaun hinzu
        pasturePanel.add(separator, gbc);

        // Wir erstellen die eigene Weidefläche für den aktiven Spieler
        JPanel activePanel = createPlayerPasturePanel(active, true);
        // Wir platzieren die eigene Wiese ganz unten
        gbc.gridy = 2;
        // Die eigene Wiese erhält 50 Prozent der Wiesenhöhe
        gbc.weighty = 0.5;
        // Wir fügen die eigene Wiese hinzu
        pasturePanel.add(activePanel, gbc);

        // Wir leeren die Handkarten-Leiste ganz unten
        handCardsPanel.removeAll();
        // Wir holen uns alle Handkarten des aktiven Spielers
        List<Card> hand = active.getHand();
        // Wir gehen jede Handkarte der Reihe nach durch
        for (int i = 0; i < hand.size(); i++) {
            // Wir holen uns die Spielkarte
            Card c = hand.get(i);
            // Wir erstellen die grafische Ansicht für diese Handkarte
            JPanel cardView = createCardPanel(c, i);
            // Wir fügen die Karte zur Leiste ganz unten hinzu
            handCardsPanel.add(cardView);
        }

        // Wir leeren das Protokoll-Anzeigefeld komplett
        logListModel.clear();
        // Wir tragen alle Spielereignisse der Reihe nach ein
        for (String log : game.getActionLog()) {
            // Wir fügen das Ereignis hinzu
            logListModel.addElement(log);
        }
        // Wir sorgen dafür dass die Anzeige immer nach ganz unten zur neuesten Nachricht springt
        logList.ensureIndexIsVisible(logListModel.size() - 1);

        // Wir ordnen die Wiese komplett neu an
        pasturePanel.revalidate();
        // Wir zeichnen die Wiese neu
        pasturePanel.repaint();
        // Wir ordnen die Handkarten-Leiste neu an
        handCardsPanel.revalidate();
        // Wir zeichnen die Handkarten-Leiste neu
        handCardsPanel.repaint();
    }

    // Diese Funktion baut die Weidefläche für einen bestimmten Spieler inklusive Schweine auf
    private JPanel createPlayerPasturePanel(Player player, boolean isActivePlayer) {
        // Wir erstellen ein neues Panel mit BorderLayout
        JPanel panel = new JPanel(new BorderLayout());
        // Wir machen den Hintergrund unsichtbar
        panel.setOpaque(false);

        // Wir erstellen die Beschriftung mit dem Namen des Spielers
        JLabel nameLabel = new JLabel(player.getUsername() + (isActivePlayer ? " (DU)" : ""), JLabel.CENTER);
        // Wenn es der eigene Spieler ist schreiben wir den Namen etwas größer und fetter
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, isActivePlayer ? 20 : 15));
        // Wir färben die Schrift weiß
        nameLabel.setForeground(Color.WHITE);
        // Wir fügen einen leeren Abstand nach unten hinzu
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        // Wir fügen die Namensbeschriftung oben im Panel ein
        panel.add(nameLabel, BorderLayout.NORTH);

        // Wir erstellen eine Reihe für die Schweine des Spielers
        JPanel pigsRow = new JPanel(new GridLayout(1, player.getSchweine().size(), isActivePlayer ? 15 : 8, 0));
        // Wir machen die Reihe im Hintergrund unsichtbar
        pigsRow.setOpaque(false);

        // Wir holen uns alle Schweine des Spielers
        List<Sau> schweine = player.getSchweine();
        // Wir gehen jedes Schwein nacheinander durch
        for (int i = 0; i < schweine.size(); i++) {
            // Wir holen uns das Schwein
            Sau sau = schweine.get(i);
            // Wir speichern die Nummer des Schweins für Klicks ab
            int index = i;
            
            // Wir erstellen die grafische Vektoransicht zum Zeichnen des Schweinchens
            JPanel pigCanvas = new JPanel() {
                // Hier speichern wir ob sich die Maus aktuell über diesem Schweinchen befindet
                private boolean isHovered = false;

                {
                    // Wir reagieren auf Maus-Ereignisse über dem Schweinchen
                    addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            // Wenn der Spieler eine Handkarte angeklickt hat
                            if (selectedHandCard != null) {
                                // Wir prüfen ob die Karte regelkonform auf dieses Schweinchen gespielt werden darf
                                if (selectedHandCard.kannGespieltWerden(game, game.getActivePlayer(), index, player)) {
                                    // Wir aktivieren das Leuchten des Schweinchens bei Mausberührung
                                    isHovered = true;
                                    // Der Mauszeiger verwandelt sich in eine zeigende Hand
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    // Wir zeichnen das Schweinchen neu
                                    repaint();
                                }
                            }
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            // Wir schalten das Leuchten wieder aus wenn die Maus das Schweinchen verlässt
                            isHovered = false;
                            // Der Mauszeiger wird wieder normal
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            // Wir zeichnen das Schweinchen neu
                            repaint();
                        }

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            // Wenn der Spieler eine Karte zum Ausspielen ausgewählt hat
                            if (selectedHandCard != null) {
                                try {
                                    // Wir prüfen ob der Spielzug auf dieses Schweinchen regelkonform erlaubt ist
                                    if (selectedHandCard.kannGespieltWerden(game, game.getActivePlayer(), index, player)) {
                                        
                                        // Wir merken uns die Karte
                                        Card cardToPlay = selectedHandCard;
                                        
                                        // Wir führen den gesamten Spielzug im Spiel aus
                                        game.spieleZug(cardToPlay, index, player);
                                        
                                        // Wir setzen die Kartenauswahl komplett zurück
                                        selectedHandCard = null;
                                        selectedHandCardIndex = -1;

                                        // Wenn das Spiel nach diesem Zug vorbei ist
                                        if (game.isGameOver()) {
                                            // Wir zeigen den Siegesbildschirm an
                                            showVictoryScreen();
                                        // Wenn das Spiel weitergeht
                                        } else {
                                            // Wir blenden den Sichtschutz ein
                                            showPassScreen();
                                        }
                                    }
                                } catch (Exception ex) {
                                    // Bei einem Fehler zeigen wir ein kurzes Fehlerfenster auf dem Bildschirm an
                                    JOptionPane.showMessageDialog(GUIGame.this, "Fehler: " + ex.getMessage(), "Ungueltiger Zug", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    });
                }

                @Override
                protected void paintComponent(Graphics g) {
                    // Wir rufen die Standard-Zeichenfunktion auf
                    super.paintComponent(g);
                    // Wir holen uns das zweidimensionale Zeichenwerkzeug
                    Graphics2D g2d = (Graphics2D) g.create();
                    // Wir schalten die Kantenglättung für schöne weiche Rundungen ein
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Wir lesen die Breite und Höhe der Schweine-Box aus
                    int w = getWidth();
                    int h = getHeight();

                    // Wir prüfen ob das Schweinchen leuchtet weil die Maus darüber ist
                    if (isHovered) {
                        // Wir zeichnen einen leicht transparenten weißen Hintergrund
                        g2d.setColor(new Color(255, 255, 255, 35));
                        g2d.fillRoundRect(0, 0, w, h, 20, 20);
                        // Wir zeichnen einen glänzenden goldenen Rahmen
                        g2d.setColor(COLOR_GOLD);
                        g2d.setStroke(new BasicStroke(2.0f));
                        g2d.drawRoundRect(0, 0, w - 1, h - 1, 20, 20);
                    // Wenn die Maus nicht drüber ist
                    } else {
                        // Wir zeichnen eine dezente dunkle Hintergrundbox
                        g2d.setColor(new Color(0, 0, 0, 25));
                        g2d.fillRoundRect(0, 0, w, h, 20, 20);
                    }

                    // Wir berechnen die Größe des Schweinchens
                    int pigW = isActivePlayer ? 90 : 60;
                    int pigH = isActivePlayer ? 60 : 40;
                    // Wir platzieren das Schweinchen zentriert in der Box
                    int pigX = (w - pigW) / 2;
                    int pigY = h - pigH - (isActivePlayer ? 24 : 14);

                    // 1. Wir zeichnen einen weichen dunklen Schatten unter das Schwein auf die Wiese
                    g2d.setColor(new Color(0, 0, 0, 35));
                    g2d.fillOval(pigX - 5, pigY + pigH - 8, pigW + 10, 12);

                    // 2. Körper des Schweinchens (wir zeichnen ein rosa Oval)
                    g2d.setColor(COLOR_PIG_PINK);
                    g2d.fillOval(pigX, pigY, pigW, pigH);
                    // Wir zeichnen eine feine dunkle Umrandung um den Körper
                    g2d.setColor(new Color(60, 40, 40, 50));
                    g2d.drawOval(pigX, pigY, pigW, pigH);

                    // 3. Ringelschwanz zeichnen (spiralförmiger Vektor hinten links)
                    g2d.setColor(COLOR_PIG_DARK_PINK);
                    g2d.setStroke(new BasicStroke(2.0f));
                    Path2D tail = new Path2D.Double();
                    tail.moveTo(pigX + 5, pigY + pigH / 2);
                    tail.curveTo(pigX - 10, pigY + pigH / 2 - 10, pigX - 5, pigY + pigH / 2 - 20, pigX - 12, pigY + pigH / 2 - 15);
                    g2d.draw(tail);
                    g2d.setStroke(new BasicStroke(1.0f));

                    // 4. Kopf des Schweinchens (wir zeichnen ein kleineres rosa Oval vorne rechts)
                    int headW = isActivePlayer ? 45 : 30;
                    int headH = isActivePlayer ? 45 : 30;
                    int headX = pigX + pigW - (isActivePlayer ? 32 : 22);
                    int headY = pigY - (isActivePlayer ? 10 : 7);
                    g2d.setColor(COLOR_PIG_PINK);
                    g2d.fillOval(headX, headY, headW, headH);
                    g2d.setColor(new Color(60, 40, 40, 50));
                    g2d.drawOval(headX, headY, headW, headH);

                    // 5. Ruessel des Schweinchens (wir zeichnen ein flaches dunkleres Oval)
                    int snoutW = isActivePlayer ? 18 : 12;
                    int snoutH = isActivePlayer ? 12 : 8;
                    int snoutX = headX + headW - (isActivePlayer ? 10 : 7);
                    int snoutY = headY + (headH - snoutH) / 2 + 2;
                    g2d.setColor(COLOR_PIG_DARK_PINK);
                    g2d.fillOval(snoutX, snoutY, snoutW, snoutH);
                    g2d.setColor(new Color(60, 40, 40, 50));
                    g2d.drawOval(snoutX, snoutY, snoutW, snoutH);
                    // Rüssellöcher (zwei kleine schwarze Punkte)
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.fillOval(snoutX + (isActivePlayer ? 4 : 3), snoutY + (isActivePlayer ? 3 : 2), 2, 2);
                    g2d.fillOval(snoutX + (isActivePlayer ? 10 : 7), snoutY + (isActivePlayer ? 3 : 2), 2, 2);

                    // 6. Niedliche, leicht transparente rosa Bäckchen für den Wow-Effekt
                    g2d.setColor(new Color(255, 105, 180, 110));
                    g2d.fillOval(headX + (isActivePlayer ? 8 : 5), headY + (isActivePlayer ? 18 : 12), isActivePlayer ? 10 : 7, isActivePlayer ? 8 : 5);

                    // 7. Augen des Schweinchens (Kulleraugen mit Lichtreflexen)
                    g2d.setColor(Color.WHITE);
                    int eyeSize = isActivePlayer ? 8 : 6;
                    int eyeX = headX + (isActivePlayer ? 14 : 9);
                    int eyeY = headY + (isActivePlayer ? 10 : 7);
                    g2d.fillOval(eyeX, eyeY, eyeSize, eyeSize);
                    g2d.fillOval(eyeX + (isActivePlayer ? 12 : 8), eyeY, eyeSize, eyeSize);
                    
                    // Pupillen
                    g2d.setColor(Color.BLACK);
                    g2d.fillOval(eyeX + (isActivePlayer ? 2 : 1), eyeY + (isActivePlayer ? 2 : 1), eyeSize / 2, eyeSize / 2);
                    g2d.fillOval(eyeX + (isActivePlayer ? 14 : 9), eyeY + (isActivePlayer ? 2 : 1), eyeSize / 2, eyeSize / 2);

                    // Kleine weiße Glanzpunkte in den Pupillen (Lichtreflexe für extremen Niedlichkeits-Look)
                    g2d.setColor(Color.WHITE);
                    g2d.fillOval(eyeX + (isActivePlayer ? 2 : 1), eyeY + (isActivePlayer ? 2 : 1), 2, 2);
                    g2d.fillOval(eyeX + (isActivePlayer ? 14 : 9), eyeY + (isActivePlayer ? 2 : 1), 2, 2);

                    // 8. Ohren des Schweinchens (Zwei kleine Dreiecke)
                    Path2D earLeft = new Path2D.Double();
                    earLeft.moveTo(headX + (isActivePlayer ? 10 : 7), headY + 4);
                    earLeft.lineTo(headX + (isActivePlayer ? 4 : 3), headY - (isActivePlayer ? 8 : 5));
                    earLeft.lineTo(headX + (isActivePlayer ? 18 : 12), headY - 1);
                    earLeft.closePath();
                    g2d.setColor(COLOR_PIG_PINK);
                    g2d.fill(earLeft);
                    g2d.setColor(new Color(60, 40, 40, 50));
                    g2d.draw(earLeft);

                    // 9. Matschflecken zeichnen falls das Schwein eine Drecksau ist
                    if (sau.isDirty()) {
                        g2d.setColor(COLOR_MUD);
                        // Wir zeichnen mehrere detaillierte Matschflecken auf den Körper
                        g2d.fillOval(pigX + (isActivePlayer ? 12 : 8), pigY + (isActivePlayer ? 18 : 12), isActivePlayer ? 16 : 10, isActivePlayer ? 12 : 8);
                        g2d.fillOval(pigX + (isActivePlayer ? 40 : 28), pigY + (isActivePlayer ? 8 : 6), isActivePlayer ? 20 : 14, isActivePlayer ? 10 : 7);
                        g2d.fillOval(pigX + (isActivePlayer ? 30 : 22), pigY + (isActivePlayer ? 30 : 20), isActivePlayer ? 14 : 9, isActivePlayer ? 10 : 7);
                        g2d.fillOval(headX + (isActivePlayer ? 8 : 6), headY + (isActivePlayer ? 22 : 15), isActivePlayer ? 10 : 7, isActivePlayer ? 7 : 5);
                    } else {
                        // Sauberes Schweinchen glänzt mit kleinen, eleganten goldenen Funken
                        g2d.setColor(COLOR_GOLD);
                        g2d.setFont(new Font("SansSerif", Font.BOLD, isActivePlayer ? 14 : 10));
                        g2d.drawString("+", pigX - 6, pigY + 12);
                        g2d.drawString("+", pigX + pigW + 2, pigY + pigH / 2);
                        g2d.drawString("+", headX + headW / 2, headY - 8);
                    }

                    // 10. Gemütlichen Landhaus-Stall zeichnen falls vorhanden
                    if (sau.hasStall()) {
                        // Wir färben das Holz hellbraun
                        g2d.setColor(COLOR_WOOD);
                        int pWidth = isActivePlayer ? 10 : 7;
                        int sX_Left = pigX - (isActivePlayer ? 12 : 8);
                        int sX_Right = pigX + pigW + (isActivePlayer ? 4 : 3);
                        int sY = headY - (isActivePlayer ? 8 : 5);
                        int sHeight = h - sY - 15;
                        
                        // Linker Pfosten mit Holzmaserungs-Linien
                        g2d.fillRect(sX_Left, sY, pWidth, sHeight);
                        // Rechter Pfosten
                        g2d.fillRect(sX_Right, sY, pWidth, sHeight);
                        
                        // Pfostenumrandung
                        g2d.setColor(COLOR_DARK_WOOD);
                        g2d.drawRect(sX_Left, sY, pWidth, sHeight);
                        g2d.drawRect(sX_Right, sY, pWidth, sHeight);

                        // Rustikales rotes Stalldach drüber
                        g2d.setColor(COLOR_STABLE_RED);
                        Path2D roof = new Path2D.Double();
                        roof.moveTo(sX_Left - (isActivePlayer ? 8 : 5), sY + (isActivePlayer ? 8 : 5));
                        roof.lineTo((sX_Left + sX_Right + pWidth) / 2, sY - (isActivePlayer ? 18 : 12));
                        roof.lineTo(sX_Right + pWidth + (isActivePlayer ? 8 : 5), sY + (isActivePlayer ? 8 : 5));
                        roof.closePath();
                        g2d.fill(roof);
                        g2d.setColor(COLOR_DARK_WOOD);
                        g2d.draw(roof);

                        // Eine gemütliche warm leuchtende Laterne am Pfosten zeichnen
                        int latX = sX_Left + (isActivePlayer ? 15 : 10);
                        int latY = sY + (isActivePlayer ? 12 : 8);
                        // Laternenband
                        g2d.setColor(Color.DARK_GRAY);
                        g2d.drawLine(sX_Left + pWidth, sY + 8, latX, latY);
                        // Gelber warmer Lichtkreis (Glow-Effekt)
                        g2d.setColor(new Color(255, 235, 59, 100));
                        g2d.fillOval(latX - 6, latY - 6, 12, 12);
                        // Gelber Glaskörper
                        g2d.setColor(new Color(255, 235, 59));
                        g2d.fillOval(latX - 3, latY - 3, 6, 6);

                        // Blitzableiter mit coolem blauem Glüheffekt zeichnen
                        if (sau.blitzSchutz()) {
                            int rX = (sX_Left + sX_Right + pWidth) / 2;
                            int rY = sY - (isActivePlayer ? 18 : 12);
                            int rodH = isActivePlayer ? 22 : 15;
                            
                            // Blaues leuchtendes Energiefeld um den Stab
                            g2d.setColor(new Color(33, 150, 243, 80));
                            g2d.setStroke(new BasicStroke(4.0f));
                            g2d.drawLine(rX, rY, rX, rY - rodH);
                            
                            // Metallstange
                            g2d.setColor(Color.LIGHT_GRAY);
                            g2d.setStroke(new BasicStroke(2.0f));
                            g2d.drawLine(rX, rY, rX, rY - rodH);
                            g2d.setStroke(new BasicStroke(1.0f));
                            
                            // Goldene Kugel an der Spitze
                            g2d.setColor(COLOR_GOLD);
                            g2d.fillOval(rX - 3, rY - rodH - 3, 6, 6);
                        }

                        // Bauer-ärgere-dich (Vernageltes Holzkreuz mit Nieten) zeichnen
                        if (sau.bauerSchutz()) {
                            g2d.setColor(new Color(141, 110, 99));
                            g2d.setStroke(new BasicStroke(isActivePlayer ? 7.0f : 4.0f));
                            
                            // Zwei fette vernagelte Holzbohlen als X
                            g2d.drawLine(sX_Left, h - 22, sX_Right + pWidth, sY + 12);
                            g2d.drawLine(sX_Left, sY + 12, sX_Right + pWidth, h - 22);
                            
                            // Dünne schwarze Ränder
                            g2d.setColor(COLOR_DARK_WOOD);
                            g2d.setStroke(new BasicStroke(1.0f));
                            g2d.draw(new Line2D.Double(sX_Left, h - 22, sX_Right + pWidth, sY + 12));
                            g2d.draw(new Line2D.Double(sX_Left, sY + 12, sX_Right + pWidth, h - 22));

                            // Kleine Nieten/Nägel an den Enden der Bretter zeichnen (silberne Punkte)
                            g2d.setColor(Color.LIGHT_GRAY);
                            g2d.fillOval(sX_Left + 2, h - 24, 3, 3);
                            g2d.fillOval(sX_Right + pWidth - 5, h - 24, 3, 3);
                            g2d.fillOval(sX_Left + 2, sY + 12, 3, 3);
                            g2d.fillOval(sX_Right + pWidth - 5, sY + 12, 3, 3);
                        }
                    }

                    // 11. Status-Beschriftung auf dem Wiesenboden
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("SansSerif", Font.BOLD, isActivePlayer ? 11 : 9));
                    String statusText = (index + 1) + ". " + (sau.isDirty() ? "Drecksau" : "Sauber");
                    FontMetrics fm = g2d.getFontMetrics();
                    g2d.drawString(statusText, (w - fm.stringWidth(statusText)) / 2, h - 4);

                    g2d.dispose();
                }
            };
            pigCanvas.setOpaque(false);
            pigsRow.add(pigCanvas);
        }

        panel.add(pigsRow, BorderLayout.CENTER);
        return panel;
    }

    // Diese Funktion baut das Panel für eine einzelne Handkarte auf
    private JPanel createCardPanel(Card card, int cardIndex) {
        JPanel cardPanel = new JPanel() {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                // Basisgradient je nach Kartentyp bestimmen (Harmonische Premium-Farben)
                GradientPaint gp;
                Color topColor;
                Color bottomColor;

                switch (card.getName()) {
                    case "Matsch":
                        topColor = new Color(139, 105, 80);
                        bottomColor = new Color(78, 52, 46);
                        break;
                    case "Regen":
                        topColor = new Color(79, 195, 247);
                        bottomColor = new Color(21, 101, 192);
                        break;
                    case "Stall":
                        topColor = new Color(255, 202, 40);
                        bottomColor = new Color(245, 127, 23);
                        break;
                    case "Blitz":
                        topColor = new Color(186, 104, 200);
                        bottomColor = new Color(74, 20, 140);
                        break;
                    case "Blitzableiter":
                        topColor = new Color(176, 190, 197);
                        bottomColor = new Color(55, 71, 79);
                        break;
                    case "Bauer schrubbt":
                        topColor = new Color(255, 167, 38);
                        bottomColor = new Color(230, 81, 0);
                        break;
                    case "Bauer aerger dich":
                        topColor = new Color(239, 83, 80);
                        bottomColor = new Color(183, 28, 28);
                        break;
                    case "Schlammvulkan":
                        topColor = new Color(255, 112, 67);
                        bottomColor = new Color(191, 54, 12);
                        break;
                    default:
                        topColor = Color.LIGHT_GRAY;
                        bottomColor = Color.GRAY;
                }

                // Wir zeichnen einen sanften Schlagschatten unter die Karte für ein plastisches Aussehen
                g2d.setColor(new Color(0, 0, 0, 45));
                g2d.fillRoundRect(3, 3, w - 3, h - 3, 18, 18);

                // Elegant abgerundete Karte mit dem Farbverlauf bemalen
                gp = new GradientPaint(0, 0, topColor, 0, h, bottomColor);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, w - 3, h - 3, 18, 18);

                // Rahmen zeichnen
                if (selectedHandCardIndex == cardIndex) {
                    // Strahlend goldener dicker Auswahlrahmen
                    g2d.setColor(COLOR_GOLD);
                    g2d.setStroke(new BasicStroke(3.0f));
                    g2d.drawRoundRect(1, 1, w - 5, h - 5, 18, 18);
                    
                    // Goldenes Glühen im Hintergrund
                    g2d.setColor(new Color(255, 215, 0, 30));
                    g2d.fillRoundRect(1, 1, w - 5, h - 5, 18, 18);
                } else if (isHovered) {
                    // Feiner weißer Rahmen bei Schwebung
                    g2d.setColor(Color.WHITE);
                    g2d.setStroke(new BasicStroke(2.0f));
                    g2d.drawRoundRect(0, 0, w - 4, h - 4, 18, 18);
                } else {
                    // Dezent dunkler feiner Rahmen
                    g2d.setColor(new Color(0, 0, 0, 40));
                    g2d.drawRoundRect(0, 0, w - 4, h - 4, 18, 18);
                }
                g2d.setStroke(new BasicStroke(1.0f));

                // Name der Spielkarte
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
                FontMetrics fmName = g2d.getFontMetrics();
                g2d.drawString(card.getName(), (w - 3 - fmName.stringWidth(card.getName())) / 2, 28);

                // Ein hübscher kreisförmiger Hintergrund für das Symbol in der Mitte der Karte
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillOval(w / 2 - 24, 45, 45, 45);
                g2d.setColor(new Color(255, 255, 255, 50));
                g2d.drawOval(w / 2 - 24, 45, 45, 45);

                // Ein kurzes feines Wortsymbol zeichnen
                g2d.setFont(new Font("SansSerif", Font.BOLD, 10));
                g2d.setColor(Color.WHITE);
                String symbol = "";
                switch (card.getName()) {
                    case "Matsch": symbol = "MATSCH"; break;
                    case "Regen": symbol = "REGEN"; break;
                    case "Stall": symbol = "STALL"; break;
                    case "Blitz": symbol = "BLITZ"; break;
                    case "Blitzableiter": symbol = "SCHUTZ"; break;
                    case "Bauer schrubbt": symbol = "WASCHEN"; break;
                    case "Bauer aerger dich": symbol = "RIEGEL"; break;
                    case "Schlammvulkan": symbol = "VULKAN"; break;
                }
                FontMetrics fmSymbol = g2d.getFontMetrics();
                g2d.drawString(symbol, (w - 3 - fmSymbol.stringWidth(symbol)) / 2, 71);

                // Beschreibungstext (laienfreundlich und sauber formatiert)
                g2d.setFont(new Font("SansSerif", Font.PLAIN, 10));
                g2d.setColor(new Color(245, 245, 245));
                
                String desc = card.getBeschreibung();
                String[] words = desc.split(" ");
                StringBuilder line = new StringBuilder();
                int yPos = 112;
                
                for (String word : words) {
                    if (g2d.getFontMetrics().stringWidth(line.toString() + " " + word) < w - 24) {
                        line.append(" ").append(word);
                    } else {
                        g2d.drawString(line.toString().trim(), 14, yPos);
                        yPos += 14;
                        line = new StringBuilder(word);
                    }
                }
                if (line.length() > 0) {
                    g2d.drawString(line.toString().trim(), 14, yPos);
                }

                g2d.dispose();
            }
        };

        cardPanel.setPreferredSize(new Dimension(160, 185));
        cardPanel.setOpaque(false);
        
        // Maus-Aktionen für die Handkarte
        cardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                // Karte schwebt sanft nach oben
                cardPanel.setBounds(cardPanel.getX(), cardPanel.getY() - 8, cardPanel.getWidth(), cardPanel.getHeight());
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cardPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedHandCardIndex == cardIndex) {
                    selectedHandCard = null;
                    selectedHandCardIndex = -1;
                } else {
                    selectedHandCard = card;
                    selectedHandCardIndex = cardIndex;
                }
                updateUIState();
            }
        });

        return cardPanel;
    }

    // Diese Funktion steuert das freiwillige Abwerfen oder das globale Ausspielen von Ereignissen
    private void handleDiscardOrGlobalPlay() {
        if (selectedHandCard == null || game == null) return;

        Card card = selectedHandCard;
        String name = card.getName();

        // 1. Fall: Globales Ereignis spielen
        if (name.equals("Regen") || name.equals("Schlammvulkan")) {
            try {
                game.spieleZug(card, -1, null);
                
                selectedHandCard = null;
                selectedHandCardIndex = -1;

                if (game.isGameOver()) {
                    showVictoryScreen();
                } else {
                    showPassScreen();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Fehler: " + ex.getMessage(), "Ereignis-Fehler", JOptionPane.ERROR_MESSAGE);
            }
        } 
        // 2. Fall: Karte abwerfen
        else {
            int reply = JOptionPane.showConfirmDialog(
                this, 
                "Moechtest du die Karte " + name + " wirklich ungenutzt auf den Ablagestapel werfen?", 
                "Karte abwerfen", 
                JOptionPane.YES_NO_OPTION
            );
            
            if (reply == JOptionPane.YES_OPTION) {
                game.karteFreiwilligAbwerfen(card);
                
                selectedHandCard = null;
                selectedHandCardIndex = -1;
                
                showPassScreen();
            }
        }
    }

    // Diese Funktion wickelt das Abwerfen der blockierten Hand ab
    private void handleDiscardEntireHand() {
        if (game == null) return;
        
        int reply = JOptionPane.showConfirmDialog(
            this,
            "Bestaetigst du, dass du keinen gueltigen Spielzug machen kannst?\n(Deine Karten werden den anderen gezeigt, abgeworfen und du ziehst 3 neue Karten).",
            "Hand komplett abwerfen",
            JOptionPane.YES_NO_OPTION
        );

        if (reply == JOptionPane.YES_OPTION) {
            game.handAbwerfenUndNeuZiehen();
            
            selectedHandCard = null;
            selectedHandCardIndex = -1;
            
            showPassScreen();
        }
    }

    // Diese Funktion zeigt den Sichtschutz für den Spielerwechsel an
    private void showPassScreen() {
        Component[] comps = passPanel.getComponents();
        for (Component c : comps) {
            // Wir suchen im Glaspanel nach dem Hinweistext
            if (c instanceof JPanel) {
                Component[] innerComps = ((JPanel) c).getComponents();
                for (Component ic : innerComps) {
                    if (ic instanceof JLabel && ((JLabel) ic).getText().startsWith("Bereit machen")) {
                        ((JLabel) ic).setText("Bereit machen, " + game.getActivePlayer().getUsername() + "!");
                    }
                }
            }
        }
        
        cardLayout.show(mainContainer, "PASS");
    }

    // Diese Funktion schaltet auf den Siegesbildschirm um
    private void showVictoryScreen() {
        Component[] comps = victoryPanel.getComponents();
        for (Component c : comps) {
            if (c instanceof JPanel) {
                Component[] innerComps = ((JPanel) c).getComponents();
                for (Component ic : innerComps) {
                    if (ic instanceof JLabel && ((JLabel) ic).getText().contains("gewonnen")) {
                        ((JLabel) ic).setText("Sieg! " + game.getWinner().getUsername() + " HAT GEWONNEN!");
                    }
                }
            }
        }
        
        cardLayout.show(mainContainer, "VICTORY");
    }
}
