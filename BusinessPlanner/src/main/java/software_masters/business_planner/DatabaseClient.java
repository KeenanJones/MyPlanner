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
	private final int PORT = 1099;
	private Database database;
	private User u;
	
	
	/**
	 * Constructor: Connects to Server through RMI, allows user to call methods
	 * to view, create, edit plans in Database
	 */
	public DatabaseClient(String IP)
	{
		this.IP = IP;

		try
		{
			Registry registry = getRegistry();
			database = (Database) registry.lookup("RMIDatabase");
			
		} catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("registry lookup failed in DatabaseClient");
		}
	}
	
	/**
	 * This function returns the Registry object created by DatabaseServer
	 * link: https://www.programcreek.com/java-api-examples/?class=java.rmi.registry.LocateRegistry&method=createRegistry
	 * @return the registry object
	 * @throws RemoteException
	 */
	private Registry getRegistry() throws RemoteException
	{
		Registry registry = null;
		try
		{
			System.out.println("Connecting to registry --> port " + PORT);
			registry = LocateRegistry.getRegistry(IP,PORT);
			
		} catch (RemoteException e)
		{
			e.printStackTrace();
			System.out.println("get registry in DatabaseClient failed");
		}
		return registry;
	}
}
