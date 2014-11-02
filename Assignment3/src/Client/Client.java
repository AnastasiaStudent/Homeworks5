package Client;

import Server.OurServer;

import java.io.*;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (
            Scanner sc = new Scanner(System.in);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("answers.ser"));
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {

            UUID uniqueKey = UUID.randomUUID();
            Socket ss = new Socket("localhost", OurServer.port); // vznikne socket, ktery se pripoji na dany port a host
            System.out.println("Client Connection to Server is OK! " + ss.getInetAddress() + " " + ss.getLocalPort());
            //        Question qs = new Question(uniqueKey); // ulozi UUID a pripravi object pro Q/A
            System.out.println("\nProvide your opinion or ask server for information.");

            while (true) {

                System.out.println("Choose from the menu - 1 is for input; 2 is for information");
                try {
                    int menuChoice = sc.nextInt();
                    if (menuChoice == 1) {
                        System.out.println("your vote please here");
                        String msg = sc.next();
                        oos.writeInt(menuChoice);
                        oos.writeObject(uniqueKey);
                        oos.writeUTF(msg);
                        oos.flush();
                        //                    qs.setDeineAbstimmung(sc.next());  // we use here Scanner for input
                    } else if (menuChoice == 2) {
                        System.out.println("You want to dispaly msg");
                        oos.writeInt(2);
                        oos.flush();
                        //                    qs.getDeineAbstimmungPerClient(); // return answers
                    }

                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage() + "only number are allowed");
                }
            }

        }

    }
}
