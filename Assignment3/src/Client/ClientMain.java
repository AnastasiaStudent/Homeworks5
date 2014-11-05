package Client;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class ClientMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);

        UUID uniqueKey = UUID.randomUUID();
        Client2 client = new Client2(uniqueKey);
        client.connect();

        // loop forever for message from the user
        while (true) {
            try {
                System.out.println("\nYou will be answering a question. ");
                System.out.println("Choose from the menu - 1 is for input; 2 is for information");
                int menuChoice = sc.nextInt();
                if (menuChoice == 1) {
                    System.out.println("please answer this question: Is he black ? Only ja, maybe, nein");
                    System.out.print("-> ");

                    // read message from user
                    String msg = sc.next();

                    if (msg.equalsIgnoreCase("ja")) {
                        client.sendMessage("ja");
                    } else if (msg.equalsIgnoreCase("nein")) {
                        client.sendMessage("nein");
                    } else if (msg.equalsIgnoreCase("maybe")) {
                        client.sendMessage("maybe");
                    } else {
                        System.out.println("Wrong answer, you will be disconnected");
                        client.disconnect();
                        break;
                    }
                } else if (menuChoice == 2) {
                    client.sendMessage("Statistics");
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage() + " only number are allowed");
                client.disconnect();
                break;
            }
        }
    }
}



