package Lepsi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jm on 11/4/2014.
 */
public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        Scanner sc = new Scanner(System.in);
        ConsumerLepse consumer = new ConsumerLepse("queue");
        ExecutorService sd = Executors.newFixedThreadPool(10);

        Producer producer = new Producer("queue");
        String name = sc.next();
        producer.setName(name);

        while (true) {
            // read message to chat
            String message = sc.next();
            producer.sendMessage(message);

            sd.submit(consumer);

        }
    }
}
