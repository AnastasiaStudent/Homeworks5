package Lepsi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Created by jm on 11/4/2014.
 */
public class Producer extends EndPoint {

    public Producer(String endPointName) throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        super(endPointName);
    }

    public static void main(String[] args) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, IOException {
        Producer producer = new Producer("Queue");
        ConsumerLepse consumerLepse = new ConsumerLepse("Queue");

        Scanner sc = new Scanner(System.in);

        System.out.println("You have connected to the chat room");
        System.out.println("Insert your name");

        String name = sc.next();
        producer.setName(name);

        System.out.println("Now, - " + producer.getName() + " - you can chat");

        while (true) {
            System.out.println("What you want to do: Produce (1) the message or get the message (2)");
            int i = sc.nextInt();
            if (i == 1) {
                // produce
                System.out.println("your message!");
                String message = sc.next();
                producer.sendMessage(message);
//                System.out.println(producer.getName() + " says : " + message);
            } else if (i == 2){
                consumerLepse.consumeMessage(consumerLepse);
            }
        }
    }

}
