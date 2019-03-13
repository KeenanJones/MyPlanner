package software_masters.business_planner;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import junit.framework.TestCase;

public class DatabaseServiceTest extends TestCase
{

	private final int PORT = 1098;
	private final String IP = "localhost";
	
	public DatabaseServiceTest(String name)
	{
		super(name);
	}

	protected void setUp() throws Exception
	{
		super.setUp();
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void RMITest()
	{
		Registry registry;
		Database database;
		String s="";
		try
		{
			DatabaseServer server = new DatabaseServer();

			
			//server RMI
			System.out.println("Creating registry --> port " + PORT);
			registry = LocateRegistry.createRegistry(PORT);
			registry.rebind("RMIDatabase", server);
			
			//client
			registry = LocateRegistry.getRegistry(IP,PORT);
			database = (Database) registry.lookup("RMIDatabase");
			s = database.save();
			
		} catch (RemoteException e)
		{
			e.printStackTrace();
			System.out.println("creating registry failed");
		} catch (NotBoundException e)
		{
			e.printStackTrace();
			System.out.println("registry lookup failed");
		}
		
		assertEquals(s, "save() called in Server");
	}

}
