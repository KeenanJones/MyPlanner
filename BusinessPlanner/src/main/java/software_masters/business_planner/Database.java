package software_masters.business_planner;

import java.rmi.*;

/**
 * @author alexander.garuba
 * 
 *         This interface lays out abstraction for the business plan server database.
 * 
 * @since 3.12.2019
 */
public interface Database extends Remote
{

	void save() throws RemoteException;

	Template getPlan(String year, User u) throws RemoteException;

	void replacePlan(Template t, User u) throws RemoteException;

	Template makePlan(User u) throws RemoteException;

	Template makePlan(String year, User u) throws RemoteException;

	void addUser(User u, User newUser) throws RemoteException;

	boolean canEdit(User u, Template t) throws RemoteException;
	
	User login(String user, String pass) throws RemoteException;
}