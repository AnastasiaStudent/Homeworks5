package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jm on 10/10/2014.
 */
public class OurServer {
    public static final int port = 5147;
    public static final String frage = "Is Mr. Obama right-wing ?";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket ser = new ServerSocket(port);
        System.out.println("ServerSocket has been established " + " : " + ser.getLocalPort());
        ExecutorService ex = Executors.newFixedThreadPool(10);

        while (true) {
            Socket soc = ser.accept();
            System.out.println("Socket Connection has been created" + soc.getLocalAddress() + " <: " + soc.getPort());

            ex.submit(new ThreadPoolServer(soc));

        }
    }
}
