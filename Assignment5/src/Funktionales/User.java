package Funktionales;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class User {
    public Connector connector;
    public Channel ch;
    public QueueingConsumer consumer;
    public int userID;

    public User() throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException {
        connector = new Connector();
        this.ch = connector.getChannel();
        ch.queueDeclare("chatroom", false, false, false, null);
        ch.exchangeDeclare("chat", "fanout");
        ch.queueBind("chatroom", "chat", "");

        userID = this.hashCode();
    }

    public String consume() throws IOException {
        consumer = new QueueingConsumer(ch);
        ch.basicConsume("chatroom", true, consumer);
        ReceiverThread receiverThread = new ReceiverThread(consumer, userID);
        Thread th = new Thread(receiverThread);
        th.start();
        return "";
    }

    public void publish(String msg) throws IOException {
        msg = String.valueOf(userID) + ":" + msg;
        ch.basicPublish("chat", "", null, msg.getBytes());
    }
}
