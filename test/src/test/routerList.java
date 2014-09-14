package test;
import java.io.*;
import java.net.*;
/*routerList class to create objects for each node 
 * to maintain router list table
 * for itself and do comparisons to see whether cost or degree is less
 * and adjust accordingly
 */
public class routerList implements Serializable {
	private static final long serialVersionUID = 1L;
	public String[] ip=new String[20];
	public int[] port=new int[20];
	public int[] degree=new int[20];
	public int[] cost=new int[20];
	public String[] nextip=new String[20];
	public int[] nextPort=new int[20];
	public int rowCount;
	
//constructor initialize the first row with the value of the 
	public routerList(String ip,int port,int degree,int cost,String nextip,int nextport)
	{
		
		this.ip[0]=ip;
		this.port[0]=port;
		this.degree[0]=degree;
		this.cost[0]=cost;
		this.nextip[0]=nextip;
		this.nextPort[0]=nextport;
		rowCount=1;
	}
public routerList() {
	}
	//returns location i value (row) of the ip address and port number -1 if it does not exist
	public int locate(String ipe,int porte)
	{
		int i;
		boolean flag=false;
		for(i=0;i<this.rowCount;i++)
		{
			if((this.ip[i].equals(ipe))&&(this.port[i]==porte))
			{
				flag=true;
				break;
			}
		}
		if (flag==true)
		{
		return i;
		}
		else
		{
		return -1;
		}
	}
	public void display() 
	{
		System.out.println("Router entry \t\tip \tport degree cost  nextip     nextport ");
		for(int k=0;k<rowCount;k++)
		{
		System.out.println("router entry["+k+"]"+" -> "+ip[k]+" "+port[k]+"    "+degree[k]+"    "+cost[k]+"  "+nextip[k]+" "+nextPort[k]);
		}
	}
	public queryresult farthest() //farthest node
	{
		int farthest=0;
		for(int i=1;i<this.rowCount;i++)
		{
			if(this.cost[farthest]<this.cost[i])
			{
				farthest=i;
			}
		}
		queryresult result=new queryresult();
		int line=0;
		for(int i=1;i<this.rowCount;i++)
		{
			if(this.cost[i]==this.cost[farthest])
			{
		result.ip[line]=this.ip[i];
		result.port[line]=this.port[i];
		++line;
		result.size=line;
			}
		}
		return result;
	}
	public void compare(routerList r,Socket w)
	{
		int i,k=0;
		for(i=0;i<r.rowCount;i++)
		{
		   k=this.locate(r.ip[i],r.port[i]);
		   //node entry does not exist so add it and increment row count.
		   if(k==-1)
		   {
			   this.ip[this.rowCount]=r.ip[i];
			   this.port[this.rowCount]=r.port[i];
			   this.degree[this.rowCount]=r.degree[i];
			   this.cost[this.rowCount]=r.cost[i]+1;
			   this.nextip[this.rowCount]=w.getInetAddress().getHostAddress().toString();
			   this.nextPort[this.rowCount]=w.getPort();
			   ++this.rowCount;
			   System.out.println("new entry added for "+w.getLocalPort()+"its new rowcount is"+this.rowCount+" new entry-> "+r.port[i] );
			   this.display();
		   }
		   //exists
		   
		   else
		   {
			   //if degree is greater and if cost is lesser -> update that entry
			   if(this.cost[k]>r.cost[i])
			   {
				   this.ip[k]=r.ip[i];
				   this.port[k]=r.port[i];
				   this.cost[k]=r.cost[i]+1;
				   this.nextip[k]=w.getInetAddress().getHostAddress().toString();
				   this.nextPort[k]=w.getPort();
			   }
			   if(this.degree[k]<r.degree[i])
			   {
				   this.degree[k]=r.degree[i];
			   }
		   }
		   k=0;
		}
	}
}