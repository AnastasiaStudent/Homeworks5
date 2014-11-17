package RMI;

import java.io.Serializable;

public class Produkt implements Serializable  {
private String pName;

public Produkt (String name)
{
pName = name;	
}

public String getpName() {
	return pName;
}

public void setpName(String pName) {
	this.pName = pName;
}
 
}