package Lepsi;

import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Created by jm on 11/4/2014.
 */
public class Producer extends EndPoint {
    String name;

    public Producer(String endPointName) throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        super(endPointName);
    }

    public static void main(String[] args) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, IOException {
        Scanner sc = new Scanner(System.in);
        Producer producer = new Producer("Queue");
        while (true) {
            String message = sc.next();
            producer.sendMessage(message);
        }
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
}
