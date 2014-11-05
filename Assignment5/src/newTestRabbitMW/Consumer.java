package newTestRabbitMW;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jm on 10/10/2014.
 */
public class Consumer {
    QueueingConsumer qr;
    Channel channel;
    GetResponse re;

    public Consumer() throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://test:test@b40.cz:5672");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueBind(ChatClient.ExchangevarNameQue, "chat", "");
        qr = new QueueingConsumer(channel);
        channel.basicConsume(ChatClient.ExchangevarNameQue, true, qr);
        re = channel.basicGet(ChatClient.ExchangevarNameQue, true);

    }

    public String returnMessage() throws IOException, InterruptedException {
        if (re.getMessageCount() == 0) {
            return "No messages found";
        } else {

            while (true) {
                QueueingConsumer.Delivery delivery = qr.nextDelivery();
                String delMsg = new String(delivery.getBody());
                String routingKey = delivery.getEnvelope().getRoutingKey();
                return " petr says " + routingKey + "':'" + delMsg;
            }
        }
    }
}




