package software_masters.business_planner;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
	
	private ArrayList<User> userList;
	private ArrayList<Department> deptList;
	User u;
	
	/**
	 * Constructor calls super from UnicastRemoteObject and initializes lists
	 * @throws RemoteException
	 */
	public DatabaseServer() throws RemoteException
	{
		super();
		userList = new ArrayList<User>();
		deptList = new ArrayList<Department>();
	}

	/**
	 * This function saves the entire database to an xml file 'Server.data'
	 * @throws RemoteException
	 */
	public void save() throws RemoteException
	{
		String filename = "Server.data";
		XMLEncoder encoder = null;
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		} catch (FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File " + filename);
		}
		encoder.writeObject(this);
		encoder.close();

	}
	
	/**
	 * This function returns a plan from the user's department based on year
	 * @param year the year of the plan to be retrieved
	 * @param u the user that is requesting a plan
	 * @return the requested plan
	 * @throws RemoteException
	 */
	public Template getPlan(String year, User u) throws RemoteException
	{
		Template t = null;
		Department dept = u.getDepartment();
		
		for (int i = 0; i < dept.templateList.size(); i++)
		{
			if (year.equals(dept.templateList.get(i).getYear()))
			{
				t = dept.templateList.get(i);
				break;
			}
		}
		
		return t;
		
	}
	
	/**
	 * This function replaces a plan in the database corresponding to the user's
	 * department and the year of the passed plan
	 * @param t the plan that will be saved
	 * @param u the user that is submitting the change
	 * @throws RemoteException
	 */
	public void replacePlan(Template t, User u) throws RemoteException
	{
		Department dept = u.getDepartment();
		
		for (int i = 0; i < dept.templateList.size(); i++)
		{
			if (t.getYear().equals(dept.templateList.get(i).getYear()))
			{
				dept.templateList.set(i, t);
				break;
			}
		}

	}
	
	/**
	 * This function returns the default plan template, based on user's department
	 * for the user to edit as a 'blank' business plan
	 * @param u the user that is creating a new plan
	 * @return the default template
	 * @throws RemoteException
	 */
	public Template makePlan(User u) throws RemoteException
	{
		return deptList.get(deptList.indexOf(u.getDepartment())).deptTemplate;
	}
	
	/**
	 * This function returns the existing plan in the user's department, based
	 * on year, for the user to create a new plan from
	 * @param year the year of the plan to be retrieved
	 * @param u the user that is requesting a plan
	 * @return the requested plan
	 * @throws RemoteException
	 */
	public Template makePlan(String year, User u) throws RemoteException
	{
		return getPlan(year,u);
	}
	
	/**
	 * This function allows administrators to add users to the system
	 * @param u the user adding the new user (admin)
	 * @param newUser the new user to be added
	 * @throws RemoteException
	 * @throws IllegalAccessException if the user attempting to add is a non-admin
	 */
	public void addUser(User u, User newUser) throws RemoteException
	{
		if (u.isAdmin)
		{
			userList.add(newUser);
		}
		else
		{
			System.out.println("Received at Server: Non-admin request to add user");
		}

	}

	/**
	 * This function returns whether a user can edit a given template in their
	 * department
	 * @param u the editing user
	 * @param t the template the user is attempting to edit
	 * @return true/false whether the user can edit
	 */
	public boolean canEdit(User u, Template t) throws RemoteException
	{
		boolean access = false;
		
		if (u.isAdmin)
		{
			access = true;
		}
		else
		{
			access = t.isEditable();
		}
		
		return access;
	}

}
