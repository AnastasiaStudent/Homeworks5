package RMI;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class ServerMain {

	public static void main(String[] args) {
		try
		{
		Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		ProduktServerImpl server = new ProduktServerImpl();
		Naming.rebind("rmi://localhost/ProduktServer", server);
		}
		catch(Exception e) {} 
	}
}
