package test;
import java.io.*;
import java.util.*;
import java.net.*;
@SuppressWarnings({ "unused" })
/*This thread is used for broadcasting DVRP objects of routerList class type
 */
public class clienWrite extends Thread {
	private routerList rs;
	private node n;
	private int i;
	public clienWrite(node n1,int i)
	{
		n=n1;		 
		this.i=i;
	}
	public void run()
	{
		System.out.println("client write "+i);
				try
				(
					ObjectOutputStream outs = new ObjectOutputStream(n.clients[i].getOutputStream());
				)
				{
				while(true)
				{
					outs.writeObject(n.rl);
					outs.reset();
                    Thread.sleep(600); //neighbour broadcast timeout
				}
				}
				catch(Exception e)
				{
					e.getStackTrace();
				}
	}

}
