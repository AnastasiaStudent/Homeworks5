package novy;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jm on 10/28/2014.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, RemoteException, MalformedURLException, NotBoundException {
        Mensch ms = new Mensch(5, "Prvni");
        Server server = new Server();

        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        registry.rebind("PKP", server);
        // Naming.rebind("Counter", myCounter);
        System.out.println("Server started");



        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new Cleint_Prod(ms, server));
        executorService.execute(new Cleint_Prod(ms, server));
        executorService.execute(new Client_Cons(server));
        executorService.shutdown();



    }
}
