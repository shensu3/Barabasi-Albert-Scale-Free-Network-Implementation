package test;
import java.io.*;
import java.net.*;
import java.util.*;
public class queryapp {
	@SuppressWarnings("unused")
	/*class to query desired nodes
	 * run this to query particular node
	 */
	public static void main(String[] args)
	{
		 try {
			  String choice;
			  int ch;
			  Scanner inFromUser = new Scanner(System.in); 
			  System.out.println("Enter ip address & port number of the node you want to query");
		      String serverHostname = new String(inFromUser.nextLine());
		      DatagramSocket clientSocket = new DatagramSocket(); 
		      InetAddress IPAddress = InetAddress.getByName(serverHostname); 		  
		      byte[] sendData = new byte[1024]; 
		      byte[] receiveData = new byte[1024]; 
		      System.out.print("Enter port: ");
		      int port = inFromUser.nextInt();      
		      System.out.println("Enter choice 1.degree or 2.farthest");
		      ch=inFromUser.nextInt();
		      if(ch==1)
		      {
		    	  choice="degree";
		      }
		      else
		      {
		    	  choice="farthest";
		      }
		      ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			  ObjectOutput oo = new ObjectOutputStream(bStream);
			     oo.writeObject(choice);
			     byte[] serializedMessage = bStream.toByteArray();
			     DatagramPacket sendPacket = new DatagramPacket(serializedMessage, serializedMessage.length, IPAddress, port);
			     clientSocket.send(sendPacket); 
		         clientSocket.setSoTimeout(10000);
		      if(choice.equals("degree"))
		      {
		    	  	 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					 clientSocket.receive(receivePacket);
					 ByteArrayInputStream bais=new ByteArrayInputStream(receivePacket.getData());
				     DataInputStream dais=new DataInputStream(bais);
				     int i=dais.readInt();
				     System.out.println("Degree of the node you requested is "+ i);
		      }
		      else if(choice.equals("farthest"))
		      {
		    	  DatagramPacket recvpkt = new DatagramPacket(receiveData,receiveData.length); 
		    	  clientSocket.receive(recvpkt); 
		 		  ByteArrayInputStream bais=new ByteArrayInputStream(recvpkt.getData());
		 		  ObjectInputStream iStream = new ObjectInputStream(bais);
		 		  queryresult result = (queryresult) iStream.readObject();
		 		  System.out.println("Farthest node ip and port is ");
		 		  result.display();
		      }
		      inFromUser.close();
		      clientSocket.close();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	}
}
