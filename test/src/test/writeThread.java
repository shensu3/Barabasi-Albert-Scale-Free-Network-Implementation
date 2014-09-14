package test;
import java.io.*;
import java.util.*;
import java.net.*;
@SuppressWarnings({ "unused"})
/*Thread run by node to write from server to 
 * all its clients (i e each connection)
 * broadcasting to neighbor nodes its routing table table
 */
public class writeThread extends Thread
{
	private node n;
	private int total;
	private Socket s;
public writeThread(Socket s1,node n1)
	 {
		 n=n1;		 
		 s=s1;
	 }
	public void run()
	 {
		    int i=0;
			System.out.println("each connection write");
				try (
					ObjectOutputStream outs = new ObjectOutputStream(s.getOutputStream());
					)
					{
					while(true)
					    {
						outs.reset(); 	
						outs.writeObject(n.rl); //writes only the first conrtuctor value with 1 row
						outs.reset();
	                    Thread.sleep(600);
						}
				}
				catch (Exception e) {
				
					e.printStackTrace();
				}
				try {
					s.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
	 }
	 
}
