package novy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.LinkedBlockingQueue;

public class Server extends UnicastRemoteObject implements Prod_Con_Methods {
    Mensch ms;
    LinkedBlockingQueue<Mensch> lbq;

    public Server() throws RemoteException {
        super();
        lbq = new LinkedBlockingQueue<Mensch>(10);
    }

    public void produce(Mensch e) throws RemoteException, InterruptedException {
        this.ms = e;
        lbq.put(e);
    }

    public Mensch consume() throws RemoteException, InterruptedException {

        return lbq.take();
    }

    public int size() {
        return lbq.size();
    }

}
