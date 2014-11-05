package novy;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, RemoteException, MalformedURLException, NotBoundException {
        // create Produkt where are going to consume, produce
        Mensch ms = new Mensch(5, "Prvni");
        //Server connection
        Server server = new Server();

        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        registry.rebind("PKP", server);
        // Naming.rebind("Counter", myCounter);
        System.out.println("Server started");


        /*
        2 Prod + 1 Consumer
         */
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new Cleint_Prod(ms, server));
        executorService.execute(new Cleint_Prod(ms, server));
        executorService.execute(new Client_Cons(server));
        executorService.shutdown();



    }
}
