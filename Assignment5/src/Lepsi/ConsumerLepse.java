package Lepsi;


import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jm on 11/4/2014.
 */
public class ConsumerLepse extends EndPoint implements Consumer {

    public ConsumerLepse(String que) throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        super(que);
    }

    //    public static void main(String[] args) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, IOException {
    //        ConsumerLepse cl = new ConsumerLepse("Queue");
    //    }

    /**
     * Called when consumer is registered.
     */
    public void handleConsumeOk(String consumerTag) {
        System.out.println("Consumer " + consumerTag + " registered");
    }

    /**
     * Called when new message is available.
     */
    public void handleDelivery(String consumerTag, Envelope env,
        BasicProperties props, byte[] body) throws IOException {
        //        Map map = (HashMap) SerializationUtils.deserialize(body);
        System.out.println(SerializationUtils.deserialize(body));
    }

    public void handleCancel(String consumerTag) {
        System.out.println("some cancel is there");
    }

    public void handleCancelOk(String consumerTag) {
    }

    public void handleRecoverOk(String consumerTag) {
    }

    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {
    }
}
