package Umfrage;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.ObjectInputStream;
//import java.io.FileWriter;
//import java.io.BufferedWriter;
//import java.io.IOException;


public class Server {
    public Server() {
    }

    public synchronized void meinungAufnehmen(String meinung) {

        Ergebnis ergebnis;
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        String filename = "Umfrage.txt";
        try {

            fis = new FileInputStream(filename);
            ois = new ObjectInputStream(fis);
            ergebnis = (Ergebnis) ois.readObject();
            ois.close();
            fis.close();

            if (meinung.equals("ja")) {
                ergebnis.setJa(ergebnis.getJa() + 1);
            }
            if (meinung.equals("nein")) {
                ergebnis.setNein(ergebnis.getNein() + 1);
            }

            if (meinung.equals("enthalten")) {
                ergebnis.setEnthaltung(ergebnis.getEnthaltung() + 1);
            }

            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(filename));
            o.writeObject(ergebnis);
            o.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public Ergebnis standAnzeigen() {
        Ergebnis ergebnis = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        String filename = "Umfrage.txt";
        try {
            fis = new FileInputStream(filename);
            ois = new ObjectInputStream(fis);
            ergebnis = (Ergebnis) ois.readObject();
            ois.close();
            fis.close();
            return ergebnis;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ergebnis;
        }

    }
}
