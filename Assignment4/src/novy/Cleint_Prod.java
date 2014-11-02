package novy;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by jm on 10/19/2014.
 */
public class Cleint_Prod implements Runnable {
    Mensch ms;
    Server sr;
    Prod_Con_Methods vzdalenyPC;

    public Cleint_Prod(Mensch ms, Server server) throws RemoteException, NotBoundException, MalformedURLException {
        this.ms = ms;
        this.sr = server;
        vzdalenyPC = (Prod_Con_Methods) Naming.lookup("rmi://localhost/PKP");
        System.out.println("Connection to the RMI was OK");

    }


    @Override public void run() {
        try {
            sr.produce(ms);
            System.out.println("Mensch produced " + " current size of Server: "  + sr.size());
        } catch (InterruptedException|RemoteException e) {
            e.printStackTrace();
        }
    }
}
