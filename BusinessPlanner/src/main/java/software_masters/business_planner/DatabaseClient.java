package software_masters.business_planner;

import java.rmi.registry.*;
import java.rmi.RemoteException;

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
	private final String IP;
	private final int PORT = 1098;
	private Database database;
	private User u;
	
	public static void main(String[] args)
	{
		DatabaseClient c = new DatabaseClient("10.14.1.80");
	}
	
	/**
	 * Constructor: Connects to Server through RMI, allows user to call methods
	 * to view, create, edit plans in Database
	 */
	public DatabaseClient(String ip_address)
	{
		IP = ip_address;

		try
		{
			Registry registry = LocateRegistry.getRegistry(IP,PORT);
			database = (Database) registry.lookup("RMIDatabase");
			String s = database.save();
			System.out.println(s);
		} catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("registry lookup failed in DatabaseClient");
		}
	}
}
