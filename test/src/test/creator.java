package test;
import java.io.*;
import java.util.*;
import java.net.*;
@SuppressWarnings("unused")
/*This class is used to create a central hub kind of m0 node
 * which all other m0 nodes will connect to
 * It is a high degree m0 node run (maybe) one such instance to create an initial configuration
 */
public class creator
{
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException 
	{
		System.out.println("Enter port number of center m0 node");
		Scanner in=new Scanner(System.in);
		node m=new node(in.nextInt());
		m.listen();
		m.startClientListeners();
		m.startClientwriters();
		m.startUDP();
		m.startUDPQuery();
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