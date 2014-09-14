package test;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class UDPNode extends Thread {
private node n;
private int ipu;
/*Thread to run in each node to answer join requests
 */
public UDPNode(node n1)
{
	n=n1;
	ipu=n.port+1;
	System.out.println("udp join thread port:"+ipu);
}
		public void run()
		{
			try
			{
			@SuppressWarnings("resource")
			DatagramSocket serverSocket = new DatagramSocket(n.port+1); 
			byte[] receiveData = new byte[1024]; 
			 while(true) 
		        { 
				 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				 serverSocket.receive(receivePacket);
				 //System.out.println(receivePacket.getData());
				 ByteArrayInputStream bais=new ByteArrayInputStream(receivePacket.getData());
			     DataInputStream dais=new DataInputStream(bais);
			     int numofcon=dais.readInt();
			     System.out.println(numofcon);
			     int random;
				 queryresult result;
				 List<listMem> integerList = new ArrayList<>();
				
				 	  if(n.rl.rowCount < 10) // we assume max size of the network to be 10
					     {
					    	 for(int y=0;y <n.rl.rowCount;y++)
					    	 {
					    		 int rowdegree=n.rl.degree[y];
					    		int num=0;
					    		 while(num < rowdegree)
					    		 {
					    			 listMem b=new listMem(n.rl.ip[y],n.rl.port[y]);
					    			
					    			 integerList.add(b);
					    			 num++;
					    		 }
					    	 }
					     
					    	 result=new queryresult(numofcon);	 
					    	 
					    	 for(int z=0;z<numofcon;z++)
					    	 {
					    		random= (int )(Math.random() *integerList.size());
					    		String ip1= integerList.get(random).IPAddress;
					    		int port1=integerList.get(random).ipport;
						    	result.ip[z]=new String(ip1);
						    	result.port[z]=port1;
					    		integerList.removeAll(Collections.singleton(integerList.get(random)));
					    	 }
					     }
				 	  else // network size = full
				 	  {
				 		  result=null;
				 	  }     
			     InetAddress IPAddress = receivePacket.getAddress(); 
			     int port = receivePacket.getPort();
			     ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			     ObjectOutput oo = new ObjectOutputStream(bStream);
			     oo.writeObject(result);
			     byte[] serializedMessage = bStream.toByteArray();
			     DatagramPacket sendPacket = new DatagramPacket(serializedMessage, serializedMessage.length, IPAddress, port);
			     serverSocket.send(sendPacket);
		        }
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}		
}
