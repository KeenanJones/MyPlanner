package software_masters.business_planner;

import java.rmi.registry.*;

/**
 * @author alexander.garuba
 * 
 *         This class represents any user logging in and interacting with the
 *         RMI business plan database server.
 * 
 * @since 3.12.2019
 */
public class DatabaseClient
{
	
	public static void main(String [] args)
	{
		new DatabaseClient().ping();
	}
	public void ping()
	{
		
		try
		{
			System.out.println("Connecting to registry...");
			Registry registry = LocateRegistry.getRegistry("127.0.0.1");
			DatabaseClient stub = (DatabaseClient) registry.lookup("RemoteHello");
			
			String s = stub.sayHello();
			System.out.println(s);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("problem");
		}
	}

}
