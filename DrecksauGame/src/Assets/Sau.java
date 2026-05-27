package Assets;

public class Sau {

    private boolean dirty;
    private Saustall saustall;

    public Sau() {
        this.dirty = false;
        this.saustall = null;
    }

    // Status der Schweine

    public boolean isDirty() {
        return dirty;  // DRECKIG
    }

    public boolean isClean() {
        return !dirty;  // SAUBER
    }

    public boolean hasStall() {
        return saustall != null;    // STALL
    }

    public Saustall getStall() {
        return saustall;    // KRIEGT STALL
    }

    public void makeDirty() {
        this.dirty = true;  // MACHT DRECKIG
    }

    public void makeClean() {
        this.dirty = false; // SÄUBERT SCHWEIN
    }

    public void buildStall() {
        this.saustall = new Saustall(); // ERSTELLT STALL
    }

    public boolean regenSchutz() {
        return saustall != null;    // HAT STALL
    }

    public boolean bauerSchutz() {
        return saustall != null && saustall.hasBauerAerger();   // HAT BAUER-AERGER-DICH-NICHT / IMMUN GEGEN SCHRUB
    }

    public boolean blitzSchutz() {
        return saustall != null && saustall.hasBlitzableiter(); // HAT BLITZABLEITER / IMMUN GEGEN BLITZ
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Sauberkeits Status
        sb.append(dirty ? "Drecksau!" : "Sauberschwein!");

        // Saustall Status
        if (saustall == null) {
            sb.append(" - Im Freien ");
        } else {
            sb.append(" - im Stall ");
            if (saustall.hasBauerAerger()) sb.append("eingeschlossen");
            if (saustall.hasBlitzableiter()) sb.append("mit Blitzableiter");
        }
        return sb.toString();
    }
}
