// Das hier ist der Ordnername in dem diese Datei liegt
package Assets;

// Das ist die Klasse die den Stall eines Schweins darstellt
public class Saustall {

    // Hier speichern wir ob die Stalltür gegen den Bauern verriegelt ist
    private boolean hasBauerAerger;
    
    // Hier speichern wir ob ein Blitzableiter auf dem Stalldach montiert ist
    private boolean hasBlitzableiter;

    // Das ist der Bauplan um einen neuen leeren Stall zu bauen
    public Saustall() {
        // Zu Beginn ist die Stalltür nicht verriegelt
        this.hasBauerAerger = false;
        // Zu Beginn ist noch kein Blitzableiter montiert
        this.hasBlitzableiter = false;
    }

    // Diese Funktion verrät uns ob die Stalltür verriegelt ist
    public boolean hasBauerAerger() {
        // Wir geben den Zustand der Türverriegelung zurück
        return hasBauerAerger;
    }

    // Diese Funktion verrät uns ob ein Blitzableiter montiert ist
    public boolean hasBlitzableiter() {
        // Wir geben den Zustand des Blitzableiters zurück
        return hasBlitzableiter;
    }

    // Diese Funktion verriegelt die Stalltür dauerhaft
    public void addBauerAerger() {
        // Wir setzen den Zustand für die Verriegelung auf wahr
        this.hasBauerAerger = true;
    }

    // Diese Funktion montiert einen Blitzableiter auf das Stalldach
    public void addBlitzableiter() {
        // Wir setzen den Zustand für den Blitzableiter auf wahr
        this.hasBlitzableiter = true;
    }
}
