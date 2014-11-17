package Sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadPoolServer implements Runnable {
    public Socket socket;
    public ObjectInputStream sInput;
    public ObjectOutputStream sOutput; // to write stat. to file
    String answer;
    CounterInter ci;
    StoreReturnValues srv;

    public ThreadPoolServer(Socket socket) {
        this.socket = socket;
        System.out.println("Threadpool created and assigned taks to do e.g. Object Input/Output Streams");
        System.out.println("Waiting for clients input to write into file and console \n");

        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
            ci = new CounterInter();
            srv = new StoreReturnValues();
        } catch (IOException e) {
            System.out.println("\nException creating new Input/Output Streams: " + e);
        }
    }

    public void run() {
        try {
            while (true) {
                answer = sInput.readUTF();
                //                System.out.println(answer);
                ci.setUUIDandAnswer(answer);
                System.out.println("answer and id has been saved in CI object");
                break;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Exception reading Streams (EOF or ClassNF):  " + e.toString());
        }

        try {
            srv.store(ci);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception write Streams to file:  " + e.toString());
        }
        //            System.out.println("\n Sockets is on the server voted as " + ci.getAnswer());
        ci.getAnswersMap();

        try {
            sOutput.writeObject("Your answer which server had to work with were : " + ci.getAnswer() + ci.sizeOfQueue());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //        try {
        //            socket.shutdownInput();
        //            socket.shutdownOutput();
        //            socket.close();
        //        } catch (IOException e) {
        //            e.toString();
        //            e.printStackTrace();
        //        }
    }
}


