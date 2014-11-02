package Socket;

import java.io.*;

/**
 * Created by jm on 10/28/2014.
 */
public class QManagement extends Question implements Serializable {

    ObjectOutputStream out;
    ObjectInputStream in;
    FileInputStream fis;
    FileOutputStream fos;
    BufferedReader bufferedReader;
    Question qe;

    public QManagement() throws IOException {
        qe = new Question();
        fis = new FileInputStream("answers.ser");
        fos = new FileOutputStream("answers.ser");
        out = new ObjectOutputStream(fos);
        in = new ObjectInputStream(fis);
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public synchronized Question getReply() throws IOException, ClassNotFoundException {
        this.qe = (Question) in.readObject();
        return qe;
    }

    public synchronized void setReply(String meinung) throws IOException {
        //        this.DeineAbstimmung = meinung;
        if (meinung.equals(ja)) {
            this.DeineAbstimmung = meinung;
            System.out.println("Ihre Meinung zur: " + " ist: " + DeineAbstimmung);
            counterJA++;
            out.writeUTF(meinung);
            out.writeObject(meinung);
            out.flush();
        } else if (meinung.equals(nein)) {
            this.DeineAbstimmung = meinung;
            System.out.println("Ihre Meinung zur: " + " ist: " + DeineAbstimmung);
            counterNEIN++;
            out.writeUTF(meinung);
            out.writeObject(meinung);
            out.flush();
        } else if (meinung.equals(maybe)) {
            this.DeineAbstimmung = meinung;
            System.out.println("Ihre Meinung zur: " + " ist: " + DeineAbstimmung);
            counterMAYBE++;
            out.writeUTF(meinung);
            out.writeObject(meinung);
            out.flush();
        } else {
            System.out.println("Nur 'ja', 'nein' oder 'maybe' sind mogliche Antworten");
            System.out.println("Your input is wrong. Read instructions again");
        }
    }
}
