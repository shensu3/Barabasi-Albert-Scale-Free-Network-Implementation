package test;
import java.io.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.util.*;
@SuppressWarnings("unused")
public class newnode {
	@SuppressWarnings("resource")
	/*class to create new node which will connect by sending a join request 
	 * to the m0 node. 
	 * 
	 */
	public static void main(String[] args) throws IOException 
	{
		System.out.println("Enter the port number of new node");
		Scanner in=new Scanner(System.in);
		node m=new node(in.nextInt());
		m.listen();
		System.out.println("Enter the ip address and UDP Query port of m0 node as well as the number of connections");
		m.UdpJoinReq(in.next().toString(),in.nextInt(),in.nextInt()); // calling the join req method to get ip-port pairs as per requirement.
		m.startClientListeners();
		m.startClientwriters();
		m.startUDPQuery(); 
		try
		{
			Thread.sleep(5000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		m.rl.display();
		while(true)
		{
		try{
			Thread.sleep(5000);
			//m.rl.display();
			}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		}
	}
}
