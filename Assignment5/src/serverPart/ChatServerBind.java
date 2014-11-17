package serverPart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.rmi.AlreadyBoundException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Created by jm on 10/10/2014.
 */
public class ChatServerBind {


    public static void main(String[] args)
        throws IOException, AlreadyBoundException,
        NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        Channel channel;
        Connection connection;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your name");
        String yourName = scan.next();

        ChatServer chse = new ChatServer(yourName);
        //Create a connection factory
        ConnectionFactory factory = new ConnectionFactory();

        //hostname of your rabbitmq server
        factory.setUri("amqp://test:test@b40.cz:5672");

        //getting a connection
        connection = factory.newConnection();

        //creating a channel
        channel = connection.createChannel();

        //declaring a queue for this channel. If queue does not exist,
        //it will be created on the server.
        channel.queueDeclare("newQeueRMIJAVA", false, false, false, null);
        ChatInterface client =null;

        while (true) {
            String msg = scan.nextLine();
            if ((chse.getName() != null) && (msg != null)) {
                client = chse.getClient();
                System.out.println(msg = "[" + chse.getName() + "] " + msg);
            }
            client.postMessage(msg);
        }
    }
}
