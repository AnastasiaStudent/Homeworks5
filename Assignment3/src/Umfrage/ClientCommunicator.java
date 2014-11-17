package Umfrage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientCommunicator {
    private final static int PORT = 7825;

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientCommunicator(String server) {
        try {
            socket = new Socket(server, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Ergebnis communicate(String meinung) {
        Ergebnis ergebnis = null;
        try {
            out.writeObject(meinung);
            ergebnis = (Ergebnis) in.readObject();
            return ergebnis;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ergebnis;

    }
}
