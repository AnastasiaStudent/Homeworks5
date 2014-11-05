package ana;

import java.io.*;


public class QueryInit implements Serializable {
    SDS sds = new SDS();

    String filename = "umfrageErgebnisse.txt";

    public synchronized void speichern(String antwort) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            this.sds = (SDS) SDSliefern();

            if (antwort.equals("ja")) {
                this.sds.ja++;
            } else if (antwort.equals("nein")) {
                this.sds.nein++;
            } else if (antwort.equals("sonstiges")) {
                this.sds.sonstiges++;
            }
            oos.writeObject(this.sds);
            oos.flush();
            System.out.println("Datei gespeichert");
        } catch (IOException e) {
        }
    }

    private SDS SDSliefern() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            this.sds = (SDS) ois.readObject();
        } catch (ClassNotFoundException | NullPointerException | IOException e) {
            System.out.println("Erste Anfrage!");
        }

        if (this.sds == null) {
            this.sds.ja = 0;
            this.sds.nein = 0;
            this.sds.sonstiges = 0;
        }
        return sds;
    }

    public String anzeigen() {

        this.sds = SDSliefern();
        String nachricht =
            "Der aktuelle Stand der Abstimmung: \nJa: " + this.sds.ja + " \nNein: " + this.sds.nein
                + " \nEnthaltung: " + this.sds.sonstiges + ".";
        return nachricht;
    }
}
