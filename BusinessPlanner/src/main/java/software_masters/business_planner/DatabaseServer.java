package software_masters.business_planner;

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

	public DatabaseServer() throws RemoteException
	{
		super();
	}

	public void save() throws RemoteException
	{
		// TODO Auto-generated method stub

	}

	public Template getPlan(String year, User u) throws RemoteException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void replacePlan(Template t, User u) throws RemoteException
	{
		// TODO Auto-generated method stub

	}

	public Template makePlan(User u) throws RemoteException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Template makePlan(String year, User u) throws RemoteException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void addUser(User u, User newUser) throws RemoteException
	{
		// TODO Auto-generated method stub

	}

	public boolean canEdit(User u, Template t) throws RemoteException
	{
		// TODO Auto-generated method stub
		return false;
	}

}
