//package Fe;
//
///**
// * Created by jm on 11/4/2014.
// */
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//import java.util.Scanner;
//
///*
// * The Client that can be run both as a console or a GUI
// */
//public class Client {
//
//    // for I/O
//    private ObjectInputStream sInput;    // to read from the socket
//    private ObjectOutputStream sOutput;    // to write on the socket
//    private Socket socket;
//
//    // the server, the port and the username
//    private String server, username;
//    private int port;
//
//    /**
//     * Constructor called by console mode
//     * server: the server address
//     * port: the port number
//     * username: the username
//     */
//    public Client(int port, String username, String server) {
//        this.port = port;
//        this.username = username;
//        this.server = server;
//    }
//
//    /*
//     * To start the Client in console mode use one of the following command
//     * > java Client
//     * > java Client username
//     * > java Client username portNumber
//     * > java Client username portNumber serverAddress
//     * at the console prompt
//     * If the portNumber is not specified 1500 is used
//     * If the serverAddress is not specified "localHost" is used
//     * If the username is not specified "Anonymous" is used
//     * > java Client
//     * is equivalent to
//     * > java Client Anonymous 1500 localhost
//     * are eqquivalent
//     *
//     * In console mode, if an error occurs the program simply stops
//     * when a GUI id used, the GUI is informed of the disconnection
//     */
//    public static void main(String[] args) throws IOException {
//        // default values
//        int portNumber = 1500;
//        String serverAddress = "localhost";
//        String userName = "Anonymous";
//
//        // depending of the number of arguments provided we fall through
//        switch (args.length) {
//            // > javac Client username portNumber serverAddr
//            case 3:
//                serverAddress = args[2];
//                // > javac Client username portNumber
//            case 2:
//                try {
//                    portNumber = Integer.parseInt(args[1]);
//                } catch (Exception e) {
//                    System.out.println("Invalid port number.");
//                    System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
//                    return;
//                }
//                // > javac Client username
//            case 1:
//                userName = args[0];
//                // > java Client
//            case 0:
//                break;
//            // invalid number of arguments
//            default:
//                System.out.println("Usage is: > java Client [username] [portNumber] {serverAddress]");
//                return;
//        }
//        // create the Client object
//        Client client = new Client(portNumber, userName, serverAddress);
//        // test if we can start the connection to the Server
//        // if it failed nothing we can do
//        if (!client.start()) {
//            return;
//        }
//
//        // wait for messages from user
//        Scanner scan = new Scanner(System.in);
//        // loop forever for message from the user
//        while (true) {
//            System.out.print("> ");
//            // read message from user
//            String msg = scan.nextLine();
//            // logout if message is ja
//            if (msg.equalsIgnoreCase("ja")) {
//                client.sendMessage(new Message(Message.ja));
//                // break to do the disconnect
//                break;
//            }
//            // message WhoIsIn
//            else if (msg.equalsIgnoreCase("nein")) {
//                client.sendMessage(new Message(Message.nein));
//            } else {        // default to ordinary message
//                client.sendMessage(new Message(Message.maybe));
//            }
//        }
//        // done disconnect
//        client.disconnect();
//    }
//
//}
//
