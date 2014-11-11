package Client;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Scanner sc = new Scanner(System.in);

        ClientCommunicator client = new ClientCommunicator();
        client.connect();

        while (true) {
            try {
                System.out.println("\nYou will be answering a question. ");
                System.out.println("Choose from the menu - 1 is for input; 2 is for information");
                int menuChoice = sc.nextInt();
                if (menuChoice == 1) {
                    System.out.println("Please answer this question: Are you fan of Akta-X ? Only 'ja', 'maybe', 'nein'");
                    System.out.print("-> ");

                    String msg = sc.next();

                    if (msg.equals("ja") || msg.equals("nein") || msg.equals("maybe")) {
                        client.writeClient(msg); //send message to server & CI
                    } else {
                        System.out.println("Wrong answer; you will be disconnected");
                        client.disconnect();
                        break;
                    }
                } else if (menuChoice == 2) {
                    client.clientsInformation();
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage() + " only number are allowed");
                client.disconnect();
                break;
            }
        }
    }
}



