package Umfrage;

import java.io.Serializable;


public class Ergebnis implements Serializable {
    int ja;
    int nein;
    int enthaltung;
    String zeile;
    String ausgabe;

    public Ergebnis() {
        ja = 0;
        nein = 0;
        enthaltung = 0;
    }

    public int getJa() {
        return ja;
    }

    public void setJa(int ja) {
        this.ja = ja;
    }

    public int getNein() {
        return nein;
    }

    public void setNein(int nein) {
        this.nein = nein;
    }

    public int getEnthaltung() {
        return enthaltung;
    }

    public void setEnthaltung(int enthaltung) {
        this.enthaltung = enthaltung;
    }

    public String ausgabeStand() {
        ausgabe = "JA: " + Integer.toString(ja) + "\n" + "Nein:" + Integer.toString(nein) + "\n" + "Enthaltung:" + Integer.toString(enthaltung);
        return ausgabe;
    }

}
