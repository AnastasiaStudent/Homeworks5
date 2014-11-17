package novy.Client;

import novy.Server.Car;
import novy.Server.Prod_Con_Methods;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class Produ {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {
        Car ms = new Car(5, "asd");
        Prod_Con_Methods vzdalenyPC = (Prod_Con_Methods) Naming.lookup("rmi://localhost/A4");
        //Prod_Con_Methods vzdalenyPC = (Prod_Con_Methods) Naming.lookup("rmi://134.103.212.238/A4");

        vzdalenyPC.produce(ms);
        System.out.println(ms.toString() + " produced. Current size of Server: " + vzdalenyPC.size());

        vzdalenyPC.consume();
        System.out.println(ms.toString() + " consumed. Current size of Server: " + vzdalenyPC.size());

    }
}
