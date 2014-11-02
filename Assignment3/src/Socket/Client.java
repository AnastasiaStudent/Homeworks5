package Socket;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 7418);
        System.out.println("Socket Connection is OK!");
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String filename = "answers.ser";
        String msg;
        String msgp;
        while (true) {
            try {
                msgp = in.readUTF();
                out.writeObject(msgp);
                out.flush();
            } finally {

            }

            msg = bufferedReader.readLine();
            out.writeUTF(msg);
            out.close();
            in.close();
            System.out.println("Kundendatei vorbereitet");
        }



    }



}
