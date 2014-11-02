package newTestRabbitMW;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jm on 10/10/2014.
 */
public class Consumer {
    public static void main(String[] args)
        throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException,
        InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://test:test@b40.cz:5672");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueBind(Sender.ExchangevarNameQue, "chat", "");

        QueueingConsumer qr = new QueueingConsumer(channel);
        channel.basicConsume(Sender.ExchangevarNameQue, true, qr);
        while (true) {
            QueueingConsumer.Delivery delivery = qr.nextDelivery();
            String delMsg = new String(delivery.getBody());
            String routingKey = delivery.getEnvelope().getRoutingKey();
            System.out.println(" [x] Received '" + routingKey + "':'" + delMsg + "'");
        }

    }
}
