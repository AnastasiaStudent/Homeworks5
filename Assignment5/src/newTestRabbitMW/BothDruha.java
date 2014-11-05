package newTestRabbitMW;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Created by jm on 11/4/2014.
 */
public class BothDruha {

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
        System.out.println("Now, you can chat");

        /*
        Part of consumer
         */
        Channel channeltoBind = connection.createChannel();
        channeltoBind.exchangeDeclare("chat", "fanout");
        String que = channel.queueDeclare().getQueue(); // random queue
        channeltoBind.queueBind(que, "chat", "");
        DefaultConsumer dc = new DefaultConsumer(channeltoBind);
        //        channeltoBind.queueBind(ExchangevarNameQue, "chat", "");
        //        QueueingConsumer qr = new QueueingConsumer(channeltoBind);
        //        channeltoBind.basicConsume(ExchangevarNameQue, true, qr);

        channeltoBind.basicConsume(que, true, dc);

        while (true) {
            /*
            Producer part
             */
            String yourmsg = sc.next();
            channel.basicPublish("chat", "", null, yourmsg.getBytes());

            /*
            Consumer part
             */
            //            QueueingConsumer.Delivery delivery = qr.nextDelivery();

            GetResponse response = channeltoBind.basicGet(que, true);
            if (response == null) {
                System.out.println("no message recienved");
            } else {
//                AMQP.BasicProperties properties = response.getProps();
                byte[] body = response.getBody();
                String delMsg = new String(body.toString());
                //            String routingKey = delivery.getEnvelope().getRoutingKey();
                System.out.println(name + " says: " + delMsg + "");
            }
        }

    }
}
