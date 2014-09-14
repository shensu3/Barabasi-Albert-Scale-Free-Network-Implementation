package test;
import java.io.*;
import java.util.*;
import java.net.*;
@SuppressWarnings("unused")
/*this class is used to listen for each connection in the server 
 * to compare the received dvrp-list object of class type routerList
 */
public class listenThread extends Thread {
private routerList temprl;
private node n;
private InputStream is;
private ObjectInputStream ois;
private int total;
private Socket s;
 public listenThread(Socket s1,node n1)
 {
	 System.out.println("each connection constructor");
	 n=n1;		 
	 s=s1;
 }
public void run()
 {
	    int i=0;
		System.out.println("each connection listen");
	    temprl=null;
			try (
	            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
				)
				{
				while(true)
				{
					if((temprl=(routerList) in.readObject())!=null)
					{
					n.rl.compare(temprl,s); //calling the routerList function to compare using dvrp protocol
					temprl=null;
					}
			}
				}
			catch (Exception e) {
			
				e.printStackTrace();
			}
			finally{
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			}		
 }
 
}
