package novy;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI interface
 */
public interface Prod_Con_Methods extends Remote {
    public void produce(Mensch e) throws RemoteException, InterruptedException;

    public Mensch consume() throws RemoteException, InterruptedException;

    public int size() throws RemoteException, InterruptedException;
}
