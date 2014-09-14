package test;
import java.io.*;
import java.util.*;
import java.net.*;
@SuppressWarnings("unused")
/*this Thread is used to create a number of threads for each connection
 * used for the concurrent serever model.
 */
public class listenerThread extends Thread {
	private ServerSocket s1;
	private node n;
	public volatile routerList rl;
public listenerThread(ServerSocket s,node n1)
{
	s1=s;
	n=n1;
	System.out.println("listen thread "+n.port );
}
public void run()
{
	while(true)
	{
			try {
			Socket clientsock = n.s1.accept();
			++n.rl.degree[0]; //incrementing the degree
			new listenThread(clientsock,n).start(); //start thread to listen for each client
			new writeThread(clientsock,n).start(); //start thread to broadcast for each client
	            }
			catch (Exception e) 
			{
				e.printStackTrace();
			}
	}
}
	
}
