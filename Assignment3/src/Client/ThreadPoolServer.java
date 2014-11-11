package Client;

import java.io.*;
import java.net.Socket;

public class ThreadPoolServer extends CounterInter implements Runnable {
    CounterInter ci = null;
    // the socket where to listen/talk
    private Socket socket;
    private ObjectInputStream sInput, ios;
    private ObjectOutputStream sOutput, oos;
    private CounterInter id; // uniqie ID client
    private String cm; //answer

    public ThreadPoolServer(Socket socket) {
        this.socket = socket;
        ci = new CounterInter();
        System.out.println("\nThread trying to create Object Input/Output Streams");
        try {
            // create output first
            sOutput = new ObjectOutputStream(socket.getOutputStream());
            sInput = new ObjectInputStream(socket.getInputStream());

            oos = new ObjectOutputStream(new FileOutputStream("answers.ser"));
            ios = new ObjectInputStream(new FileInputStream("answers.ser"));

            // read the the clients ID
//            id = (UUID) sInput.readObject();
//            System.out.println("Client    " + id + "    has connected");
        } catch (IOException e) {
            System.out.println("\nException creating new Input/output Streams: " + e);
        }
    }

    public void run() {
        while (true) {
            try {
                // read the the clients ID
                id = (CounterInter) sInput.readObject();
                System.out.println("Client    " + id + "    has connected");

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
                        oos.writeUTF(id + ":: " + ci.clientsAnswer());
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
     * Return abstimmung per client to SSoutput
     *
     * @throws IOException
     */
    public synchronized void abstimmungPerClient() throws IOException {
        String msg = "\nHow many people have provided opinion? ->"
            + " \n" + "For 'ja' -> " + counterJA
            + " \n" + "For 'nein'->" + counterNEIN
            + " \n" + "For 'maybe' -> " + counterMAYBE;

        //        sOutput.writeUTF(msg);
        sOutput.writeObject(msg);
        //        close();
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
        //        socket.close();
    }

    /**
     * Write a String to the Client output stream
     *
     * @deprecated since 2
     */
    private synchronized void writeMsg(String msg) throws IOException {
        if (!socket.isConnected()) {
            // if Client is still connected send the message to it
            close();
        } else {
            try {
                // write the message to the stream
                //                sOutput.writeObject(msg);
                sOutput.writeUTF(msg);
                oos.writeObject(msg);
            } catch (IOException e) {
                // if an error occurs, do not abort just inform the user
                System.out.println("Error sending message to " + id);
                System.out.println(e.toString());
            }
        }
    }

}
