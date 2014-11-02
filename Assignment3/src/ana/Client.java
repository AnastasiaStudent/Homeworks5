package ana;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;


class Client {
    public static void main(String[] args) throws IOException {

  /*
   * Der Name (Adresse) des Servers kann auf der Kommandozeile ¸bergeben
   * werden. Sonst wird "localhost" verwendet.
   */
        //        String serverName = "134.103.104.164";
        //  if (args.length > 0) {
        //   serverName = args[0];
        //  } else {
        String serverName = "localhost";
        //  }
        int portNr = 7824;
  /*
   * Es wird versucht, eine socket zum angegebenen Server aufzubauen. Dazu
   * wird ein Socket-Objekt erzeugt.
   */

        System.out.println("Oeffne Socket zu " + serverName
            + " auf Port " + portNr + ".");
        Socket socket = new Socket(serverName, portNr);


        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {

            String nachricht = null;
            System.out.println("Verbunden mit " + socket.getInetAddress().getHostName() + ".");

            while (true) {
                try {
                    nachricht = in.readUTF(); // auf die Antwort warten
                } catch (EOFException|SocketException e) {
                    break;
                }

                if (nachricht.equals("falsch")) {
                    System.out.println(socket.getInetAddress().getHostName()
                        + " : Ihre Meinung ist uns wichtig! Geben Sie bitte Ihre Antwort ein. Oder korrigieren Sie bitte Ihre Eingabe.");
                }
                if (nachricht.equals("ende")) {
                    break;
                } else {
                    System.out.println(socket.getInetAddress().getHostName() + " : " + nachricht);
                }
                //System.out.println("Server : " + nachricht);

                if (socket.isConnected() == false) {
                    System.out.println("Ende");
                    break;
                }
                if (socket.isInputShutdown()) {
                    System.out.println("Ende");
                    break;
                }
                if (socket.isOutputShutdown()) {
                    System.out.println("Ende");
                    break;
                }
                if (socket.isBound() == false) {
                    System.out.println("Ende");
                    break;
                }

                nachricht = keyboard.readLine();
                try {
                    out.writeUTF(nachricht); // senden Antwort
                    out.flush(); // daten uebetragung beenden
                } catch (Exception e) {
                    break;
                }
            }
            System.out.println("Verbindung beendet.");

        }
    }
}
