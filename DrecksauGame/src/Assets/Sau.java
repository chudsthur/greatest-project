// Das hier ist der Ordnername in dem diese Datei liegt
package Assets;

// Das ist die Klasse die ein einzelnes Schwein im Spiel darstellt
public class Sau {

    // Hier speichern wir ob das Schwein schmutzig ist oder nicht
    private boolean dirty;
    
    // Hier speichern wir den Stall des Schweins falls einer gebaut wurde
    private Saustall saustall;

    // Das ist der Bauplan um ein neues Schwein zu erschaffen
    public Sau() {
        // Zu Beginn des Spiels ist das Schwein sauber
        this.dirty = false;
        // Zu Beginn des Spiels hat das Schwein noch keinen Stall gebaut
        this.saustall = null;
    }

    // Diese Funktion verrät uns ob das Schwein schmutzig ist
    public boolean isDirty() {
        // Wir geben den gespeicherten Zustand ob es schmutzig ist zurück
        return dirty;
    }

    // Diese Funktion verrät uns ob das Schwein sauber ist
    public boolean isClean() {
        // Wir geben ja zurück wenn das Schwein nicht schmutzig ist
        return !dirty;
    }

    // Diese Funktion sagt uns ob das Schwein einen schützenden Stall hat
    public boolean hasStall() {
        // Wir prüfen ob ein Stall-Objekt für dieses Schwein existiert
        return saustall != null;
    }

    // Diese Funktion gibt uns das Stall-Objekt des Schweins zurück
    public Saustall getStall() {
        // Wir geben den gebauten Stall zurück
        return saustall;
    }

    // Diese Funktion macht das Schwein schmutzig
    public void makeDirty() {
        // Wir setzen den Zustand auf wahr so dass das Schwein schmutzig ist
        this.dirty = true;
    }

    // Diese Funktion wäscht das Schwein wieder komplett sauber
    public void makeClean() {
        // Wir setzen den Zustand auf falsch so dass das Schwein wieder sauber ist
        this.dirty = false;
    }

    // Diese Funktion baut einen neuen Stall für das Schwein auf
    public void buildStall() {
        // Wir erstellen ein neues Stall-Objekt für dieses Schwein
        this.saustall = new Saustall();
    }

    // Diese Funktion reißt den Stall ab wenn ein Blitz einschlägt
    public void destroyStall() {
        // Wir löschen das Stall-Objekt so dass das Schwein wieder im Freien steht
        this.saustall = null;
    }

    // Diese Funktion sagt uns ob das Schwein durch einen Stall vor Regen geschützt ist
    public boolean regenSchutz() {
        // Das Schwein ist geschützt wenn ein Stall-Objekt existiert
        return saustall != null;
    }

    // Diese Funktion sagt uns ob das Schwein vor dem Bauern geschützt ist
    public boolean bauerSchutz() {
        // Das Schwein ist vor dem Bauern sicher wenn es einen Stall hat und die Tür verriegelt ist
        return saustall != null && saustall.hasBauerAerger();
    }

    // Diese Funktion sagt uns ob das Schwein vor Blitzen geschützt ist
    public boolean blitzSchutz() {
        // Das Schwein ist vor Blitzen sicher wenn es einen Stall hat und ein Blitzableiter montiert ist
        return saustall != null && saustall.hasBlitzableiter();
    }

    // Diese Funktion wandelt den Zustand des Schweins in einen einfachen lesbaren Text um
    @Override
    public String toString() {
        // Wir erstellen einen Helfer um den Text Schritt für Schritt zusammenzubauen
        StringBuilder sb = new StringBuilder();

        // Wir prüfen ob das Schwein dreckig ist und fügen das passende Wort hinzu
        sb.append(dirty ? "Drecksau!" : "Sauberschwein!");

        // Wir prüfen ob das Schwein im Freien oder im Stall steht
        if (saustall == null) {
            // Wenn kein Stall da ist steht das Schwein im Freien
            sb.append(" (im Freien)");
        } else {
            // Wenn ein Stall da ist steht das Schwein im Stall
            sb.append(" (im Stall");
            // Wir prüfen welche Schutzvorrichtungen am Stall angebaut sind
            if (saustall.hasBauerAerger() && saustall.hasBlitzableiter()) {
                // Der Stall hat eine verriegelte Tür und einen Blitzableiter
                sb.append(" - verriegelt und mit Blitzableiter");
            } else if (saustall.hasBauerAerger()) {
                // Der Stall hat nur eine verriegelte Tür
                sb.append(" - verriegelt");
            } else if (saustall.hasBlitzableiter()) {
                // Der Stall hat nur einen Blitzableiter
                sb.append(" - mit Blitzableiter");
            } else {
                // Der Stall hat bisher noch keinen zusätzlichen Schutz angebaut
                sb.append(" - ungeschuetzt");
            }
            // Wir schließen die Klammer für den Text
            sb.append(")");
        }
        // Wir geben den fertigen Text als normales Wort zurück
        return sb.toString();
    }
}
