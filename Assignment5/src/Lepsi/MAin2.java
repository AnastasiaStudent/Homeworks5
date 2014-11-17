package Lepsi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Created by jm on 11/17/2014.
 */
public class MAin2 {
    public static void main(String[] args) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, IOException {
        EndPoint endPoint = new EndPoint("Queue");
        Scanner sc = new Scanner(System.in);


        endPoint.setName("john");
        while (true) {

            String message = sc.next();
            endPoint.sendMessage(message);
        }
    }
}
