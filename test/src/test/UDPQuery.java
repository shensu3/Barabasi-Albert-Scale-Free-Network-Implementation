package test;
import java.io.*;
import java.net.*;
/*thread that runs in each node
 * to handle queries
 */
public class UDPQuery extends Thread {
	private node n;
	public UDPQuery(node n1)
	{
		n=n1;
	}
	@SuppressWarnings("resource")
	public void run()
	{
		try
		{
		int tport=n.port+2;
		DatagramSocket serverSocket = new DatagramSocket(tport); 
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		 while(true) 
	        { 
			 System.out.println("Started query thread "+tport);
			 serverSocket.receive(receivePacket);
			 ByteArrayInputStream bais=new ByteArrayInputStream(receivePacket.getData());
	 		 ObjectInputStream iStream = new ObjectInputStream(bais);
	 		 String choice = (String) iStream.readObject();
			 System.out.println(choice);
			 InetAddress IPAddress = receivePacket.getAddress(); 
			 int port=receivePacket.getPort();
			 if(choice.equals("degree"))
			 {
				 System.out.println("getting here");
				 ByteArrayOutputStream baos=new ByteArrayOutputStream();
			     DataOutputStream daos=new DataOutputStream(baos);
			     daos.writeInt(n.rl.degree[0]);
				 daos.flush();
				 byte[] bytes=baos.toByteArray();
				 DatagramPacket packet = new DatagramPacket(bytes, bytes.length, IPAddress,port);
				 serverSocket.send(packet);
			 }
			 else if(choice.equals("farthest"))
			 {
				 ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			     ObjectOutput oo = new ObjectOutputStream(bStream);
			     queryresult result = n.rl.farthest();
			     oo.writeObject(result);
			     byte[] serializedMessage = bStream.toByteArray();
			     DatagramPacket sendPacket = new DatagramPacket(serializedMessage, serializedMessage.length, IPAddress, port);
			     serverSocket.send(sendPacket); 
			 }
			
	        }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
