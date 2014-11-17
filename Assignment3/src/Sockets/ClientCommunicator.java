package Sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientCommunicator {

    public String server = "localhost";    // for I/O
    //    public String server = "134.103.212.238";    // for I/O. localhost
    public ObjectOutputStream sOutput;
    //    public ObjectInputStream sInput;    // to write on the socket
    public Socket socket;
    public int port = 8474;
    private ObjectInputStream sInput;

    public ClientCommunicator() {
    }

    public void connect() throws IOException {
        try {
            socket = new Socket(server, port);
        } catch (Exception ec) {
            System.out.println("Error connectiong to server:" + ec);
        }

        System.out.println("Connection accepted by server " + socket.getInetAddress() + ":" + socket.getPort());

        try {
            sOutput = new ObjectOutputStream(socket.getOutputStream());
            sInput = new ObjectInputStream(socket.getInputStream());
            System.out.println("Input/Output ist ok beim Sockets");
        } catch (IOException eIO) {
            System.out.println("Exception creating new Input/output Streams: " + eIO);
        }

    }

    public void writeClient(String answer) throws IOException, InterruptedException {
        sOutput.writeUTF(answer);
        //        sOutput.writeObject(uniqueKey);
        sOutput.flush();
        //        sOutput.close();
        //        System.out.println("Clients  uniqueKey & answer finally send to the server. Your answer: (" + answer + ")");
    }

    /**
     * makes no sense
     */
    public void readRemoteAnswerFromClientsStat() throws ClassNotFoundException {
        try {
            while (true) {
                String msg = (String) sInput.readObject();
                System.out.println(msg);
                break;
            }
            sInput.close();
        } catch (IOException e) {
            System.out.println("connection RESET");
        }
    }
}
