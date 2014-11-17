package novy.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI interface
 */
public interface Prod_Con_Methods extends Remote {
    public void produce(Car e) throws RemoteException, InterruptedException;
    public Car consume() throws RemoteException, InterruptedException;
    public int size() throws RemoteException, InterruptedException;
}
