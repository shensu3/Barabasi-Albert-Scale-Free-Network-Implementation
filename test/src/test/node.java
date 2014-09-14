package test;
import java.io.*;
import java.net.*;
public class node
{
	public volatile routerList rl;
	public ServerSocket s1;//1 server socket
	public int port;//port number of that server
	public Socket clients[]=new Socket[20];//array of clients
	public int count=0;//to count number of clients we have
//constructor
public node(int tport)
{
	
	count=0;
	try
	{
	s1=new ServerSocket(tport);
	s1.getInetAddress();
	this.port=tport;
	rl=new routerList(InetAddress.getLocalHost().getHostAddress().toString(),tport,0,0,InetAddress.getLocalHost().getHostAddress().toString(),tport);
	} 
	catch (IOException e) 
	{
		
		e.printStackTrace();
	}
	finally
	{
	  System.out.println("constructor ends");
	}
}
//listener
public void listen()
{
	new listenerThread(s1,this).start();
}
//to connect using ip and port values
public void connect(String ip,int port)
{
	try 
	{
		this.clients[this.count]=new Socket(ip,port);
		System.out.println(this.clients[this.count].getLocalPort()+"connected with "+this.clients[this.count].getInetAddress().getHostAddress().toString());
		++this.count;
		++this.rl.degree[0];
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	
}
//connecting using node
public void connect(node m)
{
	try 
	{
		this.clients[this.count]=new Socket("127.0.0.1",m.port);
		System.out.println(this.clients[this.count].getLocalPort()+"connected with"+this.clients[this.count].getPort());
		++this.count;
		++this.rl.degree[0];
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
}
//start the client listening threads
public void startClientListeners()
{
	for(int i=0;i<this.count;i++)
	{
	 new clienListen(this,i).start();
	}
}
//start threads to write to all clients
public void startClientwriters()
{
	for(int i=0;i<this.count;i++)
	{
	 new clienWrite(this,i).start();
	}
}
//start udp thread to handle join requests (called only for m0 nodes)
public void startUDP()
{
	new UDPNode(this).start();
	
}
//start thread to answer queries (degree and farthest node - all nodes)
public void startUDPQuery()
{
	new UDPQuery(this).start();
}
//method to send join request (called only by the non-m0 nodes)
public void UdpJoinReq(String m0ip,int m0port,int con)
{
	try 
	{
		byte[] receiveData = new byte[1024]; 
		 DatagramSocket clientSocket = new DatagramSocket(); 
		 InetAddress IPAddress = InetAddress.getByName(m0ip); 
		 ByteArrayOutputStream baos=new ByteArrayOutputStream();
	     DataOutputStream daos=new DataOutputStream(baos);
	     daos.writeInt(con);
		 daos.flush();
		 byte[] bytes=baos.toByteArray();
		 DatagramPacket packet = new DatagramPacket(bytes, bytes.length, IPAddress,m0port);
		 clientSocket.send(packet);
		 clientSocket.setSoTimeout(10000);
		 DatagramPacket recvpkt = new DatagramPacket(receiveData,receiveData.length); 
		 clientSocket.receive(recvpkt); 
		 ByteArrayInputStream bais=new ByteArrayInputStream(recvpkt.getData());
		 ObjectInputStream iStream = new ObjectInputStream(bais);
		 queryresult result = (queryresult) iStream.readObject();
		 if(result!=null)
		 { 
			 for(int i=0;i<result.size;i++)
			 {
			 this.connect(result.ip[i],result.port[i]);
			 }
		 }
		 else 
		 {
			 System.out.println("error connecting... seems Network capacity is full... exiting...");
		 } 
		 daos.close();
		 iStream.close();
		 clientSocket.close();
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	
}

public void close()
{
	try {
		s1.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}