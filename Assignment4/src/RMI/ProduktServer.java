package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProduktServer extends Remote{
	
	public  void addProdukt(Produkt ObjRef) throws RemoteException;

	public Produkt ausleseProdukt()throws RemoteException;

}
