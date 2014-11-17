package newTestRabbitMW;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Both {
    public static final String ExchangevarNameQue = "Aufgabe5Chat";

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, InterruptedException {

        Scanner sc = new Scanner(System.in);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://test:test@b40.cz:5672");
        Connection connection = factory.newConnection();
        /*
        Part of prod
         */
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("chat", "fanout"); // it will publish message to all queues,
        // no matter what rounting key will be used
//        channel.queueDeclare(ExchangevarNameQue, false, false, false, null);
        System.out.println("You have connected to the chat room");
        System.out.println("Insert your name");
        String name = sc.next();
        System.out.println("Now, - " + name + " - you can chat");

        /*
        Part of consumer
         */
//        Channel channeltoBind = connection.createChannel();
//        channeltoBind.exchangeDeclare("chat", "fanout");
        String que = channel.queueDeclare().getQueue(); // random queue
//        channeltoBind.queueBind(que,"chat","");
        channel.queueBind(que, "chat", "");
//        channeltoBind.queueBind(ExchangevarNameQue, "chat", "");
        QueueingConsumer qr = new QueueingConsumer(channel);
//        channeltoBind.basicConsume(ExchangevarNameQue, true, qr);
        channel.basicConsume(que, true, qr);

        while (true) {
            /*
            Producer part
             */
            String yourmsg = sc.next();
            channel.basicPublish("chat", "", null, yourmsg.getBytes());

            /*
            Consumer part
             */
            QueueingConsumer.Delivery delivery = qr.nextDelivery();
            String delMsg = new String(delivery.getBody());
            System.out.println(name + " says: " + delMsg + "");
        }

    }
}
