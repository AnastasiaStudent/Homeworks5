package Umfrage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCommunicator extends Thread {
    private final static int PORT = 7825;

    private static ServerSocket serverSocket;
    private static Server server;

    private Socket incoming;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Ergebnis ergebnis = null;

    public ServerCommunicator(Socket incoming) {
        this.incoming = incoming;

        try {
            out = new ObjectOutputStream(incoming.getOutputStream());
            in = new ObjectInputStream(incoming.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("ServerCommunicator waiting for clients.....");
            server = new Server();

            while (true) {
                Socket incoming = serverSocket.accept();
                ServerCommunicator communicator = new ServerCommunicator(incoming);
                communicator.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String uebergabe = (String) in.readObject();

            if (uebergabe.equals("auswertung")) {
                ergebnis = server.standAnzeigen();
            } else {
                server.meinungAufnehmen(uebergabe);
                System.out.println("Danke fur Ihre Abstimmung");
            }

            out.writeObject(ergebnis);
            out.flush();
            incoming.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

