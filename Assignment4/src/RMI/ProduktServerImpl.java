package RMI;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;


public class ProduktServerImpl extends UnicastRemoteObject implements ProduktServer
	{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//private ArrayList<Produkt> alleProdukte;
		private FIFOPuffer f;
		public ProduktServerImpl() throws RemoteException
		{
			//alleProdukte = new ArrayList<Produkt>();
			f = new FIFOPuffer(10);
		}
		
		public void addProdukt(Produkt ObjRef) throws RemoteException
		{
		f.einfuegen(ObjRef);
		}
		
		public Produkt ausleseProdukt()  throws RemoteException
		{
		return f.auslese();
		}
	}
