package software_masters.business_planner;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * @author alexander.garuba
 * 
 *         This class implements the RMI business plan database server.
 * 
 * @since 3.12.2019
 */
public class DatabaseServer extends UnicastRemoteObject implements Database
{

	private static final long serialVersionUID = -4049963225747792465L;

	private final static int PORT = 1098;

	private ArrayList<User> userList;
	private ArrayList<Department> deptList;

	public static void main(String[] args)
	{
		/*
		 * try { DatabaseServer server = new DatabaseServer();
		 * 
		 * System.out.println("Creating registry --> port " + PORT); Registry registry =
		 * LocateRegistry.getRegistry(PORT); registry.rebind("RMIDatabase", server);
		 * 
		 * } catch (RemoteException e) { e.printStackTrace();
		 * System.out.println("creating registry in DatabaseServer failed"); }
		 */
	}

	public DatabaseServer() throws RemoteException
	{
		this(null, null);
	}

	/**
	 * Constructor calls super from UnicastRemoteObject and initializes lists
	 * 
	 * @throws RemoteException
	 */
	public DatabaseServer(ArrayList<User> users, ArrayList<Department> depts) throws RemoteException
	{
		super();
		userList = users;
		deptList = depts;

	}

	/**
	 * This function saves the entire database to an xml file 'Server.data'
	 * 
	 * @throws RemoteException
	 */
	public void save() throws RemoteException
	{
		String filename = "users.data";
		XMLEncoder encoder = null;
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		} catch (FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File " + filename);
		}
		encoder.writeObject(userList);
		encoder.close();
		
		String filename1 = "depts.data";
		XMLEncoder encoder1 = null;
		try
		{
			encoder1 = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename1)));
		} catch (FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File " + filename1);
		}
		encoder1.writeObject(deptList);
		encoder1.close();
	}

	/**
	 * This method is used to load database
	 * 
	 * @param filepath the filepath to the xml file representation of the object
	 * @return a template object from memory
	 */
	public static DatabaseServer load() throws RemoteException
	{
		String filepath = "users.data";
		XMLDecoder decoder = null;
		try
		{
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filepath)));
		} catch (FileNotFoundException e)
		{
			System.out.println("ERROR: File " + filepath + " not found");
		}
		
		String filepath1 = "depts.data";
		XMLDecoder decoder1 = null;
		try
		{
			decoder1 = new XMLDecoder(new BufferedInputStream(new FileInputStream(filepath1)));
		} catch (FileNotFoundException e)
		{
			System.out.println("ERROR: File " + filepath1 + " not found");
		}
		return new DatabaseServer( (ArrayList<User>) decoder.readObject(), (ArrayList<Department>) decoder1.readObject() );

	}

	/**
	 * This function returns a plan from the user's department based on year
	 * 
	 * @param year the year of the plan to be retrieved
	 * @param u    the user that is requesting a plan
	 * @return the requested plan
	 * @throws RemoteException
	 */
	public Template getPlan(String year, User u) throws RemoteException
	{
		Template t = null;
		Department dept = u.getDepartment();

		for (int i = 0; i < dept.getTemplateList().size(); i++)
		{
			if (year.equals(dept.getTemplateList().get(i).getYear()))
			{
				t = dept.getTemplateList().get(i);
				break;
			}
		}

		return t;

	}

	/**
	 * This function replaces a plan in the database corresponding to the user's
	 * department and the year of the passed plan
	 * 
	 * @param t the plan that will be saved
	 * @param u the user that is submitting the change
	 * @throws RemoteException
	 */
	public void replacePlan(Template t, User u) throws RemoteException
	{
		Department dept = u.getDepartment();

		for (int i = 0; i < dept.getTemplateList().size(); i++)
		{
			if (t.getYear().equals(dept.getTemplateList().get(i).getYear()))
			{
				dept.getTemplateList().set(i, t);
				break;
			}
		}

	}

	/**
	 * This function returns the default plan template, based on user's department
	 * for the user to edit as a 'blank' business plan
	 * 
	 * @param u the user that is creating a new plan
	 * @return the default template
	 * @throws RemoteException
	 */
	public Template makePlan(User u) throws RemoteException
	{
		return deptList.get(deptList.indexOf(u.getDepartment())).getDeptTemplate();
	}

	/**
	 * This function returns the existing plan in the user's department, based on
	 * year, for the user to create a new plan from
	 * 
	 * @param year the year of the plan to be retrieved
	 * @param u    the user that is requesting a plan
	 * @return the requested plan
	 * @throws RemoteException
	 */
	public Template makePlan(String year, User u) throws RemoteException
	{
		return getPlan(year, u);
	}

	/**
	 * This function allows administrators to add users to the system
	 * 
	 * @param u       the user adding the new user (admin)
	 * @param newUser the new user to be added
	 * @throws RemoteException
	 * @throws IllegalAccessException if the user attempting to add is a non-admin
	 */
	public void addUser(User u, User newUser) throws RemoteException
	{
		if (u.isAdmin())
		{
			userList.add(newUser);
		} else
		{
			System.out.println("Received at Server: Non-admin request to add user");
		}

	}

	/**
	 * This function returns whether a user can edit a given template in their
	 * department
	 * 
	 * @param u the editing user
	 * @param t the template the user is attempting to edit
	 * @return true/false whether the user can edit
	 */
	public boolean canEdit(User u, Template t) throws RemoteException
	{
		boolean access = false;

		if (u.isAdmin())
		{
			access = true;
		} else
		{
			access = t.isEditable();
		}

		return access;
	}

	/**
	 * This function takes a username and password and authenticates the login based
	 * on userList
	 * 
	 * @param user the username
	 * @param pass the password
	 * @return the User object associated with authenticated login, null if not
	 *         found
	 */
	public User login(String user, String pass) throws RemoteException
	{
		User auth = null;

		for (int i = 0; i < userList.size(); i++)
		{
			if (userList.get(i).getName().equals(user) && userList.get(i).getPassword().equals(pass))
			{
				auth = userList.get(i);
			}
		}

		return auth;
	}
	
	public ArrayList<User> getUserList()
	{
		return userList;
	}

	public void setUserList(ArrayList<User> userList)
	{
		this.userList = userList;
	}

	public ArrayList<Department> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(ArrayList<Department> deptList)
	{
		this.deptList = deptList;
	}

}
