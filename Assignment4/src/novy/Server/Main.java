package novy.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Eigentlich ServerMain, der die Clients selber ausfuhrt
 */
public class Main {
    public static void main(String[] args) throws RemoteException {
        Car ms = new Car(5, "ferrari");
        //Server connection
        Server server = new Server();
        // registrierung des Entfernen objekten in RMI Registry.
        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        registry.rebind("A4", server);

        System.out.println("Server started");


        /*
         * 2 Prod + 1 Consumer
         * Wird gewartet im theard pool
         */
        //        ExecutorService executorService = Executors.newFixedThreadPool(30);
        //        for (int i = 0; i < 20; i++) {
        //            executorService.execute(new Cleint_Prod(ms));
        //        }
        //
        //        for (int i = 0; i < 10; i++) {
        //            executorService.execute(new Client_Cons());
        //
        //        }

        //        executorService.execute(new Cleint_Prod(ms, server));
        //        executorService.execute(new Cleint_Prod(ms, server));
        //        executorService.execute(new Client_Cons(server));
        //        executorService.execute(new Client_Cons(server));

        //        executorService.shutdown();


    }
}
