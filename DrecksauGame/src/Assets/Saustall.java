package Assets;

public class Saustall {

    private boolean hasBauerAerger;
    private boolean hasBlitzableiter;

    // Grundausgangslage von Sau (kein Saustall, kein Blitzableiter)
    public Saustall() {
        this.hasBauerAerger = false;
        this.hasBlitzableiter = false;
    }

    // Wenn Bauer-ärger-dich-nicht Karte vorhanden, angeben dass vorhanden ist
    public boolean hasBauerAerger() {
        return hasBauerAerger;
    }
    // Wenn Blitzableiter Karte vorhanden, angeben dass vorhanden ist
    public boolean hasBlitzableiter() {
        return hasBlitzableiter;
    }

    // Bauer-ärger-dich-nicht Karte hinzufügen
    public void addBauerAerger() {
        this.hasBauerAerger = true;
    }
    // Blitzableiter Karte hinzufügen
    public void addBlitzableiter() {
        this.hasBlitzableiter = true;
    }
}
