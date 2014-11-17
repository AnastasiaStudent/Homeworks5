package Lepsi;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jm on 11/4/2014.
 */
public class EndPoint implements Producer {

    public String name;
    protected Channel channel;
    protected Connection connection;
    protected String endPointName;

    public EndPoint(String endpointName) throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        this.endPointName = endpointName;

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
        channel.queueDeclare(endpointName, false, false, false, null);
    }

    public void close() throws IOException {
        this.channel.close();
        this.connection.close();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sendMessage(Serializable object) throws IOException {
        channel.basicPublish("", endPointName, null,
            SerializationUtils.serialize(object));
    }

    public String consumeMessage(ConsumerLepse ls) throws IOException {
        while (true) {
            String msg = channel.basicConsume(endPointName, true, ls);
            return msg;
        }
    }
}
