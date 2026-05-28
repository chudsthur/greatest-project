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
    private static final Color COLOR_GRASS = new Color(76, 154, 82);
    // Hier definieren wir ein warmes Schlammbraun
    private static final Color COLOR_MUD = new Color(139, 90, 43);
    // Hier definieren wir ein helles Holzbraun
    private static final Color COLOR_WOOD = new Color(193, 154, 107);
    // Hier definieren wir ein dunkles Holzbraun
    private static final Color COLOR_DARK_WOOD = new Color(101, 67, 33);
    // Hier definieren wir ein gemütliches Stallrot
    private static final Color COLOR_STABLE_RED = new Color(192, 57, 43);
    // Hier definieren wir ein niedliches Schweinerosa
    private static final Color COLOR_PIG_PINK = new Color(255, 192, 203);
    // Hier definieren wir ein dunkleres Rüsselrosa
    private static final Color COLOR_PIG_DARK_PINK = new Color(244, 143, 177);
    // Hier definieren wir ein glänzendes Goldgelb
    private static final Color COLOR_GOLD = new Color(241, 196, 15);

    // Das ist der Bauplan für unser Spielfenster
    public GUIGame() {
        // Wir setzen den Fenstertitel fest
        setTitle("DRECKSAU - Das Kartenspiel");
        // Wir stellen ein dass das Programm komplett beendet wird wenn das Fenster geschlossen wird
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Wir legen die Standardgröße des Fensters fest
        setSize(1100, 800);
        // Wir legen die Mindestgröße des Fensters fest
        setMinimumSize(new Dimension(1000, 750));
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
        // Wir erstellen die Ansicht und bemalen den Hintergrund mit einem Farbverlauf
        setupPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                // Wir rufen die Standard-Zeichenfunktion auf
                super.paintComponent(g);
                // Wir holen uns ein zweidimensionales Zeichenwerkzeug
                Graphics2D g2d = (Graphics2D) g.create();
                // Wir erstellen einen Farbverlauf von Dunkelgrün zu Schlammbraun
                GradientPaint gp = new GradientPaint(0, 0, new Color(46, 125, 50), 0, getHeight(), new Color(141, 110, 99));
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
        gbc.insets = new Insets(15, 15, 15, 15);
        // Die Elemente sollen sich in der Breite anpassen
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Wir erstellen den großen Spieletitel
        JLabel titleLabel = new JLabel("DRECKSAU", JLabel.CENTER);
        // Wir setzen eine große und fette Schriftart fest
        titleLabel.setFont(new Font("Outfit", Font.BOLD, 72));
        // Wir färben die Schrift weiß
        titleLabel.setForeground(Color.WHITE);
        // Wir fügen einen leeren Abstand nach unten hinzu
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Wir setzen die Position in der Tabelle fest
        gbc.gridx = 0;
        gbc.gridy = 0;
        // Der Titel soll sich über zwei Spalten erstrecken
        gbc.gridwidth = 2;
        // Wir fügen den Titel zur Ansicht hinzu
        setupPanel.add(titleLabel, gbc);

        // Wir erstellen einen Untertitel
        JLabel subtitleLabel = new JLabel("Das schlammigste Kartenspiel fuer 2-4 Spieler", JLabel.CENTER);
        // Wir setzen eine kursive Schriftart fest
        subtitleLabel.setFont(new Font("Inter", Font.ITALIC, 20));
        // Wir färben den Text hellgrün
        subtitleLabel.setForeground(new Color(230, 240, 230));
        // Wir gehen in die nächste Zeile der Tabelle
        gbc.gridy = 1;
        // Wir fügen den Untertitel hinzu
        setupPanel.add(subtitleLabel, gbc);

        // Wir erstellen eine Zeile für die Spieleranzahl
        JPanel selectionRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        // Wir machen den Hintergrund dieser Zeile unsichtbar
        selectionRow.setOpaque(false);
        // Wir erstellen den beschreibenden Text
        JLabel countLabel = new JLabel("Spieleranzahl: ");
        // Wir setzen eine fette Schrift fest
        countLabel.setFont(new Font("Inter", Font.BOLD, 18));
        // Wir färben den Text weiß
        countLabel.setForeground(Color.WHITE);
        // Wir fügen den Text zur Zeile hinzu
        selectionRow.add(countLabel);

        // Wir legen die Auswahlmöglichkeiten für zwei, drei oder vier Spieler fest
        Integer[] choices = { 2, 3, 4 };
        // Wir erstellen ein Auswahlmenü mit diesen Zahlen
        JComboBox<Integer> countCombo = new JComboBox<>(choices);
        // Wir setzen die Schriftart fest
        countCombo.setFont(new Font("Inter", Font.PLAIN, 16));
        // Wir wählen standardmäßig drei Spieler aus
        countCombo.setSelectedItem(3);
        // Wir fügen das Auswahlmenü zur Zeile hinzu
        selectionRow.add(countCombo);
        
        // Wir gehen in die nächste Zeile der Tabelle
        gbc.gridy = 2;
        // Wir fügen die Auswahlzeile hinzu
        setupPanel.add(selectionRow, gbc);

        // Wir erstellen einen Kasten für die Namenseingabefelder
        JPanel namesPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        // Wir machen den Kasten unsichtbar so dass der Hintergrund durchscheint
        namesPanel.setOpaque(false);
        // Wir legen die gewünschte Größe des Kastens fest
        namesPanel.setPreferredSize(new Dimension(400, 160));

        // Wir erstellen Listen für die Beschriftungen und die Eingabefelder
        JLabel[] playerLabels = new JLabel[4];
        JTextField[] playerFields = new JTextField[4];

        // Wir erstellen nacheinander vier Eingabefelder
        for (int i = 0; i < 4; i++) {
            // Wir erstellen die Beschriftung für den jeweiligen Spieler
            playerLabels[i] = new JLabel("Spieler " + (i + 1) + " Name:", JLabel.RIGHT);
            // Wir setzen eine fette Schrift fest
            playerLabels[i].setFont(new Font("Inter", Font.BOLD, 16));
            // Wir färben die Schrift weiß
            playerLabels[i].setForeground(Color.WHITE);
            
            // Wir erstellen das eigentliche Eingabefeld mit einem Standardnamen
            playerFields[i] = new JTextField("Spieler " + (i + 1));
            // Wir setzen die Schriftart fest
            playerFields[i].setFont(new Font("Inter", Font.PLAIN, 16));
            // Wir legen die Standardgröße fest
            playerFields[i].setPreferredSize(new Dimension(150, 30));

            // Wir fügen die Beschriftung zum Kasten hinzu
            namesPanel.add(playerLabels[i]);
            // Wir fügen das Eingabefeld zum Kasten hinzu
            namesPanel.add(playerFields[i]);
        }

        // Da standardmäßig drei Spieler ausgewählt sind machen wir das vierte Feld unsichtbar
        playerLabels[3].setVisible(false);
        playerFields[3].setVisible(false);

        // Wir gehen in die nächste Zeile der Tabelle
        gbc.gridy = 3;
        // Wir fügen den gesamten Eingabekasten hinzu
        setupPanel.add(namesPanel, gbc);

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
            // Wir berechnen die Anordnung der Ansicht neu
            setupPanel.revalidate();
            // Wir zeichnen die Ansicht neu
            setupPanel.repaint();
        });

        // Wir erstellen den Startknopf
        JButton startBtn = new JButton("Spiel starten!");
        // Wir setzen eine große und fette Schrift fest
        startBtn.setFont(new Font("Inter", Font.BOLD, 22));
        // Wir färben den Knopf orange
        startBtn.setBackground(new Color(255, 112, 67));
        // Wir färben den Text weiß
        startBtn.setForeground(Color.WHITE);
        // Wir entfernen den unschönen Fokusrahmen um den Text
        startBtn.setFocusPainted(false);
        // Wir erstellen einen weißen Rahmen mit Abständen im Knopf
        startBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2, true),
            BorderFactory.createEmptyBorder(10, 30, 10, 30)
        ));
        // Der Mauszeiger soll sich beim Drüberfahren in eine zeigende Hand verwandeln
        startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
            
            // Wir leeren die Liste der Protokollnachrichten im Anzeigefeld
            logListModel.clear();
            // Wir tragen alle bisherigen Startmeldungen in das Anzeigefeld ein
            for (String log : game.getActionLog()) {
                // Wir fügen die Nachricht hinzu
                logListModel.addElement(log);
            }

            // Wir bauen die eigentliche Spieloberfläche komplett auf
            createGamePanel();
            // Wir fügen die Spieloberfläche zum Hauptcontainer hinzu
            mainContainer.add(gamePanel, "GAME");
            
            // Wir wechseln die Ansicht auf das Spielfeld
            cardLayout.show(mainContainer, "GAME");
        });

        // Wir gehen in die nächste Zeile der Tabelle
        gbc.gridy = 4;
        // Der Knopf soll sich über beide Spalten erstrecken
        gbc.gridwidth = 2;
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
                // Wir erstellen einen Verlauf von Dunkelgrau zu Schwarz
                GradientPaint gp = new GradientPaint(0, 0, new Color(50, 50, 50), 0, getHeight(), new Color(20, 20, 20));
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
        gbc.insets = new Insets(20, 20, 20, 20);

        // Wir erstellen ein großes Auge-Zeichen als Sichtschutz
        JLabel eyeLabel = new JLabel("Auge", JLabel.CENTER);
        // Wir setzen eine sehr große Schriftart fest
        eyeLabel.setFont(new Font("Inter", Font.BOLD, 120));
        // Wir färben das Wort goldgelb
        eyeLabel.setForeground(COLOR_GOLD);
        // Wir fügen den Text hinzu
        passPanel.add(eyeLabel, gbc);

        // Wir erstellen die Überschrift für den Spielerwechsel
        JLabel passTitle = new JLabel("SPIELERWECHSEL", JLabel.CENTER);
        // Wir setzen eine fette Schrift fest
        passTitle.setFont(new Font("Outfit", Font.BOLD, 48));
        // Wir färben den Text weiß
        passTitle.setForeground(Color.WHITE);
        // Wir gehen in die nächste Zeile der Tabelle
        gbc.gridy = 1;
        // Wir fügen die Überschrift hinzu
        passPanel.add(passTitle, gbc);

        // Wir erstellen den Hinweistext wer sich bereit machen soll
        JLabel nextPlayerText = new JLabel("Bereit machen, ...", JLabel.CENTER);
        // Wir setzen die Schriftart fest
        nextPlayerText.setFont(new Font("Inter", Font.PLAIN, 24));
        // Wir färben den Text hellgrau
        nextPlayerText.setForeground(new Color(200, 200, 200));
        // Wir gehen in die nächste Zeile
        gbc.gridy = 2;
        // Wir fügen den Text hinzu
        passPanel.add(nextPlayerText, gbc);

        // Wir erstellen den Knopf zum Aufdecken der Karten
        JButton showHandBtn = new JButton("Handkarten aufdecken");
        // Wir setzen die Schriftart fest
        showHandBtn.setFont(new Font("Inter", Font.BOLD, 20));
        // Wir färben den Knopf blau
        showHandBtn.setBackground(new Color(41, 128, 185));
        // Wir färben die Schrift weiß
        showHandBtn.setForeground(Color.WHITE);
        // Wir entfernen den unschönen Fokusrahmen
        showHandBtn.setFocusPainted(false);
        // Der Mauszeiger soll sich in eine Hand verwandeln
        showHandBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Wir fügen Abstände im Knopf hinzu
        showHandBtn.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

        // Wir reagieren auf den Klick um den Sichtschutz zu beenden
        showHandBtn.addActionListener(e -> {
            // Wir wechseln die Ansicht zurück auf das Spielfeld
            cardLayout.show(mainContainer, "GAME");
            // Wir bringen alle Anzeigen auf den allerneuesten Stand
            updateUIState();
        });
        // Wir gehen in die nächste Zeile
        gbc.gridy = 3;
        // Wir fügen den Knopf hinzu
        passPanel.add(showHandBtn, gbc);
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
                // Wir erstellen einen festlichen Farbverlauf von Orange zu Pink
                GradientPaint gp = new GradientPaint(0, 0, new Color(251, 140, 0), 0, getHeight(), new Color(216, 27, 96));
                // Wir laden den Verlauf
                g2d.setPaint(gp);
                // Wir bemalen die gesamte Fläche
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Wir schalten die Kantenglättung für schöne Formen ein
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Wir wählen ein leicht transparentes Weiß
                g2d.setColor(new Color(255, 255, 255, 40));
                // Wir zeichnen fünfzehn zufällig verteilte Konfetti-Punkte im Hintergrund
                for (int i = 0; i < 15; i++) {
                    // Wir würfeln eine zufällige Position in der Breite
                    int x = (int) (Math.random() * getWidth());
                    // Wir würfeln eine zufällige Position in der Höhe
                    int y = (int) (Math.random() * getHeight());
                    // Wir würfeln eine zufällige Größe für den Konfetti-Punkt
                    int size = 20 + (int) (Math.random() * 50);
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
        gbc.insets = new Insets(15, 15, 15, 15);

        // Wir erstellen eine Textbeschriftung für den Pokal
        JLabel crownLabel = new JLabel("POKAL", JLabel.CENTER);
        // Wir setzen eine riesige Schriftart fest
        crownLabel.setFont(new Font("Inter", Font.BOLD, 100));
        // Wir färben die Schrift weiß
        crownLabel.setForeground(Color.WHITE);
        // Wir fügen den Pokal hinzu
        victoryPanel.add(crownLabel, gbc);

        // Wir erstellen den großen Sieges-Titel
        JLabel winTitle = new JLabel("HERZLICHEN GLUECKWUNSCH!", JLabel.CENTER);
        // Wir setzen eine sehr große und fette Schrift fest
        winTitle.setFont(new Font("Outfit", Font.BOLD, 52));
        // Wir färben die Schrift weiß
        winTitle.setForeground(Color.WHITE);
        // Wir gehen in die nächste Zeile
        gbc.gridy = 1;
        // Wir fügen die Überschrift hinzu
        victoryPanel.add(winTitle, gbc);

        // Wir erstellen den Gewinner-Text
        JLabel winnerText = new JLabel("Spieler hat gewonnen!", JLabel.CENTER);
        // Wir setzen eine fette Schrift fest
        winnerText.setFont(new Font("Inter", Font.BOLD, 32));
        // Wir färben den Text goldgelb
        winnerText.setForeground(COLOR_GOLD);
        // Wir gehen in die nächste Zeile
        gbc.gridy = 2;
        // Wir fügen den Text hinzu
        victoryPanel.add(winnerText, gbc);

        // Wir erstellen eine kurze Erklärung
        JLabel descText = new JLabel("Alle Schweine sind glueckliche Drecksaeue geworden!", JLabel.CENTER);
        // Wir setzen die Schriftart fest
        descText.setFont(new Font("Inter", Font.PLAIN, 20));
        // Wir färben den Text weiß
        descText.setForeground(Color.WHITE);
        // Wir gehen in die nächste Zeile
        gbc.gridy = 3;
        // Wir fügen die Erklärung hinzu
        victoryPanel.add(descText, gbc);

        // Wir erstellen den Knopf um ein neues Spiel zu starten
        JButton restartBtn = new JButton("Neues Spiel starten");
        // Wir setzen eine fette Schrift fest
        restartBtn.setFont(new Font("Inter", Font.BOLD, 20));
        // Wir färben den Knopf dunkelgrün
        restartBtn.setBackground(new Color(46, 125, 50));
        // Wir färben die Schrift weiß
        restartBtn.setForeground(Color.WHITE);
        // Wir entfernen den Fokusrahmen
        restartBtn.setFocusPainted(false);
        // Der Mauszeiger soll sich in eine zeigende Hand verwandeln
        restartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Wir fügen Abstände im Knopf hinzu
        restartBtn.setBorder(BorderFactory.createEmptyBorder(15, 45, 15, 45));

        // Wir reagieren auf einen Klick auf den Neustartknopf
        restartBtn.addActionListener(e -> {
            // Wir wechseln die Ansicht zurück auf die Spielereingabe
            cardLayout.show(mainContainer, "SETUP");
        });
        // Wir gehen in die nächste Zeile
        gbc.gridy = 4;
        // Wir fügen den Knopf hinzu
        victoryPanel.add(restartBtn, gbc);
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
                // Wir erstellen einen Farbverlauf für frischen Rasen
                GradientPaint gp = new GradientPaint(0, 0, new Color(56, 124, 62), 0, getHeight(), new Color(86, 164, 92));
                // Wir laden den Verlauf
                g2d.setPaint(gp);
                // Wir bemalen die gesamte Wiese mit dem Grünverlauf
                g2d.fillRect(0, 0, getWidth(), getHeight());
                // Wir geben den Pinsel wieder frei
                g2d.dispose();
            }
        };
        // Wir nutzen eine Tabelle für die Wiese um Gegner und eigenen Spieler perfekt zu teilen
        pasturePanel.setLayout(new GridBagLayout());
        // Wir fügen die Wiese in die Mitte des Spielfelds ein
        gamePanel.add(pasturePanel, BorderLayout.CENTER);

        // Wir erstellen die Holz-Steuerungsleiste auf der rechten Seite
        createControlPanel();
        // Wir fügen die Leiste rechts an das Spielfeld an
        gamePanel.add(controlPanel, BorderLayout.EAST);

        // Wir erstellen einen Bereich ganz unten für die eigenen Karten
        JPanel bottomPanel = new JPanel(new BorderLayout());
        // Wir machen den Hintergrund unsichtbar damit die Wiese durchscheint
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

    // Diese Funktion baut die hölzerne Steuerungsleiste rechts auf
    private void createControlPanel() {
        // Wir bemalen die Leiste mit einer edlen Holztextur
        controlPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Wir rufen die Standard-Zeichenfunktion auf
                super.paintComponent(g);
                // Wir holen uns das zweidimensionale Zeichenwerkzeug
                Graphics2D g2d = (Graphics2D) g.create();
                // Wir schalten die Kantenglättung ein
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Wir erstellen einen Holzverlauf von Hellbraun zu Dunkelbraun
                GradientPaint gp = new GradientPaint(0, 0, new Color(139, 90, 43), getWidth(), 0, new Color(101, 67, 33));
                // Wir laden den Holzverlauf
                g2d.setPaint(gp);
                // Wir bemalen die gesamte Fläche mit dem Holzdesign
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Wir zeichnen eine feine weiße Begrenzungslinie zur Zierde
                g2d.setColor(new Color(255, 255, 255, 50));
                // Wir wählen eine feine Linienstärke von 1,5 Punkten
                g2d.setStroke(new BasicStroke(1.5f));
                // Wir zeichnen die Rahmenlinie im Kasten
                g2d.drawRect(5, 5, getWidth() - 10, getHeight() - 10);
                // Wir geben den Pinsel frei
                g2d.dispose();
            }
        };
        // Die Steuerungselemente sollen untereinander angeordnet werden
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        // Wir legen die Breite der Leiste auf 320 Bildpunkte fest
        controlPanel.setPreferredSize(new Dimension(320, 0));
        // Wir legen einen inneren Abstand um die Elemente fest
        controlPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Wir erstellen die Überschrift für die Steuerungsleiste
        JLabel labelSpielfeld = new JLabel("SCHEUER UND STAPEL", JLabel.CENTER);
        // Wir setzen eine fette Schrift fest
        labelSpielfeld.setFont(new Font("Outfit", Font.BOLD, 22));
        // Wir färben den Text weiß
        labelSpielfeld.setForeground(Color.WHITE);
        // Wir zentrieren die Überschrift mittig in der Leiste
        labelSpielfeld.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Wir fügen die Überschrift hinzu
        controlPanel.add(labelSpielfeld);
        // Wir fügen einen leeren Abstand nach unten ein
        controlPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Wir erstellen das Textfeld für Informationen zum Stapel
        deckInfoLabel = new JLabel("Karten im Deck: --", JLabel.CENTER);
        // Wir setzen eine fette Schrift fest
        deckInfoLabel.setFont(new Font("Inter", Font.BOLD, 15));
        // Wir färben den Text beige
        deckInfoLabel.setForeground(new Color(245, 242, 235));
        // Wir zentrieren den Text mittig
        deckInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Wir fügen das Textfeld hinzu
        controlPanel.add(deckInfoLabel);
        // Wir fügen einen leeren Abstand ein
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Wir erstellen den Ablagestapel als visuelle Kiste
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
                g2d.setColor(new Color(255, 255, 255, 30));
                // Wir bemalen die Kiste mit abgerundeten Ecken
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Wir wählen eine weiße Schriftfarbe
                g2d.setColor(Color.WHITE);
                // Wir setzen eine fette Schrift fest
                g2d.setFont(new Font("Inter", Font.BOLD, 14));
                // Wir erstellen einen Standardtext für einen leeren Ablagestapel
                String text = "Ablagestapel leer";
                // Wenn das Spiel läuft und Karten auf dem Stapel liegen
                if (game != null && game.getDeck().discardPileSize() > 0) {
                    // Wir ändern den Text
                    text = "Oben auf Ablagestapel";
                }
                // Wir holen uns ein Messwerkzeug für Schriftzeichen
                FontMetrics fm = g2d.getFontMetrics();
                // Wir zeichnen den Text genau zentriert in die Kiste
                g2d.drawString(text, (getWidth() - fm.stringWidth(text)) / 2, 25);
                
                // Wir geben das Zeichenwerkzeug frei
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
        playGlobalButton.setFont(new Font("Inter", Font.BOLD, 13));
        // Wir färben den Knopf blau
        playGlobalButton.setBackground(new Color(41, 128, 185));
        // Wir färben die Schrift weiß
        playGlobalButton.setForeground(Color.WHITE);
        // Wir entfernen den Fokusrahmen
        playGlobalButton.setFocusPainted(false);
        // Der Knopf soll zu Beginn unsichtbar sein da noch keine Karte ausgewählt ist
        playGlobalButton.setVisible(false);
        // Wir reagieren auf einen Klick auf diesen Knopf
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
        logTitle.setFont(new Font("Outfit", Font.BOLD, 16));
        // Wir färben den Text beige
        logTitle.setForeground(new Color(245, 242, 235));
        // Wir zentrieren die Überschrift
        logTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Wir fügen die Überschrift hinzu
        controlPanel.add(logTitle);
        // Wir fügen einen kleinen Abstand ein
        controlPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Wir erstellen eine neue Liste für die Spielnachrichten
        logListModel = new DefaultListModel<>();
        // Wir erstellen das Anzeigefeld und verknüpfen es mit der Liste
        logList = new JList<>(logListModel);
        // Wir setzen eine gut lesbare Schriftart mit fester Breite fest
        logList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        // Wir färben den Hintergrund dunkelbraun
        logList.setBackground(new Color(44, 34, 24));
        // Wir färben den Text beige
        logList.setForeground(new Color(230, 220, 200));
        // Wir färben die Hintergrundfarbe für ausgewählte Zeilen dunkelbraun
        logList.setSelectionBackground(new Color(44, 34, 24));
        // Wir färben die Schriftfarbe für ausgewählte Zeilen beige
        logList.setSelectionForeground(new Color(230, 220, 200));
        
        // Wir erstellen eine Scrollbox falls es sehr viele Nachrichten gibt
        JScrollPane logScroll = new JScrollPane(logList);
        // Wir zeichnen eine feine dunkle Umrandung um die Scrollbox
        logScroll.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,80), 2));
        // Wir begrenzen die Größe der Scrollbox
        logScroll.setMaximumSize(new Dimension(280, 220));
        // Wir legen die Standardgröße fest
        logScroll.setPreferredSize(new Dimension(280, 220));
        // Wir fügen die Scrollbox zur Steuerungsleiste hinzu
        controlPanel.add(logScroll);
        // Wir fügen einen leeren Abstand hinzu
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Wir erstellen den Notfallknopf zum Abwerfen der gesamten Hand
        discardEntireHandButton = new JButton("Hand abwerfen und neu ziehen");
        // Wir setzen die Schriftart fest
        discardEntireHandButton.setFont(new Font("Inter", Font.BOLD, 14));
        // Wir färben den Knopf dunkelrot
        discardEntireHandButton.setBackground(new Color(192, 57, 43));
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
        // Der Knopf ist zu Beginn gesperrt und wird erst aktiv wenn keine Züge mehr möglich sind
        discardEntireHandButton.setEnabled(false);
        // Wir reagieren auf einen Klick auf diesen Knopf
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
            discardEntireHandButton.setBackground(new Color(231, 76, 60));
        // Wenn der Spieler noch normal legen kann
        } else {
            // Wir färben den Knopf dezent dunkelrot
            discardEntireHandButton.setBackground(new Color(120, 40, 30));
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
                playGlobalButton.setBackground(new Color(39, 174, 96));
            // Bei allen anderen Karten handelt es sich um ein normales Abwerfen
            } else {
                // Wir ändern den Text auf ungenutzt abwerfen
                playGlobalButton.setText("Ungenutzt abwerfen");
                // Wir färben den Knopf grau
                playGlobalButton.setBackground(new Color(127, 140, 141));
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
        gbc.insets = new Insets(10, 10, 10, 10);
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
        JPanel opponentsRow = new JPanel(new GridLayout(1, opponents.size(), 15, 0));
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
                g2d.setColor(new Color(101, 67, 33, 180));
                // Wir wählen eine fette Linienstärke von 4 Punkten
                g2d.setStroke(new BasicStroke(4.0f));
                // Wir zeichnen eine Querlinie als Holzzaun
                g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
                // Wir geben das Zeichenwerkzeug frei
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
        nameLabel.setFont(new Font("Outfit", Font.BOLD, isActivePlayer ? 22 : 16));
        // Wir färben die Schrift weiß
        nameLabel.setForeground(Color.WHITE);
        // Wir fügen einen leeren Abstand nach unten hinzu
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        // Wir fügen die Namensbeschriftung oben im Panel ein
        panel.add(nameLabel, BorderLayout.NORTH);

        // Wir erstellen eine Reihe für die Schweine des Spielers
        JPanel pigsRow = new JPanel(new GridLayout(1, player.getSchweine().size(), isActivePlayer ? 15 : 8));
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
                                            // Wir blenden den Sichtschutz ein damit der nächste Spieler seine Hand nicht sieht
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
                        g2d.setColor(new Color(255, 255, 255, 60));
                        g2d.fillRoundRect(0, 0, w, h, 20, 20);
                        // Wir zeichnen einen glänzenden goldenen Rahmen um die Box
                        g2d.setColor(COLOR_GOLD);
                        // Wir wählen eine fette Linienstärke von 3 Punkten
                        g2d.setStroke(new BasicStroke(3.0f));
                        // Wir zeichnen den Umrandungsrahmen
                        g2d.drawRoundRect(0, 0, w - 1, h - 1, 20, 20);
                    // Wenn die Maus nicht drüber ist
                    } else {
                        // Wir zeichnen eine dezente dunkle Hintergrundbox
                        g2d.setColor(new Color(0, 0, 0, 30));
                        g2d.fillRoundRect(0, 0, w, h, 20, 20);
                    }

                    // Wir berechnen die Größe des Schweinchens (für den eigenen Spieler zeichnen wir es größer)
                    int pigW = isActivePlayer ? 100 : 70;
                    int pigH = isActivePlayer ? 70 : 50;
                    // Wir platzieren das Schweinchen zentriert in der Box
                    int pigX = (w - pigW) / 2;
                    int pigY = h - pigH - (isActivePlayer ? 20 : 10);

                    // A) Körper des Schweinchens (wir zeichnen ein rosa Oval)
                    g2d.setColor(COLOR_PIG_PINK);
                    g2d.fillOval(pigX, pigY, pigW, pigH);
                    // Wir zeichnen eine feine dunkle Umrandung um den Körper
                    g2d.setColor(new Color(0, 0, 0, 40));
                    g2d.drawOval(pigX, pigY, pigW, pigH);

                    // B) Kopf des Schweinchens (wir zeichnen ein kleineres rosa Oval vorne rechts)
                    int headW = isActivePlayer ? 50 : 35;
                    int headH = isActivePlayer ? 50 : 35;
                    int headX = pigX + pigW - (isActivePlayer ? 35 : 25);
                    int headY = pigY - (isActivePlayer ? 15 : 10);
                    g2d.setColor(COLOR_PIG_PINK);
                    g2d.fillOval(headX, headY, headW, headH);
                    // Wir zeichnen eine feine dunkle Umrandung um den Kopf
                    g2d.setColor(new Color(0, 0, 0, 40));
                    g2d.drawOval(headX, headY, headW, headH);

                    // C) Ruessel des Schweinchens (wir zeichnen ein flaches dunkleres Oval)
                    int snoutW = isActivePlayer ? 20 : 14;
                    int snoutH = isActivePlayer ? 14 : 10;
                    int snoutX = headX + headW - (isActivePlayer ? 12 : 8);
                    int snoutY = headY + (headH - snoutH) / 2;
                    g2d.setColor(COLOR_PIG_DARK_PINK);
                    g2d.fillOval(snoutX, snoutY, snoutW, snoutH);
                    // Wir umranden den Rüssel
                    g2d.setColor(new Color(0, 0, 0, 40));
                    g2d.drawOval(snoutX, snoutY, snoutW, snoutH);
                    // Wir zeichnen zwei kleine Punkte als Rüssellöcher
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.fillOval(snoutX + (isActivePlayer ? 5 : 3), snoutY + (isActivePlayer ? 4 : 3), isActivePlayer ? 3 : 2, isActivePlayer ? 3 : 2);
                    g2d.fillOval(snoutX + (isActivePlayer ? 12 : 8), snoutY + (isActivePlayer ? 4 : 3), isActivePlayer ? 3 : 2, isActivePlayer ? 3 : 2);

                    // D) Augen des Schweinchens (wir zeichnen zwei weiße Kreise)
                    g2d.setColor(Color.WHITE);
                    int eyeSize = isActivePlayer ? 8 : 6;
                    int eyeX = headX + (isActivePlayer ? 18 : 12);
                    int eyeY = headY + (isActivePlayer ? 12 : 8);
                    g2d.fillOval(eyeX, eyeY, eyeSize, eyeSize);
                    g2d.fillOval(eyeX + (isActivePlayer ? 12 : 8), eyeY, eyeSize, eyeSize);
                    
                    // Wir zeichnen zwei kleine schwarze Pupillen
                    g2d.setColor(Color.BLACK);
                    g2d.fillOval(eyeX + (isActivePlayer ? 3 : 2), eyeY + (isActivePlayer ? 2 : 1), eyeSize / 2, eyeSize / 2);
                    g2d.fillOval(eyeX + (isActivePlayer ? 15 : 10), eyeY + (isActivePlayer ? 2 : 1), eyeSize / 2, eyeSize / 2);

                    // E) Linkes Ohr des Schweinchens (wir zeichnen ein kleines Dreieck)
                    Path2D earLeft = new Path2D.Double();
                    earLeft.moveTo(headX + (isActivePlayer ? 10 : 7), headY + 5);
                    earLeft.lineTo(headX + (isActivePlayer ? 5 : 3), headY - (isActivePlayer ? 10 : 7));
                    earLeft.lineTo(headX + (isActivePlayer ? 20 : 14), headY - 2);
                    earLeft.closePath();
                    g2d.setColor(COLOR_PIG_PINK);
                    g2d.fill(earLeft);
                    // Wir zeichnen eine Umrandung um das Ohr
                    g2d.setColor(new Color(0, 0, 0, 40));
                    g2d.draw(earLeft);

                    // F) Matschflecken zeichnen falls das Schwein eine Drecksau ist
                    if (sau.isDirty()) {
                        // Wir färben den Schlamm braun
                        g2d.setColor(COLOR_MUD);
                        // Wir zeichnen drei unregelmäßige Schlammflecken auf den Körper
                        g2d.fillOval(pigX + (isActivePlayer ? 15 : 10), pigY + (isActivePlayer ? 20 : 15), isActivePlayer ? 18 : 12, isActivePlayer ? 14 : 10);
                        g2d.fillOval(pigX + (isActivePlayer ? 45 : 30), pigY + (isActivePlayer ? 10 : 8), isActivePlayer ? 22 : 16, isActivePlayer ? 12 : 8);
                        g2d.fillOval(pigX + (isActivePlayer ? 35 : 25), pigY + (isActivePlayer ? 35 : 25), isActivePlayer ? 15 : 10, isActivePlayer ? 12 : 8);
                        // Wir zeichnen einen Schlammfleck auf den Kopf
                        g2d.fillOval(headX + (isActivePlayer ? 10 : 8), headY + (isActivePlayer ? 25 : 18), isActivePlayer ? 12 : 8, isActivePlayer ? 8 : 6);
                    // Wenn das Schwein sauber ist glänzt es schön
                    } else {
                        // Wir färben den Glanz goldgelb
                        g2d.setColor(COLOR_GOLD);
                        // Wir zeichnen drei Sternchen um das Schwein herum
                        g2d.drawString("+", pigX - 5, pigY + 10);
                        g2d.drawString("+", pigX + pigW + 2, pigY + pigH / 2);
                        g2d.drawString("+", headX + headW / 2, headY - 8);
                    }

                    // G) Den Stall zeichnen falls für dieses Schwein ein Stall gebaut wurde
                    if (sau.hasStall()) {
                        // Wir färben das Holz hellbraun
                        g2d.setColor(COLOR_WOOD);
                        // Wir berechnen die Pfostenbreite
                        int pWidth = isActivePlayer ? 12 : 8;
                        // Wir berechnen die linke Pfostenposition
                        int sX_Left = pigX - (isActivePlayer ? 15 : 10);
                        // Wir berechnen die rechte Pfostenposition
                        int sX_Right = pigX + pigW + (isActivePlayer ? 5 : 3);
                        // Wir berechnen die Höhe des Pfostenstarts
                        int sY = headY - (isActivePlayer ? 10 : 7);
                        // Wir berechnen die Pfostenhöhe bis zum Boden
                        int sHeight = h - sY;
                        
                        // Wir zeichnen den linken Holzpfosten
                        g2d.fillRect(sX_Left, sY, pWidth, sHeight);
                        // Wir zeichnen den rechten Holzpfosten
                        g2d.fillRect(sX_Right, sY, pWidth, sHeight);
                        
                        // Wir umranden beide Pfosten mit dunklem Holzbraun
                        g2d.setColor(COLOR_DARK_WOOD);
                        g2d.drawRect(sX_Left, sY, pWidth, sHeight);
                        g2d.drawRect(sX_Right, sY, pWidth, sHeight);

                        // Wir zeichnen das rote Stalldach
                        g2d.setColor(COLOR_STABLE_RED);
                        Path2D roof = new Path2D.Double();
                        // Wir beginnen am linken Überhang des Dachs
                        roof.moveTo(sX_Left - (isActivePlayer ? 10 : 7), sY + (isActivePlayer ? 10 : 7));
                        // Wir ziehen die Linie hoch zur Dachspitze in der Mitte
                        roof.lineTo((sX_Left + sX_Right + pWidth) / 2, sY - (isActivePlayer ? 20 : 15));
                        // Wir ziehen die Linie zum rechten Überhang des Dachs
                        roof.lineTo(sX_Right + pWidth + (isActivePlayer ? 10 : 7), sY + (isActivePlayer ? 10 : 7));
                        // Wir schließen die Dachform
                        roof.closePath();
                        // Wir bemalen das Dach rot
                        g2d.fill(roof);
                        // Wir zeichnen eine feine dunkle Umrandung um das Dach
                        g2d.setColor(new Color(0, 0, 0, 60));
                        g2d.draw(roof);

                        // Wir zeichnen den Blitzableiter falls einer auf dem Dach montiert ist
                        if (sau.blitzSchutz()) {
                            // Wir berechnen die mittlere Dachposition
                            int rX = (sX_Left + sX_Right + pWidth) / 2;
                            int rY = sY - (isActivePlayer ? 20 : 15);
                            // Wir legen die Länge der Metallstange fest
                            int rodH = isActivePlayer ? 25 : 18;
                            
                            // Wir färben die Stange hellgrau
                            g2d.setColor(Color.LIGHT_GRAY);
                            // Wir wählen eine feine Linienstärke
                            g2d.setStroke(new BasicStroke(2.5f));
                            // Wir zeichnen die Metallstange auf das Dach
                            g2d.drawLine(rX, rY, rX, rY - rodH);
                            
                            // Wir färben die Kugel goldgelb
                            g2d.setColor(COLOR_GOLD);
                            // Wir zeichnen die Kugel an die Spitze des Blitzableiters
                            g2d.fillOval(rX - 4, rY - rodH - 4, 8, 8);
                        }

                        // Wir zeichnen die verriegelte Tür falls das Schwein vor dem Bauern geschützt ist
                        if (sau.bauerSchutz()) {
                            // Wir färben die Holzbretter braun
                            g2d.setColor(COLOR_MUD);
                            // Wir wählen eine sehr dicke Linienstärke für die Bretter
                            g2d.setStroke(new BasicStroke(isActivePlayer ? 8.0f : 5.0f));
                            
                            // Wir vernageln die Stalltür mit einem fetten Holzkreuz
                            g2d.drawLine(sX_Left, h - 10, sX_Right + pWidth, sY + 15);
                            g2d.drawLine(sX_Left, sY + 15, sX_Right + pWidth, h - 10);
                            
                            // Wir umranden die Bretter schwarz für ein plastisches Aussehen
                            g2d.setColor(Color.BLACK);
                            g2d.setStroke(new BasicStroke(1.0f));
                            g2d.draw(new Line2D.Double(sX_Left, h - 10, sX_Right + pWidth, sY + 15));
                            g2d.draw(new Line2D.Double(sX_Left, sY + 15, sX_Right + pWidth, h - 10));
                        }
                    }

                    // H) Wir zeichnen die Nummer des Schweinchens und seinen Zustand ganz unten hin
                    g2d.setColor(Color.WHITE);
                    // Wenn es der eigene Spieler ist schreiben wir die Beschriftung etwas größer
                    g2d.setFont(new Font("Inter", Font.BOLD, isActivePlayer ? 12 : 9));
                    // Wir erstellen den Zustandstext
                    String statusText = (index + 1) + ". " + (sau.isDirty() ? "Drecksau" : "Sauber");
                    // Wir holen uns die Textbreite um den Text genau mittig zu platzieren
                    FontMetrics fm = g2d.getFontMetrics();
                    // Wir zeichnen den Text auf den Wiesenboden
                    g2d.drawString(statusText, (w - fm.stringWidth(statusText)) / 2, h - 5);

                    // Wir geben das Zeichenwerkzeug frei
                    g2d.dispose();
                }
            };
            // Wir machen die Schweinebox im Hintergrund unsichtbar
            pigCanvas.setOpaque(false);
            // Wir fügen das Schweinchen zur Wiesenreihe hinzu
            pigsRow.add(pigCanvas);
        }

        // Wir fügen die Schweine-Reihe in die Mitte der Weide ein
        panel.add(pigsRow, BorderLayout.CENTER);
        // Wir geben das fertige Weidepanel zurück
        return panel;
    }

    // Diese Funktion baut eine einzelne Handkarte zum Anklicken auf
    private JPanel createCardPanel(Card card, int cardIndex) {
        // Wir erstellen die Handkarte und malen sie wunderschön aus
        JPanel cardPanel = new JPanel() {
            // Hier speichern wir ob die Maus über der Handkarte schwebt
            private boolean isHovered = false;

            @Override
            protected void paintComponent(Graphics g) {
                // Wir rufen die Standard-Zeichenfunktion auf
                super.paintComponent(g);
                // Wir holen uns das zweidimensionale Zeichenwerkzeug
                Graphics2D g2d = (Graphics2D) g.create();
                // Wir schalten die Kantenglättung ein
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Wir lesen die Breite und Höhe der Karte aus
                int w = getWidth();
                int h = getHeight();

                // Wir legen leere Farbvariablen für den Kartentyp an
                GradientPaint gp;
                Color topColor;
                Color bottomColor;

                // Wir wählen ein edles Farbdesign je nach Name der Spielkarte
                switch (card.getName()) {
                    case "Matsch":
                        // Matsch bekommt ein erdiges Schlammbraun
                        topColor = new Color(141, 110, 99);
                        bottomColor = new Color(93, 64, 55);
                        break;
                    case "Regen":
                        // Regen bekommt ein wolkenartiges Regenblau
                        topColor = new Color(74, 144, 226);
                        bottomColor = new Color(42, 92, 160);
                        break;
                    case "Stall":
                        // Stall bekommt ein warmes Holzgelb
                        topColor = new Color(212, 172, 13);
                        bottomColor = new Color(154, 125, 10);
                        break;
                    case "Blitz":
                        // Blitz bekommt ein mystisches Gewitter-Lila
                        topColor = new Color(155, 89, 182);
                        bottomColor = new Color(108, 52, 131);
                        break;
                    case "Blitzableiter":
                        // Blitzableiter bekommt ein glänzendes Metallsilber
                        topColor = new Color(149, 165, 166);
                        bottomColor = new Color(90, 90, 90);
                        break;
                    case "Bauer schrubbt":
                        // Bauer-schrubbt bekommt ein fleißiges Reinigungs-Orange
                        topColor = new Color(230, 126, 34);
                        bottomColor = new Color(186, 94, 20);
                        break;
                    case "Bauer aerger dich":
                        // Bauer-ärgere-dich bekommt ein wütendes Warn-Rot
                        topColor = new Color(231, 76, 60);
                        bottomColor = new Color(192, 57, 43);
                        break;
                    case "Schlammvulkan":
                        // Vulkan bekommt ein glühendes Magma-Orange
                        topColor = new Color(243, 156, 18);
                        bottomColor = new Color(211, 47, 47);
                        break;
                    default:
                        // Alle unbekannten Karten bekommen ein einfaches Grau
                        topColor = Color.LIGHT_GRAY;
                        bottomColor = Color.GRAY;
                }

                // Wir erstellen einen eleganten vertikalen Farbverlauf auf der Karte
                gp = new GradientPaint(0, 0, topColor, 0, h, bottomColor);
                // Wir laden den Verlauf
                g2d.setPaint(gp);
                // Wir bemalen die Karte mit abgerundeten Ecken
                g2d.fillRoundRect(0, 0, w, h, 18, 18);

                // Wenn diese Handkarte gerade vom Spieler ausgewählt wurde
                if (selectedHandCardIndex == cardIndex) {
                    // Wir färben den Rahmen strahlend goldgelb
                    g2d.setColor(COLOR_GOLD);
                    // Wir wählen eine dicke Linienstärke von 4 Punkten
                    g2d.setStroke(new BasicStroke(4.0f));
                    // Wir zeichnen den leuchtenden Rahmen auf die Karte
                    g2d.drawRoundRect(2, 2, w - 4, h - 4, 18, 18);
                    
                    // Wir zeichnen ein warmes goldenes Glühen über die Karte
                    g2d.setColor(new Color(241, 196, 15, 40));
                    g2d.fillRoundRect(2, 2, w - 4, h - 4, 18, 18);
                // Wenn die Maus nur über der Karte schwebt
                } else if (isHovered) {
                    // Wir färben den Rahmen weiß
                    g2d.setColor(Color.WHITE);
                    // Wir wählen eine Linienstärke von 2 Punkten
                    g2d.setStroke(new BasicStroke(2.0f));
                    // Wir zeichnen den schwebenden Rahmen auf die Karte
                    g2d.drawRoundRect(1, 1, w - 2, h - 2, 18, 18);
                // Wenn die Karte unberührt auf der Hand liegt
                } else {
                    // Wir zeichnen einen dezenten dunklen Schattenrand um die Karte
                    g2d.setColor(new Color(0, 0, 0, 50));
                    g2d.drawRoundRect(0, 0, w - 1, h - 1, 18, 18);
                }

                // A) Wir schreiben den Namen der Karte oben hin
                g2d.setColor(Color.WHITE);
                // Wir wählen eine fette moderne Schriftart
                g2d.setFont(new Font("Outfit", Font.BOLD, 18));
                // Wir holen uns die Textbreite zum Zentrieren des Namens
                FontMetrics fmName = g2d.getFontMetrics();
                // Wir schreiben den Namen genau mittig auf die Karte
                g2d.drawString(card.getName(), (w - fmName.stringWidth(card.getName())) / 2, 32);

                // B) Wir zeichnen ein kurzes Wortsymbol anstelle eines Emojis in die Mitte
                g2d.setFont(new Font("Inter", Font.BOLD, 28));
                // Wir legen ein leeres Textfeld an
                String symbol = "";
                // Wir wählen ein passendes Textsymbol je nach Kartentyp aus
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
                // Wir holen uns die Textbreite des Symbols
                FontMetrics fmSymbol = g2d.getFontMetrics();
                // Wir schreiben das Symbol genau zentriert in die Mitte der Karte
                g2d.drawString(symbol, (w - fmSymbol.stringWidth(symbol)) / 2, 75);

                // C) Wir schreiben die verständliche Beschreibung der Karte unten hin
                g2d.setFont(new Font("Inter", Font.PLAIN, 10));
                // Wir wählen ein gut lesbares Hellgrau
                g2d.setColor(new Color(240, 240, 240));
                
                // Wir holen uns den Beschreibungstext der Karte
                String desc = card.getBeschreibung();
                // Wir zerlegen den Text an allen Leerzeichen in einzelne Wörter
                String[] words = desc.split(" ");
                // Wir legen einen Speicher für die aktuelle Textzeile an
                StringBuilder line = new StringBuilder();
                // Wir beginnen mit dem Schreiben bei einer Höhe von 110 Bildpunkten
                int yPos = 110;
                
                // Wir gehen jedes einzelne Wort der Reihe nach durch
                for (String word : words) {
                    // Wir prüfen ob das Wort noch in die aktuelle Zeile passt
                    if (g2d.getFontMetrics().stringWidth(line.toString() + " " + word) < w - 20) {
                        // Wenn ja fügen wir das Wort zur aktuellen Zeile hinzu
                        line.append(" ").append(word);
                    // Wenn die Zeile voll ist
                    } else {
                        // Wir schreiben die fertige Zeile auf die Karte
                        g2d.drawString(line.toString().trim(), 12, yPos);
                        // Wir gehen 14 Bildpunkte nach unten in die nächste Zeile
                        yPos += 14;
                        // Wir beginnen eine neue Zeile mit dem aktuellen Wort
                        line = new StringBuilder(word);
                    }
                }
                // Wenn am Ende noch ein Resttext übrig ist
                if (line.length() > 0) {
                    // Wir schreiben den letzten Resttext auf die Karte
                    g2d.drawString(line.toString().trim(), 12, yPos);
                }

                // Wir geben das Zeichenwerkzeug wieder frei
                g2d.dispose();
            }
        };

        // Wir legen die Standardgröße der Handkarte fest
        cardPanel.setPreferredSize(new Dimension(160, 180));
        // Wir machen den Hintergrund der Handkarte unsichtbar
        cardPanel.setOpaque(false);
        
        // Wir reagieren auf Maus-Interaktionen mit dieser Handkarte
        cardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Der Mauszeiger verwandelt sich beim Drüberfahren in eine zeigende Hand
                cardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                // Wir verschieben die Karte optisch um zehn Bildpunkte nach oben um ein Schweben anzuzeigen
                cardPanel.setBounds(cardPanel.getX(), cardPanel.getY() - 10, cardPanel.getWidth(), cardPanel.getHeight());
                // Wir zeichnen die Karte neu
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Der Mauszeiger wird wieder normal wenn die Maus die Karte verlässt
                cardPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                // Wir zeichnen die Karte neu
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Wenn man eine bereits ausgewählte Handkarte noch einmal anklickt
                if (selectedHandCardIndex == cardIndex) {
                    // Wir heben die Kartenauswahl komplett auf
                    selectedHandCard = null;
                    selectedHandCardIndex = -1;
                // Wenn man eine andere Handkarte anklickt
                } else {
                    // Wir wählen diese Spielkarte aus
                    selectedHandCard = card;
                    // Wir speichern die Nummer der Karte ab
                    selectedHandCardIndex = cardIndex;
                }
                
                // Wir bringen die gesamte Spielfläche auf den allerneuesten Stand
                updateUIState();
            }
        });

        // Wir geben das fertige Kartenpanel zurück
        return cardPanel;
    }

    // Diese Funktion steuert das freiwillige Abwerfen oder das globale Ausspielen von Ereignissen
    private void handleDiscardOrGlobalPlay() {
        // Wenn keine Handkarte ausgewählt wurde oder das Spiel nicht läuft brechen wir ab
        if (selectedHandCard == null || game == null) return;

        // Wir holen uns die ausgewählte Spielkarte
        Card card = selectedHandCard;
        // Wir holen uns den Namen der Karte
        String name = card.getName();

        // 1. Fall: Es handelt sich um ein globales Ereignis (Regen oder Schlammvulkan)
        if (name.equals("Regen") || name.equals("Schlammvulkan")) {
            try {
                // Wir spielen die Karte ohne ein spezielles Zielschwein (-1, null) aus
                game.spieleZug(card, -1, null);
                
                // Wir setzen die Kartenauswahl komplett zurück
                selectedHandCard = null;
                selectedHandCardIndex = -1;

                // Wenn das Spiel nach diesem Ereignis beendet ist
                if (game.isGameOver()) {
                    // Wir zeigen den Siegesbildschirm
                    showVictoryScreen();
                // Wenn das Spiel weitergeht
                } else {
                    // Wir blenden den Sichtschutz für den Spielerwechsel ein
                    showPassScreen();
                }
            } catch (Exception ex) {
                // Bei einem Fehler zeigen wir ein kurzes Fehlerfenster an
                JOptionPane.showMessageDialog(this, "Fehler: " + ex.getMessage(), "Ereignis-Fehler", JOptionPane.ERROR_MESSAGE);
            }
        } 
        // 2. Fall: Der Spieler wirft eine normale Karte freiwillig ungenutzt ab
        else {
            // Wir fragen den Spieler über ein Ja-Nein-Fenster ob er die Karte wirklich abwerfen will
            int reply = JOptionPane.showConfirmDialog(
                this, 
                "Moechtest du die Karte " + name + " wirklich ungenutzt auf den Ablagestapel werfen?", 
                "Karte abwerfen", 
                JOptionPane.YES_NO_OPTION
            );
            
            // Wenn der Spieler mit Ja bestätigt
            if (reply == JOptionPane.YES_OPTION) {
                // Wir werfen die Handkarte freiwillig ab und ziehen eine neue Karte nach
                game.karteFreiwilligAbwerfen(card);
                
                // Wir setzen die Auswahl komplett zurück
                selectedHandCard = null;
                selectedHandCardIndex = -1;
                
                // Wir blenden den Sichtschutz für den Spielerwechsel ein
                showPassScreen();
            }
        }
    }

    // Diese Funktion wickelt das Notfall-Abwerfen ab wenn ein Spieler blockiert ist
    private void handleDiscardEntireHand() {
        // Wenn das Spiel nicht läuft brechen wir ab
        if (game == null) return;
        
        // Wir fragen den Spieler ob er wirklich keine gültigen Spielzüge machen kann
        int reply = JOptionPane.showConfirmDialog(
            this,
            "Bestaetigst du, dass du keinen gueltigen Spielzug machen kannst?\n(Deine Karten werden den anderen gezeigt, abgeworfen und du ziehst 3 neue Karten).",
            "Hand komplett abwerfen",
            JOptionPane.YES_NO_OPTION
        );

        // Wenn der Spieler mit Ja bestätigt
        if (reply == JOptionPane.YES_OPTION) {
            // Die Spiel-Engine wirft alle Karten ab und teilt drei neue Karten aus
            game.handAbwerfenUndNeuZiehen();
            
            // Wir setzen die Kartenauswahl komplett zurück
            selectedHandCard = null;
            selectedHandCardIndex = -1;
            
            // Wir zeigen den Sichtschutz an
            showPassScreen();
        }
    }

    // Diese Funktion schaltet auf den Sichtschutz-Bildschirm um
    private void showPassScreen() {
        // Wir durchsuchen alle Elemente in der Sichtschutz-Ansicht
        Component[] comps = passPanel.getComponents();
        // Wir gehen alle Elemente durch
        for (Component c : comps) {
            // Wenn wir den Hinweistext finden
            if (c instanceof JLabel && ((JLabel) c).getText().startsWith("Bereit machen")) {
                // Wir aktualisieren den Text und tragen den Namen des nächsten Spielers ein
                ((JLabel) c).setText("Bereit machen, " + game.getActivePlayer().getUsername() + "!");
            }
        }
        
        // Wir wechseln die Ansicht auf den Sichtschutz
        cardLayout.show(mainContainer, "PASS");
    }

    // Diese Funktion schaltet auf den Siegesbildschirm um wenn das Spiel vorbei ist
    private void showVictoryScreen() {
        // Wir durchsuchen alle Elemente in der Sieges-Ansicht
        Component[] comps = victoryPanel.getComponents();
        // Wir gehen alle Elemente nacheinander durch
        for (Component c : comps) {
            // Wenn wir den Sieges-Text finden
            if (c instanceof JLabel && ((JLabel) c).getText().contains("gewonnen")) {
                // Wir tragen den Namen des glücklichen Gewinners ein
                ((JLabel) c).setText("Sieg! " + game.getWinner().getUsername() + " HAT GEWONNEN!");
            }
        }
        
        // Wir wechseln die Ansicht auf den Siegesbildschirm
        cardLayout.show(mainContainer, "VICTORY");
    }
}
