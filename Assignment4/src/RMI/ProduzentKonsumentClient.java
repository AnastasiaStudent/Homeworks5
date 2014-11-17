package RMI;
import java.rmi.*;
import java.io.*;

public class ProduzentKonsumentClient 
{

	static Produkt prod1 = new Produkt("E-Klasse");
	static Produkt prod2 = new Produkt("S-Klasse");
	
	public static void main(String[] args) 
	{
		
	
		try
		{
		
		ProduktServer server = (ProduktServer)Naming.lookup("rmi://localhost/ProduktServer"); //Stub von registry
		
		server.addProdukt(prod1);
		server.addProdukt(prod2);
		server.addProdukt(prod1);
		server.addProdukt(prod2);
		server.addProdukt(prod1);
		server.addProdukt(prod2);
		server.addProdukt(prod1);
		server.addProdukt(prod2);
		server.addProdukt(prod1);
		server.addProdukt(prod1);
	
		/*System.out.println(server.ausleseProdukt().getpName());
		System.out.println(server.ausleseProdukt().getpName());
		System.out.println(server.ausleseProdukt().getpName());
		System.out.println(server.ausleseProdukt().getpName());
		System.out.println(server.ausleseProdukt().getpName());
		System.out.println(server.ausleseProdukt().getpName());
		System.out.println(server.ausleseProdukt().getpName());
		System.out.println(server.ausleseProdukt().getpName());
		System.out.println(server.ausleseProdukt().getpName());
	*/
		}
		catch(Exception e){System.out.println(e.getMessage());}
		System.exit(0);
	}	
}
