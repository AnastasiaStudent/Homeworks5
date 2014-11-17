package Sockets;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        boolean voted = true;
        Scanner sc = new Scanner(System.in);

        ClientCommunicator client = new ClientCommunicator();
        client.connect();
        System.out.println("\nYou will be answering a question about a serial from 90's ");
        System.out.println("\nYou are allowed to vote only once");
        while (voted) {
            try {
                System.out.println("Choose from the menu - 1 is for input");
                int menuChoice = sc.nextInt();
                if (menuChoice == 1) {
                    System.out.println("Are you fan of Akta-X ? Only 'ja', 'maybe', 'nein'");
                    System.out.print("-> ");

                    String msg = sc.next();

                    if (msg.equals("ja") || msg.equals("nein") || msg.equals("maybe")) {
                        client.writeClient(msg); //send message to server & CI
                    } else {
                        System.out.println("Wrong answer");
                    }
                    client.readRemoteAnswerFromClientsStat();
                }
                voted=false;
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage() + " only number are allowed");
            }
        }
        if(voted==false) {
            System.out.println("You cannot vote twice");
        }
    }
}




