package ana;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class ServerTP {

    private final static int PORT = 7824;
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static ExecutorService pool;

    public static void main(String args[]) {
        try {
            serverSocket = new ServerSocket(PORT);
            pool = Executors.newFixedThreadPool(10);
            System.out.println("Threadpool generated, Server waiting for clients...");

            // server = new Server();

            while (true) {
                socket = serverSocket.accept();
                Runnable task = new RunTask(socket);
                pool.execute(task);
            }

        } catch (IOException e) {
            pool.shutdown();
        }
    }


    static class RunTask implements Runnable {
        private Socket myconnection;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private String nachricht = null;

        public RunTask(Socket connection) {
            this.myconnection = connection;
            try {
                out = new ObjectOutputStream(myconnection.getOutputStream());
                in = new ObjectInputStream(myconnection.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("streams fehler");
            }

        } //Konstruktor

        public void run() {

            try {
                System.out.println("Verbunden mit "
                    + socket.getInetAddress().getHostName() + ".");
          /*
           * Als erstes wird dem Client mitgeteilt, dass er nun verbunden ist.
    			 */

                nachricht =
                    "Ist, Ihrer Meinung nach, die Aufgabe3 kompliziert? \nBitte 'ja','nein' oder 'sonstiges' fuer Enthaltung eingeben.";
                out.writeUTF(nachricht);// Antwort senden
                out.flush();
                System.out.println("Server: " + nachricht);
                while (true) {
                    nachricht = in.readUTF();// // auf die Antwort/Anfrage warten
                    System.out.println(myconnection.getInetAddress().getHostName() + ": "
                        + nachricht);
                    if (nachricht.equals("ja") || nachricht.equals("nein")
                        || nachricht.equals("sonstiges")) {
                        QueryInit qi = new QueryInit();
                        qi.speichern(nachricht);

                        break;
                    } else {
                        nachricht = "falsch";
                        out.writeUTF(nachricht);// Antwort auf Client senden
                        out.flush();
                        System.out.println("Server: " + nachricht);
                    }
                }

                nachricht =
                    "Vilen Dank fuer Ihre Antwort! \nMoechten Sie den aktuellen Stand der Abstimmung erfragen? \n'ja' = Ja, bitte oder 'nein' = Nein, danke.";
                out.writeUTF(nachricht);
                out.flush();
                System.out.println(
                    "Server an " + myconnection.getInetAddress().getHostName() + ": " + nachricht);
                while (true) {
                    nachricht = in.readUTF();// // auf die Antwort/Anfrage warten
                    System.out.println(myconnection.getInetAddress().getHostName() + ": "
                        + nachricht);
                    if (nachricht.equals("ja")) {
                        QueryInit qi2 = new QueryInit();
                        nachricht = qi2.anzeigen();
                        //nachricht = "ende";
                        out.writeUTF(nachricht);
                        out.flush();
                        break;
                    } else if (nachricht.equals("nein")) {
                        nachricht = "ende";
                        out.writeUTF(nachricht);
                        out.flush();
                        break;
                    } else {
                        nachricht = "falsch";
                        out.writeUTF(nachricht);// Antwort auf Client senden
                        out.flush();
                        System.out.println(
                            "Server an " + myconnection.getInetAddress() + ": " + nachricht);
                    }
                }
                //out.close();
                //in.close();
                myconnection.shutdownOutput();
                myconnection.shutdownInput();
                myconnection.close();
                System.out.println(
                    "Verbindung mit dem " + myconnection.getInetAddress().getHostName()
                        + " beendet.");


                // System.exit(0);
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
    }

} // ServerCommunicatorThreadpool
