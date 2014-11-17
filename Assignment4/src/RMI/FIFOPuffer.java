package RMI;

public class FIFOPuffer {
private int in_index;
private int out_index;
private int groesse;
private Produkt[] fifo;

FIFOPuffer(int groesse)
	{
	if (groesse < 2) groesse = 2;
	this.groesse = groesse;
	fifo = new Produkt[groesse];
	in_index = out_index = 0;
	}

public synchronized Produkt auslese()
	{
	Produkt temp;
	while (in_index == out_index)
		{
		try
			{
			wait();
			}
		catch(InterruptedException e){}
		}
	temp = fifo[out_index];
	if(out_index == groesse -1 ) out_index = 0;
								else out_index ++;

	notifyAll();
	return temp;
	}

public synchronized void einfuegen(Produkt p)
	{
	while ((in_index + 1)%groesse == out_index) //
		{
		try
		{
		wait();
		}
	catch(InterruptedException e){}
	}
	
fifo[in_index] = p;
if(in_index == groesse -1 ) in_index =0;
							else in_index++;
notifyAll();

}
}
