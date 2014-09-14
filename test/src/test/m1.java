package test;
import java.io.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.util.*;
@SuppressWarnings("unused")
/*class to create
 * m0 nodes which will connect
 *  to the high degree m0node
 */
public class m1 {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException 
	{
		System.out.println("Enter the port number of this node");
		Scanner in=new Scanner(System.in);
		node m1=new node(in.nextInt());
		m1.listen();
		System.out.println("Enter the ip address and port number of m0 node");
		String ip=in.next().toString();
		int port=in.nextInt();
		m1.connect(ip,port);
		m1.startClientListeners();
		m1.startClientwriters();
		m1.startUDP();
		m1.startUDPQuery();
		while(true)
		{
		try{
			Thread.sleep(5000);
			}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		}
	}
}
