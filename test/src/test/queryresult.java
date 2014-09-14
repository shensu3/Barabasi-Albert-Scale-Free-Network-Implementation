package test;

import java.io.*;
/*class to create an object to hold ip address and port 
 * to asnwer some queries and
 * send data to the new nodes
 */
public class queryresult implements Serializable {
/**
	 * 
	 */
private static final long serialVersionUID = 1L;
public int size=0;
public String ip[]=new String[20];
public int port[]=new int[20];
public queryresult()
{
	
}
public queryresult(int z, String ip1, int port1) {
	// TODO Auto-generated constructor stub
	size=z;
	ip[this.size]= ip1;
	port[this.size] = port1;
}
public queryresult(int z) {
	size=z;
}

public void display()
{
	for (int i=0;i<size;i++)
	{
		System.out.println(ip[i]+"           "+port[i] );
	}
}
}
