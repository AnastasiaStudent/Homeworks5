package Umfrage;


public class Client {
    private static ClientCommunicator communicator;

    public Client(String verbindungsdaten) {
        communicator = new ClientCommunicator(verbindungsdaten);
//        communicator.communicate("Ja");
    }


    public static void main(String[] argumente) {
        Client client = new Client(argumente[0]);

        Ergebnis ergebnis = communicator.communicate(argumente[1]);

        if (ergebnis == null) {

        } else {
            System.out.println(ergebnis.ausgabeStand());
            //System.out.println(ergebnis.ausgabeStand());
        }
    }

}
