package Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(7418);
        System.out.println("Server socket has been established");
        ExecutorService executor = Executors.newFixedThreadPool(5);

        while (true) {
            Socket s = ss.accept();
            System.out.println("Connection to the Socket is ready !");
            //            executor.execute(new RunTask(s));
        }

    }

    static class RunTask implements Runnable {

        private Socket myconnection;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public RunTask(Socket connection) {
            this.myconnection = connection;
            try {
                out = new ObjectOutputStream(myconnection.getOutputStream());
                in = new ObjectInputStream(myconnection.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                String suchname = (String) in.readObject();
                //                Question reply = server.investigate(suchname);
                //                out.writeObject(reply);
                out.flush();
                myconnection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
