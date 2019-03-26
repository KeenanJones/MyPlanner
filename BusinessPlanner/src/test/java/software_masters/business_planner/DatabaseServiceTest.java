package software_masters.business_planner;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import junit.framework.TestCase;

public class DatabaseServiceTest extends TestCase
{

	private final int PORT = 1099;
	private final String IP = "localhost";
	
	private ArrayList<User> users;
	private ArrayList<Department> depts;
	private DatabaseServer server;
	
	private Template VMOSA,Centre_Assessment;
	
	public DatabaseServiceTest(String name)
	{
		super(name);
	}

	protected void setUp() throws Exception
	{
		users = new ArrayList<User>();
		depts = new ArrayList<Department>();
		
		//set up CS department
		VMOSA = VMOSA_Builder.generateTemplate();
		Department CS = new Department(VMOSA);
		
		depts.add(CS);
		users.add(new User("bradshaw","unicorn1", CS, true));
		
		//set up math department
		Centre_Assessment = Centre_Assess_Builder.generateTemplate();
		Department MAT = new Department(Centre_Assessment);
		
		depts.add(MAT);
		users.add(new User("wilson","hippo1", MAT, true));
		
		//set up Database
		server = new DatabaseServer(users,depts);
		//server.save();
		
		super.setUp();
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testUser()
	{
		User u = users.get(0);
		assertEquals("Username: " + "bradshaw" + "\nPassword: " + "unicorn1" + "\nDepartment: " + depts.get(0) + "\nAdmin Permissions: " + true + "\n",
						u.toString());
	}
	
	public void testXML() throws RemoteException
	{
		ArrayList<User> users1 = new ArrayList<User>();
		ArrayList<Department> depts1 = new ArrayList<Department>();
		
		//set up CS department
		Template VMOSA1 = VMOSA_Builder.generateTemplate();
		Department CS = new Department(VMOSA1);
		
		depts1.add(CS);
		users1.add(new User("bradshaw","unicorn1", CS, true));
		
		//set up math department
		Template Centre_Assessment1 = Centre_Assess_Builder.generateTemplate();
		Department MAT = new Department(Centre_Assessment1);
		
		depts1.add(MAT);
		users1.add(new User("wilson","hippo1", MAT, true));
		
		DatabaseServer expected = new DatabaseServer(users1,depts1);
		
		DatabaseServer load = DatabaseServer.load();
		
		assertTrue(expected.getDeptList().get(0).equals(load.getDeptList().get(0)));
		assertTrue(expected.getDeptList().get(1).equals(load.getDeptList().get(1)));
		assertTrue(expected.getUserList().get(0).equals(load.getUserList().get(0)));
		assertTrue(expected.getUserList().get(1).equals(load.getUserList().get(1)));
		
		assertFalse(expected.getDeptList().get(0).equals(load.getDeptList().get(1)));
		assertFalse(expected.getUserList().get(0).equals(load.getUserList().get(1)));
	}
	
	public void testDatabase()
	{
		Registry registry;
		Database database;
		try
		{
			
			//server RMI
			System.out.println("Creating registry --> port " + PORT);
			registry = LocateRegistry.createRegistry(PORT);
			registry.rebind("RMIDatabase", server);
			
			//client
			registry = LocateRegistry.getRegistry(IP,PORT);
			database = (Database) registry.lookup("RMIDatabase");
			database.save();
			
			//try login
			Department CS = new Department(VMOSA_Builder.generateTemplate());
			User bradshaw = database.login("bradshaw", "unicorn1");
			
			assertTrue(bradshaw.equals(new User("bradshaw","unicorn1", CS, true)));
			
			//add first plan
			Template newTemplate = database.makePlan(bradshaw);
			newTemplate.setYear("2019");
			TemplateSection Vision = newTemplate.getRoot();
			TemplateSection Mission = Vision.getChild(0);
			TemplateSection Objective = Vision.getChild(0);
			
			assertEquals("Vision",Vision.getName());
			
			
			
		} catch (RemoteException e)
		{
			e.printStackTrace();
			System.out.println("creating registry failed");
		} catch (NotBoundException e)
		{
			e.printStackTrace();
			System.out.println("registry lookup failed");
		}
		
		//assertEquals(s, "save() called in Server");
	}

}
