package Server;

import java.io.*;
import java.util.UUID;
import java.util.Vector;

/**
 * !!!!!!!!!!!Second part of the client!!!!!!!!
 * Created by jm on 10/10/2014.
 */
public class Question implements Serializable {
    String ja = "ja";
    String nein = "nein";
    String maybe = "maybe";
    boolean status = false;
    String DeineAbstimmung = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    FileOutputStream fileOu = null;
    FileInputStream fileIn = null;
    BufferedReader bf;

    int counterJA = 0;
    int counterMAYBE = 0;
    int counterNEIN = 0;

    Vector<UUID> list;

    public Question(UUID uniqueKey) {
        try {
            fileOu = new FileOutputStream("answers.ser");
            oos = new ObjectOutputStream(fileOu);
            fileIn = new FileInputStream("answers.ser");
            ois = new ObjectInputStream(fileIn);
        } catch (IOException e) {
            e.printStackTrace();
        }


        list = new Vector<UUID>();
        list.add(uniqueKey);

        System.out.println("we are in the Question constor");

    }

    public Question() throws IOException {
        fileOu = new FileOutputStream("answers.ser");
        oos = new ObjectOutputStream(fileOu);

        fileIn = new FileInputStream("answers.ser");
        ois = new ObjectInputStream(fileIn);

        list = new Vector<UUID>();

        //        file = new FileOutputStream("answers.ser");
        //        ds = new ObjectOutputStream(file);
    }

    /**
     * This return # of people and how they voted
     *
     * @return server Abstimmung
     */
    public synchronized String getDeineAbstimmungPerClient() {
        if (status == true) {
            System.out.println("\nHow many people have provided opinion? ->");
            System.out.println("For 'ja' -> " + counterJA);
            System.out.println("For 'nein' -> " + counterNEIN);
            System.out.println("For 'maybe' -> " + counterMAYBE);
            System.out.println("Anzahl der unique clients ID: " + list.size()); // method below
            return DeineAbstimmung;
        } else {
            System.out.println("there were no clients stored");
            return null;
        }
    }

    /**
     * Store Cleints meinung in Object Output Stream
     *
     * @param meinung meinung of the Client
     * @throws java.io.IOException
     */
    public synchronized void setDeineAbstimmung(String meinung) throws IOException {
        if (meinung.equals(ja)) {
            status = true;
            this.DeineAbstimmung = meinung;
            System.out.println("Ihre Meinung zur: " + OurServer.frage + " ist: " + DeineAbstimmung);
            counterJA++;
            oos.writeUTF(meinung);
            //            oos.writeObject(meinung);
            oos.flush();
            //            oos.close();
        } else if (meinung.equals(nein)) {
            status = true;
            this.DeineAbstimmung = meinung;
            System.out.println("Ihre Meinung zur: " + OurServer.frage + " ist: " + DeineAbstimmung);
            counterNEIN++;
            oos.writeUTF(meinung);
            //            oos.writeObject(meinung);
            oos.flush();
            //            oos.close();
        } else if (meinung.equals(maybe)) {
            status = true;
            this.DeineAbstimmung = meinung;
            System.out.println("Ihre Meinung zur: " + OurServer.frage + " ist: " + DeineAbstimmung);
            counterMAYBE++;
            oos.writeUTF(meinung);
            //            oos.writeObject(meinung);
            oos.flush();
            //            oos.close();
        } else {
            System.out.println("Nur 'ja', 'nein' oder 'maybe' sind mogliche Antworten");
            System.out.println("Your input is wrong. Read instructions again");
        }
    }

    //    public UUID getId() {
    //        if (list.isEmpty()) {
    //            System.out.println("There are no clients connected to the server. ");
    //        } else {
    //            return list.elementAt(0);
    //        }
    //        return null;
    //    }
    public int getSize() {
        return list.size();
    }

}
