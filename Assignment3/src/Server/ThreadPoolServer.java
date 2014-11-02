package Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * Created by jm on 10/25/2014.
 */
public class ThreadPoolServer implements Runnable {
    ObjectInputStream ois;
    Question qus;
    Socket soc;

    public ThreadPoolServer(Socket socket) throws IOException {
        this.soc = socket;
        ois = new ObjectInputStream(new FileInputStream("answers.ser"));
        //            ois = new ObjectInputStream(soc.getInputStream());
        //            ous = new ObjectOutputStream(new FileOutputStream("answers.ser"));

    }

    @Override public void run() {
        while (true) {
            int menuChoice = 0;
            UUID uuid = null;
            String inputmsg = null;


            try {
                menuChoice = ois.readInt();
                inputmsg = ois.readUTF();
                uuid = (UUID) ois.readObject();

            } catch (IOException|ClassNotFoundException e) {
                System.out.println("we read nothing" + e.getMessage());
            }

            System.out.println(menuChoice + " : " + inputmsg + " : " + uuid);

            if (menuChoice == 1) {
                qus = new Question(uuid);
                try {
                    qus.setDeineAbstimmung(inputmsg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                qus.getDeineAbstimmungPerClient();
            }
        }
        //            try {
        //                System.out.println("Client :" + /*qus.getId() +*/ " said " + ois.readUTF() + ois.readObject());
        //            } catch (IOException e) {
        //
        //            } catch (ClassNotFoundException e) {
        //            }

        //        } else {
        //            System.out.println("There are not clients at the time");
        //        }

    }
}
