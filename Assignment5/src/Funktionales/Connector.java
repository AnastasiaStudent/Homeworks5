package Funktionales;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class Connector {
    public Connection connection;
    public Channel channel;
    public ConnectionFactory factory;

    public Connector() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException {
        factory = new ConnectionFactory();
        //hostname of your rabbitmq server
        factory.setUri("amqp://test:test@b40.cz:5672");
        //getting a connection
        connection = factory.newConnection();
        //creating a channel
        channel = connection.createChannel();
    }

    public Connection getConnection() {
        return connection;
    }

    public Channel getChannel() {
        return channel;
    }
}
