package Client;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerStart {
    // the boolean that will be turned of to stop the server
    private boolean keepGoing;
    private ExecutorService ex;
    // to display time
    private SimpleDateFormat sdf;
    // the port number to listen for connection
    private int port = 8474;

    /**
     * server constructor that receive the port to listen to for connection as parameter
     * in console
     */
    public ServerStart() {
        sdf = new SimpleDateFormat("HH:mm:ss");
    }

    public static void main(String[] args) throws IOException {

        String tempFile = "C:\\Users\\jm\\Documents\\Homeworks5.git\\Assignment3\\answers.ser";
        //Delete if tempFile exists
        File fileTemp = new File(tempFile);
        if (fileTemp.exists()) {
            System.out.println("Previous ser files have been deleted: " + fileTemp.delete());
        }

        ServerStart serverStart = new ServerStart();
        serverStart.start();
    }

    public void start() throws IOException {
        keepGoing = true;

        ServerSocket ser = new ServerSocket(port);
        // infinite loop to wait for connections
        while (keepGoing) {
            // format message saying we are waiting
            display("Server waiting for Clients on port " + port + ".");

            Socket socket = ser.accept();    // accept connection

            ThreadPoolServer t = new ThreadPoolServer(socket);  // make a thread of it
            ex = Executors.newFixedThreadPool(10);
            ex.submit(t);

            // if cannot
            if (!keepGoing) {
                break;
            }
        }
        // I was asked to stop
        try {
            ex.shutdown();
        } catch (Exception e) {
            display("Exception closing the server and clients: " + e);
        }
    }

    /**
     * Display an event (not a message) to the console or the GUI
     */
    private void display(String msg) {
        String time = sdf.format(new Date()) + " " + msg;
        System.out.println(time);
    }

}
