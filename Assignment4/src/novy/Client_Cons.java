package novy;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by jm on 10/19/2014.
 */
public class Client_Cons implements Runnable {
    Server sr;
    Prod_Con_Methods vzdalenyPC;

    public Client_Cons(Server server) throws RemoteException, NotBoundException, MalformedURLException {
        this.sr = server;
        vzdalenyPC = (Prod_Con_Methods) Naming.lookup("rmi://localhost/PKP");
        System.out.println("Connection to the RMI was OK");
    }


    @Override public void run() {
        try {
            Mensch consument = sr.consume();
            System.out.println(consument.toString() + " current size of Server: " + sr.size());
        } catch (InterruptedException | RemoteException e) {
            e.printStackTrace();
        }


    }
}
