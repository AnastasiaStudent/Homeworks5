package Funktionales;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Inspired by https://github.com/zi-yang-zhang/Simple-Java-RabbitMQ-Chat
 * Thank you
 */
public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        User user = new User();
        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter Message: ");
            String msg = reader.readLine();
            user.publish(msg);
            user.consume();
        }
    }
}
