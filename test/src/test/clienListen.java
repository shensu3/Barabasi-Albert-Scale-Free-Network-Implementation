package test;
import java.io.*;
/* This class is used for all clients listening 
 * from their servers for Distance vector protocol packets
 * of routerList class type
 */ 
public class clienListen extends Thread {
	private routerList temprl;
	private node n;
	private int i;
	public clienListen(node n1,int i)
	{
		n=n1;
		this.i=i;
	}
	public void run()
	{
		System.out.println("client listen "+i);
				try
				(
		            ObjectInputStream in = new ObjectInputStream(n.clients[i].getInputStream());
				)
				{
				while(true)
				{
					if((temprl=(routerList) in.readObject())!=null)
					{
					n.rl.compare(temprl,n.clients[i]); //calling the method in routerList class to compare using dvrp protocol rules
					}
				}
				}
				catch(Exception e)
				{
					e.getStackTrace();
				}
	}

}
