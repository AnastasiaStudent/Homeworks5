package Client;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ThreadPoolServer extends CounterInter implements Runnable {
    private ObjectOutputStream oos;
    // the socket where to listen/talk
    private Socket socket;
    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private ObjectInputStream ios;
    private UUID id; // uniqie ID client
    private String cm; //answer
    private SimpleDateFormat sdf; // deprecated methods

    public ThreadPoolServer(Socket socket) {
        this.socket = socket;
        sdf = new SimpleDateFormat("HH:mm:ss");

        System.out.println("\nThread trying to create Object Input/Output Streams");
        try {
            // create output first
            sOutput = new ObjectOutputStream(socket.getOutputStream());
            sInput = new ObjectInputStream(socket.getInputStream());

            oos = new ObjectOutputStream(new FileOutputStream("answers.ser"));
            ios = new ObjectInputStream(new FileInputStream("answers.ser"));

            // read the ID
            id = (UUID) sInput.readObject();
            System.out.println("Client    " + id + "    has connected");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\nException creating new Input/output Streams: " + e);
        }
    }

    public void run() {
        while (true) {
            try {
                //username = (String) sInput.readObject();
                cm = (String) sInput.readObject();
                //                String fileread = (String) ios.readObject();
                //                qs = new Question(cm);
                //                System.out.println("input was " + fileread);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(" Exception reading Streams:  " + id + " " + e);
                break;
            }

            /**
             *Switch on the type of message receive
             * writemsg writes to client
             * sout writes to server console
             * it wont be counted as one together but as each cleint unique
             */
            switch (cm) {
                case "ja":
                    counterJA++;
                    try {
                        //                        writeMsg("\n" + id + " voted as " + cm + ": " + counterJA);
                        oos.writeUTF("\n" + id + " voted as " + cm + ": " + counterJA);
                        System.out.println("\n" + id + " voted as " + cm + ": " + counterJA);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "nein":
                    counterNEIN++;
                    try {
                        oos.writeUTF("\n" + id + " voted as " + cm + ": " + counterNEIN);
                        System.out.println("\n" + id + " voted as " + cm + ": " + counterNEIN);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "maybe":
                    counterMAYBE++;
                    try {
                        oos.writeUTF("\n" + id + " voted as " + cm + ": " + counterMAYBE);
                        System.out.println("\n" + id + " voted as " + cm + ": " + counterMAYBE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Statistics":
                    try {
                        abstimmungPerClient();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        try {
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws IOException
     */
    public synchronized void abstimmungPerClient() throws IOException {
        oos.writeUTF("\nHow many people have provided opinion? ->"
            + " \n" + "For 'ja' -> " + counterJA
            + " \n" + "For 'nein'->" + counterNEIN
            + " \n" + "For 'maybe' -> " + counterMAYBE);
    }

    /**
     * Write a String to the Client output stream
     *
     * @deprecated since 2
     */
    public synchronized void writeMsg(String msg) throws IOException {
        if (!socket.isConnected()) {
            // if Client is still connected send the message to it
            close();
        } else {
            try {
                // write the message to the stream
                //                sOutput.writeObject(msg);
                sOutput.writeUTF(msg);
                oos.writeObject(msg);
                oos.writeUTF(msg);
            } catch (IOException e) {
                // if an error occurs, do not abort just inform the user
                System.out.println("Error sending message to " + id);
                System.out.println(e.toString());
            }
        }
    }

    /**
     * close everything
     *
     * @throws IOException
     */
    private void close() throws IOException {
        sInput.close();
        sOutput.close();
        ios.close();
        oos.close();
        socket.close();
    }

    /**
     * Display a message to the console
     *
     * @deprecated
     */
    public void display(String msg) {
        String time = sdf.format(new Date()) + " " + msg;
        System.out.println(time);
    }

    /**
     * to broadcast a message to all Clients
     * not used anymore
     *
     * @deprecated
     */
    private synchronized void broadcast(String message) {
        // add HH:mm:ss and \n to the message
        String time = sdf.format(new Date());
        String messageLf = time + " " + message + "\n";
        // System.out.println( message on console
        System.out.print(messageLf);
    }
}
