package serverPart;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by jm on 10/10/2014.
 */
public class ChatServer  extends UnicastRemoteObject implements ChatInterface {
    String msg;
    ChatInterface client;

    public ChatServer(String name) throws RemoteException {
        this.msg = name;
    }

    @Override public void postMessage(String msg) throws RemoteException {
        System.out.println("Your Message: " + msg);
    }

    @Override public String getName() throws RemoteException {
        return msg;
    }

    @Override public void setClient(ChatInterface c) throws RemoteException {
            this.client = c;
    }

    @Override public ChatInterface getClient() throws RemoteException {
        return client;
    }
}
